//Decomped by XeonLyfe

package com.apollo.client.command.commands;

import com.apollo.client.command.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;

public class VanishCommand extends Command
{
    private static Entity vehicle;
    Minecraft mc;
    
    public VanishCommand() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public String[] getAlias() {
        return new String[] { "vanish", "v" };
    }
    
    public String getSyntax() {
        return "vanish to do the epic gamer dupe";
    }
    
    public void onCommand(final String command, final String[] args) throws Exception {
        if (this.mc.player.getRidingEntity() != null && VanishCommand.vehicle == null) {
            VanishCommand.vehicle = this.mc.player.getRidingEntity();
            this.mc.player.dismountRidingEntity();
            this.mc.world.removeEntityFromWorld(VanishCommand.vehicle.getEntityId());
            Command.sendClientMessage("Vehicle " + VanishCommand.vehicle.getName() + " removed.");
        }
        else if (VanishCommand.vehicle != null) {
            VanishCommand.vehicle.isDead = false;
            this.mc.world.addEntityToWorld(VanishCommand.vehicle.getEntityId(), VanishCommand.vehicle);
            this.mc.player.startRiding(VanishCommand.vehicle, true);
            Command.sendClientMessage("Vehicle " + VanishCommand.vehicle.getName() + " created.");
            VanishCommand.vehicle = null;
        }
        else {
            Command.sendClientMessage("No Vehicle.");
        }
    }
}
