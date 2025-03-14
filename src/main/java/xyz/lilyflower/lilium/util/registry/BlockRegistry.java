package xyz.lilyflower.lilium.util.registry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.util.registry.block.ClothBlocks;
import xyz.lilyflower.lilium.util.registry.block.GenericBlocks;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;

@SuppressWarnings("InstantiationOfUtilityClass")
public class BlockRegistry {
    public static final LinkedHashMap<String, Block> BLOCKS = new LinkedHashMap<>();
    public static final LinkedHashMap<String, BlockItem> BLOCK_ITEMS = new LinkedHashMap<>();

    public static final HashSet<Block> FLOWERS = new HashSet<>();
    public static final ArrayList<Block> CLOTH_BLOCKS = new ArrayList<>();
    public static final ArrayList<Block> SKIP_DATAGEN = new ArrayList<>();

    public static Block create(String name, Block block) {
        BLOCKS.put(name, block);
        BLOCK_ITEMS.put(name, new BlockItem(block, new Item.Settings()));

        return block;
    }

    public static Block cloth(String name, Block block) {
        BLOCKS.put(name, block);
        CLOTH_BLOCKS.add(block);
        BLOCK_ITEMS.put(name, new BlockItem(block, new Item.Settings()));

        return block;
    }

    public static Block flower(String name, Block block) {
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
            Lilium.LOGGER.debug("Registering block '{}'", name);
            Registry.register(Registries.BLOCK, Identifier.of("lilium", name), block);
            BlockItem item = BLOCK_ITEMS.get(name);
            if (item != null) {
                Registry.register(Registries.ITEM, Identifier.of("lilium", name), BLOCK_ITEMS.get(name));
            }
        });
    }

    static {
        for (WoodSets value : WoodSets.values()) {
            value.contents.getFirst(); // Do nothing. We just want to initialize it.
        }
        new ClothBlocks();
        new GenericBlocks();
    }
}
