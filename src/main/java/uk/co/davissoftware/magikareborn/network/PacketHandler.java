package magikareborn.network;

import magikareborn.ModRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModRoot.MODID);

    //Usage:
    //Client to server: INSTANCE.sendToServer(new MyMessage(toSend))
    //Server to client: INSTANCE.sendTo / INSTANCE.sendToAllAround / INSTANCE.sendToDimension / INSTANCE.sendToAll

    private static final int OPUS_REQUEST_PACKET_ID = 0;
    private static final int OPUS_UPDATE_PACKET_ID = 1;
    private static final int FLUID_UPDATE_PACKET_ID = 2;

    public static void registerPacketTypes() {
        //The side is the RECEIVING side. If not registered, the packet will be dropped.
        INSTANCE.registerMessage(OpusRequestPacket.OpusRequestPacketHandler.class, OpusRequestPacket.class, OPUS_REQUEST_PACKET_ID, Side.SERVER);
        INSTANCE.registerMessage(OpusUpdatePacket.OpusPacketHandler.class, OpusUpdatePacket.class, OPUS_UPDATE_PACKET_ID, Side.SERVER);
        INSTANCE.registerMessage(OpusUpdatePacket.OpusPacketHandler.class, OpusUpdatePacket.class, OPUS_UPDATE_PACKET_ID, Side.CLIENT);
        INSTANCE.registerMessage(FluidUpdatePacket.FluidUpdatePacketHandler.class, FluidUpdatePacket.class, FLUID_UPDATE_PACKET_ID, Side.CLIENT);
    }

    public static void sendToClients(WorldServer world, BlockPos pos, IMessage packet) {
        Chunk chunk = world.getChunkFromBlockCoords(pos);
        for (EntityPlayer player : world.playerEntities) {
            if (!(player instanceof EntityPlayerMP)) {
                continue;
            }
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            if (world.getPlayerChunkMap().isPlayerWatchingChunk(playerMP, chunk.x, chunk.z)) {
                INSTANCE.sendTo(packet, playerMP);
            }
        }
    }
}
