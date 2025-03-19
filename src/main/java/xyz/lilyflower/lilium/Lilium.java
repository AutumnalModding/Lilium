package xyz.lilyflower.lilium;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.reflections.Reflections;
import xyz.lilyflower.lilium.network.LiliumDirectClickPacket;
import xyz.lilyflower.lilium.util.LiliumPacket;
import xyz.lilyflower.lilium.util.registry.BlockRegistry;

import org.apache.logging.log4j.Logger;
import xyz.lilyflower.lilium.util.registry.GroupRegistry;
import xyz.lilyflower.lilium.util.registry.ItemRegistry;
import xyz.lilyflower.lilium.util.registry.SoundRegistry;

public class Lilium implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Lilium");

	public static final RegistryKey<DamageType> RAILGUN_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("lilium", "railgun"));

	@Override
	public void onInitialize() {
		BlockRegistry.init();
		ItemRegistry.init();
		SoundRegistry.init();
		GroupRegistry.init();

		Reflections reflections = new Reflections("xyz.lilyflower.lilium.network");
		Set<Class<? extends LiliumPacket>> packets = reflections.getSubTypesOf(LiliumPacket.class);
		for (Class<? extends LiliumPacket> packet : packets) {
			try {
				Constructor<? extends LiliumPacket> constructor = packet.getConstructor();
				LiliumPacket instance = constructor.newInstance();
				instance.register();
			} catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exception) {
				LOGGER.fatal("Failed to register packet {} on common side!!", packet.getSimpleName());
				LOGGER.fatal("Reason: {}", exception.getMessage());
				LOGGER.fatal("Cause: {}", exception.getMessage());
                System.exit(1);
            }
        }
	}
}