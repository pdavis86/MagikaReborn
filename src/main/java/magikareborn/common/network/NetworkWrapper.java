package uk.co.davissoftware.magikareborn.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("unused")
public class NetworkWrapper {

    public final SimpleChannel network;
    private int id = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    public NetworkWrapper(ResourceLocation channelName) {
        NetworkRegistry.ChannelBuilder cb = NetworkRegistry.ChannelBuilder.named(channelName);
        String pv = PROTOCOL_VERSION;
        cb = cb.clientAcceptedVersions(pv::equals);
        this.network = cb.serverAcceptedVersions(pv::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    }

    public <MSG extends ISimplePacket> void registerMessage(Class<MSG> messageClass, Function<PacketBuffer, MSG> decoder, NetworkDirection direction) {
        network.registerMessage(this.id++, messageClass, ISimplePacket::encode, decoder, ISimplePacket::handle, Optional.ofNullable(direction));
    }

    public void sendToServer(Object message) {
        network.sendToServer(message);
    }

    public void send(PacketDistributor.PacketTarget target, Object message) {
        network.send(target, message);
    }

    public void sendToClientsAround(Object message, ServerWorld serverWorld, BlockPos position) {
        Chunk chunk = serverWorld.getChunkAt(position);
        network.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), message);
    }

    public void sendToClientsAll(Object message) {
        network.send(PacketDistributor.ALL.noArg(), message);
    }

    public void sendToPlayer(Object message, ServerPlayerEntity player) {
        //network.send(PacketDistributor.PLAYER.with(player), message);
        if (!(player instanceof FakePlayer)) {
            network.sendTo(message, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

}
