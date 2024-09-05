package com.buckydev.techmod.utils;

import com.buckydev.techmod.ModKeys;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class TooltipHelper {
    private static final String TOOLTIP_GENERIC = "tooltip.techmod.expand_details";
    public static final Function<Boolean, Component> TOOLTIP_COMP = (held) -> {
        var h = held ? ChatFormatting.DARK_GREEN : ChatFormatting.GREEN;
        return Component.translatable(
                TOOLTIP_GENERIC, Component.keybind(ModKeys.TOOLTIP_NAME).withStyle(h));
    };
    // Performance issues?
}
