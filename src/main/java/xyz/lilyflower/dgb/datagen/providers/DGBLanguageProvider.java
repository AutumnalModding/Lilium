package xyz.lilyflower.dgb.datagen.providers;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.apache.commons.lang3.text.WordUtils;
import xyz.lilyflower.dgb.block.DGBBlocks;

public class DGBLanguageProvider extends FabricLanguageProvider {
    public DGBLanguageProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup) {
        super(output, lookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup lookup, TranslationBuilder builder) {
        DGBBlocks.BLOCKS.forEach((name, block) -> {
//            try {
//                Path lang = dataOutput.getModContainer().findPath("assets/days-gone-by/lang/en_us.json").get();
//                builder.add(lang);
//            } catch (Exception exception) {
//                throw new RuntimeException("Failed to add existing language file!", exception);
//            }

            String localized = WordUtils.capitalize(name.replaceAll("_", " "));
            builder.add(block, localized);
        });
    }
}
