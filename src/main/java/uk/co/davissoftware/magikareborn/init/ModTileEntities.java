package uk.co.davissoftware.magikareborn.init;

import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.tileentities.SpellAltarTileEntity;
import uk.co.davissoftware.magikareborn.common.util.RegistryHandler;

public class ModTileEntities {

    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModRoot.MODID);

    public static void init() {
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static Type<?> getTileEntityType(String name) {
        return net.minecraft.util.Util.func_240976_a_(TypeReferences.BLOCK_ENTITY, ModRoot.MODID + ":" + name);
    }


    //todo: take another look at this after reading https://mcforge.readthedocs.io/en/1.16.x/tileentities/tileentity/
    public static final RegistryObject<TileEntityType<SpellAltarTileEntity>> SPELLALTAR_TILEENTITY = TILE_ENTITIES.register(
            "spellaltar_tileentity",
            () -> TileEntityType.Builder.create(
                    SpellAltarTileEntity::new,
                    new Block[]{RegistryHandler.SPELLALTAR_BLOCK.get()})
                    .build(getTileEntityType("spellaltar_tileentity")));
}





























