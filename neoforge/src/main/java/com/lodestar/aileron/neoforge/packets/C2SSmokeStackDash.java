package com.lodestar.aileron.neoforge.packets;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.payloads.SmokestackDashPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class C2SSmokeStackDash {
	public static void handle(SmokestackDashPayload payload, final IPayloadContext context) {
		context.enqueueWork(() -> {
			if (context.player() instanceof ServerPlayer serverPlayer) {
				Aileron.playerDashedServer(serverPlayer);
			}
		});
	}
}
