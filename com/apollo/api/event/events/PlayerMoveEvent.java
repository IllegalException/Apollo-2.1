//Decomped by XeonLyfe

package com.apollo.api.event.events;

import com.apollo.api.event.*;
import net.minecraft.entity.*;

public class PlayerMoveEvent extends MainEvent
{
    MoverType type;
    public double x;
    public double y;
    public double z;
    
    public PlayerMoveEvent(final MoverType moverType, final double xx, final double yy, final double zz) {
        this.type = moverType;
        this.x = xx;
        this.y = yy;
        this.z = zz;
    }
    
    public MoverType getType() {
        return this.type;
    }
    
    public void setType(final MoverType type) {
        this.type = type;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setX(final double xx) {
        this.x = xx;
    }
    
    public void setY(final double yy) {
        this.y = yy;
    }
    
    public void setZ(final double zz) {
        this.z = zz;
    }
}
