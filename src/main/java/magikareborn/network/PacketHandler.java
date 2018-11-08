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

    public static final int OPUS_PACKET_ID = 0;

    public static void registerPacketTypes() {
        //The side is the RECEIVING side. If not registered, the packet will be dropped.

        //int discriminator = 0;
        INSTANCE.registerMessage(OpusPacket.OpusPacketHandler.class, OpusPacket.class, OPUS_PACKET_ID, Side.SERVER);
        INSTANCE.registerMessage(OpusPacket.OpusPacketHandler.class, OpusPacket.class, OPUS_PACKET_ID, Side.CLIENT);
    }
}
