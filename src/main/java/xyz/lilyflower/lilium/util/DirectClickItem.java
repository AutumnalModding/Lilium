/*
MIT License

Copyright (c) 2021 Una Thompson (unascribed)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package xyz.lilyflower.lilium.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface DirectClickItem {
    /**
     * Called when a player presses the "attack" key while holding this item. (Left-click by default)
     * <p>
     * Returning a successful ActionResult <i>on the client</i> will suppress all vanilla
     * functionality and cause this to be called again on the server. Generally, as such, this
     * method should start with {@code if (player.world.isClient) return ActionResult.SUCCESS;},
     * but you can conditionally suppress vanilla logic should you desire.
     * <p>
     * If this returns {@code SUCCESS} on the server, the player's hand will be swung.
     * <p>
     * You probably also want to override {@link Item#canMine} to always return {@code false}.
     *
     * @param player the player that attacked with this item
     * @param hand the hand (currently, always MAIN_HAND, but this may change)
     * @return your reaction to this attack
     */
    ActionResult onDirectAttack(PlayerEntity player, Hand hand);
    /**
     * Called when a player presses the "use" key while holding this item. (Right-click by default)
     * <p>
     * Returning a successful ActionResult <i>on the client</i> will suppress all vanilla
     * functionality and cause this to be called again on the server. Generally, as such, this
     * method should start with {@code if (player.world.isClient) return ActionResult.SUCCESS;},
     * but you can conditionally suppress vanilla logic should you desire.
     * <p>
     * If this returns {@code SUCCESS} on the server, the player's hand will be swung.
     *
     * @param player the player that used this item
     * @param hand the hand (currently, always MAIN_HAND, but this may change)
     * @return your reaction to this use
     */
    ActionResult onDirectUse(PlayerEntity player, Hand hand);
}
