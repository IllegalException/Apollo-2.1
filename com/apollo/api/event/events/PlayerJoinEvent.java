//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;

public class PlayerJoinEvent extends MainEvent
{
    private final String name;
    
    public PlayerJoinEvent(final String n) {
        this.name = n;
    }
    
    public String getName() {
        return this.name;
    }
}
