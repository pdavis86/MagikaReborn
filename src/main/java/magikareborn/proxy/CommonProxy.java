package magikareborn.proxy;

import magikareborn.Config;
import magikareborn.ModRoot;
import magikareborn.capabilities.OpusCapabilitySerializer;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModBlocks;
import magikareborn.init.ModEntities;
import magikareborn.init.ModFluids;
import magikareborn.init.ModItems;
import magikareborn.network.PacketHandler;
import magikareborn.recipes.ManaPoolRecipe;
import magikareborn.recipes.SpellAltarRecipe;
import magikareborn.tileentities.ManaTankTileEntity;
import magikareborn.tileentities.SpellAltarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.util.ArrayList;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        File modConfigurationDirectory = e.getModConfigurationDirectory();
        Config.readConfig(modConfigurationDirectory);

        PacketHandler.registerPacketTypes();
        OpusCapabilityStorage.register();

        ModEntities.registerEntities();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ModRoot.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        Config.saveChanges();
    }

    private static void registerTileEntity(Class<? extends TileEntity> classType) {
        //System.out.println("Registering TileEntity with name: " + classType.getSimpleName().toLowerCase());
        GameRegistry.registerTileEntity(classType, new ResourceLocation(ModRoot.MODID.toLowerCase() + ":" + classType.getSimpleName().toLowerCase()));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        //System.out.println("Registering fluids");
        FluidRegistry.addBucketForFluid(ModFluids.MANA_FLUID);

        //System.out.println("Registering Blocks");
        event.getRegistry().registerAll(
                ModBlocks.MANA_FLUID_BLOCK,
                ModBlocks.LIGHT_SPELL_BLOCK,
                ModBlocks.SPELL_ALTAR_BLOCK,
                ModBlocks.MAGICAL_LOG_BLOCK,
                ModBlocks.MAGICAL_PLANKS_BLOCK,
                ModBlocks.MANA_TANK_BLOCK
        );

        //System.out.println("Registering Tile Entities");
        GameRegistry.registerTileEntity(SpellAltarTileEntity.class, ResourceHelper.getTileEntityResource(SpellAltarTileEntity.class));
        GameRegistry.registerTileEntity(ManaTankTileEntity.class, ResourceHelper.getTileEntityResource(ManaTankTileEntity.class));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        //System.out.println("Registering Items");

        //NOTE: This order determines the order in the creative tab
        event.getRegistry().registerAll(
                ModBlocks.MANA_FLUID_BLOCK.getNewItem(),
                ModItems.MAGIKA_OPUS_ITEM,
                ModBlocks.MAGICAL_LOG_BLOCK.getNewItem(),
                ModBlocks.MAGICAL_PLANKS_BLOCK.getNewItem(),
                ModBlocks.SPELL_ALTAR_BLOCK.getNewItem(),
                ModBlocks.MANA_TANK_BLOCK.getNewItem(),
                ModItems.MANA_TANK_ITEM,
                ModBlocks.LIGHT_SPELL_BLOCK.getNewItem(),
                ModItems.LIGHT_SPELL_ITEM
        );

        OreDictionary.registerOre("logWood", ModBlocks.MAGICAL_LOG_BLOCK);
        OreDictionary.registerOre("plankWood", ModBlocks.MAGICAL_PLANKS_BLOCK);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {

        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(ModRoot.MODID, "opuscapability"), new OpusCapabilitySerializer());
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

        ManaPoolRecipe.addRecipe(
                new ItemStack(ModItems.MAGIKA_OPUS_ITEM),
                new ItemStack(Items.BOOK)
        );
        ManaPoolRecipe.addRecipe(
                new ItemStack(Item.getItemFromBlock(ModBlocks.MAGICAL_LOG_BLOCK)),
                new ItemStack(Item.getItemFromBlock(Blocks.LOG), 1, OreDictionary.WILDCARD_VALUE)
        );
        ManaPoolRecipe.addRecipe(
                new ItemStack(Item.getItemFromBlock(ModBlocks.MAGICAL_PLANKS_BLOCK)),
                new ItemStack(Item.getItemFromBlock(Blocks.PLANKS), 1, OreDictionary.WILDCARD_VALUE)
        );

        //todo: Iterate over a list of recipes
        SpellAltarRecipe.addRecipe(
                new ItemStack(ModItems.LIGHT_SPELL_ITEM),
                null,
                new ArrayList<ItemStack>() {{
                    add(new ItemStack(Items.SLIME_BALL, 1, OreDictionary.WILDCARD_VALUE));
                    add(new ItemStack(Item.getItemFromBlock(Blocks.TORCH), 1, OreDictionary.WILDCARD_VALUE));
                }}
        );
    }


}
