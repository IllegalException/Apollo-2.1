//Decomped by XeonLyfe

package com.apollo.api.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.scoreboard.*;
import com.apollo.api.players.friends.*;
import com.apollo.client.module.modules.hud.*;
import com.apollo.api.players.enemy.*;

@Mixin({ GuiPlayerTabOverlay.class })
public class MixinGuiPlayerTabOverlay
{
    @Inject(method = { "getPlayerName" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfoReturnable returnable) {
        returnable.cancel();
        returnable.setReturnValue((Object)this.getPlayerName(networkPlayerInfoIn));
    }
    
    public String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        final String dname = (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (Friends.isFriend(dname)) {
            return ColorMain.getFriendColor() + dname;
        }
        if (Enemies.isEnemy(dname)) {
            return ColorMain.getEnemyColor() + dname;
        }
        return dname;
    }
}
