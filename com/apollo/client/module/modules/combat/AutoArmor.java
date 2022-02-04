//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.client.module.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class AutoArmor extends Module
{
    public AutoArmor() {
        super("AutoArmor", Module.Category.Combat);
    }
    
    public void onUpdate() {
        if (AutoArmor.mc.player.ticksExisted % 2 == 0) {
            return;
        }
        if (AutoArmor.mc.currentScreen instanceof GuiContainer && !(AutoArmor.mc.currentScreen instanceof InventoryEffectRenderer)) {
            return;
        }
        final int[] bestArmorSlots = new int[4];
        final int[] bestArmorValues = new int[4];
        for (int armorType = 0; armorType < 4; ++armorType) {
            final ItemStack oldArmor = AutoArmor.mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor != null && oldArmor.getItem() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            final ItemStack stack = AutoArmor.mc.player.inventory.getStackInSlot(slot);
            if (stack.getCount() <= 1) {
                if (stack != null) {
                    if (stack.getItem() instanceof ItemArmor) {
                        final ItemArmor armor = (ItemArmor)stack.getItem();
                        final int armorType2 = armor.armorType.ordinal() - 2;
                        if (armorType2 != 2 || !AutoArmor.mc.player.inventory.armorItemInSlot(armorType2).getItem().equals(Items.ELYTRA)) {
                            final int armorValue = armor.damageReduceAmount;
                            if (armorValue > bestArmorValues[armorType2]) {
                                bestArmorSlots[armorType2] = slot;
                                bestArmorValues[armorType2] = armorValue;
                            }
                        }
                    }
                }
            }
        }
        for (int armorType = 0; armorType < 4; ++armorType) {
            int slot2 = bestArmorSlots[armorType];
            if (slot2 != -1) {
                final ItemStack oldArmor2 = AutoArmor.mc.player.inventory.armorItemInSlot(armorType);
                if (oldArmor2 == null || oldArmor2 != ItemStack.EMPTY || AutoArmor.mc.player.inventory.getFirstEmptyStack() != -1) {
                    if (slot2 < 9) {
                        slot2 += 36;
                    }
                    AutoArmor.mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
                    AutoArmor.mc.playerController.windowClick(0, slot2, 0, ClickType.QUICK_MOVE, (EntityPlayer)AutoArmor.mc.player);
                    break;
                }
            }
        }
    }
}
