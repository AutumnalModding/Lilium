package xyz.lilyflower.lilium.datagen.providers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;

public class LiliumModelProvider extends FabricModelProvider {
    public LiliumModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        try {
            AtomicReference<List<String>> skipped = new AtomicReference<>(new ArrayList<>());

            File generated = new File("alreadyGenerated.txt");
            if (!generated.exists()) {
                //noinspection ResultOfMethodCallIgnored
                generated.createNewFile();
            }
            skipped.set(Files.readAllLines(generated.toPath()));

            BlockRegistry.BLOCKS.forEach((name, block) -> {
                List<String> list = skipped.get();
                if (!list.contains(name) && !BlockRegistry.SKIP_DATAGEN.contains(block)) {
                    System.out.println("Running datagen for '" + name + "'...");

                    if (BlockRegistry.FLOWERS.contains(block)) {
                        generator.registerFlowerPotPlant(block, BlockRegistry.BLOCKS.get("potted_" + name), BlockStateModelGenerator.TintType.NOT_TINTED);
                    } else {
                        generator.registerSimpleCubeAll(block);
                    }

                    list.add(name);
                    skipped.set(list);
                }
            });

            Files.write(generated.toPath(), skipped.get());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
    }
}
