package com.buckydev.techmod.datagen.loot;

import com.buckydev.techmod.blocks.ModBlocks;
import com.buckydev.techmod.datacomponents.ModDataComponents;
import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

public class ModBlocksLootProvider extends BlockLootSubProvider {

    public ModBlocksLootProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(e -> (Block) e.value())
                .toList();
    }

    @Override
    protected void generate() {
        dropWithPackagedContents(ModBlocks.EXAMPLE_BE.get());
        dropSelf(ModBlocks.EXAMPLE_BLOCK.get());
        dropSelf(ModBlocks.SEC_MACHINE.get());
    }

    protected void dropWithPackagedContents(Block block) {
        var v = LootTable.lootTable()
                .withPool(applyExplosionCondition(
                        block,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(block)
                                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                        .apply(CopyComponentsFunction.copyComponents(
                                                        CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                .include(ModDataComponents.CONTAINER.get())))));
        this.add(block, v);
    }
}
