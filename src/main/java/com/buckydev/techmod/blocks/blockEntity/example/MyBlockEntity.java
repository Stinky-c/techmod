package com.buckydev.techmod.blocks.blockEntity.example;

import com.buckydev.techmod.blocks.abc.blockEntity.ModBlockEntity;
import com.buckydev.techmod.blocks.abc.interfaces.IBlockEntityServerTickable;
import com.buckydev.techmod.datacomponents.ModDataComponents;
import com.buckydev.techmod.menu.ModMenus;
import com.buckydev.techmod.recipes.ModRecipes;
import com.buckydev.techmod.recipes.simple.SimpleRecipe;
import com.buckydev.techmod.recipes.simple.SimpleRecipeInput;
import com.buckydev.techmod.utils.handlers.item.ModItemStackHandler;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentMap.Builder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: can this be moved into a class in the {BaseEntityBlock} subclass?
// TODO: the capability system is completely revamped from forge 1.20.X so old forge guides no longer work
//      see mcjty: tutorial wiki

// Have separate input & output handlers, then expose a {CombinedInvWrapper} for caps
// https://www.mcjty.eu/docs/1.20.4_neo/ep3#the-block-entity

// implicit components, at construct time collect all interfaces, filter for is `isAssignableFrom` by
// a generic interface, collect all into a list held during runtime, apply and collect at needed times
// block entity; the data holder
public class MyBlockEntity extends ModBlockEntity<MyBlockEntity> implements MenuProvider, IBlockEntityServerTickable {
    public static final int SLOT_SIZE = 2;
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    private final ModItemStackHandler itemHandler = createItemHandler();
    private final Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);

    public MyBlockEntity(BlockEntityType<MyBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, Provider registries) {
        super.loadAdditional(tag, registries);
        loadAdditionalHelper(tag, registries);
    }

    private void loadAdditionalHelper(CompoundTag tag, Provider registries) {
        if (tag.contains(TAG_INVENTORY_KEY, TAG_INVENTORY_TYPE)) {
            itemHandler.deserializeNBT(registries, tag.getCompound(TAG_INVENTORY_KEY));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, Provider registries) {
        super.saveAdditional(tag, registries);
        saveAdditionalHelper(tag, registries);
    }

    private void saveAdditionalHelper(CompoundTag tag, Provider registries) {
        tag.put(TAG_INVENTORY_KEY, itemHandler.serializeNBT(registries));
    }

    // Extra networking related code from McJty - unsure if I really need it
    // #region start
    @Override
    public CompoundTag getUpdateTag(Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditionalHelper(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, Provider lookupProvider) {
        if (tag.isEmpty()) {
            loadAdditionalHelper(tag, lookupProvider);
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // Client sided
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, Provider lookupProvider) {
        CompoundTag tag = pkt.getTag();
        if (tag.isEmpty()) {
            handleUpdateTag(tag, lookupProvider);
        }
    }
    // #region end

    // Copied from McJty
    // https://www.mcjty.eu/docs/1.20.4_neo/ep2#the-block-entity-class
    @NotNull
    private ModItemStackHandler createItemHandler() {
        return new ModItemStackHandler(SLOT_SIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        };
    }

    public IItemHandler getLazyItemHandler() {
        return lazyItemHandler.get();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.techmod.example_be.title");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        var access = ContainerLevelAccess.create(getLevel(), getBlockPos());
        // need to pass in **THIS** item handler
        return ModMenus.MY_MENU.get().create(containerId, playerInventory);
        //        return new ExampleBEMenu(containerId, playerInventory, access, getLazyItemHandler());
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        // TODO: ensure everything is done right
        if (level.getGameTime() % 20 == 0) {
            var recipe = getRecipe(this.itemHandler.getStackInSlot(INPUT_SLOT));

            recipe.ifPresent(recipeHolder ->
                    doRecipe(recipeHolder, new SimpleRecipeInput(this.itemHandler.getStackInSlot(INPUT_SLOT))));
        }
    }

    public Optional<RecipeHolder<SimpleRecipe>> getRecipe(ItemStack inputStack) {
        var b = new SimpleRecipeInput(inputStack);
        return recipeManager.getRecipeFor(ModRecipes.SIMPLE_RECIPE.get(), b, getLevel());
    }

    public boolean hasRecipe() {
        var inputSlot = this.itemHandler.getStackInSlot(INPUT_SLOT);
        if (inputSlot != ItemStack.EMPTY) {
            return getRecipe(inputSlot).isPresent();
        }
        return false;
    }

    public void doRecipe(RecipeHolder<SimpleRecipe> recipeHolder, SimpleRecipeInput recipeInput) {
        var v = recipeHolder.value();
        var result = v.assemble(recipeInput, level.registryAccess());
        if (!result.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, result);
        }
    }

    // https://discord.com/channels/313125603924639766/1249305774987939900/1278200813214502933
    // apply; item -> BE
    // collect; BE -> item
    // TODO: abstract this
    @Override
    protected void applyImplicitComponents(DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        var v = componentInput.getOrDefault(ModDataComponents.CONTAINER, ItemContainerContents.EMPTY);
        for (int i = 0; i < v.getSlots(); i++) {
            itemHandler.setStackInSlot(i, v.getStackInSlot(i));
        }
    }

    @Override
    protected void collectImplicitComponents(Builder components) {
        super.collectImplicitComponents(components);
        if (!itemHandler.isEmpty()) {
            components.set(ModDataComponents.CONTAINER, itemHandler.containerize());
        }
    }

    @Override
    public DataComponentMap components() {
        return super.components();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
}
