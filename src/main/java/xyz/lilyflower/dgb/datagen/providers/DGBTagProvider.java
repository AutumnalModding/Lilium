package xyz.lilyflower.dgb.datagen.providers;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import xyz.lilyflower.dgb.block.DGBBlocks;

public class DGBTagProvider {
    public static class Blocks extends FabricTagProvider.BlockTagProvider {
        private static final TagKey<Block> COBBLESTONE = TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", "cobblestones"));

        public Blocks(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
            super(output, registries);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(BlockTags.DIRT)
                    .add(DGBBlocks.OLD_GRASS);

            getOrCreateTagBuilder(COBBLESTONE)
                    .add(DGBBlocks.OLD_COBBLESTONE);

            getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                    .add(DGBBlocks.OLD_GRASS)
                    .add(DGBBlocks.OLD_SAND)
                    .add(DGBBlocks.OLD_GRAVEL);

            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(DGBBlocks.OLD_COBBLESTONE)
                    .add(DGBBlocks.OLD_IRON_BLOCK)
                    .add(DGBBlocks.OLD_GOLD_BLOCK)
                    .add(DGBBlocks.OLD_DIAMOND_BLOCK)
                    .add(DGBBlocks.GLOWING_OBSIDIAN)
                    .add(DGBBlocks.NETHER_REACTOR)
                    .add(DGBBlocks.NETHER_REACTOR_ACTIVE)
                    .add(DGBBlocks.NETHER_REACTOR_BURNTOUT)
                    .add(DGBBlocks.GOLD_SHINY)
                    .add(DGBBlocks.OLD_BRICK);

            FabricTagProvider<Block>.FabricTagBuilder flowers = getOrCreateTagBuilder(BlockTags.FLOWERS);
            for (Block flower : DGBBlocks.FLOWERS) {
                if (!(flower instanceof FlowerPotBlock)) { // Vanilla doesn't have potted flowers in this -- so neither do we
                    flowers.add(flower);
                }
            }

            FabricTagProvider<Block>.FabricTagBuilder wool = getOrCreateTagBuilder(BlockTags.WOOL);
            for (Block cloth : DGBBlocks.CLOTHS) {
                wool.add(cloth);
            }

        }
    }
}
