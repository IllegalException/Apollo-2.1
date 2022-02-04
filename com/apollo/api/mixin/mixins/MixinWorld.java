//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.*;
import com.apollo.client.module.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ World.class })
public class MixinWorld
{
    @Inject(method = { "checkLightFor" }, at = { @At("HEAD") }, cancellable = true)
    private void updateLightmapHook(final EnumSkyBlock lightType, final BlockPos pos, final CallbackInfoReturnable<Boolean> info) {
        if (ModuleManager.isModuleEnabled("NoRender") && ((NoRender)ModuleManager.getModuleByName("NoRender")).noSkylight.getValue() && lightType == EnumSkyBlock.SKY) {
            info.setReturnValue((Object)true);
            info.cancel();
        }
    }
}
