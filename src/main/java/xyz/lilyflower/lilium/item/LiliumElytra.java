package xyz.lilyflower.lilium.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import xyz.lilyflower.lilium.util.registry.item.LiliumElytras;

public class LiliumElytra extends ElytraItem implements FabricElytraItem {
    public LiliumElytra() {
        super(new Item.Settings().rarity(Rarity.UNCOMMON).maxDamage(432));
    }

    @Environment(EnvType.CLIENT)
    public static class Renderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
        private final ElytraEntityModel<T> elytra;

        public Renderer(FeatureRendererContext<T, M> featureRendererContext, EntityModelLoader entityModelLoader) {
            super(featureRendererContext);
            this.elytra = new ElytraEntityModel<>(entityModelLoader.getModelPart(EntityModelLayers.ELYTRA));
        }

        @Override
        public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
            ItemStack stack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
            if (stack.isIn(LiliumElytras.ELYTRAS) && !stack.isOf(Items.ELYTRA)) {
                Identifier texture = Registries.ITEM.getId(stack.getItem()).withPrefixedPath("textures/entity/elytra/");

                if (stack.getName().getString().equals("Suspicious " + stack.getItem().getName().getString())) {
                    texture = texture.withSuffixedPath("_suspicious.png");
                } else {
                    texture = texture.withSuffixedPath(".png");
                }

                matrixStack.push();
                matrixStack.translate(0.0D, 0.0D, 0.125D);
                getContextModel().copyStateTo(this.elytra);
                this.elytra.setAngles(livingEntity, f, g, j, k, l);
                VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.elytra.getLayer(texture), false, stack.hasGlint());
                this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
                matrixStack.pop();
            }
        }
    }
}

