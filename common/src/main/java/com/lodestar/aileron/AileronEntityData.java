package com.lodestar.aileron;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public class AileronEntityData {
    public static final EntityDataAccessor<Integer> SMOKE_STACK_CHARGES = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
}
