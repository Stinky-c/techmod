package com.buckydev.techmod.datacomponents;

import com.buckydev.techmod.TechMod;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * <a href="https://docs.neoforged.net/docs/datastorage/codecs/#existing-codecs">Neoforge existing normal codecs</a>
 * <a href="https://docs.neoforged.net/docs/networking/streamcodecs/#existing-stream-codecs">Neoforge existing stream codecs</a>
 */
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

    public static final Supplier<DataComponentType<ItemContainerContents>> INPUT_CONTAINER =
            DATA_COMPONENTS.registerComponentType(
                    "input_container", builder -> builder.persistent(ItemContainerContents.CODEC)
                            .networkSynchronized(ItemContainerContents.STREAM_CODEC)
                            .cacheEncoding());

    public static final Supplier<DataComponentType<ItemContainerContents>> OUTPUT_CONTAINER =
            DATA_COMPONENTS.registerComponentType(
                    "output_container", builder -> builder.persistent(ItemContainerContents.CODEC)
                            .networkSynchronized(ItemContainerContents.STREAM_CODEC)
                            .cacheEncoding());

    public static final Supplier<DataComponentType<Integer>> MANA_AMOUNT = DATA_COMPONENTS.registerComponentType(
            "mana_amount", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static void submitEventBus(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
