package com.buckydev.techmod.menu.custom.exampleBE;

import com.buckydev.techmod.blocks.ModBlocks;
import com.buckydev.techmod.menu.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

// TODO: abstract definition
// Menu's are both client and serverside(fact check me) and define locations of slots
public class ExampleBEMenu extends AbstractContainerMenu {

    private final int INPUT_SLOT = 37;
    private final int OUTPUT_SLOT = 38;
    private final int INV_START = 9;
    private final int INV_END = 36;
    private final int HOTBAR_START = 0;
    private final int HOTBAR_END = 8;

    private final ContainerLevelAccess levelAccess;
    private final Inventory inventory;

    // Client - only these 2 args
    public ExampleBEMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL, new ItemStackHandler(2));
    }
    // Server - extra params here
    public ExampleBEMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler) {
        super(ModMenus.MY_MENU.get(), containerId);
        this.levelAccess = access;
        this.inventory = inventory;

        // TODO: dynamic values who/how?
        this.addPlayerHotbar(8, 142, 18); // slots: 0-8
        this.addPlayerInventory(8, 84, 18, 18); // slots: 9 - 36
        //        this.addSlotPad(); // bad idea most likely; can be misc data slots, like power or whatnot
        this.addSlot(new SlotItemHandler(itemHandler, 0, 80, 11)); // input: 37
        this.addSlot(new SlotItemHandler(itemHandler, 1, 80, 59)); // output: 38
    }

    // Copied from {AbstractFurnaceMenu}
    private void addPlayerHotbar(int startX, int startY, int stepX) {
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, startX + k * stepX, startY));
        }
    }

    // Copied from {AbstractFurnaceMenu}
    private void addPlayerInventory(int startX, int startY, int stepX, int stepY) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, startX + j * stepX, startY + i * stepY));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null; // FIXME: missing implementation, crashes
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.levelAccess, player, ModBlocks.EXAMPLE_BE.get());
    }
}
