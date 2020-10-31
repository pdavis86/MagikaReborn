package uk.co.davissoftware.magikareborn.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import uk.co.davissoftware.magikareborn.common.helpers.ToolHelper;

public class SpellAltarBlock extends BlockBase {

    public static final String NAME = "spellaltarblock";

    public SpellAltarBlock() {
        super(Block.Properties
                .create(Material.WOOD)
                .hardnessAndResistance(1.5f, 8.0f)
                //.slipperiness(0.8F)
                .harvestTool(ToolType.AXE)
                .harvestLevel(ToolHelper.HARVESTLEVEL_WOOD)
                .sound(SoundType.WOOD)
        );
    }

//    @SideOnly(Side.CLIENT)
//    public void initModel() {
//        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getResourceLocation(), ResourceHelper.INVENTORY_VARIANT));
//    }
//
//    @Override
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//
//        if (worldIn.isRemote) {
//            return true;
//        }
//
//        playerIn.openGui(ModRoot.instance, SpellAltarGuiContainer.GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
//        return new SpellAltarTileEntity();
//    }
}
