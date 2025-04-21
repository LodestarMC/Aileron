package com.lodestar.aileron;

import eu.midnightdust.lib.config.MidnightConfig;

public class AileronConfig extends MidnightConfig {
	@Comment public static Comment generalChanges;
	@Entry public static FireworkUseBehaviour fireworkUseBehaviour = FireworkUseBehaviour.COSMETIC;
	public enum FireworkUseBehaviour {
		COSMETIC, NORMAL, DISABLE
	}

	@Comment public static Comment cameraSettings;
	@Entry public static boolean doCameraRoll = true;
	@Entry(min = 0.0, max = 2.0) public static double cameraRollScale = 1.0;
	@Entry(min = 0.05, max = 1.0) public static double cameraRollSpeed = 0.1;

	@Comment public static Comment enchantments;
	@Entry(min = 0.1, max = 2.0) public static double cloudskipperSpeedMultiplier = 1.0;
	@Entry(min = -2032.0, max = 4064.0) public static double cloudskipperCloudLevel = 192.0;
	@Entry public static boolean smokestackAirRecharge = true;
	@Entry(min = 2) public static int smokestackChargeTicks = 20;

	@Comment public static Comment campfires;
	@Entry public static boolean campfiresPushPlayers = true;
	@Entry public static float campfirePushMaxStrength = 24;
	@Entry public static float campfirePushBaseStrength = 10;

	public static FireworkUseBehaviour fireworkUseBehaviour() {
		return fireworkUseBehaviour;
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

	public static double cloudskipperSpeedMultiplier() {
		return cloudskipperSpeedMultiplier;
	}

	public static double cloudskipperCloudLevel() {
		return cloudskipperCloudLevel;
	}

	public static boolean smokestackAirRecharge() {
		return smokestackAirRecharge;
	}

	public static int smokestackChargeTicks() {
		return smokestackChargeTicks;
	}

	public static boolean campfiresPushPlayers() {
		return campfiresPushPlayers;
	}
	public static float campfirePushMaxStrength() {
		return campfirePushMaxStrength;
	}
	public static float campfirePushBaseStrength() {
		return campfirePushBaseStrength;
	}

	public static void init() {
		MidnightConfig.init(Aileron.MOD_ID, AileronConfig.class);
	}
}
