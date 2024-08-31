package com.buckydev.techmod.utils;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@Deprecated
public class ShapeBuilder {
    private VoxelShape containerShape;

    public ShapeBuilder(VoxelShape shape) {
        containerShape = shape;
    }

    public static ShapeBuilder empty() {
        return new ShapeBuilder(Shapes.empty());
    }

    public static ShapeBuilder box() {
        return ShapeBuilder.box(0, 0, 0, 16, 16, 16);
    }

    public static ShapeBuilder box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new ShapeBuilder(Shapes.box(minX, minY, minZ, maxX, maxY, maxZ));
    }

    public ShapeBuilder join(VoxelShape shape2, BooleanOp operator) {
        this.containerShape = Shapes.join(containerShape, shape2, operator);
        return this;
    }

    public ShapeBuilder joinOR(VoxelShape shape2) {
        return this.join(shape2, BooleanOp.OR);
    }

    public VoxelShape getVoxelShape() {
        return containerShape;
    }
}
