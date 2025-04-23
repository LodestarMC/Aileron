package com.lodestar.aileron.client.neoforge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.particle.CustomCampfireParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import static com.lodestar.aileron.neoforge.AileronParticlesImpl.CUSTOM_CAMPFIRE_SMOKE;

@EventBusSubscriber(modid = Aileron.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AileronClientParticlesImpl {
	@SubscribeEvent
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(CUSTOM_CAMPFIRE_SMOKE.get(), CustomCampfireParticle.CustomCampfireParticleProvider::new);
	}

	public static void register() {
	}
}
