package com.lodestar.aileron;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

import java.util.List;

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

    public static void equipmentChange(LivingEntity entity, EquipmentSlot slot, ItemStack from, ItemStack to) {
        if (slot == EquipmentSlot.CHEST) {
            if (Aileron.isElytra(from)) {
                AileronAccessoryCompat.chestElytraChange(entity, true);
            }
            if (Aileron.isElytra(to)) {
                AileronAccessoryCompat.chestElytraChange(entity, false);
            }
        }
    }

    public static void accessoryElytraChange(LivingEntity entity, ItemStack accessoryElytra, boolean remove) {
        ItemStack chestElytra = Aileron.getChestElytra(entity);
        if (!chestElytra.isEmpty()) {
            AileronAccessoryCompat.elytraChangeItem(entity, chestElytra, EquipmentSlot.CHEST, !remove);
        }
        AileronAccessoryCompat.elytraChangeItem(entity, accessoryElytra, EquipmentSlot.BODY, remove);
    }

    public static void chestElytraChange(LivingEntity entity, boolean remove) {
        ItemStack accessoryElytra = getAccessoryElytra(entity);
        if (!accessoryElytra.isEmpty()) {
            AileronAccessoryCompat.elytraChangeItem(entity, accessoryElytra, EquipmentSlot.BODY, !remove);
        }
    }

    public static void elytraChangeItem(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, boolean remove) {
        if (Aileron.isElytra(itemStack)) {
            itemStack.getEnchantments().keySet().forEach(enchantment -> {
                List<EnchantmentAttributeEffect> attributeEffects = enchantment.value().effects().get(EnchantmentEffectComponents.ATTRIBUTES);
                if (attributeEffects != null) {
                    attributeEffects.forEach(attributeEffect -> {
                        elytraChangeItemForAttribute(entity, itemStack, slot, remove, enchantment, attributeEffect, AileronAttributes.CLOUDSKIPPER_DRAG);
                        elytraChangeItemForAttribute(entity, itemStack, slot, remove, enchantment, attributeEffect, AileronAttributes.SMOKESTACK_CAPACITY);
                    });
                }
            });
        }
    }

    private static void elytraChangeItemForAttribute(LivingEntity player, ItemStack itemStack, EquipmentSlot slot, boolean remove, Holder<Enchantment> enchantment, EnchantmentAttributeEffect attributeEffect, Holder<Attribute> forAttribute) {
        if (forAttribute.value() == attributeEffect.attribute().value()) {
            AttributeModifier modifier = attributeEffect.getModifier(itemStack.getEnchantments().getLevel(enchantment), slot);
            AttributeInstance attribute = player.getAttributes().getInstance(forAttribute);
            if (attribute != null) {
                if (remove) {
                    attribute.removeModifier(modifier);
                } else {
                    attribute.addOrUpdateTransientModifier(modifier);
                }
            }
        }
    }
}
