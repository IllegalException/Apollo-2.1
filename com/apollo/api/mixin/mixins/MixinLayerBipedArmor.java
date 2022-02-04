//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.model.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.*;
import com.apollo.client.module.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ LayerBipedArmor.class })
public class MixinLayerBipedArmor
{
    @Inject(method = { "setModelSlotVisible" }, at = { @At("HEAD") }, cancellable = true)
    protected void setModelSlotVisible(final ModelBiped model, final EntityEquipmentSlot slotIn, final CallbackInfo info) {
        if (ModuleManager.isModuleEnabled("NoRender") && ((NoRender)ModuleManager.getModuleByName("NoRender")).armor.getValue()) {
            info.cancel();
            switch (slotIn) {
                case HEAD: {
                    model.bipedHead.showModel = false;
                    model.bipedHeadwear.showModel = false;
                }
                case CHEST: {
                    model.bipedBody.showModel = false;
                    model.bipedRightArm.showModel = false;
                    model.bipedLeftArm.showModel = false;
                }
                case LEGS: {
                    model.bipedBody.showModel = false;
                    model.bipedRightLeg.showModel = false;
                    model.bipedLeftLeg.showModel = false;
                }
                case FEET: {
                    model.bipedRightLeg.showModel = false;
                    model.bipedLeftLeg.showModel = false;
                    break;
                }
            }
        }
    }
}
