package com.lodestar.aileron;

public interface ISmokeStackChargeData {
    boolean getStartFlyingNextTick();
    boolean charged();
    int getBoostTicks();
    void setBoostTicks(int boostTicks);
    void setSmokeTrailTicks(int boostTicks);
}
