package com.lodestar.aileron.accessor;

public interface AileronPlayer {
    boolean charged();
    int getBoostTicks();
    void setBoostTicks(int boostTicks);
    void setSmokeTrailTicks(int boostTicks);

    int getCampfireDamageIFrames();
    void setCampfireDamageIFrames(int campfireDamageIFrames);
}
