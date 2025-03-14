package xyz.lilyflower.lilium.util.registry;

import java.util.LinkedHashMap;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.item.GenericItems;

@SuppressWarnings("InstantiationOfUtilityClass")
public class ItemRegistry {
    public static final LinkedHashMap<String, Item> ITEMS = new LinkedHashMap<>();

    public static Item create(String name, Item item) {
        ITEMS.put(name, item);
        return item;
    }

    public static void init() {
        ITEMS.forEach((name, item) -> {
            Lilium.LOGGER.debug("Registering item '{}'", name);
            Registry.register(Registries.ITEM, Identifier.of("lilium", name), item);
        });
    }

    static {
        new GenericItems();
    }
}
