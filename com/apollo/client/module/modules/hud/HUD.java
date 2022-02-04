//Decomped by XeonLyfe

package com.apollo.client.module.modules.hud;

import com.apollo.api.values.*;
import net.minecraft.util.math.*;
import java.awt.*;
import java.text.*;
import net.minecraft.client.resources.*;
import com.apollo.api.util.world.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import com.apollo.client.module.modules.combat.*;
import net.minecraft.client.entity.*;
import com.apollo.api.players.friends.*;
import com.apollo.client.module.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import java.util.function.*;
import com.apollo.api.util.font.*;
import net.minecraft.client.gui.*;
import com.apollo.api.util.color.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import com.apollo.client.*;
import net.minecraft.potion.*;

public class HUD extends Module
{
    public static Value.Boolean customFont;
    Value.Boolean PotionEffects;
    Value.Boolean Watermark;
    Value.Boolean Welcomer;
    Value.Boolean Inventory;
    Value.Integer inventoryX;
    Value.Integer inventoryY;
    Value.Boolean InfoTell;
    Value.Mode Type;
    Value.Boolean ArrayList;
    Value.Boolean ArmorHud;
    Value.Integer potionx;
    Value.Integer potiony;
    Value.Integer welcomex;
    Value.Integer welcomey;
    Value.Integer infox;
    Value.Integer infoy;
    Value.Boolean sortUp;
    Value.Boolean right;
    Value.Boolean psortUp;
    Value.Boolean pright;
    Value.Integer arrayx;
    Value.Integer arrayy;
    private BlockPos[] surroundOffset;
    ResourceLocation resource;
    Color c;
    int sort;
    int modCount;
    int count;
    DecimalFormat format1;
    DecimalFormat format2;
    private static final RenderItem itemRender;
    int totems;
    
    public HUD() {
        super("HUD", Module.Category.HUD);
        this.format1 = new DecimalFormat("0");
        this.format2 = new DecimalFormat("00");
        this.setDrawn(false);
        this.resource = new ResourceLocation("minecraft:inventory_viewer.png");
    }
    
    public void setup() {
        final ArrayList<String> Modes = new ArrayList<String>();
        Modes.add("PvP");
        Modes.add("Combat");
        this.Type = this.registerMode("Info Type", "InfoType", (List)Modes, "PvP");
        this.infox = this.registerInteger("Information X", "InformationX", 0, 0, 1000);
        this.infoy = this.registerInteger("Information Y", "InformationY", 0, 0, 1000);
        this.InfoTell = this.registerBoolean("Information", "Information", false);
        this.ArmorHud = this.registerBoolean("Armor Hud", "ArmorHud", false);
        this.ArrayList = this.registerBoolean("ArrayList", "ArrayList", false);
        this.sortUp = this.registerBoolean("Array Sort Up", "ArraySortUp", false);
        this.right = this.registerBoolean("Array Right", "ArrayRight", false);
        this.arrayx = this.registerInteger("Array X", "ArrayX", 0, 0, 1000);
        this.arrayy = this.registerInteger("Array Y", "ArrayY", 0, 0, 1000);
        this.Inventory = this.registerBoolean("Inventory", "Inventory", false);
        this.inventoryX = this.registerInteger("Inventory X", "InventoryX", 0, 0, 1000);
        this.inventoryY = this.registerInteger("Inventory Y", "InventoryY", 12, 0, 1000);
        this.PotionEffects = this.registerBoolean("Potion Effects", "PotionEffects", false);
        this.potionx = this.registerInteger("Potion X", "PotionX", 0, 0, 1000);
        this.potiony = this.registerInteger("Potion Y", "PotionY", 0, 0, 1000);
        this.psortUp = this.registerBoolean("Potion Sort Up", "PotionSortUp", false);
        this.pright = this.registerBoolean("Potion Right", "PotionRight", false);
        this.Watermark = this.registerBoolean("Watermark", "Watermark", false);
        this.Welcomer = this.registerBoolean("Welcomer", "Welcomer", false);
        this.welcomex = this.registerInteger("Welcomer X", "WelcomerX", 0, 0, 1000);
        this.welcomey = this.registerInteger("Welcomer Y", "WelcomerY", 0, 0, 1000);
        HUD.customFont = this.registerBoolean("Custom Font", "CustomFont", true);
    }
    
    public void onRender() {
        if (ColorMain.rainbow.getValue()) {
            this.c = Rainbow.getColor();
        }
        else {
            this.c = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue());
        }
        if (this.PotionEffects.getValue()) {
            this.count = 0;
            try {
                final String name;
                final double duration;
                final int amplifier;
                final double p1;
                final double p2;
                final double p3;
                final String minutes;
                final String seconds;
                final String s;
                HUD.mc.player.getActivePotionEffects().forEach(effect -> {
                    name = I18n.format(effect.getPotion().getName(), new Object[0]);
                    duration = effect.getDuration() / TpsUtils.getTickRate();
                    amplifier = effect.getAmplifier() + 1;
                    p1 = duration % 60.0;
                    p2 = duration / 60.0;
                    p3 = p2 % 60.0;
                    minutes = this.format1.format(p3);
                    seconds = this.format2.format(p1);
                    s = name + " " + amplifier + ChatFormatting.GRAY + " " + minutes + ":" + seconds;
                    if (this.psortUp.getValue()) {
                        if (this.pright.getValue()) {
                            this.drawStringWithShadow(s, this.potionx.getValue() - this.getWidth(s), this.potiony.getValue() + this.count * 10, this.c.getRGB());
                        }
                        else {
                            this.drawStringWithShadow(s, this.potionx.getValue(), this.potiony.getValue() + this.count * 10, this.c.getRGB());
                        }
                        ++this.count;
                    }
                    else {
                        if (this.pright.getValue()) {
                            this.drawStringWithShadow(s, this.potionx.getValue() - this.getWidth(s), this.potiony.getValue() + this.count * -10, this.c.getRGB());
                        }
                        else {
                            this.drawStringWithShadow(s, this.potionx.getValue(), this.potiony.getValue() + this.count * -10, this.c.getRGB());
                        }
                        ++this.count;
                    }
                    return;
                });
            }
            catch (NullPointerException e2) {
                e2.printStackTrace();
            }
        }
        if (this.Watermark.getValue()) {
            this.drawStringWithShadow("Apollo v0.2", 0, 0, this.c.getRGB());
        }
        if (this.Welcomer.getValue()) {
            this.drawStringWithShadow("Hello Apollo User: " + HUD.mc.player.getName() + " :)", this.welcomex.getValue(), this.welcomey.getValue(), this.c.getRGB());
        }
        if (this.Inventory.getValue()) {
            this.drawInventory(this.inventoryX.getValue(), this.inventoryY.getValue());
        }
        if (this.InfoTell.getValue()) {
            if (this.Type.getValue().equalsIgnoreCase("PvP")) {
                final Color on = new Color(0, 255, 0);
                final Color off = new Color(255, 0, 0);
                final Color watermark = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue());
                this.totems = HUD.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
                if (HUD.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                    ++this.totems;
                }
                final EntityEnderCrystal crystal = (EntityEnderCrystal)HUD.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).filter(e -> HUD.mc.player.getDistance(e) <= AutoCrystal.range.getValue()).map(entity -> entity).min(Comparator.comparing(c -> HUD.mc.player.getDistance(c))).orElse(null);
                final EntityOtherPlayerMP players = (EntityOtherPlayerMP)HUD.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityOtherPlayerMP).filter(entity -> !Friends.isFriend(entity.getName())).filter(e -> HUD.mc.player.getDistance(e) <= AutoCrystal.placeRange.getValue()).map(entity -> entity).min(Comparator.comparing(c -> HUD.mc.player.getDistance(c))).orElse(null);
                final AutoCrystal a = (AutoCrystal)ModuleManager.getModuleByName("AutoCrystal");
                this.surroundOffset = new BlockPos[] { new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) };
                final List<EntityPlayer> entities = new ArrayList<EntityPlayer>((Collection<? extends EntityPlayer>)HUD.mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList()));
                if (this.Type.getValue().equalsIgnoreCase("PvP")) {
                    this.drawStringWithShadow("Apollo", this.infox.getValue(), this.infoy.getValue(), this.c.getRGB());
                    if (players != null && HUD.mc.player.getDistance((Entity)players) <= AutoCrystal.range.getValue()) {
                        this.drawStringWithShadow("HitRange", this.infox.getValue(), this.infoy.getValue() + 10, on.getRGB());
                    }
                    else {
                        this.drawStringWithShadow("HitRange", this.infox.getValue(), this.infoy.getValue() + 10, off.getRGB());
                    }
                    if (players != null && HUD.mc.player.getDistance((Entity)players) <= AutoCrystal.placeRange.getValue()) {
                        this.drawStringWithShadow("PlaceRange", this.infox.getValue(), this.infoy.getValue() + 20, on.getRGB());
                    }
                    else {
                        this.drawStringWithShadow("PlaceRange", this.infox.getValue(), this.infoy.getValue() + 20, off.getRGB());
                    }
                    if (this.totems > 0 && ModuleManager.isModuleEnabled("AutoTotem")) {
                        this.drawStringWithShadow(this.totems + "", this.infox.getValue(), this.infoy.getValue() + 30, on.getRGB());
                    }
                    else {
                        this.drawStringWithShadow(this.totems + "", this.infox.getValue(), this.infoy.getValue() + 30, off.getRGB());
                    }
                    if (this.getPing() > 100) {
                        this.drawStringWithShadow("PING " + this.getPing(), this.infox.getValue(), this.infoy.getValue() + 40, off.getRGB());
                    }
                    else {
                        this.drawStringWithShadow("PING " + this.getPing(), this.infox.getValue(), this.infoy.getValue() + 40, on.getRGB());
                    }
                    for (final EntityPlayer e3 : entities) {
                        int i = 0;
                        for (final BlockPos add : this.surroundOffset) {
                            ++i;
                            final BlockPos o = new BlockPos(e3.getPositionVector().x, e3.getPositionVector().y, e3.getPositionVector().z).add(add.getX(), add.getY(), add.getZ());
                            if (HUD.mc.world.getBlockState(o).getBlock() == Blocks.OBSIDIAN) {
                                if (i == 1 && a.canPlaceCrystal(o.north(1).down())) {
                                    this.drawStringWithShadow("LBY", this.infox.getValue(), this.infoy.getValue() + 50, on.getRGB());
                                }
                                if (i == 2 && a.canPlaceCrystal(o.east(1).down())) {
                                    this.drawStringWithShadow("LBY", this.infox.getValue(), this.infoy.getValue() + 50, on.getRGB());
                                }
                                if (i == 3 && a.canPlaceCrystal(o.south(1).down())) {
                                    this.drawStringWithShadow("LBY", this.infox.getValue(), this.infoy.getValue() + 50, on.getRGB());
                                }
                                if (i == 4 && a.canPlaceCrystal(o.west(1).down())) {
                                    this.drawStringWithShadow("LBY", this.infox.getValue(), this.infoy.getValue() + 50, on.getRGB());
                                }
                            }
                            else {
                                this.drawStringWithShadow("LBY", this.infox.getValue(), this.infoy.getValue() + 50, off.getRGB());
                            }
                        }
                    }
                }
            }
            else if (this.Type.getValue().equalsIgnoreCase("Combat")) {
                this.drawStringWithShadow(" ", this.infox.getValue(), this.infoy.getValue(), this.c.getRGB());
                if (ModuleManager.isModuleEnabled("AutoCrystal")) {
                    this.drawStringWithShadow("AC: Enabled", this.infox.getValue(), this.infoy.getValue(), Color.green.getRGB());
                }
                else {
                    this.drawStringWithShadow("AC: Disabled", this.infox.getValue(), this.infoy.getValue(), Color.red.getRGB());
                }
                if (ModuleManager.isModuleEnabled("KillAura")) {
                    this.drawStringWithShadow("KA: Enabled", this.infox.getValue(), this.infoy.getValue() + 10, Color.green.getRGB());
                }
                else {
                    this.drawStringWithShadow("KA: Disabled", this.infox.getValue(), this.infoy.getValue() + 10, Color.red.getRGB());
                }
                if (ModuleManager.isModuleEnabled("AutoFeetPlace")) {
                    this.drawStringWithShadow("FP: Enabled", this.infox.getValue(), this.infoy.getValue() + 20, Color.green.getRGB());
                }
                else {
                    this.drawStringWithShadow("FP: Disabled", this.infox.getValue(), this.infoy.getValue() + 20, Color.red.getRGB());
                }
                if (ModuleManager.isModuleEnabled("AutoTrap")) {
                    this.drawStringWithShadow("AT: Enabled", this.infox.getValue(), this.infoy.getValue() + 30, Color.green.getRGB());
                }
                else {
                    this.drawStringWithShadow("AT: Disabled", this.infox.getValue(), this.infoy.getValue() + 30, Color.red.getRGB());
                }
                if (ModuleManager.isModuleEnabled("SelfTrap")) {
                    this.drawStringWithShadow("ST: Enabled", this.infox.getValue(), this.infoy.getValue() + 40, Color.green.getRGB());
                }
                else {
                    this.drawStringWithShadow("ST: Disabled", this.infox.getValue(), this.infoy.getValue() + 40, Color.red.getRGB());
                }
            }
        }
        final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
        if (this.ArrayList.getValue()) {
            if (this.sortUp.getValue()) {
                this.sort = -1;
            }
            else {
                this.sort = 1;
            }
            this.modCount = 0;
            final Object o2;
            int rgb;
            int r;
            int g;
            int b;
            final int n;
            final int n2;
            final int n3;
            final int n4;
            ModuleManager.getModules().stream().filter(Module::isEnabled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> FontUtils.getStringWidth(HUD.customFont.getValue(), module.getName() + ChatFormatting.GRAY + " " + module.getHudInfo()) * -1)).forEach(m -> {
                if (ColorMain.rainbow.getValue()) {
                    rgb = Color.HSBtoRGB(o2[0], 1.0f, 1.0f);
                    r = (rgb >> 16 & 0xFF);
                    g = (rgb >> 8 & 0xFF);
                    b = (rgb & 0xFF);
                    this.c = new Color(r, g, b);
                }
                else {
                    this.c = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue());
                }
                if (this.sortUp.getValue()) {
                    if (this.right.getValue()) {
                        this.drawStringWithShadow(m.getName() + ChatFormatting.GRAY + m.getHudInfo(), this.arrayx.getValue() - FontUtils.getStringWidth(HUD.customFont.getValue(), m.getName() + ChatFormatting.GRAY + m.getHudInfo()), this.arrayy.getValue() + this.modCount * 10, this.c.getRGB());
                        o2[n] += 0.02f;
                    }
                    else {
                        this.drawStringWithShadow(m.getName() + ChatFormatting.GRAY + m.getHudInfo(), this.arrayx.getValue(), this.arrayy.getValue() + this.modCount * 10, this.c.getRGB());
                        o2[n2] += 0.02f;
                    }
                    ++this.modCount;
                }
                else {
                    if (this.right.getValue()) {
                        this.drawStringWithShadow(m.getName() + ChatFormatting.GRAY + m.getHudInfo(), this.arrayx.getValue() - FontUtils.getStringWidth(HUD.customFont.getValue(), m.getName() + ChatFormatting.GRAY + " " + m.getHudInfo()), this.arrayy.getValue() + this.modCount * -10, this.c.getRGB());
                        o2[n3] += 0.02f;
                    }
                    else {
                        this.drawStringWithShadow(m.getName() + ChatFormatting.GRAY + m.getHudInfo(), this.arrayx.getValue(), this.arrayy.getValue() + this.modCount * -10, this.c.getRGB());
                        o2[n4] += 0.02f;
                    }
                    ++this.modCount;
                }
                return;
            });
        }
        if (this.ArmorHud.getValue()) {
            GlStateManager.enableTexture2D();
            final ScaledResolution resolution = new ScaledResolution(HUD.mc);
            final int j = resolution.getScaledWidth() / 2;
            int iteration = 0;
            final int y = resolution.getScaledHeight() - 55 - (HUD.mc.player.isInWater() ? 10 : 0);
            for (final ItemStack is : HUD.mc.player.inventory.armorInventory) {
                ++iteration;
                if (is.isEmpty()) {
                    continue;
                }
                final int x = j - 90 + (9 - iteration) * 20 + 2;
                GlStateManager.enableDepth();
                HUD.itemRender.zLevel = 200.0f;
                HUD.itemRender.renderItemAndEffectIntoGUI(is, x, y);
                HUD.itemRender.renderItemOverlayIntoGUI(HUD.mc.fontRenderer, is, x, y, "");
                HUD.itemRender.zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                final String s2 = (is.getCount() > 1) ? (is.getCount() + "") : "";
                HUD.mc.fontRenderer.drawStringWithShadow(s2, (float)(x + 19 - 2 - HUD.mc.fontRenderer.getStringWidth(s2)), (float)(y + 9), 16777215);
                final float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
                final float red = 1.0f - green;
                final int dmg = 100 - (int)(red * 100.0f);
                this.drawStringWithShadow(dmg + "", x + 8 - HUD.mc.fontRenderer.getStringWidth(dmg + "") / 2, y - 11, ColourHolder.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
    
    public void drawInventory(final int x, final int y) {
        if (this.Inventory.getValue()) {
            GlStateManager.enableAlpha();
            HUD.mc.renderEngine.bindTexture(this.resource);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            HUD.mc.ingameGUI.drawTexturedModalRect(x, y, 7, 17, 162, 54);
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            final NonNullList<ItemStack> items = (NonNullList<ItemStack>)Minecraft.getMinecraft().player.inventory.mainInventory;
            for (int size = items.size(), item = 9; item < size; ++item) {
                final int slotX = x + 1 + item % 9 * 18;
                final int slotY = y + 1 + (item / 9 - 1) * 18;
                RenderHelper.enableGUIStandardItemLighting();
                HUD.mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)items.get(item), slotX, slotY);
                HUD.mc.getRenderItem().renderItemOverlays(HUD.mc.fontRenderer, (ItemStack)items.get(item), slotX, slotY);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }
    
    public int getPing() {
        int p = -1;
        if (HUD.mc.player == null || HUD.mc.getConnection() == null || HUD.mc.getConnection().getPlayerInfo(HUD.mc.player.getName()) == null) {
            p = -1;
        }
        else {
            p = HUD.mc.getConnection().getPlayerInfo(HUD.mc.player.getName()).getResponseTime();
        }
        return p;
    }
    
    private void drawStringWithShadow(final String text, final int x, final int y, final int color) {
        if (HUD.customFont.getValue()) {
            Main.fontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        else {
            HUD.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
        }
    }
    
    private int getWidth(final String s) {
        if (HUD.customFont.getValue()) {
            return Main.fontRenderer.getStringWidth(s);
        }
        return HUD.mc.fontRenderer.getStringWidth(s);
    }
    
    static {
        itemRender = Minecraft.getMinecraft().getRenderItem();
    }
}
