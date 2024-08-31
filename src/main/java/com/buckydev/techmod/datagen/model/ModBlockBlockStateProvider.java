package com.buckydev.techmod.datagen.model;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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

        horizontalBlockWithItem(
                ModBlocks.EXAMPLE_BE.get(), new ModelFile.UncheckedModelFile(modLoc("block/example_be")));
        horizontalBlockWithItem(
                ModBlocks.SEC_MACHINE.get(), new ModelFile.UncheckedModelFile(modLoc("block/secondmachine")));
    }

    protected void horizontalBlockWithItem(Block block, ModelFile model) {
        horizontalBlock(block, model);
        itemModels().getBuilder(blockKey(block)).parent(model);
    }

    private String blockKey(Block block) {
        // FIXME: what witchcraft is this?
        return ModBlocks.BLOCKS.getRegistry().get().getKey(block).getPath();
    }
}
