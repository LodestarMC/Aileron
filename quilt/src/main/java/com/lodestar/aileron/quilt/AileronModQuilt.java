package com.lodestar.aileron.quilt;

import com.lodestar.aileron.fabriclike.AileronFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class AileronModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        AileronFabricLike.init();
    }
}
