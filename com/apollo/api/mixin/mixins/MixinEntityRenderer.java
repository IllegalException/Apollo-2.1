//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.multiplayer.*;
import com.apollo.client.module.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.google.common.base.*;
import com.apollo.client.module.modules.misc.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer
{
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    public RayTraceResult rayTraceBlocks(final WorldClient world, final Vec3d start, final Vec3d end) {
        if (ModuleManager.isModuleEnabled("RenderTweaks") && ((RenderTweaks)ModuleManager.getModuleByName("RenderTweaks")).viewClip.getValue()) {
            return null;
        }
        return world.rayTraceBlocks(start, end);
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
        if (((NoEntityTrace)ModuleManager.getModuleByName("NoEntityTrace")).noTrace()) {
            return new ArrayList<Entity>();
        }
        return (List<Entity>)worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffect(final float ticks, final CallbackInfo info) {
        if (ModuleManager.isModuleEnabled("NoRender") && ((NoRender)ModuleManager.getModuleByName("NoRender")).hurtCam.getValue()) {
            info.cancel();
        }
    }
}
