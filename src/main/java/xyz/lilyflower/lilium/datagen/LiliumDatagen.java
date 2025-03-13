package xyz.lilyflower.lilium.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import xyz.lilyflower.lilium.datagen.providers.LiliumLanguageProvider;
import xyz.lilyflower.lilium.datagen.providers.LiliumLootProvider;
import xyz.lilyflower.lilium.datagen.providers.LiliumModelProvider;
import xyz.lilyflower.lilium.datagen.providers.LiliumTagProvider;

public class LiliumDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(LiliumModelProvider::new);
        pack.addProvider(LiliumLanguageProvider::new);
        pack.addProvider(LiliumTagProvider.Blocks::new);
        pack.addProvider(LiliumLootProvider::new);
    }
}
