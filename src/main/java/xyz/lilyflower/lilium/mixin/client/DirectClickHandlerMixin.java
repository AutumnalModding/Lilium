package xyz.lilyflower.lilium.mixin.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.lilyflower.lilium.network.payload.DirectAttackPayload;
import xyz.lilyflower.lilium.network.payload.DirectUsePayload;
import xyz.lilyflower.lilium.util.DirectAttackItem;
import xyz.lilyflower.lilium.util.DirectUseItem;

@Mixin(MinecraftClient.class)
public class DirectClickHandlerMixin {
    @Shadow
    public ClientPlayerEntity player;

    @Inject(at=@At("HEAD"), method="doAttack", cancellable=true)
    private void doAttack(CallbackInfoReturnable<Boolean> ci) {
        ItemCooldownManager manager = player.getItemCooldownManager();
        Item item = player.getMainHandStack().getItem();
        if (player != null && item instanceof DirectAttackItem dci) {
            if (!manager.isCoolingDown(item) && dci.onDirectAttack(player, Hand.MAIN_HAND).isAccepted()) {
                ClientPlayNetworking.send(DirectAttackPayload.UNIT);
            }
            
            ci.setReturnValue(false);
            ci.cancel();
        }
    }

    @Inject(at=@At("HEAD"), method="doItemUse", cancellable=true)
    private void doItemUse(CallbackInfo ci) {
        ItemCooldownManager manager = player.getItemCooldownManager();
        Item item = player.getMainHandStack().getItem();
        if (player != null && item instanceof DirectUseItem dci) {
            if (!manager.isCoolingDown(item) && dci.onDirectUse(player, Hand.MAIN_HAND).isAccepted()) {
                ClientPlayNetworking.send(DirectUsePayload.UNIT);
            }

            ci.cancel();
        }
    }

    @Inject(at=@At("HEAD"), method="handleBlockBreaking", cancellable=true)
    private void handleBlockBreaking(boolean bl, CallbackInfo ci) {
        if (player != null && player.getMainHandStack().getItem() instanceof DirectAttackItem) {
            MinecraftClient self = (MinecraftClient)(Object)this;
            if (self.crosshairTarget instanceof BlockHitResult bhr) {
                var i = player.getMainHandStack().getItem();
                if (!i.canMine(self.world.getBlockState(bhr.getBlockPos()), self.world, bhr.getBlockPos(), player)) {
                    ci.cancel();
                }
            }
        }
    }
}
