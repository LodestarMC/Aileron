package com.lodestar.aileron;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AileronTags {
    public static final TagKey<Item> ELYTRA = TagKey.create(
            Registries.ITEM,
            new ResourceLocation(Aileron.MOD_ID, "elytra")
    );
}
