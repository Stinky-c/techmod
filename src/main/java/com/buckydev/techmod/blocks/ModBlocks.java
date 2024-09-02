package com.buckydev.techmod.blocks;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.abc.DeferredModBlock;
import com.buckydev.techmod.blocks.blockEntity.example.ExampleBlockEntity;
import com.buckydev.techmod.blocks.blockEntity.example.MyBlockEntity;
import com.buckydev.techmod.blocks.blockEntity.secMachine.SecMachine;
import com.buckydev.techmod.items.ModItems;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TechMod.MODID);

    public static final DeferredModBlock<Block> EXAMPLE_BLOCK = registerBlock(
            "example_block",
            () -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()));

    public static final DeferredModBlock<ExampleBlockEntity<MyBlockEntity>> EXAMPLE_BE = registerBlock(
            "example_be", () -> new ExampleBlockEntity<>(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredModBlock<SecMachine> SEC_MACHINE = registerBlock(
            "secondmachine", () -> new SecMachine(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    // Methods
    private static <T extends Block> DeferredModBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> deferredBlock = BLOCKS.register(name, blockSupplier);
        DeferredItem<BlockItem> deferredItem = registerBlockItem(name, deferredBlock);
        return new DeferredModBlock<>(deferredBlock, deferredItem);
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(
            String name, DeferredBlock<T> deferredBlock) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(deferredBlock.get(), new Item.Properties()));
    }

    public static void submitEventBus(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
