package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEntityData;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

	private static final ResourceLocation smokeStock = new ResourceLocation("aileron:textures/gui/smokestack.png");
	@Shadow private int screenWidth;
	@Shadow private int screenHeight;

	@ModifyArg(method = "renderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"), index = 3)
	public int shiftUpwards(int i) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (Aileron.canChargeSmokeStack(player)) {
			return i + 4;
		} else {
			return i;
		}
	}

	@Inject(method = "renderExperienceBar", at = @At(value = "TAIL"))
	public void renderExperienceBar(GuiGraphics graphics, int i, CallbackInfo ci) {
		final Gui self = (Gui) (Object) this;

		LocalPlayer player = Minecraft.getInstance().player;
		if (Aileron.canChargeSmokeStack(player)) {
			int y = this.screenHeight - 40;
			int smokeStockMaxLevel = EnchantmentHelper.getItemEnchantmentLevel(BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(Aileron.MOD_ID, "smokestack")), player.getInventory().getArmor(2));

			RenderSystem.setShaderTexture(0, smokeStock);
			int x = screenWidth / 2 - 10 + ((3 - smokeStockMaxLevel) * 3);

			for (int j = 0; j < smokeStockMaxLevel; j++) {
				if (player.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES) > j) {
					RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
				} else {
					RenderSystem.setShaderColor(0f, 0f, 0f, 0.5f);
				}
				graphics.blit(new ResourceLocation("textures/gui/icons.png"), x + (j * 6), y, 0, 0, 8, 8, 8, 8);
			}
		}

	}
}
