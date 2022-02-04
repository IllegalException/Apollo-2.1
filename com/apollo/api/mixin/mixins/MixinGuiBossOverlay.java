//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.api.event.events.*;
import com.apollo.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiBossOverlay.class })
public class MixinGuiBossOverlay
{
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    private void renderBossHealth(final CallbackInfo ci) {
        final BossbarEvent event = new BossbarEvent();
        Main.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
