package com.lodestar.aileron.client;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.AileronEntityData;
import com.lodestar.aileron.accessor.AileronPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

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
				int stocks = ((AileronPlayer)self).getSmokestackCharges();

				if (stocks > 0) {
					self.setDeltaMovement(self.getDeltaMovement().add(self.getLookAngle().scale(1.5)));
					((AileronPlayer)self).setSmokestackCharges(stocks - 1);
					AileronClientNetworking.sendSmokeStackDash();
				}

				cooldown = 50;
			}

			wasJumping = jumping;
		}

		if (cooldown > 0) cooldown--;
	}
}
