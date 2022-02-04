//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import java.util.*;
import org.spongepowered.asm.mixin.*;
import com.apollo.client.module.*;
import com.apollo.client.module.modules.misc.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiNewChat.class })
public abstract class MixinGuiNewChat
{
    @Shadow
    private int scrollPos;
    @Shadow
    @Final
    private List<ChatLine> drawnChatLines;
    
    @Shadow
    public abstract int getLineCount();
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    private void drawRectBackgroundClean(final int left, final int top, final int right, final int bottom, final int color) {
        if (!ModuleManager.isModuleEnabled("ChatModifier") || !((ChatModifier)ModuleManager.getModuleByName("ChatModifier")).clearBkg.getValue()) {
            Gui.drawRect(left, top, right, bottom, color);
        }
    }
}
