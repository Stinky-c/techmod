package com.buckydev.techmod.blocks.abc.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractModHorizontalBlock extends AbstractModBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public AbstractModHorizontalBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /**
     * @see net.minecraft.world.level.block.EndPortalFrameBlock#getStateForPlacement
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return getStateDefinition()
                .any()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
