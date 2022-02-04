//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.client.module.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

public class FastBow extends Module
{
    public FastBow() {
        super("FastBow", Module.Category.Combat);
    }
    
    public void onUpdate() {
        if (FastBow.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && FastBow.mc.player.isHandActive() && FastBow.mc.player.getItemInUseMaxCount() >= 3) {
            FastBow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, FastBow.mc.player.getHorizontalFacing()));
            FastBow.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(FastBow.mc.player.getActiveHand()));
            FastBow.mc.player.stopActiveHand();
        }
    }
}
