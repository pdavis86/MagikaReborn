package magikareborn.proxy;

import magikareborn.Capabilities.ManaCapabilitySerializer;
import magikareborn.Capabilities.ManaCapabilityStorage;
import magikareborn.Capabilities.OpusCapabilityStorage;
import magikareborn.Capabilities.OpusCapabilitySerializer;
import magikareborn.Config;
import magikareborn.ModRoot;
import magikareborn.init.ModBlocks;
import magikareborn.init.ModFluids;
import magikareborn.init.ModItems;
import magikareborn.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
        
    public void preInit(FMLPreInitializationEvent e) {
        File modConfigurationDirectory = e.getModConfigurationDirectory();
        Config.readConfig(modConfigurationDirectory);

        PacketHandler.registerPacketTypes();
        OpusCapabilityStorage.register();
        ManaCapabilityStorage.register();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ModRoot.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        Config.saveChanges();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        System.out.println("Registering fluids");
        FluidRegistry.addBucketForFluid(ModFluids.MANA_FLUID);

        System.out.println("Registering Blocks");
        event.getRegistry().registerAll(
            ModBlocks.MANA_FLUID_BLOCK
        );

        System.out.println("Registering Tile Entities");
        //registerTileEntity(????.class);
    }

    private static void registerTileEntity(Class<? extends TileEntity> classType){
        //System.out.println("Registering TileEntity with name: " + classType.getSimpleName().toLowerCase());
        GameRegistry.registerTileEntity(classType, new ResourceLocation(ModRoot.MODID.toLowerCase() + ":" + classType.getSimpleName().toLowerCase()));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        System.out.println("Registering Items");

        event.getRegistry().registerAll(
            ModItems.MAGIKA_OPUS_ITEM,
            ModBlocks.MANA_FLUID_BLOCK.getNewItem()
        );
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {

        if(event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(ModRoot.MODID,"opuscapability"), new OpusCapabilitySerializer());
            event.addCapability(new ResourceLocation(ModRoot.MODID, "manacapability"), new ManaCapabilitySerializer());
        }
    }


}
