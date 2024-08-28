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
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class ModRecipeProvider extends RecipeProvider {
    // Get the parameters from GatherDataEvent.
    public ModRecipeProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EXAMPLE_BE.asItem(), 1)
                .pattern("zxz")
                .pattern("xzx")
                .pattern("vvv")
                .define('z', Tags.Items.GEMS_DIAMOND)
                .define('x', Tags.Items.INGOTS_IRON)
                .define('v', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_air", has(Items.AIR)) // Always unlocked?
                .group(TechMod.MODID)
                .save(output);
    }
}
