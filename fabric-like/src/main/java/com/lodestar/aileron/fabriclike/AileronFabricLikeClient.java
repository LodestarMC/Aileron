package com.lodestar.aileron.fabriclike;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.CustomCampfireParticle;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.world.inventory.InventoryMenu.BLOCK_ATLAS;

public class AileronFabricLikeClient {
    public static void init() {
        ParticleFactoryRegistry.getInstance().register(AileronFabricLike.CUSTOM_CAMPFIRE_SMOKE, CustomCampfireParticle.CustomCampfireParticleProvider::new);


        ClientPlayNetworking.registerGlobalReceiver(AileronFabricLike.LAUNCH_SMOKE_STACK_PACKET_ID, (client, handler, buf, responseSender) -> {
            Aileron.clientLaunchPlayer();
        });
    }
}
