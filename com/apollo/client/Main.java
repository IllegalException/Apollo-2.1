//Decomped by XeonLyfe

package com.apollo.client;

import net.minecraftforge.fml.common.*;
import com.apollo.client.clickgui.*;
import com.apollo.api.values.*;
import com.apollo.api.players.friends.*;
import com.apollo.client.module.*;
import com.apollo.api.util.config.*;
import com.apollo.api.util.render.*;
import com.apollo.client.macro.*;
import com.apollo.api.event.*;
import com.apollo.api.util.font.*;
import com.apollo.api.players.enemy.*;
import java.awt.*;
import com.apollo.api.util.world.*;
import com.apollo.api.*;
import com.apollo.client.command.*;
import net.minecraftforge.fml.common.event.*;
import org.lwjgl.opengl.*;
import org.apache.logging.log4j.*;
import me.zero.alpine.*;

@Mod(modid = "apollo", name = "Apollo", version = "v0.2", clientSideOnly = true)
public class Main
{
    public static final String MODID = "apollo";
    public static String MODNAME;
    public static final String MODVER = "v0.2";
    public static final String FORGENAME = "Apollo";
    public static final Logger log;
    public ClickGUI clickGUI;
    public ValuesManager valuesManager;
    public Friends friends;
    public ModuleManager moduleManager;
    public SaveConfiguration saveConfiguration;
    public LoadConfiguration loadConfiguration;
    public SaveModules saveModules;
    public LoadModules loadModules;
    public CapeUtils capeUtils;
    public MacroManager macroManager;
    EventProcessor eventProcessor;
    public static CFontRenderer fontRenderer;
    public static Enemies enemies;
    public static final EventBus EVENT_BUS;
    @Mod.Instance
    private static Main INSTANCE;
    
    public Main() {
        Main.INSTANCE = this;
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        DiscordRich.init();
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        (this.eventProcessor = new EventProcessor()).init();
        Main.fontRenderer = new CFontRenderer(new Font("Comic Sans ", 0, 20), true, true);
        final TpsUtils tpsUtils = new TpsUtils();
        this.valuesManager = new ValuesManager();
        Main.log.info("Settings initialized!");
        this.friends = new Friends();
        Main.enemies = new Enemies();
        Main.log.info("Friends and enemies initialized!");
        this.moduleManager = new ModuleManager();
        Main.log.info("Modules initialized!");
        this.clickGUI = new ClickGUI();
        Main.log.info("ClickGUI initialized!");
        this.macroManager = new MacroManager();
        Main.log.info("Macros initialized!");
        this.saveConfiguration = new SaveConfiguration();
        Runtime.getRuntime().addShutdownHook((Thread)new Stopper());
        Main.log.info("Config Saved!");
        this.loadConfiguration = new LoadConfiguration();
        Main.log.info("Config Loaded!");
        this.saveModules = new SaveModules();
        Runtime.getRuntime().addShutdownHook((Thread)new Stopper());
        Main.log.info("Modules Saved!");
        this.loadModules = new LoadModules();
        Main.log.info("Modules Loaded!");
        CommandManager.initCommands();
        Main.log.info("Commands initialized!");
        Main.log.info("Initialization complete!\n");
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Display.setTitle(Main.MODNAME + " " + "v0.2");
        this.capeUtils = new CapeUtils();
        Main.log.info("Capes initialised!");
        Main.log.info("PostInitialization complete!\n");
    }
    
    public static Main getInstance() {
        return Main.INSTANCE;
    }
    
    static {
        Main.MODNAME = "Apollo";
        log = LogManager.getLogger(Main.MODNAME);
        EVENT_BUS = (EventBus)new EventManager();
    }
}
