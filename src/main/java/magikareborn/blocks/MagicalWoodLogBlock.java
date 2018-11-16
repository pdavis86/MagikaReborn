package magikareborn.blocks;

import magikareborn.ModRoot;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModItems;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagicalWoodLogBlock extends BlockLog {

    public MagicalWoodLogBlock() {
        super();
        String name = "MagicalWoodLogBlock";
        setRegistryName(name.toLowerCase());
        setUnlocalizedName(ModRoot.MODID.toLowerCase() + "." + name.toLowerCase());
        setCreativeTab(ModItems.MAGIKA_REBORN_CREATIVE_TAB);
        setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), ResourceHelper.VARIANT_INVENTORY));
    }

    public Item getNewItem() {
        return new ItemBlock(this) {
            @Override
            public int getItemBurnTime(ItemStack itemStack) {
                return 300; // same value as vanilla log
            }
        }.setRegistryName(getRegistryName());
    }

    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();

        switch (meta & 0b1100) {
            case 0b0000:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;

            case 0b0100:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;

            case 0b1000:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;

            case 0b1100:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
                break;
        }

        return state;
    }

    public int getMetaFromState(IBlockState state) {
        switch ((BlockLog.EnumAxis) state.getValue(LOG_AXIS)) {
            case X:
                return 0b0100;
            case Y:
                return 0b0000;
            case Z:
                return 0b1000;
            default:
            case NONE:
                return 0b1100;
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{LOG_AXIS});
    }

}