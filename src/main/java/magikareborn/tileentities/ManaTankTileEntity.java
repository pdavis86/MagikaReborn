package magikareborn.tileentities;

import magikareborn.fluids.AnimatedFluidTank;
import magikareborn.init.ModFluids;
import magikareborn.network.FluidUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;

public class ManaTankTileEntity extends TileEntity {

    private static final int CAPACITY = Fluid.BUCKET_VOLUME * 4;

    private AnimatedFluidTank fluidTank;

    public ManaTankTileEntity() {
        fluidTank = new AnimatedFluidTank(this, ModFluids.MANA_FLUID, 0, CAPACITY);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        //System.out.println("readFromNBT");
        super.readFromNBT(compound);
        readTankFromNBT(compound);
    }

    public void readTankFromNBT(NBTTagCompound compound) {
        fluidTank.readFromNBT(compound);

        //todo: Fix buf where renderer does not start rendering until an update is made
        System.out.println("readTankFromNBT getFluidAmount: " + fluidTank.getFluidAmount());
        //fluidTank.sendUpdate(fluidTank.getFluidAmount());
        //PacketHandler.sendToClients((WorldServer) world, pos, new FluidUpdatePacket(pos, fluidTank.getFluid()));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        //System.out.println("writeToNBT");
        NBTTagCompound tags = super.writeToNBT(compound);
        writeTankToNBT(compound);
        return tags;
    }

    public void writeTankToNBT(NBTTagCompound compound) {
        fluidTank.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {

        //System.out.println("hasCapability called with facing " + facing);

        //facing = facing != null ? facing : EnumFacing.NORTH;

        //System.out.println("hasCapability changed facing to " + facing);

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ) { //&& facing != null
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {

        //System.out.println("getCapability called with facing " + facing);

        //facing = facing != null ? facing : EnumFacing.NORTH;

        //System.out.println("getCapability changed facing to " + facing);

        //System.out.println("fluidTank fluidStack: " + fluidTank.getFluid().getFluid().getName());

        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ) { //&& facing != null
            return (T) fluidTank;
        }
        return super.getCapability(capability, facing);
    }

    /*public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }*/

    /*@Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public GuiContainer getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }*/

    public boolean containsFluid() {
        return fluidTank.getFluid() != null;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }
}
