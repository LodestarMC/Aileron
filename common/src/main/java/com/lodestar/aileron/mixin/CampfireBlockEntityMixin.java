package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {

	@Redirect(method = "particleTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CampfireBlock;makeParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;ZZ)V"))
	private static void makeParticles(Level level, BlockPos blockPos, boolean bl, boolean bl2) {

		// check for neighboring campfires
		BlockPos[] possibleNeighbors = new BlockPos[]{
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
			level.addAlwaysVisibleParticle(particleType, true, (double) blockPos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) blockPos.getY() + random.nextDouble() + random.nextDouble(), (double) blockPos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), neighbors * 40 + (bl ? 280 : 80), 0.07D, 0.0D);
			if (bl2) {
				level.addParticle(ParticleTypes.SMOKE, (double) blockPos.getX() + 0.5D + random.nextDouble() / 4.0D * (double) (random.nextBoolean() ? 1 : -1), (double) blockPos.getY() + 0.4D, (double) blockPos.getZ() + 0.5D + random.nextDouble() / 4.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
			}
		} else {
			CampfireBlock.makeParticles(level, blockPos, bl, bl2);
		}
	}
}
