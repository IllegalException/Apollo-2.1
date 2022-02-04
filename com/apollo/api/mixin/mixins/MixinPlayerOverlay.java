//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.*;
import com.apollo.client.module.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiIngame.class })
public class MixinPlayerOverlay
{
    @Inject(method = { "renderPumpkinOverlay" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPumpkinOverlayHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (ModuleManager.isModuleEnabled("NoRender") && ((NoRender)ModuleManager.getModuleByName("NoRender")).noOverlay.getValue()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPotionEffectsHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (ModuleManager.isModuleEnabled("NoRender") && ((NoRender)ModuleManager.getModuleByName("NoRender")).noOverlay.getValue()) {
            info.cancel();
        }
    }
}
