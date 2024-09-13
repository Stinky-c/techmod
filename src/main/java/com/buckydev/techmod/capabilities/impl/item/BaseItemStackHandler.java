package com.buckydev.techmod.capabilities.impl.item;

import java.util.function.Consumer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.items.ItemStackHandler;

public class BaseItemStackHandler extends ItemStackHandler {

    public final Consumer<Integer> contentsChangeHandler;

    public BaseItemStackHandler(Consumer<Integer> contentsChangeHandler) {
        this(1, contentsChangeHandler);
    }

    public BaseItemStackHandler(int size, Consumer<Integer> contentsChangeHandler) {
        this(NonNullList.withSize(size, ItemStack.EMPTY), contentsChangeHandler);
    }

    public BaseItemStackHandler(NonNullList<ItemStack> stacks, Consumer<Integer> contentsChangeHandler) {
        super(stacks);
        this.contentsChangeHandler = contentsChangeHandler;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.contentsChangeHandler.accept(slot);
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
