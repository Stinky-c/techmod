package com.buckydev.techmod.blocks.abc.baseEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractModBaseEntityBlock<BE extends BlockEntity> extends BaseEntityBlock {

    protected AbstractModBaseEntityBlock(Properties properties) {
        super(properties);
    }

    private Class<BE> beClass;
    private BlockEntityType<BE> beType;

    @Nullable
    private BlockEntityTicker<BE> serverTicker;

    @Nullable
    private BlockEntityTicker<BE> clientTicker;

    public void updateBlockEntity(
            Class<BE> blockEntityClass,
            BlockEntityType<BE> blockEntityType,
            BlockEntityTicker<BE> clientTicker,
            BlockEntityTicker<BE> serverTicker) {
        this.beClass = blockEntityClass;
        this.beType = blockEntityType;
        this.clientTicker = clientTicker;
        this.serverTicker = serverTicker;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return this.beType.create(pos, state);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return super.getMenuProvider(state, level, pos); // Uses 'instanceof' to check if the block can cast to
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> blockEntityType) {

        return (BlockEntityTicker<T>) (level.isClientSide() ? clientTicker : serverTicker);

        // FIXME: figure out generic impl of this function, tick method needs to be present on BlockEntity. see
        // {FurnaceBlock#getTicker}

        // Not sure why this cast isn't safe, may need to make T generic to whole class
        /*
                return createTickerHelper(
                        blockEntityType,
                        getBlockEntityType(),
                        (lLevel, lPos, lState, lBlockEntity) -> lBlockEntity instanceof BlockEntityTicker<T> be
                                ? be.tick(lLevel, lPos, lState, lBlockEntity)
                                : null);

                return createTickerHelper(blockEntityType, getBlockEntityType(), (lLevel, lPos, lState, lBlockEntity) -> {});

        */
    }
}
