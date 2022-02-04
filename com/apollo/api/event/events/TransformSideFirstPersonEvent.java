//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import net.minecraft.util.*;

public class TransformSideFirstPersonEvent extends MainEvent
{
    private final EnumHandSide handSide;
    
    public TransformSideFirstPersonEvent(final EnumHandSide handSide) {
        this.handSide = handSide;
    }
    
    public EnumHandSide getHandSide() {
        return this.handSide;
    }
}
