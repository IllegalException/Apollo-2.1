//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import me.zero.alpine.listener.*;
import com.apollo.api.event.events.*;
import java.util.function.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;
import com.apollo.client.command.*;
import com.apollo.client.*;
import net.minecraft.world.*;

public class TotemPopCounter extends Module
{
    private HashMap<String, Integer> playerList;
    private boolean isDead;
    @EventHandler
    public Listener<TotemPopEvent> listListener;
    @EventHandler
    public Listener<PacketEvent.Receive> popListener;
    
    public TotemPopCounter() {
        super("TotemPopCounter", Module.Category.Misc);
        this.playerList = new HashMap<String, Integer>();
        this.isDead = false;
        final int[] popCounter = { 0 };
        this.listListener = (Listener<TotemPopEvent>)new Listener(event -> {
            if (this.playerList == null) {
                this.playerList = new HashMap<String, Integer>();
            }
            if (this.playerList.get(event.getEntity().getName()) == null) {
                this.playerList.put(event.getEntity().getName(), 1);
                this.sendMessage(this.formatName2(event.getEntity().getName()) + " this der popped " + this.formatNumber(1) + " totem" + this.ending());
            }
            else if (this.playerList.get(event.getEntity().getName()) != null) {
                popCounter[0] = this.playerList.get(event.getEntity().getName());
                final int n = 0;
                ++popCounter[n];
                this.playerList.put(event.getEntity().getName(), popCounter[0]);
                this.sendMessage(this.formatName2(event.getEntity().getName()) + "this der popped " + this.formatNumber(popCounter[0]) + " totems" + this.ending());
            }
        }, new Predicate[0]);
        final SPacketEntityStatus[] packet = { null };
        final Entity[] entity = { null };
        this.popListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (TotemPopCounter.mc.player != null && event.getPacket() instanceof SPacketEntityStatus) {
                packet[0] = (SPacketEntityStatus)event.getPacket();
                if (packet[0].getOpCode() == 35) {
                    entity[0] = packet[0].getEntity((World)TotemPopCounter.mc.world);
                    if (this.selfCheck(entity[0].getName())) {
                        Main.EVENT_BUS.post((Object)new TotemPopEvent(entity[0]));
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (!this.isDead && 0.0f >= TotemPopCounter.mc.player.getHealth()) {
            this.sendMessage(this.formatName(TotemPopCounter.mc.player.getName()) + " died and " + this.grammar(TotemPopCounter.mc.player.getName()) + " pop list was reset");
            this.isDead = true;
            this.playerList.clear();
            return;
        }
        if (this.isDead && 0.0f < TotemPopCounter.mc.player.getHealth()) {
            this.isDead = false;
        }
        for (final EntityPlayer player : TotemPopCounter.mc.world.playerEntities) {
            if (0.0f >= player.getHealth() && this.selfCheck(player.getName()) && this.playerList.containsKey(player.getName())) {
                this.sendMessage(this.formatName(player.getName()) + " died after popping " + this.formatNumber(this.playerList.get(player.getName())) + " totems" + this.ending());
                this.playerList.remove(player.getName(), this.playerList.get(player.getName()));
            }
        }
    }
    
    private boolean selfCheck(final String name) {
        return !this.isDead && (name.equalsIgnoreCase(TotemPopCounter.mc.player.getName()) || !name.equalsIgnoreCase(TotemPopCounter.mc.player.getName()));
    }
    
    private boolean isSelf(final String name) {
        return name.equalsIgnoreCase(TotemPopCounter.mc.player.getName());
    }
    
    private String formatName(String name) {
        String extraText = "";
        if (this.isSelf(name)) {
            extraText = "";
            name = "I";
        }
        return extraText + ChatFormatting.RED + name + TextFormatting.RESET;
    }
    
    private String formatName2(String name) {
        String extraText = "";
        if (this.isSelf(name)) {
            extraText = "";
            name = "I";
        }
        return extraText + ChatFormatting.GREEN + name + TextFormatting.RESET;
    }
    
    private String grammar(final String name) {
        if (this.isSelf(name)) {
            return "my";
        }
        return "their";
    }
    
    private String ending() {
        return "";
    }
    
    private String formatNumber(final int message) {
        return ChatFormatting.RED + "" + message + TextFormatting.RESET;
    }
    
    private void sendMessage(final String message) {
        Command.sendClientMessage(message);
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
