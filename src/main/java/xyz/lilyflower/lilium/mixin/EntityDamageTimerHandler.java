package xyz.lilyflower.lilium.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lilyflower.lilium.util.LiliumTimer;

@Mixin(ServerWorld.class)
public class EntityDamageTimerHandler implements LiliumTimer {
    @Unique private long delay;
    @Unique private float amount;
    @Unique private LivingEntity target;
    @Unique private DamageSource source;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) { // Fix parameters as needed
        if (--this.delay == 0L) {
            target.damage(source, amount);
        }
    }

    @Override
    public void delayEntityDamage(long ticks, LivingEntity target, DamageSource source, float amount) {
        this.delay = ticks;
        this.target = target;
        this.source = source;
        this.amount = amount;
    }
}
