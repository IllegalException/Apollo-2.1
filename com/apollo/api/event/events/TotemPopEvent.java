//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import net.minecraft.entity.*;

public class TotemPopEvent extends MainEvent
{
    private final Entity entity;
    
    public TotemPopEvent(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
