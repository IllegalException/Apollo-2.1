//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import com.apollo.client.*;
import org.lwjgl.input.*;
import com.apollo.client.macro.*;
import com.mojang.realmsclient.gui.*;

public class MacroCommand extends Command
{
    public String[] getAlias() {
        return new String[] { "macro", "macros" };
    }
    
    public String getSyntax() {
        return "macro <add | del> <key> <value>";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        if (args[0].equalsIgnoreCase("add")) {
            Main.getInstance().macroManager.delMacro(Main.getInstance().macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1])));
            Main.getInstance().macroManager.addMacro(new Macro(Keyboard.getKeyIndex(args[1].toUpperCase()), args[2].replace("_", " ")));
            Command.sendClientMessage(ChatFormatting.GREEN + "Added" + ChatFormatting.GRAY + " macro for key \"" + args[1].toUpperCase() + "\" with value \"" + args[2].replace("_", " ") + "\".");
        }
        if (args[0].equalsIgnoreCase("del")) {
            if (Main.getInstance().macroManager.getMacros().contains(Main.getInstance().macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1].toUpperCase())))) {
                Main.getInstance().macroManager.delMacro(Main.getInstance().macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1].toUpperCase())));
                Command.sendClientMessage(ChatFormatting.RED + "Removed " + ChatFormatting.GRAY + "macro for key \"" + args[1].toUpperCase() + "\".");
            }
            else {
                Command.sendClientMessage(ChatFormatting.GRAY + "That macro doesn't exist!");
            }
        }
    }
}
