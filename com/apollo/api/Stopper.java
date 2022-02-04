//Decomped by XeonLyfe

package com.apollo.api;

import com.apollo.client.*;
import com.apollo.api.util.config.*;

public class Stopper extends Thread
{
    @Override
    public void run() {
        saveConfig();
    }
    
    public static void saveConfig() {
        Main.getInstance().saveModules.saveModules();
        SaveConfiguration.saveAutoGG();
        SaveConfiguration.saveAutoReply();
        SaveConfiguration.saveBinds();
        SaveConfiguration.saveDrawn();
        SaveConfiguration.saveEnabled();
        SaveConfiguration.saveEnemies();
        SaveConfiguration.saveFont();
        SaveConfiguration.saveFriends();
        SaveConfiguration.saveGUI();
        SaveConfiguration.saveMacros();
        SaveConfiguration.saveMessages();
        SaveConfiguration.savePrefix();
    }
}
