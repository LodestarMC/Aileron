package com.lodestar.aileron.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class AileronClientKeybinds {

    public static final String KEYBIND_CATEGORY = "key.category.aileron";

    public final static KeyMapping SMOKESTACK_BOOST = new KeyMapping(
            "key.aileron.smokestack_boost",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            KEYBIND_CATEGORY
    );

    @ExpectPlatform
    public static void register() {
    }
}
