package com.buckydev.techmod.fluids;

import com.buckydev.techmod.TechMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, TechMod.MODID);

    //    public static final DeferredHolder<Fluid, Fluid> EXAMPLE_FLUID =
    //            FLUIDS.register("example_fluid", () -> ...);

    public static void registerFluid() {}

    public static void submitEventBus(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
