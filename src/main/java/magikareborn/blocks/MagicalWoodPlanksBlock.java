package magikareborn.blocks;

import magikareborn.base.BaseBlock;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagicalWoodPlanksBlock extends BaseBlock {

    public MagicalWoodPlanksBlock() {
        super("MagicalWoodPlanksBlock", Material.WOOD, ModItems.MAGIKA_REBORN_CREATIVE_TAB);
        this.setSoundType(SoundType.WOOD);
        setHardness(1.5f);
        //todo: does this do anything? - setHarvestLevel(ToolHelper.TOOLCLASS_AXE, 1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), ResourceHelper.VARIANT_INVENTORY));
    }
}
