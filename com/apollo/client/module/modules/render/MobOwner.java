//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import com.apollo.api.util.world.*;
import java.util.*;
import com.apollo.client.command.*;

public class MobOwner extends Module
{
    private Value.Integer requestTime;
    private Value.Boolean debug;
    private final Map<String, String> cachedUUIDs;
    private int apiRequests;
    private final String invalidText = "Servers offline!";
    private static long startTime1;
    private static long startTime2;
    
    public MobOwner() {
        super("MobOwner", Module.Category.Render);
        this.cachedUUIDs = new HashMap<String, String>() {};
        this.apiRequests = 0;
    }
    
    public void setup() {
        this.requestTime = this.registerInteger("Reset Ticks", "ResetTicks", 10, 0, 20);
        this.debug = this.registerBoolean("Debug", "Debug", false);
    }
    
    public void onUpdate() {
        this.resetRequests();
        this.resetCache();
        for (final Entity entity : MobOwner.mc.world.loadedEntityList) {
            if (entity instanceof EntityTameable) {
                final EntityTameable entityTameable = (EntityTameable)entity;
                if (entityTameable.isTamed() && entityTameable.getOwner() != null) {
                    entityTameable.setAlwaysRenderNameTag(true);
                    entityTameable.setCustomNameTag("Owner: " + entityTameable.getOwner().getDisplayName().getFormattedText());
                }
            }
            if (entity instanceof AbstractHorse) {
                final AbstractHorse abstractHorse = (AbstractHorse)entity;
                if (!abstractHorse.isTame()) {
                    continue;
                }
                if (abstractHorse.getOwnerUniqueId() == null) {
                    continue;
                }
                abstractHorse.setAlwaysRenderNameTag(true);
                abstractHorse.setCustomNameTag("Owner: " + this.getUsername(abstractHorse.getOwnerUniqueId().toString()));
            }
        }
    }
    
    private String getUsername(final String uuid) {
        for (final Map.Entry<String, String> entries : this.cachedUUIDs.entrySet()) {
            if (entries.getKey().equalsIgnoreCase(uuid)) {
                return entries.getValue();
            }
        }
        try {
            if (this.apiRequests > 10) {
                return "Too many API requests!";
            }
            this.cachedUUIDs.put(uuid, Objects.requireNonNull(EntityUtil.getNameFromUUID(uuid)).replace("\"", ""));
            ++this.apiRequests;
        }
        catch (IllegalStateException illegal) {
            this.cachedUUIDs.put(uuid, "Servers offline!");
        }
        for (final Map.Entry<String, String> entries : this.cachedUUIDs.entrySet()) {
            if (entries.getKey().equalsIgnoreCase(uuid)) {
                return entries.getValue();
            }
        }
        return "Servers offline!";
    }
    
    public void onDisable() {
        this.cachedUUIDs.clear();
        for (final Entity entity : MobOwner.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityTameable) && !(entity instanceof AbstractHorse)) {
                continue;
            }
            try {
                entity.setAlwaysRenderNameTag(false);
            }
            catch (Exception ex) {}
        }
    }
    
    private void resetRequests() {
        if (MobOwner.startTime1 == 0L) {
            MobOwner.startTime1 = System.currentTimeMillis();
        }
        if (MobOwner.startTime1 + 10000L <= System.currentTimeMillis()) {
            MobOwner.startTime1 = System.currentTimeMillis();
            if (this.apiRequests >= 2) {
                this.apiRequests = 0;
                if (this.debug.getValue()) {
                    Command.sendClientMessage("Reset API requests counter!");
                }
            }
        }
    }
    
    private void resetCache() {
        if (MobOwner.startTime2 == 0L) {
            MobOwner.startTime2 = System.currentTimeMillis();
        }
        if (MobOwner.startTime2 + this.requestTime.getValue() * 1000 <= System.currentTimeMillis()) {
            MobOwner.startTime2 = System.currentTimeMillis();
            for (final Map.Entry<String, String> entries : this.cachedUUIDs.entrySet()) {
                if (entries.getKey().equalsIgnoreCase("Servers offline!")) {
                    this.cachedUUIDs.clear();
                    if (this.debug.getValue()) {
                        Command.sendClientMessage("Reset cached UUIDs list!");
                    }
                }
            }
        }
    }
    
    static {
        MobOwner.startTime1 = 0L;
        MobOwner.startTime2 = 0L;
    }
}
