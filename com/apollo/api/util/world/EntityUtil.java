//Decomped by XeonLyfe

package com.apollo.api.util.world;

import net.minecraft.client.*;
import com.apollo.api.util.*;
import java.net.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import java.io.*;
import net.minecraft.util.math.*;
import net.minecraft.potion.*;
import net.minecraft.block.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;

public class EntityUtil
{
    private static final Minecraft mc;
    
    public static boolean isPassive(final Entity e) {
        return (!(e instanceof EntityWolf) || !((EntityWolf)e).isAngry()) && (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid || (e instanceof EntityIronGolem && ((EntityIronGolem)e).getRevengeTarget() == null));
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
    
    public static boolean isFakeLocalPlayer(final Entity entity) {
        return entity != null && entity.getEntityId() == -100 && Wrapper.getPlayer() != entity;
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    public static String getNameFromUUID(final String uuid) {
        try {
            final String jsonUrl = IOUtils.toString(new URL("https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names"));
            final JsonParser parser = new JsonParser();
            return parser.parse(jsonUrl).getAsJsonArray().get(parser.parse(jsonUrl).getAsJsonArray().size() - 1).getAsJsonObject().get("name").toString();
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public static Block isColliding(final double posX, final double posY, final double posZ) {
        Block block = null;
        if (EntityUtil.mc.player != null) {
            final AxisAlignedBB bb = (EntityUtil.mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(posX, posY, posZ) : EntityUtil.mc.player.getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(posX, posY, posZ);
            final int y = (int)bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; ++x) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; ++z) {
                    block = EntityUtil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                }
            }
        }
        return block;
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (EntityUtil.mc.player != null && EntityUtil.mc.player.isPotionActive(Potion.getPotionById(1))) {
            final int amplifier = EntityUtil.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static boolean isInLiquid() {
        if (EntityUtil.mc.player == null) {
            return false;
        }
        if (EntityUtil.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        boolean inLiquid = false;
        final AxisAlignedBB bb = (EntityUtil.mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().getEntityBoundingBox() : EntityUtil.mc.player.getEntityBoundingBox();
        final int y = (int)bb.minY;
        for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; ++x) {
            for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; ++z) {
                final Block block = EntityUtil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (!(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static void setTimer(final float speed) {
        Minecraft.getMinecraft().timer.tickLength = 50.0f / speed;
    }
    
    public static void resetTimer() {
        Minecraft.getMinecraft().timer.tickLength = 50.0f;
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final Vec3d vec) {
        return getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static boolean isMobAggressive(final Entity entity) {
        if (entity instanceof EntityPigZombie) {
            if (((EntityPigZombie)entity).isArmsRaised() || ((EntityPigZombie)entity).isAngry()) {
                return true;
            }
        }
        else {
            if (entity instanceof EntityWolf) {
                return ((EntityWolf)entity).isAngry() && !Wrapper.getPlayer().equals((Object)((EntityWolf)entity).getOwner());
            }
            if (entity instanceof EntityEnderman) {
                return ((EntityEnderman)entity).isScreaming();
            }
        }
        return isHostileMob(entity);
    }
    
    public static boolean isNeutralMob(final Entity entity) {
        return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
    }
    
    public static boolean isFriendlyMob(final Entity entity) {
        return (entity.isCreatureType(EnumCreatureType.CREATURE, false) && !isNeutralMob(entity)) || entity.isCreatureType(EnumCreatureType.AMBIENT, false) || entity instanceof EntityVillager || entity instanceof EntityIronGolem || (isNeutralMob(entity) && !isMobAggressive(entity));
    }
    
    public static boolean isHostileMob(final Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return getInterpolatedPos(entity, ticks).subtract(Wrapper.getMinecraft().getRenderManager().renderPosX, Wrapper.getMinecraft().getRenderManager().renderPosY, Wrapper.getMinecraft().getRenderManager().renderPosZ);
    }
    
    public static boolean isInWater(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double y = entity.posY + 0.01;
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int)y, z);
                if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isDrivenByPlayer(final Entity entityIn) {
        return Wrapper.getPlayer() != null && entityIn != null && entityIn.equals((Object)Wrapper.getPlayer().getRidingEntity());
    }
    
    public static boolean isAboveWater(final Entity entity) {
        return isAboveWater(entity, false);
    }
    
    public static boolean isAboveWater(final Entity entity, final boolean packet) {
        if (entity == null) {
            return false;
        }
        final double y = entity.posY - (packet ? 0.03 : (isPlayer(entity) ? 0.2 : 0.5));
        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public static boolean isPlayer(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public static double getRelativeX(final float yaw) {
        return MathHelper.sin(-yaw * 0.017453292f);
    }
    
    public static double getRelativeZ(final float yaw) {
        return MathHelper.cos(yaw * 0.017453292f);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
