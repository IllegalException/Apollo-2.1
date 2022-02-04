//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.api.event.events.*;
import com.apollo.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ItemRenderer.class })
public class MixinItemRenderer
{
    @Inject(method = { "transformSideFirstPerson" }, at = { @At("HEAD") })
    public void transformSideFirstPerson(final EnumHandSide hand, final float p_187459_2_, final CallbackInfo ci) {
        final TransformSideFirstPersonEvent event = new TransformSideFirstPersonEvent(hand);
        Main.EVENT_BUS.post((Object)event);
    }
}
