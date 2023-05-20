package com.lodestar.aileron.client.fabric;

import com.lodestar.aileron.client.AileronClient;
import net.fabricmc.api.ClientModInitializer;

public class AileronClientImpl implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AileronClient.init();
	}
}
