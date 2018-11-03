package magikareborn.proxy;

import magikareborn.Config;
import magikareborn.ModRoot;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModBlocks;
import magikareborn.init.ModFluids;
import magikareborn.tileentities.BlinkingTileEntity;
import magikareborn.tileentities.DataTileEntity;
import magikareborn.tileentities.PedestalTileEntity;
import magikareborn.tileentities.TinyChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
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

        /*ModFluids.MANA_FLUID.setUnlocalizedName(ModRoot.MODID.toLowerCase() + "." + ModFluids.MANA_FLUID.getName().toLowerCase());
        FluidRegistry.addBucketForFluid(ModFluids.MANA_FLUID);*/
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ModRoot.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        Config.saveChanges();
    }

    /*private static void registerFluid(Fluid fluid, Block block){

        String unlocalizedName = ModRoot.MODID.toLowerCase() + "." + fluid.getName().toLowerCase();

        fluid.setUnlocalizedName(unlocalizedName);
        //FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);

        if(block != null) {
            block.setUnlocalizedName(unlocalizedName);
            block.setRegistryName(new ResourceLocation(ModRoot.MODID.toLowerCase(), fluid.getName().toLowerCase()));
        }
    }*/

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        System.out.println("Registering fluids");
        /*Fluid fluid = new ManaFluid();
        Block fluidBlock = new ManaFluidBlock();
        registerFluid(fluid, fluidBlock);*/
        FluidRegistry.addBucketForFluid(ModFluids.MANA_FLUID);

        /*ModBlocks.MANA_FLUID_BLOCK.setUnlocalizedName(ModRoot.MODID.toLowerCase() + "." + ModFluids.MANA_FLUID.getName().toLowerCase());
        ModBlocks.MANA_FLUID_BLOCK.setRegistryName(new ResourceLocation(ModRoot.MODID.toLowerCase(), ModFluids.MANA_FLUID.getName().toLowerCase()));
        event.getRegistry().register(ModBlocks.MANA_FLUID_BLOCK);*/

        ModBlocks.MANA_FLUID_BLOCK.setCreativeTab(CreativeTabs.MISC);
        ModBlocks.MANA_FLUID_BLOCK.setRegistryName("blah");
        ModBlocks.MANA_FLUID_BLOCK.setUnlocalizedName(ModRoot.MODID.toLowerCase() + "." + "blah".toLowerCase());
        event.getRegistry().register(ModBlocks.MANA_FLUID_BLOCK);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.MANA_FLUID_BLOCK), 0, new ModelResourceLocation(ModBlocks.MANA_FLUID_BLOCK.getRegistryName(), ResourceHelper.VARIANT_INVENTORY));
        ModelLoader.registerItemVariants(Item.getItemFromBlock(ModBlocks.MANA_FLUID_BLOCK));

        System.out.println("Registering Blocks");
        event.getRegistry().registerAll(
            //ModBlocks.MANA_FLUID_BLOCK,
            ModBlocks.DATA_BLOCK,
            ModBlocks.BLINKING_BLOCK,
            ModBlocks.PEDESTAL_BLOCK,
            ModBlocks.TINYCHEST_BLOCK
            //ModBlocks.LecternBlock
        );

        registerTileEntity(DataTileEntity.class);
        registerTileEntity(BlinkingTileEntity.class);
        registerTileEntity(PedestalTileEntity.class);
        registerTileEntity(TinyChestTileEntity.class);
    }

    private static void registerTileEntity(Class<? extends TileEntity> classType){
        //System.out.println("Registering TileEntity with name: " + classType.getSimpleName().toLowerCase());
        GameRegistry.registerTileEntity(classType, new ResourceLocation(ModRoot.MODID.toLowerCase() + ":" + classType.getSimpleName().toLowerCase()));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        System.out.println("Registering Items");

        System.out.println("FINDME: " + ModBlocks.MANA_FLUID_BLOCK.getRegistryName().getResourcePath());

        event.getRegistry().registerAll(
            new ItemBlock(ModBlocks.MANA_FLUID_BLOCK).setRegistryName(ModBlocks.MANA_FLUID_BLOCK.getRegistryName()),
            ModBlocks.DATA_BLOCK.getNewItem(),
            ModBlocks.BLINKING_BLOCK.getNewItem(),
            ModBlocks.PEDESTAL_BLOCK.getNewItem(),
            ModBlocks.TINYCHEST_BLOCK.getNewItem()
            //ModBlocks.LecternBlock.getNewItem(),
            //ModItems.LecternItem,
        );
    }

}
