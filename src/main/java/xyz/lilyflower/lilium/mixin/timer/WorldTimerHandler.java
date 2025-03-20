package xyz.lilyflower.lilium.mixin.timer;

import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.lilyflower.lilium.util.LiliumTimer;

@Mixin(ServerWorld.class)
public abstract class WorldTimerHandler implements LiliumTimer {
    @Unique private long damage_delay;
    @Unique private float damage_amount;
    @Unique private LivingEntity damage_target;
    @Unique private PlayerEntity damage_raycast_player;
    @Unique private double damage_raycast_distance;
    @Unique private DamageSource damage_type;
    @Unique private BlockPos damage_position;

    @Unique private long explosion_delay;
    @Unique private ExplosionBehavior explosion_behaviour;
    @Unique private BlockPos explosion_location;
    @Unique private PlayerEntity explosion_player;
    @Unique private float explosion_power;
    @Unique private boolean explosion_fire;
    @Unique private World.ExplosionSourceType explosion_type;

    @Unique private long component_modification_delay;
    @Unique private ComponentType component_modification_type;
    @Unique private Object component_modification_value;
    @Unique private ItemStack component_modification_stack;

    @Inject(method = "tick", at = @At("TAIL"))
    private void process(CallbackInfo ci) {
        if (--this.damage_delay == 0L) {
            if (this.damage_raycast_player != null) {
                double distance = this.damage_raycast_distance;
                PlayerEntity player = this.damage_raycast_player;

                Vec3d start = player.getEyePos();
                Vec3d end = player.getEyePos().add(player.getRotationVector().multiply(distance));

                HitResult result = ProjectileUtil.raycast(player, start, end, new Box(start, end), entity -> entity instanceof LivingEntity, distance*distance);
                if (result instanceof EntityHitResult ehr) {
                    LivingEntity entity = (LivingEntity) ehr.getEntity();

                    if (!entity.isInvulnerable() && !entity.isInCreativeMode()) {
                        entity.damage(this.damage_type, this.damage_amount);
                    }
                }
            } else if (this.damage_target != null && this.damage_position != null && this.damage_target.getBlockPos() == this.damage_position) {
                this.damage_target.damage(this.damage_type, this.damage_amount);
            } else if (this.damage_target != null && this.damage_position == null) {
                this.damage_target.damage(this.damage_type, this.damage_amount);
            }
        }

        if (--this.explosion_delay == 0L) {
            BlockPos pos = (this.explosion_player == null ? this.explosion_location : this.explosion_player.getBlockPos());
            ((World) (Object) this).createExplosion(null, null, this.explosion_behaviour, pos.getX(), pos.getY(), pos.getZ(), this.explosion_power, this.explosion_fire, this.explosion_type);
        }

        if (--this.component_modification_delay == 0L) {
            this.component_modification_stack.set(this.component_modification_type, this.component_modification_value);
        }
    }

    @Override
    public void lilium$damage_entity(long delay, LivingEntity target, DamageSource source, float amount) {
        this.damage_delay = delay;
        this.damage_target = target;
        this.damage_type = source;
        this.damage_amount = amount;
        this.damage_position = this.damage_target.getBlockPos();
    }

    @Override
    public void lilium$damage_raycast(long delay, PlayerEntity source, double distance, DamageSource type, float amount) {
        this.damage_delay = delay;
        this.damage_raycast_player = source;
        this.damage_raycast_distance = distance;
        this.damage_type = type;
        this.damage_amount = amount;
    }

    @Override
    public void lilium$explode_at_position(long delay, ExplosionBehavior behaviour, BlockPos location, float power, boolean fire, World.ExplosionSourceType type) {
        this.explosion_delay = delay;
        this.explosion_behaviour = behaviour;
        this.explosion_location = location;
        this.explosion_power = power;
        this.explosion_fire = fire;
        this.explosion_type = type;
    }

    @Override
    public void lilium$explode_at_player(long delay, ExplosionBehavior behaviour, PlayerEntity player, float power, boolean fire, World.ExplosionSourceType type) {
        this.explosion_delay = delay;
        this.explosion_behaviour = behaviour;
        this.explosion_player = player;
        this.explosion_power = power;
        this.explosion_fire = fire;
        this.explosion_type = type;
    }

    @Override
    public void lilium$modify_component(long delay, ComponentType<?> type, Object value, ItemStack stack) {
        this.component_modification_delay = delay;
        this.component_modification_type = type;
        this.component_modification_value = value;
        this.component_modification_stack = stack;
    }
}
