package com.lodestar.aileron.forge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.CloudSkipperEnchantment;
import com.lodestar.aileron.SmokeStackEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AileronForgeEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Aileron.MOD_ID);

    public static final RegistryObject<Enchantment> SMOKESTACK = ENCHANTMENTS.register("smokestack",
            () -> new SmokeStackEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[] { EquipmentSlot.CHEST }));

    public static final RegistryObject<Enchantment> CLOUDSKIPPER = ENCHANTMENTS.register("cloudskipper",
            () -> new CloudSkipperEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[] { EquipmentSlot.CHEST }));


    public static void register(IEventBus bus) {
        ENCHANTMENTS.register(bus);
    }
}
