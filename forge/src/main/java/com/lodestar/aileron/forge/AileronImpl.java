package com.lodestar.aileron.forge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEntityData;
import com.lodestar.aileron.client.AileronClient;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@Mod(Aileron.MOD_ID)
public class AileronImpl {
	public AileronImpl() {
		Aileron.init();
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> AileronClient::init);
	}

	public static boolean isElytra(ItemStack stack) {
		return stack.is(Items.ELYTRA) || stack.getItem() instanceof ElytraItem;
	}

	public static boolean canChargeSmokeStack(@Nullable Player player) {
		return player != null && player.getItemBySlot(EquipmentSlot.CHEST).canElytraFly(player) && ((player.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES) > 0 && player.isFallFlying()) || player.isCrouching());
	}

	public static boolean isModInstalled(String modId) {
		return ModList.get().isLoaded(modId);
	}
}
