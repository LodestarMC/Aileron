package com.lodestar.aileron;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class AileronGuiRender {

    private static final ResourceLocation TEXTURE_EMPTY = new ResourceLocation("aileron:textures/gui/sprites/hud/smokestack_empty.png");
    private static final ResourceLocation TEXTURE_FULL = new ResourceLocation("aileron:textures/gui/sprites/hud/smokestack_full.png");

    public static void renderSmokeStackBar(GuiGraphics graphics, int screenHeight, int screenWidth) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (!Aileron.canChargeSmokeStack(player)) return;

        int smokeStockLevel = EnchantmentHelper.getItemEnchantmentLevel(
                BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(Aileron.MOD_ID, "smokestack")),
                player.getInventory().getArmor(2)
        );

        int screenX = (screenWidth / 2) - 12;
        screenX += (3 - smokeStockLevel) * 3;
        int screenY = screenHeight - 58;

        int smokeStackCharges = player.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES);
        for (int spriteIndex = 0; spriteIndex < smokeStockLevel; spriteIndex++) {
            ResourceLocation texture;
            if (smokeStackCharges > spriteIndex) texture = TEXTURE_FULL;
            else texture = TEXTURE_EMPTY;
            int spriteX = screenX + (spriteIndex * 8);
            int spriteY = screenY + (spriteIndex / 2);
            graphics.blit(texture, spriteX, spriteY, 0, 0, 9, 9, 9, 9);
        }

    }

}
