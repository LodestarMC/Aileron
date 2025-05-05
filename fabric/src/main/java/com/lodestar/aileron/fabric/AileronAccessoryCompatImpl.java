package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronAccessoryCompat;
import dev.emi.trinkets.api.*;
import dev.emi.trinkets.api.event.TrinketEquipCallback;
import dev.emi.trinkets.api.event.TrinketUnequipCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class AileronAccessoryCompatImpl {

    public static void register() {
        if (AileronAccessoryCompat.isAccessoryModInstalled()) {
            TrinketUnequipCallback.EVENT.register(
                (stack, slot, entity) -> {
                    AileronAccessoryCompat.accessoryElytraChange(entity, stack, true);
                }
            );
            TrinketEquipCallback.EVENT.register(
                (stack, slot, entity) -> {
                    AileronAccessoryCompat.accessoryElytraChange(entity, stack, false);
                }
            );
            ServerEntityEvents.EQUIPMENT_CHANGE.register(
                (entity, slot, from, to) -> {
                    if (slot == EquipmentSlot.CHEST) {
                        AileronAccessoryCompat.equipmentChange(entity, slot, from, to);
                    }
                }
            );
        }
    }

    public static ItemStack getAccessoryElytra(LivingEntity entity) {
        if (AileronAccessoryCompat.isAccessoryModInstalled()) {
            var v = TrinketsApi.TRINKET_COMPONENT.maybeGet(entity);
            if (v.isPresent()) {
                TrinketComponent component = v.get();
                ArrayList<Tuple<SlotReference, ItemStack>> elytras = new ArrayList<>(component.getEquipped(
                        (itemStack -> (Aileron.isElytra(itemStack) && (itemStack.getMaxDamage() - itemStack.getDamageValue() > 0)))
                ));
                if (!elytras.isEmpty()) {
                    return elytras.getFirst().getB();
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
