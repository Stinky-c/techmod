package com.buckydev.techmod.blocks.blockEntity.secMachine;

import com.buckydev.techmod.blocks.blockEntity.abc.block.AbstractModHorizontalBlock;
import com.buckydev.techmod.utils.VoxelShapeUtils;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SecMachine extends AbstractModHorizontalBlock {
    private static final VoxelShape SHAPE = VoxelShapeUtils.combine(
            Shapes.box(0, 0, 0, 1, 0.125, 1),
            Shapes.box(0.0625, 0.125, 0.125, 0.9375, 0.875, 0.5625),
            Shapes.box(0, 0.875, 0, 1, 1, 1),
            Shapes.box(0.125, 0.125, 0.6875, 0.875, 0.875, 0.9375),
            Shapes.box(0.0625, 0.125, 0.5625, 0.9375, 0.25, 0.6875),
            Shapes.box(0.0625, 0.75, 0.5625, 0.9375, 0.875, 0.6875),
            Shapes.box(0.125, 0.1875, 0.0625, 0.875, 0.8125, 0.125));
    private static final Map<Direction, VoxelShape> SHAPE_MAP = VoxelShapeUtils.horizontalMap(SHAPE);

    public SecMachine(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Don't apply transformations here. It won't run well
        // TODO: abstract this
        Direction direction = state.getValue(FACING);
        return SHAPE_MAP.getOrDefault(direction, SHAPE);
    }
}
