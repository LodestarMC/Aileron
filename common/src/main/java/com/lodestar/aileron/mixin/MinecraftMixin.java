package com.lodestar.aileron.mixin;

import com.lodestar.aileron.AileronConfig;
import com.lodestar.aileron.accessor.AileronCamera;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	@Shadow @Final public GameRenderer gameRenderer;
	float previousEMA = 0.0f;
	float EMA = 0.0f;

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		Camera camera = gameRenderer.getMainCamera();
		AileronCamera ema = ((AileronCamera) camera);

		float curYaw = camera.getEntity() != null ? camera.getEntity().getYRot() : 0;

		previousEMA = EMA;
		EMA = (float) Mth.lerp(AileronConfig.cameraRollSpeed(), EMA, curYaw);

		ema.setPreviousEMAValue(previousEMA);
		ema.setEMAValue(EMA);
	}

}
