//Decomped by XeonLyfe

package com.apollo.client.module.modules.movement;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import com.apollo.api.event.events.*;
import java.util.function.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;
import com.apollo.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;

public class PlayerModifier extends Module
{
    public Value.Boolean guiMove;
    public Value.Boolean noPush;
    public Value.Boolean noSlow;
    Value.Boolean antiKnockBack;
    @EventHandler
    private final Listener<InputUpdateEvent> eventListener;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    @EventHandler
    private final Listener<WaterPushEvent> waterPushEventListener;
    
    public PlayerModifier() {
        super("Player Modifier", Module.Category.Movement);
        this.eventListener = (Listener<InputUpdateEvent>)new Listener(event -> {
            if (this.noSlow.getValue() && PlayerModifier.mc.player.isHandActive() && !PlayerModifier.mc.player.isRiding()) {
                final MovementInput movementInput = event.getMovementInput();
                movementInput.moveStrafe *= 5.0f;
                final MovementInput movementInput2 = event.getMovementInput();
                movementInput2.moveForward *= 5.0f;
            }
        }, new Predicate[0]);
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (this.antiKnockBack.getValue()) {
                if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == PlayerModifier.mc.player.getEntityId()) {
                    event.cancel();
                }
                if (event.getPacket() instanceof SPacketExplosion) {
                    event.cancel();
                }
            }
        }, new Predicate[0]);
        this.waterPushEventListener = (Listener<WaterPushEvent>)new Listener(event -> {
            if (this.noPush.getValue()) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        this.guiMove = this.registerBoolean("Gui Move", "GuiMove", true);
        this.noPush = this.registerBoolean("No Push", "NoPush", true);
        this.noSlow = this.registerBoolean("No Slow", "NoSlow", true);
        this.antiKnockBack = this.registerBoolean("Velocity", "Velocity", true);
    }
    
    public void onUpdate() {
        if (this.guiMove.getValue() && PlayerModifier.mc.currentScreen != null && !(PlayerModifier.mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP player = PlayerModifier.mc.player;
                player.rotationPitch -= 5.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP player2 = PlayerModifier.mc.player;
                player2.rotationPitch += 5.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP player3 = PlayerModifier.mc.player;
                player3.rotationYaw += 5.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP player4 = PlayerModifier.mc.player;
                player4.rotationYaw -= 5.0f;
            }
            if (PlayerModifier.mc.player.rotationPitch > 90.0f) {
                PlayerModifier.mc.player.rotationPitch = 90.0f;
            }
            if (PlayerModifier.mc.player.rotationPitch < -90.0f) {
                PlayerModifier.mc.player.rotationPitch = -90.0f;
            }
        }
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
