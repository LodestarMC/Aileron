package com.lodestar.aileron.mixin;

import com.lodestar.aileron.AileronGuiRender;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

	@Shadow private int screenWidth;
	@Shadow private int screenHeight;


	@Inject(method = "renderExperienceBar", at = @At(value = "TAIL"))
	public void renderExperienceBar(GuiGraphics graphics, int i, CallbackInfo ci) {
		AileronGuiRender.renderSmokeStackBar(graphics, screenHeight, screenWidth);
	}

}
