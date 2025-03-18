package xyz.lilyflower.lilium.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lilyflower.lilium.util.LiliumTimer;

@Mixin(ServerWorld.class)
public abstract class EntityDamageTimerHandler implements LiliumTimer {
    @Unique private long delay;
    @Unique private float amount;
    @Unique private LivingEntity target;
    @Unique private DamageSource source;
    @Unique private BlockPos position;

    @Inject(method = "tick", at = @At("TAIL"))
    private void process(CallbackInfo ci) {
        if (--this.delay == 0L && this.target.getBlockPos() == this.position) {
            target.damage(source, amount);
        }
    }

    @Override
    public void damage(long delay, LivingEntity target, DamageSource source, float amount) {
        this.delay = delay;
        this.target = target;
        this.source = source;
        this.amount = amount;
        this.position = this.target.getBlockPos();
    }
}
