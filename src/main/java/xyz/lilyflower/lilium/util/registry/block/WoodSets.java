package xyz.lilyflower.lilium.util.registry.block;

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
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WoodType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import xyz.lilyflower.lilium.block.LiliumSaplingBlock;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;

@SuppressWarnings("unused")
// TODO colormaps for: Acemus, Cerasu, and Kulist leaves
public enum WoodSets {
    ACEMUS("acemus", MapColor.OFF_WHITE, MapColor.OFF_WHITE),
    CEDRUM("cedrum", MapColor.ORANGE, MapColor.TERRACOTTA_ORANGE),
    CERASU("cerasu", MapColor.RED, MapColor.RED),
    DELNAS("delnas", MapColor.ORANGE, MapColor.BROWN),
    EWCALY("ewcaly", MapColor.PALE_GREEN, MapColor.OFF_WHITE),
    HEKUR("hekur", MapColor.OFF_WHITE, MapColor.OFF_WHITE),
    KIPARIS("kiparis", MapColor.ORANGE, MapColor.OFF_WHITE),
    KULIST("kulist", MapColor.PALE_YELLOW, MapColor.BROWN),
    LATA("lata", MapColor.OFF_WHITE, MapColor.ORANGE),
    NUCIS("nucis", MapColor.BROWN, MapColor.BROWN),
    PORFFOR("porffor", MapColor.PALE_PURPLE, MapColor.LIGHT_GRAY),
    SALYX("salyx", MapColor.LIGHT_GRAY, MapColor.LIGHT_GRAY),
    TUOPA("tuopa", MapColor.PALE_YELLOW, MapColor.PALE_YELLOW),
    MARSHMALLOW("marshmallow", MapColor.WHITE, MapColor.PINK),
    DARK_MARSHMALLOW("dark_marshmallow", MapColor.WHITE, MapColor.RED),
    LIGHT_MARSHMALLOW("light_marshmallow", MapColor.WHITE, MapColor.PINK),
    ;

    public final List<Block> contents;
    public final String name;
    
    public static final ArrayList<BlockItem> WOODEN_BLOCK_ITEMS = new ArrayList<>();

    public static final HashSet<Block> LOGS = new HashSet<>();
    public static final HashSet<Block> PLANKS = new HashSet<>();
    public static final HashSet<Block> STAIRS = new HashSet<>();
    public static final HashSet<Block> SLABS = new HashSet<>();
    public static final HashSet<Block> FENCES = new HashSet<>();
    public static final HashSet<Block> GATES = new HashSet<>();
    public static final HashSet<Block> PLATES = new HashSet<>();
    public static final HashSet<Block> BUTTONS = new HashSet<>();
    public static final HashSet<Block> SAPLINGS = new HashSet<>();
    public static final HashSet<Block> LEAVES = new HashSet<>();

    WoodSets(String name, MapColor top, MapColor side) {
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
        Block sapling = new LiliumSaplingBlock(null, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING));
        Block leaves = new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).mapColor(side));

        blocks.add(log);
        blocks.add(planks);
        blocks.add(stairs);
        blocks.add(slab);
        blocks.add(fence);
        blocks.add(gate);
        blocks.add(plate);
        blocks.add(button);
        blocks.add(sapling);
        blocks.add(leaves);

        BlockRegistry.SKIP_DATAGEN.addAll(blocks);

        BlockRegistry.BLOCKS.put(name + "_log", log);
        BlockRegistry.BLOCKS.put(name + "_planks", planks);
        BlockRegistry.BLOCKS.put(name + "_stairs", stairs);
        BlockRegistry.BLOCKS.put(name + "_slab", slab);
        BlockRegistry.BLOCKS.put(name + "_fence", fence);
        BlockRegistry.BLOCKS.put(name + "_fence_gate", gate);
        BlockRegistry.BLOCKS.put(name + "_pressure_plate", plate);
        BlockRegistry.BLOCKS.put(name + "_button", button);
        BlockRegistry.BLOCKS.put(name + "_sapling", sapling);
        BlockRegistry.BLOCKS.put(name + "_leaves", leaves);

        this.name = name;
        this.contents = blocks;
    }
    
    static {
        for (WoodSets set : values()) {
            Block log = set.contents.get(0);
            Block planks = set.contents.get(1);
            Block stairs = set.contents.get(2);
            Block slab = set.contents.get(3);
            Block fence = set.contents.get(4);
            Block gate = set.contents.get(5);
            Block plate = set.contents.get(6);
            Block button = set.contents.get(7);
            Block sapling = set.contents.get(8);
            Block leaves = set.contents.get(9);

            LOGS.add(log);
            PLANKS.add(planks);
            STAIRS.add(stairs);
            SLABS.add(slab);
            FENCES.add(fence);
            GATES.add(gate);
            PLATES.add(plate);
            BUTTONS.add(button);
            SAPLINGS.add(sapling);
            LEAVES.add(leaves);
            
            BlockItem logItem = new BlockItem(log, new Item.Settings());
            BlockItem planksItem = new BlockItem(planks, new Item.Settings());
            BlockItem stairsItem = new BlockItem(stairs, new Item.Settings());
            BlockItem slabItem = new BlockItem(slab, new Item.Settings());
            BlockItem fenceItem = new BlockItem(fence, new Item.Settings());
            BlockItem gateItem = new BlockItem(gate, new Item.Settings());
            BlockItem plateItem = new BlockItem(plate, new Item.Settings());
            BlockItem buttonItem = new BlockItem(button, new Item.Settings());
            BlockItem saplingItem = new BlockItem(sapling, new Item.Settings());
            BlockItem leavesItem = new BlockItem(leaves, new Item.Settings());

            BlockRegistry.BLOCK_ITEMS.put(set.name + "_log", logItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_planks", planksItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_stairs", stairsItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_slab", slabItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_fence", fenceItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_fence_gate", gateItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_pressure_plate", plateItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_button", buttonItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_sapling", saplingItem);
            BlockRegistry.BLOCK_ITEMS.put(set.name + "_leaves", leavesItem);

            WOODEN_BLOCK_ITEMS.add(logItem);
            WOODEN_BLOCK_ITEMS.add(planksItem);
            WOODEN_BLOCK_ITEMS.add(stairsItem);
            WOODEN_BLOCK_ITEMS.add(slabItem);
            WOODEN_BLOCK_ITEMS.add(fenceItem);
            WOODEN_BLOCK_ITEMS.add(gateItem);
            WOODEN_BLOCK_ITEMS.add(plateItem);
            WOODEN_BLOCK_ITEMS.add(buttonItem);
            WOODEN_BLOCK_ITEMS.add(saplingItem);
            WOODEN_BLOCK_ITEMS.add(leavesItem);
        }
    }
}
