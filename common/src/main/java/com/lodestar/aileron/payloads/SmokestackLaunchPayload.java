package com.lodestar.aileron.payloads;

import com.lodestar.aileron.AileronNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record SmokestackLaunchPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SmokestackLaunchPayload> ID = AileronNetworking.SMOKESTACK_LAUNCH_PACKET_ID;
    public static final StreamCodec<RegistryFriendlyByteBuf, SmokestackLaunchPayload> CODEC =
            StreamCodec.unit(new SmokestackLaunchPayload());

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
