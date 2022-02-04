//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.item.*;

public class NoEntityTrace extends Module
{
    Value.Boolean pickaxeOnly;
    boolean isHoldingPickaxe;
    
    public NoEntityTrace() {
        super("NoEntityTrace", Module.Category.Misc);
        this.isHoldingPickaxe = false;
    }
    
    public void setup() {
        this.pickaxeOnly = this.registerBoolean("Pickaxe Only", "PickaxeOnly", true);
    }
    
    public void onUpdate() {
        this.isHoldingPickaxe = (NoEntityTrace.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe);
    }
    
    public boolean noTrace() {
        if (this.pickaxeOnly.getValue()) {
            return this.isEnabled() && this.isHoldingPickaxe;
        }
        return this.isEnabled();
    }
}
