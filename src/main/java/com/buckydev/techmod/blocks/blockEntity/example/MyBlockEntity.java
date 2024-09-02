package com.buckydev.techmod.blocks.blockEntity.example;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.interfaces.IBlockEntityServerTickable;
import com.buckydev.techmod.menu.custom.exampleBE.ExampleBEMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: can this be moved into a class in the {BaseEntityBlock} subclass?
// TODO: the capability system is completely revamped from forge 1.20.X so old forge guides no longer work
//      see mcjty: tutorial wiki
// block entity; the data holder
public class MyBlockEntity extends BlockEntity implements MenuProvider, IBlockEntityServerTickable {
    public static final int SLOT_SIZE = 2;
    private final ItemStackHandler itemHandler = createItemHandler();
    private final Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);

    public static final String INVENTORY_KEY = "Inventory";
    public static final int INVENTORY_KEY_TYPE = CompoundTag.TAG_COMPOUND;

    public MyBlockEntity(BlockEntityType<MyBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        loadAdditionalHelper(tag, registries);
    }

    private void loadAdditionalHelper(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains(INVENTORY_KEY, INVENTORY_KEY_TYPE)) {
            itemHandler.deserializeNBT(registries, tag.getCompound(INVENTORY_KEY));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        saveAdditionalHelper(tag, registries);
    }

    private void saveAdditionalHelper(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put(INVENTORY_KEY, itemHandler.serializeNBT(registries));
    }

    // Extra networking related code from McJty - unsure if I really need it
    // #region start
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditionalHelper(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
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
    public void onDataPacket(
            Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        CompoundTag tag = pkt.getTag();
        if (tag.isEmpty()) {
            handleUpdateTag(tag, lookupProvider);
        }
    }
    // #region end

    // Copied from McJty
    // https://www.mcjty.eu/docs/1.20.4_neo/ep2#the-block-entity-class
    @NotNull
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(SLOT_SIZE) {
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
        return new ExampleBEMenu(containerId, playerInventory, access, getLazyItemHandler());
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (level.getGameTime() % 20 == 1) {
            TechMod.LOGGER.info("Hello from tick at {}", pos.toShortString());
        }
    }
}
