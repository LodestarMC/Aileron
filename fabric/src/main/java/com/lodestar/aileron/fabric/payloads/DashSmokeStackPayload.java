package com.lodestar.aileron.fabric.payloads;

import com.lodestar.aileron.fabric.AileronNetworkingImpl;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record DashSmokeStackPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DashSmokeStackPayload> ID = AileronNetworkingImpl.SMOKESTACK_DASH_PACKET_ID;
    public static final StreamCodec<RegistryFriendlyByteBuf, DashSmokeStackPayload> CODEC =
            StreamCodec.unit(new DashSmokeStackPayload());

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}