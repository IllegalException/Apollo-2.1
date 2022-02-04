//Decomped by XeonLyfe

package com.apollo.client.clickgui.buttons;

import com.apollo.api.values.*;
import com.apollo.client.clickgui.frame.*;
import com.apollo.api.util.color.*;
import com.apollo.client.clickgui.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.api.util.font.*;

public class BooleanComponent extends Component
{
    private boolean hovered;
    private final Value.Boolean op;
    private final Buttons parent;
    private int offset;
    private int x;
    private int y;
    
    public BooleanComponent(final Value.Boolean option, final Buttons button, final int offset) {
        this.op = option;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void renderComponent() {
        if (ColorMain.rainbow.getValue()) {
            ClickGUI.color = Rainbow.getColorWithOpacity(ClickGuiModule.opacity.getValue()).getRGB();
        }
        else {
            ClickGUI.color = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), ClickGuiModule.opacity.getValue()).getRGB();
        }
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 1, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 16, this.hovered ? (this.op.getValue() ? ClickGUI.color : new Color(195, 195, 195, ClickGuiModule.opacity.getValue() - 50).darker().darker().getRGB()) : (this.op.getValue() ? ClickGUI.color : new Color(195, 195, 195, ClickGuiModule.opacity.getValue() - 50).getRGB()));
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 1, new Color(195, 195, 195, ClickGuiModule.opacity.getValue() - 50).getRGB());
        FontUtils.drawStringWithShadow(HUD.customFont.getValue(), this.op.getName(), this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 4, -1);
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.op.setValue(!this.op.getValue());
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 16;
    }
}
