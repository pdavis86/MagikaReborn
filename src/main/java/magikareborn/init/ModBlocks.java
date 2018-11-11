package magikareborn.init;

import magikareborn.blocks.LightSpellBlock;
import magikareborn.blocks.ManaFluidBlock;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static final ManaFluidBlock MANA_FLUID_BLOCK = new ManaFluidBlock("ManaFluidBlock", ModFluids.MANA_FLUID, new MaterialLiquid(MapColor.ICE), CreativeTabs.MISC);
    public static  final LightSpellBlock LIGHT_SPELL_BLOCK = new LightSpellBlock();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        MANA_FLUID_BLOCK.initModel();
        LIGHT_SPELL_BLOCK.initModel();
    }
}
