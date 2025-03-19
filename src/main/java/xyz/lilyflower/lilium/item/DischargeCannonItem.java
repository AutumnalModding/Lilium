package xyz.lilyflower.lilium.item;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.util.DirectClickItem;
import xyz.lilyflower.lilium.util.LiliumTimer;
import xyz.lilyflower.lilium.util.registry.sound.GenericSounds;

public class DischargeCannonItem extends Item implements DirectClickItem {
    public static final ComponentType<Double> CHARGE_LEVEL = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannon_charge_level"),
            ComponentType.<Double>builder().codec(Codec.DOUBLE).build()
    );

    public static final ComponentType<Integer> OVERCHARGE_TICKS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannon_overcharge_ticks"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public DischargeCannonItem() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult onDirectAttack(PlayerEntity player, Hand hand) {
        if (player.getWorld().isClient) return ActionResult.SUCCESS;

        ItemStack stack = player.getMainHandStack();

        double charge = stack.getOrDefault(CHARGE_LEVEL, 0D);
        if (charge < 0.66F) return ActionResult.FAIL;
        stack.set(OVERCHARGE_TICKS, 0);
        stack.set(CHARGE_LEVEL, 0D);

        player.getWorld().playSound(
                null,
                player.getBlockPos(),
                GenericSounds.DISCHARGE_CANNON_FIRE,
                SoundCategory.PLAYERS,
                1f,
                1f
        );

        World world = player.getWorld();
        ExplosionBehavior behavior = new AdvancedExplosionBehavior(true, false, Optional.of((float) (2.5D * charge)), Optional.empty());
        DamageSource source = new DamageSource(player.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(Lilium.RAILGUN_DAMAGE_TYPE));

        ((LiliumTimer) world).lilium$explosion_player(30L, behavior, player, 4F, false, World.ExplosionSourceType.TRIGGER);
        ((LiliumTimer) world).lilium$damage_raycast(30L, player, 200.0D, source, (float) (20.0D * charge));

        return ActionResult.CONSUME;
    }

    @Override
    public ActionResult onDirectUse(PlayerEntity player, Hand hand) {
        if (player.getWorld().isClient) return ActionResult.SUCCESS;

        ItemStack stack = player.getMainHandStack();

        double charge = stack.getOrDefault(CHARGE_LEVEL, 0D);
        double target;

        if (charge >= 1.0D) {
            double coefficient = 0.0825F;
            int ticks = stack.getOrDefault(OVERCHARGE_TICKS, 0);
            stack.set(OVERCHARGE_TICKS, ++ticks);
            double log = Math.log(ticks == 0 ? 1 : ticks);
            target = 1.0D + (coefficient * log);
            if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                Lilium.LOGGER.info("Charge Target {}, Damage {}, Use Time: {} seconds ({} ticks)", target, 20.0D * target, ticks / 20, ticks);
            }

        } else {
            target = charge + 0.001666666666666667F;
        }

        if (Double.compare(target, 1.0D) == 0) {
            player.getWorld().playSound(
                    null,
                    player.getBlockPos(),
                    GenericSounds.DISCHARGE_CANNON_CHARGED,
                    SoundCategory.PLAYERS,
                    1f,
                    1f
            );
        }

        stack.set(CHARGE_LEVEL, target);
        return ActionResult.CONSUME;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
