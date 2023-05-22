package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@ModifyArg(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 6))
	private Vec3 modifyVelocity(Vec3 vec3) {
		LivingEntity self = (LivingEntity) (Object) this;

		Vec3 negator = new Vec3(1.0 / 0.9900000095367432D, 1.0, 1.0 / 0.9900000095367432D);

		int cloudSkipper = self instanceof Player ? EnchantmentHelper.getItemEnchantmentLevel(Registry.ENCHANTMENT.get(new ResourceLocation(Aileron.MOD_ID, "cloudskipper")), ((Player) self).getInventory().getArmor(2)) : 0;

		double fac;
		double y = self.position().y;
		if (y < 100)
			fac = 0.0;
		else if (y < 230)
			fac = 0.00006 * Math.pow(y - 100, 2);
		else
			fac = 1;

		fac *= 0.6;
		fac *= cloudSkipper / 3.0;

		if (fac > 0.1 && !self.level.isClientSide && self.tickCount % ((int) (1.0 - fac) * 2 + 1) == 0) {
			ServerLevel serverLevel = ((ServerLevel) self.level);

			for (ServerPlayer player : serverLevel.players()) {
				Vec3 pos = self.position().add(self.getLookAngle().scale(-1.0));
				serverLevel.sendParticles(player, ParticleTypes.POOF, false, pos.x, pos.y, pos.z, 1 + (int) (fac * 4.0), 0.1, 0.1, 0.1, 0.025);
			}
		}

		negator = new Vec3(negator.x, 1.0, negator.z);

		// lerp between vec3 and vec3 * negator based on fac
		vec3 = vec3.lerp(vec3.multiply(negator), fac);

		return vec3;
	}
}
