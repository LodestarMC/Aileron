package com.lodestar.aileron.client.neoforge;

import com.lodestar.aileron.payloads.SmokestackDashPayload;
import net.neoforged.neoforge.network.PacketDistributor;

public class AileronClientNetworkingImpl {
	public static void sendSmokeStackDash() {
		PacketDistributor.sendToServer(new SmokestackDashPayload());
	}

	public static void register() {
	}
}
