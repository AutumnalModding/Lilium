package xyz.lilyflower.lilium.client;

import java.io.IOException;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;
import xyz.lilyflower.lilium.util.registry.block.GenericBlocks;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;

@SuppressWarnings("deprecation")
public class LiliumClient implements ClientModInitializer {
    private static int[] ACEMUS_COLOURMAP;
    private static int[] CERASU_COLOURMAP;
    private static int[] KULIST_COLOURMAP;

    private static int getLeavesColor(BlockState state, BlockRenderView world, BlockPos pos, int tintIndex) {
        return BiomeColors.getFoliageColor(world, pos);
    }

    private static int getLeavesColor(BlockPos pos, int[] colormap) {
        int i = pos.getX() + pos.getY() & 0xff;
        int j = pos.getZ() + pos.getY() & 0xff;
        return colormap[i << 8 | j];
    }

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(GenericBlocks.GEAR_PRIMARY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(GenericBlocks.GEAR_SECONDARY, RenderLayer.getCutout());

        for (Block flower : BlockRegistry.FLOWERS) {
            BlockRenderLayerMap.INSTANCE.putBlock(flower, RenderLayer.getCutout());
        }

        for (WoodSets set : WoodSets.values()) {
            BlockRenderLayerMap.INSTANCE.putBlock(set.contents.get(8), RenderLayer.getCutout());
        }

        ColorProviderRegistry.BLOCK.register(LiliumClient::getLeavesColor, WoodSets.HEKUR.contents.get(9));
        ColorProviderRegistry.BLOCK.register(LiliumClient::getLeavesColor, WoodSets.LATA.contents.get(9));
        ColorProviderRegistry.BLOCK.register(LiliumClient::getLeavesColor, WoodSets.NUCIS.contents.get(9));
        ColorProviderRegistry.BLOCK.register(LiliumClient::getLeavesColor, WoodSets.TUOPA.contents.get(9));

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ResourceManager manager = MinecraftClient.getInstance().getResourceManager();

            try {
                ACEMUS_COLOURMAP = RawTextureDataLoader.loadRawTextureData(manager, Identifier.of("lilium", "textures/colormap/acemus.png"));
                CERASU_COLOURMAP = RawTextureDataLoader.loadRawTextureData(manager, Identifier.of("lilium", "textures/colormap/cerasu.png"));
                KULIST_COLOURMAP = RawTextureDataLoader.loadRawTextureData(manager, Identifier.of("lilium", "textures/colormap/kulist.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getLeavesColor(pos, ACEMUS_COLOURMAP), WoodSets.ACEMUS.contents.get(9));
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getLeavesColor(pos, CERASU_COLOURMAP), WoodSets.CERASU.contents.get(9));
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getLeavesColor(pos, KULIST_COLOURMAP), WoodSets.KULIST.contents.get(9));
    }
}
