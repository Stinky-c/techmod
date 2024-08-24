package com.buckydev.techmod.menu;

import com.buckydev.techmod.TechMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPE =
            DeferredRegister.create(Registries.MENU, TechMod.MODID);

    //    public static final DeferredHolder<MenuType>

    public static void submitEventBus(IEventBus eventBus) {
        MENU_TYPE.register(eventBus);
    }
}
