package com.lodestar.aileron.neoforge;

import com.lodestar.aileron.Aileron;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AileronParticlesImpl {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
			DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Aileron.MOD_ID);

	public static Supplier<SimpleParticleType> registerSimpleParticle(String name) {
		return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(true));
	}

	public static void register(IEventBus bus) {
		PARTICLE_TYPES.register(bus);
	}

	public static void register() {
		register(AileronImpl.modEventBus);
	}
}
