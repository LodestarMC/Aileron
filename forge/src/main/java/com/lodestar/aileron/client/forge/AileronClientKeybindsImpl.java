package com.lodestar.aileron.client.forge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.client.AileronClientKeybinds;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aileron.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AileronClientKeybindsImpl {

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(AileronClientKeybinds.SMOKESTACK_BOOST);
    }

    public static void register() {
    }
}
