//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;

public class PlayerLeaveEvent extends MainEvent
{
    private final String name;
    
    public PlayerLeaveEvent(final String n) {
        this.name = n;
    }
    
    public String getName() {
        return this.name;
    }
}
