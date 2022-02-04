//Decomped by XeonLyfe

package com.apollo.client.clickgui.frame;

import com.apollo.client.module.*;
import net.minecraft.util.*;
import com.apollo.client.*;
import com.apollo.api.values.*;
import com.apollo.client.clickgui.buttons.*;
import java.util.*;
import com.apollo.api.util.color.*;
import com.apollo.client.clickgui.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.api.util.font.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class Buttons extends Component
{
    public Module mod;
    public Frames parent;
    public int offset;
    private boolean isHovered;
    private final ArrayList<Component> subcomponents;
    public boolean open;
    private final int height;
    private static final ResourceLocation opengui;
    private static final ResourceLocation closedgui;
    
    public Buttons(final Module mod, final Frames parent, final int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        this.height = 16;
        int opY = offset + 16;
        if (Main.getInstance().valuesManager.getSettingsForMod(mod) != null && !Main.getInstance().valuesManager.getSettingsForMod(mod).isEmpty()) {
            for (final Value s : Main.getInstance().valuesManager.getSettingsForMod(mod)) {
                switch (s.getType()) {
                    case MODE: {
                        this.subcomponents.add((Component)new ModeComponent((Value.Mode)s, this, mod, opY));
                        opY += 16;
                        continue;
                    }
                    case BOOLEAN: {
                        this.subcomponents.add((Component)new BooleanComponent((Value.Boolean)s, this, opY));
                        opY += 16;
                        continue;
                    }
                    case DOUBLE: {
                        this.subcomponents.add((Component)new DoubleComponent((Value.Double)s, this, opY));
                        opY += 16;
                        continue;
                    }
                    case INT: {
                        this.subcomponents.add((Component)new IntegerComponent((Value.Integer)s, this, opY));
                        opY += 16;
                        continue;
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        this.subcomponents.add((Component)new KeybindComponent(this, opY));
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 16;
        for (final Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 16;
        }
    }
    
    @Override
    public void renderComponent() {
        if (ColorMain.rainbow.getValue()) {
            ClickGUI.color = Rainbow.getColorWithOpacity(ClickGuiModule.opacity.getValue()).getRGB();
        }
        else {
            ClickGUI.color = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), ClickGuiModule.opacity.getValue()).getRGB();
        }
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset + 1, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 16 + this.offset, this.isHovered ? (this.mod.isEnabled() ? ClickGUI.color : new Color(195, 195, 195, ClickGuiModule.opacity.getValue() - 50).darker().darker().getRGB()) : (this.mod.isEnabled() ? ClickGUI.color : new Color(195, 195, 195, ClickGuiModule.opacity.getValue() - 50).getRGB()));
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + this.offset + 1, new Color(195, 195, 195, ClickGuiModule.opacity.getValue() - 50).getRGB());
        FontUtils.drawStringWithShadow(HUD.customFont.getValue(), this.mod.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2 + 2, -1);
        if (this.subcomponents.size() > 1) {
            if (ClickGuiModule.icon.getValue().equalsIgnoreCase("Image")) {
                FontUtils.drawStringWithShadow(HUD.customFont.getValue(), this.open ? "" : "", this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 2 + 2, -1);
                if (this.open) {
                    this.drawOpenRender(this.parent.getX() + this.parent.getWidth() - 13, this.parent.getY() + this.offset + 2 + 2);
                }
                else {
                    this.drawClosedRender(this.parent.getX() + this.parent.getWidth() - 13, this.parent.getY() + this.offset + 2 + 2);
                }
            }
            else {
                FontUtils.drawStringWithShadow(HUD.customFont.getValue(), this.open ? "~" : ">", this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 2 + 2, -1);
            }
        }
        if (this.open && !this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }
    }
    
    @Override
    public int getHeight() {
        if (this.open) {
            return 16 * (this.subcomponents.size() + 1);
        }
        return 16;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (final Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        for (final Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 16 + this.offset;
    }
    
    public void drawOpenRender(final int x, final int y) {
        GlStateManager.enableAlpha();
        this.mc.getTextureManager().bindTexture(Buttons.opengui);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        Gui.drawScaledCustomSizeModalRect(x, y, 0.0f, 0.0f, 256, 256, 10, 10, 256.0f, 256.0f);
        GL11.glPopMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
    }
    
    public void drawClosedRender(final int x, final int y) {
        GlStateManager.enableAlpha();
        this.mc.getTextureManager().bindTexture(Buttons.closedgui);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        Gui.drawScaledCustomSizeModalRect(x, y, 0.0f, 0.0f, 256, 256, 10, 10, 256.0f, 256.0f);
        GL11.glPopMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
    }
    
    static {
        opengui = new ResourceLocation("minecraft:opengui.png");
        closedgui = new ResourceLocation("minecraft:closedgui.png");
    }
}
