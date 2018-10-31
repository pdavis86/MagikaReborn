package magikareborn.blocks;

import magikareborn.base.BaseBlock;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class LecternBlock extends BaseBlock {

    public LecternBlock() {
        super("LecternBlock", Material.WOOD, CreativeTabs.MISC); //todo: change tab
        setHardness(2.0f);
        setResistance(2.0f);
    }
}
