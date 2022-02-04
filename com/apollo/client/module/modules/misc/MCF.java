//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.apollo.client.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import org.lwjgl.input.*;
import com.apollo.api.players.friends.*;
import com.mojang.realmsclient.gui.*;
import com.apollo.client.command.*;

public class MCF extends Module
{
    @EventHandler
    private final Listener<InputEvent.MouseInputEvent> listener;
    
    public MCF() {
        super("MCF", Module.Category.Misc);
        this.listener = (Listener<InputEvent.MouseInputEvent>)new Listener(event -> {
            if (MCF.mc.objectMouseOver.typeOfHit.equals((Object)RayTraceResult.Type.ENTITY) && MCF.mc.objectMouseOver.entityHit instanceof EntityPlayer && Mouse.getEventButton() == 2) {
                if (Friends.isFriend(MCF.mc.objectMouseOver.entityHit.getName())) {
                    Main.getInstance().friends.delFriend(MCF.mc.objectMouseOver.entityHit.getName());
                    Command.sendClientMessage(ChatFormatting.RED + "Removed " + MCF.mc.objectMouseOver.entityHit.getName() + " from friends list");
                }
                else {
                    Main.getInstance().friends.addFriend(MCF.mc.objectMouseOver.entityHit.getName());
                    Command.sendClientMessage(ChatFormatting.GREEN + "Added " + MCF.mc.objectMouseOver.entityHit.getName() + " to friends list");
                }
            }
        }, new Predicate[0]);
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
