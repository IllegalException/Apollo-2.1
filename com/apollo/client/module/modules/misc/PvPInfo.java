//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import net.minecraft.entity.*;
import com.apollo.api.values.*;
import me.zero.alpine.listener.*;
import com.apollo.api.event.events.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import java.util.stream.*;
import com.apollo.client.command.*;
import net.minecraft.entity.item.*;
import com.mojang.realmsclient.gui.*;
import com.apollo.api.util.world.*;
import net.minecraft.init.*;
import com.apollo.client.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;

public class PvPInfo extends Module
{
    List<Entity> knownPlayers;
    List<Entity> antipearlspamplz;
    List<Entity> players;
    List<Entity> pearls;
    private HashMap<String, Integer> popList;
    public Set strengthedPlayers;
    public Set renderPlayers;
    Value.Boolean visualrange;
    Value.Boolean pearlalert;
    Value.Boolean popcounter;
    Value.Boolean strengthdetect;
    Value.Mode ChatColor;
    @EventHandler
    public Listener<TotemPopEvent> totemPopEvent;
    @EventHandler
    public Listener<PacketEvent.Receive> totemPopListener;
    
    public PvPInfo() {
        super("PvPInfo", Module.Category.Misc);
        this.knownPlayers = new ArrayList<Entity>();
        this.antipearlspamplz = new ArrayList<Entity>();
        this.popList = new HashMap<String, Integer>();
        this.totemPopEvent = (Listener<TotemPopEvent>)new Listener(event -> {
            if (this.popcounter.getValue()) {
                if (this.popList == null) {
                    this.popList = new HashMap<String, Integer>();
                }
                if (this.popList.get(event.getEntity().getName()) == null) {
                    this.popList.put(event.getEntity().getName(), 1);
                    Command.sendClientMessage(this.getTextColor() + event.getEntity().getName() + " popped " + ChatFormatting.RED + 1 + this.getTextColor() + " totem!");
                }
                else if (this.popList.get(event.getEntity().getName()) != null) {
                    int popCounter = this.popList.get(event.getEntity().getName());
                    final int newPopCounter = ++popCounter;
                    this.popList.put(event.getEntity().getName(), newPopCounter);
                    Command.sendClientMessage(this.getTextColor() + event.getEntity().getName() + " popped " + ChatFormatting.RED + newPopCounter + this.getTextColor() + " totems!");
                }
            }
        }, new Predicate[0]);
        this.totemPopListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (PvPInfo.mc.world == null || PvPInfo.mc.player == null) {
                return;
            }
            if (event.getPacket() instanceof SPacketEntityStatus) {
                final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
                if (packet.getOpCode() == 35) {
                    final Entity entity = packet.getEntity((World)PvPInfo.mc.world);
                    Main.EVENT_BUS.post((Object)new TotemPopEvent(entity));
                }
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        final ArrayList<String> colors = new ArrayList<String>();
        colors.add("Black");
        colors.add("Dark Green");
        colors.add("Dark Red");
        colors.add("Gold");
        colors.add("Dark Gray");
        colors.add("Green");
        colors.add("Red");
        colors.add("Yellow");
        colors.add("Dark Blue");
        colors.add("Dark Aqua");
        colors.add("Dark Purple");
        colors.add("Gray");
        colors.add("Blue");
        colors.add("Aqua");
        colors.add("Light Purple");
        colors.add("White");
        this.visualrange = this.registerBoolean("Visual Range", "VisualRange", false);
        this.pearlalert = this.registerBoolean("Pearl Alert", "PearlAlert", false);
        this.popcounter = this.registerBoolean("Pop Counter", "PopCounter", false);
        this.strengthdetect = this.registerBoolean("Strength Detect", "StrengthDetect", false);
        this.ChatColor = this.registerMode("Color", "Color", (List)colors, "Light Purple");
    }
    
    public void onUpdate() {
        if (this.visualrange.getValue()) {
            if (PvPInfo.mc.player == null) {
                return;
            }
            this.players = (List<Entity>)PvPInfo.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).collect(Collectors.toList());
            try {
                for (final Entity e2 : this.players) {
                    if (e2 instanceof EntityPlayer && !e2.getName().equalsIgnoreCase(PvPInfo.mc.player.getName()) && !this.knownPlayers.contains(e2)) {
                        this.knownPlayers.add(e2);
                        Command.sendClientMessage(this.getTextColor() + e2.getName() + " has been spotted thanks to Apollo!");
                    }
                }
            }
            catch (Exception ex) {}
            try {
                for (final Entity e2 : this.knownPlayers) {
                    if (e2 instanceof EntityPlayer && !e2.getName().equalsIgnoreCase(PvPInfo.mc.player.getName()) && !this.players.contains(e2)) {
                        this.knownPlayers.remove(e2);
                    }
                }
            }
            catch (Exception ex2) {}
        }
        if (this.pearlalert.getValue()) {
            this.pearls = (List<Entity>)PvPInfo.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderPearl).collect(Collectors.toList());
            try {
                for (final Entity e2 : this.pearls) {
                    if (e2 instanceof EntityEnderPearl && !this.antipearlspamplz.contains(e2)) {
                        this.antipearlspamplz.add(e2);
                        Command.sendClientMessage(this.getTextColor() + e2.getEntityWorld().getClosestPlayerToEntity(e2, 3.0).getName() + " has just thrown a pearl!");
                    }
                }
            }
            catch (Exception ex3) {}
        }
        if (this.popcounter.getValue()) {
            for (final EntityPlayer player : PvPInfo.mc.world.playerEntities) {
                if (player.getHealth() <= 0.0f && this.popList.containsKey(player.getName())) {
                    Command.sendClientMessage(this.getTextColor() + player.getName() + " died after popping " + ChatFormatting.GREEN + this.popList.get(player.getName()) + this.getTextColor() + " totems!");
                    this.popList.remove(player.getName(), this.popList.get(player.getName()));
                }
            }
        }
        if (this.strengthdetect.getValue() && this.isEnabled() && PvPInfo.mc.player != null) {
            for (final EntityPlayer ent : PvPInfo.mc.world.playerEntities) {
                if (EntityUtil.isLiving((Entity)ent) && ent.getHealth() > 0.0f) {
                    if (ent.isPotionActive(MobEffects.STRENGTH) && !this.strengthedPlayers.contains(ent)) {
                        Command.sendClientMessage(this.getTextColor() + ent.getDisplayNameString() + " has (drank) strength!");
                        this.strengthedPlayers.add(ent);
                    }
                    if (this.strengthedPlayers.contains(ent) && !ent.isPotionActive(MobEffects.STRENGTH)) {
                        Command.sendClientMessage(this.getTextColor() + ent.getDisplayNameString() + " no longer has strength!");
                        this.strengthedPlayers.remove(ent);
                    }
                    this.checkRender();
                }
            }
        }
    }
    
    public void checkRender() {
        try {
            this.renderPlayers.clear();
            for (final EntityPlayer ent : PvPInfo.mc.world.playerEntities) {
                if (EntityUtil.isLiving((Entity)ent) && ent.getHealth() > 0.0f) {
                    this.renderPlayers.add(ent);
                }
            }
            for (final EntityPlayer ent : this.strengthedPlayers) {
                if (!this.renderPlayers.contains(ent)) {
                    this.strengthedPlayers.remove(ent);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
        this.popList = new HashMap<String, Integer>();
        this.strengthedPlayers = new HashSet();
        this.renderPlayers = new HashSet();
    }
    
    public ChatFormatting getTextColor() {
        if (this.ChatColor.getValue().equalsIgnoreCase("Black")) {
            return ChatFormatting.BLACK;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Dark Green")) {
            return ChatFormatting.DARK_GREEN;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Dark Red")) {
            return ChatFormatting.DARK_RED;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Gold")) {
            return ChatFormatting.GOLD;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Dark Gray")) {
            return ChatFormatting.DARK_GRAY;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Green")) {
            return ChatFormatting.GREEN;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Red")) {
            return ChatFormatting.RED;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Yellow")) {
            return ChatFormatting.YELLOW;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Dark Blue")) {
            return ChatFormatting.DARK_BLUE;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Dark Aqua")) {
            return ChatFormatting.DARK_AQUA;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Dark Purple")) {
            return ChatFormatting.DARK_PURPLE;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Gray")) {
            return ChatFormatting.GRAY;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Blue")) {
            return ChatFormatting.BLUE;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Light Purple")) {
            return ChatFormatting.LIGHT_PURPLE;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("White")) {
            return ChatFormatting.WHITE;
        }
        if (this.ChatColor.getValue().equalsIgnoreCase("Aqua")) {
            return ChatFormatting.AQUA;
        }
        return null;
    }
    
    public void onDisable() {
        this.knownPlayers.clear();
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
