package com.buckydev.techmod.capabilities.impl.item;

import java.util.function.Consumer;

/**
 * See {@link net.neoforged.neoforge.items.IItemHandler}
 */
public class InputItemHandler extends BaseItemStackHandler {

    public InputItemHandler(int size, Consumer<Integer> contentsChangeHandler) {
        super(size, contentsChangeHandler);
    }

    //    @Override
    //    public ItemStack extractItem(int slot, int amount, boolean simulate) {
    //        // TODO: test me
    //        return ItemStack.EMPTY;
    //    }
}
