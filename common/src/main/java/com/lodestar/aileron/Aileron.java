package com.lodestar.aileron;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Aileron {
    public static final String MOD_ID = "aileron";

    public static void init() {

    }

    @ExpectPlatform
    public static void launchClient(ServerPlayer player) {}

    @ExpectPlatform
    public static void smokeDash() {}

    @ExpectPlatform
    public static AileronConfigInfo getConfigInfo() {
        return null;
    }

    @ExpectPlatform
    public static boolean isModInstalled(String modId) {
        return false;
    }

    @ExpectPlatform
    public static boolean wearingElytra(Player player) {
        return false;
    }

    @ExpectPlatform
    public static boolean isElytra(ItemStack stack) {
        return false;
    }

    public static void clientLaunchPlayer() {
        LocalPlayer player = Minecraft.getInstance().player;
        ((ISmokeStackChargeData) player).setBoostTicks(50);
    }

    public static void playerDashedServer(ServerPlayer player) {
        ServerLevel serverLevel = (ServerLevel) player.level;
        int stocks = player.getEntityData().get(SmokeStocks.DATA_SMOKE_STOCKS);

        if(stocks > 0) {
            player.getEntityData().set(SmokeStocks.DATA_SMOKE_STOCKS, stocks - 1);

            for (ServerPlayer serverPlayer : serverLevel.players()) {
                serverLevel.sendParticles(serverPlayer, ParticleTypes.LARGE_SMOKE, false, player.getX(), player.getY(), player.getZ(), 40, 0.5, 0.5, 0.5, 0.1);
                serverLevel.sendParticles(serverPlayer, ParticleTypes.CAMPFIRE_COSY_SMOKE, false, player.getX(), player.getY(), player.getZ(), 40, 0.5, 0.5, 0.5, 0.1);
                serverLevel.sendParticles(serverPlayer, ParticleTypes.SMOKE, false, player.getX(), player.getY(), player.getZ(), 120, 0.5, 0.5, 0.5, 0.4);
            }

            ((ISmokeStackChargeData) player).setBoostTicks(50);
        }
    }

}
