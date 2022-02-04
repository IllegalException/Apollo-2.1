//Decomped by XeonLyfe

package com.apollo.client.module;

import java.util.*;
import com.apollo.client.module.modules.combat.*;
import com.apollo.client.module.modules.exploits.*;
import com.apollo.client.module.modules.movement.*;
import com.apollo.client.module.modules.misc.*;
import com.apollo.client.module.modules.render.*;
import com.apollo.client.module.modules.hud.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import com.apollo.api.util.render.*;
import com.apollo.api.event.events.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import java.util.stream.*;

public class ModuleManager
{
    public static ArrayList<Module> modules;
    
    public ModuleManager() {
        ModuleManager.modules = new ArrayList<Module>();
        addMod(new AutoArmor());
        addMod(new AutoCrystal());
        addMod(new AutoFeetPlace());
        addMod(new AutoTotem());
        addMod(new AutoTrap());
        addMod(new AutoWeb());
        addMod(new DesyncAura());
        addMod(new FastBow());
        addMod(new HoleFill());
        addMod(new KillAura());
        addMod(new OffhandCrystal());
        addMod(new OffhandGap());
        addMod(new SelfTrap());
        addMod(new PacketSwing());
        addMod(new CoordExploit());
        addMod(new MiningBypass());
        addMod(new LiquidInteract());
        addMod(new NoInteract());
        addMod(new NoSwing());
        addMod(new PortalGodMode());
        addMod(new HoleTP());
        addMod(new PlayerModifier());
        addMod(new ReverseStep());
        addMod(new Speed());
        addMod(new Sprint());
        addMod(new Step());
        addMod(new Announcer());
        addMod(new AutoGG());
        addMod(new AutoReply());
        addMod(new AutoTool());
        addMod(new ChatModifier());
        addMod(new ChatSuffix());
        addMod(new FastPlace());
        addMod(new MCF());
        addMod(new MultiTask());
        addMod(new NoEntityTrace());
        addMod(new NoKick());
        addMod(new PvPInfo());
        addMod(new TotemPopCounter());
        addMod(new BlockHighlight());
        addMod(new CapesModule());
        addMod(new EntityESP());
        addMod(new Freecam());
        addMod(new Fullbright());
        addMod(new HitSpheres());
        addMod(new HoleESP());
        addMod(new LogoutSpots());
        addMod(new MobOwner());
        addMod(new Nametags());
        addMod(new NoRender());
        addMod(new RenderTweaks());
        addMod(new ShulkerViewer());
        addMod(new StorageESP());
        addMod(new Tracers());
        addMod(new ViewModel());
        addMod(new VoidESP());
        addMod(new ClickGuiModule());
        addMod(new ColorMain());
        addMod(new HUD());
        addMod(new Notifications());
        addMod(new TextRadar());
        addMod(new Notifications2());
    }
    
    public static void addMod(final Module m) {
        ModuleManager.modules.add(m);
    }
    
    public static void onUpdate() {
        ModuleManager.modules.stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }
    
    public static void onRender() {
        ModuleManager.modules.stream().filter(Module::isEnabled).forEach(Module::onRender);
    }
    
    public static void onWorldRender(final RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("Apollo");
        Minecraft.getMinecraft().profiler.startSection("setup");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        final Vec3d renderPos = getInterpolatedPos((Entity)Minecraft.getMinecraft().player, event.getPartialTicks());
        final RenderEvent e = new RenderEvent((Tessellator)ApolloTessellator.INSTANCE, renderPos, event.getPartialTicks());
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();
        final RenderEvent renderEvent;
        ModuleManager.modules.stream().filter(module -> module.isEnabled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(renderEvent);
            Minecraft.getMinecraft().profiler.endSection();
            return;
        });
        Minecraft.getMinecraft().profiler.startSection("release");
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        ApolloTessellator.releaseGL();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }
    
    public static ArrayList<Module> getModules() {
        return ModuleManager.modules;
    }
    
    public static ArrayList<Module> getModulesInCategory(final Module.Category c) {
        final ArrayList<Module> list = getModules().stream().filter(m -> m.getCategory().equals((Object)c)).collect((Collector<? super Object, ?, ArrayList<Module>>)Collectors.toList());
        return list;
    }
    
    public static void onBind(final int key) {
        if (key == 0 || key == 0) {
            return;
        }
        ModuleManager.modules.forEach(module -> {
            if (module.getBind() == key) {
                module.toggle();
            }
        });
    }
    
    public static Module getModuleByName(final String name) {
        final Module m = getModules().stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m;
    }
    
    public static boolean isModuleEnabled(final String name) {
        final Module m = getModules().stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m.isEnabled();
    }
    
    public static boolean isModuleEnabled(final Module m) {
        return m.isEnabled();
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
}
