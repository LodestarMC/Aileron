package com.lodestar.aileron.mixin;

import com.lodestar.aileron.AileronConfig;
import com.lodestar.aileron.accessor.AileronPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public class MixinFireworkRocketItem {

	@Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FireworkRocketEntity;<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V"), cancellable = true)
	public void use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		if (!AileronConfig.fireworkChanges()) return;

		ItemStack stack = player.getItemInHand(hand);
		if (!player.getAbilities().instabuild) stack.shrink(1);

		((AileronPlayer) player).setSmokeTrailTicks(100);
		FireworkRocketItem item = (FireworkRocketItem) (Object) this;
		player.getCooldowns().addCooldown(item, 100);
		player.awardStat(Stats.ITEM_USED.get(item));
		cir.setReturnValue(InteractionResultHolder.pass(stack));
	}

}
