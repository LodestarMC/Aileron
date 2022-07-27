package com.lodestar.aileron.quilt;

import com.lodestar.aileron.fabriclike.AileronFabricLike;
import com.lodestar.aileron.fabriclike.AileronFabricLikeClient;
import net.fabricmc.api.ClientModInitializer;

public class AileronModQuiltClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AileronFabricLikeClient.init();
    }
}
