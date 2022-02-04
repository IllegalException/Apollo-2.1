//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.apollo.client.module.*;
import com.apollo.client.module.modules.movement.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockSoulSand.class })
public class MixinBlockSoulSand
{
    @Inject(method = { "onEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn, final CallbackInfo info) {
        if (ModuleManager.isModuleEnabled("Player Modifier") && ((PlayerModifier)ModuleManager.getModuleByName("Player Modifier")).noSlow.getValue()) {
            info.cancel();
        }
    }
}
