//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.function.*;
import com.mojang.realmsclient.gui.*;

public class AutoTotem extends Module
{
    int totems;
    boolean moving;
    boolean returnI;
    Value.Boolean soft;
    
    public AutoTotem() {
        super("AutoTotem", Module.Category.Combat);
        this.moving = false;
        this.returnI = false;
    }
    
    public void setup() {
        this.soft = this.registerBoolean("Soft", "Soft", true);
    }
    
    public void onUpdate() {
        if (AutoTotem.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            int t = -1;
            for (int i = 0; i < 45; ++i) {
                if (AutoTotem.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                    t = i;
                    break;
                }
            }
            if (t == -1) {
                return;
            }
            AutoTotem.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            this.returnI = false;
        }
        this.totems = AutoTotem.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++this.totems;
        }
        else {
            if (this.soft.getValue() && !AutoTotem.mc.player.getHeldItemOffhand().isEmpty()) {
                return;
            }
            if (this.moving) {
                AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                this.moving = false;
                if (!AutoTotem.mc.player.inventory.getItemStack().isEmpty()) {
                    this.returnI = true;
                }
                return;
            }
            if (AutoTotem.mc.player.inventory.getItemStack().isEmpty()) {
                if (this.totems == 0) {
                    return;
                }
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (AutoTotem.mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                AutoTotem.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                this.moving = true;
            }
            else if (!this.soft.getValue()) {
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (AutoTotem.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                AutoTotem.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            }
        }
    }
    
    public String getHudInfo() {
        final String t = "[" + ChatFormatting.WHITE + this.totems + ChatFormatting.GRAY + "]";
        return t;
    }
}
