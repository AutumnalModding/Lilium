package xyz.lilyflower.lilium.util;

import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

@SuppressWarnings("unused")
public interface LiliumTimer {
    void lilium$damage_entity(long delay, LivingEntity target, DamageSource source, float amount);
    void lilium$damage_raycast(long delay, PlayerEntity source, double distance, DamageSource type, float amount);
    void lilium$cooldown_item(long delay, int cooldown, Item item);
    void lilium$explode_at_position(long delay, ExplosionBehavior behaviour, BlockPos location, float power, boolean fire, World.ExplosionSourceType type);
    void lilium$explode_at_player(long delay, ExplosionBehavior behaviour, PlayerEntity player, float power, boolean fire, World.ExplosionSourceType type);
    void lilium$modify_component(long delay, ComponentType<?> type, Object value, ItemStack stack);
    void lilium$apply_velocity(long delay, Entity target, Vec3d velocity);
    void lilium$apply_look_velocity(long delay, Entity target, double power);
}
