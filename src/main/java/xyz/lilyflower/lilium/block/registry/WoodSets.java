package xyz.lilyflower.lilium.block.registry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WoodType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

@SuppressWarnings("unused")
public class WoodSets {
    public static final ArrayList<BlockItem> WOODEN_BLOCK_ITEMS = new ArrayList<>();
    public static final HashSet<Block> LOGS = new HashSet<>();
    public static final HashSet<Block> PLANKS = new HashSet<>();
    public static final HashSet<Block> STAIRS = new HashSet<>();
    public static final HashSet<Block> SLABS = new HashSet<>();
    public static final HashSet<Block> FENCES = new HashSet<>();
    public static final HashSet<Block> GATES = new HashSet<>();
    public static final HashSet<Block> PLATES = new HashSet<>();
    public static final HashSet<Block> BUTTONS = new HashSet<>();

    public static final List<Block> ACEMUS = set("acemus", MapColor.OFF_WHITE, MapColor.OFF_WHITE);
    public static final List<Block> CEDRUM = set("cedrum", MapColor.ORANGE, MapColor.TERRACOTTA_ORANGE);
    public static final List<Block> CERASU = set("cerasu", MapColor.RED, MapColor.RED);
    public static final List<Block> DELNAS = set("delnas", MapColor.ORANGE, MapColor.BROWN);
    public static final List<Block> EWCALY = set("ewcaly", MapColor.PALE_GREEN, MapColor.OFF_WHITE);
    public static final List<Block> HEKUR = set("hekur", MapColor.OFF_WHITE, MapColor.OFF_WHITE);
    public static final List<Block> KIPARIS = set("kiparis", MapColor.ORANGE, MapColor.OFF_WHITE);
    public static final List<Block> KULIST = set("kulist", MapColor.PALE_YELLOW, MapColor.BROWN);
    public static final List<Block> LATA = set("lata", MapColor.OFF_WHITE, MapColor.ORANGE);
    public static final List<Block> NUCIS = set("nucis", MapColor.BROWN, MapColor.BROWN);
    public static final List<Block> PORFFOR = set("porffor", MapColor.PALE_PURPLE, MapColor.LIGHT_GRAY);
    public static final List<Block> SALYX = set("salyx", MapColor.LIGHT_GRAY, MapColor.LIGHT_GRAY);
    public static final List<Block> TUOPA = set("tuopa", MapColor.PALE_YELLOW, MapColor.PALE_YELLOW);
    public static final List<Block> MARSHMALLOW = set("marshmallow", MapColor.WHITE, MapColor.PINK);
    public static final List<Block> DARK_MARSHMALLOW = set("dark_marshmallow", MapColor.WHITE, MapColor.RED);
    public static final List<Block> LIGHT_MARSHMALLOW = set("light_marshmallow", MapColor.WHITE, MapColor.PINK);

    private static List<Block> set(String name, MapColor top, MapColor side) {
        ArrayList<Block> blocks = new ArrayList<>();

        BlockSetType set = new BlockSetType(name);
        WoodType type = new WoodType(name, set);

        Block log = Blocks.createLogBlock(top, side);
        Block planks = new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).mapColor(side));
        Block stairs = new StairsBlock(planks.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS).mapColor(side));
        Block slab = new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB).mapColor(side));
        Block fence = new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE).mapColor(side));
        Block gate = new FenceGateBlock(type, AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE).mapColor(side));
        Block plate = new PressurePlateBlock(set, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE).mapColor(side));
        Block button = new ButtonBlock(set, 30, AbstractBlock.Settings.copy(Blocks.OAK_BUTTON).mapColor(side));

        blocks.add(log);
        blocks.add(planks);
        blocks.add(stairs);
        blocks.add(slab);
        blocks.add(fence);
        blocks.add(gate);

        BlockRegistry.SKIP_DATAGEN.addAll(blocks);
        LOGS.add(log);
        PLANKS.add(planks);
        STAIRS.add(stairs);
        SLABS.add(slab);
        FENCES.add(fence);
        GATES.add(gate);
        PLATES.add(plate);
        BUTTONS.add(button);

        BlockRegistry.BLOCKS.put(name + "_log", log);
        BlockRegistry.BLOCKS.put(name + "_planks", planks);
        BlockRegistry.BLOCKS.put(name + "_stairs", stairs);
        BlockRegistry.BLOCKS.put(name + "_slab", slab);
        BlockRegistry.BLOCKS.put(name + "_fence", fence);
        BlockRegistry.BLOCKS.put(name + "_fence_gate", gate);
        BlockRegistry.BLOCKS.put(name + "_pressure_plate", plate);
        BlockRegistry.BLOCKS.put(name + "_button", button);

        BlockItem logItem = new BlockItem(log, new Item.Settings());
        BlockItem planksItem = new BlockItem(planks, new Item.Settings());
        BlockItem stairsItem = new BlockItem(stairs, new Item.Settings());
        BlockItem slabItem = new BlockItem(slab, new Item.Settings());
        BlockItem fenceItem = new BlockItem(fence, new Item.Settings());
        BlockItem gateItem = new BlockItem(gate, new Item.Settings());
        BlockItem plateItem = new BlockItem(plate, new Item.Settings());
        BlockItem buttonItem = new BlockItem(button, new Item.Settings());

        BlockRegistry.BLOCK_ITEMS.put(name + "_log", logItem);
        BlockRegistry.BLOCK_ITEMS.put(name + "_planks", planksItem);
        BlockRegistry.BLOCK_ITEMS.put(name + "_stairs", stairsItem);
        BlockRegistry.BLOCK_ITEMS.put(name + "_slab", slabItem);
        BlockRegistry.BLOCK_ITEMS.put(name + "_fence", fenceItem);
        BlockRegistry.BLOCK_ITEMS.put(name + "_fence_gate", gateItem);
        BlockRegistry.BLOCK_ITEMS.put(name + "_pressure_plate", plateItem);
        BlockRegistry.BLOCK_ITEMS.put(name + "_button", buttonItem);

        WOODEN_BLOCK_ITEMS.add(logItem);
        WOODEN_BLOCK_ITEMS.add(planksItem);
        WOODEN_BLOCK_ITEMS.add(stairsItem);
        WOODEN_BLOCK_ITEMS.add(slabItem);
        WOODEN_BLOCK_ITEMS.add(fenceItem);
        WOODEN_BLOCK_ITEMS.add(gateItem);
        WOODEN_BLOCK_ITEMS.add(plateItem);
        WOODEN_BLOCK_ITEMS.add(buttonItem);

        return blocks;
    }
}
