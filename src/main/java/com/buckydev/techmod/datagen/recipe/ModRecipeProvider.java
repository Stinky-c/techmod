package com.buckydev.techmod.datagen.recipe;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.ModBlocks;
import com.buckydev.techmod.recipes.builder.RightClickBlockRecipeBuilder;
import com.buckydev.techmod.recipes.builder.SimpleRecipeBuilder;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
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

        new RightClickBlockRecipeBuilder(
                        // Our constructor parameters. This example adds the ever-popular dirt -> diamond conversion.
                        new ItemStack(Items.DIAMOND), Blocks.DIRT.defaultBlockState(), Ingredient.of(Items.APPLE))
                .unlockedBy("has_apple", has(Items.APPLE))
                .save(output);

        new SimpleRecipeBuilder(new ItemStack(Items.DIAMOND), Ingredient.of(Items.APPLE))
                .unlockedBy("has_apple", has(Items.APPLE))
                .save(output, TechMod.createRL("apple"));
    }
}
