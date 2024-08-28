package com.buckydev.techmod.blocks;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.blockEntity.example.MyBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TechMod.MODID);
    // Always strongly type
    public static final Supplier<BlockEntityType<MyBlockEntity>> EX_BE = BLOCK_ENTITY_TYPE.register(
            "ex_be", () -> BlockEntityType.Builder.of(MyBlockEntity::new, ModBlocks.EXAMPLE_BE.get())
                    .build(null));

    // FIXME: here be dragons - mod loading breaks when using this helper function
    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBE(
            String name, BlockEntitySupplier<T> blockEntity, Block... validBlocks) {
        return BLOCK_ENTITY_TYPE.register(
                name, () -> BlockEntityType.Builder.of(blockEntity, validBlocks).build(null));
        // Data type is optional ; BlockEntityType$Builder#build
        // https://docs.neoforged.net/docs/blockentities/#registering
    }

    public static void submitEventBus(IEventBus eventBus) {
        eventBus.addListener(ModBlockEntities::registerBlockEntityCapabilities);
        BLOCK_ENTITY_TYPE.register(eventBus);
    }

    public static void registerBlockEntityCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                ItemHandler.BLOCK, ModBlockEntities.EX_BE.get(), (object, context) -> object.getLazyItemHandler());
    }
}
