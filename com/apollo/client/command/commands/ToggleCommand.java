//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import com.mojang.realmsclient.gui.*;
import com.apollo.client.module.*;

public class ToggleCommand extends Command
{
    boolean found;
    
    public String[] getAlias() {
        return new String[] { "toggle", "t" };
    }
    
    public String getSyntax() {
        return "toggle <Module>";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        this.found = false;
        ModuleManager.getModules().forEach(m -> {
            if (m.getName().equalsIgnoreCase(args[0])) {
                if (m.isEnabled()) {
                    m.disable();
                    this.found = true;
                    Command.sendClientMessage(m.getName() + ChatFormatting.RED + " disabled!");
                }
                else if (!m.isEnabled()) {
                    m.enable();
                    this.found = true;
                    Command.sendClientMessage(m.getName() + ChatFormatting.GREEN + " enabled!");
                }
            }
            return;
        });
        if (!this.found && args.length == 1) {
            Command.sendClientMessage(ChatFormatting.GRAY + "Module not found!");
        }
    }
}
