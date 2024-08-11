package com.buckydev.techmod.items;

import com.buckydev.techmod.TechMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TechMod.MODID);

	public static final DeferredItem<Item> EXAMPLE_ITEM = registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(2f).build()));
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = TechMod.CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.techmod")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> EXAMPLE_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
		output.accept(EXAMPLE_ITEM.get());
	}).build());

	private static DeferredItem<Item> registerSimpleItem(String name, Item.Properties properties) {
		return ITEMS.registerSimpleItem(name, properties);
	}

	public static void submitEventBus(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
