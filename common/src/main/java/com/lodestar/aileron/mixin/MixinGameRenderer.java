package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronConfig;
import com.lodestar.aileron.ICameraEMA;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Shadow @Final private Camera mainCamera;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V"))
    public void renderLevel(float partial, long l, PoseStack poseStack, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null && player.isFallFlying() && !Aileron.isModInstalled("cameraoverhaul")) {
            float roll = ((ICameraEMA) mainCamera).getSmoothedEMADifference() * 0.225f;

            float deltaMovementSpeed = (float) player.getDeltaMovement().length();

            if(AileronConfig.doCameraRoll()) poseStack.mulPose(Vector3f.ZP.rotationDegrees((float) (roll * deltaMovementSpeed * AileronConfig.cameraRollScale())));
        }
    }
}
