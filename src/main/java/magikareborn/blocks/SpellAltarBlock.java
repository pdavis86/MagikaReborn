package magikareborn.blocks;

import magikareborn.base.BaseBlock;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpellAltarBlock extends BaseBlock {

    public SpellAltarBlock() {
        super("SpellAltarBlock", Material.WOOD, ModItems.MAGIKA_REBORN_CREATIVE_TAB);
        this.setSoundType(SoundType.WOOD);
        setHardness(1.5f);
        //todo: does this do anything? - setHarvestLevel(ToolHelper.TOOLCLASS_AXE, 1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), ResourceHelper.VARIANT_INVENTORY));
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        playerIn.sendMessage(new TextComponentString("You left clicked me!"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            /*TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityHopper)
            {
                playerIn.displayGUIChest((TileEntityHopper)tileentity);
                playerIn.addStat(StatList.HOPPER_INSPECTED);
            }*/

            //player.inventory.mainInventory.set(player.inventory.currentItem, rest);

            /*ItemStack heldItem = playerIn.getHeldItem(hand);
            IItemHandler playerInventory = playerIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            FluidActionResult result = FluidUtil.tryEmptyContainerAndStow(heldItem, fluidHandler, playerInventory, Fluid.BUCKET_VOLUME, playerIn);
            if(result.isSuccess()) {
                playerIn.setHeldItem(hand, result.getResult());
                return true;
            }*/

            playerIn.sendMessage(new TextComponentString("You clicked me with " + hand));

            return true;
        }
    }
}
