package com.lodestar.aileron.forge;


import com.lodestar.aileron.Aileron;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmokeStockDashPacketForge {

    public SmokeStockDashPacketForge() {

    }

    public SmokeStockDashPacketForge(FriendlyByteBuf buffer) {

    }

    public void encode(FriendlyByteBuf buffer) {

    }


    public static void handle(SmokeStockDashPacketForge msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Aileron.playerDashedServer(ctx.get().getSender());
        });
        ctx.get().setPacketHandled(true);
    }
}
