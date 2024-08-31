package com.buckydev.techmod.datagen.recipe;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EXAMPLE_BE.asItem(), 1)
                .pattern("zxz")
                .pattern("xzx")
                .pattern("vvv")
                .define('z', Tags.Items.GEMS_DIAMOND)
                .define('x', Tags.Items.INGOTS_IRON)
                .define('v', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_air", has(Items.AIR)) // Always unlocked?
                .group(TechMod.MODID)
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SEC_MACHINE.asItem(), 1)
                .requires(Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_air", has(Items.AIR))
                .save(output.withConditions(new ModLoadedCondition("jei")));
    }
}
