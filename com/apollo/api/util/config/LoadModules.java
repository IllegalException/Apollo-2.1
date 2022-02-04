//Decomped by XeonLyfe

package com.apollo.api.util.config;

import java.io.*;
import com.apollo.client.module.*;
import com.apollo.client.*;
import com.apollo.api.values.*;
import java.util.*;

public class LoadModules
{
    public LoadModules() {
        this.loadCombat();
        this.loadExploits();
        this.loadHud();
        this.loadMisc();
        this.loadMovement();
        this.loadRender();
    }
    
    public void loadCombat() {
        try {
            final File file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Value.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Combat)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndModConfig(configname, mm);
                        if (mod instanceof Value.Integer) {
                            ((Value.Integer)mod).setValue(Integer.parseInt(isOn));
                        }
                        else {
                            if (!(mod instanceof Value.Double)) {
                                continue;
                            }
                            ((Value.Double)mod).setValue(Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        }
        catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Boolean.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Combat)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Boolean)mod).setValue(Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        }
        catch (Exception var14) {
            var14.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Combat.getAbsolutePath(), "String.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Combat)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Mode)mod).setValue(isOn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var15) {
            var15.printStackTrace();
        }
    }
    
    public void loadExploits() {
        try {
            final File file = new File(SaveConfiguration.Exploits.getAbsolutePath(), "Value.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Exploits)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndModConfig(configname, mm);
                        if (mod instanceof Value.Integer) {
                            ((Value.Integer)mod).setValue(Integer.parseInt(isOn));
                        }
                        else {
                            if (!(mod instanceof Value.Double)) {
                                continue;
                            }
                            ((Value.Double)mod).setValue(Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        }
        catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Exploits.getAbsolutePath(), "Boolean.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Exploits)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Boolean)mod).setValue(Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        }
        catch (Exception var14) {
            var14.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Exploits.getAbsolutePath(), "String.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Exploits)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Mode)mod).setValue(isOn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var15) {
            var15.printStackTrace();
        }
    }
    
    public void loadHud() {
        try {
            final File file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Value.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.HUD)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndModConfig(configname, mm);
                        if (mod instanceof Value.Integer) {
                            ((Value.Integer)mod).setValue(Integer.parseInt(isOn));
                        }
                        else {
                            if (!(mod instanceof Value.Double)) {
                                continue;
                            }
                            ((Value.Double)mod).setValue(Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        }
        catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Boolean.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.HUD)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Boolean)mod).setValue(Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        }
        catch (Exception var14) {
            var14.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Hud.getAbsolutePath(), "String.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.HUD)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Mode)mod).setValue(isOn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var15) {
            var15.printStackTrace();
        }
    }
    
    public void loadMisc() {
        try {
            final File file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Value.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Misc)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndModConfig(configname, mm);
                        if (mod instanceof Value.Integer) {
                            ((Value.Integer)mod).setValue(Integer.parseInt(isOn));
                        }
                        else {
                            if (!(mod instanceof Value.Double)) {
                                continue;
                            }
                            ((Value.Double)mod).setValue(Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        }
        catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Boolean.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Misc)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Boolean)mod).setValue(Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        }
        catch (Exception var14) {
            var14.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Misc.getAbsolutePath(), "String.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Misc)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Mode)mod).setValue(isOn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var15) {
            var15.printStackTrace();
        }
    }
    
    public void loadMovement() {
        try {
            final File file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Value.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Movement)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndModConfig(configname, mm);
                        if (mod instanceof Value.Integer) {
                            ((Value.Integer)mod).setValue(Integer.parseInt(isOn));
                        }
                        else {
                            if (!(mod instanceof Value.Double)) {
                                continue;
                            }
                            ((Value.Double)mod).setValue(Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        }
        catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Boolean.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Movement)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Boolean)mod).setValue(Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        }
        catch (Exception var14) {
            var14.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Movement.getAbsolutePath(), "String.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Movement)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Mode)mod).setValue(isOn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var15) {
            var15.printStackTrace();
        }
    }
    
    public void loadRender() {
        try {
            final File file = new File(SaveConfiguration.Render.getAbsolutePath(), "Value.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Render)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndModConfig(configname, mm);
                        if (mod instanceof Value.Integer) {
                            ((Value.Integer)mod).setValue(Integer.parseInt(isOn));
                        }
                        else {
                            if (!(mod instanceof Value.Double)) {
                                continue;
                            }
                            ((Value.Double)mod).setValue(Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        }
        catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Render.getAbsolutePath(), "Boolean.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Render)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Boolean)mod).setValue(Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        }
        catch (Exception var14) {
            var14.printStackTrace();
        }
        try {
            final File file = new File(SaveConfiguration.Render.getAbsolutePath(), "String.json");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.trim();
                final String configname = curLine.split(":")[0];
                final String isOn = curLine.split(":")[1];
                final String m = curLine.split(":")[2];
                for (final Module mm : ModuleManager.getModulesInCategory(Module.Category.Render)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        final Value mod = Main.getInstance().valuesManager.getSettingByNameAndMod(configname, mm);
                        ((Value.Mode)mod).setValue(isOn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var15) {
            var15.printStackTrace();
        }
    }
}
