package xyz.lilyflower.lilium.util.registry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ColoredFallingBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.ColorCode;
import xyz.lilyflower.lilium.block.SupplyCrateBlock;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;

@SuppressWarnings("unused")
public class GenericBlocks {
    public static final Block OLD_GRASS = BlockRegistry.create("grass", new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK)));
    public static final Block OLD_COBBLESTONE = BlockRegistry.create("cobblestone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
    public static final Block OLD_BRICK = BlockRegistry.create("bricks", new Block(AbstractBlock.Settings.copy(Blocks.BRICKS)));
    public static final Block OLD_SAND = BlockRegistry.create("sand", new ColoredFallingBlock(new ColorCode(14406560), AbstractBlock.Settings.copy(Blocks.SAND)));
    public static final Block OLD_GRAVEL = BlockRegistry.create("gravel", new ColoredFallingBlock(new ColorCode(-8356741), AbstractBlock.Settings.copy(Blocks.GRAVEL)));
    public static final Block OLD_IRON_BLOCK = BlockRegistry.create("iron_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block OLD_GOLD_BLOCK = BlockRegistry.create("gold_block", new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)));
    public static final Block OLD_DIAMOND_BLOCK = BlockRegistry.create("diamond_block", new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK)));

    public static final Block GEAR_PRIMARY = BlockRegistry.create("gear_primary", new GearBlock(Blocks.CACTUS));
    public static final Block GEAR_SECONDARY = BlockRegistry.create("gear_secondary", new GearBlock(Blocks.CACTUS));
    public static final Block GOLD_SHINY = BlockRegistry.create("gold_shiny", new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)));

    public static final Block UPDATE = BlockRegistry.create("update", new Block(AbstractBlock.Settings.copy(Blocks.DIRT)));
    public static final Block GLOWING_OBSIDIAN = BlockRegistry.create("glowing_obsidian", new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)));
    public static final Block NETHER_REACTOR = BlockRegistry.create("nether_reactor", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block NETHER_REACTOR_ACTIVE = BlockRegistry.create("nether_reactor_active", new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK)));
    public static final Block NETHER_REACTOR_BURNTOUT = BlockRegistry.create("nether_reactor_burntout", new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)));

    public static final Block ROSE = BlockRegistry.flower("rose", new FlowerBlock(StatusEffects.LUCK, 30.0F, AbstractBlock.Settings.copy(Blocks.POPPY)));
    public static final Block CYAN_ROSE = BlockRegistry.flower("rose_cyan", new FlowerBlock(StatusEffects.INVISIBILITY, 30.0F, AbstractBlock.Settings.copy(Blocks.POPPY)));
    public static final Block PAEONIA = BlockRegistry.flower("paeonia", new FlowerBlock(StatusEffects.HUNGER, 3.0F, AbstractBlock.Settings.copy(Blocks.POPPY)));

    public static final Block SUPPLY_CRATE = BlockRegistry.create("chest_locked_aprilfools_super_old_legacy_we_should_not_even_have_this", new SupplyCrateBlock());

    public static final Block OLD_GLASS = BlockRegistry.create("glass", new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static final Block GLASS_BUT_NOT_REALLY = BlockRegistry.create("glass_but_not_really", new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
}
