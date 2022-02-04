//Decomped by XeonLyfe

package com.apollo.client.module.modules.movement;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import java.util.*;
import com.apollo.api.event.events.*;
import com.apollo.api.util.world.*;
import com.mojang.realmsclient.gui.*;

public class Sprint extends Module
{
    Value.Mode Mode;
    
    public Sprint() {
        super("Sprint", Module.Category.Movement);
    }
    
    public void setup() {
        final ArrayList<String> sprintModes = new ArrayList<String>();
        sprintModes.add("Legit");
        sprintModes.add("Rage");
        this.Mode = this.registerMode("Mode", "Mode", (List)sprintModes, "Legit");
    }
    
    public void onUpdate() {
        if (Sprint.mc.gameSettings.keyBindSneak.isKeyDown()) {
            Sprint.mc.player.setSprinting(false);
            return;
        }
        if (Sprint.mc.player.getFoodStats().getFoodLevel() > 6 && this.Mode.getValue().equalsIgnoreCase("Rage")) {
            if (Sprint.mc.player.moveForward == 0.0f) {
                if (Sprint.mc.player.moveStrafing == 0.0f) {
                    return;
                }
            }
        }
        else if (Sprint.mc.player.moveForward <= 0.0f) {
            return;
        }
        Sprint.mc.player.setSprinting(true);
    }
    
    public void onJump(final JumpEvent event) {
        if (this.Mode.getValue().equalsIgnoreCase("Rage")) {
            final double[] dir = MotionUtils.forward(0.01745329238474369);
            event.getLocation().setX(dir[0] * 0.20000000298023224);
            event.getLocation().setZ(dir[1] * 0.20000000298023224);
        }
    }
    
    public String getHudInfo() {
        String t = " ";
        if (this.Mode.getValue().equalsIgnoreCase("Rage")) {
            return t = "[" + ChatFormatting.WHITE + "Rage" + ChatFormatting.GRAY + "]";
        }
        return t;
    }
}
