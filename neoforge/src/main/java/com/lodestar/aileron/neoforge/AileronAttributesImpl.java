package com.lodestar.aileron.neoforge;



import com.lodestar.aileron.Aileron;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AileronAttributesImpl {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
            BuiltInRegistries.ATTRIBUTE, Aileron.MOD_ID);

    public static Holder<Attribute> registerAttribute(String name, Attribute attribute) {
        return ATTRIBUTES.register(name, () -> attribute);
    }

    public static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
    }

    public static void register() {
        ATTRIBUTES.register(AileronImpl.modEventBus);
    }
}
