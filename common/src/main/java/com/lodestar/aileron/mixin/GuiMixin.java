package com.lodestar.aileron.mixin;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.SmokeStocks;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
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

    @Shadow private int screenWidth;

    @Shadow private int screenHeight;

    @ModifyArg(method = "renderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I"), index = 3)
    public float shiftUpwards(float g) {
        LocalPlayer player = Minecraft.getInstance().player;

        boolean wearingElytra = Aileron.wearingElytra(player);

        if ((player.getEntityData().get(SmokeStocks.DATA_SMOKE_STOCKS) > 0 && player.isFallFlying() && wearingElytra) || (wearingElytra && player.isCrouching())) {
            return g + 4;
        } else {
            return g;
        }
    }

    private static final ResourceLocation smokeStock = new ResourceLocation("aileron:textures/gui/smokestack.png");

    @Inject(method = "renderExperienceBar", at = @At(value = "TAIL"))
    public void renderExperienceBar(PoseStack poseStack, int i, CallbackInfo ci) {
        final Gui self = (Gui) (Object) this;

        LocalPlayer player = Minecraft.getInstance().player;
        boolean wearingElytra = Aileron.wearingElytra(player);

        if ((player.getEntityData().get(SmokeStocks.DATA_SMOKE_STOCKS) > 0 && player.isFallFlying() && wearingElytra) || (wearingElytra && player.isCrouching())) {
            int y = this.screenHeight - 40;
            int smokeStockMaxLevel = EnchantmentHelper.getItemEnchantmentLevel(Registry.ENCHANTMENT.get(new ResourceLocation(Aileron.MOD_ID, "smokestack")), player.getInventory().getArmor(2));

            RenderSystem.setShaderTexture(0, smokeStock);
            int x = screenWidth / 2 - 10 + ((3 - smokeStockMaxLevel) * 3);

            for (int j = 0; j < smokeStockMaxLevel; j ++) {
                if(player.getEntityData().get(SmokeStocks.DATA_SMOKE_STOCKS) > j) {
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                } else {
                    RenderSystem.setShaderColor(0f, 0f, 0f, 0.5f);
                }
                GuiComponent.blit(poseStack, x + (j * 6), y, self.getBlitOffset(), 0, 0, 8, 8, 8, 8);
            }
        }

    }
}
