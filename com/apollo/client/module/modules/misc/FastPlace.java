//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.init.*;

public class FastPlace extends Module
{
    Value.Boolean exp;
    Value.Boolean crystals;
    Value.Boolean everything;
    
    public FastPlace() {
        super("FastPlace", Module.Category.Misc);
    }
    
    public void setup() {
        this.exp = this.registerBoolean("Exp", "Exp", false);
        this.crystals = this.registerBoolean("Crystals", "Crystals", false);
        this.everything = this.registerBoolean("Eveything", "Everything", false);
    }
    
    public void onUpdate() {
        if ((this.exp.getValue() && FastPlace.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) || FastPlace.mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
        if ((this.crystals.getValue() && FastPlace.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) || FastPlace.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
        if (this.everything.getValue()) {
            FastPlace.mc.rightClickDelayTimer = 0;
        }
        FastPlace.mc.playerController.blockHitDelay = 0;
    }
}
