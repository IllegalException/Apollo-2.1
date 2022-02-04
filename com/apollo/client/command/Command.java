//Decomped by XeonLyfe

package com.apollo.client.command;

import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.client.module.*;
import net.minecraft.util.text.*;
import java.awt.*;

public abstract class Command
{
    static Minecraft mc;
    public static String prefix;
    public static boolean MsgWaterMark;
    public static ChatFormatting cf;
    
    public abstract String[] getAlias();
    
    public abstract String getSyntax();
    
    public abstract void onCommand(final String p0, final String[] p1) throws Exception;
    
    public static void sendClientMessage(final String message) {
        Notifications.addMessage(new TextComponentString(Command.cf + message));
        if (Notifications.disableChat.getValue() && ModuleManager.isModuleEnabled("Notifications")) {
            return;
        }
        if (Command.MsgWaterMark) {
            Command.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + "Apollo" + ChatFormatting.GRAY + "] " + ChatFormatting.RESET + Command.cf + message));
        }
        else {
            Command.mc.player.sendMessage((ITextComponent)new TextComponentString(Command.cf + message));
        }
    }
    
    public static Color getColorFromChatFormatting(final ChatFormatting cf) {
        if (cf == ChatFormatting.BLACK) {
            return Color.BLACK;
        }
        if (cf == ChatFormatting.GRAY) {
            return Color.GRAY;
        }
        if (cf == ChatFormatting.AQUA) {
            return Color.CYAN;
        }
        if (cf == ChatFormatting.BLUE || cf == ChatFormatting.DARK_BLUE || cf == ChatFormatting.DARK_AQUA) {
            return Color.BLUE;
        }
        if (cf == ChatFormatting.DARK_GRAY) {
            return Color.DARK_GRAY;
        }
        if (cf == ChatFormatting.DARK_GREEN || cf == ChatFormatting.GREEN) {
            return Color.GREEN;
        }
        if (cf == ChatFormatting.DARK_PURPLE) {
            return Color.MAGENTA;
        }
        if (cf == ChatFormatting.RED || cf == ChatFormatting.DARK_RED) {
            return Color.RED;
        }
        if (cf == ChatFormatting.LIGHT_PURPLE) {
            return Color.PINK;
        }
        if (cf == ChatFormatting.YELLOW) {
            return Color.YELLOW;
        }
        if (cf == ChatFormatting.GOLD) {
            return Color.ORANGE;
        }
        return Color.WHITE;
    }
    
    public static void sendRawMessage(final String message) {
        Command.mc.player.sendMessage((ITextComponent)new TextComponentString(message));
        Notifications.addMessage(new TextComponentString(Command.cf + message));
        if (Notifications.disableChat.getValue() && ModuleManager.isModuleEnabled("Notifications")) {
            return;
        }
        if (Command.MsgWaterMark) {
            Command.mc.player.sendMessage((ITextComponent)new TextComponentString(ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + "Apollo" + ChatFormatting.GRAY + "] " + ChatFormatting.RESET + Command.cf + message));
        }
        else {
            Command.mc.player.sendMessage((ITextComponent)new TextComponentString(Command.cf + message));
        }
    }
    
    public static String getPrefix() {
        return Command.prefix;
    }
    
    public static void setPrefix(final String p) {
        Command.prefix = p;
    }
    
    static {
        Command.mc = Minecraft.getMinecraft();
        Command.prefix = "-";
        Command.MsgWaterMark = true;
        Command.cf = ChatFormatting.GRAY;
    }
}
