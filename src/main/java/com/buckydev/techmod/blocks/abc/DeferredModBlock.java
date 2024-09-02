package com.buckydev.techmod.blocks.abc;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class DeferredModBlock<T extends Block> implements ItemLike {
    private final DeferredBlock<T> block;
    private final DeferredItem<BlockItem> item;

    public DeferredModBlock(DeferredBlock<T> deferredBlock, DeferredItem<BlockItem> deferredItem) {
        this.block = deferredBlock;
        this.item = deferredItem;
    }

    public T get() {
        return this.block.get();
    }

    @Override
    public Item asItem() {
        return this.item.asItem();
    }
}
