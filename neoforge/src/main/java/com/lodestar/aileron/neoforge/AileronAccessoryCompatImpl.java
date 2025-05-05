package com.lodestar.aileron.neoforge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronAccessoryCompat;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class AileronAccessoryCompatImpl {

    public static void register() {
        if (AileronAccessoryCompat.isAccessoryModInstalled()) {
            NeoForge.EVENT_BUS.addListener(AileronAccessoryCompatImpl::curioChange);
            NeoForge.EVENT_BUS.addListener(AileronAccessoryCompatImpl::equipmentChange);
        }
    }

    public static void curioChange(CurioChangeEvent event) {
        AileronAccessoryCompat.accessoryElytraChange(event.getEntity(), event.getFrom(), true);
        AileronAccessoryCompat.accessoryElytraChange(event.getEntity(), event.getTo(), false);
    }

    public static void equipmentChange(LivingEquipmentChangeEvent event) {
        AileronAccessoryCompat.equipmentChange(event.getEntity(), event.getSlot(), event.getFrom(), event.getTo());
    }

    public static ItemStack getAccessoryElytra(LivingEntity entity) {
        if (AileronAccessoryCompat.isAccessoryModInstalled()) {
            Optional<IItemHandlerModifiable> optional = CuriosApi.getCuriosInventory(entity).map(ICuriosItemHandler::getEquippedCurios);
            if (optional.isPresent()) {
                IItemHandlerModifiable handler = optional.get();
                for (int i = 0; i < handler.getSlots(); i++) {
                    ItemStack stack = handler.getStackInSlot(i);
                    if (Aileron.isElytra(stack) && (stack.getMaxDamage() - stack.getDamageValue() > 0)) {
                        return stack;
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
