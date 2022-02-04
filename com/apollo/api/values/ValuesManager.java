//Decomped by XeonLyfe

package com.apollo.api.values;

import com.apollo.client.module.*;
import java.util.stream.*;
import java.util.*;

public class ValuesManager
{
    private final List<Value> values;
    
    public ValuesManager() {
        this.values = new ArrayList<Value>();
    }
    
    public List<Value> getSettings() {
        return this.values;
    }
    
    public void addSetting(final Value value) {
        this.values.add(value);
    }
    
    public Value getSettingByNameAndMod(final String name, final Module parent) {
        return this.values.stream().filter(s -> s.getParent().equals(parent)).filter(s -> s.getConfigName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
    public Value getSettingByNameAndModConfig(final String configname, final Module parent) {
        return this.values.stream().filter(s -> s.getParent().equals(parent)).filter(s -> s.getConfigName().equalsIgnoreCase(configname)).findFirst().orElse(null);
    }
    
    public List<Value> getSettingsForMod(final Module parent) {
        return this.values.stream().filter(s -> s.getParent().equals(parent)).collect((Collector<? super Object, ?, List<Value>>)Collectors.toList());
    }
    
    public List<Value> getSettingsByCategory(final Module.Category category) {
        return this.values.stream().filter(s -> s.getCategory().equals(category)).collect((Collector<? super Object, ?, List<Value>>)Collectors.toList());
    }
    
    public Value getSettingByName(final String name) {
        for (final Value set : this.getSettings()) {
            if (set.getName().equalsIgnoreCase(name)) {
                return set;
            }
        }
        System.err.println("[Apollo] Error Value NOT found: '" + name + "'!");
        return null;
    }
}
