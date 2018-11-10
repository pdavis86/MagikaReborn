package magikareborn;

import magikareborn.commands.ResetCommand;
import magikareborn.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.Level;

@Mod(modid = ModRoot.MODID, name = ModRoot.MODNAME, version = ModRoot.MODVERSION, useMetadata = true)
public class ModRoot {

    public static final String MODID = "magikareborn";
    public static final String MODNAME = "Magika Reborn";
    public static final String MODVERSION= "0.0.1";

    @SidedProxy(clientSide = "magikareborn.proxy.ClientProxy", serverSide = "magikareborn.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static ModRoot instance;

    public static Logger logger;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        logger = event.getModLog();
        //logger.log(Level.ERROR, "Test!");

        //System.out.println("preInit() " + event.getModMetadata().name);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ResetCommand());
    }
}
