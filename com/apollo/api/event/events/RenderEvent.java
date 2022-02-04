//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;

public class RenderEvent extends MainEvent
{
    private final Tessellator tessellator;
    private final Vec3d renderPos;
    private final float partialTicks;
    
    public RenderEvent(final Tessellator tessellator, final Vec3d renderPos, final float ticks) {
        this.tessellator = tessellator;
        this.renderPos = renderPos;
        this.partialTicks = ticks;
    }
    
    public Tessellator getTessellator() {
        return this.tessellator;
    }
    
    public BufferBuilder getBuffer() {
        return this.tessellator.getBuffer();
    }
    
    public Vec3d getRenderPos() {
        return this.renderPos;
    }
    
    public void setTranslation(final Vec3d translation) {
        this.getBuffer().setTranslation(-translation.x, -translation.y, -translation.z);
    }
    
    public void resetTranslation() {
        this.setTranslation(this.renderPos);
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
