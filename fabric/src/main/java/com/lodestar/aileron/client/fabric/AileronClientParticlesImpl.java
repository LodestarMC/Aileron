package com.lodestar.aileron.client.fabric;

import com.lodestar.aileron.particle.CustomCampfireParticle;
import com.lodestar.aileron.fabric.AileronParticlesImpl;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class AileronClientParticlesImpl {
	public static void register() {
		ParticleFactoryRegistry.getInstance().register(AileronParticlesImpl.CUSTOM_CAMPFIRE_SMOKE, CustomCampfireParticle.CustomCampfireParticleProvider::new);
	}
}
