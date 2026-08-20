package com.legendary.items.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ExcaliburSwordItem extends Item {

    private static final int INVINCIBILITY_DURATION_TICKS = 5 * 20;
    private static final int COOLDOWN_TICKS = 60 * 20; // 60 sekund

    public ExcaliburSwordItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.getItemCooldownManager().isCoolingDown(stack)) {
            if (!world.isClient()) {
                float progress = user.getItemCooldownManager().getCooldownProgress(stack.getItem(), 0f);
                int remainingTicks = Math.round(progress * COOLDOWN_TICKS);
                int remainingSeconds = (remainingTicks / 20) + 1;
                user.sendMessage(Text.literal("Twoja umiejętność jeszcze się ładuje: " + remainingSeconds + "s"), false);
            }
            return ActionResult.FAIL;
        }

        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, INVINCIBILITY_DURATION_TICKS, 4, false, true, true));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, INVINCIBILITY_DURATION_TICKS, 0, false, true, true));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, INVINCIBILITY_DURATION_TICKS, 3, false, true, true));

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS, 1.2f, 1.0f);

            if (world instanceof net.minecraft.server.world.ServerWorld serverWorld) {
                serverWorld.spawnParticles(net.minecraft.particle.ParticleTypes.END_ROD,
                        user.getX(), user.getY() + 1.0, user.getZ(),
                        40, 0.5, 1.0, 0.5, 0.05);
            }
        }

        user.getItemCooldownManager().set(stack, COOLDOWN_TICKS);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_RETURN, SoundCategory.PLAYERS, 0.6f, 1.4f);

        return ActionResult.SUCCESS;
    }
}
