package com.buckydev.techmod.blocks.blockEntity.example;

import com.buckydev.techmod.blocks.abc.baseEntity.AbstractModHorizontalBaseEntityBlock;
import com.buckydev.techmod.utils.VoxelShapeUtils;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// entity Block; the block subclass
// TODO: make generic
public class ExampleBlockEntity<BE extends BlockEntity> extends AbstractModHorizontalBaseEntityBlock<BE> {
    private static final VoxelShape SHAPE = VoxelShapeUtils.combine(
            Shapes.box(0, 0, 0, 1, 0.25, 1),
            Shapes.box(0, 0.75, 0, 1, 1, 1),
            Shapes.box(0.8125, 0.25, 0.3125, 0.9375, 0.75, 0.6875),
            Shapes.box(0.0625, 0.25, 0.3125, 0.1875, 0.75, 0.6875),
            Shapes.box(0.3125, 0.25, 0.0625, 0.6875, 0.75, 0.1875),
            Shapes.box(0.3125, 0.25, 0.8125, 0.6875, 0.75, 0.9375),
            Shapes.box(0.125, 0.25, 0.125, 0.875, 0.75, 0.875));
    private static final Map<Direction, VoxelShape> SHAPE_MAP = VoxelShapeUtils.horizontalMap(SHAPE);

    public ExampleBlockEntity(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var direction = state.getValue(FACING);
        return SHAPE_MAP.getOrDefault(direction, SHAPE);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }
}
