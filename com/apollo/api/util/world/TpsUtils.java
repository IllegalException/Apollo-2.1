//Decomped by XeonLyfe

package com.apollo.api.util.world;

import com.apollo.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import java.util.*;
import com.apollo.client.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.server.*;

public class TpsUtils
{
    private static final float[] tickRates;
    private int nextIndex;
    private long timeLastTimeUpdate;
    @EventHandler
    Listener<PacketEvent.Receive> listener;
    
    public TpsUtils() {
        this.nextIndex = 0;
        this.listener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketTimeUpdate) {
                this.onTimeUpdate();
            }
        }, new Predicate[0]);
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(TpsUtils.tickRates, 0.0f);
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public static float getTickRate() {
        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (final float tickRate : TpsUtils.tickRates) {
            if (tickRate > 0.0f) {
                sumTickRates += tickRate;
                ++numTicks;
            }
        }
        return MathHelper.clamp(sumTickRates / numTicks, 0.0f, 20.0f);
    }
    
    private void onTimeUpdate() {
        if (this.timeLastTimeUpdate != -1L) {
            final float timeElapsed = (System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
            TpsUtils.tickRates[this.nextIndex % TpsUtils.tickRates.length] = MathHelper.clamp(20.0f / timeElapsed, 0.0f, 20.0f);
            ++this.nextIndex;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
    
    static {
        tickRates = new float[20];
    }
}
