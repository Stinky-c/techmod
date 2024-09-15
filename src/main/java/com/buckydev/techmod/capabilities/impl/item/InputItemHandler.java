package com.buckydev.techmod.capabilities.impl.item;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;

/**
 * See {@link net.neoforged.neoforge.items.IItemHandler}.
 * <p>
 * maybe add a "dynamic" filter for {@link InputItemHandler#isItemValid(int, ItemStack)}
 */
public class InputItemHandler extends BaseItemStackHandler {

    public InputItemHandler(int size, Consumer<Integer> contentsChangeHandler) {
        super(size, contentsChangeHandler);
    }
}
