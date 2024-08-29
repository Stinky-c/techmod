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

        // TODO: dynamic values how?
        this.addPlayerHotbar(inventory, 8, 142, 18);
        this.addPlayerInventory(inventory, 8, 84, 18, 18);
        this.addSlot(new SlotItemHandler(itemHandler, 0, 80, 11));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 80, 59));
    }

    // Copied from {AbstractFurnaceMenu}
    private void addPlayerHotbar(Inventory inventory, int startX, int startY, int stepX) {
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, startX + k * stepX, startY));
        }
    }

    // Copied from {AbstractFurnaceMenu}
    private void addPlayerInventory(Inventory inventory, int startX, int startY, int stepX, int stepY) {
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
