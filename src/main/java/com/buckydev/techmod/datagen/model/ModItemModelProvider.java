package com.buckydev.techmod.datagen.model;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.ModBlocks;
import com.buckydev.techmod.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TechMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // It expects a string as the first argument for some reason
        withExistingParent(ModBlocks.EXAMPLE_BLOCK.asItem().toString(), modLoc("block/example_block"));
        withExistingParent(
                ModBlocks.EXAMPLE_BE.asItem().toString(),
                modLoc("block/example_be")); // FIXME: renders the block item wrong

        withExistingParent(ModBlocks.SEC_MACHINE.asItem().toString(), modLoc("block/secondmachine"));

        basicItem(ModItems.EXAMPLE_FOOD.get());
        basicItem(ModItems.EXAMPLE_ITEM.get());
    }
}
