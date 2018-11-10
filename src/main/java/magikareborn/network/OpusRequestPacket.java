package magikareborn.network;

import io.netty.buffer.ByteBuf;
import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class OpusRequestPacket implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class OpusRequestPacketHandler implements IMessageHandler<OpusRequestPacket, IMessage> {
        @Override
        public IMessage onMessage(OpusRequestPacket message, MessageContext ctx) {

            if (ctx.side == Side.SERVER) {
                EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

                //todo: comment out
                //serverPlayer.sendMessage(new TextComponentString("Client requested Opus data"));
                System.out.println("Server sending requested Opus data");

                IOpusCapability opusCapability = serverPlayer.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                OpusUpdatePacket replyMessage = new OpusUpdatePacket(opusCapability);
                return replyMessage;
            }

            // No response packet
            return null;
        }
    }
}
