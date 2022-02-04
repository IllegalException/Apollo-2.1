//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import com.apollo.api.util.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class AutoFeetPlace extends Module
{
    Value.Boolean triggerable;
    Value.Integer timeoutTicks;
    Value.Boolean disableNone;
    Value.Integer tickDelay;
    Value.Boolean rotate;
    Value.Integer blocksPerTick;
    Value.Boolean disableOnJump;
    private int offsetStep;
    private int delayStep;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    private boolean isSneaking;
    private int totalTicksRunning;
    private boolean firstRun;
    private boolean missingObiDisable;
    
    public AutoFeetPlace() {
        super("AutoFeetPlace", Module.Category.Combat);
        this.offsetStep = 0;
        this.delayStep = 0;
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.isSneaking = false;
        this.totalTicksRunning = 0;
        this.missingObiDisable = false;
    }
    
    public void setup() {
        this.triggerable = this.registerBoolean("Triggerable", "Troggerable", false);
        this.disableNone = this.registerBoolean("Disable No Obby", "DisableNoObby", false);
        this.disableOnJump = this.registerBoolean("Disable on Jump", "DisableOnJump", false);
        this.rotate = this.registerBoolean("Rotate", "Rotate", false);
        this.tickDelay = this.registerInteger("Tick Delay", "TickDelay", 5, 0, 10);
        this.timeoutTicks = this.registerInteger("Timeout Ticks", "TimeoutTicks", 40, 1, 100);
        this.blocksPerTick = this.registerInteger("Blocks Per Tick", "BlocksPerTick", 4, 0, 8);
    }
    
    private static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (AutoFeetPlace.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(AutoFeetPlace.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = AutoFeetPlace.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }
    
    protected void onEnable() {
        if (AutoFeetPlace.mc.player == null) {
            this.disable();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = AutoFeetPlace.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }
    
    protected void onDisable() {
        if (AutoFeetPlace.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            AutoFeetPlace.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            AutoFeetPlace.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoFeetPlace.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.missingObiDisable = false;
    }
    
    public void onUpdate() {
        if (!AutoFeetPlace.mc.player.onGround && this.disableOnJump.getValue()) {
            return;
        }
        if (AutoFeetPlace.mc.player == null) {
            return;
        }
        if (this.triggerable.getValue() && this.totalTicksRunning >= this.timeoutTicks.getValue()) {
            this.totalTicksRunning = 0;
            this.disable();
            return;
        }
        if (!this.firstRun) {
            if (this.delayStep < this.tickDelay.getValue()) {
                ++this.delayStep;
                return;
            }
            this.delayStep = 0;
        }
        if (this.firstRun) {
            this.firstRun = false;
            if (this.findObiInHotbar() == -1) {
                this.missingObiDisable = true;
            }
        }
        Vec3d[] offsetPattern = new Vec3d[0];
        int maxSteps = 0;
        offsetPattern = Offsets.SURROUND;
        maxSteps = Offsets.SURROUND.length;
        int blocksPlaced = 0;
        while (blocksPlaced < this.blocksPerTick.getValue()) {
            if (this.offsetStep >= maxSteps) {
                this.offsetStep = 0;
                break;
            }
            final BlockPos offsetPos = new BlockPos(offsetPattern[this.offsetStep]);
            final BlockPos targetPos = new BlockPos(AutoFeetPlace.mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
            if (this.placeBlock(targetPos)) {
                ++blocksPlaced;
            }
            ++this.offsetStep;
        }
        if (blocksPlaced > 0) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                AutoFeetPlace.mc.player.inventory.currentItem = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                AutoFeetPlace.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoFeetPlace.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
        }
        ++this.totalTicksRunning;
        if (this.missingObiDisable && this.disableNone.getValue()) {
            this.missingObiDisable = false;
            this.disable();
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        final Block block = AutoFeetPlace.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        for (final Entity entity : AutoFeetPlace.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return false;
            }
        }
        final EnumFacing side = getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtils.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = AutoFeetPlace.mc.world.getBlockState(neighbour).getBlock();
        final int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.missingObiDisable = true;
            return false;
        }
        if (this.lastHotbarSlot != obiSlot) {
            AutoFeetPlace.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if ((!this.isSneaking && BlockUtils.blackList.contains(neighbourBlock)) || BlockUtils.shulkerList.contains(neighbourBlock)) {
            AutoFeetPlace.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoFeetPlace.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.getValue()) {
            BlockUtils.faceVectorPacketInstant(hitVec);
        }
        AutoFeetPlace.mc.playerController.processRightClickBlock(AutoFeetPlace.mc.player, AutoFeetPlace.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoFeetPlace.mc.player.swingArm(EnumHand.MAIN_HAND);
        AutoFeetPlace.mc.rightClickDelayTimer = 4;
        return true;
    }
    
    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoFeetPlace.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    private static class Offsets
    {
        private static final Vec3d[] SURROUND;
        
        static {
            SURROUND = new Vec3d[] { new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0) };
        }
    }
}
