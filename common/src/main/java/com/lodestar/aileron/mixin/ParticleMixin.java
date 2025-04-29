package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public class ParticleMixin {
    @Inject(method = "move", at = @At("HEAD"))
    private void moveStart(double d, double e, double f, CallbackInfo ci) {
        Aileron.smokeCollisionCalculation = true;
    }

    @Inject(method = "move", at = @At("TAIL"))
    private void moveEnd(double d, double e, double f, CallbackInfo ci) {
        Aileron.smokeCollisionCalculation = false;
    }
}
