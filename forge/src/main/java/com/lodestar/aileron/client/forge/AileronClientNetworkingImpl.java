package com.lodestar.aileron.client.forge;

import com.lodestar.aileron.forge.AileronNetworkingImpl;
import com.lodestar.aileron.forge.packets.S2CSmokeStackDash;

public class AileronClientNetworkingImpl {
	public static void sendSmokeStackDash() {
		AileronNetworkingImpl.CHANNEL.sendToServer(new S2CSmokeStackDash());
	}

	public static void register() {
	}
}
