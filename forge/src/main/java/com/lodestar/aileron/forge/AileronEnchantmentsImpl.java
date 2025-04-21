package com.lodestar.aileron.forge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.enchantment.CloudSkipperEnchantment;
import com.lodestar.aileron.enchantment.SmokeStackEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AileronEnchantmentsImpl {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS =
			DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Aileron.MOD_ID);

	public static final RegistryObject<Enchantment> SMOKESTACK = ENCHANTMENTS.register("smokestack",
            SmokeStackEnchantment::new);

	public static final RegistryObject<Enchantment> CLOUDSKIPPER = ENCHANTMENTS.register("cloudskipper",
            CloudSkipperEnchantment::new);

	public static void register(IEventBus bus) {
		ENCHANTMENTS.register(bus);
	}

	public static void register() {
		register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
