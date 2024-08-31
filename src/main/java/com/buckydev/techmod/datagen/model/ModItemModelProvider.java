package com.buckydev.techmod.datagen.model;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.ModBlocks;
import com.buckydev.techmod.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TechMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basicBlockItem(ModBlocks.EXAMPLE_BLOCK.asItem(), modLoc("block/example_block"));

        basicItem(ModItems.EXAMPLE_FOOD.get());
        basicItem(ModItems.EXAMPLE_ITEM.get());
    }

    private void basicBlockItem(ItemLike item, ResourceLocation rl) {
        withExistingParent(item.toString(), rl);
    }
}
