package uk.co.davissoftware.magikareborn.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.capabilities.IOpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapabilityStorage;

public class OpusRequestPacket implements ISimplePacket {

    //todo: repalce any instances of stdout

    public OpusRequestPacket() {
        //For creating a packet to send
    }

    @SuppressWarnings("unused")
    public OpusRequestPacket(PacketBuffer buffer) {
        //For when this packet has been received
    }

    @Override
    public void encode(PacketBuffer packetBuffer) {
        //Nothing here
    }

    @Override
    public void handleThreadsafe(NetworkEvent.Context context) {
        ServerPlayerEntity player = context.getSender();
        if (player == null) {
            ModRoot.LOGGER.warn("Player was null on receiving OpusRequestPacket");
            return;
        }

        IOpusCapability opusCapability = OpusCapability.getFromPlayer(player);
        if (opusCapability == null) {
            return;
        }

        PacketHandler.getInstance().sendToPlayer(new OpusUpdatePacket(opusCapability), player);
    }

}
