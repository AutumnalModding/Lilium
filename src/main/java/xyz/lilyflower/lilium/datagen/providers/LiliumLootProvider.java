package xyz.lilyflower.lilium.datagen.providers;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;

public class LiliumLootProvider extends FabricBlockLootTableProvider {

    public LiliumLootProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup) {
        super(output, lookup);
    }

    @Override
    public void generate() {
        BlockRegistry.BLOCK_ITEMS.forEach((name, item) -> {
            addDrop(BlockRegistry.BLOCKS.get(name), item);
        });
    }
}
