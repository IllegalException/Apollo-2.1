//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import com.apollo.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.apollo.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ViewModel extends Module
{
    Value.Double xr;
    Value.Double yr;
    Value.Double zr;
    Value.Double xl;
    Value.Double yl;
    Value.Double zl;
    @EventHandler
    private Listener<TransformSideFirstPersonEvent> eventListener;
    
    public ViewModel() {
        super("ViewModel", Module.Category.Render);
        this.eventListener = (Listener<TransformSideFirstPersonEvent>)new Listener(event -> {
            if (event.getHandSide() == EnumHandSide.RIGHT) {
                GlStateManager.translate(this.xr.getValue(), this.yr.getValue(), this.zr.getValue());
            }
            else if (event.getHandSide() == EnumHandSide.LEFT) {
                GlStateManager.translate(this.xl.getValue(), this.yl.getValue(), this.zl.getValue());
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        this.xl = this.registerDouble("Left X", "LeftX", 0.0, -2.0, 2.0);
        this.yl = this.registerDouble("Left Y", "LeftY", 0.2, -2.0, 2.0);
        this.zl = this.registerDouble("Left Z", "LeftZ", -1.2, -2.0, 2.0);
        this.xr = this.registerDouble("Right X", "RightX", 0.0, -2.0, 2.0);
        this.yr = this.registerDouble("Right Y", "RightY", 0.2, -2.0, 2.0);
        this.zr = this.registerDouble("Right Z", "RightZ", -1.2, -2.0, 2.0);
    }
    
    public void onEnable() {
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
    }
}
