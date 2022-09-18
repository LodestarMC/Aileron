package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.ISmokeStackChargeData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {

    @Inject(method = "makeParticles", at = @At("HEAD"), cancellable = true)
    private static void overrideParticles(Level level, BlockPos blockPos, boolean signal, boolean douse, CallbackInfo ci) {
        if (douse) return;

        // check for neighboring campfires
        BlockPos[] possibleNeighbors = new BlockPos[] {
            blockPos.north(),
            blockPos.south(),
            blockPos.east(),
            blockPos.west()
        };

        int neighbors = 0;

        for (BlockPos neighbor : possibleNeighbors) {
            if (level.getBlockState(neighbor).getBlock() instanceof CampfireBlock) {
                neighbors++;
            }
        }

        if (neighbors > 0) {
            Random random = level.getRandom();
            SimpleParticleType particleType = (SimpleParticleType) Registry.PARTICLE_TYPE.get(new ResourceLocation(Aileron.MOD_ID, "custom_campfire_smoke"));
            level.addAlwaysVisibleParticle(particleType, true, (double)blockPos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)blockPos.getY() + random.nextDouble() + random.nextDouble(), (double)blockPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), neighbors * 40 + (signal ? 280 : 80), 0.07D, 0.0D);
            ci.cancel();
        }
    }

    @Redirect(method = "entityInside", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public boolean hurt(Entity instance, DamageSource damageSource, float f) {
        if (instance instanceof Player player && ((instance.isCrouching() && Aileron.wearingElytra(player)) || (((ISmokeStackChargeData) instance).getCampfireDamageIFrames() > 0)))
            return false;
        else
            return instance.hurt(damageSource, f);
    }

}
