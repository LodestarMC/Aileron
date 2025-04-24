package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEntityData;
import com.lodestar.aileron.accessor.AileronPlayer;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class AileronImpl implements ModInitializer {

	public static boolean isModInstalled(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	public static boolean canChargeSmokeStack(@Nullable Player player) {
		ItemStack elytra = Aileron.getElytra(player);
		if (elytra.isEmpty()) {
			return false;
		}
		return player != null && ElytraItem.isFlyEnabled(Aileron.getElytra(player)) && ((((AileronPlayer)player).getSmokestackCharges() > 0 && player.isFallFlying()) || player.isCrouching());
	}

	@Override
	public void onInitialize() {
		Aileron.init();
	}
}
