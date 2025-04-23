package com.lodestar.aileron.client.fabric;

import com.lodestar.aileron.client.AileronClient;
import com.lodestar.aileron.fabric.AileronNetworkingImpl;
import com.lodestar.aileron.fabric.payloads.DashSmokeStackPayload;
import com.lodestar.aileron.fabric.payloads.LaunchSmokeStackPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class AileronClientNetworkingImpl {
	public static void sendSmokeStackDash() {
		ClientPlayNetworking.send(new DashSmokeStackPayload());
	}

	public static void register() {
		PayloadTypeRegistry.playS2C().register(AileronNetworkingImpl.LAUNCH_SMOKE_STACK_PACKET_ID, LaunchSmokeStackPayload.CODEC);
		ClientPlayNetworking.registerGlobalReceiver(AileronNetworkingImpl.LAUNCH_SMOKE_STACK_PACKET_ID, (payload, context) -> {
			context.client().execute(AileronClient::launchPlayer);
		});
	}
}