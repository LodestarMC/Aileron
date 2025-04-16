package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronConfig;
import com.lodestar.aileron.accessor.AileronCamera;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

	@Shadow @Final private Camera mainCamera;
	@Unique private float smoothDeltaMovementSpeed = 0.0f;

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V"))
	public void renderLevel(float partial, long l, PoseStack poseStack, CallbackInfo ci) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null && player.isFallFlying() && !(Aileron.isModInstalled("cameraoverhaul") || Aileron.isModInstalled("do_a_barrel_roll"))) {
			float roll = ((AileronCamera) mainCamera).getSmoothedEMADifference(player, partial) * 0.225f;

			float deltaMovementSpeed = (float) player.getDeltaMovement().length();
			smoothDeltaMovementSpeed = Mth.lerp(0.2f, smoothDeltaMovementSpeed, deltaMovementSpeed);

			float rotation = (float) (roll * smoothDeltaMovementSpeed * AileronConfig.cameraRollScale());

			if (AileronConfig.doCameraRoll())
				poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
		}
	}
}
