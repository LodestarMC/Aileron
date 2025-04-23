package com.lodestar.aileron;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import java.rmi.registry.Registry;

public class AileronEnchantments {

	public static final ResourceKey<Enchantment> CLOUDSKIPPER = create("cloudskipper");
	public static final ResourceKey<Enchantment>  SMOKESTACK = create("smokestack");

	private static ResourceKey<Enchantment> create(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, name));
	}
}
