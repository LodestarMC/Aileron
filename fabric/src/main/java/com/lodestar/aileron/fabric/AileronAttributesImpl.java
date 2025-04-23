package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

public class AileronAttributesImpl {
    public static Holder<Attribute> registerAttribute(String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, ResourceLocation.fromNamespaceAndPath(Aileron.MOD_ID, name), attribute);
    }

    public static void register() {
    }
}
