package com.buckydev.techmod.datagen;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.datagen.loot.ModBlocksLootProvider;
import com.buckydev.techmod.datagen.model.ModBlockBlockStateProvider;
import com.buckydev.techmod.datagen.model.ModItemModelProvider;
import com.buckydev.techmod.datagen.tags.ModBlocksTagProvider;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = TechMod.MODID)
public class ModDatagenHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent dataEvent) {
        DataGenerator generator = dataEvent.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fileHelper = dataEvent.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = dataEvent.getLookupProvider();

        // https://docs.neoforged.net/docs/resources/#data-generation

        generator.addProvider(dataEvent.includeClient(), new ModBlockBlockStateProvider(output, fileHelper));
        generator.addProvider(dataEvent.includeClient(), new ModItemModelProvider(output, fileHelper));

        generator.addProvider(dataEvent.includeServer(), new ModBlocksTagProvider(output, lookupProvider, fileHelper));
        generator.addProvider(
                dataEvent.includeServer(),
                new LootTableProvider(
                        output,
                        // A set of required table resource locations. These are later verified to be present.
                        // It is generally not recommended for mods to validate existence,
                        // therefore we pass in an empty set.
                        Set.of(),
                        // A list of sub provider entries.
                        List.of(new SubProviderEntry(ModBlocksLootProvider::new, LootContextParamSets.BLOCK)),
                        lookupProvider));
    }
}
