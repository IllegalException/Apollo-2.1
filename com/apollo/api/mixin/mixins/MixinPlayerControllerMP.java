//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import com.apollo.client.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.*;
import com.apollo.api.event.events.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.*;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP
{
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V") }, cancellable = true)
    private void onPlayerDestroyBlock(final BlockPos pos, final CallbackInfoReturnable<Boolean> info) {
        Main.EVENT_BUS.post((Object)new DestroyBlockEvent(pos));
    }
    
    @Inject(method = { "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> cir) {
        final DamageBlockEvent event = new DamageBlockEvent(posBlock, directionFacing);
        Main.EVENT_BUS.post((Object)event);
        if (event.isCancelled()) {
            cir.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    private void resetBlock(final CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled("MultiTask")) {
            ci.cancel();
        }
    }
}
