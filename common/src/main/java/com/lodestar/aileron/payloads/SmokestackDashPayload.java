package com.lodestar.aileron.payloads;

import com.lodestar.aileron.AileronNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record SmokestackDashPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SmokestackDashPayload> ID = AileronNetworking.SMOKESTACK_DASH_PACKET_ID;
    public static final StreamCodec<RegistryFriendlyByteBuf, SmokestackDashPayload> CODEC =
            StreamCodec.unit(new SmokestackDashPayload());

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}