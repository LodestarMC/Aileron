package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEntityData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class AileronImpl implements ModInitializer {
	public static boolean isElytra(ItemStack stack) {
		return stack.is(Items.ELYTRA) || stack.getItem() instanceof FabricElytraItem;
	}

	public static boolean isModInstalled(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	public static boolean canChargeSmokeStack(@Nullable Player player) {
		return player != null && ((isElytra(player.getItemBySlot(EquipmentSlot.CHEST)) && ElytraItem.isFlyEnabled(player.getItemBySlot(EquipmentSlot.CHEST))) || EntityElytraEvents.ALLOW.invoker().allowElytraFlight(player)) && ((player.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES) > 0 && player.isFallFlying()) || player.isCrouching());
	}

	@Override
	public void onInitialize() {
		Aileron.init();
	}
}
