package com.lodestar.aileron.forge;


import com.lodestar.aileron.Aileron;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSmokeStackLaunch {
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(Aileron::clientLaunchPlayer);
        ctx.get().setPacketHandled(true);
    }
}
