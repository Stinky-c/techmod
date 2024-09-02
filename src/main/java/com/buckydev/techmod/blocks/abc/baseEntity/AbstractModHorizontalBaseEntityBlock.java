package com.buckydev.techmod.blocks.abc.baseEntity;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractModHorizontalBaseEntityBlock<BE extends BlockEntity>
        extends AbstractModBaseEntityBlock<BE> {

    protected AbstractModHorizontalBaseEntityBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    /**
     * Ensure super is called
     *
     */
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /**
     * Example extra blockstates:
     *     {@code return super(context).setValue([Extra key], [Extra value])}
     * @see net.minecraft.world.level.block.EndPortalFrameBlock#getStateForPlacement
     *
     */
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateDefinition()
                .any()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
