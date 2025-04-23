package com.lodestar.aileron.mixin;

import com.lodestar.aileron.AileronGuiRender;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

	@Shadow @Final
	private Minecraft minecraft;

	@Inject(method = "renderItemHotbar", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", ordinal = 1))
	public void moveAttackIndicator1(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		guiGraphics.pose().translate(AileronGuiRender.moveAttackIndicator(), 0, 0);
	}

	@Inject(method = "renderItemHotbar", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", ordinal = 1))
	public void moveAttackIndicator2(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		guiGraphics.pose().translate(-AileronGuiRender.moveAttackIndicator(), 0, 0);
	}

	@Inject(method = "renderHotbarAndDecorations", at = @At(value = "TAIL"))
	public void renderSmokeStackBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		AileronGuiRender.renderSmokeStackBar(guiGraphics);
	}

}
