package com.lodestar.aileron.fabric.payloads;

import com.lodestar.aileron.fabric.AileronNetworkingImpl;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record LaunchSmokeStackPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<LaunchSmokeStackPayload> ID = AileronNetworkingImpl.LAUNCH_SMOKE_STACK_PACKET_ID;
    public static final StreamCodec<RegistryFriendlyByteBuf, LaunchSmokeStackPayload> CODEC =
            StreamCodec.unit(new LaunchSmokeStackPayload());

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
