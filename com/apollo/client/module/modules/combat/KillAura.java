//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.api.values.*;
import com.apollo.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import com.apollo.api.players.friends.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.item.*;
import com.apollo.client.module.*;
import net.minecraft.entity.*;
import com.apollo.client.*;
import java.awt.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.client.command.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class KillAura extends Module
{
    private Value.Boolean swordOnly;
    private Value.Boolean caCheck;
    private Value.Boolean criticals;
    private Value.Double range;
    private boolean isAttacking;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public KillAura() {
        super("KillAura", Module.Category.Combat);
        this.isAttacking = false;
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (event.getPacket() instanceof CPacketUseEntity && this.criticals.getValue() && ((CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && KillAura.mc.player.onGround && this.isAttacking) {
                KillAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(KillAura.mc.player.posX, KillAura.mc.player.posY + 0.10000000149011612, KillAura.mc.player.posZ, false));
                KillAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(KillAura.mc.player.posX, KillAura.mc.player.posY, KillAura.mc.player.posZ, false));
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        this.range = this.registerDouble("Range", "Range", 5.0, 0.0, 10.0);
        this.swordOnly = this.registerBoolean("Sword Only", "SwordOnly", true);
        this.criticals = this.registerBoolean("Criticals", "Criticals", true);
        this.caCheck = this.registerBoolean("AutoCrystal Check", "AutoCrystalCheck", false);
    }
    
    public void onUpdate() {
        if (KillAura.mc.player == null || KillAura.mc.player.isDead) {
            return;
        }
        final List<Entity> targets = (List<Entity>)KillAura.mc.world.loadedEntityList.stream().filter(entity -> entity != KillAura.mc.player).filter(entity -> KillAura.mc.player.getDistance(entity) <= this.range.getValue()).filter(entity -> !entity.isDead).filter(entity -> entity instanceof EntityPlayer).filter(entity -> entity.getHealth() > 0.0f).filter(entity -> !Friends.isFriend(entity.getName())).sorted(Comparator.comparing(e -> KillAura.mc.player.getDistance(e))).collect(Collectors.toList());
        targets.forEach(target -> {
            if (!this.swordOnly.getValue() || KillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                if (!this.caCheck.getValue() || !((AutoCrystal)ModuleManager.getModuleByName("AutoCrystal")).isActive) {
                    this.attack(target);
                }
            }
        });
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
        Notifications2.sendNotification("KillAura was Enabled", TrayIcon.MessageType.INFO);
        Command.sendClientMessage("KillAura Was Enabled!");
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
        Notifications2.sendNotification("KillAura was Disabled", TrayIcon.MessageType.INFO);
        Command.sendClientMessage("KillAura Was Disabled!");
    }
    
    public void attack(final Entity e) {
        if (KillAura.mc.player.getCooledAttackStrength(0.0f) >= 1.0f) {
            this.isAttacking = true;
            KillAura.mc.playerController.attackEntity((EntityPlayer)KillAura.mc.player, e);
            KillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
            this.isAttacking = false;
        }
    }
}
