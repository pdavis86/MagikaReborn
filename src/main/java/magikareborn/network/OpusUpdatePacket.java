package magikareborn.network;

import io.netty.buffer.ByteBuf;
import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class OpusUpdatePacket implements IMessage {

    private IOpusCapability _capability;

    //Required
    public OpusUpdatePacket() {
    }

    public OpusUpdatePacket(IOpusCapability capability) {
        _capability = capability;
    }

    public IOpusCapability getCapability() {
        return  _capability;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        //buf.writeInt(_updateFlags);

        buf.writeInt(_capability.getSelectedTab());
        buf.writeInt(_capability.getMagicLevel());
        buf.writeFloat(_capability.getMagicXp());
        buf.writeFloat(_capability.getMana());

        /*if ((_updateFlags & FLAG_UPDATE_MANA) == FLAG_UPDATE_MANA) {
        }

        if ((_updateFlags & FLAG_UPDATE_UTILITY) == FLAG_UPDATE_UTILITY) {
        }*/
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        //_updateFlags = buf.readInt();

        _capability = new OpusCapability();
        _capability.setSelectedTab(buf.readInt());
        _capability.setMagicLevel(buf.readInt());
        _capability.setMagicXp(buf.readFloat());
        _capability.setMana(buf.readFloat());
    }

    public static class OpusPacketHandler implements IMessageHandler<OpusUpdatePacket, IMessage> {
        @Override
        public IMessage onMessage(OpusUpdatePacket message, MessageContext ctx) {
            //only access blocks and tile entities if world.isBlockLoaded(pos) is true.

            if (ctx.side == Side.SERVER) {
                EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
                serverPlayer.getServerWorld().addScheduledTask(() -> {
                    IOpusCapability opusCapability = serverPlayer.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                    opusCapability.cloneFrom(message.getCapability());
                });
            } else {
                EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;

                if (clientPlayer == null) {
                    //System.out.println("OpusUpdatePacket: Client player was NULL");
                    return null;
                }

                IOpusCapability opusCapability = clientPlayer.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                opusCapability.cloneFrom(message.getCapability());
            }

            // No response packet
            return null;
        }
    }
}
