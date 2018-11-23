package magikareborn.blocks;

import magikareborn.ModRoot;
import magikareborn.base.BaseBlock;
import magikareborn.gui.SpellAltarGuiContainer;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModItems;
import magikareborn.tileentities.SpellAltarTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class SpellAltarBlock extends BaseBlock implements ITileEntityProvider {

    public SpellAltarBlock() {
        super("SpellAltarBlock", Material.WOOD, ModItems.MAGIKA_REBORN_CREATIVE_TAB);
        this.setSoundType(SoundType.WOOD);
        setLightLevel(16 / 16f);
        setHardness(1.5f);
        //todo: does this do anything? - setHarvestLevel(ToolHelper.TOOLCLASS_AXE, 1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), ResourceHelper.INVENTORY_VARIANT));
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        playerIn.sendMessage(new TextComponentString("You left clicked me!"));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (worldIn.isRemote) {
            return true;
        }

        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof SpellAltarTileEntity)) {
            return false;
        }

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

        //playerIn.sendMessage(new TextComponentString("You clicked me with " + hand));

        playerIn.openGui(ModRoot.instance, SpellAltarGuiContainer.GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new SpellAltarTileEntity();
    }
}
