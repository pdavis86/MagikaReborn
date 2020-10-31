package uk.co.davissoftware.magikareborn;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapabilityStorage;
import uk.co.davissoftware.magikareborn.common.network.PacketHandler;
import uk.co.davissoftware.magikareborn.common.util.RegistryHandler;
import uk.co.davissoftware.magikareborn.init.ModBlocks;
import uk.co.davissoftware.magikareborn.init.ModEvents;

@Mod("magikareborn")
public class ModRoot {

    public static final String MODID = "magikareborn";
    //public static final String MODNAME = "Magika Reborn";
    //static final String MODVERSION = "0.0.1";

    public static final ItemGroup CREATIVE_TAB = new ItemGroup(MODID) {

        @Override
        public ItemStack createIcon() {
            //todo: change this to ModItems.MAGIKA_OPUS_ITEM
            return new ItemStack(Blocks.DIRT);
        }
    };

    public static final Logger LOGGER = LogManager.getLogger();

    public ModRoot() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        //LOGGER.info("HELLO FROM PREINIT");
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        MinecraftForge.EVENT_BUS.register(new ModEvents());
        PacketHandler.init();
        OpusCapabilityStorage.register();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

        ModBlocks.initModels();
    }

//    private void enqueueIMC(final InterModEnqueueEvent event) {
//        // some example code to dispatch IMC to another mod
//        InterModComms.sendTo("magikareborn", "helloworld", () -> {
//            LOGGER.info("Hello world from the MDK");
//            return "Hello world";
//        });
//    }
//
//    private void processIMC(final InterModProcessEvent event) {
//        // some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m -> m.getMessageSupplier().get()).
//                collect(Collectors.toList()));
//    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
