package magikareborn.base;

import magikareborn.ModRoot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

abstract public class BaseItem extends Item {

    public BaseItem(String name, CreativeTabs creativeTab) {
        setRegistryName(name.toLowerCase());
        setUnlocalizedName(ModRoot.MODID + "." + name.toLowerCase());
        if (creativeTab != null) {
            setCreativeTab(creativeTab);
        }
    }
}
