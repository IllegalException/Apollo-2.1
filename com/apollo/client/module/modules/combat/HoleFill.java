//Decomped by XeonLyfe

package com.apollo.client.module.modules.combat;

import com.apollo.client.module.*;
import net.minecraft.block.*;
import com.apollo.api.values.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import java.util.function.*;
import java.util.*;
import com.apollo.client.command.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.client.multiplayer.*;

public class HoleFill extends Module
{
    private ArrayList<BlockPos> holes;
    private final List<Block> obbyonly;
    private final List<Block> bothonly;
    private final List<Block> echestonly;
    private final List<Block> webonly;
    private List<Block> list;
    Value.Double range;
    Value.Integer yRange;
    Value.Integer waitTick;
    Value.Boolean chat;
    Value.Boolean rotate;
    Value.Mode type;
    BlockPos pos;
    private int waitCounter;
    
    public HoleFill() {
        super("HoleFill", Module.Category.Combat);
        this.holes = new ArrayList<BlockPos>();
        this.obbyonly = Arrays.asList(Blocks.OBSIDIAN);
        this.bothonly = Arrays.asList(Blocks.OBSIDIAN, Blocks.ENDER_CHEST);
        this.echestonly = Arrays.asList(Blocks.ENDER_CHEST);
        this.webonly = Arrays.asList(Blocks.WEB);
        this.list = Arrays.asList(new Block[0]);
    }
    
    public void setup() {
        final ArrayList<String> blockmode = new ArrayList<String>();
        blockmode.add("Obby");
        blockmode.add("EChest");
        blockmode.add("Both");
        blockmode.add("Web");
        this.type = this.registerMode("Block", "BlockMode", (List)blockmode, "Obby");
        this.range = this.registerDouble("Place Range", "PlaceRange", 5.0, 0.0, 10.0);
        this.yRange = this.registerInteger("Y Range", "YRange", 2, 0, 10);
        this.waitTick = this.registerInteger("Tick Delay", "TickDelay", 1, 0, 20);
        this.rotate = this.registerBoolean("Rotate", "Rotate", false);
        this.chat = this.registerBoolean("Toggle Msg", "ToggleMsg", false);
    }
    
    public void onUpdate() {
        this.holes = new ArrayList<BlockPos>();
        if (this.type.getValue().equalsIgnoreCase("Obby")) {
            this.list = this.obbyonly;
        }
        if (this.type.getValue().equalsIgnoreCase("EChest")) {
            this.list = this.echestonly;
        }
        if (this.type.getValue().equalsIgnoreCase("Both")) {
            this.list = this.bothonly;
        }
        if (this.type.getValue().equalsIgnoreCase("Web")) {
            this.list = this.webonly;
        }
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>)BlockPos.getAllInBox(HoleFill.mc.player.getPosition().add(-this.range.getValue(), (double)(-this.yRange.getValue()), -this.range.getValue()), HoleFill.mc.player.getPosition().add(this.range.getValue(), (double)this.yRange.getValue(), this.range.getValue()));
        for (final BlockPos pos : blocks) {
            if (!HoleFill.mc.world.getBlockState(pos).getMaterial().blocksMovement() && !HoleFill.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement()) {
                final boolean solidNeighbours = (HoleFill.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) && HoleFill.mc.world.getBlockState(pos.add(0, 0, 0)).getMaterial() == Material.AIR && HoleFill.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && HoleFill.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR;
                if (!solidNeighbours) {
                    continue;
                }
                this.holes.add(pos);
            }
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = HoleFill.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (this.list.contains(block)) {
                        newSlot = i;
                        break;
                    }
                }
            }
        }
        if (newSlot == -1) {
            return;
        }
        final int oldSlot = HoleFill.mc.player.inventory.currentItem;
        if (this.waitTick.getValue() > 0) {
            if (this.waitCounter < this.waitTick.getValue()) {
                HoleFill.mc.player.inventory.currentItem = newSlot;
                this.holes.forEach(this::place);
                HoleFill.mc.player.inventory.currentItem = oldSlot;
                return;
            }
            this.waitCounter = 0;
        }
    }
    
    public void onEnable() {
        if (HoleFill.mc.player != null && this.chat.getValue()) {
            Command.sendRawMessage("�aHolefill turned ON!");
        }
    }
    
    public void onDisable() {
        if (HoleFill.mc.player != null && this.chat.getValue()) {
            Command.sendRawMessage("�cHolefill turned OFF!");
        }
    }
    
    private void place(final BlockPos blockPos) {
        for (final Entity entity : HoleFill.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return;
            }
        }
        placeBlockScaffold(blockPos, this.rotate.getValue());
        ++this.waitCounter;
    }
    
    public static boolean placeBlockScaffold(final BlockPos pos, final boolean rotate) {
        final Vec3d eyesPos = new Vec3d(HoleFill.mc.player.posX, HoleFill.mc.player.posY + HoleFill.mc.player.getEyeHeight(), HoleFill.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    faceVectorPacketInstant(hitVec);
                }
                HoleFill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)HoleFill.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                processRightClickBlock(neighbor, side2, hitVec);
                HoleFill.mc.player.swingArm(EnumHand.MAIN_HAND);
                HoleFill.mc.rightClickDelayTimer = 0;
                HoleFill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)HoleFill.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return true;
            }
        }
        return false;
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return HoleFill.mc.world.getBlockState(pos);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }
    
    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        HoleFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], HoleFill.mc.player.onGround));
    }
    
    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { HoleFill.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - HoleFill.mc.player.rotationYaw), HoleFill.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - HoleFill.mc.player.rotationPitch) };
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(HoleFill.mc.player.posX, HoleFill.mc.player.posY + HoleFill.mc.player.getEyeHeight(), HoleFill.mc.player.posZ);
    }
    
    public static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(HoleFill.mc.player, HoleFill.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }
    
    private static PlayerControllerMP getPlayerController() {
        return HoleFill.mc.playerController;
    }
}
