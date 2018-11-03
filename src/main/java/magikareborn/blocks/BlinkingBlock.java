package magikareborn.blocks;

import magikareborn.base.BaseBlock;
import magikareborn.helpers.ResourceHelper;
import magikareborn.tileentities.BlinkingTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class BlinkingBlock extends BaseBlock implements ITileEntityProvider {

    public static final PropertyBool LIT = PropertyBool.create("lit");

    public BlinkingBlock() {
        super("BlinkingBlock", Material.ROCK, CreativeTabs.MISC);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), ResourceHelper.VARIANT_INVENTORY));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return super.getLightOpacity(state, world, pos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new BlinkingTileEntity();
    }

    /*@Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new BlinkingTileEntity();
    }*/

    private BlinkingTileEntity getTE(IBlockAccess world, BlockPos pos) {
        return (BlinkingTileEntity) world.getTileEntity(pos);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.withProperty(LIT, getTE(world, pos).isLit());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer (this, LIT);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}
