package com.lodestar.aileron;

import com.lodestar.aileron.accessor.AileronPlayer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Aileron {
	public static final String MOD_ID = "aileron";

	public static void init() {
		AileronConfig.init();
		AileronParticles.register();
		AileronEnchantments.register();
		AileronNetworking.register();
	}

	@ExpectPlatform
	public static boolean isModInstalled(String modId) {
		return false;
	}

	@ExpectPlatform
	public static boolean canChargeSmokeStack(@Nullable Player player) {
		return false;
	}

	@ExpectPlatform
	public static boolean isElytra(ItemStack stack) {
		return false;
	}

	public static void boostPlayer(Player player) {
		if (player instanceof AileronPlayer ap) {
			ap.setBoostTicks(50);
		}
	}

	public static void playerDashedServer(ServerPlayer player) {
		ServerLevel serverLevel = (ServerLevel) player.level;
		int stocks = player.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES);

		if (stocks > 0) {
			player.getEntityData().set(AileronEntityData.SMOKE_STACK_CHARGES, stocks - 1);

			sendBoostParticles(serverLevel, player.getX(), player.getY(), player.getZ());

			boostPlayer(player);
		}
	}

	public static void sendBoostParticles(ServerLevel serverLevel, double x, double y, double z) {
		for (ServerPlayer serverPlayer : serverLevel.players()) {
			serverLevel.sendParticles(serverPlayer, ParticleTypes.LARGE_SMOKE, false, x, y, z, 40, 0.5, 0.5, 0.5, 0.1);
			serverLevel.sendParticles(serverPlayer, ParticleTypes.CAMPFIRE_COSY_SMOKE, false, x, y, z, 40, 0.5, 0.5, 0.5, 0.1);
			serverLevel.sendParticles(serverPlayer, ParticleTypes.SMOKE, false, x, y, z, 120, 0.5, 0.5, 0.5, 0.4);
		}
	}

}
