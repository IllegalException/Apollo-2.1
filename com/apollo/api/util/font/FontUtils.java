//Decomped by XeonLyfe

package com.apollo.api.util.font;

import net.minecraft.client.*;
import com.apollo.client.*;

public class FontUtils
{
    private static final Minecraft mc;
    
    public static float drawStringWithShadow(final boolean customFont, final String text, final int x, final int y, final int color) {
        if (customFont) {
            return Main.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        return (float)FontUtils.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }
    
    public static int getStringWidth(final boolean customFont, final String str) {
        if (customFont) {
            return Main.fontRenderer.getStringWidth(str);
        }
        return FontUtils.mc.fontRenderer.getStringWidth(str);
    }
    
    public static int getFontHeight(final boolean customFont) {
        if (customFont) {
            return Main.fontRenderer.getHeight();
        }
        return FontUtils.mc.fontRenderer.FONT_HEIGHT;
    }
    
    public static float drawKeyStringWithShadow(final boolean customFont, final String text, final int x, final int y, final int color) {
        if (customFont) {
            return Main.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        return (float)FontUtils.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
