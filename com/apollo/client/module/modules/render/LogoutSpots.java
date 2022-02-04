//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import net.minecraft.entity.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.event.world.*;
import java.util.concurrent.*;
import java.util.function.*;
import com.apollo.api.event.events.*;
import org.lwjgl.opengl.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.client.module.*;
import com.apollo.api.util.color.*;
import java.awt.*;
import com.apollo.api.util.render.*;
import com.apollo.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;
import java.text.*;
import com.apollo.client.command.*;
import java.util.*;

public class LogoutSpots extends Module
{
    Map<Entity, String> loggedPlayers;
    List<Entity> lastTickEntities;
    @EventHandler
    private final Listener<PlayerJoinEvent> listener1;
    @EventHandler
    private final Listener<PlayerLeaveEvent> listener2;
    @EventHandler
    private final Listener<WorldEvent.Unload> listener3;
    @EventHandler
    private final Listener<WorldEvent.Load> listener4;
    
    public LogoutSpots() {
        super("LogoutSpots", Module.Category.Render);
        this.loggedPlayers = new ConcurrentHashMap<Entity, String>();
        this.listener1 = (Listener<PlayerJoinEvent>)new Listener(event -> this.loggedPlayers.forEach((e, s) -> {
            try {
                if (e.getName().equalsIgnoreCase(event.getName())) {
                    this.loggedPlayers.remove(e);
                    Command.sendClientMessage(event.getName() + " reconnected!");
                }
            }
            catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
            }
        }), new Predicate[0]);
        this.listener2 = (Listener<PlayerLeaveEvent>)new Listener(event -> {
            if (LogoutSpots.mc.world == null) {
                return;
            }
            String date;
            String pos;
            this.lastTickEntities.forEach(e -> {
                if (e.getName().equalsIgnoreCase(event.getName())) {
                    date = new SimpleDateFormat("k:mm").format(new Date());
                    this.loggedPlayers.put(e, date);
                    pos = "x" + e.getPosition().getX() + " y" + e.getPosition().getY() + " z" + e.getPosition().getZ();
                    Command.sendClientMessage(event.getName() + " disconnected at " + pos + "!");
                }
            });
        }, new Predicate[0]);
        this.listener3 = (Listener<WorldEvent.Unload>)new Listener(event -> {
            this.lastTickEntities.clear();
            if (LogoutSpots.mc.player == null) {
                this.loggedPlayers.clear();
            }
            else if (!LogoutSpots.mc.player.isDead) {
                this.loggedPlayers.clear();
            }
        }, new Predicate[0]);
        this.listener4 = (Listener<WorldEvent.Load>)new Listener(event -> {
            this.lastTickEntities.clear();
            if (LogoutSpots.mc.player == null) {
                this.loggedPlayers.clear();
            }
            else if (!LogoutSpots.mc.player.isDead) {
                this.loggedPlayers.clear();
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        this.lastTickEntities = (List<Entity>)LogoutSpots.mc.world.loadedEntityList;
    }
    
    public void onWorldRender(final RenderEvent event) {
        this.loggedPlayers.forEach((e, time) -> {
            if (LogoutSpots.mc.player.getDistance(e) < 500.0f) {
                GL11.glPushMatrix();
                this.drawLogoutBox(e.getRenderBoundingBox(), 1, 0, 0, 0, 255);
                this.drawNametag(e, time);
                GL11.glPopMatrix();
            }
        });
    }
    
    public void drawLogoutBox(final AxisAlignedBB bb, final int width, final int r, final int b, final int g, final int a) {
        final ColorMain colorMain = (ColorMain)ModuleManager.getModuleByName("Colors");
        final Color c = Rainbow.getColor();
        Color color;
        if (ColorMain.rainbow.getValue()) {
            color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
        }
        else {
            color = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), 255);
        }
        ApolloTessellator.drawBoundingBox(bb, (float)width, color.getRGB());
    }
    
    public void onEnable() {
        this.lastTickEntities = new ArrayList<Entity>();
        this.loggedPlayers.clear();
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
    
    private void drawNametag(final Entity entityIn, final String t) {
        GlStateManager.pushMatrix();
        final float f = LogoutSpots.mc.player.getDistance(entityIn);
        final float sc = (f < 25.0f) ? 0.5f : 2.0f;
        float m = f / 20.0f * (float)Math.pow(1.258925437927246, 0.1 / sc);
        if (m < 0.5f) {
            m = 0.5f;
        }
        if (m > 5.0f) {
            m = 5.0f;
        }
        final Vec3d interp = getInterpolatedRenderPos(entityIn, LogoutSpots.mc.getRenderPartialTicks());
        float mm;
        if (m > 2.0f) {
            mm = m / 2.0f;
        }
        else {
            mm = m;
        }
        final float yAdd = entityIn.height + mm;
        final double x = interp.x;
        final double y = interp.y + yAdd;
        final double z = interp.z;
        final float viewerYaw = LogoutSpots.mc.getRenderManager().playerViewY;
        final float viewerPitch = LogoutSpots.mc.getRenderManager().playerViewX;
        final boolean isThirdPersonFrontal = LogoutSpots.mc.getRenderManager().options.thirdPersonView == 2;
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(-viewerYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(m, m, m);
        final FontRenderer fontRendererIn = LogoutSpots.mc.fontRenderer;
        GlStateManager.scale(-0.025f, -0.025f, 0.025f);
        final String line1 = entityIn.getName() + "  (" + t + ")";
        final String line2 = "x" + entityIn.getPosition().getX() + " y" + entityIn.getPosition().getY() + " z" + entityIn.getPosition().getZ();
        final int i = fontRendererIn.getStringWidth(line1) / 2;
        final int ii = fontRendererIn.getStringWidth(line2) / 2;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableTexture2D();
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        final ColorMain colorMain = (ColorMain)ModuleManager.getModuleByName("Colors");
        final Color c = Rainbow.getColor();
        Color color;
        if (ColorMain.rainbow.getValue()) {
            color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 255);
        }
        else {
            color = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), 255);
        }
        fontRendererIn.drawStringWithShadow(line1, (float)(-i), 10.0f, color.getRGB());
        fontRendererIn.drawStringWithShadow(line2, (float)(-ii), 20.0f, color.getRGB());
        GlStateManager.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.popMatrix();
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return getInterpolatedPos(entity, ticks).subtract(LogoutSpots.mc.getRenderManager().renderPosX, LogoutSpots.mc.getRenderManager().renderPosY, LogoutSpots.mc.getRenderManager().renderPosZ);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
}
