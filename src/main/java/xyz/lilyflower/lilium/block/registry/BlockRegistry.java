package xyz.lilyflower.lilium.block.registry;

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

@SuppressWarnings("InstantiationOfUtilityClass")
public class BlockRegistry {
    public static final LinkedHashMap<String, Block> BLOCKS = new LinkedHashMap<>();
    public static final LinkedHashMap<String, BlockItem> BLOCK_ITEMS = new LinkedHashMap<>();

    public static final HashSet<Block> FLOWERS = new HashSet<>();
    public static final HashSet<Block> CLOTH_BLOCKS = new HashSet<>();
    public static final ArrayList<Block> SKIP_DATAGEN = new ArrayList<>();

    static Block create(String name, Block block) {
        BLOCKS.put(name, block);
        BLOCK_ITEMS.put(name, new BlockItem(block, new Item.Settings()));

        return block;
    }

    static Block cloth(String name, Block block) {
        BLOCKS.put(name, block);
        CLOTH_BLOCKS.add(block);
        BLOCK_ITEMS.put(name, new BlockItem(block, new Item.Settings()));

        return block;
    }

    static Block flower(String name, Block block) {
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
        System.out.println("Registering blocks");
        BLOCKS.forEach((name, block) -> {
            Lilium.LOGGER.debug("Registering block '{}'.", name);
            Registry.register(Registries.BLOCK, Identifier.of("lilium", name), block);
        });

        BLOCK_ITEMS.forEach((name, block) -> {
            Registry.register(Registries.ITEM, Identifier.of("lilium", name), block);
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
