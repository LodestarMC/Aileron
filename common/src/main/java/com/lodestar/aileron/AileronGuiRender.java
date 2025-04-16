package com.lodestar.aileron;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class AileronGuiRender {

    private static final ResourceLocation TEXTURE_EMPTY = new ResourceLocation("aileron:textures/gui/sprites/hud/smokestack_empty.png");
    private static final ResourceLocation TEXTURE_FULL = new ResourceLocation("aileron:textures/gui/sprites/hud/smokestack_full.png");

    public static int moveAttackIndicator(int spriteX) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return spriteX;
        if (!Aileron.canChargeSmokeStack(player)) return spriteX;

        if (player.getMainArm() == HumanoidArm.LEFT) spriteX -= 9;
        else spriteX += 5;

        return spriteX;
    }

    public static void renderSmokeStackBar(GuiGraphics graphics, int screenHeight, int screenWidth) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (!Aileron.canChargeSmokeStack(player)) return;

        int smokeStockLevel = EnchantmentHelper.getItemEnchantmentLevel(
                BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(Aileron.MOD_ID, "smokestack")),
                Aileron.getElytra(player)
        );

        int screenX = (screenWidth / 2);
        if (player.getMainArm() == HumanoidArm.LEFT) screenX -= 102;
        else screenX += 92;

        int screenY = screenHeight - 10;

        int smokeStackCharges = player.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES);
        for (int spriteIndex = 0; spriteIndex < smokeStockLevel; spriteIndex++) {
            ResourceLocation texture;
            if (smokeStackCharges > spriteIndex) texture = TEXTURE_FULL;
            else texture = TEXTURE_EMPTY;
            int spriteY = screenY - (spriteIndex * 9);
            graphics.blit(texture, screenX, spriteY, 0, 0, 9, 9, 9, 9);
        }

    }

}
