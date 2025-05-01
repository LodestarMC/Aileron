package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronConfig;
import com.lodestar.aileron.AileronParticles;
import com.lodestar.aileron.accessor.AileronPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow public abstract boolean isFallFlying();

	@Unique
	private BlockState prevLeavesState = null;
	@Unique
	private BlockPos prevLeavesPos = null;

	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo info) {
		if (!this.isFallFlying()) {
			prevLeavesState = null;
			prevLeavesPos = null;
		}
	}

	@Redirect(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 6))
	private void modifyVelocity(LivingEntity instance, Vec3 vec3) {
		Vec3 negator = new Vec3(1.0 / 0.9900000095367432D, 1.0, 1.0 / 0.9900000095367432D);

		double cloudskipperDrag = instance instanceof Player ? ((AileronPlayer) (Player) instance).getCloudskipperDrag() : 1.0;

		double fac = 0;
		double y = instance.position().y;
		double cloudLevel = AileronConfig.cloudskipperCloudLevel();
		double bottom = cloudLevel - 92.0;
		double top = cloudLevel + 38.0;
		if (y < bottom)
			fac = 0.0f;
		else if (y < top)
			fac = 0.00006f * Math.pow(y - bottom, 2);
		else
			fac = 1.0f;

		fac *= (1.0 - cloudskipperDrag);
		double speedFac = fac * 0.6f * AileronConfig.cloudskipperSpeedMultiplier();

		if (speedFac > 0.1 && !level().isClientSide && tickCount % ((int) (1.0 - speedFac) * 2 + 1) == 0) {
			ServerLevel serverLevel = ((ServerLevel) level());

			for (ServerPlayer player : serverLevel.players()) {
				Vec3 pos = instance.position().add(instance.getLookAngle().scale(-1.0));
				if (fac >= instance.getRandom().nextDouble())
					serverLevel.sendParticles(player, AileronParticles.CLOUDSKIPPER_TRAIL.get(), false, pos.x, pos.y, pos.z, 1 + (int) (speedFac * 4.0), 0.1, 0.1, 0.1, 0.025);
			}
		}

		negator = new Vec3(negator.x, 1.0, negator.z);

		// lerp between vec3 and vec3 * negator based on fac
		vec3 = vec3.lerp(vec3.multiply(negator), fac);

		BlockState block = instance.getInBlockState();
		if (Aileron.isElytraFlightPassable(block) && Aileron.canGoThroughLeaves(instance)) {
			Vec3 stuck = new Vec3(0.8, 0.6, 0.8);
			vec3 = vec3.multiply(stuck);
			if (prevLeavesPos == null) {
				prevLeavesPos = instance.blockPosition();
				prevLeavesState = block;
			} else if (prevLeavesPos != instance.blockPosition()) {
				instance.level().playSound(null, instance.blockPosition(), block.getSoundType().getHitSound(), instance.getSoundSource(), 0.5f, 1.0f);
				if ((prevLeavesState.getBlock() instanceof LeavesBlock)) {
					if (!(Boolean)prevLeavesState.getValue(LeavesBlock.PERSISTENT)) {
						instance.level().destroyBlock(prevLeavesPos, true, instance);
					}
				}
				prevLeavesPos = instance.blockPosition();
				prevLeavesState = block;
			}
		}

		instance.setDeltaMovement(vec3);
	}
}
