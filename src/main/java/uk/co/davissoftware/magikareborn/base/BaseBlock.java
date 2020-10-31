package magikareborn.base;

import magikareborn.ModRoot;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

abstract public class BaseBlock extends Block {

    public BaseBlock(String name, Material material, CreativeTabs creativeTab) {
        super(material);
        String nameLower = name.toLowerCase();
        setRegistryName(nameLower);
        setUnlocalizedName(ModRoot.MODID.toLowerCase() + "." + nameLower);
        setCreativeTab(creativeTab);
    }

    public Item getNewItem() {
        return new ItemBlock(this).setRegistryName(getResourceLocation());
    }

    @SuppressWarnings("ConstantConditions")
    protected ResourceLocation getResourceLocation() {
        return getRegistryName();
    }
}
