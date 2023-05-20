package com.lodestar.aileron.forge;

import com.lodestar.aileron.AileronConfigInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;
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

        configInfo.fireworkChanges = AileronForgeConfig.fireworkChanges;
        configInfo.cameraRoll = AileronForgeConfig.doCameraRoll;
        configInfo.cameraRollScale = AileronForgeConfig.cameraRollScale;
        configInfo.cameraRollSpeed = AileronForgeConfig.cameraRollSpeed;
        configInfo.campfiresPushPlayers = AileronForgeConfig.campfiresPushPlayers;
        configInfo.smokeStackChargeTicks = AileronForgeConfig.smokeStackChargeTicks;

        return configInfo;
    }

    public static boolean wearingElytra(Player player) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
        return stack.canElytraFly(player);
    }

    public static boolean isElytra(ItemStack stack) {
        return stack.is(Items.ELYTRA); // Sadge, but on forge there's no way to check if an item is an elytra.
    }

    public static boolean isModInstalled(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
