package com.buckydev.techmod.blocks;

import com.buckydev.techmod.TechMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TechMod.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(TechMod.MODID);

    public static final DeferredBlock<Block> EXAMPLE_BLOCK =
            registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM =
            registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    public static DeferredBlock<Block> registerSimpleBlock(String name, BlockBehaviour.Properties properties) {
        return BLOCKS.registerSimpleBlock(name, properties);
    }

    public static DeferredItem<BlockItem> registerSimpleBlockItem(String name, DeferredBlock<Block> deferredBlock) {
        return BLOCK_ITEMS.registerSimpleBlockItem(name, deferredBlock);
    }

    public static void submitEventBus(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }
}
