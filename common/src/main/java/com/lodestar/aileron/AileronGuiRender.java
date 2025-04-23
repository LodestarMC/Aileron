package com.lodestar.aileron;

import com.lodestar.aileron.accessor.AileronPlayer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class AileronGuiRender {

    private static final ResourceLocation TEXTURE_EMPTY = ResourceLocation.fromNamespaceAndPath("aileron", "textures/gui/sprites/hud/smokestack_empty.png");
    private static final ResourceLocation TEXTURE_FULL = ResourceLocation.fromNamespaceAndPath("aileron", "textures/gui/sprites/hud/smokestack_full.png");

    public static int moveAttackIndicator() {
        int spriteX = 0;

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return spriteX;
        if (!Aileron.canChargeSmokeStack(player)) return spriteX;

        int smokeStockLevel = ((AileronPlayer) player).getSmokestackCapacity();
        boolean left = player.getMainArm() == HumanoidArm.LEFT;

        int offset = (8 * (smokeStockLevel / 3 + 1));
        if (left) spriteX -= 1 + offset;
        else spriteX += -3 + offset;

        return spriteX;
    }

    public static void renderSmokeStackBar(GuiGraphics graphics) {

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (!Aileron.canChargeSmokeStack(player)) return;

        int smokeStockLevel = ((AileronPlayer) player).getSmokestackCapacity();
        boolean left = player.getMainArm() == HumanoidArm.LEFT;

        int screenX = (graphics.guiWidth() / 2);
        if (left) screenX -= 102;
        else screenX += 92;

        int screenY = graphics.guiHeight() - 10;

        int smokeStackCharges = ((AileronPlayer) player).getSmokestackCharges();
        RenderSystem.enableBlend();
        for (int spriteIndex = 0; spriteIndex < smokeStockLevel; spriteIndex++) {
            ResourceLocation texture;
            int xPos = spriteIndex / 3;
            int yPos = spriteIndex % 3;
            if (smokeStackCharges > spriteIndex) texture = TEXTURE_FULL;
            else texture = TEXTURE_EMPTY;
            int spriteX = screenX + ((xPos * 8) * (left ? -1 : 1));
            int spriteY = screenY - (yPos * 9);
            graphics.blit(texture, spriteX, spriteY, 0, 0, 9, 9, 9, 9);
        }
        RenderSystem.disableBlend();
    }

}
