package com.buckydev.techmod.menu.abc;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractModMenu extends AbstractContainerMenu {

    protected AbstractModMenu(MenuType<? extends AbstractModMenu> menuType, int containerId) {
        super(menuType, containerId);
    }

    // Copied from {AbstractFurnaceMenu}
    protected void addPlayerHotbar(Inventory inventory, int startX, int startY, int stepX) {
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, startX + k * stepX, startY));
        }
    }

    // Copied from {AbstractFurnaceMenu}
    protected void addPlayerInventory(Inventory inventory, int startX, int startY, int stepX, int stepY) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, startX + j * stepX, startY + i * stepY));
            }
        }
    }
}
