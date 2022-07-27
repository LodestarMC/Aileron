package com.lodestar.aileron.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class AileronForgeConfig {

    public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC_SERVER;
    public static final ForgeConfigSpec SPEC_CLIENT;

    public static final ForgeConfigSpec.BooleanValue DO_CAMERA_ROLL;
    public static final ForgeConfigSpec.ConfigValue<Double> CAMERA_ROLL_SCALE;
    public static final ForgeConfigSpec.ConfigValue<Double> CAMERA_ROLL_SPEED;
    public static final ForgeConfigSpec.BooleanValue PUSH_PLAYERS;
    public static final ForgeConfigSpec.ConfigValue<Integer> SMOKE_STACK_CHARGE_TICKS;

    static {
        CLIENT_BUILDER.push("Aileron Forge Config");

        CLIENT_BUILDER.push("Camera Settings");

        DO_CAMERA_ROLL = CLIENT_BUILDER.comment("Enable camera roll").define("doCameraRoll", true);
        CAMERA_ROLL_SCALE = CLIENT_BUILDER.comment("Camera Roll Scale").define("cameraRollScale", 1.0);
        CAMERA_ROLL_SPEED = CLIENT_BUILDER.comment("Camera Roll Speed").define("cameraRollSpeed", 0.1);

        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.pop();
        SPEC_CLIENT = CLIENT_BUILDER.build();

        SERVER_BUILDER.push("Aileron Forge Config");

        SERVER_BUILDER.push("Campfires");

        PUSH_PLAYERS = SERVER_BUILDER.comment("Push players").define("campfiresPushPlayers", true);
        SMOKE_STACK_CHARGE_TICKS = SERVER_BUILDER.comment("Smoke Stack Charge Ticks").define("smokeStackChargeTicks", 20);

        SERVER_BUILDER.pop();

        SERVER_BUILDER.pop();

        SPEC_SERVER = SERVER_BUILDER.build();
    }



}
