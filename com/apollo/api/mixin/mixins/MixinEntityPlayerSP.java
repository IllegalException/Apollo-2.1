//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.entity.*;
import com.apollo.api.event.events.*;
import com.apollo.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer
{
    public MixinEntityPlayerSP() {
        super((World)null, (GameProfile)null);
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(final AbstractClientPlayer player, final MoverType type, final double x, final double y, final double z) {
        final PlayerMoveEvent moveEvent = new PlayerMoveEvent(type, x, y, z);
        Main.EVENT_BUS.post((Object)moveEvent);
        if (moveEvent.isCancelled()) {}
        super.move(type, moveEvent.x, moveEvent.y, moveEvent.z);
    }
}
