//Decomped by XeonLyfe

package com.apollo.client.module.modules.hud;

import com.apollo.api.values.*;
import java.util.*;
import com.apollo.client.*;
import net.minecraft.client.gui.*;
import com.apollo.client.module.modules.misc.*;
import com.apollo.client.module.*;
import com.apollo.client.command.*;

public class ClickGuiModule extends Module
{
    public ClickGuiModule INSTANCE;
    public Value.Boolean customFont;
    public static Value.Integer opacity;
    public static Value.Mode icon;
    
    public ClickGuiModule() {
        super("ClickGUI", Module.Category.HUD);
        this.setBind(25);
        this.setDrawn(false);
        this.INSTANCE = this;
    }
    
    public void setup() {
        final ArrayList<String> icons = new ArrayList<String>();
        icons.add("Font");
        icons.add("Image");
        ClickGuiModule.opacity = this.registerInteger("Opacity", "Opacity", 200, 50, 255);
        ClickGuiModule.icon = this.registerMode("Icon", "Icons", (List)icons, "Image");
    }
    
    public void onEnable() {
        ClickGuiModule.mc.displayGuiScreen((GuiScreen)Main.getInstance().clickGUI);
        if (((Announcer)ModuleManager.getModuleByName("Announcer")).clickGui.getValue() && ModuleManager.isModuleEnabled("Announcer") && ClickGuiModule.mc.player != null) {
            if (((Announcer)ModuleManager.getModuleByName("Announcer")).clientSide.getValue()) {
                Command.sendClientMessage(Announcer.guiMessage);
            }
            else {
                ClickGuiModule.mc.player.sendChatMessage(Announcer.guiMessage);
            }
        }
        this.disable();
    }
    
    private void drawStringWithShadow(final String text, final int x, final int y, final int color) {
        if (this.customFont.getValue()) {
            Main.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        else {
            ClickGuiModule.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
        }
    }
}
