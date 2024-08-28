package com.buckydev.techmod.menu.custom.exampleBE;

import com.buckydev.techmod.TechMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

// Screens are graphical, and only need to be registered client side.
public class ExampleBEScreen extends AbstractContainerScreen<ExampleBEMenu> {
    private static final ResourceLocation BACKGROUND_LOCATION =
            ResourceLocation.fromNamespaceAndPath(TechMod.MODID, "textures/gui/example_be.png");

    public ExampleBEScreen(ExampleBEMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // https://docs.neoforged.net/docs/gui/screens#rendering-the-container-screen
        guiGraphics.blit(BACKGROUND_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick); // Super adds most things
        this.renderTooltip(graphics, mouseX, mouseY); // Mouse hover tooltip
    }
}
