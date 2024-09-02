package com.buckydev.techmod.recipes.simple;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record SimpleRecipeInput(ItemStack inputStack) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
        if (slot != 0) throw new IllegalArgumentException("No item for index " + slot);
        return this.inputStack;
    }

    @Override
    public int size() {
        return 1;
    }
}
