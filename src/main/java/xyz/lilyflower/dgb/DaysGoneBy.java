package xyz.lilyflower.dgb;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.lilyflower.dgb.block.DGBBlocks;

public class DaysGoneBy implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("DGB Logger");

	public static final RegistryKey<ItemGroup> DGB_ITEMGROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("days-gone-by", "item_group"));
	public static final ItemGroup DGB_ITEMGROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(DGBBlocks.CYAN_ROSE))
			.displayName(Text.translatable("itemGroup.days-gone-by"))
			.build();

	@Override
	public void onInitialize() {
		DGBBlocks.init();

		Registry.register(Registries.ITEM_GROUP, DGB_ITEMGROUP_KEY, DGB_ITEMGROUP);
		ItemGroupEvents.modifyEntriesEvent(DGB_ITEMGROUP_KEY).register(entries -> {
			DGBBlocks.BLOCK_ITEMS.forEach((name, block) -> {
				entries.add(block);
			});
		});
	}
}