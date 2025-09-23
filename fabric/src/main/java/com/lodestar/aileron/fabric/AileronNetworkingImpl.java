package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronNetworking;
import com.lodestar.aileron.payloads.SmokestackDashPayload;
import com.lodestar.aileron.payloads.SmokestackLaunchPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class AileronNetworkingImpl {

    public static void sendSmokeStackLaunch(ServerPlayer player) {
        ServerPlayNetworking.send(player, new SmokestackLaunchPayload());
    }

    public static void register() {
        PayloadTypeRegistry.playS2C().register(SmokestackLaunchPayload.ID, SmokestackLaunchPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(SmokestackDashPayload.ID, SmokestackDashPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
                AileronNetworking.SMOKESTACK_DASH_PACKET_ID,
                (payload, context) ->
                        context.player().getServer().execute(() -> Aileron.playerDashedServer(context.player()))
        );
    }
}