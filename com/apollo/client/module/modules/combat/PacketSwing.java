//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.client.module.*;
import net.minecraft.item.*;

public class PacketSwing extends Module
{
    public PacketSwing() {
        super("PacketSwing", Module.Category.Combat);
    }
    
    public void onUpdate() {
        if (PacketSwing.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && PacketSwing.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            PacketSwing.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            PacketSwing.mc.entityRenderer.itemRenderer.itemStackMainHand = PacketSwing.mc.player.getHeldItemMainhand();
        }
    }
}
