//Decomped by XeonLyfe

package com.apollo.client.module.modules.misc;

import com.apollo.client.module.*;
import com.apollo.client.*;
import com.apollo.client.command.*;

public class RpcModule extends Module
{
    public RpcModule() {
        super("DiscordRPC", Module.Category.Misc);
        this.setEnabled(true);
        this.setDrawn(false);
    }
    
    public void onEnable() {
        DiscordRich.init();
        if (RpcModule.mc.player != null) {
            Command.sendClientMessage("discord rpc started");
        }
    }
    
    public void onDisable() {
        Command.sendClientMessage("you need to restart your game disable rpc");
    }
}
