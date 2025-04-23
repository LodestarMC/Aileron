package com.lodestar.aileron.fabric.loot;

import com.lodestar.aileron.AileronEnchantments;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
public class AileronLootModifiersImpl {

    private static final ResourceLocation END_CITY_TREASURE_ID = ResourceLocation.withDefaultNamespace("chests/end_city_treasure");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registry) -> {
            if (END_CITY_TREASURE_ID.equals(key.registry())) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(EnchantRandomlyFunction.randomEnchantment().withEnchantment(registry.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(AileronEnchantments.CLOUDSKIPPER)))
                                .setWeight(2))
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(EnchantRandomlyFunction.randomEnchantment().withEnchantment(registry.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(AileronEnchantments.SMOKESTACK)))
                                .setWeight(2))
                        .add(LootItem.lootTableItem(Items.AIR)
                                .setWeight(6));

                tableBuilder.withPool(poolBuilder);
            }
        });
    }

    public static void register() {
        modifyLootTables();
    }
}
