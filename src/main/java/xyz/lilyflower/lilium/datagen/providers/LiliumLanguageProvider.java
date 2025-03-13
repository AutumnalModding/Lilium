package xyz.lilyflower.lilium.datagen.providers;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.apache.commons.lang3.text.WordUtils;
import xyz.lilyflower.lilium.block.registry.BlockRegistry;

public class LiliumLanguageProvider extends FabricLanguageProvider {
    public LiliumLanguageProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup) {
        super(output, lookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup lookup, TranslationBuilder builder) {
        BlockRegistry.BLOCKS.forEach((name, block) -> {
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
