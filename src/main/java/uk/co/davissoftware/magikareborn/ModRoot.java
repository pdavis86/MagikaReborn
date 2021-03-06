package magikareborn;

import magikareborn.commands.ResetCommand;
import magikareborn.init.ModCreativeTab;
import magikareborn.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@Mod(modid = ModRoot.MODID, name = ModRoot.MODNAME, version = ModRoot.MODVERSION, useMetadata = true)
public class ModRoot {

    public static final String MODID = "magikareborn";
    public static final String MODNAME = "Magika Reborn";
    static final String MODVERSION = "0.0.1";

    public static final CreativeTabs MAGIKA_REBORN_CREATIVE_TAB = new ModCreativeTab();

    @SidedProxy(clientSide = "magikareborn.proxy.ClientProxy", serverSide = "magikareborn.proxy.ServerProxy")
    private static CommonProxy proxy;

    @Mod.Instance
    public static ModRoot instance;

    public static Logger logger;
	
	//todo: Charge time
	//todo: Target self
	//todo: Recycler block
	//todo: Tooltips
	//todo: Cooldown is one of the stats

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
