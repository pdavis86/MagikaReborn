package magikareborn.init;

import magikareborn.blocks.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static final DataBlock DATA_BLOCK = new DataBlock();
    //public static final ManaFluidBlock MANA_FLUID_BLOCK = new ManaFluidBlock();
    public static final LecternBlock LECTERN_BLOCK = new LecternBlock();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        DATA_BLOCK.initModel();
    }
}
