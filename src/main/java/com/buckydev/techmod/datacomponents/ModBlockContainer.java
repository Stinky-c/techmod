package com.buckydev.techmod.datacomponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

// see {ItemContainerContents} for extra
public record ModBlockContainer(NonNullList<ItemStack> stacks) {

    public static final ModBlockContainer EMPTY = new ModBlockContainer(NonNullList.create());

    public static final Codec<ModBlockContainer> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                    NonNullList.codecOf(ItemStack.CODEC).fieldOf("stacks").forGetter(ModBlockContainer::stacks))
            .apply(inst, ModBlockContainer::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ModBlockContainer> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC.apply(codec -> ByteBufCodecs.collection(NonNullList::createWithCapacity, codec)),
            ModBlockContainer::stacks,
            ModBlockContainer::new);

    private void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= getSlots()) {
            throw new UnsupportedOperationException("Slot " + slot + " not in valid range - [0," + getSlots() + ")");
        }
    }

    public int getSlots() {
        return stacks.size();
    }

    public ItemStack getStackInSlot(int index) {
        validateSlotIndex(index);
        return stacks.get(index).copy();
    }
}
