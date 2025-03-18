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
            Identifier.of("lilium", "discharge_cannnon_charge_level"),
            ComponentType.<Float>builder().codec(Codec.FLOAT).build()
    );

    public static final ComponentType<Boolean> COOLING_DOWN = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannon_cooldown_active"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final ComponentType<Integer> COOLDOWN_TICKS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannnon_cooldown_ticks"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static final ComponentType<Integer> CHARGE_TICKS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannnon_charge_ticks"),
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
        if (charge < 0.33F) return ActionResult.FAIL;
        if (stack.getOrDefault(COOLING_DOWN, false)) return ActionResult.FAIL;
        stack.set(COOLING_DOWN, true);
        stack.set(CHARGE_TICKS, 0);
        stack.set(CHARGE_LEVEL, 0F);
        stack.set(COOLDOWN_TICKS, 30 + (int) (150 * charge));

        ((LiliumTimer) player).cooldown(30L, (int) (150 * charge), this);
        player.getWorld().playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                player.getBlockPos(), // The position of where the sound will come from
                Lilium.DISCHARGE, // The sound that will play
                SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                1f, // Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );

        ExplosionBehavior behavior = new AdvancedExplosionBehavior(true, false, Optional.of(2F * charge), Optional.empty());
        World world = player.getWorld();
        BlockPos pos = player.getBlockPos();
        world.createExplosion(null, null, behavior, pos.getX(), pos.getY(), pos.getZ(), 4F, false, World.ExplosionSourceType.TRIGGER);

        double distance = 200.0F;
        Vec3d start = player.getEyePos();
        Vec3d end = player.getEyePos().add(player.getRotationVector().multiply(distance));

        HitResult result = ProjectileUtil.raycast(player, start, end, new Box(start, end), entity -> entity instanceof LivingEntity, distance*distance);
        if (result instanceof EntityHitResult ehr) {
            LivingEntity entity = (LivingEntity) ehr.getEntity();

            if (!entity.isInvulnerable() && !entity.isInCreativeMode()) {
                DamageSource source = new DamageSource(player.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(Lilium.RAILGUN_DAMAGE_TYPE));
                ServerWorld there = (ServerWorld) player.getWorld();
                ((LiliumTimer) there).damage(30L, entity, source, 20.0F * charge);

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

        // Algo 1:
//        int max = 40 * 2;
//        int halfway = 20 * 60;
//
//
//        double log = Math.log(ticks == 0 ? 1 : ticks);
//
//        double charge = stack.getOrDefault(CHARGE_LEVEL, 0F);
//        double target;
//        if (charge > halfway) {
//            target = coefficient * log;
//        } else {
//            target = max / (1 + Math.pow(Math.E, -ticks));
//        }
//
//        double damage = max / (1 + Math.exp(-ticks));
//
//        stack.set(CHARGE_TICKS, ++ticks);
//
//        Lilium.LOGGER.info("Charge Target {}, Damage {}, Use Time: {} seconds ({} ticks)", target, damage, ticks / 20, ticks);
//        stack.set(CHARGE_LEVEL, (float) target);

        // Algo 2:
//        float coefficient = 0.185F;
//
//        double log = Math.log(ticks == 0 ? 1 : ticks);
//        double target = coefficient * log;
//        stack.set(CHARGE_TICKS, ++ticks);
//
//        Lilium.LOGGER.info("Charge Target {}, Damage {}, Use Time: {} seconds ({} ticks)", target, 20.0F * target, ticks / 20, ticks);
//
//        stack.set(CHARGE_LEVEL, (float) target);

        return ActionResult.CONSUME;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
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
