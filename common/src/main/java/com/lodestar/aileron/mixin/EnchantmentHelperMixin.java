package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
	@Inject(at = @At("RETURN"), method = "getAvailableEnchantmentResults")
	private static void getPossibleEntries(int i, ItemStack itemStack, Stream<Holder<Enchantment>> stream, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
		if (!Aileron.isElytra(itemStack)) {
			cir.getReturnValue().removeIf(ele -> ele != null && (ele.enchantment.is(AileronEnchantments.CLOUDSKIPPER) || ele.enchantment.is(AileronEnchantments.SMOKESTACK)));
		}
	}
}

