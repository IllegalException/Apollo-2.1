//Decomped by XeonLyfe

package com.apollo.api.mixin;

import net.minecraftforge.fml.relauncher.*;
import com.apollo.client.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import java.util.*;

public class ApolloMixinLoader implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public ApolloMixinLoader() {
        Main.log.info("Apollo mixins initialized");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.apollo.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        Main.log.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    @Nullable
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        ApolloMixinLoader.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        ApolloMixinLoader.isObfuscatedEnvironment = false;
    }
}
