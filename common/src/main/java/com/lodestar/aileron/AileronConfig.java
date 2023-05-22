package com.lodestar.aileron;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class AileronConfig {
	@ExpectPlatform
	public static boolean fireworkChanges() {
		return false;
	}

	@ExpectPlatform
	public static boolean doCameraRoll() {
		return false;
	}

	@ExpectPlatform
	public static double cameraRollScale() {
		return 0.0;
	}

	@ExpectPlatform
	public static double cameraRollSpeed() {
		return 0.0;
	}

	@ExpectPlatform
	public static boolean campfiresPushPlayers() {
		return false;
	}

	@ExpectPlatform
	public static int smokeStackChargeTicks() {
		return 0;
	}

	@ExpectPlatform
	public static void init() {
	}
}
