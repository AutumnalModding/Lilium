package xyz.lilyflower.lilium.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import net.fabricmc.api.ClientModInitializer;
import org.reflections.Reflections;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.client.util.ColourRegistry;
import xyz.lilyflower.lilium.client.util.RendererRegistry;
import xyz.lilyflower.lilium.util.LiliumPacket;

public class LiliumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColourRegistry.init();
        RendererRegistry.init();

        Reflections reflections = new Reflections("xyz.lilyflower.lilium.network");
        Set<Class<? extends LiliumPacket>> packets = reflections.getSubTypesOf(LiliumPacket.class);
        for (Class<? extends LiliumPacket> packet : packets) {
            try {
                Constructor<? extends LiliumPacket> constructor = packet.getConstructor();
                LiliumPacket instance = constructor.newInstance();
                instance.registerClient();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exception) {
                Lilium.LOGGER.fatal("Failed to register packet {} on client side!!", packet.getSimpleName());
                Lilium.LOGGER.fatal("Reason: {}", exception.getMessage());
                Lilium.LOGGER.fatal("Cause: {}", exception.getMessage());
                System.exit(1);
            }
        }
    }
}
