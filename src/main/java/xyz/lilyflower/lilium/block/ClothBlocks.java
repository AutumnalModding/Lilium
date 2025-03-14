package xyz.lilyflower.lilium.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;

@SuppressWarnings("unused")
public class ClothBlocks {
    public static final Block CLOTH_CRIMSON = BlockRegistry.cloth("cloth_crimson", new Block(AbstractBlock.Settings.copy(Blocks.RED_WOOL)));
    public static final Block CLOTH_AMBER = BlockRegistry.cloth("cloth_amber", new Block(AbstractBlock.Settings.copy(Blocks.ORANGE_WOOL)));
    public static final Block CLOTH_CANARY = BlockRegistry.cloth("cloth_canary", new Block(AbstractBlock.Settings.copy(Blocks.YELLOW_WOOL)));
    public static final Block CLOTH_CHARTREUSE = BlockRegistry.cloth("cloth_chartreuse", new Block(AbstractBlock.Settings.copy(Blocks.LIME_WOOL)));
    public static final Block CLOTH_EMERALD = BlockRegistry.cloth("cloth_emerald", new Block(AbstractBlock.Settings.copy(Blocks.LIME_WOOL)));
    public static final Block CLOTH_SPRING_GREEN = BlockRegistry.cloth("cloth_spring_green", new Block(AbstractBlock.Settings.copy(Blocks.CYAN_WOOL)));
    public static final Block CLOTH_AQUA = BlockRegistry.cloth("cloth_aqua", new Block(AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_WOOL)));
    public static final Block CLOTH_CAPRI = BlockRegistry.cloth("cloth_capri", new Block(AbstractBlock.Settings.copy(Blocks.BLUE_WOOL)));
    public static final Block CLOTH_ULTRAMARINE = BlockRegistry.cloth("cloth_ultramarine", new Block(AbstractBlock.Settings.copy(Blocks.BLUE_WOOL)));
    public static final Block CLOTH_VIOLET = BlockRegistry.cloth("cloth_violet", new Block(AbstractBlock.Settings.copy(Blocks.PURPLE_WOOL)));
    public static final Block CLOTH_LILAC = BlockRegistry.cloth("cloth_lilac", new Block(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL)));
    public static final Block CLOTH_FUCHSIA = BlockRegistry.cloth("cloth_fuchsia", new Block(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL)));
    public static final Block CLOTH_ROSE = BlockRegistry.cloth("cloth_rose", new Block(AbstractBlock.Settings.copy(Blocks.PINK_WOOL)));
    public static final Block CLOTH_GRAY = BlockRegistry.cloth("cloth_gray", new Block(AbstractBlock.Settings.copy(Blocks.GRAY_WOOL)));
    public static final Block CLOTH_LIGHT_GRAY = BlockRegistry.cloth("cloth_light_gray", new Block(AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_WOOL)));
    public static final Block CLOTH_WHITE = BlockRegistry.cloth("cloth_white", new Block(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)));
}
