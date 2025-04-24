package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronAccessoryCompat;
import dev.emi.trinkets.api.*;
import dev.emi.trinkets.api.event.TrinketEquipCallback;
import dev.emi.trinkets.api.event.TrinketUnequipCallback;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class AileronAccessoryCompatImpl {

    public static void register() {
        if (isAccessoryModInstalled()) {
            TrinketUnequipCallback.EVENT.register(
                (stack, slot, entity) -> {
                    AileronAccessoryCompat.accessoryChangeItem(entity, stack, true);
                }
            );
            TrinketEquipCallback.EVENT.register(
                    (stack, slot, entity) -> {
                        AileronAccessoryCompat.accessoryChangeItem(entity, stack, false);
                    }
            );
        }
    }

    public static boolean isAccessoryModInstalled() {
        return Aileron.isModInstalled("trinkets");
    }


    public static ItemStack getAccessoryElytra(LivingEntity entity) {
        if (isAccessoryModInstalled()) {
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
