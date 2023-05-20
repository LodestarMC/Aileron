package com.lodestar.aileron.fabric;

import static com.lodestar.aileron.fabric.MidnightConfig.*;

public class AileronFabricConfig {

    @Comment public static Comment generalChanges;
    @Entry public static boolean fireworkChanges = true;

    @Comment public static Comment cameraSettings;
    @Entry public static boolean doCameraRoll = true;
    @Entry(min=0.0,max=2.0) public static double cameraRollScale = 1.0;
    @Entry(min=0.05,max=1.0) public static double cameraRollSpeed = 0.1;

    @Comment public static Comment campfires;
    @Entry public static boolean campfiresPushPlayers = true;
    @Entry(min=2) public static int smokeStackChargeTicks = 20;
}
