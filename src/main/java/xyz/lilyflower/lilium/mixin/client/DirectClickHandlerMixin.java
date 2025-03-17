package xyz.lilyflower.lilium.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.lilyflower.lilium.network.payload.DirectAttackPayload;
import xyz.lilyflower.lilium.util.DirectClickItem;

@Mixin(MinecraftClient.class)
public class DirectClickHandlerMixin {
    @Shadow
    public ClientPlayerEntity player;

    @Inject(at=@At("HEAD"), method="doAttack", cancellable=true)
    private void doAttack(CallbackInfoReturnable<Boolean> ci) {
        ItemCooldownManager manager = player.getItemCooldownManager();
        Item item = player.getMainHandStack().getItem();
        if (player != null && item instanceof DirectClickItem dci) {
            if (!manager.isCoolingDown(item) && dci.onDirectAttack(player, Hand.MAIN_HAND).isAccepted()) {
                ClientPlayNetworking.send(DirectAttackPayload.UNIT);
            }
            
            ci.setReturnValue(false);
            ci.cancel();
        }
    }
}
