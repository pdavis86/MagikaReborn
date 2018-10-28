package magikareborn.proxy;

import magikareborn.Config;
import magikareborn.blocks.BlockLiquidMana;

import magikareborn.init.ModBlocks;
import magikareborn.init.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.network.NetworkRegistry;
//import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
        
    public static Configuration config;
        
    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(),"MagikaReborn.cfg"));
        Config.readConfig();

        ModFluids.registerFluids();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        System.out.println("Registering Blocks");

        ModBlocks.blockLiquidMana = new BlockLiquidMana();

        event.getRegistry().registerAll(
            ModBlocks.blockLiquidMana
        );
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //event.getRegistry().register(new ItemBlock(ModBlocks.liquidEssence).setRegistryName(ModBlocks.liquidEssence.getRegistryName()));

        System.out.println("Registering ItemBlocks");

        event.getRegistry().registerAll(
            new ItemBlock(ModBlocks.blockLiquidMana).setRegistryName(ModBlocks.blockLiquidMana.getRegistryName())
        );
    }
}
