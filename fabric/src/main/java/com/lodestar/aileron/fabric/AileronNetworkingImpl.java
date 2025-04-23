package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.fabric.payloads.DashSmokeStackPayload;
import com.lodestar.aileron.fabric.payloads.LaunchSmokeStackPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class AileronNetworkingImpl {
	public static final CustomPacketPayload.Type<LaunchSmokeStackPayload> LAUNCH_SMOKE_STACK_PACKET_ID =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, "launch_smoke_stack"));
	public static final CustomPacketPayload.Type<DashSmokeStackPayload> SMOKESTACK_DASH_PACKET_ID =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, "dash_smoke_stack"));

	public static void sendSmokeStackLaunch(ServerPlayer player) {
		ServerPlayNetworking.send(player, new LaunchSmokeStackPayload());
	}

	public static void register() {
		PayloadTypeRegistry.playC2S().register(SMOKESTACK_DASH_PACKET_ID, DashSmokeStackPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(SMOKESTACK_DASH_PACKET_ID, (payload, context) -> {
			context.player().getServer().execute(() -> Aileron.playerDashedServer(context.player()));
		});
	}
}