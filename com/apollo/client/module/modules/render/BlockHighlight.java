//Decomped by XeonLyfe

package com.apollo.client.module.modules.render;

import com.apollo.api.values.*;
import com.apollo.api.event.events.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.client.module.*;
import com.apollo.api.util.color.*;
import java.awt.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import com.apollo.api.util.render.*;
import net.minecraft.util.math.*;

public class BlockHighlight extends Module
{
    Value.Integer w;
    Value.Boolean shade;
    int c;
    int c2;
    
    public BlockHighlight() {
        super("BlockHighlight", Module.Category.Render);
    }
    
    public void setup() {
        this.shade = this.registerBoolean("Fill", "Fill", false);
        this.w = this.registerInteger("Width", "Width", 2, 1, 10);
    }
    
    public void onWorldRender(final RenderEvent event) {
        final RayTraceResult ray = BlockHighlight.mc.objectMouseOver;
        final ColorMain colorMain = (ColorMain)ModuleManager.getModuleByName("Colors");
        if (ColorMain.rainbow.getValue()) {
            this.c = Rainbow.getColorWithOpacity(255).getRGB();
            this.c2 = Rainbow.getColorWithOpacity(50).getRGB();
        }
        else {
            this.c = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), 255).getRGB();
            this.c2 = new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), 50).getRGB();
        }
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos pos = ray.getBlockPos();
            final AxisAlignedBB bb = BlockHighlight.mc.world.getBlockState(pos).getSelectedBoundingBox((World)BlockHighlight.mc.world, pos);
            if (bb != null && pos != null && BlockHighlight.mc.world.getBlockState(pos).getMaterial() != Material.AIR) {
                ApolloTessellator.prepareGL();
                ApolloTessellator.drawBoundingBox(bb, (float)this.w.getValue(), this.c);
                ApolloTessellator.releaseGL();
                if (this.shade.getValue()) {
                    ApolloTessellator.prepare(7);
                    ApolloTessellator.drawBox(bb, this.c2, 63);
                    ApolloTessellator.release();
                }
            }
        }
    }
}
