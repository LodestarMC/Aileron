package com.lodestar.aileron;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class CustomCampfireParticle extends TextureSheetParticle {
    CustomCampfireParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, int l) {
        super(clientLevel, d, e, f);
        this.scale(3.0F);
        this.setSize(0.25F, 0.25F);
        this.lifetime = this.random.nextInt(50) + l;
        this.lifetime = this.random.nextInt(50) + l;

        this.gravity = 3.0E-6F;
        this.xd = 0;
        this.yd = h + (double) (this.random.nextFloat() / 500.0F);
        this.zd = 0;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
            this.xd += (this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1));
            this.zd += (this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1));
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.age >= this.lifetime - 60 && this.alpha > 0.01F) {
                this.alpha -= 0.015F;
            }

        } else {
            this.remove();
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class CustomCampfireParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public CustomCampfireParticleProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            CustomCampfireParticle campfireSmokeParticle = new CustomCampfireParticle(clientLevel, d, e, f, g, h, i, (int) g);
            campfireSmokeParticle.setAlpha(0.95F);
            campfireSmokeParticle.pickSprite(this.sprites);
            return campfireSmokeParticle;
        }
    }
}