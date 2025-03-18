package xyz.lilyflower.lilium.network.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DirectUsePayload() implements CustomPayload {
    public static final DirectUsePayload UNIT = new DirectUsePayload();
    public static final Id<DirectUsePayload> PAYLOAD_ID = new Id<>(Identifier.of("lilium", "direct_use"));
    public static final PacketCodec<RegistryByteBuf, DirectUsePayload> CODEC = PacketCodec.unit(UNIT);

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
