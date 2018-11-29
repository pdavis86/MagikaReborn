package magikareborn.items;

import magikareborn.ModRoot;
import magikareborn.base.BaseItem;
import magikareborn.fluids.ManaTankFluidHandler;
import magikareborn.helpers.FluidHelper;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModFluids;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.DispenseFluidContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaTankItem extends BaseItem {

    private final int CAPACITY = Fluid.BUCKET_VOLUME * 4;
    private final ItemStack EMPTY_STACK = new ItemStack(this);

    public ManaTankItem() {
        super("ManaTankItem", ModRoot.MAGIKA_REBORN_CREATIVE_TAB);
        setMaxStackSize(1);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DispenseFluidContainer.getInstance());
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResource(this));
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ManaTankFluidHandler(stack, CAPACITY);
    }

    //If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        //System.out.println("tag compound = " + stack.getTagCompound());
        return stack.getTagCompound();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(@Nullable final CreativeTabs tab, @Nonnull final NonNullList subItems) {
        if (!this.isInCreativeTab(tab)) return;

        subItems.add(EMPTY_STACK);

        final ItemStack stack = new ItemStack(this);
        final IFluidHandlerItem fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (fluidHandler == null) {
            return;
        }

        final FluidStack fluidStack = new FluidStack(ModFluids.MANA_FLUID, CAPACITY);

        final int fluidFillAmount = fluidHandler.fill(fluidStack, true);
        if (fluidFillAmount != fluidStack.amount) {
            return;
        }

        subItems.add(fluidHandler.getContainer());
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(@Nonnull final ItemStack stack) {
        String unlocalizedName = this.getUnlocalizedNameInefficiently(stack);
        IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(stack);
        if (fluidHandler == null) {
            return unlocalizedName;
        }
        IFluidTankProperties[] props = fluidHandler.getTankProperties();
        FluidStack fluidStack = props[0].getContents();
        if (fluidStack == null || fluidStack.amount <= 0) {
            unlocalizedName += ".empty.name";
        } else {
            unlocalizedName += ".name";
        }

        return new TextComponentTranslation(unlocalizedName).getUnformattedText().trim();
    }

    /*@Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {

        ItemStack heldItemStack = playerIn.getHeldItem(handIn);

        RayTraceResult rayTraceResult = rayTrace(worldIn, playerIn, true);

        if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return ActionResult.newResult(EnumActionResult.PASS, heldItemStack);
        }

        FluidStack fluidStack = FluidHelper.getFluidStack(heldItemStack);

        if (fluidStack == null || fluidStack.amount <= 0) {
            System.out.println("Fluid stack in item is empty so try to fill");
            return FluidHelper.tryFillAt(worldIn, playerIn, rayTraceResult, heldItemStack, CAPACITY);
        }

        BlockPos clickPos = rayTraceResult.getBlockPos();

        ActionResult<ItemStack> returnResult;
        if (!worldIn.isBlockModifiable(playerIn, clickPos)) {
            //System.out.println("Failed to place fluid because location not modifiable");
            returnResult = ActionResult.newResult(EnumActionResult.FAIL, heldItemStack);

        } else {
            BlockPos targetPos = clickPos.offset(rayTraceResult.sideHit);

            //System.out.println("Click pos: " + clickPos.getX() + " " + clickPos.getY() + " " + clickPos.getZ());
            //System.out.println("Target pos: " + targetPos.getX() + " " + targetPos.getY() + " " + targetPos.getZ());

            if (!playerIn.canPlayerEdit(targetPos, rayTraceResult.sideHit, heldItemStack)) {
                //System.out.println("Failed to place fluid because player cannot edit");
                return ActionResult.newResult(EnumActionResult.FAIL, heldItemStack);
            }

            System.out.println("Fluid stack is not empty so try to place");
            returnResult = FluidHelper.tryPlaceAt(worldIn, playerIn, targetPos, heldItemStack);
        }

        if (returnResult.getType() == EnumActionResult.SUCCESS) {
            StatBase thisItemUseStats = StatList.getObjectUseStats(this);
            if (thisItemUseStats != null) {
                playerIn.addStat(thisItemUseStats);
            }
        }

        return returnResult;
    }*/

}