package com.lodestar.aileron.forge;

import com.lodestar.aileron.Aileron;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Aileron.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AileronForgeParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Aileron.MOD_ID);

    public static final RegistryObject<SimpleParticleType> CUSTOM_CAMPFIRE_SMOKE = PARTICLE_TYPES.register("custom_campfire_smoke", () -> new SimpleParticleType(true));

    public static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }

}
