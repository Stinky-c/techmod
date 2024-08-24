package com.buckydev.techmod.blocks.blockEntity.example;

import com.buckydev.techmod.utils.ShapeBuilder;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

// entity Block; the block subclass
public class ExampleBlockEntity extends BaseEntityBlock {
    private static final VoxelShape SHAPE = ShapeBuilder.empty()
            .join(Shapes.box(0, 0, 0, 1, 0.25, 1), BooleanOp.OR)
            .join(Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR)
            .join(Shapes.box(0.8125, 0.25, 0.3125, 0.9375, 0.75, 0.6875), BooleanOp.OR)
            .join(Shapes.box(0.0625, 0.25, 0.3125, 0.1875, 0.75, 0.6875), BooleanOp.OR)
            .join(Shapes.box(0.3125, 0.25, 0.0625, 0.6875, 0.75, 0.1875), BooleanOp.OR)
            .join(Shapes.box(0.3125, 0.25, 0.8125, 0.6875, 0.75, 0.9375), BooleanOp.OR)
            .join(Shapes.box(0.125, 0.25, 0.125, 0.875, 0.75, 0.875), BooleanOp.OR)
            .getVoxelShape();

    public ExampleBlockEntity(Properties properties) {
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

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MyBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }
}
