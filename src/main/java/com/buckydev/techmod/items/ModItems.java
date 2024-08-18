package com.buckydev.techmod.items;

import com.buckydev.techmod.TechMod;
import java.util.function.Supplier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TechMod.MODID);

    public static final DeferredItem<Item> EXAMPLE_FOOD = registerSimpleItem(
            "example_food",
            new Properties()
                    .food(new FoodProperties.Builder()
                            .alwaysEdible()
                            .nutrition(4)
                            .saturationModifier(4f)
                            .build())
                    .stacksTo(16));

    public static final DeferredItem<PickaxeItem> EXAMPLE_FOOD2 = registerItem(
            "eat_pickaxe",
            () -> new PickaxeItem(
                    Tiers.IRON,
                    new Item.Properties()
                            .attributes(PickaxeItem.createAttributes(Tiers.IRON, 3f, 3f))
                            .food(new FoodProperties.Builder()
                                    .alwaysEdible()
                                    .nutrition(4)
                                    .saturationModifier(4)
                                    .build())));

    public static final DeferredItem<Item> EXAMPLE_ITEM = registerSimpleItem("example_item", new Item.Properties());

    private static DeferredItem<Item> registerSimpleItem(String name, Item.Properties itemProperties) {
        return ITEMS.registerSimpleItem(name, itemProperties);
    }

    private static <T extends Item> DeferredItem<T> registerItem(String name, Supplier<T> itemSupplier) {
        return ITEMS.register(name, itemSupplier);
    }

    public static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {}

    public static void submitEventBus(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
