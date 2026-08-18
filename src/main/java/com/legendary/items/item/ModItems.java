package com.legendary.items.item;

import com.legendary.items.LegendaryItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

	public static final Item EXCALIBUR_SWORD = register("excalibur_sword",
			key -> new ExcaliburSwordItem(ModToolMaterials.EXCALIBUR, new Item.Settings().registryKey(key).maxCount(1)));

	public static final Item THUNDER_AXE = register("thunder_axe",
			key -> new ThunderAxeItem(ModToolMaterials.THUNDER_AXE, 7.0f, -2.9f, new Item.Settings().registryKey(key).maxCount(1)));

	private static Item register(String name, java.util.function.Function<RegistryKey<Item>, Item> factory) {
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(LegendaryItems.MOD_ID, name));
		Item item = factory.apply(key);
		return Registry.register(Registries.ITEM, key, item);
	}

	public static void registerItems() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.add(EXCALIBUR_SWORD);
			entries.add(THUNDER_AXE);
		});
	}
}
