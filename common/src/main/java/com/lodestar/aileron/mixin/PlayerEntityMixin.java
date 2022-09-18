package com.lodestar.aileron.mixin;

import com.lodestar.aileron.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin implements ISmokeStackChargeData {

    @Shadow public abstract boolean isLocalPlayer();

    int campfireDamageIFrames = 0;
    int smokeTrailTicks = 0;
    int chargeTime = 0;
    boolean charged = false;
    int startFlyingTimer = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    public void postTick(CallbackInfo ci) {
        AileronConfigInfo config = Aileron.getConfigInfo();

        Player self = ((Player) (Object) this);
        Level level = self.level;

        if(boostTicks > 0) boostTicks --;
        if(!self.isFallFlying()) boostTicks = 0;

        if(campfireDamageIFrames > 0) campfireDamageIFrames --;
        if(startFlyingTimer > 0) startFlyingTimer --;

        if(smokeTrailTicks > 0) smokeTrailTicks --;
        if(!self.isFallFlying()) smokeTrailTicks = 0;


        // boosting
        if (boostTicks > 0) {

            if(level.isClientSide && isLocalPlayer()) {
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
        if (smokeTrailTicks > 0 ) {

            if (self.tickCount == 0) {
                final ServerLevel serverLevel = ((ServerLevel) level);

                for (ServerPlayer player : serverLevel.players()) {
                    Vec3 pos = self.position().add(self.getLookAngle().scale(-1.0));
                    serverLevel.sendParticles(player, ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, false, pos.x, pos.y, pos.z, 2, 0.1, 0.1, 0.1, 0.005);
                }
            }

        }


        BlockState underBlockState = level.getBlockState(self.blockPosition());
        if(self.isCrouching() && underBlockState.is(BlockTags.CAMPFIRES) && Aileron.wearingElytra(self)) {

            if(level.isClientSide) {
                SimpleParticleType[] particles = { ParticleTypes.FLAME, ParticleTypes.SMOKE };

                for (SimpleParticleType particle : particles) {
                    Vec3 randomOffset = new Vec3(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
                    randomOffset = randomOffset.normalize().scale(2.0);
                    Vec3 motion = randomOffset.scale(-0.075);
                    Vec3 position = self.position().add(0.0, self.getEyeHeight() / 2.0, 0.0).add(randomOffset);
                    level.addParticle(particle, position.x, position.y, position.z, motion.x, motion.y, motion.z);
                }
            } else {
                final ServerLevel serverLevel = ((ServerLevel) level);
                chargeTime++;

                if (chargeTime % config.smokeStackChargeTicks == 0 && chargeTime > 0) {
                    int stocks = self.getEntityData().get(SmokeStocks.DATA_SMOKE_STOCKS);

                    int smokeStockMaxLevel = EnchantmentHelper.getEnchantmentLevel(AileronEnchantments.SMOKESTACK, self);

                    if (stocks < smokeStockMaxLevel || !charged) {
                        charged = true;

                        if (stocks < smokeStockMaxLevel)
                            self.getEntityData().set(SmokeStocks.DATA_SMOKE_STOCKS, stocks + 1);


                        for (ServerPlayer player : serverLevel.players()) {
                            serverLevel.sendParticles(player, ParticleTypes.LARGE_SMOKE, false, self.getX(), self.getY(), self.getZ(), 20, 0.5, 0.5, 0.5, 0.1);
                            serverLevel.sendParticles(player, ParticleTypes.SMOKE, false, self.getX(), self.getY(), self.getZ(), 100, 0.5, 0.5, 0.5, 0.4);
                        }
                        level.playSound(null, self.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8f, 0.8f + (stocks * 0.2f));
                    }
                }
            }
        } else {
            chargeTime = 0;

            if (!level.isClientSide && !self.isFallFlying() && campfireDamageIFrames == 0 && !charged) {
                self.getEntityData().set(SmokeStocks.DATA_SMOKE_STOCKS, 0);
            }
        }

        if(startFlyingTimer == 0 && !level.isClientSide) {
            startFlyingTimer = -1;
            self.startFallFlying();
        }

        if(!self.isCrouching() && charged) {
            final ServerLevel serverLevel = ((ServerLevel) level);

            charged = false;
            chargeTime = 0;

            for (ServerPlayer player : serverLevel.players()) {
                serverLevel.sendParticles(player, ParticleTypes.LARGE_SMOKE, false, self.getX(), self.getY(), self.getZ(), 40, 0.5, 0.5, 0.5, 0.1);
                serverLevel.sendParticles(player, ParticleTypes.CAMPFIRE_COSY_SMOKE, false, self.getX(), self.getY(), self.getZ(), 40, 0.5, 0.5, 0.5, 0.1);
                serverLevel.sendParticles(player, ParticleTypes.SMOKE, false, self.getX(), self.getY(), self.getZ(), 120, 0.5, 0.5, 0.5, 0.4);
            }

            setCampfireDamageIFrames(20);

            level.playSound(null, self.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8f, 0.8f);

            self.startFallFlying();
            Aileron.launchClient((ServerPlayer) self);
            self.tryToStartFallFlying();
            startFlyingTimer = 5;
        }

        if(self.isFallFlying() && self.level.isClientSide && self.isLocalPlayer()) {
            int maxRange = 38;
            int depth = 0;

            BlockPos blockPosition = self.blockPosition();

            while (depth < maxRange && level.isEmptyBlock(blockPosition) && level.isInWorldBounds(blockPosition)) {
                depth++;
                blockPosition = blockPosition.below();
            }

            BlockState blockState = level.getBlockState(blockPosition);
            if (blockState.is(BlockTags.CAMPFIRES) && config.campfiresPushPlayers) {
                // player is over a campfire
                // determine range of campfire
                boolean isLit = blockState.getValue(CampfireBlock.LIT);


                if (isLit) {
                    // check for neighboring campfires
                    BlockPos[] possibleNeighbors = new BlockPos[] {
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
                    int range = isExtendedRange ? 24 : 10 + (int)(3.0 * neighbors);

                    double distance = Math.abs(blockPosition.getY() - self.position().y);

                    // if player is within range of campfire
                    if (distance < range) {
                        double force = Math.min(range / distance / 7, 1.0);


                        Vec3 existingDeltaMovement = self.getDeltaMovement();
                        self.setDeltaMovement(existingDeltaMovement.x, Math.min(existingDeltaMovement.y + force, 1.0), existingDeltaMovement.z);
                    }
                }
            }

        }

        if(self.level.isClientSide && self.isLocalPlayer())
            AileronClient.localPlayerTick(self);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    public void defineSynchedData(CallbackInfo ci) {
        ((Player) (Object) this).getEntityData().define(SmokeStocks.DATA_SMOKE_STOCKS, 0);
    }

    @Override
    public boolean charged() {
        return charged;
    }

    int boostTicks = 0;

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
