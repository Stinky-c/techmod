package com.buckydev.techmod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class ModKeys {
    public static final String KEY_CATEGORY = "key.categories.techmod.primary";

    public static final String TOOLTIP_NAME = "key.techmod.show_tooltip";
    public static final Lazy<KeyMapping> TOOLTIP_MAPPING = Lazy.of(() -> new KeyMapping(
            TOOLTIP_NAME, KeyConflictContext.GUI, InputConstants.Type.KEYSYM, InputConstants.KEY_LSHIFT, KEY_CATEGORY));

    public static boolean isKeyDown(KeyMapping keyMapping) {
        var key = keyMapping.getKey();
        int keyCode = key.getValue();
        if (keyCode != InputConstants.UNKNOWN.getValue()) {
            long winHandle = Minecraft.getInstance().getWindow().getWindow();
            if (key.getType() == InputConstants.Type.KEYSYM) {
                return InputConstants.isKeyDown(winHandle, keyCode);
            } else if (key.getType() == InputConstants.Type.MOUSE) {
                return GLFW.glfwGetMouseButton(winHandle, keyCode) == GLFW.GLFW_PRESS;
            } else {
                TechMod.LOGGER.warn("Unsupported 'InputConstants.Type' found");
                return false;
            }
        }

        return false;
    }

    public static void submitEventbus(IEventBus eventBus) {
        eventBus.addListener(ModKeys::registerBindings);
    }

    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(TOOLTIP_MAPPING.get());
    }
}
