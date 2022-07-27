package com.lodestar.aileron;

public interface ICameraEMA {
    double getPreviousEMAValue();
    double getEMAValue();

    void setPreviousEMAValue(float previousEMA);
    void setEMAValue(float EMA);

    float getSmoothedEMADifference();
}
