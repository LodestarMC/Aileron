package com.lodestar.aileron.enchantment;

import com.lodestar.aileron.Aileron;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CloudSkipperEnchantment extends Enchantment {
    public CloudSkipperEnchantment(Rarity rarity) {
        super(rarity, EnchantmentCategory.BREAKABLE, new EquipmentSlot[0]);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return Aileron.isElytra(itemStack);
    }

    @Override
    public int getMinCost(int i) {
        return 5 + (i - 1) * 8;
    }

    @Override
    public int getMaxCost(int i) {
        return super.getMinCost(i) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && !(enchantment instanceof SmokeStackEnchantment);
    }
}
