package com.lodestar.aileron;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class AileronClient {
    public static int cooldown = 0;
    public static boolean wasJumping = false;

    public static void localPlayerTick(Player self) {
        if(self instanceof LocalPlayer) {
            boolean jumping = ((LocalPlayer) self).input.jumping;
            if (jumping && !wasJumping && cooldown <= 0 && self.isFallFlying() && ((LocalPlayer) self).getFallFlyingTicks() > 0) {
                int stocks = self.getEntityData().get(SmokeStocks.DATA_SMOKE_STOCKS);

                if (stocks > 0) {
                    self.setDeltaMovement(self.getDeltaMovement().add(self.getLookAngle().scale(1.5)));
                    self.getEntityData().set(SmokeStocks.DATA_SMOKE_STOCKS, stocks - 1);
                    self.level.playSound(null, self.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.8f, 0.4f);
                    Aileron.smokeDash();
                }

                cooldown = 50;
            }

            wasJumping = jumping;
        }

        if(cooldown > 0) cooldown --;
    }
}
