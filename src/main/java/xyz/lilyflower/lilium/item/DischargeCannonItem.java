package xyz.lilyflower.lilium.item;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.util.DirectClickItem;
import xyz.lilyflower.lilium.util.LiliumTimer;

public class DischargeCannonItem extends Item implements DirectClickItem {
    public static final ComponentType<Float> CHARGE_LEVEL = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannon_charge_level"),
            ComponentType.<Float>builder().codec(Codec.FLOAT).build()
    );

    public static final ComponentType<Boolean> COOLING_DOWN = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannon_cooldown_active"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Integer> COOLDOWN_TICKS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannon_cooldown_ticks"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> CHARGE_TICKS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannon_charge_ticks"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public DischargeCannonItem() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult onDirectAttack(PlayerEntity player, Hand hand) {
        if (player.getWorld().isClient) return ActionResult.SUCCESS;

        ItemStack stack = player.getMainHandStack();

        float charge = stack.getOrDefault(CHARGE_LEVEL, 0F);
        if (charge < 0.66F) return ActionResult.FAIL;
        if (stack.getOrDefault(COOLING_DOWN, false)) return ActionResult.FAIL;
        stack.set(COOLING_DOWN, true);
        stack.set(CHARGE_TICKS, 0);
        stack.set(CHARGE_LEVEL, 0F);
        stack.set(COOLDOWN_TICKS, 30 + (int) (150 * charge));

        ((LiliumTimer) player).lilium$cooldown(30L, (int) (150 * charge), this);
        player.getWorld().playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                player.getBlockPos(), // The position of where the sound will come from
                Lilium.DISCHARGE, // The sound that will play
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                1f, // Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );

        ExplosionBehavior behavior = new AdvancedExplosionBehavior(true, false, Optional.of(2.5F * charge), Optional.empty());
        World world = player.getWorld();
        ((LiliumTimer) world).lilium$playerExplosion(30L, behavior, player, 4F, false, World.ExplosionSourceType.TRIGGER);

        double distance = 200.0F;
        Vec3d start = player.getEyePos();
        Vec3d end = player.getEyePos().add(player.getRotationVector().multiply(distance));

        HitResult result = ProjectileUtil.raycast(player, start, end, new Box(start, end), entity -> entity instanceof LivingEntity, distance*distance);
        if (result instanceof EntityHitResult ehr) {
            LivingEntity entity = (LivingEntity) ehr.getEntity();

            if (!entity.isInvulnerable() && !entity.isInCreativeMode()) {
                DamageSource source = new DamageSource(player.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(Lilium.RAILGUN_DAMAGE_TYPE));
                ServerWorld there = (ServerWorld) player.getWorld();
                ((LiliumTimer) there).lilium$damage(30L, entity, source, 20.0F * charge);

            }
        }

        return ActionResult.CONSUME;
    }

    @Override
    public ActionResult onDirectUse(PlayerEntity player, Hand hand) {
        if (player.getWorld().isClient) return ActionResult.SUCCESS;

        ItemStack stack = player.getMainHandStack();

        int ticks = stack.getOrDefault(CHARGE_TICKS, 0);
        float charge = stack.getOrDefault(CHARGE_LEVEL, 0F);
        float coefficient = 0.185F;
        double log = Math.log(ticks == 0 ? 1 : ticks);

        stack.set(CHARGE_TICKS, ++ticks);

        float target;
        if (charge >= 1.0F) {
            target = (float) (coefficient * log);
        } else {
            target = charge + 0.001666666666666667F;
        }

        stack.set(CHARGE_LEVEL, target);

        return ActionResult.CONSUME;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;

        if (stack.getOrDefault(COOLING_DOWN, false)) {
            int cooldown = stack.getOrDefault(COOLDOWN_TICKS, 0);
            stack.set(COOLDOWN_TICKS, --cooldown);
            if (cooldown <= 0) {
                stack.set(COOLING_DOWN, false);
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
