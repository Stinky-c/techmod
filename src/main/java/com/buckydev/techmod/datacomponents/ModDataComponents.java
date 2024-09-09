package com.buckydev.techmod.datacomponents;

import com.buckydev.techmod.TechMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(TechMod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ModBlockContainer>> CONTAINER_CUSTOM =
            DATA_COMPONENTS.registerComponentType(
                    "container_custom", builder -> builder.persistent(ModBlockContainer.CODEC)
                            .networkSynchronized(ModBlockContainer.STREAM_CODEC)
                            .cacheEncoding());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemContainerContents>> CONTAINER =
            DATA_COMPONENTS.registerComponentType(
                    "container", builder -> builder.persistent(ItemContainerContents.CODEC)
                            .networkSynchronized(ItemContainerContents.STREAM_CODEC)
                            .cacheEncoding());

    public static void submitEventBus(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
