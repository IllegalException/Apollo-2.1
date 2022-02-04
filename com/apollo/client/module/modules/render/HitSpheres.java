//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.event.events.*;
import com.apollo.api.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import com.apollo.api.players.friends.*;
import org.lwjgl.opengl.*;
import com.apollo.api.util.render.*;
import java.util.*;

public class HitSpheres extends Module
{
    public HitSpheres() {
        super("HitSpheres", Module.Category.Render);
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (this.isEnabled()) {
            for (final Entity ep : Wrapper.getWorld().loadedEntityList) {
                if (ep instanceof EntityPlayerSP) {
                    continue;
                }
                if (!(ep instanceof EntityPlayer)) {
                    continue;
                }
                final double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * Wrapper.getMinecraft().timer.renderPartialTicks;
                final double d2 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * Wrapper.getMinecraft().timer.renderPartialTicks;
                final double d3 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * Wrapper.getMinecraft().timer.renderPartialTicks;
                if (Friends.isFriend(ep.getName())) {
                    GL11.glColor4f(0.15f, 0.15f, 1.0f, 1.0f);
                }
                else if (Wrapper.getPlayer().getDistanceSq(ep) >= 64.0) {
                    GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                }
                else {
                    GL11.glColor4f(1.0f, Wrapper.getPlayer().getDistance(ep) / 150.0f, 0.0f, 1.0f);
                }
                ApolloTessellator.drawSphere(d, d2, d3, 6.0f, 20, 15);
            }
        }
    }
}
