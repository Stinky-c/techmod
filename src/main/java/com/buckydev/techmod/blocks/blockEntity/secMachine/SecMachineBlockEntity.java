package com.buckydev.techmod.blocks.blockEntity.secMachine;

import com.buckydev.techmod.blocks.abc.blockEntity.ModBlockEntity;
import com.buckydev.techmod.blocks.abc.interfaces.IBlockEntityServerTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SecMachineBlockEntity extends ModBlockEntity<SecMachineBlockEntity> implements IBlockEntityServerTickable {

    public SecMachineBlockEntity(BlockEntityType<SecMachineBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {}
}
