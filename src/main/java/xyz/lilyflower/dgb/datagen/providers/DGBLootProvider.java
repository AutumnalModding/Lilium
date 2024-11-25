package xyz.lilyflower.dgb.datagen.providers;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import xyz.lilyflower.dgb.block.DGBBlocks;

public class DGBLootProvider extends FabricBlockLootTableProvider {

    public DGBLootProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup) {
        super(output, lookup);
    }

    @Override
    public void generate() {
        DGBBlocks.BLOCK_ITEMS.forEach((name, item) -> {
            addDrop(DGBBlocks.BLOCKS.get(name), item);
        });
    }
}
