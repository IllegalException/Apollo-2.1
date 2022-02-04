//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import java.util.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import com.google.common.collect.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ NetHandlerPlayClient.class })
public abstract class MixinNetHandlerPlayClient
{
    @Shadow
    private final Map<UUID, NetworkPlayerInfo> playerInfoMap;
    
    public MixinNetHandlerPlayClient() {
        this.playerInfoMap = (Map<UUID, NetworkPlayerInfo>)Maps.newHashMap();
    }
    
    @Inject(method = { "Lnet/minecraft/client/network/NetHandlerPlayClient;handlePlayerListItem(Lnet/minecraft/network/play/server/SPacketPlayerListItem;)V" }, at = { @At("HEAD") })
    public void preHandlePlayerListItem(final SPacketPlayerListItem listItem, final CallbackInfo callbackInfo) {
        try {
            if (listItem.getEntries().size() <= 1) {
                if (listItem.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                    listItem.getEntries().forEach(data -> {
                        if (data.getProfile().getId().equals(Minecraft.getMinecraft().player.getGameProfile().getId()) || data.getProfile().getName() != null) {}
                    });
                }
                else if (listItem.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                    listItem.getEntries().forEach(data2 -> {
                        if (data2.getProfile().getId() == null || !data2.getProfile().getId().equals(Minecraft.getMinecraft().player.getGameProfile().getId())) {}
                    });
                }
            }
        }
        catch (Exception ex) {}
    }
}
