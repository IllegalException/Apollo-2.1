//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import com.apollo.client.module.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraft.client.*;

public class ModsCommand extends Command
{
    public String[] getAlias() {
        return new String[] { "modules", "mods" };
    }
    
    public String getSyntax() {
        return "modules";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        final int size = ModuleManager.getModules().size();
        final TextComponentString msg = new TextComponentString("�7Modules: �f ");
        for (int i = 0; i < size; ++i) {
            final Module mod = ModuleManager.getModules().get(i);
            if (mod != null) {
                msg.appendSibling(new TextComponentString((mod.isEnabled() ? "�a" : "�c") + mod.getName() + "�7" + ((i == size - 1) ? "" : ", ")).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(mod.getCategory().name()))).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, Command.getPrefix() + "toggle " + mod.getName()))));
            }
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((ITextComponent)msg);
    }
}
