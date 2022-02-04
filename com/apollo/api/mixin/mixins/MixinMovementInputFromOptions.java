//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import com.apollo.client.module.*;
import com.apollo.client.module.modules.movement.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { MovementInputFromOptions.class }, priority = 10000)
public abstract class MixinMovementInputFromOptions extends MovementInput
{
    @Redirect(method = { "updatePlayerMoveState" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(final KeyBinding keyBinding) {
        if (ModuleManager.isModuleEnabled("Player Modifier") && ((PlayerModifier)ModuleManager.getModuleByName("Player Modifier")).guiMove.getValue() && Minecraft.getMinecraft().currentScreen != null && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat) && Minecraft.getMinecraft().player != null) {
            return Keyboard.isKeyDown(keyBinding.getKeyCode());
        }
        return keyBinding.isKeyDown();
    }
}
