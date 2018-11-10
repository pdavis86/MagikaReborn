package magikareborn.network;

import io.netty.buffer.ByteBuf;
import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class OpusUpdatePacket implements IMessage {

    //Flags
    public static final int FLAG_UPDATE_MANA = 0x1;
    public static final int FLAG_UPDATE_UTILITY = 0x2;

    public IOpusCapability Capability;

    private int _updateFlags;

    //Required
    public OpusUpdatePacket() {
    }

    public OpusUpdatePacket(IOpusCapability capability) {
        Capability = capability;
    }

    public int addUpdateFlag(int flag) {
        _updateFlags = _updateFlags & flag;
        return _updateFlags;
    }

    public int removeUpdateFlags(int flag) {
        _updateFlags = _updateFlags ^ flag;
        return _updateFlags;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(_updateFlags);
        buf.writeInt(Capability.getSelectedTab());

        if ((_updateFlags & FLAG_UPDATE_MANA) == FLAG_UPDATE_MANA) {
            //todo:
        }

        if ((_updateFlags & FLAG_UPDATE_UTILITY) == FLAG_UPDATE_UTILITY) {
            //todo:
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        _updateFlags = buf.readInt();

        Capability = new OpusCapability();
        Capability.setSelectedTab(buf.readInt());
    }

    public static class OpusPacketHandler implements IMessageHandler<OpusUpdatePacket, IMessage> {
        @Override
        public IMessage onMessage(OpusUpdatePacket message, MessageContext ctx) {
            //only access blocks and tile entities if world.isBlockLoaded(pos) is true.

            if (ctx.side == Side.SERVER) {
                //EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
                /*serverPlayer.sendMessage(new TextComponentString("Server received message from client"));*/

                //serverPlayer.getServerWorld().addScheduledTask(() -> {
                //    do stuff
                //});
            } else {

                EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;

                //clientPlayer.sendMessage(new TextComponentString("Client received message from server"));
                System.out.println("Client received OpusUpdatePacket from server");

                if (clientPlayer == null) {
                    System.out.println("Client player was NULL");
                    return null;
                }

                //clientPlayer.sendMessage(new TextComponentString("Loading Opus data"));
                System.out.println("Loading Opus data");

                IOpusCapability opusCapability = clientPlayer.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                opusCapability.cloneFrom(message.Capability);
            }

            // No response packet
            return null;
        }
    }
}
