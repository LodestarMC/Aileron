package com.lodestar.aileron.client.neoforge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.client.AileronClientKeybinds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Aileron.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AileronClientKeybindsImpl {

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(AileronClientKeybinds.SMOKESTACK_BOOST);
    }

    public static void register() {
    }
}
