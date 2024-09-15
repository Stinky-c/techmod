package com.buckydev.techmod.capabilities.impl.item;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;

/**
 * See {@link net.neoforged.neoforge.items.IItemHandler}
 */
public class OutputItemHandler extends BaseItemStackHandler {

    public OutputItemHandler(int size, Consumer<Integer> contentsChangeHandler) {
        super(size, contentsChangeHandler);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return false;
    }
}
