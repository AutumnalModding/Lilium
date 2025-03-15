package xyz.lilyflower.lilium.block;

import java.util.HashMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;
import xyz.lilyflower.lilium.util.registry.block.GenericBlocks;
import xyz.lilyflower.lilium.util.registry.block.WoodSets;

public class SupplyCrateBlock extends Block {
    public static final DirectionProperty FACING;

    public SupplyCrateBlock() {
        super(AbstractBlock.Settings.copy(Blocks.CHEST));
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));

    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(final StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        if (player instanceof ServerPlayerEntity server && server.interactionManager.getGameMode() == GameMode.ADVENTURE) {
            return ActionResult.FAIL;
        }

        world.playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                pos, // The position of where the sound will come from
                Lilium.CRATE_OPEN, // The sound that will play
                SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );

        Random random = world.getRandom();
        int selector = random.nextBetween(0, 100);

        CrateType type = CrateType.NOTHING;
        if (selector == 0) type = CrateType.RESOURCE;
        if (selector >= 10 && selector <= 20) type = CrateType.ENGINEER;
        if (selector >= 21 && selector <= 51) type = CrateType.PRESERVATION;
        if (selector >= 52 && selector <= 92) type = CrateType.BUILDER;
        if (selector >= 93 && selector <= 100) type = CrateType.REACTOR;

        HashMap<Item, Integer> drops = new HashMap<>();

        switch (type) {
            case NOTHING -> {} // NOP
            case ENGINEER -> {
                drops.put(GenericBlocks.OLD_SAND.asItem(), 20);
                drops.put(GenericBlocks.OLD_GRAVEL.asItem(), 20);
                drops.put(GenericBlocks.GEAR_PRIMARY.asItem(), 16);
                drops.put(GenericBlocks.GEAR_SECONDARY.asItem(), 16);
            }

            case RESOURCE -> {
                int resource = random.nextBetween(0, 2);
                drops.put(switch (resource) {
                    case 0 -> GenericBlocks.OLD_IRON_BLOCK.asItem();
                    case 1 -> GenericBlocks.OLD_GOLD_BLOCK.asItem();
                    case 2 -> GenericBlocks.OLD_DIAMOND_BLOCK.asItem();
                    default -> throw new IllegalStateException("Unexpected value: " + resource);
                }, 3);
            }

            case BUILDER -> {
                int types = random.nextBetween(1, 3);

                for (int index = 0; index < types; index++) {
                    int count = random.nextBetween(1, 64);
                    int drop = random.nextBetween(0, 3);
                    drops.put(switch (drop) {
                        case 0 -> GenericBlocks.OLD_SAND.asItem();
                        case 1 -> GenericBlocks.OLD_GRAVEL.asItem();
                        case 2 -> GenericBlocks.OLD_BRICK.asItem();
                        case 3 -> GenericBlocks.OLD_COBBLESTONE.asItem();
                        default -> throw new IllegalStateException("Unexpected value: " + drop);
                    }, count);
                }

                boolean updateBlockPresent = (random.nextBetween(1, 100)) <= 5;
                if (updateBlockPresent) drops.put(GenericBlocks.UPDATE.asItem(), 1);

                int cloths = random.nextBetween(0, 3);
                if (cloths > 0) {
                    for (int cloth = 0; cloth < cloths; cloth++) {
                        int amount = random.nextBetween(2, 8);
                        Block block = BlockRegistry.CLOTH_BLOCKS.get(random.nextBetween(0, 15));

                        drops.put(block.asItem(), amount);
                    }
                }
            }

            case REACTOR -> {
                drops.put(GenericBlocks.NETHER_REACTOR.asItem(), 1);
                drops.put(GenericBlocks.GOLD_SHINY.asItem(), 4);
                drops.put(GenericBlocks.OLD_COBBLESTONE.asItem(), 14);
            }

            case PRESERVATION -> {
                int roses = random.nextBetween(1, 5);
                boolean cyan = random.nextBetween(1, 100) <= 5;
                int cyans = cyan ? random.nextBetween(0, 3) : 0;
                boolean paeonia = random.nextBetween(1, 100) <= 20;
                int paeonias = paeonia ? random.nextBetween(0, 2) : 0;
                Item sapling = WoodSets.values()[random.nextBetween(0, 12)].contents.get(8).asItem();
                int saplings = random.nextBetween(0, 6);
                int grass = random.nextBetween(1, 8);

                drops.put(GenericBlocks.ROSE.asItem(), roses);
                drops.put(GenericBlocks.CYAN_ROSE.asItem(), cyans);
                drops.put(GenericBlocks.PAEONIA.asItem(), paeonias);
                drops.put(GenericBlocks.OLD_GRASS.asItem(), grass);
                drops.put(sapling, saplings);
            }
        }

        drops.forEach((drop, count) -> {
            if (count > 0) {
                Block.dropStack(world, pos, new ItemStack(drop, count));
            }
        });

        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        return ActionResult.success(true);
    }

    private enum CrateType {
        RESOURCE,
        NOTHING,
        REACTOR,
        ENGINEER,
        PRESERVATION,
        BUILDER
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }
}
