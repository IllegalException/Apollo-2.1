//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.*;
import net.minecraft.block.properties.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockLiquid.class })
public class MixinBlockLiquid
{
    @Inject(method = { "canCollideCheck" }, at = { @At("HEAD") }, cancellable = true)
    public void canCollideCheck(final IBlockState blockState, final boolean b, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((Object)(ModuleManager.isModuleEnabled("LiquidInteract") || (b && (int)blockState.getValue((IProperty)BlockLiquid.LEVEL) == 0)));
    }
}
