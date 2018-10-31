package magikareborn.proxy;

import magikareborn.init.ModBlocks;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        //ModRoot.logger.log(Level.INFO, "Current language is " + Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
    }


    @SubscribeEvent
    public static void onModelEvent(final ModelRegistryEvent event)
    {
        System.out.println("Registering block models");

        //Item item = Item.getItemFromBlock(ModBlocks.manaFluidBlock);

        /*System.out.println("Item getUnlocalizedName is: " + item.getUnlocalizedName());
        ResourceLocation foo = ModBlocks.blockLiquidMana.getRegistryName();
        System.out.println("block getResourcePath is: " + foo.getResourceDomain() + ":" + foo.getResourcePath());
        System.out.println("block getUnlocalizedName is: " + ModBlocks.blockLiquidMana.getUnlocalizedName());*/

        //ResourceLocation itemResLoc = item.getRegistryName();

        //System.out.println("itemResLoc.getResourcePath is: " + itemResLoc.getResourcePath());

        /*ModelLoader.registerItemVariants(item);
        ModelLoader.setCustomModelResourceLocation(
                item
                , 0
                , new ModelResourceLocation(itemResLoc, itemResLoc.getResourcePath())
        );*/

        //ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));

        /*ModelBakery.registerItemVariants(item);
        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(ModRoot.MODID + ":fluid", ModBlocks.manaFluidBlock.getFluid().getName());
        ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);*/
        //ModGravestoneExtended.proxy.registerFluidRenderers(block, modelResourceLocation);
    }

    /*public void registerFluidModels(Fluid fluid) {
        if(fluid == null) {
            return;
        }

        Block block = fluid.getBlock();
        if(block != null) {
            Item item = Item.getItemFromBlock(block);
            FluidStateMapper mapper = new FluidStateMapper(fluid);

            // item-model
            if(item != Items.AIR) {
                ModelLoader.registerItemVariants(item);
                ModelLoader.setCustomMeshDefinition(item, mapper);
            }
            // block-model
            ModelLoader.setCustomStateMapper(block, mapper);
        }
    }*/
}
