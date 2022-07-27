package com.lodestar.aileron.forge;

import com.lodestar.aileron.AileronConfigInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class AileronImpl {
    public static void launchClient(ServerPlayer player) {
        AileronForge.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SmokeStockLaunchPacketForge());
    }

    public static void smokeDash() {
        AileronForge.CHANNEL.sendToServer(new SmokeStockDashPacketForge());
    }

    public static AileronConfigInfo getConfigInfo() {
        AileronConfigInfo configInfo = new AileronConfigInfo();

        configInfo.cameraRoll = AileronForgeConfig.DO_CAMERA_ROLL.get();
        configInfo.cameraRollScale = AileronForgeConfig.CAMERA_ROLL_SCALE.get();
        configInfo.cameraRollSpeed = AileronForgeConfig.CAMERA_ROLL_SPEED.get();
        configInfo.campfiresPushPlayers = AileronForgeConfig.PUSH_PLAYERS.get();
        configInfo.smokeStackChargeTicks = AileronForgeConfig.SMOKE_STACK_CHARGE_TICKS.get();

        return configInfo;
    }
}
