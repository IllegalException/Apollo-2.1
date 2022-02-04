//Decomped by XeonLyfe

package com.apollo.client.module.modules.hud;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.util.text.*;
import com.apollo.client.*;
import java.util.*;

public class Notifications extends Module
{
    Value.Integer notX;
    Value.Integer notY;
    Value.Boolean sortUp;
    Value.Boolean sortRight;
    public static Value.Boolean disableChat;
    int sort;
    int notCount;
    int waitCounter;
    TextFormatting notColor;
    static List<TextComponentString> list;
    
    public Notifications() {
        super("Notifications", Module.Category.HUD);
    }
    
    public void setup() {
        Notifications.disableChat = this.registerBoolean("No Chat Msg", "NoChatMsg", true);
        this.sortUp = this.registerBoolean("Sort Up", "SortUp", false);
        this.sortRight = this.registerBoolean("Sort Right", "SortRight", false);
        this.notX = this.registerInteger("X", "X", 0, 0, 1000);
        this.notY = this.registerInteger("Y", "Y", 50, 0, 1000);
    }
    
    public void onUpdate() {
        if (this.waitCounter < 500) {
            ++this.waitCounter;
            return;
        }
        this.waitCounter = 0;
        if (Notifications.list.size() > 0) {
            Notifications.list.remove(0);
        }
    }
    
    public void onRender() {
        if (this.sortUp.getValue()) {
            this.sort = -1;
        }
        else {
            this.sort = 1;
        }
        this.notCount = 0;
        for (final TextComponentString s : Notifications.list) {
            this.notCount = Notifications.list.indexOf(s) + 1;
            this.notColor = s.getStyle().getColor();
            if (this.sortUp.getValue()) {
                if (this.sortRight.getValue()) {
                    this.drawStringWithShadow(s.getText(), this.notX.getValue() - this.getWidth(s.getText()), this.notY.getValue() + this.notCount * 10, -1);
                }
                else {
                    this.drawStringWithShadow(s.getText(), this.notX.getValue(), this.notY.getValue() + this.notCount * 10, -1);
                }
            }
            else if (this.sortRight.getValue()) {
                this.drawStringWithShadow(s.getText(), this.notX.getValue() - this.getWidth(s.getText()), this.notY.getValue() + this.notCount * -10, -1);
            }
            else {
                this.drawStringWithShadow(s.getText(), this.notX.getValue(), this.notY.getValue() + this.notCount * -10, -1);
            }
        }
    }
    
    public static void addMessage(final TextComponentString m) {
        if (Notifications.list.size() < 3) {
            Notifications.list.remove(m);
            Notifications.list.add(m);
        }
        else {
            Notifications.list.remove(0);
            Notifications.list.remove(m);
            Notifications.list.add(m);
        }
    }
    
    private void drawStringWithShadow(final String text, final int x, final int y, final int color) {
        if (HUD.customFont.getValue()) {
            Main.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        else {
            Notifications.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
        }
    }
    
    private int getWidth(final String s) {
        return Notifications.mc.fontRenderer.getStringWidth(s);
    }
    
    static {
        Notifications.list = new ArrayList<TextComponentString>();
    }
}
