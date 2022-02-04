//Decomped by XeonLyfe

package com.apollo.client.module.modules.movement;

import com.apollo.api.values.*;
import com.apollo.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import java.util.*;
import com.apollo.client.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import com.apollo.client.module.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.apollo.api.util.world.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.block.*;

public class Speed extends Module
{
    int waitCounter;
    int forward;
    private double moveSpeed;
    public static boolean doSlow;
    public Timer waitTimer;
    Value.Boolean ice;
    Value.Mode Mode;
    Value.Double speed;
    @EventHandler
    private final Listener<PlayerMoveEvent> listener;
    
    public Speed() {
        super("Speed", Module.Category.Movement);
        this.forward = 1;
        this.waitTimer = new Timer();
        this.listener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            final boolean icee = this.ice.getValue() && (Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockIce || Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockPackedIce);
            if (icee) {
                return;
            }
            if (Speed.mc.player.isInLava() || Speed.mc.player.isInWater() || Speed.mc.player.isOnLadder()) {
                return;
            }
            if (this.Mode.getValue().equalsIgnoreCase("Strafe")) {
                double motionY = 0.41999998688697815;
                if (Speed.mc.player.onGround && MotionUtils.isMoving((EntityLivingBase)Speed.mc.player) && this.waitTimer.hasReached(300L)) {
                    if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                        motionY += (Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                    }
                    event.setY(Speed.mc.player.motionY = motionY);
                    this.moveSpeed = MotionUtils.getBaseMoveSpeed() * ((EntityUtil.isColliding(0.0, -0.5, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) ? 0.9 : 1.901);
                    Speed.doSlow = true;
                    this.waitTimer.reset();
                }
                else if (Speed.doSlow || Speed.mc.player.collidedHorizontally) {
                    final double moveSpeed = this.moveSpeed;
                    double n;
                    if (EntityUtil.isColliding(0.0, -0.8, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) {
                        n = 0.4;
                    }
                    else {
                        final double n2 = 0.7;
                        final double baseMoveSpeed = MotionUtils.getBaseMoveSpeed();
                        this.moveSpeed = baseMoveSpeed;
                        n = n2 * baseMoveSpeed;
                    }
                    this.moveSpeed = moveSpeed - n;
                    Speed.doSlow = false;
                }
                else {
                    this.moveSpeed -= this.moveSpeed / 159.0;
                }
                this.moveSpeed = Math.max(this.moveSpeed, MotionUtils.getBaseMoveSpeed());
                final double[] dir = MotionUtils.forward(this.moveSpeed);
                event.setX(dir[0]);
                event.setZ(dir[1]);
            }
        }, new Predicate[0]);
    }
    
    public void setup() {
        this.ice = this.registerBoolean("Ice", "Ice", true);
        final ArrayList<String> modes = new ArrayList<String>();
        modes.add("Strafe");
        modes.add("YPort");
        modes.add("Packet");
        modes.add("Packet2");
        modes.add("FakeStrafe");
        this.speed = this.registerDouble("Speed", "Speed", 8.0, 0.0, 10.0);
        this.Mode = this.registerMode("Modes", "Modes", (List)modes, "Strafe");
    }
    
    public void onEnable() {
        this.moveSpeed = MotionUtils.getBaseMoveSpeed();
        Main.EVENT_BUS.subscribe((Object)this);
    }
    
    public void onUpdate() {
        final boolean icee = this.ice.getValue() && (Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockIce || Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockPackedIce);
        if (icee) {
            MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, MotionUtils.getBaseMoveSpeed() + (Speed.mc.player.isPotionActive(MobEffects.SPEED) ? ((Speed.mc.player.ticksExisted % 2 == 0) ? 0.7 : 0.1) : 0.4));
        }
        if (!icee) {
            if ((this.Mode.getValue().equalsIgnoreCase("Packet") || this.Mode.getValue().equalsIgnoreCase("Packet2")) && MotionUtils.isMoving((EntityLivingBase)Speed.mc.player) && Speed.mc.player.onGround) {
                final boolean step = ModuleManager.isModuleEnabled("Step");
                final double posX = Speed.mc.player.posX;
                final double posY = Speed.mc.player.posY;
                final double posZ = Speed.mc.player.posZ;
                final boolean ground = Speed.mc.player.onGround;
                final double[] dir1 = MotionUtils.forward(0.5);
                final BlockPos pos = new BlockPos(posX + dir1[0], posY, posZ + dir1[1]);
                final Block block = Speed.mc.world.getBlockState(pos).getBlock();
                if (step && !(block instanceof BlockAir)) {
                    MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, 0.0);
                    return;
                }
                if (Speed.mc.world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() instanceof BlockAir) {
                    return;
                }
                for (double x = 0.0625; x < this.speed.getValue(); x += 0.262) {
                    final double[] dir2 = MotionUtils.forward(x);
                    Speed.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX + dir2[0], posY, posZ + dir2[1], ground));
                }
                if (this.Mode.getValue().equalsIgnoreCase("Packet2")) {
                    MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, 2.0);
                }
                Speed.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX + Speed.mc.player.motionX, (Speed.mc.player.posY <= 10.0) ? 255.0 : 1.0, posZ + Speed.mc.player.motionZ, ground));
            }
            if (this.Mode.getValue().equalsIgnoreCase("YPort")) {
                if (!MotionUtils.isMoving((EntityLivingBase)Speed.mc.player) || (Speed.mc.player.isInWater() && Speed.mc.player.isInLava()) || Speed.mc.player.collidedHorizontally) {
                    return;
                }
                if (Speed.mc.player.onGround) {
                    EntityUtil.setTimer(1.15f);
                    Speed.mc.player.jump();
                    final boolean ice = Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockIce || Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockPackedIce;
                    MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, MotionUtils.getBaseMoveSpeed() + (ice ? 0.3 : 0.06));
                }
                else {
                    Speed.mc.player.motionY = -1.0;
                    EntityUtil.resetTimer();
                }
            }
        }
    }
    
    public void onDisable() {
        Main.EVENT_BUS.unsubscribe((Object)this);
        EntityUtil.resetTimer();
    }
    
    public String getHudInfo() {
        String t = "";
        if (this.Mode.getValue().equalsIgnoreCase("Strafe")) {
            t = "[" + ChatFormatting.WHITE + "Strafe" + ChatFormatting.GRAY + "]";
        }
        if (this.Mode.getValue().equalsIgnoreCase("YPort")) {
            t = "[" + ChatFormatting.WHITE + "YPort" + ChatFormatting.GRAY + "]";
        }
        if (this.Mode.getValue().equalsIgnoreCase("Packet")) {
            t = "[" + ChatFormatting.WHITE + "Packet" + ChatFormatting.GRAY + "]";
        }
        if (this.Mode.getValue().equalsIgnoreCase("Packet2")) {
            t = "[" + ChatFormatting.WHITE + "Packet2" + ChatFormatting.GRAY + "]";
        }
        if (this.Mode.getValue().equalsIgnoreCase("FakeStrafe")) {
            t = "[" + ChatFormatting.WHITE + "Strafe" + ChatFormatting.GRAY + "]";
        }
        return t;
    }
}
