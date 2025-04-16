package com.lodestar.aileron.fabric.loot;

import com.lodestar.aileron.AileronEnchantments;
import com.lodestar.aileron.fabric.AileronEnchantmentsImpl;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public class AileronLootModifiers {

    private static final ResourceLocation END_CITY_TREASURE_ID = new ResourceLocation("chests/end_city_treasure");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (END_CITY_TREASURE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(EnchantRandomlyFunction.randomEnchantment().withEnchantment(AileronEnchantmentsImpl.CLOUDSKIPPER))
                                .setWeight(2))
                        .add(LootItem.lootTableItem(Items.BOOK)
                                .apply(EnchantRandomlyFunction.randomEnchantment().withEnchantment(AileronEnchantmentsImpl.SMOKESTACK))
                                .setWeight(2))
                        .add(LootItem.lootTableItem(Items.AIR)
                                .setWeight(6));

                tableBuilder.withPool(poolBuilder);
            }
        });
    }
}
