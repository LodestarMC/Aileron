package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class AileronParticlesImpl {
	public static final SimpleParticleType CUSTOM_CAMPFIRE_SMOKE = FabricParticleTypes.simple();

	public static void register() {
		Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Aileron.MOD_ID, "custom_campfire_smoke"), CUSTOM_CAMPFIRE_SMOKE);
	}
}
