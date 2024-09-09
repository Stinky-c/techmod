package com.buckydev.techmod.utils.handlers;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ModItemStackHandler extends ItemStackHandler {

    public ModItemStackHandler() {
        this(1);
    }

    public ModItemStackHandler(int size) {
        this(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    public ModItemStackHandler(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public boolean isEmpty() {
        // find all items stacks that aren't empty, and check if there is a non-empty one
        return this.stacks.stream()
                .filter(itemStack -> !itemStack.isEmpty())
                .findFirst()
                .isEmpty();
    }

    public NonNullList<ItemStack> copyOfStacks() {
        return NonNullList.copyOf(this.stacks);
    }

    public ItemContainerContents containerize() {
        return ItemContainerContents.fromItems(copyOfStacks());
    }
}
