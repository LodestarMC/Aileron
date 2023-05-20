package com.lodestar.aileron.forge.packets;

import com.lodestar.aileron.client.AileronClient;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSmokeStackLaunch {
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(AileronClient::launchPlayer);
        ctx.get().setPacketHandled(true);
    }
}
