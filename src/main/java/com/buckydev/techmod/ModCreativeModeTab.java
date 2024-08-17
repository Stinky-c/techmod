package com.buckydev.techmod;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TechMod.MODID);

    public static final Supplier<CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register(
            "mod_tab", () -> CreativeModeTab.builder().build());

    public static void sumbitEventBus(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
