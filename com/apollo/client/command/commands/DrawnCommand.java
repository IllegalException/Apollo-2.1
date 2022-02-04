//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import com.mojang.realmsclient.gui.*;
import com.apollo.client.module.*;

public class DrawnCommand extends Command
{
    boolean found;
    
    public String[] getAlias() {
        return new String[] { "drawn", "visible", "d" };
    }
    
    public String getSyntax() {
        return "drawn <Module>";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        this.found = false;
        ModuleManager.getModules().forEach(m -> {
            if (m.getName().equalsIgnoreCase(args[0])) {
                if (m.isDrawn()) {
                    m.setDrawn(false);
                    this.found = true;
                    Command.sendClientMessage(m.getName() + ChatFormatting.RED + " drawn = false");
                }
                else if (!m.isDrawn()) {
                    m.setDrawn(true);
                    this.found = true;
                    Command.sendClientMessage(m.getName() + ChatFormatting.GREEN + " drawn = true");
                }
            }
            return;
        });
        if (!this.found && args.length == 1) {
            Command.sendClientMessage(ChatFormatting.GRAY + "Module not found!");
        }
    }
}
