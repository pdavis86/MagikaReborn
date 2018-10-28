package magikareborn.proxy;

import magikareborn.ModRoot;
import magikareborn.init.ModBlocks;
import magikareborn.init.ModFluids;
import magikareborn.textures.ResourceManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
    }

    @SubscribeEvent
    public static void onModelEvent(final ModelRegistryEvent event)
    {
        System.out.println("Registering block models");

        //todo: remove this when not messing around maybe?
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(ModBlocks.blockLiquidMana)
                , 0
                , new ModelResourceLocation(ModRoot.MODID + ":" + ResourceManager.getBlockTexturePath("liquidManaStill.png"))
        );
    }
}
