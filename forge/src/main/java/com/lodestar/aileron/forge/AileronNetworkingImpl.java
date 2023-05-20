package com.lodestar.aileron.forge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.forge.packets.S2CSmokeStackDash;
import com.lodestar.aileron.forge.packets.S2CSmokeStackLaunch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class AileronNetworkingImpl {
	private static final String PROTOCOL_VERSION = "1";

	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(Aileron.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static int packetID = 0;

	public static void sendSmokeStackLaunch(ServerPlayer player) {
		AileronNetworkingImpl.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new S2CSmokeStackLaunch());
	}

	public static void register() {
		CHANNEL.registerMessage(packetID++, S2CSmokeStackLaunch.class, (a, b) -> {}, fBBuf -> new S2CSmokeStackLaunch(), S2CSmokeStackLaunch::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(packetID++, S2CSmokeStackDash.class, (a, b) -> {}, fBBuf -> new S2CSmokeStackDash(), S2CSmokeStackDash::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
	}
}
