//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import net.minecraft.util.math.*;

public class DestroyBlockEvent extends MainEvent
{
    BlockPos pos;
    
    public DestroyBlockEvent(final BlockPos blockPos) {
        this.pos = blockPos;
    }
    
    public BlockPos getBlockPos() {
        return this.pos;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
}
