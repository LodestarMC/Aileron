package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class AileronParticlesImpl {
	public static Supplier<SimpleParticleType> registerSimpleParticle(String name) {
		SimpleParticleType particleType = FabricParticleTypes.simple();
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, name), particleType);
		return () -> particleType;
	}

	public static void register() {
	}
}
