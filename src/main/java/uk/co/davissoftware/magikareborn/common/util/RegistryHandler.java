package uk.co.davissoftware.magikareborn.common.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.blocks.LightSpellBlock;
import uk.co.davissoftware.magikareborn.common.blocks.SpellAltarBlock;
import uk.co.davissoftware.magikareborn.common.containers.SpellAltarContainer;
import uk.co.davissoftware.magikareborn.common.entities.LightSpellEntity;
import uk.co.davissoftware.magikareborn.common.helpers.BlockHelper;
import uk.co.davissoftware.magikareborn.common.items.LightSpellItem;

@SuppressWarnings("unused")
public class RegistryHandler {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModRoot.MODID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModRoot.MODID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ModRoot.MODID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ModRoot.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Items
    public static final RegistryObject<Item> LIGHTSPELL_ITEM = ITEMS.register("lightspellitem", LightSpellItem::new);

    //Blocks
    public static final RegistryObject<Block> SPELLALTAR_BLOCK = BLOCKS.register(SpellAltarBlock.NAME, SpellAltarBlock::new);
    public static final RegistryObject<Block> LIGHTSPELL_BLOCK = BLOCKS.register(LightSpellBlock.NAME, LightSpellBlock::new);

    //Block Items
    public static final RegistryObject<Item> SPELLALTAR_BLOCKITEM = ITEMS.register(
            SpellAltarBlock.NAME,
            () -> BlockHelper.GetBlockItem(SPELLALTAR_BLOCK.get()));

    //Entities
    public static final RegistryObject<EntityType<LightSpellEntity>> LIGHTSPELL_ENTITY = ENTITIES.register(
            "lightspellentity",
            () -> EntityType.Builder.<LightSpellEntity>create(LightSpellEntity::new, EntityClassification.MISC)
                    .setCustomClientFactory((spawnEntity, world) -> new LightSpellEntity(RegistryHandler.LIGHTSPELL_ENTITY.get(), world))
                    .build(ModRoot.MODID + ":" + "lightspellentity")
    );

    //Containers
    public static final RegistryObject<ContainerType<SpellAltarContainer>> SPELLALTAR_CONTAINER = CONTAINERS
            .register("spellaltar_container", () -> IForgeContainerType.create(SpellAltarContainer::new));

}
