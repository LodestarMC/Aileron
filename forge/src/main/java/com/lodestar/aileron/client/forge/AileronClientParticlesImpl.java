package com.lodestar.aileron.client.forge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.particle.CustomCampfireParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lodestar.aileron.forge.AileronParticlesImpl.CUSTOM_CAMPFIRE_SMOKE;

@Mod.EventBusSubscriber(modid = Aileron.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AileronClientParticlesImpl {
	@SubscribeEvent
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(CUSTOM_CAMPFIRE_SMOKE.get(), CustomCampfireParticle.CustomCampfireParticleProvider::new);
	}

	public static void register() {
	}
}
