package com.buckydev.techmod.datagen.model;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockBlockStateProvider extends BlockStateProvider {

    public ModBlockBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TechMod.MODID, exFileHelper);
    }
    /**
     *  @see BlockStateProvider
     *  @link <a href="https://docs.neoforged.net/docs/resources/client/models/datagen/#block-model-datagen">Neoforged blockmodel datagen</a>
     *  */
    @Override
    protected void registerStatesAndModels() {

        simpleBlock(ModBlocks.EXAMPLE_BLOCK.get());
    }
}
