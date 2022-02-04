//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import com.apollo.client.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.api.event.events.*;

@Mixin({ EntityPlayer.class })
public abstract class MixinEntityPlayer
{
    @Shadow
    public abstract String getName();
    
    @Inject(method = { "jump" }, at = { @At("HEAD") }, cancellable = true)
    public void onJump(final CallbackInfo ci) {
        if (Minecraft.getMinecraft().player.getName() == this.getName()) {
            Main.EVENT_BUS.post((Object)new PlayerJumpEvent());
        }
    }
    
    @Inject(method = { "isPushedByWater" }, at = { @At("HEAD") }, cancellable = true)
    private void onPushedByWater(final CallbackInfoReturnable<Boolean> cir) {
        final WaterPushEvent event = new WaterPushEvent();
        Main.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            cir.setReturnValue((Object)false);
        }
    }
}
