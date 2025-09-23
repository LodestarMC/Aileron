package com.lodestar.aileron.client.fabric;

import com.lodestar.aileron.AileronNetworking;
import com.lodestar.aileron.client.AileronClient;
import com.lodestar.aileron.payloads.SmokestackDashPayload;
import com.lodestar.aileron.payloads.SmokestackLaunchPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class AileronClientNetworkingImpl {
	public static void sendSmokeStackDash() {
		ClientPlayNetworking.send(new SmokestackDashPayload());
	}

	public static void register() {
		ClientPlayNetworking.registerGlobalReceiver(
                AileronNetworking.SMOKESTACK_LAUNCH_PACKET_ID,
                (payload, context) -> {
			        context.client().execute(AileronClient::launchPlayer);
		        }
        );
	}
}