//Decomped by XeonLyfe

package com.apollo.client.module.modules.hud;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.util.text.*;
import java.util.*;
import net.minecraft.entity.player.*;
import com.apollo.api.players.friends.*;
import com.apollo.api.players.enemy.*;
import net.minecraft.entity.*;
import com.apollo.client.*;

public class TextRadar extends Module
{
    Value.Boolean sortUp;
    Value.Boolean sortRight;
    Value.Integer radarX;
    Value.Integer radarY;
    Value.Mode display;
    int sort;
    int playerCount;
    TextFormatting friendcolor;
    TextFormatting distancecolor;
    TextFormatting healthcolor;
    
    public TextRadar() {
        super("TextRadar", Module.Category.HUD);
    }
    
    public void setup() {
        final ArrayList<String> displayModes = new ArrayList<String>();
        displayModes.add("All");
        displayModes.add("Friend");
        displayModes.add("Enemy");
        this.display = this.registerMode("Display", "Display", (List)displayModes, "All");
        this.sortUp = this.registerBoolean("Sort Up", "SortUp", false);
        this.sortRight = this.registerBoolean("Sort Right", "SortRight", false);
        this.radarX = this.registerInteger("X", "X", 0, 0, 1000);
        this.radarY = this.registerInteger("Y", "Y", 50, 0, 1000);
    }
    
    public void onRender() {
        if (this.sortUp.getValue()) {
            this.sort = -1;
        }
        else {
            this.sort = 1;
        }
        this.playerCount = 0;
        TextRadar.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).filter(e -> e != TextRadar.mc.player).forEach(e -> {
            if (Friends.isFriend(((Entity)e).getName())) {
                this.friendcolor = ColorMain.getFriendColor();
            }
            else if (Enemies.isEnemy(((Entity)e).getName())) {
                this.friendcolor = ColorMain.getEnemyColor();
            }
            else {
                this.friendcolor = TextFormatting.GRAY;
            }
            if (e.getHealth() + e.getAbsorptionAmount() <= 5.0f) {
                this.healthcolor = TextFormatting.RED;
            }
            if (e.getHealth() + e.getAbsorptionAmount() > 5.0f && e.getHealth() + e.getAbsorptionAmount() < 15.0f) {
                this.healthcolor = TextFormatting.YELLOW;
            }
            if (e.getHealth() + e.getAbsorptionAmount() >= 15.0f) {
                this.healthcolor = TextFormatting.GREEN;
            }
            if (TextRadar.mc.player.getDistance((Entity)e) < 20.0f) {
                this.distancecolor = TextFormatting.RED;
            }
            if (TextRadar.mc.player.getDistance((Entity)e) >= 20.0f && TextRadar.mc.player.getDistance((Entity)e) < 50.0f) {
                this.distancecolor = TextFormatting.YELLOW;
            }
            if (TextRadar.mc.player.getDistance((Entity)e) >= 50.0f) {
                this.distancecolor = TextFormatting.GREEN;
            }
            if (!this.display.getValue().equalsIgnoreCase("Friend") || Friends.isFriend(((Entity)e).getName())) {
                if (!this.display.getValue().equalsIgnoreCase("Enemy") || Enemies.isEnemy(((Entity)e).getName())) {
                    if (this.sortUp.getValue()) {
                        if (this.sortRight.getValue()) {
                            this.drawStringWithShadow(TextFormatting.GRAY + "[" + this.healthcolor + (int)(e.getHealth() + e.getAbsorptionAmount()) + TextFormatting.GRAY + "] " + this.friendcolor + ((Entity)e).getName() + TextFormatting.GRAY + " [" + this.distancecolor + (int)TextRadar.mc.player.getDistance((Entity)e) + TextFormatting.GRAY + "]", this.radarX.getValue() - this.getWidth(TextFormatting.GRAY + "[" + this.healthcolor + (int)(e.getHealth() + e.getAbsorptionAmount()) + TextFormatting.GRAY + "] " + this.friendcolor + ((Entity)e).getName() + TextFormatting.GRAY + " [" + this.distancecolor + (int)TextRadar.mc.player.getDistance((Entity)e) + TextFormatting.GRAY + "]"), this.radarY.getValue() + this.playerCount * 10, -1);
                        }
                        else {
                            this.drawStringWithShadow(TextFormatting.GRAY + "[" + this.healthcolor + (int)(e.getHealth() + e.getAbsorptionAmount()) + TextFormatting.GRAY + "] " + this.friendcolor + ((Entity)e).getName() + TextFormatting.GRAY + " [" + this.distancecolor + (int)TextRadar.mc.player.getDistance((Entity)e) + TextFormatting.GRAY + "]", this.radarX.getValue(), this.radarY.getValue() + this.playerCount * 10, -1);
                        }
                        ++this.playerCount;
                    }
                    else {
                        if (this.sortRight.getValue()) {
                            this.drawStringWithShadow(TextFormatting.GRAY + "[" + this.healthcolor + (int)(e.getHealth() + e.getAbsorptionAmount()) + TextFormatting.GRAY + "] " + this.friendcolor + ((Entity)e).getName() + TextFormatting.GRAY + " [" + this.distancecolor + (int)TextRadar.mc.player.getDistance((Entity)e) + TextFormatting.GRAY + "]", this.radarX.getValue() - this.getWidth(TextFormatting.GRAY + "[" + this.healthcolor + (int)(e.getHealth() + e.getAbsorptionAmount()) + TextFormatting.GRAY + "] " + this.friendcolor + ((Entity)e).getName() + TextFormatting.GRAY + " [" + this.distancecolor + (int)TextRadar.mc.player.getDistance((Entity)e) + TextFormatting.GRAY + "]"), this.radarY.getValue() + this.playerCount * -10, -1);
                        }
                        else {
                            this.drawStringWithShadow(TextFormatting.GRAY + "[" + this.healthcolor + (int)(e.getHealth() + e.getAbsorptionAmount()) + TextFormatting.GRAY + "] " + this.friendcolor + ((Entity)e).getName() + TextFormatting.GRAY + " [" + this.distancecolor + (int)TextRadar.mc.player.getDistance((Entity)e) + TextFormatting.GRAY + "]", this.radarX.getValue(), this.radarY.getValue() + this.playerCount * -10, -1);
                        }
                        ++this.playerCount;
                    }
                }
            }
        });
    }
    
    private void drawStringWithShadow(final String text, final int x, final int y, final int color) {
        if (HUD.customFont.getValue()) {
            Main.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        else {
            TextRadar.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
        }
    }
    
    private int getWidth(final String s) {
        if (HUD.customFont.getValue()) {
            return Main.fontRenderer.getStringWidth(s);
        }
        return TextRadar.mc.fontRenderer.getStringWidth(s);
    }
}
