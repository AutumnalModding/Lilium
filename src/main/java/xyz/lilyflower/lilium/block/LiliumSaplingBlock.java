package xyz.lilyflower.lilium.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class LiliumSaplingBlock extends SaplingBlock {
    public LiliumSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
    }

    @Override
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, state.cycle(STAGE), 4);
        } else {
            if (this.generator != null) {
                this.generator.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
            } // TODO: special sapling generators
            }
    }
}
