package com.buckydev.techmod.recipes.test;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RightClickBlockRecipeSerializer implements RecipeSerializer<RightClickBlockRecipe> {
    public static final MapCodec<RightClickBlockRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                    BlockState.CODEC.fieldOf("state").forGetter(RightClickBlockRecipe::getInputState),
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(RightClickBlockRecipe::getInputItem),
                    ItemStack.CODEC.fieldOf("result").forGetter(RightClickBlockRecipe::getResult))
            .apply(inst, RightClickBlockRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RightClickBlockRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY),
                    RightClickBlockRecipe::getInputState,
                    Ingredient.CONTENTS_STREAM_CODEC,
                    RightClickBlockRecipe::getInputItem,
                    ItemStack.STREAM_CODEC,
                    RightClickBlockRecipe::getResult,
                    RightClickBlockRecipe::new);

    // Return our map codec.
    @Override
    public MapCodec<RightClickBlockRecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, RightClickBlockRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
