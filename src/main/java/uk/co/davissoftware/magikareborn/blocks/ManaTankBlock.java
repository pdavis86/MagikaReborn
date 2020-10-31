package magikareborn.blocks;

import magikareborn.ModRoot;
import magikareborn.base.BaseBlock;
import magikareborn.helpers.ResourceHelper;
import magikareborn.renderers.tileEntitySpecialRenderers.ManaTankTileEntitySpecialRenderer;
import magikareborn.tileentities.ManaTankTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class ManaTankBlock extends BaseBlock implements ITileEntityProvider {

    //todo: set lightlevel based on mana mb   tank.getFluid().getFluid().getLuminosity() / capacity

    public ManaTankBlock() {
        super("ManaTankBlock", Material.GLASS, ModRoot.MAGIKA_REBORN_CREATIVE_TAB);
        setHardness(0.1f);
    }

    @SuppressWarnings("ConstantConditions")
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), ResourceHelper.INVENTORY_VARIANT));
        ClientRegistry.bindTileEntitySpecialRenderer(ManaTankTileEntity.class, new ManaTankTileEntitySpecialRenderer());
    }

    // used by the renderer to control lighting and visibility of other blocks.
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    // used by the renderer to control lighting and visibility of other blocks
    // also by (eg) wall or fence to control whether the fence joins itself to this block
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new ManaTankTileEntity();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te == null || !te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
            return false;
        }

        IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
        if (fluidHandler != null && FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler)) {
            return true; // return true as we did something
        }

        // prevent interaction so stuff like buckets and other things don't place the liquid block
        return FluidUtil.getFluidHandler(playerIn.getHeldItem(hand)) != null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ManaTankTileEntity && stack != null && stack.hasTagCompound()) {
            ((ManaTankTileEntity) te).readTankFromNBT(stack.getTagCompound());
        }
    }

    @Override
    public boolean removedByPlayer(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {

        // Change order of these call so we have the tile entity when getting the drops
        this.onBlockDestroyedByPlayer(world, pos, state);
        if (willHarvest) {
            this.harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getHeldItemMainhand());
        }

        world.setBlockToAir(pos);

        // return false to prevent the above called functions to be called again but note that no xp will be dropped
        return false;
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {

        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        Item item = this.getItemDropped(state, rand, fortune);

        if (item == Items.AIR) {
            return;
        }

        // save liquid data on the stack
        TileEntity te = world.getTileEntity(pos);
        ItemStack stack = new ItemStack(item, 1, this.damageDropped(state));
        if (te instanceof ManaTankTileEntity && !stack.isEmpty()) {
            if (((ManaTankTileEntity) te).containsFluid()) {
                NBTTagCompound tag = new NBTTagCompound();
                ((ManaTankTileEntity) te).writeTankToNBT(tag);
                stack.setTagCompound(tag);
            }
        }

        drops.add(stack);
    }

}
