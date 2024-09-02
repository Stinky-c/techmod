package com.buckydev.techmod.blocks;

import com.buckydev.techmod.TechMod;
import com.buckydev.techmod.blocks.abc.DeferredModBlock;
import com.buckydev.techmod.blocks.abc.baseEntity.AbstractModBaseEntityBlock;
import com.buckydev.techmod.blocks.blockEntity.example.MyBlockEntity;
import com.buckydev.techmod.blocks.interfaces.IBlockEntityClientTickable;
import com.buckydev.techmod.blocks.interfaces.IBlockEntityServerTickable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    // NOTE: access to unbound DeferredHolders is caused when the value is gotten before its assigned
    // ensure all `DeferredHolder#get` calls are behind lambdas

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TechMod.MODID);

    public static final Supplier<BlockEntityType<MyBlockEntity>> EX_BE =
            register("ex_be", MyBlockEntity.class, MyBlockEntity::new, ModBlocks.EXAMPLE_BE);

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(
            String name,
            Class<T> blockEntityClass,
            BlockEntityFactory<T> factory,
            DeferredModBlock<? extends AbstractModBaseEntityBlock<?>>... validBlocks) {
        // Heavily inspired by AE2
        // https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/92e8deec2417040a46274f3789e97abff485585e/src/main/java/appeng/core/definitions/AEBlockEntities.java#L217

        return BLOCK_ENTITY_TYPE.register(name, () -> {
            AtomicReference<BlockEntityType<T>> typeRef = new AtomicReference<>();
            BlockEntityType.BlockEntitySupplier<T> supplier = (pos, state) -> factory.create(typeRef.get(), pos, state);

            var blocks =
                    Arrays.stream(validBlocks).map(DeferredModBlock::get).toArray(AbstractModBaseEntityBlock[]::new);

            BlockEntityType<T> b = BlockEntityType.Builder.of(supplier, blocks).build(null);
            typeRef.set(b);

            BlockEntityTicker<T> serverTicker = null;
            if (IBlockEntityServerTickable.class.isAssignableFrom(blockEntityClass)) {
                serverTicker = (Llevel, lPos, lState, lBlockEntity) ->
                        ((IBlockEntityServerTickable) lBlockEntity).serverTick(Llevel, lPos, lState);
            }
            BlockEntityTicker<T> clientTicker = null;
            if (IBlockEntityClientTickable.class.isAssignableFrom(blockEntityClass)) {
                clientTicker = (lLevel, lPos, lState, lBlockEntity) ->
                        ((IBlockEntityClientTickable) lBlockEntity).clientTick(lLevel, lPos, lState);
            }

            for (var block : blocks) {
                block.updateBlockEntity(blockEntityClass, b, clientTicker, serverTicker);
            }

            return b;
        });
    }

    public static void submitEventBus(IEventBus eventBus) {
        BLOCK_ENTITY_TYPE.register(eventBus);
        eventBus.addListener(ModBlockEntities::registerBlockEntityCapabilities);
    }

    public static void registerBlockEntityCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                ItemHandler.BLOCK, ModBlockEntities.EX_BE.get(), (object, context) -> object.getLazyItemHandler());
    }

    @FunctionalInterface
    public interface BlockEntityFactory<T extends BlockEntity> {
        T create(BlockEntityType<T> blockEntity, BlockPos pos, BlockState state);
    }
}
