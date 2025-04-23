package com.lodestar.aileron.neoforge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.neoforge.packets.C2SSmokeStackDash;
import com.lodestar.aileron.neoforge.packets.S2CSmokeStackLaunch;
import com.lodestar.aileron.payloads.SmokestackDashPayload;
import com.lodestar.aileron.payloads.SmokestackLaunchPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Aileron.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AileronNetworkingImpl {
	private static final String PROTOCOL_VERSION = "1";

	public static void sendSmokeStackLaunch(ServerPlayer player) {
		PacketDistributor.sendToPlayer(player, new SmokestackLaunchPayload());
	}

	@SubscribeEvent
	public static void register(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
		registrar.playToServer(
				SmokestackDashPayload.ID,
				SmokestackDashPayload.CODEC,
				C2SSmokeStackDash::handle
		);
		registrar.playToClient(
				SmokestackLaunchPayload.ID,
				SmokestackLaunchPayload.CODEC,
				S2CSmokeStackLaunch::handle
		);
	}

	public static void register() {
	}
}
