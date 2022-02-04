//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import java.util.concurrent.*;
import com.apollo.api.event.events.*;
import java.awt.*;
import com.apollo.api.util.render.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class StorageESP extends Module
{
    Value.Integer w;
    ConcurrentHashMap<TileEntity, String> chests;
    
    public StorageESP() {
        super("StorageESP", Module.Category.Render);
        this.chests = new ConcurrentHashMap<TileEntity, String>();
    }
    
    public void setup() {
        this.w = this.registerInteger("Width", "Width", 2, 1, 10);
    }
    
    public void onUpdate() {
        StorageESP.mc.world.loadedTileEntityList.forEach(e -> this.chests.put(e, ""));
    }
    
    public void onWorldRender(final RenderEvent event) {
        final Color c2 = new Color(255, 255, 0, 255);
        final Color c3 = new Color(180, 70, 200, 255);
        final Color c4 = new Color(150, 150, 150, 255);
        final Color c5 = new Color(255, 0, 0, 255);
        if (this.chests != null && this.chests.size() > 0) {
            ApolloTessellator.prepareGL();
            final Color color;
            final Color color2;
            final Color color3;
            final Color color4;
            this.chests.forEach((c, t) -> {
                if (StorageESP.mc.world.loadedTileEntityList.contains(c)) {
                    if (c instanceof TileEntityChest) {
                        ApolloTessellator.drawBoundingBox(StorageESP.mc.world.getBlockState(c.getPos()).getSelectedBoundingBox((World)StorageESP.mc.world, c.getPos()), (float)this.w.getValue(), color.getRGB());
                    }
                    if (c instanceof TileEntityEnderChest) {
                        ApolloTessellator.drawBoundingBox(StorageESP.mc.world.getBlockState(c.getPos()).getSelectedBoundingBox((World)StorageESP.mc.world, c.getPos()), (float)this.w.getValue(), color2.getRGB());
                    }
                    if (c instanceof TileEntityShulkerBox) {
                        ApolloTessellator.drawBoundingBox(StorageESP.mc.world.getBlockState(c.getPos()).getSelectedBoundingBox((World)StorageESP.mc.world, c.getPos()), (float)this.w.getValue(), color3.getRGB());
                    }
                    if (c instanceof TileEntityDispenser || c instanceof TileEntityFurnace || c instanceof TileEntityHopper) {
                        ApolloTessellator.drawBoundingBox(StorageESP.mc.world.getBlockState(c.getPos()).getSelectedBoundingBox((World)StorageESP.mc.world, c.getPos()), (float)this.w.getValue(), color4.getRGB());
                    }
                }
                return;
            });
            ApolloTessellator.releaseGL();
        }
    }
}
