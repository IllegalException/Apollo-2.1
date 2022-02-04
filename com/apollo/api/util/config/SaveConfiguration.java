//Decomped by XeonLyfe

package com.apollo.api.util.config;

import net.minecraft.client.*;
import java.io.*;
import com.apollo.client.clickgui.*;
import com.apollo.client.clickgui.frame.*;
import java.util.*;
import com.apollo.client.*;
import com.apollo.client.macro.*;
import org.lwjgl.input.*;
import com.apollo.api.players.friends.*;
import com.apollo.api.players.enemy.*;
import com.apollo.client.command.*;
import com.apollo.client.module.modules.misc.*;
import com.apollo.client.module.*;

public class SaveConfiguration
{
    Minecraft mc;
    public static File GameSenseDev;
    public static File Modules;
    public static File Messages;
    public static File Miscellaneous;
    public static File Combat;
    public static File Exploits;
    public static File Hud;
    public static File Misc;
    public static File Movement;
    public static File Render;
    
    public SaveConfiguration() {
        this.mc = Minecraft.getMinecraft();
        SaveConfiguration.GameSenseDev = new File(this.mc.gameDir + File.separator + "Apollo");
        if (!SaveConfiguration.GameSenseDev.exists()) {
            SaveConfiguration.GameSenseDev.mkdirs();
        }
        SaveConfiguration.Modules = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Modules");
        if (!SaveConfiguration.Modules.exists()) {
            SaveConfiguration.Modules.mkdirs();
        }
        SaveConfiguration.Messages = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Messages");
        if (!SaveConfiguration.Messages.exists()) {
            SaveConfiguration.Messages.mkdirs();
        }
        SaveConfiguration.Miscellaneous = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Miscellaneous");
        if (!SaveConfiguration.Miscellaneous.exists()) {
            SaveConfiguration.Miscellaneous.mkdirs();
        }
        SaveConfiguration.Combat = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Modules" + File.separator + "Combat");
        if (!SaveConfiguration.Combat.exists()) {
            SaveConfiguration.Combat.mkdirs();
        }
        SaveConfiguration.Exploits = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Modules" + File.separator + "Exploits");
        if (!SaveConfiguration.Exploits.exists()) {
            SaveConfiguration.Exploits.mkdirs();
        }
        SaveConfiguration.Hud = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Modules" + File.separator + "Hud");
        if (!SaveConfiguration.Hud.exists()) {
            SaveConfiguration.Hud.mkdirs();
        }
        SaveConfiguration.Misc = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Modules" + File.separator + "Misc");
        if (!SaveConfiguration.Misc.exists()) {
            SaveConfiguration.Misc.mkdirs();
        }
        SaveConfiguration.Movement = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Modules" + File.separator + "Movement");
        if (!SaveConfiguration.Movement.exists()) {
            SaveConfiguration.Movement.mkdirs();
        }
        SaveConfiguration.Render = new File(this.mc.gameDir + File.separator + "Apollo" + File.separator + "Modules" + File.separator + "Render");
        if (!SaveConfiguration.Render.exists()) {
            SaveConfiguration.Render.mkdirs();
        }
    }
    
    public static void saveGUI() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ClickGUI.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Frames frames : ClickGUI.frames) {
                out.write(frames.category + ":" + frames.getX() + ":" + frames.getY() + ":" + frames.isOpen());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveMacros() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ClientMacros.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Macro m : Main.getInstance().macroManager.getMacros()) {
                out.write(Keyboard.getKeyName(m.getKey()) + ":" + m.getValue().replace(" ", "_"));
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveFriends() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Friends.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Friend f : Friends.getFriends()) {
                out.write(f.getName());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveEnemies() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Enemies.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Enemy e : Enemies.getEnemies()) {
                out.write(e.getName());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void savePrefix() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "CommandPrefix.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(Command.getPrefix());
            out.write("\r\n");
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveFont() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "CustomFont.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write("Comic Sans:20");
            out.write("");
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveAutoGG() {
        try {
            final File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "AutoGG.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final String s : AutoGG.getAutoGgMessages()) {
                out.write(s);
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveAutoReply() {
        try {
            final File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "AutoReply.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(AutoReply.getReply());
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveMessages() {
        try {
            final File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "ClientMessages.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(Command.MsgWaterMark + "");
            out.write(",");
            out.write(Command.cf.getName());
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveDrawn() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "DrawnModules.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Module module : ModuleManager.getModules()) {
                out.write(module.getName() + ":" + module.isDrawn());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveEnabled() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "EnabledModules.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Module module : ModuleManager.getModules()) {
                if (module.isEnabled()) {
                    out.write(module.getName());
                    out.write("\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public static void saveBinds() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ModuleBinds.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Module module : ModuleManager.getModules()) {
                out.write(module.getName() + ":" + Keyboard.getKeyName(module.getBind()));
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
}
