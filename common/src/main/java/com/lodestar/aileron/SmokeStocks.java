package com.lodestar.aileron;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public class SmokeStocks {
    public static final EntityDataAccessor<Integer> DATA_SMOKE_STOCKS = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
}
