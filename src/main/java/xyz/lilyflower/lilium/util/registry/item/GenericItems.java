package xyz.lilyflower.lilium.util.registry.item;

import net.minecraft.item.Item;
import xyz.lilyflower.lilium.item.DischargeCannonItem;
import xyz.lilyflower.lilium.util.registry.ItemRegistry;

@SuppressWarnings("unused")
public class GenericItems {
    public static final Item DISCHARGE_CANNON = ItemRegistry.create("discharge_cannon", new DischargeCannonItem());
    public static final Item RUBY = ItemRegistry.create("ruby", new Item(new Item.Settings()));
}
