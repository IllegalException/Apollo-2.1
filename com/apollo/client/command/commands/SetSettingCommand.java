//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import com.apollo.client.module.*;
import com.apollo.client.*;
import com.apollo.api.values.*;
import java.util.*;

public class SetSettingCommand extends Command
{
    public String[] getAlias() {
        return new String[] { "set" };
    }
    
    public String getSyntax() {
        return "set <Module> <Value> <Value>";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        for (final Module m : ModuleManager.getModules()) {
            if (m.getName().equalsIgnoreCase(args[0])) {
                Main.getInstance().valuesManager.getSettingsForMod(m).stream().filter(s -> s.getConfigName().equalsIgnoreCase(args[1])).forEach(s -> {
                    if (((Value)s).getType().equals((Object)Value.Type.BOOLEAN)) {
                        ((Value.Boolean)s).setValue(Boolean.parseBoolean(args[2]));
                        Command.sendClientMessage(((Value)s).getConfigName() + " set to " + ((Value.Boolean)s).getValue() + "!");
                    }
                    if (((Value)s).getType().equals((Object)Value.Type.INT)) {
                        if (Integer.parseInt(args[2]) > ((Value.Integer)s).getMax()) {
                            ((Value.Integer)s).setValue(((Value.Integer)s).getMax());
                        }
                        if (Integer.parseInt(args[2]) < ((Value.Integer)s).getMin()) {
                            ((Value.Integer)s).setValue(((Value.Integer)s).getMin());
                        }
                        if (Integer.parseInt(args[2]) < ((Value.Integer)s).getMax() && Integer.parseInt(args[2]) > ((Value.Integer)s).getMin()) {
                            ((Value.Integer)s).setValue(Integer.parseInt(args[2]));
                        }
                        Command.sendClientMessage(((Value)s).getConfigName() + " set to " + ((Value.Integer)s).getValue() + "!");
                    }
                    if (((Value)s).getType().equals((Object)Value.Type.DOUBLE)) {
                        if (Double.parseDouble(args[2]) > ((Value.Double)s).getMax()) {
                            ((Value.Double)s).setValue(((Value.Double)s).getMax());
                        }
                        if (Double.parseDouble(args[2]) < ((Value.Double)s).getMin()) {
                            ((Value.Double)s).setValue(((Value.Double)s).getMin());
                        }
                        if (Double.parseDouble(args[2]) < ((Value.Double)s).getMax() && Double.parseDouble(args[2]) > ((Value.Double)s).getMin()) {
                            ((Value.Double)s).setValue(Double.parseDouble(args[2]));
                        }
                        Command.sendClientMessage(((Value)s).getConfigName() + " set to " + ((Value.Double)s).getValue() + "!");
                    }
                    if (((Value)s).getType().equals((Object)Value.Type.MODE)) {
                        if (!s.getModes().contains(args[2])) {
                            Command.sendClientMessage("Invalid input!");
                        }
                        else {
                            s.setValue(args[2]);
                            Command.sendClientMessage(((Value)s).getConfigName() + " set to " + s.getValue() + "!");
                        }
                    }
                });
            }
        }
    }
}
