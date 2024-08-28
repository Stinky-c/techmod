package com.buckydev.techmod.creativeTabs;

import com.buckydev.techmod.TechMod;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class DeferredCreativeTabs extends DeferredRegister<CreativeModeTab> {

    // Copied straight from mekansim with modifications, I <3 Mek
    // https://github.com/mekanism/Mekanism/blob/572d8c57f841b4e951d7b31f4a290b4306ee4d29/src/main/java/mekanism/common/registration/impl/CreativeTabDeferredRegister.java#L1

    public DeferredCreativeTabs() {
        this(event -> {});
    }

    public DeferredCreativeTabs(Consumer<BuildCreativeModeTabContentsEvent> addToExistingTabs) {
        super(Registries.CREATIVE_MODE_TAB, TechMod.MODID);
    }

    @Override
    public void register(@NotNull IEventBus bus) {
        super.register(bus); // TODO: clean this maybe
    }

    public DeferredHolder<CreativeModeTab, CreativeModeTab> registerMain(
            String title, Supplier<ItemStack> icon, UnaryOperator<CreativeModeTab.Builder> operator) {
        return register(getNamespace(), title, icon, operator);
    }

    public DeferredHolder<CreativeModeTab, CreativeModeTab> register(
            String name, String title, Supplier<ItemStack> icon, UnaryOperator<CreativeModeTab.Builder> operator) {
        return register(name, () -> {
            CreativeModeTab.Builder builder = CreativeModeTab.builder()
                    .title(Component.translatable(title))
                    .icon(icon)
                    .withTabFactory(ModCreativeTab::new);
            return operator.apply(builder).build();
        });
    }

    public static void addToDisplay(CreativeModeTab.Output output, ItemLike... items) {
        for (ItemLike item : items) {
            addToDisplay(output, item);
        }
    }

    public static void addToDisplay(CreativeModeTab.Output output, ItemLike itemLike) {
        CreativeModeTab.TabVisibility visibility;
        if (output instanceof BuildCreativeModeTabContentsEvent) {
            // If we are added from the event, only add the item to the parent tab, as we will already be contained in
            // the search tab
            // from when we are adding to our tabs
            visibility = CreativeModeTab.TabVisibility.PARENT_TAB_ONLY;
        } else {
            visibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
        }
        if (itemLike.asItem() instanceof ICustomCreativeTabContents contents) {
            if (contents.addDefault()) {
                output.accept(itemLike, visibility);
            }
            contents.addItems(stack -> output.accept(stack, visibility));
        } else {
            output.accept(itemLike, visibility);
        }
    }

    public static void addToDisplay(DeferredRegister.Items register, CreativeModeTab.Output output) {
        for (Holder<Item> itemProvider : register.getEntries()) {
            addToDisplay(output, itemProvider.value());
        }
    }

    public static void addToDisplay(DeferredRegister.Blocks register, CreativeModeTab.Output output) {
        for (Holder<Block> blockProvider : register.getEntries()) {
            Block block = blockProvider.value();
            addToDisplay(output, block);
        }
    }

    //    public static void addToDisplay(FluidDeferredRegister register, CreativeModeTab.Output output) {
    //        for (Holder<Item> bucket : register.getBucketEntries()) {
    //            addToDisplay(output, bucket.value());
    //        }
    //    }

    public interface ICustomCreativeTabContents {

        void addItems(Consumer<ItemStack> addToTab);

        default boolean addDefault() {
            return true;
        }
    }

    public static class ModCreativeTab extends CreativeModeTab {

        protected ModCreativeTab(CreativeModeTab.Builder builder) {
            super(builder);
        }
    }
}
