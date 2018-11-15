package magikareborn.init;

import magikareborn.blocks.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static final ManaFluidBlock MANA_FLUID_BLOCK = new ManaFluidBlock("ManaFluidBlock", ModFluids.MANA_FLUID, new MaterialLiquid(MapColor.ICE), ModItems.MAGIKA_REBORN_CREATIVE_TAB);
    public static final LightSpellBlock LIGHT_SPELL_BLOCK = new LightSpellBlock();
    public static final SpellAltarBlock SPELL_ALTAR_BLOCK = new SpellAltarBlock();
    public static  final MagicalWoodLogBlock MAGICAL_LOG_BLOCK = new MagicalWoodLogBlock();
    public static  final MagicalWoodPlanksBlock MAGICAL_PLANKS_BLOCK = new MagicalWoodPlanksBlock();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        MANA_FLUID_BLOCK.initModel();
        LIGHT_SPELL_BLOCK.initModel();
        SPELL_ALTAR_BLOCK.initModel();
        MAGICAL_LOG_BLOCK.initModel();
        MAGICAL_PLANKS_BLOCK.initModel();
    }
}
