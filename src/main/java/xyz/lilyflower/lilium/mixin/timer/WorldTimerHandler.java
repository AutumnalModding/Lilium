package xyz.lilyflower.lilium.mixin.timer;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lilyflower.lilium.util.LiliumTimer;

@Mixin(ServerWorld.class)
public abstract class WorldTimerHandler implements LiliumTimer {
    @Shadow @Final private List<ServerPlayerEntity> players;
    @Unique private long damage_delay;
    @Unique private float damage_amount;
    @Unique private LivingEntity damage_target;
    @Unique private DamageSource damage_type;
    @Unique private BlockPos damage_position;

    @Unique private long explosion_delay;
    @Unique private ExplosionBehavior explosion_behaviour;
    @Unique private BlockPos explosion_location;
    @Unique private PlayerEntity explosion_player;
    @Unique private float explosion_power;
    @Unique private boolean explosion_fire;
    @Unique private World.ExplosionSourceType explosion_type;

    @Inject(method = "tick", at = @At("TAIL"))
    private void process(CallbackInfo ci) {
        if (--this.damage_delay == 0L && this.damage_target.getBlockPos() == this.damage_position) {
            damage_target.damage(damage_type, damage_amount);
        }

        if (--this.explosion_delay == 0L) {
            BlockPos pos = (this.explosion_player == null ? this.explosion_location : this.explosion_player.getBlockPos());
            ((World) (Object) this).createExplosion(null, null, this.explosion_behaviour, pos.getX(), pos.getY(), pos.getZ(), this.explosion_power, this.explosion_fire, this.explosion_type);
        }
    }

    @Override
    public void lilium$damage(long delay, LivingEntity target, DamageSource source, float amount) {
        this.damage_delay = delay;
        this.damage_target = target;
        this.damage_type = source;
        this.damage_amount = amount;
        this.damage_position = this.damage_target.getBlockPos();
    }

    @Override
    public void lilium$explosion(long delay, ExplosionBehavior behaviour, BlockPos location, float power, boolean fire, World.ExplosionSourceType type) {
        this.explosion_delay = delay;
        this.explosion_behaviour = behaviour;
        this.explosion_location = location;
        this.explosion_power = power;
        this.explosion_fire = fire;
        this.explosion_type = type;
    }

    @Override
    public void lilium$playerExplosion(long delay, ExplosionBehavior behaviour, PlayerEntity player, float power, boolean fire, World.ExplosionSourceType type) {
        this.explosion_delay = delay;
        this.explosion_behaviour = behaviour;
        this.explosion_player = player;
        this.explosion_power = power;
        this.explosion_fire = fire;
        this.explosion_type = type;
    }
}
