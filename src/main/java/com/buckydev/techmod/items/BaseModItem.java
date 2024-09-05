package com.buckydev.techmod.items;

import com.buckydev.techmod.ModKeys;
import com.buckydev.techmod.items.interfaces.IAppendHoverTooltip;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public abstract class BaseModItem extends Item {

    public BaseModItem(Properties properties) {
        super(properties);
    }

    /**
     * Ensure super is called last to enable the proper ordering of tooltips
     */
    @Override
    public void appendHoverText(
            ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (this instanceof IAppendHoverTooltip hoverText) {
            var keyHeld = ModKeys.isKeyDown(ModKeys.TOOLTIP_MAPPING.get());
            hoverText.getHoverToolTip(tooltipComponents, keyHeld);
            if (keyHeld) {
                hoverText.getHoverDetails(tooltipComponents);
            }
        }
    }
}
