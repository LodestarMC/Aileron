package com.lodestar.aileron.fabric;

import com.google.common.collect.Iterables;
import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEntityData;
import com.lodestar.aileron.fabric.loot.AileronLootModifiers;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;

public class AileronImpl implements ModInitializer {
	public static boolean isElytra(ItemStack stack) {
		return stack.is(Items.ELYTRA) || stack.getItem() instanceof FabricElytraItem;
	}

	public static ItemStack getAccessoryElytra(LivingEntity entity) {
		if (Aileron.isModInstalled("trinkets")) {
			var v = TrinketsApi.TRINKET_COMPONENT.maybeGet(entity);
			if (v.isPresent()) {
				TrinketComponent component = v.get();
				ArrayList<Tuple<SlotReference, ItemStack>> elytras = new ArrayList<>(component.getEquipped(
						(itemStack -> (Aileron.isElytra(itemStack) && (itemStack.getMaxDamage() - itemStack.getDamageValue() > 0)))
				));
				if (!elytras.isEmpty()) {
					return elytras.get(0).getB();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public static boolean isModInstalled(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	public static boolean canChargeSmokeStack(@Nullable Player player) {
		return player != null && ((isElytra(Aileron.getElytra(player)) && ElytraItem.isFlyEnabled(Aileron.getElytra(player))) || EntityElytraEvents.ALLOW.invoker().allowElytraFlight(player)) && ((player.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES) > 0 && player.isFallFlying()) || player.isCrouching());
	}

	public static EnchantmentCategory getElytraEnchantmentCategory() {
		return EnchantmentCategory.BREAKABLE;
	}

	@Override
	public void onInitialize() {
		Aileron.init();
		AileronLootModifiers.modifyLootTables();
	}
}
