package uk.co.davissoftware.magikareborn.common.network;

import net.minecraftforge.fml.network.NetworkDirection;
import uk.co.davissoftware.magikareborn.common.helpers.ResourceHelper;

public class PacketHandler extends NetworkWrapper {

    private static PacketHandler instance;

    private PacketHandler() {
        super(ResourceHelper.getResource("network"));
    }

    public static synchronized PacketHandler getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    public static void init() {
        instance = new PacketHandler();
        instance.registerMessage(OpusRequestPacket.class, OpusRequestPacket::new, NetworkDirection.PLAY_TO_SERVER);
        instance.registerMessage(OpusUpdatePacket.class, OpusUpdatePacket::new, NetworkDirection.PLAY_TO_SERVER);
        instance.registerMessage(OpusUpdatePacket.class, OpusUpdatePacket::new, NetworkDirection.PLAY_TO_CLIENT);
        //instance.registerMessage(FluidUpdatePacket.class, FluidUpdatePacket::new, NetworkDirection.PLAY_TO_CLIENT);
    }

//    public static void sendVanillaPacket(IPacket<?> packet, Entity player) {
//        if (player instanceof ServerPlayerEntity && ((ServerPlayerEntity) player).connection != null) {
//            ((ServerPlayerEntity) player).connection.sendPacket(packet);
//        }
//    }

}
