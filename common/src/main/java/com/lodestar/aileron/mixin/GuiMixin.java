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

	@ModifyArg(
			method = "renderHotbar",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"
			),
			slice = @Slice(
					from = @At(
							value = "INVOKE",
							target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V"
					),
					to = @At(
							value = "INVOKE",
							target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V"
					)
			),
			index = 1
	)
	private int renderAttackIndicator(int x) {
		return AileronGuiRender.moveAttackIndicator(x);
	}

	@Inject(method = "renderHotbar", at = @At(value = "TAIL"))
	public void renderSmokeStackBar(float f, GuiGraphics guiGraphics, CallbackInfo ci) {
		AileronGuiRender.renderSmokeStackBar(guiGraphics, screenHeight, screenWidth);
	}

}
