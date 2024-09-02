package com.buckydev.techmod.blocks.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockEntityServerTickable {
    void serverTick(Level level, BlockPos pos, BlockState state);
}
