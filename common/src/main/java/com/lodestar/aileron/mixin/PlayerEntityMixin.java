package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronConfig;
import com.lodestar.aileron.AileronEntityData;
import com.lodestar.aileron.AileronNetworking;
import com.lodestar.aileron.accessor.AileronPlayer;
import com.lodestar.aileron.client.AileronClient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin implements AileronPlayer {

	int campfireDamageIFrames = 0;
	int smokeTrailTicks = 0;
	int chargeTime = 0;
	int airChargeTime = 0;
	boolean charged = false;
	int startFlyingTimer = 0;
	int boostTicks = 0;

	@Shadow
	public abstract boolean isLocalPlayer();

	@Inject(method = "tick", at = @At("TAIL"))
	public void postTick(CallbackInfo ci) {
		Player self = ((Player) (Object) this);
		Level level = self.level();

		if (boostTicks > 0) boostTicks--;
		if (!self.isFallFlying()) boostTicks = 0;

		if (campfireDamageIFrames > 0) campfireDamageIFrames--;
		if (startFlyingTimer > 0) startFlyingTimer--;

		if (smokeTrailTicks > 0) smokeTrailTicks--;
		if (!self.isFallFlying()) smokeTrailTicks = 0;


		// boosting
		if (boostTicks > 0) {

			if (level.isClientSide && isLocalPlayer()) {
				Vec3 vec31 = self.getLookAngle();
				Vec3 vec32 = self.getDeltaMovement();
				self.setDeltaMovement(vec32.add(vec31.x * 0.1D + (vec31.x * 1.5D - vec32.x) * 0.5D, vec31.y * 0.1D + (vec31.y * 1.5D - vec32.y) * 0.5D, vec31.z * 0.1D + (vec31.z * 1.5D - vec32.z) * 0.5D));
			} else {
				if (self.tickCount % 3 == 0) {

					final ServerLevel serverLevel = ((ServerLevel) level);

					for (ServerPlayer player : serverLevel.players()) {
						serverLevel.sendParticles(player, ParticleTypes.FLAME, false, self.getX(), self.getY(), self.getZ(), 2, 0.2, 0.2, 0.2, 0.1);
						serverLevel.sendParticles(player, ParticleTypes.LARGE_SMOKE, false, self.getX(), self.getY(), self.getZ(), 3, 0.2, 0.2, 0.2, 0.1);
						serverLevel.sendParticles(player, ParticleTypes.CAMPFIRE_COSY_SMOKE, false, self.getX(), self.getY(), self.getZ(), 1, 0.2, 0.2, 0.2, 0.0);
					}


				}
			}

		}

		// smoke trail
		if (smokeTrailTicks > 0) {

			if (self.tickCount % 3 == 0) {
				final ServerLevel serverLevel = ((ServerLevel) level);

				for (ServerPlayer player : serverLevel.players()) {
					Vec3 pos = self.position().add(self.getLookAngle().scale(-1.0));
					serverLevel.sendParticles(player, ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, false, pos.x, pos.y, pos.z, 2, 0.1, 0.1, 0.1, 0.005);
				}
			}

		}

		BlockState underBlockState = level.getBlockState(self.blockPosition());
		if (self.isCrouching() && underBlockState.is(BlockTags.CAMPFIRES) && Aileron.canChargeSmokeStack(self)) {

			if (level.isClientSide) {
				boolean isSoul = underBlockState.is(Blocks.SOUL_CAMPFIRE);
				SimpleParticleType[] particles = {isSoul ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, ParticleTypes.SMOKE};

				for (SimpleParticleType particle : particles) {
					Vec3 randomOffset = new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
					randomOffset = randomOffset.normalize().scale(2.0);
					Vec3 motion = randomOffset.scale(-0.075);
					Vec3 position = self.position().add(0.0, self.getEyeHeight() / 2.0, 0.0).add(randomOffset);
					level.addParticle(particle, position.x, position.y, position.z, motion.x, motion.y, motion.z);
				}
			}
			else {
				final ServerLevel serverLevel = ((ServerLevel) level);
				chargeTime++;

				if (chargeTime % AileronConfig.smokestackChargeTicks() == 0 && chargeTime > 0) {
					smokeCharge(true);
				}
			}
		}
		else {
			chargeTime = 0;

			if (!level.isClientSide && !self.isFallFlying() && campfireDamageIFrames == 0 && !charged) {
				self.getEntityData().set(AileronEntityData.SMOKE_STACK_CHARGES, 0);
			}
		}

		if (startFlyingTimer == 0 && !level.isClientSide) {
			startFlyingTimer = -1;
			self.startFallFlying();
		}

		if (!self.isCrouching() && charged) {
			final ServerLevel serverLevel = ((ServerLevel) level);

			charged = false;
			chargeTime = 0;

			Aileron.sendBoostParticles(serverLevel, self.getX(), self.getY(), self.getZ());

			setCampfireDamageIFrames(20);

			level.playSound(null, self.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8f, 0.8f);

			self.startFallFlying();
			AileronNetworking.sendSmokeStackLaunch((ServerPlayer) self);
			self.tryToStartFallFlying();
			startFlyingTimer = 5;
		}

		if (self.isFallFlying()) {
			int maxRange = 38;
			int depth = 0;

			BlockPos blockPosition = self.blockPosition();

			while (depth < maxRange && level.isEmptyBlock(blockPosition) && level.isInWorldBounds(blockPosition)) {
				depth++;
				blockPosition = blockPosition.below();
			}

			BlockState blockState = level.getBlockState(blockPosition);
			if (blockState.is(BlockTags.CAMPFIRES)) {
				// player is over a campfire
				// determine range of campfire
				boolean isLit = blockState.getValue(CampfireBlock.LIT);


				if (isLit) {
					// check for neighboring campfires
					BlockPos[] possibleNeighbors = new BlockPos[]{
							blockPosition.north(),
							blockPosition.south(),
							blockPosition.east(),
							blockPosition.west()
					};

					int neighbors = 0;

					for (BlockPos neighbor : possibleNeighbors) {
						if (level.getBlockState(neighbor).getBlock() instanceof CampfireBlock) {
							neighbors++;
						}
					}


					boolean isExtendedRange = blockState.getValue(CampfireBlock.SIGNAL_FIRE);
					int range = isExtendedRange ? 24 : 10 + (int) (3.0 * neighbors);

					double distance = Math.abs(blockPosition.getY() - self.position().y);

					// if player is within range of campfire
					if (distance < range) {
						if (self.level().isClientSide) {
							if (AileronConfig.campfiresPushPlayers() && self.isLocalPlayer()) {
								double force = Math.min(range / distance / 7, 1.0);

								Vec3 existingDeltaMovement = self.getDeltaMovement();
								self.setDeltaMovement(existingDeltaMovement.x, Math.min(existingDeltaMovement.y + force, 1.0), existingDeltaMovement.z);
							}
						}
						else if (AileronConfig.smokestackAirRecharge()) {
							airChargeTime++;
							if (airChargeTime >= AileronConfig.smokestackChargeTicks()) {
								airChargeTime = 0;
								smokeCharge(false);
							}
						}
					}
				}
			}

		}
		else {
			airChargeTime = 0;
		}
		if (self.level().isClientSide && self.isLocalPlayer())
			AileronClient.localPlayerTick(self);
	}

	@Unique
	public void smokeCharge(boolean shouldSetCharged) {
		shouldSetCharged = AileronConfig.campfiresPushPlayers() && shouldSetCharged;

		Player self = ((Player) (Object) this);
		Level level = self.level();
		ServerLevel serverLevel = (ServerLevel) level;

		int stocks = self.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES);
		int smokeStockMaxLevel = EnchantmentHelper.getItemEnchantmentLevel(BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(Aileron.MOD_ID, "smokestack")), self.getItemBySlot(EquipmentSlot.CHEST));
		boolean chargeEffect = false;
		if (shouldSetCharged && !charged) {
			chargeEffect = true;
			charged = true;
		}
		if (stocks < smokeStockMaxLevel) {
			chargeEffect = true;
			self.getEntityData().set(AileronEntityData.SMOKE_STACK_CHARGES, stocks + 1);
		}
		if (chargeEffect) {
			for (ServerPlayer player : serverLevel.players()) {
				serverLevel.sendParticles(player, ParticleTypes.LARGE_SMOKE, false, self.getX(), self.getY(), self.getZ(), 20, 0.5, 0.5, 0.5, 0.1);
				serverLevel.sendParticles(player, ParticleTypes.SMOKE, false, self.getX(), self.getY(), self.getZ(), 100, 0.5, 0.5, 0.5, 0.4);
			}
			level.playSound(null, self.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8f, 0.8f + (stocks * 0.2f));
		}
	}

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	public void defineSynchedData(CallbackInfo ci) {
		((Player) (Object) this).getEntityData().define(AileronEntityData.SMOKE_STACK_CHARGES, 0);
	}

	@Override
	public boolean charged() {
		return charged;
	}

	@Override
	public int getBoostTicks() {
		return boostTicks;
	}

	@Override
	public void setBoostTicks(int boostTicks) {
		this.boostTicks = boostTicks;
	}

	@Override
	public void setSmokeTrailTicks(int boostTicks) {
		this.smokeTrailTicks = boostTicks;
	}

	@Override
	public int getCampfireDamageIFrames() {
		return campfireDamageIFrames;
	}

	@Override
	public void setCampfireDamageIFrames(int campfireDamageIFrames) {
		this.campfireDamageIFrames = campfireDamageIFrames;
	}
}
