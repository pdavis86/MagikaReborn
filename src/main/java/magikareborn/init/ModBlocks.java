package magikareborn.init;

import magikareborn.blocks.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static final DataBlock DATA_BLOCK = new DataBlock();
    public static final BlinkingBlock BLINKING_BLOCK = new BlinkingBlock();
    public static final PedestalBlock PEDESTAL_BLOCK = new PedestalBlock();
    public static final TinyChestBlock TINYCHEST_BLOCK = new TinyChestBlock();
    //public static final ManaFluidBlock MANA_FLUID_BLOCK = new ManaFluidBlock();
    //public static final LecternBlock LECTERN_BLOCK = new LecternBlock();

    public static final BlockFluidClassic MANA_FLUID_BLOCK = new BlockFluidClassic(ModFluids.MANA_FLUID, new MaterialLiquid(MapColor.ICE));


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        //MANA_FLUID_BLOCK.initModel();
        DATA_BLOCK.initModel();
        BLINKING_BLOCK.initModel();
        PEDESTAL_BLOCK.initModel();
        TINYCHEST_BLOCK.initModel();
    }
}
