package com.lodestar.aileron.fabric;

import com.lodestar.aileron.AileronConfigInfo;
import com.lodestar.aileron.fabriclike.AileronFabricLike;
import com.lodestar.aileron.fabriclike.AileronFabricLikeConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import org.quiltmc.loader.api.QuiltLoader;

public class AileronImpl {
    public static void launchClient(ServerPlayer player) {
        ServerPlayNetworking.send((ServerPlayer) player, AileronFabricLike.LAUNCH_SMOKE_STACK_PACKET_ID, PacketByteBufs.empty());
    }

    public static void smokeDash() {
        ClientPlayNetworking.send(AileronFabricLike.SMOKE_STACK_DASH_PACKET_ID, PacketByteBufs.empty());
    }

    public static AileronConfigInfo getConfigInfo() {
        AileronConfigInfo configInfo = new AileronConfigInfo();

        configInfo.cameraRoll = AileronFabricLikeConfig.doCameraRoll;
        configInfo.cameraRollScale = AileronFabricLikeConfig.cameraRollScale;
        configInfo.cameraRollSpeed = AileronFabricLikeConfig.cameraRollSpeed;
        configInfo.campfiresPushPlayers = AileronFabricLikeConfig.campfiresPushPlayers;
        configInfo.smokeStackChargeTicks = AileronFabricLikeConfig.smokeStackChargeTicks;

        return configInfo;
    }


    public static boolean isModInstalled(String modId) {
        return QuiltLoader.isModLoaded(modId);
    }
}
