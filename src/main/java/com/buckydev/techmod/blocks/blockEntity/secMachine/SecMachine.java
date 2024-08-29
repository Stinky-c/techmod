package com.buckydev.techmod.blocks.blockEntity.secMachine;

import com.buckydev.techmod.utils.ShapeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SecMachine extends Block {
    private static final VoxelShape SHAPE = ShapeBuilder.empty()
            .joinOR(Shapes.box(0, 0, 0, 1, 0.125, 1))
            .joinOR(Shapes.box(0.0625, 0.125, 0.125, 0.9375, 0.875, 0.5625))
            .joinOR(Shapes.box(0, 0.875, 0, 1, 1, 1))
            .joinOR(Shapes.box(0.125, 0.125, 0.6875, 0.875, 0.875, 0.9375))
            .joinOR(Shapes.box(0.0625, 0.125, 0.5625, 0.9375, 0.25, 0.6875))
            .joinOR(Shapes.box(0.0625, 0.75, 0.5625, 0.9375, 0.875, 0.6875))
            .joinOR(Shapes.box(0.125, 0.1875, 0.0625, 0.875, 0.8125, 0.125))
            .getVoxelShape();

    public SecMachine(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
