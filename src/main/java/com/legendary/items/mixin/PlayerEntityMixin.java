package com.legendary.items.mixin;

import com.legendary.items.PardonedPlayers;
import com.legendary.items.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    private void legendaryitems$onDropInventory(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        for (Hand hand : Hand.values()) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() == ModItems.TOTEM_OF_PARDON) {
                stack.decrement(1);
                PardonedPlayers.markPardoned(player.getUuid());
                ci.cancel();
                return;
            }
        }
    }
}
