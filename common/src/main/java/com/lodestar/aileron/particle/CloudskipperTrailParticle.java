package com.lodestar.aileron.particle;


import com.lodestar.aileron.Aileron;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Vector;

@Environment(EnvType.CLIENT)
public class CloudskipperTrailParticle extends TextureSheetParticle  {
	private final SpriteSet spriteSet;

	public CloudskipperTrailParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, SpriteSet spriteSet) {
		super(clientLevel, d, e, f, g, h, i);
		this.spriteSet = spriteSet;

		this.setSpriteFromAge(spriteSet);
		setAlpha(Minecraft.getInstance().gameRenderer.getMainCamera());
	}

	@Override
	public void tick() {
		this.setSpriteFromAge(spriteSet);
		super.tick();
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
		setAlpha(camera);
		super.render(vertexConsumer, camera, f);
	}

	public void setAlpha(Camera camera) {
		Vec3 particlePos = new Vec3(x, y, z);
		Vec3 cameraPos = camera.getPosition();
		this.alpha = (float) Math.min(particlePos.subtract(cameraPos).length() * 0.1f, 1.0f);
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public static class CloudskipperTrailParticleProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public CloudskipperTrailParticleProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}


		@Override
		public @Nullable Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			return new CloudskipperTrailParticle(clientLevel, d, e, f, g, h, i, spriteSet);
		}
	}
}