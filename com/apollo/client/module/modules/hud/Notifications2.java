//Decomped by XeonLyfe

package com.apollo.client.module.modules.hud;

import com.apollo.client.module.*;
import java.awt.*;

public class Notifications2 extends Module
{
    public Notifications2() {
        super("Desktop's Notifications", Module.Category.HUD);
    }
    
    public static void sendNotification(final String message, final TrayIcon.MessageType messageType) {
        final SystemTray tray = SystemTray.getSystemTray();
        final Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        final TrayIcon icon = new TrayIcon(image, "Apollo");
        icon.setImageAutoSize(true);
        icon.setToolTip("Apollo");
        try {
            tray.add(icon);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        icon.displayMessage("Apollo", message, messageType);
    }
}
