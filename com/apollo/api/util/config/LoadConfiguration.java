//Decomped by XeonLyfe

package com.apollo.api.util.config;

import java.io.*;
import com.apollo.client.clickgui.*;
import com.apollo.client.clickgui.frame.*;
import com.apollo.client.*;
import org.lwjgl.input.*;
import com.apollo.client.macro.*;
import com.apollo.api.players.friends.*;
import com.apollo.api.players.enemy.*;
import com.apollo.client.command.*;
import java.awt.*;
import com.apollo.api.util.font.*;
import com.apollo.client.module.modules.misc.*;
import com.mojang.realmsclient.gui.*;
import com.apollo.client.module.*;
import java.util.*;

public class LoadConfiguration
{
    public LoadConfiguration() {
        this.loadAutoGG();
        this.loadAutoReply();
        this.loadBinds();
        this.loadDrawn();
        this.loadEnabled();
        this.loadEnemies();
        this.loadFont();
        this.loadFriends();
        this.loadGUI();
        this.loadMacros();
        this.loadMessages();
        this.loadPrefix();
    }
    
    public void loadGUI() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ClickGUI.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                final String x = curLine.split(":")[1];
                final String y = curLine.split(":")[2];
                final String e = curLine.split(":")[3];
                final int x2 = Integer.parseInt(x);
                final int y2 = Integer.parseInt(y);
                final boolean open = Boolean.parseBoolean(e);
                final Frames frames = ClickGUI.getFrameByName(name);
                if (frames != null) {
                    frames.x = x2;
                    frames.y = y2;
                    frames.open = open;
                }
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveGUI();
        }
    }
    
    public void loadMacros() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ClientMacros.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String bind = curLine.split(":")[0];
                final String value = curLine.split(":")[1];
                Main.getInstance().macroManager.addMacro(new Macro(Keyboard.getKeyIndex(bind), value.replace("_", " ")));
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveMacros();
        }
    }
    
    public void loadFriends() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Friends.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Friends.friends.clear();
            String line;
            while ((line = br.readLine()) != null) {
                Main.getInstance().friends.addFriend(line);
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveFriends();
        }
    }
    
    public void loadEnemies() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Enemies.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Enemies.enemies.clear();
            String line;
            while ((line = br.readLine()) != null) {
                Enemies.addEnemy(line);
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveEnemies();
        }
    }
    
    public void loadPrefix() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "CommandPrefix.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                Command.setPrefix(line);
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.savePrefix();
        }
    }
    
    public void loadFont() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "CustomFont.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String name = line.split(":")[0];
                final String size = line.split(":")[1];
                final int sizeInt = Integer.parseInt(size);
                (Main.fontRenderer = new CFontRenderer(new Font(name, 0, sizeInt), true, false)).setFont(new Font(name, 0, sizeInt));
                Main.fontRenderer.setAntiAlias(true);
                Main.fontRenderer.setFractionalMetrics(false);
                Main.fontRenderer.setFontName(name);
                Main.fontRenderer.setFontSize(sizeInt);
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveFont();
        }
    }
    
    public void loadAutoGG() {
        try {
            final File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "AutoGG.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                AutoGG.addAutoGgMessage(line);
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveAutoGG();
        }
    }
    
    public void loadAutoReply() {
        try {
            final File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "AutoReply.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                AutoReply.setReply(line);
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveAutoReply();
        }
    }
    
    public void loadMessages() {
        try {
            final File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "ClientMessages.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String watermark = curLine.split(",")[0];
                final String color = curLine.split(",")[1];
                final boolean w = Boolean.parseBoolean(watermark);
                final ChatFormatting c = Command.cf = ChatFormatting.getByName(color);
                Command.MsgWaterMark = w;
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveMessages();
        }
    }
    
    public void loadDrawn() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "DrawnModules.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final boolean drawn = Boolean.parseBoolean(isOn);
                for (final Module m : ModuleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(name)) {
                        m.setDrawn(drawn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveDrawn();
        }
    }
    
    public void loadEnabled() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "EnabledModules.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                for (final Module m : ModuleManager.getModules()) {
                    if (m.getName().equals(line)) {
                        m.enable();
                    }
                }
            }
            br.close();
        }
        catch (Exception var7) {
            var7.printStackTrace();
            SaveConfiguration.saveEnabled();
        }
    }
    
    public void loadBinds() {
        try {
            final File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ModuleBinds.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String name = curLine.split(":")[0];
                final String bind = curLine.split(":")[1];
                for (final Module m : ModuleManager.getModules()) {
                    if (m != null && m.getName().equalsIgnoreCase(name)) {
                        m.setBind(Keyboard.getKeyIndex(bind));
                    }
                }
            }
            br.close();
        }
        catch (Exception var6) {
            var6.printStackTrace();
            SaveConfiguration.saveBinds();
        }
    }
}
