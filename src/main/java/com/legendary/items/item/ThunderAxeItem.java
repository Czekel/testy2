package com.legendary.items.item;

import com.legendary.items.entity.ThunderAxeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ThunderAxeItem extends Item {

    private static final int THROW_COOLDOWN_TICKS = 1200; // 60 sekund

    public ThunderAxeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            if (!world.isClient()) {
                float progress = user.getItemCooldownManager().getCooldownProgress(stack.getItem(), 0f);
                int remainingTicks = Math.round(progress * THROW_COOLDOWN_TICKS);
                int remainingSeconds = (remainingTicks / 20) + 1;
                user.sendMessage(Text.literal("Twoja umiejętność jeszcze się ładuje: " + remainingSeconds + "s"), false);
            }
            return ActionResult.FAIL;
        }

        if (!world.isClient()) {
            ThunderAxeEntity axeEntity = new ThunderAxeEntity(world, user);
            axeEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2.4f, 1.0f);
            world.spawnEntity(axeEntity);

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        user.getItemCooldownManager().set(stack, THROW_COOLDOWN_TICKS);
        user.incrementStat(net.minecraft.stat.Stats.USED.getOrCreateStat(this));

        return ActionResult.SUCCESS;
    }
}
