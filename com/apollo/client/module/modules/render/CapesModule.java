//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import java.util.*;

public class CapesModule extends Module
{
    public Value.Mode capeMode;
    
    public CapesModule() {
        super("Capes", Module.Category.Render);
        this.setDrawn(false);
    }
    
    public void setup() {
        final ArrayList<String> CapeType = new ArrayList<String>();
        CapeType.add("Black");
        CapeType.add("White");
        this.capeMode = this.registerMode("Type", "Type", (List)CapeType, "Black");
    }
}
