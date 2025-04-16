package com.lodestar.aileron.forge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEntityData;
import com.lodestar.aileron.client.AileronClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Mod(Aileron.MOD_ID)
public class AileronImpl {
	public AileronImpl() {
		Aileron.init();
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> AileronClient::init);
	}

	public static boolean isElytra(ItemStack stack) {
		return stack.is(Items.ELYTRA) || stack.getItem() instanceof ElytraItem;
	}

	public static ItemStack getAccessoryElytra(LivingEntity entity) {
		if (Aileron.isModInstalled("curios")) {
			Optional<IItemHandlerModifiable> optional = CuriosApi.getCuriosInventory(entity).map(ICuriosItemHandler::getEquippedCurios);
			if (optional.isPresent()) {
				IItemHandlerModifiable handler = optional.get();
				for (int i = 0; i < handler.getSlots(); i++) {
					ItemStack stack = handler.getStackInSlot(i);
					if (Aileron.isElytra(stack) && (stack.getMaxDamage() - stack.getDamageValue() > 0)) {
						return stack;
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public static boolean canChargeSmokeStack(@Nullable Player player) {
		return player != null && Aileron.getElytra(player).canElytraFly(player) && ((player.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES) > 0 && player.isFallFlying()) || player.isCrouching());
	}

	public static boolean isModInstalled(String modId) {
		return ModList.get().isLoaded(modId);
	}

	public static final EnchantmentCategory ELYTRA_ENCHANTMENT_CATEGORY = EnchantmentCategory.create("elytra", item -> {
		return Aileron.isElytra(item.getDefaultInstance());
	});
	public static EnchantmentCategory getElytraEnchantmentCategory() {
		return ELYTRA_ENCHANTMENT_CATEGORY;
	}
}
