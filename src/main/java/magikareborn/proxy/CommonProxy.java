package magikareborn.proxy;

import magikareborn.Config;
import magikareborn.ModRoot;
import magikareborn.init.ModBlocks;
import magikareborn.tileentities.DataTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
        
    public static Configuration _config;
        
    public void preInit(FMLPreInitializationEvent e) {
        File modConfigurationDirectory = e.getModConfigurationDirectory();
        _config = new Configuration(new File(modConfigurationDirectory.getPath(),"MagikaReborn.cfg"));
        Config.readConfig(_config);

        registerFluids();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (_config.hasChanged()) {
            _config.save();
        }
    }

    public static void registerFluids()
    {
        //todo: replace system.out
        System.out.println("Registering fluids");

        //FluidRegistry.registerFluid(ModFluids.manaFluid);
        //FluidRegistry.addBucketForFluid(ModFluids.manaFluid);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        System.out.println("Registering Blocks");

        event.getRegistry().registerAll(
            //ModBlocks.manaFluidBlock
            //ModBlocks.LecternBlock
            ModBlocks.DATA_BLOCK
        );

        registerTileEntity(DataTileEntity.class);
        //GameRegistry.registerTileEntity(DataTileEntity.class, new ResourceLocation(ModRoot.MODID + ":" + "DataTileEntity".toLowerCase()));
    }

    private static void registerTileEntity(Class classType){
        System.out.println("Registering TileEntity with name: " + classType.getSimpleName().toLowerCase());
        GameRegistry.registerTileEntity(classType, new ResourceLocation(ModRoot.MODID + ":" + classType.getSimpleName().toLowerCase()));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        System.out.println("Registering Items");

        event.getRegistry().registerAll(
            //new ItemBlock(ModBlocks.manaFluidBlock).setRegistryName(ModBlocks.manaFluidBlock.getRegistryName())
            //new ItemBlock(ModBlocks.LecternBlock).setRegistryName(ModBlocks.LecternBlock.getRegistryName()),
            //ModItems.LecternItem,
            //new ItemBlock(ModBlocks.DataBlock).setRegistryName(ModBlocks.DataBlock.getRegistryName())
            ModBlocks.DATA_BLOCK.getNewItem()
        );
    }
}
