package com.lodestar.aileron.client;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEntityData;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public class AileronClient {
	public static int cooldown = 0;
	public static boolean wasJumping = false;

	public static void init() {
		AileronClientNetworking.register();
		AileronClientParticles.register();
		AileronClientKeybinds.register();
	}

	public static void launchPlayer() {
		Aileron.boostPlayer(Minecraft.getInstance().player);
	}


	public static void localPlayerTick(Player self) {
		if (self instanceof LocalPlayer localPlayer) {
			boolean jumping;
			if (AileronClientKeybinds.SMOKESTACK_BOOST.same(Minecraft.getInstance().options.keyJump)) {
				jumping = Minecraft.getInstance().options.keyJump.isDown();
			} else {
				jumping = AileronClientKeybinds.SMOKESTACK_BOOST.isDown();
			}
			if (jumping && !wasJumping && cooldown <= 0 && self.isFallFlying() && localPlayer.getFallFlyingTicks() > 0) {
				int stocks = self.getEntityData().get(AileronEntityData.SMOKE_STACK_CHARGES);

				if (stocks > 0) {
					self.setDeltaMovement(self.getDeltaMovement().add(self.getLookAngle().scale(1.5)));
					self.getEntityData().set(AileronEntityData.SMOKE_STACK_CHARGES, stocks - 1);
					AileronClientNetworking.sendSmokeStackDash();
				}

				cooldown = 50;
			}

			wasJumping = jumping;
		}

		if (cooldown > 0) cooldown--;
	}
}
