package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronConfigInfo;
import com.lodestar.aileron.ICameraEMA;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    float previousEMA = 0.0f;
    float EMA = 0.0f;

    @Shadow @Final public GameRenderer gameRenderer;

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        AileronConfigInfo config = Aileron.getConfigInfo();

        Camera camera = gameRenderer.getMainCamera();
        ICameraEMA ema = ((ICameraEMA) camera);

        float curYaw = camera.getEntity().getYRot();

        previousEMA = EMA;
        EMA = (float) (EMA + (curYaw - EMA) * config.cameraRollSpeed);

        ema.setPreviousEMAValue(previousEMA);
        ema.setEMAValue(EMA);
    }

}
