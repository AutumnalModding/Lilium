package xyz.lilyflower.lilium.item;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.util.DirectClickItem;
import xyz.lilyflower.lilium.util.LiliumTimer;
import xyz.lilyflower.lilium.util.registry.sound.GenericSounds;

public class DischargeCannonItem extends Item implements DirectClickItem {

    protected static final int RED = 0xFFFF0000;
    protected static final int GREEN = 0xFF00FF00;

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
        World world = player.getWorld();

        double charge = stack.getOrDefault(CHARGE_LEVEL, 0D);
        if (charge < 0.66F) return ActionResult.FAIL;
        player.getItemCooldownManager().set(this, 30);

        stack.set(OVERCHARGE_TICKS, 0);

        ((LiliumTimer) world).lilium$modify_component(30L, CHARGE_LEVEL, 0D, stack);
        player.getWorld().playSound(
                null,
                player.getBlockPos(),
                GenericSounds.DISCHARGE_CANNON_FIRE,
                SoundCategory.PLAYERS,
                1f,
                1f
        );

        raycastParticle(player, (world1, encountered, pitchYaw, currentRaycastPosition, x, y, z, iterations) -> {
            if (world1 instanceof ServerWorld serverWorld) {
                List<ServerPlayerEntity> players = serverWorld.getPlayers();
                for (ServerPlayerEntity spe : players) {
                    serverWorld.spawnParticles(spe, ParticleTypes.END_ROD, true, x, y, z, 1, 0, 0, 0, 0);
//                    if (player.getPos().squaredDistanceTo(currentRaycastPosition) < 25 && !encountered.contains(player)) {
//                        // don't send a million playSound packets
////                        player.networkHandler.sendPacket(new PlaySoundFromEntityS2CPacket(registryEntry, this.getSoundCategory(), player, 1.0F, 1.0F, this.getRandom().nextLong()));
//                        encountered.add(player);
//                    }
                }
            }
        });

        DamageSource source = new DamageSource(player.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(Lilium.RAILGUN_DAMAGE_TYPE));
        double damage = charge >= 1.5D ? 40.0D * (charge - 0.5D) : 20.0D * charge;
        double velocity = (charge >= 1.5F ? 6D * charge : 3D * charge);

        ((LiliumTimer) world).lilium$apply_look_velocity(30L, player.getControllingVehicle() != null ? player.getControllingVehicle() : player, velocity);
        ((LiliumTimer) world).lilium$damage_raycast(30L, player, 200.0D, source, (float) damage);

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
                double damage = target >= 1.5D ? 40.0D * (target - 0.5D) : 20.0D * target;
                Lilium.LOGGER.info("Charge Target {}, Damage {}, Use Time: {} seconds ({} ticks)", target, damage, ticks / 20, ticks);
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
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return MathHelper.clamp(
                Math.round(
                        (float) ((double) stack.getOrDefault(CHARGE_LEVEL, 0D)) * 13.0F
                ), 0,
                13);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return stack.getOrDefault(CHARGE_LEVEL, 0D) > 0D;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ColorHelper.Argb.lerp((float) MathHelper.clamp(stack.getOrDefault(CHARGE_LEVEL, 0D), 0, 1), RED, GREEN);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @SuppressWarnings({"UnusedReturnValue", "deprecation"})
    public Vec3d raycastParticle(PlayerEntity player, IRailgunRaycast step) {
        Vec3d vec3d = Vec3d.fromPolar(player.getPitch(), player.getYaw()).normalize();
        Vec3d raycast = player.getEyePos();
        int iterations = 0;
        Set<PlayerEntity> encountered = new HashSet<>();
        while (!player.getWorld().getBlockState(BlockPos.ofFloored(raycast)).isSolid()) {
            if (iterations > 384) {
                break;
            }
            raycast = raycast.add(vec3d);
            double x = raycast.getX();
            double y = raycast.getY();
            double z = raycast.getZ();
            step.step(player.getWorld(), encountered, vec3d, raycast, x, y, z, iterations);
            iterations++;
        }
        return raycast;
    }

    @FunctionalInterface
    public interface IRailgunRaycast {
        void step(World world, Set<PlayerEntity> encountered, Vec3d pitchYaw, Vec3d currentRaycastPosition, double x, double y, double z, int iterations);
    }
}
