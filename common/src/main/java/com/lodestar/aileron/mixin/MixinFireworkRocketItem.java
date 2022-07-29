package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.ISmokeStackChargeData;
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
    public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        ((ISmokeStackChargeData) player).setSmokeTrailTicks(100);
        player.getCooldowns().addCooldown((FireworkRocketItem) (Object) this, 100);
        player.awardStat(Stats.ITEM_USED.get((FireworkRocketItem) (Object) this));
        cir.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(interactionHand)));
    }
}
