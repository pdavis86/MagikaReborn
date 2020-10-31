package uk.co.davissoftware.magikareborn.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface ISimplePacket {
    void encode(PacketBuffer packetBuffer);

    default void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            this.handleThreadsafe(context);
        });
        context.setPacketHandled(true);
    }

    //NOTE: a general rule of thumb is to only access blocks and tile entities if world.isBlockLoaded(pos) is true.
    void handleThreadsafe(NetworkEvent.Context context);
}
