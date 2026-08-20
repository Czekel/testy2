package com.legendary.items.item;

import com.legendary.items.LegendaryItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item EXCALIBUR_SWORD = register("excalibur_sword",
            key -> new ExcaliburSwordItem(new Item.Settings()
                    .registryKey(key)
                    .maxCount(1)
                    .maxDamage(2000)
                    .attributeModifiers(
                            AttributeModifiersComponent.builder()
                                    .add(EntityAttributes.ATTACK_DAMAGE,
                                            new EntityAttributeModifier(
                                                    Identifier.of(LegendaryItems.MOD_ID, "excalibur_attack_damage"),
                                                    7.0,
                                                    EntityAttributeModifier.Operation.ADD_VALUE),
                                            AttributeModifierSlot.MAINHAND)
                                    .add(EntityAttributes.ATTACK_SPEED,
                                            new EntityAttributeModifier(
                                                    Identifier.of(LegendaryItems.MOD_ID, "excalibur_attack_speed"),
                                                    -2.4,
                                                    EntityAttributeModifier.Operation.ADD_VALUE),
                                            AttributeModifierSlot.MAINHAND)
                                    .build()
                    )));

    public static final Item THUNDER_AXE = register("thunder_axe",
            key -> new ThunderAxeItem(new Item.Settings().registryKey(key).maxCount(1).maxDamage(1500)));

    public static final Item TOTEM_OF_PARDON = register("totem_of_pardon",
            key -> new TotemOfPardonItem(new Item.Settings().registryKey(key).maxCount(1)));

    private static Item register(String name, java.util.function.Function<RegistryKey<Item>, Item> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(LegendaryItems.MOD_ID, name));
        Item item = factory.apply(key);
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(EXCALIBUR_SWORD);
            entries.add(THUNDER_AXE);
            entries.add(TOTEM_OF_PARDON);
        });
    }
}
