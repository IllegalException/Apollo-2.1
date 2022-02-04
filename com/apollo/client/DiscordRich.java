//Decomped by XeonLyfe

package com.apollo.client;

import net.minecraft.client.*;
import club.minnced.discord.rpc.*;

public class DiscordRich
{
    private static final String ClientId = "754125001824796675";
    private static final Minecraft mc;
    private static final DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static String details;
    private static String state;
    
    public static void init() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        DiscordRich.rpc.Discord_Initialize("754125001824796675", handlers, true, "");
        DiscordRich.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordRich.presence.details = "Version v0.2";
        DiscordRich.presence.state = "Main Menu";
        DiscordRich.presence.largeImageKey = "apollo";
        DiscordRich.presence.largeImageText = "Currently FPS: " + Minecraft.getDebugFPS();
        DiscordRich.rpc.Discord_UpdatePresence(DiscordRich.presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DiscordRich.rpc.Discord_RunCallbacks();
                    DiscordRich.details = Main.MODNAME + " Version " + "v0.2";
                    DiscordRich.state = "";
                    if (DiscordRich.mc.isIntegratedServerRunning()) {
                        DiscordRich.state = "Playing Singleplayer";
                    }
                    else if (DiscordRich.mc.getCurrentServerData() != null) {
                        if (!DiscordRich.mc.getCurrentServerData().serverIP.equals("")) {
                            DiscordRich.state = "Playing " + DiscordRich.mc.getCurrentServerData().serverIP;
                        }
                    }
                    else {
                        DiscordRich.state = "Main Menu | RIP S845";
                    }
                    if (!DiscordRich.details.equals(DiscordRich.presence.details) || !DiscordRich.state.equals(DiscordRich.presence.state)) {
                        DiscordRich.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    DiscordRich.presence.details = DiscordRich.details;
                    DiscordRich.presence.state = DiscordRich.state;
                    DiscordRich.rpc.Discord_UpdatePresence(DiscordRich.presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }, "Discord-RPC-Callback-Handler").start();
    }
    
    static {
        mc = Minecraft.getMinecraft();
        rpc = DiscordRPC.INSTANCE;
        DiscordRich.presence = new DiscordRichPresence();
    }
}
