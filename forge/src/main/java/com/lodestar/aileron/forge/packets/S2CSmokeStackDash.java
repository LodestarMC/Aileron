package com.lodestar.aileron.forge.packets;

import com.lodestar.aileron.Aileron;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSmokeStackDash {
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer sender = ctx.get().getSender();
			if (sender != null) Aileron.playerDashedServer(sender);
		});
		ctx.get().setPacketHandled(true);
	}
}
