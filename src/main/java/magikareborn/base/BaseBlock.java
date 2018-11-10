package magikareborn.base;

import magikareborn.ModRoot;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

abstract public class BaseBlock extends Block {

    public BaseBlock(String name, Material material, CreativeTabs creativeTab) {
        super(material);
        setRegistryName(name.toLowerCase());
        setUnlocalizedName(ModRoot.MODID.toLowerCase() + "." + name.toLowerCase());
        setCreativeTab(creativeTab);
    }

    /*public ResourceLocation getResourceLocation(){
        return this.getRegistryName();
    }*/

    public Item getNewItem() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }
}
