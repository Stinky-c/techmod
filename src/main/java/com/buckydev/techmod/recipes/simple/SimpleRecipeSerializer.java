package com.buckydev.techmod.recipes.simple;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleRecipeSerializer implements RecipeSerializer<SimpleRecipe> {

    private final MapCodec<SimpleRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                    ItemStack.CODEC.fieldOf("result").forGetter(SimpleRecipe::getResult),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(SimpleRecipe::getInputItem))
            .apply(inst, SimpleRecipe::new));

    private final StreamCodec<RegistryFriendlyByteBuf, SimpleRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            SimpleRecipe::getResult,
            Ingredient.CONTENTS_STREAM_CODEC,
            SimpleRecipe::getInputItem,
            SimpleRecipe::new);

    @Override
    public MapCodec<SimpleRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SimpleRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
