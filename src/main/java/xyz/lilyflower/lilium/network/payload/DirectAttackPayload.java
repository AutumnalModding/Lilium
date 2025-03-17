package xyz.lilyflower.lilium.network.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DirectAttackPayload() implements CustomPayload {
    public static final DirectAttackPayload UNIT = new DirectAttackPayload();
    public static final Id<DirectAttackPayload> PAYLOAD_ID = new Id<>(Identifier.of("lilium", "direct_attack"));
    public static final PacketCodec<RegistryByteBuf, DirectAttackPayload> CODEC = PacketCodec.unit(UNIT);

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
