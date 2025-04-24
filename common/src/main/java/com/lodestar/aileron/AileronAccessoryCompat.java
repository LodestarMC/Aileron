package com.lodestar.aileron;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

public class AileronAccessoryCompat {

    @ExpectPlatform
    public static void register() {
    }

    @ExpectPlatform
    public static boolean isAccessoryModInstalled() {
        return false;
    }

    @ExpectPlatform
    public static ItemStack getAccessoryElytra(LivingEntity entity) {
        return ItemStack.EMPTY;
    }

    public static void accessoryChange(LivingEntity entity, ItemStack from, ItemStack to) {
        accessoryChangeItem(entity, from, true);
        accessoryChangeItem(entity, to, false);
    }

    public static void accessoryChangeItem(LivingEntity entity, ItemStack itemStack, boolean remove) {
        if (Aileron.isElytra(itemStack)) {
            itemStack.getEnchantments().keySet().forEach(enchantment -> {
                enchantment.value().effects().get(EnchantmentEffectComponents.ATTRIBUTES).forEach(attributeEffect -> {
                    accessoryChangeItemForAttribute(entity, itemStack, remove, enchantment, attributeEffect, AileronAttributes.CLOUDSKIPPER_DRAG);
                    accessoryChangeItemForAttribute(entity, itemStack, remove, enchantment, attributeEffect, AileronAttributes.SMOKESTACK_CAPACITY);
                });
            });
        }
    }

    private static void accessoryChangeItemForAttribute(LivingEntity player, ItemStack itemStack, boolean remove, Holder<Enchantment> enchantment, EnchantmentAttributeEffect attributeEffect, Holder<Attribute> forAttribute) {
        if (forAttribute.value() == attributeEffect.attribute().value()) {
            AttributeModifier modifier = attributeEffect.getModifier(itemStack.getEnchantments().getLevel(enchantment), EquipmentSlot.BODY);
            AttributeInstance attribute = player.getAttributes().getInstance(forAttribute);
            if (remove) {
                attribute.removeModifier(modifier);
            } else {
                attribute.addTransientModifier(modifier);
            }
        }
    }
}
