package xyz.lilyflower.lilium.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import xyz.lilyflower.lilium.block.registry.BlockRegistry;
import xyz.lilyflower.lilium.block.registry.GenericBlocks;

public class LiliumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(GenericBlocks.GEAR_PRIMARY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GenericBlocks.GEAR_SECONDARY, RenderLayer.getCutout());

        for (Block flower : BlockRegistry.FLOWERS) {
            BlockRenderLayerMap.INSTANCE.putBlock(flower, RenderLayer.getCutout());
        }
    }
}
