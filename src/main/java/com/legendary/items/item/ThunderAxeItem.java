package com.legendary.items.item;

import com.legendary.items.entity.ThunderAxeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ThunderAxeItem extends AxeItem {

	private static final int THROW_COOLDOWN_TICKS = 15;

	public ThunderAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		if (user.getItemCooldownManager().isCoolingDown(this)) {
			return TypedActionResult.fail(stack);
		}

		if (!world.isClient) {
			ThunderAxeEntity axeEntity = new ThunderAxeEntity(world, user);
			axeEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2.4f, 1.0f);
			world.spawnEntity(axeEntity);

			world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory
