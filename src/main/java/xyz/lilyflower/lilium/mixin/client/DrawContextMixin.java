package xyz.lilyflower.lilium.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.lilyflower.lilium.Lilium;
import xyz.lilyflower.lilium.item.DischargeCannonItem;
import xyz.lilyflower.lilium.util.registry.item.GenericItems;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @WrapOperation(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V", ordinal = 0))
    private void drawOverchangeBarBackground(DrawContext instance, RenderLayer layer, int x1, int y1, int x2, int y2, int color, Operation<Void> original, @Local(argsOnly = true)ItemStack stack, @Share(value = "overcharge", namespace = "lilium")LocalDoubleRef doubleRef) {
        original.call(instance, layer, x1, y1, x2, y2, color);
        if (stack.isOf(GenericItems.DISCHARGE_CANNON) && stack.getOrDefault(DischargeCannonItem.OVERCHARGE_TICKS, 0) > 0) {
            original.call(instance, layer, x1, y1 - 1, x2, y2 - 1, color);
        }
    }

    @WrapOperation(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V", ordinal = 1))
    private void drawOverchangeBar(DrawContext instance, RenderLayer layer, int x1, int y1, int x2, int y2, int color, Operation<Void> original, TextRenderer textRenderer, ItemStack stack, int x, int y, @Nullable String countOverride) {
        original.call(instance, layer, x1, y1, x2, y2, color);
        double overcharge = stack.getOrDefault(DischargeCannonItem.OVERCHARGE_TICKS, 0);
        if (stack.isOf(GenericItems.DISCHARGE_CANNON) && overcharge > 0) {
            int itemBarStep = MathHelper.clamp(
                    Math.round(
                            (float) overcharge * 13.0F / 430 // 430 ticks is about max damage
                    ), 0,
                    13);
            original.call(instance, layer, x1, y1 - 1, x + 2 + itemBarStep, y2 - 1, color);
        }
    }
}
