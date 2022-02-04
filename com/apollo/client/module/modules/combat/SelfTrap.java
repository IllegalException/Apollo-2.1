//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.client.module.*;
import com.apollo.api.values.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import com.apollo.api.util.world.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class SelfTrap extends Module
{
    private Value.Integer blocksPerTick;
    private Value.Integer tickDelay;
    private Value.Boolean rotate;
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    private int delayStep;
    private boolean isSneaking;
    private int offsetStep;
    private boolean firstRun;
    Value.Mode mode;
    
    public SelfTrap() {
        super("SelfTrap", Module.Category.Combat);
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.delayStep = 0;
        this.isSneaking = false;
        this.offsetStep = 0;
    }
    
    public void setup() {
        final ArrayList<String> modes = new ArrayList<String>();
        modes.add("Normal");
        modes.add("NoStep");
        modes.add("Simple");
        this.mode = this.registerMode("Mode", "Mode", (List)modes, "Normal");
        this.rotate = this.registerBoolean("Rotate", "Rotate", true);
        this.blocksPerTick = this.registerInteger("Blocks Per Tick", "BlocksPerTick", 5, 0, 10);
        this.tickDelay = this.registerInteger("Delay", "Delay", 0, 0, 10);
    }
    
    protected void onEnable() {
        if (SelfTrap.mc.player == null) {
            this.disable();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = SelfTrap.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }
    
    protected void onDisable() {
        if (SelfTrap.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            SelfTrap.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            SelfTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }
    
    public void onUpdate() {
        if (SelfTrap.mc.player == null) {
            return;
        }
        if (!this.firstRun) {
            if (this.delayStep < this.tickDelay.getValue()) {
                ++this.delayStep;
                return;
            }
            this.delayStep = 0;
        }
        this.findClosestTarget();
        if (this.closestTarget == null) {
            if (this.firstRun) {
                this.firstRun = false;
            }
            return;
        }
        if (this.firstRun) {
            this.firstRun = false;
            this.lastTickTargetName = this.closestTarget.getName();
        }
        else if (!this.lastTickTargetName.equals(this.closestTarget.getName())) {
            this.lastTickTargetName = this.closestTarget.getName();
            this.offsetStep = 0;
        }
        final List<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (this.mode.getValue().equalsIgnoreCase("Normal")) {
            Collections.addAll(placeTargets, Offsets.TRAP);
        }
        if (this.mode.getValue().equalsIgnoreCase("NoStep")) {
            Collections.addAll(placeTargets, Offsets.TRAPFULLROOF);
        }
        if (this.mode.getValue().equalsIgnoreCase("Simple")) {
            Collections.addAll(placeTargets, Offsets.TRAPSIMPLE);
        }
        int blocksPlaced = 0;
        while (blocksPlaced < this.blocksPerTick.getValue()) {
            if (this.offsetStep >= placeTargets.size()) {
                this.offsetStep = 0;
                break;
            }
            final BlockPos offsetPos = new BlockPos((Vec3d)placeTargets.get(this.offsetStep));
            final BlockPos targetPos = new BlockPos(this.closestTarget.getPositionVector()).down().add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
            if (this.placeBlockInRange(targetPos)) {
                ++blocksPlaced;
            }
            ++this.offsetStep;
        }
        if (blocksPlaced > 0) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                SelfTrap.mc.player.inventory.currentItem = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                SelfTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
        }
    }
    
    private boolean placeBlockInRange(final BlockPos pos) {
        final Block block = SelfTrap.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        for (final Entity entity : SelfTrap.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return false;
            }
        }
        final EnumFacing side = BlockUtils.getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtils.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = SelfTrap.mc.world.getBlockState(neighbour).getBlock();
        final int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != obiSlot) {
            SelfTrap.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if ((!this.isSneaking && BlockUtils.blackList.contains(neighbourBlock)) || BlockUtils.shulkerList.contains(neighbourBlock)) {
            SelfTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)SelfTrap.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.getValue()) {
            BlockUtils.faceVectorPacketInstant(hitVec);
        }
        SelfTrap.mc.playerController.processRightClickBlock(SelfTrap.mc.player, SelfTrap.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        SelfTrap.mc.player.swingArm(EnumHand.MAIN_HAND);
        SelfTrap.mc.rightClickDelayTimer = 4;
        return true;
    }
    
    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = SelfTrap.mc.player.inventory.getStackInSlot(i);
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
    
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)SelfTrap.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == SelfTrap.mc.player) {
                this.closestTarget = target;
            }
        }
    }
    
    private static class Offsets
    {
        private static final Vec3d[] TRAP;
        private static final Vec3d[] TRAPFULLROOF;
        private static final Vec3d[] TRAPSIMPLE;
        
        static {
            TRAP = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0) };
            TRAPFULLROOF = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0), new Vec3d(0.0, 4.0, 0.0) };
            TRAPSIMPLE = new Vec3d[] { new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0) };
        }
    }
}
