package com.lodestar.aileron.mixin;

import com.lodestar.aileron.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
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
public class PlayerEntityMixin implements ISmokeStackChargeData {

    int chargeTime = 0;
    boolean charged = false, startFlyingNextTick = false;

    @Inject(method = "tick", at = @At("TAIL"))
    public void postTick(CallbackInfo ci) {
        AileronConfigInfo config = Aileron.getConfigInfo();

        Player self = ((Player) (Object) this);
        Level level = self.level;

        if(!self.level.isClientSide) {
            final ServerLevel serverLevel = ((ServerLevel) level);

            BlockState underBlockState = level.getBlockState(self.blockPosition());
            if(self.isCrouching() && underBlockState.is(BlockTags.CAMPFIRES) && self.getInventory().getArmor(2).getItem() instanceof ElytraItem) {
                chargeTime++;

                if(chargeTime % config.smokeStackChargeTicks == 0 && chargeTime > 0) {
                    int stocks = self.getEntityData().get(SmokeStocks.DATA_SMOKE_STOCKS);

                    int smokeStockMaxLevel = EnchantmentHelper.getItemEnchantmentLevel(Registry.ENCHANTMENT.get(new ResourceLocation(Aileron.MOD_ID, "smokestack")), self.getInventory().getArmor(2));

                    if (stocks < smokeStockMaxLevel || !charged) {
                        charged = true;

                        if(stocks < smokeStockMaxLevel) self.getEntityData().set(SmokeStocks.DATA_SMOKE_STOCKS, stocks + 1);

                        for (ServerPlayer player : serverLevel.players()) {
                            serverLevel.sendParticles(player, ParticleTypes.LARGE_SMOKE, false, self.getX(), self.getY(), self.getZ(), 20, 0.5, 0.5, 0.5, 0.1);
                            serverLevel.sendParticles(player, ParticleTypes.SMOKE, false, self.getX(), self.getY(), self.getZ(), 100, 0.5, 0.5, 0.5, 0.4);
                        }

                        level.playSound(null, self.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8f, 0.8f + (stocks * 0.2f));
                    }
                }
            } else {
                chargeTime = 0;
            }

            if(startFlyingNextTick) {
                self.startFallFlying();
                startFlyingNextTick = false;
            }

            if(!self.isCrouching() && charged) {
                charged = false;
                chargeTime = 0;

                for (ServerPlayer player : serverLevel.players()) {
                    serverLevel.sendParticles(player, ParticleTypes.LARGE_SMOKE, false, self.getX(), self.getY(), self.getZ(), 40, 0.5, 0.5, 0.5, 0.1);
                    serverLevel.sendParticles(player, ParticleTypes.CAMPFIRE_COSY_SMOKE, false, self.getX(), self.getY(), self.getZ(), 40, 0.5, 0.5, 0.5, 0.1);
                    serverLevel.sendParticles(player, ParticleTypes.SMOKE, false, self.getX(), self.getY(), self.getZ(), 120, 0.5, 0.5, 0.5, 0.4);
                }


                level.playSound(null, self.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8f, 0.8f);

                Aileron.launchClient((ServerPlayer) self);
                self.startFallFlying();
                startFlyingNextTick = true;
            }
        }

        if(self.isFallFlying() && self.level.isClientSide) {
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

            AileronClient.localPlayerTick(self);
        }
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    public void defineSynchedData(CallbackInfo ci) {
        ((Player) (Object) this).getEntityData().define(SmokeStocks.DATA_SMOKE_STOCKS, 0);
    }

    @Override
    public boolean getStartFlyingNextTick() {
        return startFlyingNextTick;
    }

    @Override
    public boolean charged() {
        return charged;
    }
}
