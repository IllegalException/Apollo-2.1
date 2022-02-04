//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import java.util.concurrent.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import com.apollo.api.event.events.*;
import com.apollo.api.util.render.*;
import com.apollo.api.util.color.*;
import net.minecraft.world.*;
import java.awt.*;
import net.minecraft.util.math.*;

public class HoleESP extends Module
{
    public static Value.Integer rangeS;
    Value.Boolean rainbow;
    Value.Boolean hideOwn;
    Value.Boolean flatOwn;
    Value.Mode mode;
    Value.Mode type;
    private final BlockPos[] surroundOffset;
    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;
    
    public HoleESP() {
        super("HoleESP", Module.Category.Render);
        this.surroundOffset = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) };
    }
    
    public void setup() {
        HoleESP.rangeS = this.registerInteger("Range", "Range", 5, 1, 20);
        this.rainbow = this.registerBoolean("Rainbow", "Rainbow", false);
        this.hideOwn = this.registerBoolean("Hide Own", "HideOwn", false);
        this.flatOwn = this.registerBoolean("Flat Own", "FlatOwn", false);
        final ArrayList<String> render = new ArrayList<String>();
        render.add("Outline");
        render.add("Fill");
        render.add("Both");
        final ArrayList<String> modes = new ArrayList<String>();
        modes.add("Air");
        modes.add("Ground");
        modes.add("Flat");
        this.type = this.registerMode("Render", "Render", (List)render, "Both");
        this.mode = this.registerMode("Mode", "Mode", (List)modes, "Air");
    }
    
    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleESP.mc.player.posX), Math.floor(HoleESP.mc.player.posY), Math.floor(HoleESP.mc.player.posZ));
    }
    
    public void onUpdate() {
        if (this.safeHoles == null) {
            this.safeHoles = new ConcurrentHashMap<BlockPos, Boolean>();
        }
        else {
            this.safeHoles.clear();
        }
        final int range = (int)Math.ceil(HoleESP.rangeS.getValue());
        final List<BlockPos> blockPosList = this.getSphere(getPlayerPos(), (float)range, range, false, true, 0);
        for (final BlockPos pos : blockPosList) {
            if (!HoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (this.hideOwn.getValue() && pos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ))) {
                continue;
            }
            boolean isSafe = true;
            boolean isBedrock = true;
            for (final BlockPos offset : this.surroundOffset) {
                final Block block = HoleESP.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                if (block != Blocks.BEDROCK) {
                    isBedrock = false;
                }
                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    isSafe = false;
                    break;
                }
            }
            if (!isSafe) {
                continue;
            }
            this.safeHoles.put(pos, isBedrock);
        }
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (HoleESP.mc.player == null || this.safeHoles == null) {
            return;
        }
        if (this.safeHoles.isEmpty()) {
            return;
        }
        ApolloTessellator.prepare(7);
        if (this.mode.getValue().equalsIgnoreCase("Air")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawBox(blockPos, 0, 255, 0);
                }
                else {
                    this.drawBox(blockPos, 255, 0, 0);
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Ground")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawBox2(blockPos, 0, 255, 0);
                }
                else {
                    this.drawBox2(blockPos, 255, 0, 0);
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Flat")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawFlat(blockPos, 0, 255, 0);
                }
                else {
                    this.drawFlat(blockPos, 255, 0, 0);
                }
                return;
            });
        }
        ApolloTessellator.release();
        ApolloTessellator.prepare(7);
        if (this.mode.getValue().equalsIgnoreCase("Air")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawOutline(blockPos, 1, 0, 255, 0);
                }
                else {
                    this.drawOutline(blockPos, 1, 255, 0, 0);
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Ground")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawOutline(blockPos, 1, 0, 255, 0);
                }
                else {
                    this.drawOutline(blockPos, 1, 255, 0, 0);
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Flat")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawOutline(blockPos, 1, 0, 255, 0);
                }
                else {
                    this.drawOutline(blockPos, 1, 255, 0, 0);
                }
                return;
            });
        }
        ApolloTessellator.release();
    }
    
    private void drawBox(final BlockPos blockPos, final int r, final int g, final int b) {
        if (this.type.getValue().equalsIgnoreCase("Fill") || this.type.getValue().equalsIgnoreCase("Both")) {
            final Color c = Rainbow.getColor();
            final AxisAlignedBB bb = HoleESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)HoleESP.mc.world, blockPos);
            Color color;
            if (this.rainbow.getValue()) {
                color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 50);
            }
            else {
                color = new Color(r, g, b, 50);
            }
            if (this.mode.getValue().equalsIgnoreCase("Air")) {
                if (this.flatOwn.getValue() && blockPos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ))) {
                    ApolloTessellator.drawBox(blockPos, color.getRGB(), 1);
                }
                else {
                    ApolloTessellator.drawBox(blockPos, color.getRGB(), 63);
                }
            }
        }
    }
    
    public void drawBox2(final BlockPos blockPos, final int r, final int g, final int b) {
        if (this.type.getValue().equalsIgnoreCase("Fill") || this.type.getValue().equalsIgnoreCase("Both")) {
            final Color c = Rainbow.getColor();
            final AxisAlignedBB bb = HoleESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)HoleESP.mc.world, blockPos);
            Color color;
            if (this.rainbow.getValue()) {
                color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 50);
            }
            else {
                color = new Color(r, g, b, 50);
            }
            if (this.mode.getValue().equalsIgnoreCase("Ground")) {
                ApolloTessellator.drawBox2(blockPos, color.getRGB(), 63);
            }
        }
    }
    
    public void drawFlat(final BlockPos blockPos, final int r, final int g, final int b) {
        if (this.type.getValue().equalsIgnoreCase("Fill") || this.type.getValue().equalsIgnoreCase("Both")) {
            final Color c = Rainbow.getColor();
            final AxisAlignedBB bb = HoleESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)HoleESP.mc.world, blockPos);
            if (this.mode.getValue().equalsIgnoreCase("Flat")) {
                Color color;
                if (this.rainbow.getValue()) {
                    color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 50);
                }
                else {
                    color = new Color(r, g, b, 50);
                }
                ApolloTessellator.drawBox(blockPos, color.getRGB(), 1);
            }
        }
    }
    
    public void drawOutline(final BlockPos blockPos, final int width, final int r, final int g, final int b) {
        if (this.type.getValue().equalsIgnoreCase("Outline") || this.type.getValue().equalsIgnoreCase("Both")) {
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            final int r2 = rgb >> 16 & 0xFF;
            final int g2 = rgb >> 8 & 0xFF;
            final int b2 = rgb & 0xFF;
            final float[] array = hue;
            final int n = 0;
            array[n] += 0.02f;
            if (this.mode.getValue().equalsIgnoreCase("Air")) {
                if (this.flatOwn.getValue() && blockPos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ))) {
                    if (this.rainbow.getValue()) {
                        ApolloTessellator.drawBoundingBoxBottom2(blockPos, (float)width, r2, g2, b2, 255);
                    }
                    else {
                        ApolloTessellator.drawBoundingBoxBottom2(blockPos, (float)width, r, g, b, 255);
                    }
                }
                else if (this.rainbow.getValue()) {
                    ApolloTessellator.drawBoundingBoxBlockPos(blockPos, (float)width, r2, g2, b2, 255);
                }
                else {
                    ApolloTessellator.drawBoundingBoxBlockPos(blockPos, (float)width, r, g, b, 255);
                }
            }
            if (this.mode.getValue().equalsIgnoreCase("Flat")) {
                if (this.rainbow.getValue()) {
                    ApolloTessellator.drawBoundingBoxBottom2(blockPos, (float)width, r2, g2, b2, 255);
                }
                else {
                    ApolloTessellator.drawBoundingBoxBottom2(blockPos, (float)width, r, g, b, 255);
                }
            }
            if (this.mode.getValue().equalsIgnoreCase("Ground")) {
                if (this.rainbow.getValue()) {
                    ApolloTessellator.drawBoundingBoxBlockPos2(blockPos, (float)width, r2, g2, b2, 255);
                }
                else {
                    ApolloTessellator.drawBoundingBoxBlockPos2(blockPos, (float)width, r, g, b, 255);
                }
            }
        }
    }
}
