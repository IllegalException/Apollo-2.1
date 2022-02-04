//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;

public class PlayerSkinEvent extends MainEvent
{
    private final NetworkPlayerInfo networkPlayerInfo;
    
    public PlayerSkinEvent(final NetworkPlayerInfo networkPlayerInfo) {
        this.networkPlayerInfo = networkPlayerInfo;
    }
    
    public NetworkPlayerInfo getNetworkPlayerInfo() {
        return this.networkPlayerInfo;
    }
    
    public static class GetSkin extends PlayerSkinEvent
    {
        public ResourceLocation skinLocation;
        
        public GetSkin(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation skinLocation) {
            super(networkPlayerInfo);
            this.skinLocation = skinLocation;
        }
    }
    
    public static class HasSkin extends PlayerSkinEvent
    {
        public boolean result;
        
        public HasSkin(final NetworkPlayerInfo networkPlayerInfo, final boolean result) {
            super(networkPlayerInfo);
            this.result = result;
        }
    }
}
