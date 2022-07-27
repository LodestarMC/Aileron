package com.lodestar.aileron.forge;

import com.lodestar.aileron.Aileron;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod(Aileron.MOD_ID)
public class AileronForge {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Aileron.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static int packetID = 0;

    public AileronForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        Aileron.init();
        AileronForgeParticles.register(bus);
        AileronForgeEnchantments.register(bus);

        CHANNEL.registerMessage(packetID++, SmokeStockLaunchPacketForge.class, SmokeStockLaunchPacketForge::encode, SmokeStockLaunchPacketForge::new, SmokeStockLaunchPacketForge::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(packetID++, SmokeStockDashPacketForge.class, SmokeStockDashPacketForge::encode, SmokeStockDashPacketForge::new, SmokeStockDashPacketForge::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AileronForgeConfig.SPEC_SERVER, "aileron-server-config.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AileronForgeConfig.SPEC_CLIENT, "aileron-client-config.toml");
    }
}
