//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import io.netty.util.internal.*;
import com.apollo.api.util.world.*;
import net.minecraft.init.*;
import java.util.*;
import com.apollo.api.event.events.*;
import com.apollo.api.util.render.*;
import com.apollo.api.util.color.*;
import net.minecraft.world.*;
import java.awt.*;
import net.minecraft.util.math.*;

public class VoidESP extends Module
{
    Value.Boolean rainbow;
    Value.Integer renderDistance;
    Value.Integer activeYValue;
    Value.Mode renderType;
    Value.Mode renderMode;
    private ConcurrentSet<BlockPos> voidHoles;
    
    public VoidESP() {
        super("VoidESP", Module.Category.Render);
    }
    
    public void setup() {
        final ArrayList<String> render = new ArrayList<String>();
        render.add("Outline");
        render.add("Fill");
        render.add("Both");
        final ArrayList<String> modes = new ArrayList<String>();
        modes.add("Box");
        modes.add("Flat");
        this.rainbow = this.registerBoolean("Rainbow", "Rainbow", false);
        this.renderDistance = this.registerInteger("Distance", "Distance", 10, 1, 40);
        this.activeYValue = this.registerInteger("Activate Y", "ActivateY", 20, 0, 256);
        this.renderType = this.registerMode("Render", "Render", (List)render, "Both");
        this.renderMode = this.registerMode("Mode", "Mode", (List)modes, "Flat");
    }
    
    public void onUpdate() {
        if (VoidESP.mc.player.dimension == 1) {
            return;
        }
        if (VoidESP.mc.player.getPosition().getY() > this.activeYValue.getValue()) {
            return;
        }
        if (this.voidHoles == null) {
            this.voidHoles = (ConcurrentSet<BlockPos>)new ConcurrentSet();
        }
        else {
            this.voidHoles.clear();
        }
        final List<BlockPos> blockPosList = (List<BlockPos>)BlockUtils.getCircle(getPlayerPos(), 0, (float)this.renderDistance.getValue(), false);
        for (final BlockPos blockPos : blockPosList) {
            if (VoidESP.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.BEDROCK)) {
                continue;
            }
            if (this.isAnyBedrock(blockPos, Offsets.center)) {
                continue;
            }
            this.voidHoles.add((Object)blockPos);
        }
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (VoidESP.mc.player == null || this.voidHoles == null) {
            return;
        }
        if (VoidESP.mc.player.getPosition().getY() > this.activeYValue.getValue()) {
            return;
        }
        if (this.voidHoles.isEmpty()) {
            return;
        }
        this.voidHoles.forEach(blockPos -> {
            ApolloTessellator.prepare(7);
            if (this.renderMode.getValue().equalsIgnoreCase("Box")) {
                this.drawBox(blockPos, 255, 255, 0);
            }
            else {
                this.drawFlat(blockPos, 255, 255, 0);
            }
            ApolloTessellator.release();
            ApolloTessellator.prepare(7);
            this.drawOutline(blockPos, 1, 255, 255, 0);
            ApolloTessellator.release();
        });
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(VoidESP.mc.player.posX), Math.floor(VoidESP.mc.player.posY), Math.floor(VoidESP.mc.player.posZ));
    }
    
    private boolean isAnyBedrock(final BlockPos origin, final BlockPos[] offset) {
        for (final BlockPos pos : offset) {
            if (VoidESP.mc.world.getBlockState(origin.add((Vec3i)pos)).getBlock().equals(Blocks.BEDROCK)) {
                return true;
            }
        }
        return false;
    }
    
    public void drawFlat(final BlockPos blockPos, final int r, final int g, final int b) {
        if (this.renderType.getValue().equalsIgnoreCase("Fill") || this.renderType.getValue().equalsIgnoreCase("Both")) {
            final Color c = Rainbow.getColor();
            final AxisAlignedBB bb = VoidESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)VoidESP.mc.world, blockPos);
            if (this.renderMode.getValue().equalsIgnoreCase("Flat")) {
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
    
    private void drawBox(final BlockPos blockPos, final int r, final int g, final int b) {
        if (this.renderType.getValue().equalsIgnoreCase("Fill") || this.renderType.getValue().equalsIgnoreCase("Both")) {
            final Color c = Rainbow.getColor();
            final AxisAlignedBB bb = VoidESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)VoidESP.mc.world, blockPos);
            Color color;
            if (this.rainbow.getValue()) {
                color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 50);
            }
            else {
                color = new Color(r, g, b, 50);
            }
            ApolloTessellator.drawBox(blockPos, color.getRGB(), 63);
        }
    }
    
    public void drawOutline(final BlockPos blockPos, final int width, final int r, final int g, final int b) {
        if (this.renderType.getValue().equalsIgnoreCase("Outline") || this.renderType.getValue().equalsIgnoreCase("Both")) {
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            final int r2 = rgb >> 16 & 0xFF;
            final int g2 = rgb >> 8 & 0xFF;
            final int b2 = rgb & 0xFF;
            final float[] array = hue;
            final int n = 0;
            array[n] += 0.02f;
            if (this.renderMode.getValue().equalsIgnoreCase("Box")) {
                if (this.rainbow.getValue()) {
                    ApolloTessellator.drawBoundingBoxBlockPos(blockPos, (float)width, r2, g2, b2, 255);
                }
                else {
                    ApolloTessellator.drawBoundingBoxBlockPos(blockPos, (float)width, r, g, b, 255);
                }
            }
            if (this.renderMode.getValue().equalsIgnoreCase("Flat")) {
                if (this.rainbow.getValue()) {
                    ApolloTessellator.drawBoundingBoxBottom2(blockPos, (float)width, r2, g2, b2, 255);
                }
                else {
                    ApolloTessellator.drawBoundingBoxBottom2(blockPos, (float)width, r, g, b, 255);
                }
            }
        }
    }
    
    private static class Offsets
    {
        static final BlockPos[] center;
        
        static {
            center = new BlockPos[] { new BlockPos(0, 0, 0), new BlockPos(0, 1, 0), new BlockPos(0, 2, 0) };
        }
    }
}
