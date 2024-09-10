package com.buckydev.techmod.menu;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.menu.abc.AbstractModMenu;
import com.buckydev.techmod.menu.custom.exampleBE.ExampleBEMenu;
import com.buckydev.techmod.menu.custom.exampleBE.ExampleBEScreen;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPE =
            DeferredRegister.create(Registries.MENU, TechMod.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ExampleBEMenu>> MY_MENU =
            register("example_be", ExampleBEMenu::new);
    // {IMenuTypeExtension#create} - permits having a {FriendlyByteBuf} in the constructor

    public static <T extends AbstractModMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(
            String name, ModMenuFactory<T> factory) {
        return MENU_TYPE.register(name, () -> {
            AtomicReference<MenuType<T>> typeRef = new AtomicReference<>();
            MenuType.MenuSupplier<T> supplier =
                    (containerId, playerInventory) -> factory.create(typeRef.get(), containerId, playerInventory);

            var v = new MenuType<>(supplier, FeatureFlags.DEFAULT_FLAGS);
            typeRef.set(v);
            return v;
        });
    }

    public static void submitEventBus(IEventBus eventBus) {
        eventBus.addListener(ModMenus::registerScreens);
        MENU_TYPE.register(eventBus);
    }

    // listener
    public static void registerScreens(RegisterMenuScreensEvent event) {
        // FIXME: abstract
        event.register(MY_MENU.get(), ExampleBEScreen::new);
    }

    @FunctionalInterface
    public interface ModMenuFactory<T extends AbstractModMenu> {
        T create(MenuType<T> menuType, int containerId, Inventory inventory);
    }
}
