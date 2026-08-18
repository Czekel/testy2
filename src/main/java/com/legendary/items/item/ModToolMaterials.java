package com.legendary.items.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

public class ModToolMaterials {

	public static final ToolMaterial EXCALIBUR = new ToolMaterial(
			BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
			3000,
			9.0f,
			5.0f,
			25,
			ItemTags.REPAIRS_NETHERITE_ARMOR
	);

	public static final ToolMaterial THUNDER_AXE = new ToolMaterial(
			BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
			1800,
			8.0f,
			6.0f,
			18,
			ItemTags.REPAIRS_DIAMOND_ARMOR
	);
}
