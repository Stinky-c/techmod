package com.buckydev.techmod.blocks.blockEntity.example;

import com.buckydev.techmod.blocks.ModBlockEntities;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

// TODO: can this be moved into a class in the {BaseEntityBlock} subclass?
// TODO: the capability system is completely revamped from forge 1.20.X so old forge guides no longer work
//      see mcjty: tutorial wiki
// block entity; the data holder
public class MyBlockEntity extends BlockEntity {
    public static final int SLOT_SIZE = 2;
    private final ItemStackHandler itemHandler = createItemHandler();
    private final Lazy<IItemHandler> lazyItemHandler = Lazy.of(() -> itemHandler);

    public static final String INVENTORY_KEY = "Inventory";
    public static final int INVENTORY_KEY_TYPE = CompoundTag.TAG_COMPOUND;

    public MyBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EX_BE.get(), pos, blockState);
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
    @Nonnull
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
}
