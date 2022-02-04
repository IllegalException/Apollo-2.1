//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiScreen.class })
public class MixinShulkerViewer
{
    RenderItem itemRender;
    ResourceLocation resource;
    FontRenderer fontRenderer;
    
    public MixinShulkerViewer() {
        this.itemRender = Minecraft.getMinecraft().getRenderItem();
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }
    
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderToolTip(final ItemStack stack, final int x, final int y, final CallbackInfo info) {
        this.resource = new ResourceLocation("textures/gui/container/shulker_box.png");
        if (ModuleManager.isModuleEnabled("ShulkerViewer") && stack.getItem() instanceof ItemShulkerBox) {
            final NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
                final NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
                if (blockEntityTag.hasKey("Items", 9)) {
                    info.cancel();
                    final NonNullList<ItemStack> nonnulllist = (NonNullList<ItemStack>)NonNullList.withSize(27, (Object)ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems(blockEntityTag, (NonNullList)nonnulllist);
                    GlStateManager.enableBlend();
                    GlStateManager.disableRescaleNormal();
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();
                    final int x2 = x + 4;
                    final int y2 = y - 30;
                    this.itemRender.zLevel = 300.0f;
                    Minecraft.getMinecraft().renderEngine.bindTexture(this.resource);
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x2, y2, 7, 5, 162, 66);
                    this.fontRenderer.drawString(stack.getDisplayName(), x + 6, y - 28, Color.DARK_GRAY.getRGB());
                    GlStateManager.enableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.enableTexture2D();
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                    RenderHelper.enableGUIStandardItemLighting();
                    for (int i = 0; i < nonnulllist.size(); ++i) {
                        final int iX = x + 5 + i % 9 * 18;
                        final int iY = y + 1 + (i / 9 - 1) * 18;
                        final ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                        this.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
                        this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemStack, iX, iY, (String)null);
                    }
                    RenderHelper.disableStandardItemLighting();
                    this.itemRender.zLevel = 0.0f;
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.enableRescaleNormal();
                }
            }
        }
    }
}
