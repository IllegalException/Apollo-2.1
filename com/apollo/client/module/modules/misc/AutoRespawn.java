//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import com.apollo.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.apollo.client.*;
import com.apollo.client.command.*;
import net.minecraft.client.gui.*;

public class AutoRespawn extends Module
{
    Value.Boolean coords;
    @EventHandler
    private final Listener<GuiScreenDisplayedEvent> listener;
    
    public AutoRespawn() {
        super("AutoRespawn", Module.Category.Misc);
        this.listener = (Listener<GuiScreenDisplayedEvent>)new Listener(event -> {
            if (event.getScreen() instanceof GuiGameOver) {
                if (this.coords.getValue()) {
                    Command.sendClientMessage(String.format("You died at x%d y%d z%d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ));
                }
                if (AutoRespawn.mc.player != null) {
                    AutoRespawn.mc.player.respawnPlayer();
                }
                AutoRespawn.mc.displayGuiScreen((GuiScreen)null);
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        this.coords = this.registerBoolean("Coords", "Coords", false);
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
