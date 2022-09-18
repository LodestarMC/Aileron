package com.lodestar.aileron.fabriclike;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEnchantments;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class AileronFabricLike {
    public static final ResourceLocation LAUNCH_SMOKE_STACK_PACKET_ID = new ResourceLocation(Aileron.MOD_ID, "launch_smoke_stack");
    public static final ResourceLocation SMOKE_STACK_DASH_PACKET_ID = new ResourceLocation(Aileron.MOD_ID, "dash_smoke_stack");

    public static final SimpleParticleType CUSTOM_CAMPFIRE_SMOKE = FabricParticleTypes.simple();

    public static void init() {
        Aileron.init();
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Aileron.MOD_ID, "custom_campfire_smoke"), CUSTOM_CAMPFIRE_SMOKE);
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(Aileron.MOD_ID, "smokestack"), AileronEnchantments.SMOKESTACK);
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(Aileron.MOD_ID, "cloudskipper"), AileronEnchantments.CLOUDSKIPPER);

        ServerPlayNetworking.registerGlobalReceiver(SMOKE_STACK_DASH_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            Aileron.playerDashedServer(player);
        });

        MidnightConfig.init(Aileron.MOD_ID, AileronFabricLikeConfig.class);
    }
}
