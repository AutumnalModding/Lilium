package xyz.lilyflower.lilium.datagen.providers;

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
import xyz.lilyflower.lilium.block.registry.BlockRegistry;
import xyz.lilyflower.lilium.block.registry.GenericBlocks;
import xyz.lilyflower.lilium.block.registry.WoodBlocks;

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

            FabricTagBuilder builder = getOrCreateTagBuilder(BlockTags.AXE_MINEABLE);
            for (Block log : WoodBlocks.LOGS) {
                builder.add(log);
            }
            for (Block plank : WoodBlocks.PLANKS) {
                builder.add(plank);
            }
            for (Block stair : WoodBlocks.STAIRS) {
                builder.add(stair);
            }
            for (Block slab : WoodBlocks.SLABS) {
                builder.add(slab);
            }
            for (Block fence : WoodBlocks.FENCES) {
                builder.add(fence);
            }
            for (Block gate : WoodBlocks.GATES) {
                builder.add(gate);
            }
            for (Block plate : WoodBlocks.PLATES) {
                builder.add(plate);
            }
            for (Block button : WoodBlocks.BUTTONS) {
                builder.add(button);
            }

            FabricTagProvider<Block>.FabricTagBuilder flowers = getOrCreateTagBuilder(BlockTags.FLOWERS);
            for (Block flower : BlockRegistry.FLOWERS) {
                if (!(flower instanceof FlowerPotBlock)) { // Vanilla doesn't have potted flowers in this -- so neither do we
                    flowers.add(flower);
                }
            }

            FabricTagProvider<Block>.FabricTagBuilder wool = getOrCreateTagBuilder(BlockTags.WOOL);
            for (Block cloth : BlockRegistry.CLOTH_BLOCKS) {
                wool.add(cloth);
            }

            FabricTagProvider<Block>.FabricTagBuilder logs = getOrCreateTagBuilder(BlockTags.LOGS);
            for (Block log : WoodBlocks.LOGS) {
                logs.add(log);
            }

            FabricTagProvider<Block>.FabricTagBuilder planks = getOrCreateTagBuilder(BlockTags.PLANKS);
            for (Block plank : WoodBlocks.PLANKS) {
                planks.add(plank);
            }

            FabricTagProvider<Block>.FabricTagBuilder stairs = getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS);
            for (Block stair : WoodBlocks.STAIRS) {
                stairs.add(stair);
            }

            FabricTagProvider<Block>.FabricTagBuilder slabs = getOrCreateTagBuilder(BlockTags.WOODEN_SLABS);
            for (Block slab : WoodBlocks.SLABS) {
                slabs.add(slab);
            }

            FabricTagProvider<Block>.FabricTagBuilder fences = getOrCreateTagBuilder(BlockTags.WOODEN_FENCES);
            for (Block fence : WoodBlocks.FENCES) {
                fences.add(fence);
            }

            FabricTagProvider<Block>.FabricTagBuilder gates = getOrCreateTagBuilder(BlockTags.FENCE_GATES);
            for (Block gate : WoodBlocks.GATES) {
                gates.add(gate);
            }

            FabricTagProvider<Block>.FabricTagBuilder plates = getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES);
            for (Block plate : WoodBlocks.PLATES) {
                plates.add(plate);
            }

            FabricTagProvider<Block>.FabricTagBuilder buttons = getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS);
            for (Block button : WoodBlocks.BUTTONS) {
                buttons.add(button);
            }
        }
    }
}
