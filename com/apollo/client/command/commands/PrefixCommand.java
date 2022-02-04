//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;

public class PrefixCommand extends Command
{
    public String[] getAlias() {
        return new String[] { "prefix", "setprefix" };
    }
    
    public String getSyntax() {
        return "prefix <character>";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        Command.setPrefix(args[0]);
        Command.sendClientMessage("Command prefix set to " + Command.getPrefix());
    }
}
