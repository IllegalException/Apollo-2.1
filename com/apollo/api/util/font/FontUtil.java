//Decomped by XeonLyfe

package com.apollo.api.util.font;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import com.apollo.client.*;

public class FontUtil
{
    private static FontRenderer fontRenderer;
    private static final Minecraft mc;
    
    public static void setupFontUtils() {
        FontUtil.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }
    
    public static int getStringWidth(final String text) {
        return FontUtil.fontRenderer.getStringWidth(StringUtils.stripControlCodes(text));
    }
    
    public static int getFontHeight() {
        return FontUtil.fontRenderer.FONT_HEIGHT;
    }
    
    public static void drawString(final String text, final double x, final double y, final int color) {
        FontUtil.fontRenderer.drawString(text, (int)x, (int)y, color);
    }
    
    public static float drawStringWithShadow(final boolean customFont, final String text, final int x, final int y, final int color) {
        if (customFont) {
            return Main.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        return (float)FontUtil.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }
    
    public static int getStringWidth(final boolean customFont, final String str) {
        if (customFont) {
            return Main.fontRenderer.getStringWidth(str);
        }
        return FontUtil.mc.fontRenderer.getStringWidth(str);
    }
    
    public static void drawCenteredString(final String text, final double x, final double y, final int color) {
        drawString(text, x - FontUtil.fontRenderer.getStringWidth(text) / 2.0f, y, color);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
