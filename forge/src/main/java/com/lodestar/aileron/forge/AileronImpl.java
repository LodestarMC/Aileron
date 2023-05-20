package com.lodestar.aileron.forge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;

public class AileronImpl {
    public static void launchClient(ServerPlayer player) {
        AileronForge.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new S2CSmokeStackLaunch());
    }

    public static void smokeDash() {
        AileronForge.CHANNEL.sendToServer(new S2CSmokeStackDash());
    }

    public static boolean wearingElytra(Player player) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
        return stack.canElytraFly(player);
    }

    public static boolean isElytra(ItemStack stack) {
        return stack.is(Items.ELYTRA) || stack.getItem() instanceof ElytraItem;
    }

    public static boolean isModInstalled(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
