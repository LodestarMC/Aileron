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

	@Inject(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V"))
	public void moveAttackIndicator1(float f, GuiGraphics guiGraphics, CallbackInfo ci) {
		guiGraphics.pose().translate(AileronGuiRender.moveAttackIndicator(), 0, 0);
	}

	@Inject(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V"))
	public void moveAttackIndicator2(float f, GuiGraphics guiGraphics, CallbackInfo ci) {
		guiGraphics.pose().translate(-AileronGuiRender.moveAttackIndicator(), 0, 0);
	}

	@Inject(method = "renderHotbar", at = @At(value = "TAIL"))
	public void renderSmokeStackBar(float f, GuiGraphics guiGraphics, CallbackInfo ci) {
		AileronGuiRender.renderSmokeStackBar(guiGraphics, screenHeight, screenWidth);
	}

}
