//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderPlayer.class })
public abstract class MixinNametags
{
    @Inject(method = { "renderEntityName" }, at = { @At("HEAD") }, cancellable = true)
    private void renderLivingLabel(final AbstractClientPlayer entity, final double x, final double y, final double z, final String name, final double distanceSq, final CallbackInfo callback) {
        if (ModuleManager.isModuleEnabled("Nametags")) {
            callback.cancel();
        }
    }
}
