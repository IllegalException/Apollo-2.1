//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class DamageBlockEvent extends MainEvent
{
    private BlockPos pos;
    private EnumFacing face;
    
    public DamageBlockEvent(final BlockPos pos, final EnumFacing face) {
        this.pos = pos;
        this.face = face;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
    
    public EnumFacing getFace() {
        return this.face;
    }
    
    public void setFace(final EnumFacing face) {
        this.face = face;
    }
}
