package com.buckydev.techmod.capabilities.impl.item;

import java.util.function.Consumer;

/**
 * See {@link net.neoforged.neoforge.items.IItemHandler}
 */
public class OutputItemHandler extends BaseItemStackHandler {

    public OutputItemHandler(int size, Consumer<Integer> contentsChangeHandler) {
        super(size, contentsChangeHandler);
    }

    //    @Override
    //    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
    //        return stack;
    //    }
}
