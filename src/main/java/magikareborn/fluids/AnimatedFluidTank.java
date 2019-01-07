package magikareborn.fluids;

import magikareborn.network.FluidUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class AnimatedFluidTank extends FluidTank {

    private TileEntity _te;

    public AnimatedFluidTank(TileEntity te, Fluid fluid, int amount, int capacity) {
        super(fluid, amount, capacity);
        _te = te;
    }

    private void sendUpdate(int changeAmount) {
        if (changeAmount != 0) {
            World world = _te.getWorld();
            if (!world.isRemote) {
                BlockPos pos = _te.getPos();
                //System.out.println("Sending update to amount: " + this.getFluidAmount());
                //PacketHandler.INSTANCE.sendToAllAround(new FluidUpdatePacket(pos, this.getFluid()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getY(), 32));
                PacketHandler.sendToClients((WorldServer) world, pos, new FluidUpdatePacket(pos, this.getFluid()));
            }
        }
    }

    @Override
    public int fillInternal(FluidStack resource, boolean doFill) {
        //System.out.println("doFill: " + doFill);
        int amount = super.fillInternal(resource, doFill);
        if (amount > 0 && doFill) {
            sendUpdate(amount);
        }
        return amount;
    }

    @Override
    public FluidStack drainInternal(int maxDrain, boolean doDrain) {
        //System.out.println("doDrain: " + doDrain);
        FluidStack fluid = super.drainInternal(maxDrain, doDrain);
        if (fluid != null && doDrain) {
            sendUpdate(-fluid.amount);
        }
        return fluid;
    }
}
