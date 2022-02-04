//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import org.lwjgl.input.*;
import com.apollo.client.module.*;

public class BindCommand extends Command
{
    public String[] getAlias() {
        return new String[] { "bind", "b" };
    }
    
    public String getSyntax() {
        return "bind <Module> <Key>";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        final int key = Keyboard.getKeyIndex(args[1].toUpperCase());
        final int bind;
        ModuleManager.getModules().forEach(m -> {
            if (args[0].equalsIgnoreCase(m.getName())) {
                m.setBind(bind);
                Command.sendClientMessage(args[0] + " bound to " + args[1].toUpperCase());
            }
        });
    }
}
