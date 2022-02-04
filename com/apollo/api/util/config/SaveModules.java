//Decomped by XeonLyfe

package com.apollo.api.util.config;

import java.io.*;
import com.apollo.client.*;
import com.apollo.client.module.*;
import com.apollo.api.values.*;
import java.util.*;

public class SaveModules
{
    public void saveModules() {
        this.saveCombat();
        this.saveExploits();
        this.saveHud();
        this.saveMisc();
        this.saveMovement();
        this.saveRender();
    }
    
    public void saveCombat() {
        try {
            final File file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Value.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Combat)) {
                if (i.getType() == Value.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" + ((Value.Double)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Value.Type.INT) {
                    out.write(i.getConfigName() + ":" + ((Value.Integer)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
        try {
            final File file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Boolean.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Combat)) {
                if (i.getType() == Value.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Value.Boolean)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex2) {}
        try {
            final File file = new File(SaveConfiguration.Combat.getAbsolutePath(), "String.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Combat)) {
                if (i.getType() == Value.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Value.Mode)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex3) {}
    }
    
    public void saveExploits() {
        try {
            final File file = new File(SaveConfiguration.Exploits.getAbsolutePath(), "Value.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Exploits)) {
                if (i.getType() == Value.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" + ((Value.Double)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Value.Type.INT) {
                    out.write(i.getConfigName() + ":" + ((Value.Integer)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
        try {
            final File file = new File(SaveConfiguration.Exploits.getAbsolutePath(), "Boolean.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Exploits)) {
                if (i.getType() == Value.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Value.Boolean)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex2) {}
        try {
            final File file = new File(SaveConfiguration.Exploits.getAbsolutePath(), "String.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Exploits)) {
                if (i.getType() == Value.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Value.Mode)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex3) {}
    }
    
    public void saveHud() {
        try {
            final File file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Value.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.HUD)) {
                if (i.getType() == Value.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" + ((Value.Double)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Value.Type.INT) {
                    out.write(i.getConfigName() + ":" + ((Value.Integer)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
        try {
            final File file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Boolean.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.HUD)) {
                if (i.getType() == Value.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Value.Boolean)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex2) {}
        try {
            final File file = new File(SaveConfiguration.Hud.getAbsolutePath(), "String.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.HUD)) {
                if (i.getType() == Value.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Value.Mode)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex3) {}
    }
    
    public void saveMisc() {
        try {
            final File file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Value.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Misc)) {
                if (i.getType() == Value.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" + ((Value.Double)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Value.Type.INT) {
                    out.write(i.getConfigName() + ":" + ((Value.Integer)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
        try {
            final File file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Boolean.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Misc)) {
                if (i.getType() == Value.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Value.Boolean)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex2) {}
        try {
            final File file = new File(SaveConfiguration.Misc.getAbsolutePath(), "String.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Misc)) {
                if (i.getType() == Value.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Value.Mode)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex3) {}
    }
    
    public void saveMovement() {
        try {
            final File file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Value.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Movement)) {
                if (i.getType() == Value.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" + ((Value.Double)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Value.Type.INT) {
                    out.write(i.getConfigName() + ":" + ((Value.Integer)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
        try {
            final File file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Boolean.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Movement)) {
                if (i.getType() == Value.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Value.Boolean)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex2) {}
        try {
            final File file = new File(SaveConfiguration.Movement.getAbsolutePath(), "String.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Movement)) {
                if (i.getType() == Value.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Value.Mode)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex3) {}
    }
    
    public void saveRender() {
        try {
            final File file = new File(SaveConfiguration.Render.getAbsolutePath(), "Value.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Render)) {
                if (i.getType() == Value.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" + ((Value.Double)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Value.Type.INT) {
                    out.write(i.getConfigName() + ":" + ((Value.Integer)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
        try {
            final File file = new File(SaveConfiguration.Render.getAbsolutePath(), "Boolean.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Render)) {
                if (i.getType() == Value.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Value.Boolean)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex2) {}
        try {
            final File file = new File(SaveConfiguration.Render.getAbsolutePath(), "String.json");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final Value i : Main.getInstance().valuesManager.getSettingsByCategory(Module.Category.Render)) {
                if (i.getType() == Value.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Value.Mode)i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex3) {}
    }
}
