package com.lodestar.aileron;

import com.lodestar.aileron.accessor.AileronPlayer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Aileron {
	public static final String MOD_ID = "aileron";

	public static boolean smokeCollisionCalculation = false;

	public static void init() {
		AileronConfig.init();
		AileronParticles.register();
		AileronAttributes.register();
		AileronLootModifiers.register();
		AileronNetworking.register();
		AileronAccessoryCompat.register();
	}

	@ExpectPlatform
	public static boolean isModInstalled(String modId) {
		return false;
	}

	@ExpectPlatform
	public static boolean canChargeSmokeStack(@Nullable Player player) {
		return false;
	}

	public static boolean isElytra(ItemStack stack) {
		return stack.is(AileronTags.ELYTRA);
	}

	public static ItemStack getChestElytra(LivingEntity entity) {
		ItemStack chestItem = entity.getItemBySlot(EquipmentSlot.CHEST);
		if (isElytra(chestItem)) {
			return chestItem;
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack getElytra(LivingEntity entity) {
		ItemStack stack = AileronAccessoryCompat.getAccessoryElytra(entity);
		if (stack.isEmpty()) {
			ItemStack chestItem = getChestElytra(entity);
			stack = (!chestItem.isEmpty()) ? chestItem : ItemStack.EMPTY;
		}
		return stack;
	}

	public static void boostPlayer(Player player) {
		if (player instanceof AileronPlayer ap) {
			ap.setBoostTicks(50);
		}
	}

	public static void playerDashedServer(ServerPlayer player) {
		ServerLevel serverLevel = (ServerLevel) player.level();
		int stocks = ((AileronPlayer)player).getSmokestackCharges();

		if (stocks > 0) {
			((AileronPlayer)player).setSmokestackCharges(stocks - 1);

			sendBoostParticles(serverLevel, player.getX(), player.getY(), player.getZ());
			serverLevel.playSound(null, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8f, 0.8f + (stocks * 0.2f));

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

	public static boolean isElytraFlightPassable(BlockState blockState) {
		return (blockState.getBlock() instanceof LeavesBlock) || blockState.is(AileronTags.ELYTRA_FLIGHT_PASSABLE);
	}

	public static boolean canGoThroughLeaves(LivingEntity entity) {
		if (entity != null) {
			return (entity.isFallFlying()) && (entity.getDeltaMovement().length() > 0.3);
		}
		return false;
	}

}
