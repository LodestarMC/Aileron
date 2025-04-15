package com.lodestar.aileron;

import eu.midnightdust.lib.config.MidnightConfig;

public class AileronConfig extends MidnightConfig {
	@Comment public static Comment generalChanges;
	@Entry public static boolean fireworkChanges = true;

	@Comment public static Comment cameraSettings;
	@Entry public static boolean doCameraRoll = true;
	@Entry(min = 0.0, max = 2.0) public static double cameraRollScale = 1.0;
	@Entry(min = 0.05, max = 1.0) public static double cameraRollSpeed = 0.1;

	@Comment public static Comment enchantments;
	@Entry(min = 0.1, max = 2.0) public static double cloudskipperSpeedMultiplier = 1.0;
	@Entry(min = -2032.0, max = 4064.0) public static double cloudskipperCloudLevel = 192.0;

	@Comment public static Comment campfires;
	@Entry public static boolean campfiresPushPlayers = true;
	@Entry(min = 2) public static int smokeStackChargeTicks = 20;

	public static boolean fireworkChanges() {
		return fireworkChanges;
	}

	public static boolean doCameraRoll() {
		return doCameraRoll;
	}

	public static double cameraRollScale() {
		return cameraRollScale;
	}

	public static double cameraRollSpeed() {
		return cameraRollSpeed;
	}

	public static double cloudskipperSpeedMultiplier() { return cloudskipperSpeedMultiplier; }

	public static double cloudskipperCloudLevel() { return cloudskipperCloudLevel; }

	public static boolean campfiresPushPlayers() {
		return campfiresPushPlayers;
	}

	public static int smokeStackChargeTicks() {
		return smokeStackChargeTicks;
	}

	public static void init() {
		MidnightConfig.init(Aileron.MOD_ID, AileronConfig.class);
	}
}
