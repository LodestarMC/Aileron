package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class AileronNetworkingImpl {
	public static final ResourceLocation LAUNCH_SMOKE_STACK_PACKET_ID = new ResourceLocation(Aileron.MOD_ID, "launch_smoke_stack");
	public static final ResourceLocation SMOKE_STACK_DASH_PACKET_ID = new ResourceLocation(Aileron.MOD_ID, "dash_smoke_stack");

	public static void sendSmokeStackLaunch(ServerPlayer player) {
		ServerPlayNetworking.send(player, LAUNCH_SMOKE_STACK_PACKET_ID, PacketByteBufs.empty());
	}

	public static void register() {
		ServerPlayNetworking.registerGlobalReceiver(SMOKE_STACK_DASH_PACKET_ID, (server, player, handler, buf, responseSender) -> Aileron.playerDashedServer(player));
	}
}
