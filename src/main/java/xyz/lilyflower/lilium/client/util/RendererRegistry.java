package xyz.lilyflower.lilium.client.util;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.block.Block;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import xyz.lilyflower.lilium.item.DischargeCannonItem;
import xyz.lilyflower.lilium.item.LiliumElytra;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;
import xyz.lilyflower.lilium.util.registry.block.GenericBlocks;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;

public class RendererRegistry {
    public static void init() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(((EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper, EntityRendererFactory.Context context) -> {
            if (entityRenderer.getModel() instanceof PlayerEntityModel || entityRenderer.getModel() instanceof BipedEntityModel || entityRenderer.getModel() instanceof ArmorStandEntityModel) {
                registrationHelper.register(new LiliumElytra.Renderer<>(entityRenderer, context.getModelLoader()));
            }
        }));

        BlockRenderLayerMap.INSTANCE.putBlock(GenericBlocks.GEAR_PRIMARY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GenericBlocks.GEAR_SECONDARY, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(GenericBlocks.OLD_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GenericBlocks.GLASS_BUT_NOT_REALLY, RenderLayer.getTranslucent());

        for (Block flower : BlockRegistry.FLOWERS) {
            BlockRenderLayerMap.INSTANCE.putBlock(flower, RenderLayer.getCutout());
        }

        for (WoodSets set : WoodSets.values()) {
            BlockRenderLayerMap.INSTANCE.putBlock(set.contents.get(8), RenderLayer.getCutout());
        }

        ModelPredicateProviderRegistry.register(Identifier.of("lilium", "discharge_cannon_state"), (stack, world, entity, seed) -> {
            double charge = stack.getOrDefault(DischargeCannonItem.CHARGE_LEVEL, 1.0D);
            if (charge >= 0.99F && charge < 1.0F) return 0.98F;
            if (charge >= 1.0F && charge < 1.5F) return 0.99F;
            if (charge >= 1.5F) return 1.0F;
            return (float) charge;
        });
    }
}
