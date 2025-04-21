package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.enchantment.CloudSkipperEnchantment;
import com.lodestar.aileron.enchantment.SmokeStackEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class AileronEnchantmentsImpl {
	public static Enchantment SMOKESTACK = new SmokeStackEnchantment();
	public static Enchantment CLOUDSKIPPER = new CloudSkipperEnchantment();

	public static void register() {
		Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(Aileron.MOD_ID, "smokestack"), SMOKESTACK);
		Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(Aileron.MOD_ID, "cloudskipper"), CLOUDSKIPPER);
	}
}
