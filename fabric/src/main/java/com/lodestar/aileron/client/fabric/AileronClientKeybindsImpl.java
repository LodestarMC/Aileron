package com.lodestar.aileron.client.fabric;

import com.lodestar.aileron.client.AileronClientKeybinds;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class AileronClientKeybindsImpl {
    public static void register() {
        KeyBindingHelper.registerKeyBinding(AileronClientKeybinds.SMOKESTACK_BOOST);
    }
}
