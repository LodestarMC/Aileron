package com.lodestar.aileron.client.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.fabric.AileronNetworkingImpl;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class AileronClientNetworkingImpl {
	public static void sendSmokeStackDash() {
		ClientPlayNetworking.send(AileronNetworkingImpl.SMOKE_STACK_DASH_PACKET_ID, PacketByteBufs.empty());
	}

	public static void register() {
		ClientPlayNetworking.registerGlobalReceiver(AileronNetworkingImpl.LAUNCH_SMOKE_STACK_PACKET_ID, (client, handler, buf, responseSender) -> Aileron.clientLaunchPlayer());
	}
}
