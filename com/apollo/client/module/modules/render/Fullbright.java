//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import java.util.*;
import net.minecraft.potion.*;

public class Fullbright extends Module
{
    float old;
    Value.Mode Mode;
    
    public Fullbright() {
        super("Fullbright", Module.Category.Render);
    }
    
    public void setup() {
        final ArrayList<String> modes = new ArrayList<String>();
        modes.add("Gamma");
        modes.add("Potion");
        this.Mode = this.registerMode("Mode", "Mode", (List)modes, "Gamma");
    }
    
    public void onEnable() {
        this.old = Fullbright.mc.gameSettings.gammaSetting;
    }
    
    public void onUpdate() {
        if (this.Mode.getValue().equalsIgnoreCase("Gamma")) {
            Fullbright.mc.gameSettings.gammaSetting = 666.0f;
            Fullbright.mc.player.removePotionEffect(Potion.getPotionById(16));
        }
        else if (this.Mode.getValue().equalsIgnoreCase("Potion")) {
            final PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(16), 123456789, 5);
            potionEffect.setPotionDurationMax(true);
            Fullbright.mc.player.addPotionEffect(potionEffect);
        }
    }
    
    public void onDisable() {
        Fullbright.mc.gameSettings.gammaSetting = this.old;
        Fullbright.mc.player.removePotionEffect(Potion.getPotionById(16));
    }
}
