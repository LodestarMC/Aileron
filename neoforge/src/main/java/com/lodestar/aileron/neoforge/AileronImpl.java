package com.lodestar.aileron.neoforge;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.accessor.AileronPlayer;
import com.lodestar.aileron.client.AileronClient;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

@Mod(Aileron.MOD_ID)
public class AileronImpl {

	public static IEventBus modEventBus;

	public AileronImpl(IEventBus modEventBus, ModContainer modContainer) {
		AileronImpl.modEventBus = modEventBus;

		Aileron.init();
		if (FMLLoader.getDist().isClient()) {
			AileronClient.init();
		}

		AileronImpl.modEventBus = null;
	}

	public static boolean canChargeSmokeStack(@Nullable Player player) {
		return player != null && Aileron.getElytra(player).canElytraFly(player) && ((((AileronPlayer)player).getSmokestackCharges() > 0 && player.isFallFlying()) || player.isCrouching());
	}

	public static boolean isModInstalled(String modId) {
		return ModList.get().isLoaded(modId);
	}
}
