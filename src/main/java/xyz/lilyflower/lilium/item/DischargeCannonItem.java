package xyz.lilyflower.lilium.item;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.util.DirectClickItem;

public class DischargeCannonItem extends Item implements DirectClickItem {
    public static final ComponentType<Float> CHARGE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lilium", "discharge_cannnon_charge_level"),
            ComponentType.<Float>builder().codec(Codec.FLOAT).build()
    );

    public DischargeCannonItem() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public ActionResult onDirectAttack(PlayerEntity player, Hand hand) {
        if (player.getWorld().isClient) return ActionResult.SUCCESS;

        double distance = 200.0F;
        Vec3d start = player.getEyePos();
        Vec3d end = player.getEyePos().add(player.getRotationVector().multiply(distance));

        HitResult result = ProjectileUtil.raycast(player, start, end, new Box(start, end), entity -> entity instanceof LivingEntity, distance*distance);
        if (result instanceof EntityHitResult ehr) {
            LivingEntity entity = (LivingEntity) ehr.getEntity();

            if (!entity.isInvulnerable()) {
                DamageSource source = new DamageSource(player.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(Lilium.RAILGUN_DAMAGE_TYPE));
                entity.damage(source, 80.0F);
                player.getItemCooldownManager().set(this, 600);
            }
        }

        return ActionResult.CONSUME;
    }

    @Override
    public ActionResult onDirectUse(PlayerEntity player, Hand hand) {
        return null;
    }
}
