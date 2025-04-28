package com.lodestar.aileron;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AileronTags {
    public static final TagKey<Item> ELYTRA = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, "elytra")
    );

    public static final TagKey<Block> SMOKE_PASSABLE = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, "smoke_passable")
    );

    public static final TagKey<Block> ELYTRA_FLIGHT_PASSABLE = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, "elytra_flight_passable")
    );
}
