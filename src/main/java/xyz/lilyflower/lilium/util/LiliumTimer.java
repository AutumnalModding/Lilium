package xyz.lilyflower.lilium.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;

public interface LiliumTimer {
    void damage(long delay, LivingEntity target, DamageSource source, float amount);
    void cooldown(long delay, int cooldown, Item item);
}
