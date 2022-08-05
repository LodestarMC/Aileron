package com.lodestar.aileron.forge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.CustomCampfireParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lodestar.aileron.forge.AileronForgeParticles.CUSTOM_CAMPFIRE_SMOKE;

@Mod.EventBusSubscriber(modid = Aileron.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AileronForgeClientEBS {
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.register(CUSTOM_CAMPFIRE_SMOKE.get(), CustomCampfireParticle.CustomCampfireParticleProvider::new);
    }
}
