package magikareborn.network;

import magikareborn.ModRoot;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModRoot.MODID);

    //Usage:
    //Client to server: INSTANCE.sendToServer(new MyMessage(toSend))
    //Server to client: INSTANCE.sendTo / INSTANCE.sendToAllAround / INSTANCE.sendToDimension / INSTANCE.sendToAll

    public static final int OPUS_REQUEST_PACKET_ID = 0;
    public static final int OPUS_UPDATE_PACKET_ID = 1;

    public static void registerPacketTypes() {
        //The side is the RECEIVING side. If not registered, the packet will be dropped.

        //int discriminator = 0;
        INSTANCE.registerMessage(OpusRequestPacket.OpusRequestPacketHandler.class, OpusRequestPacket.class, OPUS_REQUEST_PACKET_ID, Side.SERVER);
        INSTANCE.registerMessage(OpusUpdatePacket.OpusPacketHandler.class, OpusUpdatePacket.class, OPUS_UPDATE_PACKET_ID, Side.SERVER);
        INSTANCE.registerMessage(OpusUpdatePacket.OpusPacketHandler.class, OpusUpdatePacket.class, OPUS_UPDATE_PACKET_ID, Side.CLIENT);
    }
}
