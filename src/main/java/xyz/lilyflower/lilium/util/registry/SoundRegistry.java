package xyz.lilyflower.lilium.util.registry;

import java.util.HashMap;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {
    private static final HashMap<String, SoundEvent> SOUNDS = new HashMap<>();

    public static SoundEvent create(String name) {
        SoundEvent event = SoundEvent.of(Identifier.of("lilium", name));
        SOUNDS.put(name, event);
        return event;
    }

    public static void init() {
        SOUNDS.forEach((name, sound) -> {
            Registry.register(Registries.SOUND_EVENT, Identifier.of("lilium", name), sound);
        });
    }
}
