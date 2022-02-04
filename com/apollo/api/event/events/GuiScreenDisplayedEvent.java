//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import net.minecraft.client.gui.*;

public class GuiScreenDisplayedEvent extends MainEvent
{
    private final GuiScreen guiScreen;
    
    public GuiScreenDisplayedEvent(final GuiScreen screen) {
        this.guiScreen = screen;
    }
    
    public GuiScreen getScreen() {
        return this.guiScreen;
    }
}
