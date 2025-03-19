package xyz.lilyflower.lilium.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;

public interface LiliumTimer {
    void lilium$damage(long delay, LivingEntity target, DamageSource source, float amount);
    void lilium$damage_raycast(long delay, PlayerEntity source, double distance, DamageSource type, float amount);
    void lilium$cooldown(long delay, int cooldown, Item item);
    void lilium$explosion(long delay, ExplosionBehavior behaviour, BlockPos location, float power, boolean fire, World.ExplosionSourceType type);
    void lilium$explosion_player(long delay, ExplosionBehavior behaviour, PlayerEntity player, float power, boolean fire, World.ExplosionSourceType type);
}
