package com.lodestar.aileron.mixin;

import com.lodestar.aileron.AileronGuiRender;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

	@Shadow private int screenWidth;
	@Shadow private int screenHeight;

	// TODO this could be more optimized, make the variable only change once instead of for each method call
	@ModifyArg(
			method = "renderHotbar",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
					ordinal = 5
			),
			index = 1
	)
	private int attackIndicator1(int x) {
		return AileronGuiRender.moveAttackIndicator(x);
	}

	@ModifyArg(
			method = "renderHotbar",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
					ordinal = 4
			),
			index = 1
	)
	private int attackIndicator2(int x) {
		return AileronGuiRender.moveAttackIndicator(x);
	}


	@Inject(method = "renderExperienceBar", at = @At(value = "TAIL"))
	public void renderExperienceBar(GuiGraphics graphics, int i, CallbackInfo ci) {
		AileronGuiRender.renderSmokeStackBar(graphics, screenHeight, screenWidth);
	}

}
