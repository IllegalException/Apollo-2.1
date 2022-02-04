//Decomped by XeonLyfe

package com.apollo.client.module;

import net.minecraft.client.*;
import com.apollo.api.event.events.*;
import com.apollo.api.values.*;
import com.apollo.client.*;
import java.util.*;

public class Module
{
    protected static final Minecraft mc;
    String name;
    Category category;
    int bind;
    boolean enabled;
    boolean drawn;
    
    public Module(final String m, final Category c) {
        this.name = m;
        this.category = c;
        this.bind = 0;
        this.enabled = false;
        this.drawn = true;
        this.setup();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String m) {
        this.name = m;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(final Category c) {
        this.category = c;
    }
    
    public int getBind() {
        return this.bind;
    }
    
    public void setBind(final int b) {
        this.bind = b;
    }
    
    protected void onEnable() {
    }
    
    protected void onDisable() {
    }
    
    public void onUpdate() {
    }
    
    public void onRender() {
    }
    
    public void onWorldRender(final RenderEvent event) {
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean e) {
        this.enabled = e;
    }
    
    public void enable() {
        this.setEnabled(true);
        this.onEnable();
    }
    
    public void disable() {
        this.setEnabled(false);
        this.onDisable();
    }
    
    public void toggle() {
        if (this.isEnabled()) {
            this.disable();
        }
        else if (!this.isEnabled()) {
            this.enable();
        }
    }
    
    public String getHudInfo() {
        return "";
    }
    
    public void setup() {
    }
    
    public boolean isDrawn() {
        return this.drawn;
    }
    
    public void setDrawn(final boolean d) {
        this.drawn = d;
    }
    
    protected Value.Integer registerInteger(final String name, final String configname, final int value, final int min, final int max) {
        final Value.Integer s = new Value.Integer(name, configname, this, this.getCategory(), value, min, max);
        Main.getInstance().valuesManager.addSetting((Value)s);
        return s;
    }
    
    protected Value.Double registerDouble(final String name, final String configname, final double value, final double min, final double max) {
        final Value.Double s = new Value.Double(name, configname, this, this.getCategory(), value, min, max);
        Main.getInstance().valuesManager.addSetting((Value)s);
        return s;
    }
    
    protected Value.Boolean registerBoolean(final String name, final String configname, final boolean value) {
        final Value.Boolean s = new Value.Boolean(name, configname, this, this.getCategory(), value);
        Main.getInstance().valuesManager.addSetting((Value)s);
        return s;
    }
    
    protected Value.Mode registerMode(final String name, final String configname, final List<String> modes, final String value) {
        final Value.Mode s = new Value.Mode(name, configname, this, this.getCategory(), (List)modes, value);
        Main.getInstance().valuesManager.addSetting((Value)s);
        return s;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public enum Category
    {
        Combat, 
        Exploits, 
        Movement, 
        Misc, 
        Render, 
        HUD;
    }
}
