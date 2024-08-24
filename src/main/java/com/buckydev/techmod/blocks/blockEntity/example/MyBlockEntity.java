package com.buckydev.techmod.blocks.blockEntity.example;

import com.buckydev.techmod.blocks.ModBlockEntities;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// TODO: can this be moved into a class in the {BaseEntityBlock} subclass?
// block entity; the data holder
public class MyBlockEntity extends BlockEntity {

    public MyBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EX_BE.get(), pos, blockState);
    }

    @ParametersAreNonnullByDefault
    @Override
    protected void loadAdditional(CompoundTag tag, Provider registries) {
        super.loadAdditional(tag, registries);
    }
}
