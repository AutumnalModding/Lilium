package xyz.lilyflower.dgb.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import xyz.lilyflower.dgb.datagen.providers.DGBLanguageProvider;
import xyz.lilyflower.dgb.datagen.providers.DGBLootProvider;
import xyz.lilyflower.dgb.datagen.providers.DGBModelProvider;
import xyz.lilyflower.dgb.datagen.providers.DGBTagProvider;

public class DGBDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(DGBModelProvider::new);
        pack.addProvider(DGBLanguageProvider::new);
        pack.addProvider(DGBTagProvider.Blocks::new);
        pack.addProvider(DGBLootProvider::new);
    }
}
