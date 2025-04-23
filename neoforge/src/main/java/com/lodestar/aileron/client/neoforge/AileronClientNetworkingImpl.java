package com.lodestar.aileron.client.neoforge;

import com.lodestar.aileron.neoforge.AileronNetworkingImpl;
import com.lodestar.aileron.neoforge.packets.S2CSmokeStackDash;

public class AileronClientNetworkingImpl {
	public static void sendSmokeStackDash() {
		AileronNetworkingImpl.CHANNEL.sendToServer(new S2CSmokeStackDash());
	}

	public static void register() {
	}
}
