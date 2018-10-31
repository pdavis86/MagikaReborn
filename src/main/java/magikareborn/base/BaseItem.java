package magikareborn.base;

import magikareborn.ModRoot;
import net.minecraft.item.Item;

abstract public class BaseItem extends Item {

    public BaseItem(String name){
        setRegistryName(name.toLowerCase());
        setUnlocalizedName(ModRoot.MODID + "." + name.toLowerCase());
    }
}
