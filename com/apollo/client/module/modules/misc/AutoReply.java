//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.apollo.client.*;

public class AutoReply extends Module
{
    private static String reply;
    @EventHandler
    private final Listener<ClientChatReceivedEvent> listener;
    
    public AutoReply() {
        super("AutoReply", Module.Category.Misc);
        this.listener = (Listener<ClientChatReceivedEvent>)new Listener(event -> {
            if (event.getMessage().getUnformattedText().contains("whispers: ") && !event.getMessage().getUnformattedText().startsWith(AutoReply.mc.player.getName())) {
                AutoReply.mc.player.sendChatMessage("/r " + AutoReply.reply);
            }
            else if (event.getMessage().getUnformattedText().contains("whispers: I don't speak to newfags!") && !event.getMessage().getUnformattedText().startsWith(AutoReply.mc.player.getName())) {
                return;
            }
        }, new Predicate[0]);
    }
    
    public static String getReply() {
        return AutoReply.reply;
    }
    
    public static void setReply(final String r) {
        AutoReply.reply = r;
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
    
    static {
        AutoReply.reply = "I don't speak to newfags!";
    }
}
