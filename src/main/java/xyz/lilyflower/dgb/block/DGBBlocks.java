package xyz.lilyflower.dgb.block;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ColoredFallingBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import xyz.lilyflower.dgb.DaysGoneBy;

@SuppressWarnings("unused")
public class DGBBlocks {
    public static final LinkedHashMap<String, Block> BLOCKS = new LinkedHashMap<>();
    public static final LinkedHashMap<String, BlockItem> BLOCK_ITEMS = new LinkedHashMap<>();

    public static final ArrayList<Block> CLOTHS = new ArrayList<>();
    public static final ArrayList<Block> FLOWERS = new ArrayList<>();
    public static final ArrayList<Block> SKIP_DATAGEN = new ArrayList<>();

    public static final Block OLD_GRASS = create("grass", new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK)));
    public static final Block OLD_COBBLESTONE = create("cobblestone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
    public static final Block OLD_BRICK = create("bricks", new Block(AbstractBlock.Settings.copy(Blocks.BRICKS)));
    public static final Block OLD_SAND = create("sand", new ColoredFallingBlock(new ColorCode(14406560), AbstractBlock.Settings.copy(Blocks.SAND)));
    public static final Block OLD_GRAVEL = create("gravel", new ColoredFallingBlock(new ColorCode(-8356741), AbstractBlock.Settings.copy(Blocks.GRAVEL)));
    public static final Block OLD_IRON_BLOCK = create("iron_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block OLD_GOLD_BLOCK = create("gold_block", new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)));
    public static final Block OLD_DIAMOND_BLOCK = create("diamond_block", new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_BLOCK)));

    public static final Block GEAR_PRIMARY = create("gear_primary", new GearBlock(Blocks.CACTUS));
    public static final Block GEAR_SECONDARY = create("gear_secondary", new GearBlock(Blocks.CACTUS));
    public static final Block GOLD_SHINY = create("gold_shiny", new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)));

    public static final Block UPDATE = create("update", new Block(AbstractBlock.Settings.copy(Blocks.DIRT)));
    public static final Block GLOWING_OBSIDIAN = create("glowing_obsidian", new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)));
    public static final Block NETHER_REACTOR = create("nether_reactor", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
    public static final Block NETHER_REACTOR_ACTIVE = create("nether_reactor_active", new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK)));
    public static final Block NETHER_REACTOR_BURNTOUT = create("nether_reactor_burntout", new Block(AbstractBlock.Settings.copy(Blocks.OBSIDIAN)));

    public static final Block ROSE = flower("rose", new FlowerBlock(StatusEffects.LUCK, 30.0F, AbstractBlock.Settings.copy(Blocks.POPPY)));
    public static final Block CYAN_ROSE = flower("rose_cyan", new FlowerBlock(StatusEffects.INVISIBILITY, 30.0F, AbstractBlock.Settings.copy(Blocks.POPPY)));
    public static final Block PAEONIA = flower("paeonia", new FlowerBlock(StatusEffects.HUNGER, 3.0F, AbstractBlock.Settings.copy(Blocks.POPPY)));

    private static Block create(String name, Block block) {
        BLOCKS.put(name, block);
        BLOCK_ITEMS.put(name, new BlockItem(block, new Item.Settings()));

        return block;
    }

    private static Block flower(String name, Block block) {
        Block potted = Blocks.createFlowerPotBlock(block);

        BLOCKS.put(name, block);
        BLOCKS.put("potted_" + name, potted);

        FLOWERS.add(block);
        FLOWERS.add(potted);
        SKIP_DATAGEN.add(potted);

        BLOCK_ITEMS.put(name, new BlockItem(block, new Item.Settings()));

        return block;
    }

    public static void init() {
        BLOCKS.forEach((name, block) -> {
            DaysGoneBy.LOGGER.debug("Registering block '{}'.", name);
            Registry.register(Registries.BLOCK, Identifier.of("days-gone-by", name), block);
        });

        BLOCK_ITEMS.forEach((name, block) -> {
            Registry.register(Registries.ITEM, Identifier.of("days-gone-by", name), block);
        });
    }

    static {
        for (int i = 1; i <= 16; i++) {
            Block cloth = create("cloth_" + String.format("%02d", i), new Block(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)));
            CLOTHS.add(cloth);
        }
    }
}
