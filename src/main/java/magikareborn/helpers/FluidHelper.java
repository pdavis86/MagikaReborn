package magikareborn.helpers;

import magikareborn.init.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fluids.capability.wrappers.BlockWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;

import javax.annotation.Nullable;

public class FluidHelper {

    @Nullable
    private static FluidStack getMatchingFluidStack(IFluidHandler fluidHandler, Fluid fluid) {
        // Theoretically a tank may contain multiple fluid stacks so grab first one that matches fluid type
        IFluidTankProperties[] tankProperties = fluidHandler.getTankProperties();
        FluidStack result = null;
        for (IFluidTankProperties tankProperty : tankProperties) {
            if (tankProperty.getContents() != null && tankProperty.getContents().getFluid() == fluid) {
                result = tankProperty.getContents();
            }
        }
        return result;
    }

    @Nullable
    public static FluidStack getFluidStack(ItemStack itemStack) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey(FluidHandlerItemStack.FLUID_NBT_KEY)) {
            return FluidStack.loadFluidStackFromNBT(itemStack.getTagCompound().getCompoundTag(FluidHandlerItemStack.FLUID_NBT_KEY));
        }
        return null;
    }

    public static ActionResult<ItemStack> tryPlaceAt(World world, EntityPlayer player, BlockPos blockPos, ItemStack itemStack) {

        if (world == null || blockPos == null) {
            System.out.println("not valid location to place at");
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(itemStack);

        if (containerFluidHandler == null) {
            System.out.println("Item stack not really a fluid handling container");
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        FluidStack containerFluidStack = getFluidStack(itemStack);

        if (containerFluidStack == null || containerFluidStack.amount <= 0) {
            System.out.println("No actual fluid in container");
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        Fluid fluid = containerFluidStack.getFluid();

        if (fluid == null) {
            System.out.println("Malformed fluid stack has null fluid");
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        if (!fluid.canBePlacedInWorld()) {
            System.out.println("Fluid type doesn't allow placement in world");
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        IBlockState destBlockState = world.getBlockState(blockPos);

        if (!world.isAirBlock(blockPos) && destBlockState.getMaterial().isSolid() && !destBlockState.getBlock().isReplaceable(world, blockPos)) {
            System.out.println("Location is not replaceable");
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        if (world.provider.doesWaterVaporize() && fluid.doesVaporize(containerFluidStack)) {
            fluid.vaporize(player, world, blockPos, containerFluidStack);
            return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
        }

        Block blockToPlace = fluid.getBlock();

        IFluidHandler blockFluidHandler;
        if (blockToPlace instanceof IFluidBlock) {
            blockFluidHandler = new FluidBlockWrapper((IFluidBlock) blockToPlace, world, blockPos);

        } else if (blockToPlace instanceof BlockLiquid) {
            blockFluidHandler = new BlockLiquidWrapper((BlockLiquid) blockToPlace, world, blockPos);

        } else {
            blockFluidHandler = new BlockWrapper(blockToPlace, world, blockPos);
        }

        int blockCapacity = blockFluidHandler.getTankProperties()[0].getCapacity();
        int amountInContainer = containerFluidStack.amount;

        FluidStack blockFluidStack = blockFluidHandler.getTankProperties()[0].getContents();
        if (blockFluidStack == null) {
            System.out.println("Block fluid stack is null");
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        System.out.println("Before transferring fluids amount in container = " + amountInContainer + " and block capacity = " + blockCapacity);

        if (amountInContainer > blockCapacity) {
            containerFluidStack.amount -= blockCapacity;
            blockFluidStack.amount = blockCapacity;

        } else {
            blockFluidStack.amount = amountInContainer;
            containerFluidStack.amount = 0;
        }

        System.out.println("After transferring amount in container = " + containerFluidStack.amount + " and amount in block = " + blockFluidStack.amount);

        SoundEvent soundevent = fluid.getEmptySound(containerFluidStack);
        world.playSound(player, blockPos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (player.capabilities.isCreativeMode) {
            containerFluidStack.amount = amountInContainer;
            world.setBlockState(blockPos, blockToPlace.getDefaultState());
            System.out.println("Placing fluid was a success");
            return new ActionResult<>(EnumActionResult.PASS, itemStack);
        }

        if (containerFluidStack.amount <= 0) {
            containerFluidStack.amount = 0;
            System.out.println("fully drained the container so returning empty container");
        }

        updateFluidNBT(itemStack, containerFluidStack);

        sendUpdatePacketToClient(player);

        System.out.println("After transfer block fluid stack = " + blockFluidStack.getFluid().getName() + " " + blockFluidStack.amount +
                " and container fluid stack now = " + containerFluidStack.getFluid().getName() + " " + containerFluidStack.amount);

        world.setBlockState(blockPos, blockToPlace.getDefaultState());

        System.out.println("Placing fluid was a success");

        return ActionResult.newResult(EnumActionResult.SUCCESS, containerFluidHandler.getContainer());
    }


    private static void sendUpdatePacketToClient(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            //System.out.println("Sending player inventory update");
            ((EntityPlayerMP) player).connection.sendPacket(new SPacketHeldItemChange(player.inventory.currentItem));
        }
    }

    private static void updateFluidNBT(ItemStack itemStack, FluidStack fluidStack) {
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound fluidTag = new NBTTagCompound();
        fluidStack.writeToNBT(fluidTag);

        NBTTagCompound tc = itemStack.getTagCompound();
        if (tc == null) {
            return;
        }

        tc.setTag(FluidHandlerItemStack.FLUID_NBT_KEY, fluidTag);
        System.out.println("Wrote fluid tag to container item stack = " + fluidTag);
    }

    public static ActionResult<ItemStack> tryFillAt(World world, EntityPlayer player, RayTraceResult mop, ItemStack itemStack, int capacity) {

        if (world == null || itemStack.isEmpty()) {
            System.out.println("invalid parameters (null or empty");
            return new ActionResult<>(EnumActionResult.PASS, itemStack);
        }

        BlockPos blockPos = mop.getBlockPos();
        Block block = world.getBlockState(blockPos).getBlock();

        if (!(block instanceof IFluidBlock || block instanceof BlockLiquid)) {
            System.out.println("Not a fluid block in that location");
            return new ActionResult<>(EnumActionResult.PASS, itemStack);
        }

        IFluidHandler sourceFluidHandler = FluidUtil.getFluidHandler(world, blockPos, mop.sideHit);
        if (sourceFluidHandler == null) {
            System.out.println("Malformed fluid block at position = " + blockPos);
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(itemStack);
        if (containerFluidHandler == null) {
            System.out.println("Malformed fluid item at position " + itemStack);
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
        }

        FluidStack containerFluidStack = getFluidStack(itemStack);

        // convert null fluid stack to empty amount = 0
        if (containerFluidStack == null) {
            containerFluidStack = new FluidStack(ModFluids.MANA_FLUID, 0);
        }

        int amountRoomInContainer = capacity - containerFluidStack.amount;

        if (amountRoomInContainer <= 0) {
            System.out.println("No room in container, already full");
            return new ActionResult<>(EnumActionResult.PASS, itemStack);
        }

        FluidStack sourceFluidStack = getMatchingFluidStack(sourceFluidHandler, ModFluids.MANA_FLUID);

        if (sourceFluidStack == null) {
            System.out.println("No matching fluid in block");
            return new ActionResult<>(EnumActionResult.PASS, itemStack);
        }

        if (sourceFluidStack.amount <= 0) {
            System.out.println("Not enough fluid in source");
            return new ActionResult<>(EnumActionResult.PASS, itemStack);
        }

        System.out.println("Before transfer source fluid stack = " + sourceFluidStack.getFluid().getName() + " " + sourceFluidStack.amount +
                " and container fluid stack now = " + containerFluidStack.getFluid().getName() + " " + containerFluidStack.amount);

        if (sourceFluidStack.amount > amountRoomInContainer) // some to spare
        {
            containerFluidStack.amount = capacity;
            sourceFluidStack.amount -= amountRoomInContainer;

        } else {
            containerFluidStack.amount = sourceFluidStack.amount;
            sourceFluidStack.amount = 0;
            world.setBlockToAir(blockPos);
        }

        System.out.println("After transfer source fluid stack = " + sourceFluidStack.getFluid().getName() + " " + sourceFluidStack.amount +
                " and container fluid stack now = " + containerFluidStack.getFluid().getName() + " " + containerFluidStack.amount);

        SoundEvent soundevent = containerFluidStack.getFluid().getFillSound(containerFluidStack);
        player.playSound(soundevent, 1f, 1f);

        updateFluidNBT(itemStack, containerFluidStack);

        sendUpdatePacketToClient(player);

        return ActionResult.newResult(EnumActionResult.SUCCESS, containerFluidHandler.getContainer());
    }

}
