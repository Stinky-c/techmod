package com.buckydev.techmod.items.interfaces;

import com.buckydev.techmod.utils.TooltipHelper;
import java.util.List;
import net.minecraft.network.chat.Component;

public interface IAppendHoverTooltip {

    /**
     * Adds a tooltip saying to hold key for details
     * @param componentList Existing tooltip components, add new components here
     */
    default void getHoverToolTip(List<Component> componentList, boolean keyHeld) {
        componentList.add(TooltipHelper.TOOLTIP_COMP.apply(keyHeld));
    }

    /**
     * Adds a tooltip while details key is held
     * @param componentList Existing tooltip components, add new components here
     */
    void getHoverDetails(List<Component> componentList);
}
