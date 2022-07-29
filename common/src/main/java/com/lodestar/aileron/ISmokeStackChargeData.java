package com.lodestar.aileron;

public interface ISmokeStackChargeData {
    boolean charged();
    int getBoostTicks();
    void setBoostTicks(int boostTicks);
    void setSmokeTrailTicks(int boostTicks);

    int getCampfireDamageIFrames();
    void setCampfireDamageIFrames(int campfireDamageIFrames);
}
