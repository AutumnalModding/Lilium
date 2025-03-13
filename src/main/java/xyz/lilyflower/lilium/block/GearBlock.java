package xyz.lilyflower.lilium.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import xyz.lilyflower.lilium.block.registry.GenericBlocks;

public class GearBlock extends Block {
    public static final BooleanProperty BREAKING = BooleanProperty.of("breaking");

    public GearBlock(Block parent) {
        super(AbstractBlock.Settings.copy(parent));

        this.setDefaultState(this.getDefaultState().with(BREAKING, false));
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.setBlockState(pos, state.with(BREAKING, true));
            world.breakBlock(pos, false);
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }

        if (neighborState.isOf(this)) {
            if (neighborState.get(BREAKING)) {
                this.blockDrop(state, (ServerWorld) world, pos);
            }
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    public void blockDrop(BlockState state, ServerWorld world, BlockPos pos) {
        if (state.getBlock() instanceof GearBlock)  {
            world.setBlockState(pos, state.with(BREAKING, true));
            world.breakBlock(new BlockPos(pos), false);
        }
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockState there = world.getBlockState(pos.offset(direction));
            if (direction != Direction.UP && (there.isOf(GenericBlocks.OLD_SAND) || there.isOf(GenericBlocks.OLD_GRAVEL)) || (there.getBlock() instanceof GearBlock && there.get(BREAKING))) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BREAKING);
    }
}
