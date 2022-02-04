//Decomped by XeonLyfe

package com.apollo.api.util;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import org.lwjgl.input.*;

public class Wrapper
{
    private static FontRenderer fontRenderer;
    public static Minecraft mc;
    
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().player;
    }
    
    public static World getWorld() {
        return (World)getMinecraft().world;
    }
    
    public static int getKey(final String keyname) {
        return Keyboard.getKeyIndex(keyname.toUpperCase());
    }
    
    public static FontRenderer getFontRenderer() {
        return Wrapper.fontRenderer;
    }
    
    static {
        Wrapper.mc = Minecraft.getMinecraft();
    }
}
