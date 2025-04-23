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

@EventBusSubscriber(modid = Aileron.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AileronParticlesImpl {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
			DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Aileron.MOD_ID);

	public static final Supplier<SimpleParticleType> CUSTOM_CAMPFIRE_SMOKE = PARTICLE_TYPES.register("custom_campfire_smoke", () -> new SimpleParticleType(true));

	public static void register(IEventBus bus) {
		PARTICLE_TYPES.register(bus);
	}

	public static void register() {
		register(AileronImpl.modEventBus);
	}
}
