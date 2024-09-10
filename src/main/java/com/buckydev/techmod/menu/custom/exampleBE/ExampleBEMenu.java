package com.buckydev.techmod.menu.custom.exampleBE;

import com.buckydev.techmod.blocks.ModBlocks;
import com.buckydev.techmod.menu.abc.AbstractModMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

// TODO: abstract definition
// Menu's are both client and serverside(fact check me) and define locations of slots
public class ExampleBEMenu extends AbstractModMenu {

    private final ContainerLevelAccess levelAccess;
    private final Inventory inventory;

    // Client - only these 2 args
    public ExampleBEMenu(MenuType<ExampleBEMenu> type, int containerId, Inventory inventory) {
        this(type, containerId, inventory, ContainerLevelAccess.NULL, new ItemStackHandler(2));
    }
    // Server - extra params here
    public ExampleBEMenu(
            MenuType<ExampleBEMenu> type,
            int containerId,
            Inventory inventory,
            ContainerLevelAccess access,
            IItemHandler itemHandler) {
        super(type, containerId);
        this.levelAccess = access;
        this.inventory = inventory;

        // TODO: dynamic values how?
        this.addPlayerHotbar(inventory, 8, 142, 18);
        this.addPlayerInventory(inventory, 8, 84, 18, 18);
        this.addSlot(new SlotItemHandler(itemHandler, 0, 80, 11));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 80, 59));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null; // FIXME: missing implementation, crashes
    }

    @Override
    // FIXME: abstract
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.levelAccess, player, ModBlocks.EXAMPLE_BE.get());
    }
}
