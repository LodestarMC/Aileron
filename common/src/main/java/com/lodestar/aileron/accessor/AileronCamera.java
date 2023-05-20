package com.lodestar.aileron.accessor;

public interface AileronCamera {
    double getPreviousEMAValue();
    double getEMAValue();

    void setPreviousEMAValue(float previousEMA);
    void setEMAValue(float EMA);

    float getSmoothedEMADifference();
}
