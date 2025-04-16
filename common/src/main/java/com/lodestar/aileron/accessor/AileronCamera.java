package com.lodestar.aileron.accessor;

import net.minecraft.world.entity.Entity;

public interface AileronCamera {
	double getPreviousEMAValue();

	void setPreviousEMAValue(float previousEMA);

	double getEMAValue();

	void setEMAValue(float EMA);

	float getSmoothedEMADifference(Entity entity, float partial);
}
