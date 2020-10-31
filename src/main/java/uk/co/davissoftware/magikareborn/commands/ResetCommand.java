package magikareborn.commands;

import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.network.OpusUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class ResetCommand extends CommandBase {

    @Nonnull
    @Override
    public String getName() {
        return "magikareborn";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "commands.magikareborn.usage";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {

        if (sender.getEntityWorld().isRemote) {
            return;
        }

        if (args.length <= 0) {
            throw new WrongUsageException(getUsage(sender));
        }

        switch (args[0]) {
            case "reset":
                if (args.length == 1) {
                    sender.sendMessage(new TextComponentTranslation("commands.magikareborn.resetingyou"));
                    EntityPlayerMP player = (EntityPlayerMP) sender;
                    IOpusCapability opusCapability = player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                    if (opusCapability == null) {
                        throw new CommandException("Could not find Capability");
                    }
                    opusCapability.cloneFrom(new OpusCapability(player), true);
                    PacketHandler.INSTANCE.sendTo(new OpusUpdatePacket(opusCapability), player);

                } else {
                    sender.sendMessage(new TextComponentTranslation("commands.magikareborn.resetingplayer", args[1]));
                    EntityPlayerMP player = (EntityPlayerMP) server.getEntityWorld().getPlayerEntityByName(args[1]);
                    if (player == null) {
                        throw new CommandException("Could not find player " + args[1]);
                    }
                    IOpusCapability opusCapability = player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                    if (opusCapability == null) {
                        throw new CommandException("Could not find Capability");
                    }
                    opusCapability.cloneFrom(new OpusCapability(player), true);
                    PacketHandler.INSTANCE.sendTo(new OpusUpdatePacket(opusCapability), player);
                }
                break;

            case "resetall":
                sender.sendMessage(new TextComponentTranslation("commands.magikareborn.resetingall"));
                for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
                    IOpusCapability opusCapability = player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                    if (opusCapability == null) {
                        throw new CommandException("Could not find Capability for player " + player.getName());
                    }
                    opusCapability.cloneFrom(new OpusCapability(player), true);
                    PacketHandler.INSTANCE.sendTo(new OpusUpdatePacket(opusCapability), player);
                }
                break;

            default:
                throw new WrongUsageException(getUsage(sender));
        }
    }
}
