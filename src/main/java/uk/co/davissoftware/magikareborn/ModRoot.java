package uk.co.davissoftware.magikareborn;

import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.davissoftware.magikareborn.client.screens.SpellAltarScreen;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapabilityStorage;
import uk.co.davissoftware.magikareborn.common.network.PacketHandler;
import uk.co.davissoftware.magikareborn.common.util.RegistryHandler;
import uk.co.davissoftware.magikareborn.init.ModBlocks;
import uk.co.davissoftware.magikareborn.init.ModEntities;
import uk.co.davissoftware.magikareborn.init.ModEvents;
import uk.co.davissoftware.magikareborn.init.ModTileEntities;

@Mod("magikareborn")
public class ModRoot {

    public static final String MODID = "magikareborn";

    public static final ItemGroup CREATIVE_TAB = new ItemGroup(MODID) {

        @Override
        public ITextComponent func_242392_c() {
            return new TranslationTextComponent("itemgroup.magikareborn");
        }

        @Override
        public ItemStack createIcon() {
            //todo: change this to ModItems.MAGIKA_OPUS_ITEM
            return new ItemStack(RegistryHandler.SPELLALTAR_BLOCK.get());
        }
    };

    public static final Logger LOGGER = LogManager.getLogger();

    //todo: Charge time
    //todo: Target self
    //todo: Recycler block
    //todo: Tooltips
    //todo: Cooldown is one of the stats

    public ModRoot() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        RegistryHandler.init();
        ModTileEntities.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        PacketHandler.init();
        OpusCapabilityStorage.register();
    }

    @SuppressWarnings("unchecked")
    private void clientSetup(final FMLClientSetupEvent event) {
        ModBlocks.initModels();
        ModEntities.registerEntityRenderers();

        ScreenManager.registerFactory(RegistryHandler.SPELLALTAR_CONTAINER.get(), SpellAltarScreen::new);
    }

//    private void enqueueIMC(final InterModEnqueueEvent event) {
//        // some example code to dispatch IMC to another mod
//        InterModComms.sendTo("magikareborn", "helloworld", () -> {
//            LOGGER.info("Hello world from the MDK");
//            return "Hello world";
//        });
//    }

//    private void processIMC(final InterModProcessEvent event) {
//        // some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m -> m.getMessageSupplier().get()).
//                collect(Collectors.toList()));
//    }

}
