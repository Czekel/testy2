package com.legendary.items.entity;

import com.legendary.items.LegendaryItems;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

	public static final EntityType<ThunderAxeEntity> THUNDER_AXE_ENTITY = register(
			"thunder_axe_entity",
			EntityType.Builder.<ThunderAxeEntity>create(ThunderAxeEntity::new, SpawnGroup.MISC)
					.dimensions(EntityDimensions.fixed(0.4f, 0.4f))
					.maxTrackingRange(4)
					.trackingTickInterval(10)
	);

	private static <T extends net.minecraft.entity.Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(LegendaryItems.MOD_ID, name));
		return Registry.register(Registries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void registerEntities() {
		// Statyczna inicjalizacja pol powyzej wystarczy, ta metoda tylko wymusza zaladowanie klasy.
	}
}
