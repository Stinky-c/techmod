package com.buckydev.techmod.capabilities.impl.item;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

/**
 * Useful for block caps
 */
public class WrappedInputItemHandler implements IItemHandlerModifiable {
    private final IItemHandlerModifiable handler;

    public WrappedInputItemHandler(IItemHandlerModifiable parent) {
        this.handler = parent;
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
     * Returns nothing when trying to extract.
     */
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
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
     */
    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return this.isItemValid(slot, stack);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.handler.setStackInSlot(slot, stack);
    }
}
