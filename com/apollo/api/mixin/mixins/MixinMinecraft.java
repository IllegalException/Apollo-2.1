//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.api.event.events.*;
import com.apollo.client.*;
import com.apollo.client.module.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { Minecraft.class }, priority = 9999)
public class MixinMinecraft
{
    @Shadow
    public EntityPlayerSP player;
    @Shadow
    public PlayerControllerMP playerController;
    
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") })
    private void displayGuiScreen(final GuiScreen guiScreenIn, final CallbackInfo info) {
        final GuiScreenDisplayedEvent screenEvent = new GuiScreenDisplayedEvent(guiScreenIn);
        Main.EVENT_BUS.post((Object)screenEvent);
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActive(final EntityPlayerSP player) {
        return !ModuleManager.isModuleEnabled("MultiTask") && this.player.isHandActive();
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    private boolean isHittingBlock(final PlayerControllerMP playerControllerMP) {
        return !ModuleManager.isModuleEnabled("MultiTask") && this.playerController.getIsHittingBlock();
    }
}
