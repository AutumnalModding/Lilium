package xyz.lilyflower.lilium.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import xyz.lilyflower.lilium.network.payload.DirectAttackPayload;
import xyz.lilyflower.lilium.network.payload.DirectUsePayload;
import xyz.lilyflower.lilium.util.DirectAttackItem;
import xyz.lilyflower.lilium.util.DirectUseItem;
import xyz.lilyflower.lilium.util.LiliumPacket;


public class LiliumDirectClickPacket implements LiliumPacket {
    @Override
    public void register() {
        PayloadTypeRegistry.playC2S().register(DirectAttackPayload.PAYLOAD_ID, DirectAttackPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(DirectUsePayload.PAYLOAD_ID, DirectUsePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(DirectAttackPayload.PAYLOAD_ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            Item item = player.getMainHandStack().getItem();
            ItemCooldownManager manager = player.getItemCooldownManager();
            if (item instanceof DirectAttackItem dci && !manager.isCoolingDown(item) && dci.onDirectAttack(player, Hand.MAIN_HAND).shouldSwingHand()) {
                player.swingHand(Hand.MAIN_HAND, true);
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(DirectUsePayload.PAYLOAD_ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            Item item = player.getMainHandStack().getItem();
            ItemCooldownManager manager = player.getItemCooldownManager();
            if (item instanceof DirectUseItem dci && !manager.isCoolingDown(item) && dci.onDirectUse(player, Hand.MAIN_HAND).shouldSwingHand()) {
                player.swingHand(Hand.MAIN_HAND, true);
            }
        });
    }

    @Override
    public void registerClient() {

    }
}