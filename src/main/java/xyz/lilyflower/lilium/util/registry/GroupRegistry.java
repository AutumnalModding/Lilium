package xyz.lilyflower.lilium.util.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.lilyflower.lilium.util.registry.block.GenericBlocks;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;
import xyz.lilyflower.lilium.util.registry.item.LiliumElytras;

public class GroupRegistry {
    public static final ItemGroup ITEMGROUP_LILIUM_GENERIC = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GenericBlocks.CYAN_ROSE))
            .displayName(Text.translatable("itemGroup.lilium.generic"))
            .entries((displayContext, entries) -> {
                BlockRegistry.BLOCK_ITEMS.forEach((name, item) -> {
                    if (!WoodSets.WOODEN_BLOCK_ITEMS.contains(item)) {
                        entries.add(item);
                    }
                });

                ItemRegistry.ITEMS.forEach((name, item) -> {
                    if (!name.contains("elytra_")) {
                        entries.add(item);
                    }
                });
            })
            .build();
    public static final ItemGroup ITEMGROUP_LILIUM_DENDROLOGY = FabricItemGroup.builder()
            .icon(() -> new ItemStack(WoodSets.DARK_MARSHMALLOW.contents.getFirst()))
            .displayName(Text.translatable("itemGroup.lilium.dendrology"))
            .entries((displayContext, entries) -> {
                for (BlockItem item : WoodSets.WOODEN_BLOCK_ITEMS) {
                    entries.add(item);
                }
            })
            .build();
    public static final ItemGroup ITEMGROUP_LILIUM_ELYTRAS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(LiliumElytras.ELYTRA_LESBIAN))
            .displayName(Text.translatable("itemGroup.lilium.elytras"))
            .entries((displayContext, entries) -> {
                ItemRegistry.ITEMS.forEach((name, item) -> {
                    if (name.contains("elytra_")) {
                        entries.add(item);
                    }
                });
            })
            .build();
    
    public static void init() {
        Registry.register(Registries.ITEM_GROUP, RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("lilium", "item_group_generic")), ITEMGROUP_LILIUM_GENERIC);
        Registry.register(Registries.ITEM_GROUP, RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("lilium", "item_group_dendrology")), ITEMGROUP_LILIUM_DENDROLOGY);
        Registry.register(Registries.ITEM_GROUP, RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("lilium", "item_group_elytras")), ITEMGROUP_LILIUM_ELYTRAS);
    }
}
