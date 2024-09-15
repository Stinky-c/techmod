package com.buckydev.techmod.capabilities.impl.item;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

/**
 * Useful for block caps
 */
public class WrappedOutputItemHandler implements IItemHandlerModifiable {
    private final IItemHandlerModifiable handler;

    public WrappedOutputItemHandler(IItemHandlerModifiable handler) {
        this.handler = handler;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getSlots() {
        return this.handler.getSlots();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.handler.getStackInSlot(slot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return this.handler.insertItem(slot, stack, simulate);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.handler.extractItem(slot, amount, simulate);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getSlotLimit(int slot) {
        return this.handler.getSlotLimit(slot);
    }
    /**
     * {@inheritDoc}
     * Always false to prevent items from being inserted
     */
    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.handler.setStackInSlot(slot, stack);
    }
}
