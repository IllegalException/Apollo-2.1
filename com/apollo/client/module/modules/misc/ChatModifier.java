//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import com.apollo.api.event.events.*;
import java.util.function.*;
import com.mojang.realmsclient.gui.*;
import com.apollo.client.*;
import net.minecraft.network.play.client.*;
import com.apollo.client.command.*;
import java.text.*;
import java.util.*;
import net.minecraft.util.text.*;

public class ChatModifier extends Module
{
    public Value.Boolean clearBkg;
    Value.Boolean chattimestamps;
    Value.Mode format;
    Value.Mode color;
    Value.Mode decoration;
    Value.Boolean space;
    Value.Boolean greentext;
    @EventHandler
    private final Listener<ClientChatReceivedEvent> chatReceivedEventListener;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public ChatModifier() {
        super("ChatModifier", Module.Category.Misc);
        this.chatReceivedEventListener = (Listener<ClientChatReceivedEvent>)new Listener(event -> {
            if (this.chattimestamps.getValue()) {
                final String decoLeft = this.decoration.getValue().equalsIgnoreCase(" ") ? "" : this.decoration.getValue().split(" ")[0];
                String decoRight = this.decoration.getValue().equalsIgnoreCase(" ") ? "" : this.decoration.getValue().split(" ")[1];
                if (this.space.getValue()) {
                    decoRight += " ";
                }
                final String dateFormat = this.format.getValue().replace("H24", "k").replace("H12", "h");
                final String date = new SimpleDateFormat(dateFormat).format(new Date());
                final TextComponentString time = new TextComponentString(ChatFormatting.getByName(this.color.getValue()) + decoLeft + date + decoRight + ChatFormatting.RESET);
                event.setMessage(time.appendSibling(event.getMessage()));
            }
        }, new Predicate[0]);
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (this.greentext.getValue() && event.getPacket() instanceof CPacketChatMessage) {
                if (((CPacketChatMessage)event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage)event.getPacket()).getMessage().startsWith(Command.getPrefix())) {
                    return;
                }
                final String message = ((CPacketChatMessage)event.getPacket()).getMessage();
                String prefix = "";
                prefix = ">";
                final String s = prefix + message;
                if (s.length() > 255) {
                    return;
                }
                ((CPacketChatMessage)event.getPacket()).message = s;
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        final ArrayList<String> formats = new ArrayList<String>();
        formats.add("H24:mm");
        formats.add("H12:mm");
        formats.add("H12:mm a");
        formats.add("H24:mm:ss");
        formats.add("H12:mm:ss");
        formats.add("H12:mm:ss a");
        final ArrayList<String> deco = new ArrayList<String>();
        deco.add("< >");
        deco.add("[ ]");
        deco.add("{ }");
        deco.add(" ");
        final ArrayList<String> colors = new ArrayList<String>();
        for (final ChatFormatting cf : ChatFormatting.values()) {
            colors.add(cf.getName());
        }
        this.clearBkg = this.registerBoolean("Clear Chat", "ClearChat", false);
        this.greentext = this.registerBoolean("Green Text", "GreenText", false);
        this.chattimestamps = this.registerBoolean("Chat Time Stamps", "ChatTimeStamps", false);
        this.format = this.registerMode("Format", "Format", (List)formats, "H24:mm");
        this.decoration = this.registerMode("Deco", "Deco", (List)deco, "[ ]");
        this.color = this.registerMode("Color", "Colors", (List)colors, ChatFormatting.GRAY.getName());
        this.space = this.registerBoolean("Space", "Space", false);
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
