package com.lodestar.aileron.fabric;

import com.lodestar.aileron.Aileron;
import com.lodestar.aileron.CloudSkipperEnchantment;
import com.lodestar.aileron.SmokeStackEnchantment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AileronFabric implements ModInitializer {
    public static Enchantment SMOKESTACK = new SmokeStackEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[] { EquipmentSlot.CHEST });
    public static Enchantment CLOUDSKIPPER = new CloudSkipperEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[] { EquipmentSlot.CHEST });

    public static final ResourceLocation LAUNCH_SMOKE_STACK_PACKET_ID = new ResourceLocation(Aileron.MOD_ID, "launch_smoke_stack");
    public static final ResourceLocation SMOKE_STACK_DASH_PACKET_ID = new ResourceLocation(Aileron.MOD_ID, "dash_smoke_stack");

    public static final SimpleParticleType CUSTOM_CAMPFIRE_SMOKE = FabricParticleTypes.simple();

    @Override
    public void onInitialize() {
        Aileron.init();
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Aileron.MOD_ID, "custom_campfire_smoke"), CUSTOM_CAMPFIRE_SMOKE);
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(Aileron.MOD_ID, "smokestack"), SMOKESTACK);
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(Aileron.MOD_ID, "cloudskipper"), CLOUDSKIPPER);

        ServerPlayNetworking.registerGlobalReceiver(SMOKE_STACK_DASH_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            Aileron.playerDashedServer(player);
        });

        MidnightConfig.init(Aileron.MOD_ID, AileronFabricConfig.class);
    }
}
