package xyz.lilyflower.lilium.mixin.timer;

import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lilyflower.lilium.util.LiliumTimer;

@Mixin(PlayerEntity.class)
public abstract class PlayerTimerHandler implements LiliumTimer {
    @Shadow @Final private ItemCooldownManager itemCooldownManager;
    @Unique private long delay;
    @Unique private int cooldown;
    @Unique private Item item;

    @Inject(method = "tick", at = @At("TAIL"))
    private void process(CallbackInfo ci) {
        if (--this.delay == 0L) {
            this.itemCooldownManager.set(this.item, this.cooldown);
        }
    }

    @Override
    public void lilium$cooldown(long delay, int cooldown, Item item) {
        this.delay = delay;
        this.cooldown = cooldown;
        this.item = item;
    }
}
