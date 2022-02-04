//Decomped by XeonLyfe

package com.apollo.client.command;

import java.util.*;
import com.apollo.client.command.commands.*;
import com.mojang.realmsclient.gui.*;

public class CommandManager
{
    private static ArrayList<Command> commands;
    boolean b;
    
    public static void initCommands() {
        CommandManager.commands = new ArrayList<Command>();
        addCommand(new AutoGgCommand());
        addCommand(new AutoReplyCommand());
        addCommand(new BindCommand());
        addCommand(new CmdsCommand());
        addCommand(new DrawnCommand());
        addCommand(new EnemyCommand());
        addCommand(new FriendCommand());
        addCommand(new MacroCommand());
        addCommand(new ModsCommand());
        addCommand(new OpenFolderCommand());
        addCommand(new PrefixCommand());
        addCommand(new SaveConfigCommand());
        addCommand(new SetSettingCommand());
        addCommand(new ToggleCommand());
        addCommand(new VanishCommand());
    }
    
    public static void addCommand(final Command c) {
        CommandManager.commands.add(c);
    }
    
    public static ArrayList<Command> getCommands() {
        return CommandManager.commands;
    }
    
    public void callCommand(final String input) {
        final String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        final String command = split[0];
        final String args = input.substring(command.length()).trim();
        this.b = false;
        final String[] array;
        int length;
        int i = 0;
        String s;
        final String s2;
        final String s3;
        CommandManager.commands.forEach(c -> {
            c.getAlias();
            for (length = array.length; i < length; ++i) {
                s = array[i];
                if (s.equalsIgnoreCase(s2)) {
                    this.b = true;
                    try {
                        c.onCommand(s3, s3.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
                    }
                    catch (Exception e) {
                        Command.sendClientMessage(ChatFormatting.GRAY + c.getSyntax());
                    }
                }
            }
            return;
        });
        if (!this.b) {
            Command.sendClientMessage(ChatFormatting.GRAY + "Unknown command!");
        }
    }
}
