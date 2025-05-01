package com.lodestar.aileron;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class AileronParticles {

	public static final Supplier<SimpleParticleType> CUSTOM_CAMPFIRE_SMOKE = registerSimpleParticle("custom_campfire_smoke");
	public static final Supplier<SimpleParticleType> CLOUDSKIPPER_TRAIL= registerSimpleParticle("cloudskipper_trail");

	@ExpectPlatform
	public static Supplier<SimpleParticleType> registerSimpleParticle(String name) {
		return null;
	}

	@ExpectPlatform
	public static void register() {
	}
}
