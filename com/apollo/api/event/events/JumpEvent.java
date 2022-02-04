//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import com.apollo.api.util.world.*;

public class JumpEvent extends MainEvent
{
    private Location location;
    
    public JumpEvent(final Location location) {
        this.location = location;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation(final Location location) {
        this.location = location;
    }
}
