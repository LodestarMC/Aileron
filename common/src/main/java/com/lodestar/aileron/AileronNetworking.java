package com.lodestar.aileron;

import com.lodestar.aileron.payloads.SmokestackDashPayload;
import com.lodestar.aileron.payloads.SmokestackLaunchPayload;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class AileronNetworking {

	public static final CustomPacketPayload.Type<SmokestackLaunchPayload> SMOKESTACK_LAUNCH_PACKET_ID =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, "smokestack_launch"));
	public static final CustomPacketPayload.Type<SmokestackDashPayload> SMOKESTACK_DASH_PACKET_ID =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, "smokestack_dash"));

	@ExpectPlatform
	public static void sendSmokeStackLaunch(ServerPlayer player) {
	}

	@ExpectPlatform
	public static void register() {
	}
}
