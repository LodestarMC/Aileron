package com.lodestar.aileron.forge;


import com.lodestar.aileron.Aileron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmokeStockLaunchPacketForge {

    public SmokeStockLaunchPacketForge() {

    }

    public SmokeStockLaunchPacketForge(FriendlyByteBuf buffer) {

    }

    public void encode(FriendlyByteBuf buffer) {

    }


    public static void handle(SmokeStockLaunchPacketForge msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Aileron.clientLaunchPlayer();
        });
        ctx.get().setPacketHandled(true);
    }
}
