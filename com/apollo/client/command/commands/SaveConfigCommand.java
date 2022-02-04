//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import com.apollo.api.*;

public class SaveConfigCommand extends Command
{
    public String[] getAlias() {
        return new String[] { "saveconfig", "savecfg" };
    }
    
    public String getSyntax() {
        return "saveconfig";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        Stopper.saveConfig();
        Command.sendClientMessage("Config saved!");
    }
}
