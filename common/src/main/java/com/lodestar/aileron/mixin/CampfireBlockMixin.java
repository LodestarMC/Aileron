package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.accessor.AileronPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.level.block.CampfireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

	@Redirect(method = "entityInside", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
	public boolean hurt(Entity instance, DamageSource damageSource, float f) {
		if ((instance.isCrouching() && ((instance instanceof Player && Aileron.isElytra(((Player) instance).getItemBySlot(EquipmentSlot.CHEST)))) || (instance instanceof Player && (((AileronPlayer) instance).getCampfireDamageIFrames() > 0))))
			return false;
		else
			return instance.hurt(damageSource, f);
	}

}
