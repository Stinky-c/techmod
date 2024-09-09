package com.buckydev.techmod.blocks.abc.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ModBlockEntity<T extends ModBlockEntity<T>> extends BlockEntity {

    protected static final String TAG_INVENTORY_KEY = "Inventory";
    protected static final int TAG_INVENTORY_TYPE = CompoundTag.TAG_COMPOUND;
    //    protected Lazy<RecipeManager> recipeManager = Lazy.of(() -> getLevel().getRecipeManager());
    protected RecipeManager recipeManager;

    public ModBlockEntity(BlockEntityType<T> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.recipeManager = getLevel().getRecipeManager();
    }
}
