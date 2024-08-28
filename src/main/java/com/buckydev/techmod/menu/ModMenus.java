package com.buckydev.techmod.menu;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.menu.custom.exampleBE.ExampleBEMenu;
import com.buckydev.techmod.menu.custom.exampleBE.ExampleBEScreen;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPE =
            DeferredRegister.create(Registries.MENU, TechMod.MODID);

    public static final Supplier<MenuType<ExampleBEMenu>> MY_MENU =
            MENU_TYPE.register("example_be", () -> new MenuType<>(ExampleBEMenu::new, FeatureFlags.DEFAULT_FLAGS));
    // {IMenuTypeExtension#create} - permits having a {FriendlyByteBuf} in the constructor

    public static void submitEventBus(IEventBus eventBus) {
        eventBus.addListener(ModMenus::registerScreens);
        MENU_TYPE.register(eventBus);
    }

    // listener
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MY_MENU.get(), ExampleBEScreen::new);
    }
}
