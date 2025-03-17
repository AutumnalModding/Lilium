package xyz.lilyflower.lilium.util.registry.item;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import xyz.lilyflower.lilium.item.LiliumElytra;
import xyz.lilyflower.lilium.util.registry.ItemRegistry;

public class LiliumElytras {
    public static final Map<String, Item> ELYTRAS_DYED = new HashMap<>();
    public static final TagKey<Item> ELYTRAS = TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "elytra"));

    private static void generateElytras() {
        for (DyeColor colour : DyeColor.values()) {
            ELYTRAS_DYED.put("elytra_" + colour.name().toLowerCase(), ItemRegistry.create("elytra_" + colour.name().toLowerCase(Locale.ROOT), new LiliumElytra()));
        }
    }

    public static final Item ELYTRA_LESBIAN = ItemRegistry.create("elytra_lesbian", new LiliumElytra());
    public static final Item ELYTRA_NON_BINARY = ItemRegistry.create("elytra_non_binary", new LiliumElytra());
    public static final Item ELYTRA_PRIDE = ItemRegistry.create("elytra_pride", new LiliumElytra());
    public static final Item ELYTRA_TRANS = ItemRegistry.create("elytra_trans", new LiliumElytra());
    public static final Item ELYTRA_GTNH = ItemRegistry.create("elytra_gtnh", new LiliumElytra());

    static {
        generateElytras();
    }
}
