package com.lodestar.aileron.mixin;

import com.lodestar.aileron.accessor.AileronCamera;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin implements AileronCamera {

	double previousEMAValue = 0.0;
	double EMAValue = 0.0;

	@Shadow
	public abstract float getYRot();

	@Override
	public double getPreviousEMAValue() {
		return previousEMAValue;
	}

	@Override
	public void setPreviousEMAValue(float previousEMA) {
		previousEMAValue = previousEMA;
	}

	@Override
	public double getEMAValue() {
		return EMAValue;
	}

	@Override
	public void setEMAValue(float EMA) {
		EMAValue = EMA;
	}

	@Override
	public float getSmoothedEMADifference(Entity entity, float partial) {
		return entity != null ? entity.getYRot() - (float)Mth.lerp(partial, previousEMAValue, EMAValue) : 0.0f;
	}
}
