//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import com.apollo.client.module.modules.render.*;
import com.apollo.client.module.*;
import com.apollo.client.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer
{
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();
    
    @Inject(method = { "getLocationCape" }, at = { @At("HEAD") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> cir) {
        final UUID uuid = this.getPlayerInfo().getGameProfile().getId();
        final CapesModule capesModule = (CapesModule)ModuleManager.getModuleByName("Capes");
        if (ModuleManager.isModuleEnabled("Capes") && Main.getInstance().capeUtils.hasCape(uuid)) {
            if (capesModule.capeMode.getValue().equalsIgnoreCase("Black")) {
                cir.setReturnValue((Object)new ResourceLocation("gamesense:textures/capeblack.png"));
            }
            else {
                cir.setReturnValue((Object)new ResourceLocation("gamesense:textures/capewhite.png"));
            }
        }
    }
}
