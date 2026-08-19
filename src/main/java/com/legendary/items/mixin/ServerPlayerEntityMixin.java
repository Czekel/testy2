package com.legendary.items.mixin;

import com.legendary.items.PardonedPlayers;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void legendaryitems$onCopyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        if (!alive && PardonedPlayers.isPardoned(oldPlayer.getUuid())) {
            ServerPlayerEntity newPlayer = (ServerPlayerEntity) (Object) this;
            newPlayer.getInventory().clone(oldPlayer.getInventory());
            PardonedPlayers.clearPardoned(oldPlayer.getUuid());
        }
    }
}
