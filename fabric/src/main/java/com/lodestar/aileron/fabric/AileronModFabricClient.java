package com.lodestar.aileron.fabric;

import com.lodestar.aileron.CustomCampfireParticle;
import com.lodestar.aileron.fabriclike.AileronFabricLike;
import com.lodestar.aileron.fabriclike.AileronFabricLikeClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class AileronModFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AileronFabricLikeClient.init();


    }
}
