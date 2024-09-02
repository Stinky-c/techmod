package com.buckydev.techmod.recipes.simple;

import com.buckydev.techmod.recipes.ModRecipes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SimpleRecipe implements Recipe<SimpleRecipeInput> {

    private final ItemStack result;
    private final Ingredient inputItem;

    public SimpleRecipe(ItemStack result, Ingredient inputItem) {
        this.result = result;
        this.inputItem = inputItem;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean matches(SimpleRecipeInput input, Level level) {
        return this.inputItem.test(input.inputStack());
    }

    @Override
    public ItemStack assemble(SimpleRecipeInput input, Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getResultItem(Provider registries) {
        return this.result;
    }

    public Ingredient getInputItem() {
        return this.inputItem;
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SIMPLE_RECIPE_SER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SIMPLE_RECIPE.get();
    }
}
