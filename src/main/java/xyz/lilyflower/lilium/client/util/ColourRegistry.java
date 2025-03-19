package xyz.lilyflower.lilium.client.util;

import java.io.IOException;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;

public class ColourRegistry {
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
    
    public static void init() {
        ColorProviderRegistry.BLOCK.register(ColourRegistry::getLeavesColor, WoodSets.HEKUR.contents.get(9));
        ColorProviderRegistry.BLOCK.register(ColourRegistry::getLeavesColor, WoodSets.LATA.contents.get(9));
        ColorProviderRegistry.BLOCK.register(ColourRegistry::getLeavesColor, WoodSets.NUCIS.contents.get(9));
        ColorProviderRegistry.BLOCK.register(ColourRegistry::getLeavesColor, WoodSets.TUOPA.contents.get(9));

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

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (MinecraftClient.getInstance().player != null) {
                return getLeavesColor(MinecraftClient.getInstance().player.getBlockPos(), ACEMUS_COLOURMAP);
            }

            return 0;
        }, WoodSets.ACEMUS.contents.get(9));

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (MinecraftClient.getInstance().player != null) {
                return getLeavesColor(MinecraftClient.getInstance().player.getBlockPos(), CERASU_COLOURMAP);
            }

            return 0;
        }, WoodSets.CERASU.contents.get(9));

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (MinecraftClient.getInstance().player != null) {
                return getLeavesColor(MinecraftClient.getInstance().player.getBlockPos(), KULIST_COLOURMAP);
            }

            return 0;
        }, WoodSets.KULIST.contents.get(9));

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getLeavesColor(pos, ACEMUS_COLOURMAP), WoodSets.ACEMUS.contents.get(9));
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getLeavesColor(pos, CERASU_COLOURMAP), WoodSets.CERASU.contents.get(9));
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getLeavesColor(pos, KULIST_COLOURMAP), WoodSets.KULIST.contents.get(9));
    }
}
