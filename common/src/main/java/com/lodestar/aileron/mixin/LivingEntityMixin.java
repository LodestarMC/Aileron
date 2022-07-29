package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }


    @Redirect(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 6))
    private void modifyVelocity(LivingEntity instance, Vec3 vec3) {
        Vec3 negator = new Vec3(1.0 / 0.9900000095367432D, 1.0, 1.0 / 0.9900000095367432D);

        int cloudSkipper = instance instanceof Player ? EnchantmentHelper.getItemEnchantmentLevel(Registry.ENCHANTMENT.get(new ResourceLocation(Aileron.MOD_ID, "cloudskipper")), ((Player) instance).getInventory().getArmor(2)) : 0;

        double fac = 0;
        double y = instance.position().y;
        if (y < 100)
            fac = 0.0;
        else if (y < 230)
            fac = 0.00006 * Math.pow(y - 100, 2);
        else
            fac = 1;

        fac *= 0.6;
        fac *= cloudSkipper / 3.0;

        if(fac > 0.1 && !level.isClientSide && tickCount % 4 == 0) {
            ServerLevel serverLevel = ((ServerLevel) level);

            for (ServerPlayer player : serverLevel.players()) {
                serverLevel.sendParticles(player, ParticleTypes.POOF, false, getX(), getY(), getZ(), 2, 0.1, 0.1, 0.1, 0.025);
            }
        }

        negator = new Vec3(negator.x, 1.0, negator.z);

        // lerp between vec3 and vec3 * negator based on fac
        vec3 = vec3.lerp(vec3.multiply(negator), fac);

        instance.setDeltaMovement(vec3);
    }
}
