//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Block.class })
public abstract class MixinBlock
{
    @Shadow
    public abstract String getLocalizedName();
    
    @Inject(method = { "getRenderLayer" }, at = { @At("HEAD") })
    public void preGetRenderLayer(final CallbackInfoReturnable<BlockRenderLayer> cir) {
        if (this.getLocalizedName().equalsIgnoreCase("hmmm")) {}
    }
}
