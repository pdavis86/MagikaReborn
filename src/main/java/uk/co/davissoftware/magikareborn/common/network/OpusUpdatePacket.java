package uk.co.davissoftware.magikareborn.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.capabilities.IOpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapabilityStorage;

public class OpusUpdatePacket implements ISimplePacket {

    private final IOpusCapability _capability;

    public OpusUpdatePacket(IOpusCapability capability) {
        //For creating a packet to send
        _capability = capability;
    }

    @SuppressWarnings("unused")
    public OpusUpdatePacket(PacketBuffer packetBuffer) {
        //For when this packet has been received
        _capability = new OpusCapability();
        _capability.setSelectedTab(packetBuffer.readInt());
        _capability.setMagicLevel(packetBuffer.readInt());
        _capability.setXp(packetBuffer.readFloat());
        _capability.setMana(packetBuffer.readFloat());
    }

    @Override
    public void encode(PacketBuffer packetBuffer) {
        packetBuffer.writeInt(_capability.getSelectedTab());
        packetBuffer.writeInt(_capability.getMagicLevel());
        packetBuffer.writeFloat(_capability.getXp());
        packetBuffer.writeFloat(_capability.getMana());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void handleThreadsafe(NetworkEvent.Context context) {
        //NOTE: only access blocks and tile entities if world.isBlockLoaded(pos) is true.

        ModRoot.LOGGER.warn("Received OpusUpdatePacket");

        PlayerEntity player = context.getSender();
        if (player == null) {
            //Message was sent by the server so grab the client-side player
            player = Minecraft.getInstance().player;

            if (player == null) {
                ModRoot.LOGGER.warn("Player was null on receiving OpusUpdatePacket");
                return;
            }
        }

        IOpusCapability opusCapability = player.getCapability(OpusCapabilityStorage.CAPABILITY, null).orElse(null);
        if (opusCapability == null) {
            OpusCapability.logNullWarning(player.getName().getString());
            return;
        }

        opusCapability.fillFrom(this._capability, player instanceof ClientPlayerEntity);
    }
}
