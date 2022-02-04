//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.client.event.*;
import com.apollo.api.event.events.*;
import java.util.function.*;
import net.minecraft.init.*;
import com.apollo.client.*;
import net.minecraft.block.material.*;

public class NoRender extends Module
{
    public Value.Boolean armor;
    Value.Boolean fire;
    Value.Boolean blind;
    Value.Boolean nausea;
    public Value.Boolean hurtCam;
    public Value.Boolean noOverlay;
    Value.Boolean noBossBar;
    public Value.Boolean noSkylight;
    @EventHandler
    public Listener<RenderBlockOverlayEvent> blockOverlayEventListener;
    @EventHandler
    private final Listener<EntityViewRenderEvent.FogDensity> fogDensityListener;
    @EventHandler
    private final Listener<RenderBlockOverlayEvent> renderBlockOverlayEventListener;
    @EventHandler
    private final Listener<RenderGameOverlayEvent> renderGameOverlayEventListener;
    @EventHandler
    private final Listener<BossbarEvent> bossbarEventListener;
    
    public NoRender() {
        super("NoRender", Module.Category.Render);
        this.blockOverlayEventListener = (Listener<RenderBlockOverlayEvent>)new Listener(event -> {
            if (this.fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
                event.setCanceled(true);
            }
            if (this.noOverlay.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER) {
                event.setCanceled(true);
            }
            if (this.noOverlay.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK) {
                event.setCanceled(true);
            }
        }, new Predicate[0]);
        this.fogDensityListener = (Listener<EntityViewRenderEvent.FogDensity>)new Listener(event -> {
            if (this.noOverlay.getValue() && (event.getState().getMaterial().equals(Material.WATER) || event.getState().getMaterial().equals(Material.LAVA))) {
                event.setDensity(0.0f);
                event.setCanceled(true);
            }
        }, new Predicate[0]);
        this.renderBlockOverlayEventListener = (Listener<RenderBlockOverlayEvent>)new Listener(event -> event.setCanceled(true), new Predicate[0]);
        this.renderGameOverlayEventListener = (Listener<RenderGameOverlayEvent>)new Listener(event -> {
            if (this.noOverlay.getValue()) {
                if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.HELMET)) {
                    event.setCanceled(true);
                }
                if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.PORTAL)) {
                    event.setCanceled(true);
                }
            }
        }, new Predicate[0]);
        this.bossbarEventListener = (Listener<BossbarEvent>)new Listener(event -> {
            if (this.noBossBar.getValue()) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        this.armor = this.registerBoolean("Armor", "Armor", false);
        this.fire = this.registerBoolean("Fire", "Fire", false);
        this.blind = this.registerBoolean("Blind", "Blind", false);
        this.nausea = this.registerBoolean("Nausea", "Nausea", false);
        this.hurtCam = this.registerBoolean("HurtCam", "HurtCam", false);
        this.noSkylight = this.registerBoolean("Skylight", "Skylight", false);
        this.noOverlay = this.registerBoolean("No Overlay", "NoOverlay", false);
        this.noBossBar = this.registerBoolean("No Boss Bar", "NoBossBar", false);
    }
    
    public void onUpdate() {
        if (this.blind.getValue() && NoRender.mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            NoRender.mc.player.removePotionEffect(MobEffects.BLINDNESS);
        }
        if (this.nausea.getValue() && NoRender.mc.player.isPotionActive(MobEffects.NAUSEA)) {
            NoRender.mc.player.removePotionEffect(MobEffects.NAUSEA);
        }
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
