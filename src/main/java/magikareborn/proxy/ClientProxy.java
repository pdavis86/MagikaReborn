package magikareborn.proxy;

import magikareborn.ModRoot;
import magikareborn.init.ModBlocks;
import magikareborn.init.ModFluids;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        OBJLoader.INSTANCE.addDomain(ModRoot.MODID);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        //ModRoot.logger.log(Level.INFO, "Current language is " + Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
        ModFluids.initModels();
    }

}
