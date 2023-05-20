package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AileronImpl implements ModInitializer {
    @Override
    public void onInitialize() {
        Aileron.init();
    }

    public static boolean wearingElytra(Player player) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (stack.is(Items.ELYTRA) && ElytraItem.isFlyEnabled(stack))
            return true;
        return EntityElytraEvents.ALLOW.invoker().allowElytraFlight(player);
    }

    public static boolean isElytra(ItemStack stack) {
        return stack.is(Items.ELYTRA) || stack.getItem() instanceof FabricElytraItem;
    }

    public static boolean isModInstalled(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
