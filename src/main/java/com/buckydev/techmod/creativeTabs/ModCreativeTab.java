package com.buckydev.techmod.creativeTabs;

import com.buckydev.techmod.items.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModCreativeTab {
    public static final DeferredCreativeTabs CREATIVE_TAB = new DeferredCreativeTabs(ModItems::addToExistingTabs);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_ITEMS_TAB = CREATIVE_TAB.registerMain(
            "itemGroup.techmod",
            () -> ModItems.EXAMPLE_FOOD.get().getDefaultInstance(),
            builder -> builder.displayItems((displayParameters, output) -> {
                DeferredCreativeTabs.addToDisplay(ModItems.ITEMS, output);
                //                DeferredCreativeTabs.addToDisplay();
            }));
}
