//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import com.apollo.client.module.modules.misc.*;

public class AutoReplyCommand extends Command
{
    public String[] getAlias() {
        return new String[] { "autoreply" };
    }
    
    public String getSyntax() {
        return "autoreply <message (use \"_\" for spaces)>";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        if (args[0] != null && !args[0].equalsIgnoreCase("")) {
            AutoReply.setReply(args[0].replace("_", " "));
            Command.sendClientMessage("AutoReply message set to " + AutoReply.getReply());
        }
        else {
            Command.sendClientMessage(this.getSyntax());
        }
    }
}
