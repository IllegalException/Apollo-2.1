//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import com.apollo.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import com.apollo.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;

public class NoKick extends Module
{
    public Value.Boolean noPacketKick;
    Value.Boolean noSlimeCrash;
    Value.Boolean noOffhandCrash;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    
    public NoKick() {
        super("NoKick", Module.Category.Misc);
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (this.noOffhandCrash.getValue() && event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).getSound() == SoundEvents.ITEM_ARMOR_EQUIP_GENERIC) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        this.noPacketKick = this.registerBoolean("Packet", "Packet", true);
        this.noSlimeCrash = this.registerBoolean("Slime", "Slime", false);
        this.noOffhandCrash = this.registerBoolean("Offhand", "Offhand", false);
    }
    
    public void onUpdate() {
        if (NoKick.mc.world != null && this.noSlimeCrash.getValue()) {
            EntitySlime slime;
            NoKick.mc.world.loadedEntityList.forEach(entity -> {
                if (entity instanceof EntitySlime) {
                    slime = entity;
                    if (slime.getSlimeSize() > 4) {
                        NoKick.mc.world.removeEntity((Entity)entity);
                    }
                }
            });
        }
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
