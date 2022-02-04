//Decomped by XeonLyfe

package com.apollo.api.util.color;

import com.apollo.api.event.*;
import java.awt.*;

public class Rainbow
{
    public static int getInt() {
        return EventProcessor.INSTANCE.getRgb();
    }
    
    public static Color getColor() {
        return EventProcessor.INSTANCE.getC();
    }
    
    public static Color getColorWithOpacity(final int opacity) {
        return new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), opacity);
    }
    
    public static int getIntWithOpacity(final int opacity) {
        return getColorWithOpacity(opacity).getRGB();
    }
}
