package com.lodestar.aileron.accessor;

public interface AileronCamera {
	double getPreviousEMAValue();

	void setPreviousEMAValue(float previousEMA);

	double getEMAValue();

	void setEMAValue(float EMA);

	float getSmoothedEMADifference();
}
