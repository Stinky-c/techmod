package com.buckydev.techmod.datagen.tags;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.ModBlocks;
import com.buckydev.techmod.tags.ModTags.ModBlockTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlocksTagProvider extends BlockTagsProvider {

    public ModBlocksTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TechMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Adds block tags here
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.EXAMPLE_BLOCK.get(), ModBlocks.EXAMPLE_BE.get(), ModBlocks.SEC_MACHINE.get());

        tag(ModBlockTags.WIND_CHARGEABLE)
                .addTags(BlockTags.PLANKS, BlockTags.COAL_ORES)
                .add(ModBlocks.SEC_MACHINE.get(), Blocks.STONE);
    }
}
