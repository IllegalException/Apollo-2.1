//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import com.apollo.client.module.modules.movement.*;
import com.apollo.client.module.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Entity.class })
public class MixinEntity
{
    @Redirect(method = { "applyEntityCollision" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void velocity(final Entity entity, final double x, final double y, final double z) {
        if (!((PlayerModifier)ModuleManager.getModuleByName("Player Modifier")).noPush.getValue()) {
            entity.motionX += x;
            entity.motionY += y;
            entity.motionZ += z;
            entity.isAirBorne = true;
        }
    }
}
