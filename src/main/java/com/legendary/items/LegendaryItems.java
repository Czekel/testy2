package com.legendary.items;

import com.legendary.items.entity.ModEntities;
import com.legendary.items.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegendaryItems implements ModInitializer {

	public static final String MOD_ID = "legendaryitems";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("[LegendaryItems] Ladowanie Excalibura i Boskiego Topora...");

		ModItems.registerItems();
		ModEntities.registerEntities();
	}
}
