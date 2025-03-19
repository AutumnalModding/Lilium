package xyz.lilyflower.lilium.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import net.fabricmc.api.ClientModInitializer;
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
import org.reflections.Reflections;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.client.util.ColourRegistry;
import xyz.lilyflower.lilium.client.util.RendererRegistry;
import xyz.lilyflower.lilium.item.DischargeCannonItem;
import xyz.lilyflower.lilium.item.LiliumElytra;
import xyz.lilyflower.lilium.util.LiliumPacket;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;
import xyz.lilyflower.lilium.util.registry.block.GenericBlocks;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;

@SuppressWarnings("deprecation")
public class LiliumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColourRegistry.init();
        RendererRegistry.init();

        Reflections reflections = new Reflections("xyz.lilyflower.lilium.network");
        Set<Class<? extends LiliumPacket>> packets = reflections.getSubTypesOf(LiliumPacket.class);
        for (Class<? extends LiliumPacket> packet : packets) {
            try {
                Constructor<? extends LiliumPacket> constructor = packet.getConstructor();
                LiliumPacket instance = constructor.newInstance();
                instance.registerClient();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exception) {
                Lilium.LOGGER.fatal("Failed to register packet {} on client side!!", packet.getSimpleName());
                Lilium.LOGGER.fatal("Reason: {}", exception.getMessage());
                Lilium.LOGGER.fatal("Cause: {}", exception.getMessage());
                System.exit(1);
            }
        }
    }
}
