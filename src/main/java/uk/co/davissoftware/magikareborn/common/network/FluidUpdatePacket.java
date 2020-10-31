package magikareborn.network;

import io.netty.buffer.ByteBuf;
import magikareborn.tileentities.ManaTankTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class FluidUpdatePacket implements IMessage {

    private BlockPos blockPos;
    private FluidStack fluidStack;

    @SuppressWarnings("unused")
    public FluidUpdatePacket() {
    }

    public FluidUpdatePacket(BlockPos blockPosIn, FluidStack fluidStackIn) {
        blockPos = blockPosIn;
        fluidStack = fluidStackIn;
        //System.out.println("ctor, fluidStack is null: " + (fluidStack == null));
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        int z = byteBuf.readInt();
        blockPos = new BlockPos(x, y, z);

        NBTTagCompound tag = ByteBufUtils.readTag(byteBuf);
        fluidStack = FluidStack.loadFluidStackFromNBT(tag);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(blockPos.getX());
        byteBuf.writeInt(blockPos.getY());
        byteBuf.writeInt(blockPos.getZ());

        NBTTagCompound tag = new NBTTagCompound();
        if (fluidStack != null) {
            fluidStack.writeToNBT(tag);
        }
        ByteBufUtils.writeTag(byteBuf, tag);
    }

    public static class FluidUpdatePacketHandler implements IMessageHandler<FluidUpdatePacket, IMessage> {
        @Override
        public IMessage onMessage(FluidUpdatePacket message, MessageContext ctx) {

            //System.out.println("Received message, fluidStack is null: " + (message.fluidStack == null));
            if (message.fluidStack != null) {
                System.out.println("Received message to set amount to: " + message.fluidStack.amount);
            }

            if (ctx.side == Side.CLIENT) {
                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.blockPos);
                if (te instanceof ManaTankTileEntity) {
                    ((ManaTankTileEntity) te).getFluidTank().setFluid(message.fluidStack);
                }
            }

            // No response packet
            return null;
        }
    }
}
