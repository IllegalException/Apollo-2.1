//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;

public class RenderTweaks extends Module
{
    public Value.Boolean viewClip;
    Value.Boolean nekoAnimation;
    Value.Boolean lowOffhand;
    Value.Boolean fovChanger;
    Value.Double lowOffhandSlider;
    Value.Integer fovChangerSlider;
    ItemRenderer itemRenderer;
    private float oldFOV;
    
    public RenderTweaks() {
        super("RenderTweaks", Module.Category.Render);
        this.itemRenderer = RenderTweaks.mc.entityRenderer.itemRenderer;
    }
    
    public void setup() {
        this.viewClip = this.registerBoolean("View Clip", "ViewClip", false);
        this.nekoAnimation = this.registerBoolean("Neko Animation", "NekoAnimation", false);
        this.lowOffhand = this.registerBoolean("Low Offhand", "LowOffhand", false);
        this.lowOffhandSlider = this.registerDouble("Offhand Height", "OffhandHeight", 1.0, 0.1, 1.0);
        this.fovChanger = this.registerBoolean("FOV", "FOV", false);
        this.fovChangerSlider = this.registerInteger("FOV Slider", "FOVSlider", 90, 70, 200);
    }
    
    public void onUpdate() {
        if (this.nekoAnimation.getValue() && RenderTweaks.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && RenderTweaks.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            RenderTweaks.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            RenderTweaks.mc.entityRenderer.itemRenderer.itemStackMainHand = RenderTweaks.mc.player.getHeldItemMainhand();
        }
        if (this.lowOffhand.getValue()) {
            this.itemRenderer.equippedProgressOffHand = (float)this.lowOffhandSlider.getValue();
        }
        if (this.fovChanger.getValue()) {
            RenderTweaks.mc.gameSettings.fovSetting = (float)this.fovChangerSlider.getValue();
        }
        if (!this.fovChanger.getValue()) {
            RenderTweaks.mc.gameSettings.fovSetting = this.oldFOV;
        }
    }
    
    public void onEnable() {
        this.oldFOV = RenderTweaks.mc.gameSettings.fovSetting;
    }
    
    public void onDisable() {
        RenderTweaks.mc.gameSettings.fovSetting = this.oldFOV;
    }
}
