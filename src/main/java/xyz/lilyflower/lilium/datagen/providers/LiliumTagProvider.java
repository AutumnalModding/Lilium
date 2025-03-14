package xyz.lilyflower.lilium.datagen.providers;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;
import xyz.lilyflower.lilium.util.registry.block.GenericBlocks;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;

public class LiliumTagProvider {
    public static class Blocks extends FabricTagProvider.BlockTagProvider {
        private static final TagKey<Block> COBBLESTONE = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "cobblestones"));

        public Blocks(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
            super(output, registries);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(BlockTags.DIRT)
                    .add(GenericBlocks.OLD_GRASS);

            getOrCreateTagBuilder(COBBLESTONE)
                    .add(GenericBlocks.OLD_COBBLESTONE);

            getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                    .add(GenericBlocks.OLD_GRASS)
                    .add(GenericBlocks.OLD_SAND)
                    .add(GenericBlocks.OLD_GRAVEL);

            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(GenericBlocks.OLD_COBBLESTONE)
                    .add(GenericBlocks.OLD_IRON_BLOCK)
                    .add(GenericBlocks.OLD_GOLD_BLOCK)
                    .add(GenericBlocks.OLD_DIAMOND_BLOCK)
                    .add(GenericBlocks.GLOWING_OBSIDIAN)
                    .add(GenericBlocks.NETHER_REACTOR)
                    .add(GenericBlocks.NETHER_REACTOR_ACTIVE)
                    .add(GenericBlocks.NETHER_REACTOR_BURNTOUT)
                    .add(GenericBlocks.GOLD_SHINY)
                    .add(GenericBlocks.OLD_BRICK);

            FabricTagBuilder builder = getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(GenericBlocks.SUPPLY_CRATE);
            for (BlockItem item : WoodSets.WOODEN_BLOCK_ITEMS) {
                builder.add(item.getBlock());
            }

            FabricTagProvider<Block>.FabricTagBuilder flowers = getOrCreateTagBuilder(BlockTags.FLOWERS);
            for (Block flower : BlockRegistry.FLOWERS) {
                if (!(flower instanceof FlowerPotBlock)) { // Vanilla doesn't have potted flowers in this -- so neither do we
                    flowers.add(flower);
                }
            }

            getOrCreateTagBuilder(BlockTags.WOOL).add(BlockRegistry.CLOTH_BLOCKS.toArray(Block[]::new));

            getOrCreateTagBuilder(BlockTags.LOGS).add(WoodSets.LOGS.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.PLANKS).add(WoodSets.PLANKS.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(WoodSets.STAIRS.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(WoodSets.SLABS.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(WoodSets.FENCES.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(WoodSets.GATES.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(WoodSets.PLATES.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(WoodSets.BUTTONS.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.SAPLINGS).add(WoodSets.SAPLINGS.toArray(Block[]::new));
            getOrCreateTagBuilder(BlockTags.LEAVES).add(WoodSets.LEAVES.toArray(Block[]::new));
        }
    }
}
