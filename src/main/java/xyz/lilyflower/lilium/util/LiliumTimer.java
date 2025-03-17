package xyz.lilyflower.lilium.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public interface LiliumTimer {
    void delayEntityDamage(long ticks, LivingEntity target, DamageSource source, float amount);
}
