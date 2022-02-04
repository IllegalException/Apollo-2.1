//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.entity.player.*;
import com.apollo.api.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.apollo.api.players.friends.*;
import com.apollo.api.util.world.*;
import java.util.*;

public class AutoWeb extends Module
{
    Value.Boolean rotate;
    Value.Double range;
    Value.Integer bpt;
    Value.Boolean spoofRotations;
    Value.Boolean spoofHotbar;
    private final Vec3d[] offsetList;
    private boolean slowModeSwitch;
    private int playerHotbarSlot;
    private EntityPlayer closestTarget;
    private int lastHotbarSlot;
    private int offsetStep;
    
    public AutoWeb() {
        super("AutoWeb", Module.Category.Combat);
        this.offsetList = new Vec3d[] { new Vec3d(0.0, 1.0, 0.0), new Vec3d(0.0, 0.0, 0.0) };
        this.slowModeSwitch = false;
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.offsetStep = 0;
    }
    
    public void setup() {
        this.rotate = this.registerBoolean("Rotate", "Rotate", false);
        this.range = this.registerDouble("Range", "Range", 4.0, 0.0, 6.0);
        this.bpt = this.registerInteger("Blocks Per Tick", "BlocksPerTick", 8, 1, 15);
        this.spoofRotations = this.registerBoolean("Spoof Rotations", "SpoofRotations", false);
        this.spoofHotbar = this.registerBoolean("Silent Switch", "SilentSwitch", false);
    }
    
    public void onUpdate() {
        if (this.closestTarget == null) {
            return;
        }
        if (this.slowModeSwitch) {
            this.slowModeSwitch = false;
            return;
        }
        for (int i = 0; i < (int)Math.floor(this.bpt.getValue()); ++i) {
            if (this.offsetStep >= this.offsetList.length) {
                this.endLoop();
                return;
            }
            final Vec3d offset = this.offsetList[this.offsetStep];
            this.placeBlock(new BlockPos(this.closestTarget.getPositionVector()).down().add(offset.x, offset.y, offset.z));
            ++this.offsetStep;
        }
        this.slowModeSwitch = true;
    }
    
    private void placeBlock(final BlockPos blockPos) {
        if (!Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable()) {
            return;
        }
        if (!BlockUtils.checkForNeighbours(blockPos)) {
            return;
        }
        this.placeBlockExecute(blockPos);
    }
    
    public void placeBlockExecute(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
        final EnumFacing[] values = EnumFacing.values();
        final int length = values.length;
        final int n = 0;
        if (0 >= length) {
            return;
        }
        final EnumFacing side = values[0];
        final BlockPos neighbor = pos.offset(side);
        final EnumFacing side2 = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
        if (this.spoofRotations.getValue()) {
            BlockUtils.faceVectorPacketInstant(hitVec);
        }
        boolean needSneak = false;
        final Block blockBelow = AutoWeb.mc.world.getBlockState(neighbor).getBlock();
        if (BlockUtils.blackList.contains(blockBelow) || BlockUtils.shulkerList.contains(blockBelow)) {
            needSneak = true;
        }
        if (needSneak) {
            AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoWeb.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        final int obiSlot = this.findWebInHotBar();
        if (obiSlot == -1) {
            this.disable();
            return;
        }
        if (this.lastHotbarSlot != obiSlot) {
            if (this.spoofHotbar.getValue()) {
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(obiSlot));
            }
            else {
                Wrapper.getPlayer().inventory.currentItem = obiSlot;
            }
            this.lastHotbarSlot = obiSlot;
        }
        AutoWeb.mc.playerController.processRightClickBlock(Wrapper.getPlayer(), AutoWeb.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
        AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        if (needSneak) {
            AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoWeb.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    private int findWebInHotBar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockWeb) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private void findTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)Wrapper.getWorld().playerEntities;
        for (final EntityPlayer target : playerList) {
            if (target == AutoWeb.mc.player) {
                continue;
            }
            if (Friends.isFriend(target.getName())) {
                continue;
            }
            if (!EntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            final double currentDistance = Wrapper.getPlayer().getDistance((Entity)target);
            if (currentDistance > this.range.getValue()) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
            }
            else {
                if (currentDistance >= Wrapper.getPlayer().getDistance((Entity)this.closestTarget)) {
                    continue;
                }
                this.closestTarget = target;
            }
        }
    }
    
    private void endLoop() {
        this.offsetStep = 0;
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            this.lastHotbarSlot = this.playerHotbarSlot;
        }
        this.findTarget();
    }
    
    protected void onEnable() {
        if (AutoWeb.mc.player == null) {
            this.disable();
            return;
        }
        this.playerHotbarSlot = Wrapper.getPlayer().inventory.currentItem;
        this.lastHotbarSlot = -1;
        this.findTarget();
    }
    
    protected void onDisable() {
        if (AutoWeb.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (this.spoofHotbar.getValue()) {
                AutoWeb.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.playerHotbarSlot));
            }
            else {
                Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            }
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }
}
