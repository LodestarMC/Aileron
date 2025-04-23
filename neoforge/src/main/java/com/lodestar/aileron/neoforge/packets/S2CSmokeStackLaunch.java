package com.lodestar.aileron.neoforge.packets;

import com.lodestar.aileron.client.AileronClient;
import com.lodestar.aileron.payloads.SmokestackLaunchPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class S2CSmokeStackLaunch {
	public static void handle(SmokestackLaunchPayload payload, final IPayloadContext context) {
		context.enqueueWork(AileronClient::launchPlayer);
	}
}
