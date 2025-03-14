package xyz.lilyflower.lilium;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import xyz.lilyflower.lilium.block.registry.BlockRegistry;
import xyz.lilyflower.lilium.block.registry.GenericBlocks;

import org.apache.logging.log4j.Logger;
import xyz.lilyflower.lilium.block.registry.WoodSets;

public class Lilium implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Lilium");

	public static final RegistryKey<ItemGroup> IGK_LILIUM_GENERIC = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("lilium", "item_group_generic"));
	public static final ItemGroup ITEMGROUP_LILIUM_GENERIC = FabricItemGroup.builder()
			.icon(() -> new ItemStack(GenericBlocks.CYAN_ROSE))
			.displayName(Text.translatable("itemGroup.lilium.generic"))
			.entries((displayContext, entries) -> {
				BlockRegistry.BLOCK_ITEMS.forEach((name, item) -> {
					if (!WoodSets.WOODEN_BLOCK_ITEMS.contains(item)) {
						entries.add(item);
					}
				});
			})
			.build();

	public static final RegistryKey<ItemGroup> IGK_LILIUM_DENDROLOGY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("lilium", "item_group_dendrology"));
	public static final ItemGroup ITEMGROUP_LILIUM_DENDROLOGY = FabricItemGroup.builder()
			.icon(() -> new ItemStack(WoodSets.DARK_MARSHMALLOW.contents.get(1)))
			.displayName(Text.translatable("itemGroup.lilium.dendrology"))
			.entries((displayContext, entries) -> {
				for (BlockItem item : WoodSets.WOODEN_BLOCK_ITEMS) {
					entries.add(item);
				}
			})
			.build();

	@Override
	public void onInitialize() {
		BlockRegistry.init();

		Registry.register(Registries.ITEM_GROUP, IGK_LILIUM_GENERIC, ITEMGROUP_LILIUM_GENERIC);
		Registry.register(Registries.ITEM_GROUP, IGK_LILIUM_DENDROLOGY, ITEMGROUP_LILIUM_DENDROLOGY);
	}
}