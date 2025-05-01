package com.lodestar.aileron.client.fabric;

import com.lodestar.aileron.AileronParticles;
import com.lodestar.aileron.particle.CloudskipperTrailParticle;
import com.lodestar.aileron.particle.CustomCampfireParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class AileronClientParticlesImpl {
	public static void register() {
		ParticleFactoryRegistry.getInstance().register(AileronParticles.CUSTOM_CAMPFIRE_SMOKE.get(), CustomCampfireParticle.CustomCampfireParticleProvider::new);
		ParticleFactoryRegistry.getInstance().register(AileronParticles.CLOUDSKIPPER_TRAIL.get(), CloudskipperTrailParticle.CloudskipperTrailParticleProvider::new);
	}
}
