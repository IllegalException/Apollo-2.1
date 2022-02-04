//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.api.values.*;
import java.util.*;
import com.apollo.api.event.events.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.client.module.*;
import com.apollo.api.util.color.*;
import java.awt.*;
import com.apollo.api.util.render.*;
import net.minecraft.entity.item.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.entity.*;

public class EntityESP extends Module
{
    Value.Mode renderMode;
    Value.Boolean exp;
    Value.Boolean epearls;
    Value.Boolean items;
    Value.Boolean orbs;
    Value.Boolean crystals;
    int c;
    int c2;
    
    public EntityESP() {
        super("EntityESP", Module.Category.Render);
    }
    
    public void setup() {
        final ArrayList<String> Modes = new ArrayList<String>();
        Modes.add("Box");
        Modes.add("Outline");
        Modes.add("Glow");
        this.exp = this.registerBoolean("Exp Bottles", "ExpBottles", false);
        this.epearls = this.registerBoolean("Ender Pearls", "EnderPearls", false);
        this.crystals = this.registerBoolean("Crystals", "Crystals", false);
        this.items = this.registerBoolean("Items", "Items", false);
        this.orbs = this.registerBoolean("Exp Orbs", "ExpOrbs", false);
        this.renderMode = this.registerMode("Mode", "Mode", (List)Modes, "Box");
    }
    
    public void onWorldRender(final RenderEvent event) {
        final ColorMain colorMain = (ColorMain)ModuleManager.getModuleByName("Colors");
        if (ColorMain.rainbow.getValue()) {
            this.c = Rainbow.getColorWithOpacity(50).getRGB();
        }
        else {
            this.c = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), 50).getRGB();
        }
        if (ColorMain.rainbow.getValue()) {
            this.c2 = Rainbow.getColorWithOpacity(255).getRGB();
        }
        else {
            this.c2 = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), 255).getRGB();
        }
        if (this.renderMode.getValue().equalsIgnoreCase("Box")) {
            EntityESP.mc.world.loadedEntityList.stream().filter(entity -> entity != EntityESP.mc.player).forEach(e -> {
                ApolloTessellator.prepare(7);
                if (this.exp.getValue() && e instanceof EntityExpBottle) {
                    ApolloTessellator.drawBox(e.getRenderBoundingBox(), this.c, 63);
                }
                if (this.epearls.getValue() && e instanceof EntityEnderPearl) {
                    ApolloTessellator.drawBox(e.getRenderBoundingBox(), this.c, 63);
                }
                if (this.crystals.getValue() && e instanceof EntityEnderCrystal) {
                    ApolloTessellator.drawBox(e.getRenderBoundingBox(), this.c, 63);
                }
                if (this.items.getValue() && e instanceof EntityItem) {
                    ApolloTessellator.drawBox(e.getRenderBoundingBox(), this.c, 63);
                }
                if (this.orbs.getValue() && e instanceof EntityXPOrb) {
                    ApolloTessellator.drawBox(e.getRenderBoundingBox(), this.c, 63);
                }
                ApolloTessellator.release();
                ApolloTessellator.prepareGL();
                if (this.exp.getValue() && e instanceof EntityExpBottle) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                if (this.epearls.getValue() && e instanceof EntityEnderPearl) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                if (this.crystals.getValue() && e instanceof EntityEnderCrystal) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                if (this.items.getValue() && e instanceof EntityItem) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                if (this.orbs.getValue() && e instanceof EntityXPOrb) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                ApolloTessellator.releaseGL();
                return;
            });
        }
        if (this.renderMode.getValue().equalsIgnoreCase("Outline")) {
            EntityESP.mc.world.loadedEntityList.stream().filter(entity -> entity != EntityESP.mc.player).forEach(e -> {
                ApolloTessellator.prepareGL();
                if (this.exp.getValue() && e instanceof EntityExpBottle) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                if (this.epearls.getValue() && e instanceof EntityEnderPearl) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                if (this.crystals.getValue() && e instanceof EntityEnderCrystal) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                if (this.items.getValue() && e instanceof EntityItem) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                if (this.orbs.getValue() && e instanceof EntityXPOrb) {
                    ApolloTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1.0f, this.c2);
                }
                ApolloTessellator.releaseGL();
                return;
            });
        }
        if (this.renderMode.getValue().equalsIgnoreCase("Glow")) {
            EntityESP.mc.world.loadedEntityList.stream().filter(e -> e != EntityESP.mc.player).forEach(e -> {
                if (this.exp.getValue() && e instanceof EntityExpBottle) {
                    e.setGlowing(true);
                }
                if (this.epearls.getValue() && e instanceof EntityEnderPearl) {
                    e.setGlowing(true);
                }
                if (this.crystals.getValue() && e instanceof EntityEnderCrystal) {
                    e.setGlowing(true);
                }
                if (this.items.getValue() && e instanceof EntityItem) {
                    e.setGlowing(true);
                }
                if (this.orbs.getValue() && e instanceof EntityXPOrb) {
                    e.setGlowing(true);
                }
            });
        }
    }
    
    public void onUpdate() {
        EntityESP.mc.world.loadedEntityList.stream().filter(e -> e != EntityESP.mc.player).forEach(e -> {
            if (!this.renderMode.getValue().equalsIgnoreCase("Glow")) {
                if (e instanceof EntityExpBottle) {
                    e.setGlowing(false);
                }
                if (e instanceof EntityEnderPearl) {
                    e.setGlowing(false);
                }
                if (e instanceof EntityEnderCrystal) {
                    e.setGlowing(false);
                }
                if (e instanceof EntityItem) {
                    e.setGlowing(false);
                }
                if (e instanceof EntityXPOrb) {
                    e.setGlowing(false);
                }
            }
            if (!this.exp.getValue() && e instanceof EntityExpBottle) {
                e.setGlowing(false);
            }
            if (!this.epearls.getValue() && e instanceof EntityEnderPearl) {
                e.setGlowing(false);
            }
            if (!this.crystals.getValue() && e instanceof EntityEnderCrystal) {
                e.setGlowing(false);
            }
            if (!this.items.getValue() && e instanceof EntityItem) {
                e.setGlowing(false);
            }
            if (!this.orbs.getValue() && e instanceof EntityXPOrb) {
                e.setGlowing(false);
            }
        });
    }
    
    public void onDisable() {
        if (this.renderMode.getValue().equalsIgnoreCase("Glow")) {
            EntityESP.mc.world.loadedEntityList.stream().filter(e -> e != EntityESP.mc.player).forEach(e -> {
                if (e instanceof EntityExpBottle) {
                    e.setGlowing(false);
                }
                if (e instanceof EntityEnderPearl) {
                    e.setGlowing(false);
                }
                if (e instanceof EntityEnderCrystal) {
                    e.setGlowing(false);
                }
                if (e instanceof EntityItem) {
                    e.setGlowing(false);
                }
                if (e instanceof EntityXPOrb) {
                    e.setGlowing(false);
                }
            });
        }
    }
    
    public String getHudInfo() {
        String t = "";
        if (this.renderMode.getValue().equalsIgnoreCase("Box")) {
            t = "[" + ChatFormatting.WHITE + "Box" + ChatFormatting.GRAY + "]";
        }
        if (this.renderMode.getValue().equalsIgnoreCase("Outline")) {
            t = "[" + ChatFormatting.WHITE + "Outline" + ChatFormatting.GRAY + "]";
        }
        if (this.renderMode.getValue().equalsIgnoreCase("Glow")) {
            t = "[" + ChatFormatting.WHITE + "Glow" + ChatFormatting.GRAY + "]";
        }
        return t;
    }
}
