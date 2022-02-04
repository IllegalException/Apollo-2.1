//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import com.apollo.api.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.apollo.api.util.*;
import net.minecraft.item.*;
import com.apollo.api.util.font.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.apollo.api.util.color.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.api.players.friends.*;
import com.apollo.api.players.enemy.*;
import net.minecraft.util.text.*;
import java.awt.*;

public class Nametags extends Module
{
    Value.Boolean durability;
    Value.Boolean armor;
    Value.Boolean enchantnames;
    Value.Boolean itemName;
    Value.Boolean gamemode;
    Value.Boolean health;
    Value.Boolean ping;
    Value.Boolean entityId;
    Value.Mode borderColor;
    
    public Nametags() {
        super("Nametags", Module.Category.Render);
    }
    
    public void setup() {
        this.durability = this.registerBoolean("Durability", "Durability", true);
        this.armor = this.registerBoolean("Armor", "Armor", true);
        this.enchantnames = this.registerBoolean("Enchants", "Enchants", true);
        this.itemName = this.registerBoolean("Item Name", "ItemName", false);
        this.gamemode = this.registerBoolean("Gamemode", "Gamemode", false);
        this.health = this.registerBoolean("Health", "Health", true);
        this.ping = this.registerBoolean("Ping", "Ping", false);
        this.entityId = this.registerBoolean("Entity Id", "EntityId", false);
        final ArrayList<String> borderColorModes = new ArrayList<String>();
        borderColorModes.add("Normal");
        borderColorModes.add("Rainbow");
        borderColorModes.add("Custom");
        this.borderColor = this.registerMode("Border Color", "BorderColor", (List)borderColorModes, "Custom");
    }
    
    public void onWorldRender(final RenderEvent event) {
        for (final Object o : Nametags.mc.world.playerEntities) {
            final Entity entity = (Entity)o;
            if (entity instanceof EntityPlayer && entity != Nametags.mc.player && entity.isEntityAlive()) {
                final double x = this.interpolate(entity.lastTickPosX, entity.posX, event.getPartialTicks()) - Nametags.mc.getRenderManager().renderPosX;
                final double y = this.interpolate(entity.lastTickPosY, entity.posY, event.getPartialTicks()) - Nametags.mc.getRenderManager().renderPosY;
                final double z = this.interpolate(entity.lastTickPosZ, entity.posZ, event.getPartialTicks()) - Nametags.mc.getRenderManager().renderPosZ;
                final Vec3d m = renderPosEntity(entity);
                this.renderNameTagsFor((EntityPlayer)entity, m.x, m.y, m.z);
            }
        }
    }
    
    public void renderNameTagsFor(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        this.renderNametags(entityPlayer, n, n2, n3);
    }
    
    public static double timerPos(final double n, final double n2) {
        return n2 + (n - n2) * Wrapper.getMinecraft().timer.renderPartialTicks;
    }
    
    public static Vec3d renderPosEntity(final Entity entity) {
        return new Vec3d(timerPos(entity.posX, entity.lastTickPosX) - Nametags.mc.getRenderManager().renderPosX, timerPos(entity.posY, entity.lastTickPosY) - Nametags.mc.getRenderManager().renderPosY, timerPos(entity.posZ, entity.lastTickPosZ) - Nametags.mc.getRenderManager().renderPosZ);
    }
    
    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }
    
    private void renderItemName(final ItemStack itemStack, final int x, final int y) {
        final float n3 = 0.5f;
        final float n4 = 0.5f;
        GlStateManager.scale(n4, n3, n4);
        GlStateManager.disableDepth();
        final String s2;
        final String displayName = s2 = itemStack.getDisplayName();
        FontUtils.drawStringWithShadow(HUD.customFont.getValue(), s2, -FontUtils.getStringWidth(HUD.customFont.getValue(), s2) / 2, y, -1);
        GlStateManager.enableDepth();
        final float n5 = 2.0f;
        final int n6 = 2;
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    private void renderEnchants(final ItemStack itemStack, final int x, int y) {
        y = y;
        final int n3 = -1;
        Iterator<Enchantment> iterator3;
        for (Iterator<Enchantment> iterator2 = iterator3 = EnchantmentHelper.getEnchantments(itemStack).keySet().iterator(); iterator3.hasNext(); iterator3 = iterator2) {
            final Enchantment enchantment;
            if ((enchantment = iterator2.next()) != null) {
                final Enchantment enchantment2 = enchantment;
                if (!this.enchantnames.getValue()) {
                    return;
                }
                FontUtils.drawStringWithShadow(HUD.customFont.getValue(), this.stringForEnchants(enchantment2, EnchantmentHelper.getEnchantmentLevel(enchantment2, itemStack)), x * 2, y, -1);
                y += 8;
            }
        }
        if (itemStack.getItem().equals(Items.GOLDEN_APPLE) && itemStack.hasEffect()) {
            FontUtils.drawStringWithShadow(HUD.customFont.getValue(), "God", x * 2, y, -3977919);
        }
    }
    
    private String stringForEnchants(final Enchantment enchantment, final int n) {
        final ResourceLocation resourceLocation;
        String substring = ((resourceLocation = (ResourceLocation)Enchantment.REGISTRY.getNameForObject((Object)enchantment)) == null) ? enchantment.getName() : resourceLocation.toString();
        final int n2 = (n > 1) ? 12 : 13;
        if (substring.length() > n2) {
            substring = substring.substring(10, n2);
        }
        final StringBuilder sb = new StringBuilder();
        final String s = substring;
        final int n3 = 0;
        String s2 = sb.insert(0, s.substring(0, 1).toUpperCase()).append(substring.substring(1)).toString();
        if (n > 1) {
            s2 = new StringBuilder().insert(0, s2).append(n).toString();
        }
        return s2;
    }
    
    public static int toHex(final int r, final int g, final int b) {
        return 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }
    
    private void renderItemDurability(final ItemStack itemStack, final int x, final int y) {
        final int maxDamage = itemStack.getMaxDamage();
        final float n3 = (maxDamage - itemStack.getItemDamage()) / (float)maxDamage;
        final float green = (itemStack.getMaxDamage() - (float)itemStack.getItemDamage()) / itemStack.getMaxDamage();
        final float red = 1.0f - green;
        final int dmg = 100 - (int)(red * 100.0f);
        final int Color = toHex((int)(red * 255.0f), (int)(green * 255.0f), 0);
        final float n4 = 0.5f;
        final float n5 = 0.5f;
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        FontUtils.drawStringWithShadow(HUD.customFont.getValue(), new StringBuilder().insert(0, (int)(n3 * 100.0f)).append('%').toString(), x * 2, y, Color);
        GlStateManager.enableDepth();
        final float n6 = 2.0f;
        final int n7 = 2;
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    private void renderItems(final ItemStack itemStack, final int n, final int n2, final int n3) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        Nametags.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        final int n4 = (n3 > 4) ? ((n3 - 4) * 8 / 2) : 0;
        Nametags.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2 + n4);
        Nametags.mc.getRenderItem().renderItemOverlays(Nametags.mc.fontRenderer, itemStack, n, n2 + n4);
        Nametags.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        final float n5 = 0.5f;
        final float n6 = 0.5f;
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        this.renderEnchants(itemStack, n, n2 - 24);
        GlStateManager.enableDepth();
        final float n7 = 2.0f;
        final int n8 = 2;
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    public static Vec3d M2(final Entity entity, final Vec3d vec3d) {
        return location4(entity, vec3d.x, vec3d.y, vec3d.z);
    }
    
    public static Vec3d location1(final Entity entity, final Vec3d vec3d) {
        return location4(entity, vec3d.x, vec3d.y, vec3d.z);
    }
    
    public static Vec3d location3(final Entity entity, final double n) {
        return location4(entity, n, n, n);
    }
    
    public static Vec3d location4(final Entity entity, final double n, final double n2, final double n3) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * n, (entity.posY - entity.lastTickPosY) * n2, (entity.posZ - entity.lastTickPosZ) * n3);
    }
    
    public static Vec3d location5(final Entity entity, final float n) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(location3(entity, n));
    }
    
    public static void M(final float n) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        Nametags.mc.entityRenderer.enableLightmap();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(n);
    }
    
    private void renderNametags(final EntityPlayer entityPlayer, final double n, double distance, final double n2) {
        double tempY = distance;
        tempY += (entityPlayer.isSneaking() ? 0.5 : 0.7);
        final Entity entity3;
        final Entity entity2 = entity3 = (Entity)((Nametags.mc.getRenderViewEntity() == null) ? Nametags.mc.player : Nametags.mc.getRenderViewEntity());
        final double posX = entity2.posX;
        final double posY = entity2.posY;
        final double posZ = entity2.posZ;
        final Vec3d m;
        entity2.posX = (m = renderPosEntity(entity2)).x;
        entity2.posY = m.y;
        entity2.posZ = m.z;
        distance = entity3.getDistance(n, distance, n2);
        final int n3 = FontUtils.getStringWidth(HUD.customFont.getValue(), this.renderEntityName(entityPlayer)) / 2;
        final int n4 = FontUtils.getStringWidth(HUD.customFont.getValue(), this.renderEntityName(entityPlayer)) / 2;
        double n5 = 0.0018 + 0.003000000026077032 * distance;
        if (distance <= 8.0) {
            n5 = 0.0245;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)n, (float)tempY + 1.4f, (float)n2);
        final float n6 = -Nametags.mc.getRenderManager().playerViewY;
        final float n7 = 1.0f;
        final float n8 = 0.0f;
        GlStateManager.rotate(n6, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(Nametags.mc.getRenderManager().playerViewX, (Nametags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-n5, -n5, n5);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        if (this.borderColor.getValue().equalsIgnoreCase("Normal")) {
            drawBorderedRectReliant((float)(-n3 - 1), (float)(-Nametags.mc.fontRenderer.FONT_HEIGHT), (float)(n3 + 2), 1.0f, 1.8f, 1426064384, 855638016);
        }
        else if (this.borderColor.getValue().equalsIgnoreCase("Rainbow")) {
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            final int red = rgb >> 16 & 0xFF;
            final int green = rgb >> 8 & 0xFF;
            final int blue = rgb & 0xFF;
            final float[] array = hue;
            final int n21 = 0;
            array[n21] += 0.02f;
            final int color2 = ColourHolder.toHex(red, green, blue);
            drawBorderedRectReliant((float)(-n3 - 1), (float)(-Nametags.mc.fontRenderer.FONT_HEIGHT), (float)(n3 + 2), 1.0f, 1.8f, 1426064384, color2);
        }
        else if (this.borderColor.getValue().equalsIgnoreCase("Custom")) {
            final int color3 = ColourHolder.toHex(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue());
            drawBorderedRectReliant((float)(-n3 - 1), (float)(-Nametags.mc.fontRenderer.FONT_HEIGHT), (float)(n3 + 2), 1.0f, 1.8f, 1426064384, color3);
        }
        GlStateManager.disableBlend();
        FontUtils.drawStringWithShadow(HUD.customFont.getValue(), this.renderEntityName(entityPlayer), -n3, -(Nametags.mc.fontRenderer.FONT_HEIGHT - 1), this.renderPing(entityPlayer));
        final EntityPlayer entityPlayer2 = entityPlayer;
        final ItemStack heldItemMainhand = entityPlayer2.getHeldItemMainhand();
        final ItemStack heldItemOffhand = entityPlayer.getHeldItemOffhand();
        int n9 = 0;
        int n10 = 0;
        boolean b = false;
        GlStateManager.pushMatrix();
        for (int i = 3, n11 = 3; i >= 0; i = --n11) {
            final ItemStack itemStack;
            if (!(itemStack = (ItemStack)entityPlayer.inventory.armorInventory.get(n11)).isEmpty()) {
                final Boolean j = this.durability.getValue();
                n9 -= 8;
                if (j) {
                    b = true;
                }
                final int size;
                if (this.armor.getValue() && (size = EnchantmentHelper.getEnchantments(itemStack).size()) > n10) {
                    n10 = size;
                }
            }
        }
        if (!heldItemOffhand.isEmpty() && (this.armor.getValue() || (this.durability.getValue() && heldItemOffhand.isItemStackDamageable()))) {
            n9 -= 8;
            if (this.durability.getValue() && heldItemOffhand.isItemStackDamageable()) {
                b = true;
            }
            final int size2;
            if (this.armor.getValue() && (size2 = EnchantmentHelper.getEnchantments(heldItemOffhand).size()) > n10) {
                n10 = size2;
            }
        }
        if (!heldItemMainhand.isEmpty()) {
            final int size3;
            if (this.armor.getValue() && (size3 = EnchantmentHelper.getEnchantments(heldItemMainhand).size()) > n10) {
                n10 = size3;
            }
            int k = this.armorValue(n10);
            if (this.armor.getValue() || (this.durability.getValue() && heldItemMainhand.isItemStackDamageable())) {
                n9 -= 8;
            }
            if (this.armor.getValue()) {
                final ItemStack itemStack2 = heldItemMainhand;
                final int n12 = n9;
                final int n13 = k;
                k -= 32;
                this.renderItems(itemStack2, n12, n13, n10);
            }
            Nametags nametags;
            if (this.durability.getValue() && heldItemMainhand.isItemStackDamageable()) {
                final int n14 = k;
                this.renderItemDurability(heldItemMainhand, n9, k);
                k = n14 - (HUD.customFont.getValue() ? FontUtils.getFontHeight(HUD.customFont.getValue()) : Nametags.mc.fontRenderer.FONT_HEIGHT);
                nametags = this;
            }
            else {
                if (b) {
                    k -= (HUD.customFont.getValue() ? FontUtils.getFontHeight(HUD.customFont.getValue()) : Nametags.mc.fontRenderer.FONT_HEIGHT);
                }
                nametags = this;
            }
            if (nametags.itemName.getValue()) {
                this.renderItemName(heldItemMainhand, n9, k);
            }
            if (this.armor.getValue() || (this.durability.getValue() && heldItemMainhand.isItemStackDamageable())) {
                n9 += 16;
            }
        }
        for (int l = 3, n15 = 3; l >= 0; l = --n15) {
            final ItemStack itemStack3;
            if (!(itemStack3 = (ItemStack)entityPlayer.inventory.armorInventory.get(n15)).isEmpty()) {
                int m2 = this.armorValue(n10);
                if (this.armor.getValue()) {
                    final ItemStack itemStack4 = itemStack3;
                    final int n16 = n9;
                    final int n17 = m2;
                    m2 -= 32;
                    this.renderItems(itemStack4, n16, n17, n10);
                }
                if (this.durability.getValue() && itemStack3.isItemStackDamageable()) {
                    this.renderItemDurability(itemStack3, n9, m2);
                }
                n9 += 16;
            }
        }
        if (!heldItemOffhand.isEmpty()) {
            int m3 = this.armorValue(n10);
            if (this.armor.getValue()) {
                final ItemStack itemStack5 = heldItemOffhand;
                final int n18 = n9;
                final int n19 = m3;
                m3 -= 32;
                this.renderItems(itemStack5, n18, n19, n10);
            }
            if (this.durability.getValue() && heldItemOffhand.isItemStackDamageable()) {
                this.renderItemDurability(heldItemOffhand, n9, m3);
            }
            n9 += 16;
        }
        GlStateManager.popMatrix();
        final float n20 = 1.0f;
        final double posZ2 = posZ;
        final Entity entity4 = entity3;
        final double posY2 = posY;
        entity3.posX = posX;
        entity4.posY = posY2;
        entity4.posZ = posZ2;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    private int renderPing(final EntityPlayer entityPlayer) {
        int n = -1;
        if (Friends.isFriend(entityPlayer.getName())) {
            return ColorMain.getFriendColorInt();
        }
        if (Enemies.isEnemy(entityPlayer.getName())) {
            return ColorMain.getEnemyColorInt();
        }
        if (entityPlayer.isInvisible()) {
            return Color.GRAY.getRGB();
        }
        if (Nametags.mc.getConnection() != null && Nametags.mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) == null) {
            return -1113785;
        }
        if (entityPlayer.isSneaking()) {
            n = 16750848;
        }
        return n;
    }
    
    private String renderEntityName(final EntityPlayer entityPlayer) {
        String s = entityPlayer.getDisplayName().getFormattedText();
        if (this.entityId.getValue()) {
            s = new StringBuilder().insert(0, s).append(" ID: ").append(entityPlayer.getEntityId()).toString();
        }
        Nametags nametags = null;
        Label_0153: {
            if (this.gamemode.getValue()) {
                if (entityPlayer.isCreative()) {
                    s = new StringBuilder().insert(0, s).append(" [C]").toString();
                    nametags = this;
                    break Label_0153;
                }
                if (entityPlayer.isSpectator()) {
                    s = new StringBuilder().insert(0, s).append(" [I]").toString();
                    nametags = this;
                    break Label_0153;
                }
                s = new StringBuilder().insert(0, s).append(" [S]").toString();
            }
            nametags = this;
        }
        if (this.ping.getValue() && Nametags.mc.getConnection() != null && Nametags.mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) != null) {
            s = new StringBuilder().insert(0, s).append(" ").append(Nametags.mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()).getResponseTime()).append("ms").toString();
        }
        if (!this.health.getValue()) {
            return s;
        }
        String s2 = TextFormatting.GREEN.toString();
        final double ceil;
        if ((ceil = Math.ceil(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount())) > 0.0) {
            if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 5.0f) {
                s2 = TextFormatting.RED.toString();
            }
            else if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 5.0f && entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 10.0f) {
                s2 = TextFormatting.GOLD.toString();
            }
            else if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 10.0f && entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 15.0f) {
                s2 = TextFormatting.YELLOW.toString();
            }
            else if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 15.0f && entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() <= 20.0f) {
                s2 = TextFormatting.DARK_GREEN.toString();
            }
            else if (entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount() > 20.0f) {
                s2 = TextFormatting.GREEN.toString();
            }
        }
        else {
            s2 = TextFormatting.DARK_RED.toString();
        }
        return new StringBuilder().insert(0, s).append(s2).append(" ").append((ceil > 0.0) ? Integer.valueOf((int)ceil) : "0").toString();
    }
    
    public static void drawBorderedRectReliant(final float x, final float y, final float x1, final float y1, final float lineWidth, final int inside, final int border) {
        enableGL2D();
        drawRect(x, y, x1, y1, inside);
        glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }
    
    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void drawRect(final Rectangle rectangle, final int color) {
        drawRect((float)rectangle.x, (float)rectangle.y, (float)(rectangle.x + rectangle.width), (float)(rectangle.y + rectangle.height), color);
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1, final int color) {
        enableGL2D();
        glColor(color);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1, final float r, final float g, final float b, final float a) {
        enableGL2D();
        GL11.glColor4f(r, g, b, a);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }
    
    public static void drawRect(final float x, final float y, final float x1, final float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
    }
    
    public static void glColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    public static void glColor(final float alpha, final int redRGB, final int greenRGB, final int blueRGB) {
        final float red = 0.003921569f * redRGB;
        final float green = 0.003921569f * greenRGB;
        final float blue = 0.003921569f * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }
    
    private int armorValue(final int n) {
        int n2 = this.armor.getValue() ? -26 : -27;
        if (n > 4) {
            n2 -= (n - 4) * 8;
        }
        return n2;
    }
}
