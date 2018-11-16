package magikareborn.commands;

import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import magikareborn.network.OpusUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class ResetCommand extends CommandBase {

    @Override
    public String getName() {
        return "magikareborn";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.magikareborn.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (sender.getEntityWorld().isRemote) {
            return;
        }

        if (args.length <= 0) {
            throw new WrongUsageException(getUsage(sender), new Object[0]);
        }

        if (args[0].equals("reset")) {
            if (args.length == 1) {
                sender.sendMessage(new TextComponentTranslation("commands.magikareborn.resetingyou"));
                EntityPlayerMP player = (EntityPlayerMP) sender;
                IOpusCapability opusCapability = player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                opusCapability.cloneFrom(new OpusCapability(player), true);
                PacketHandler.INSTANCE.sendTo(new OpusUpdatePacket(opusCapability), player);

            } else {
                sender.sendMessage(new TextComponentTranslation("commands.magikareborn.resetingplayer", args[1]));
                EntityPlayerMP player = (EntityPlayerMP) server.getEntityWorld().getPlayerEntityByName(args[1]);
                if (player == null) {
                    throw new CommandException("Could not find player " + args[1]);
                }
                IOpusCapability opusCapability = player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                opusCapability.cloneFrom(new OpusCapability(player), true);
                PacketHandler.INSTANCE.sendTo(new OpusUpdatePacket(opusCapability), player);
            }

        } else if (args[0].equals("resetall")) {
            sender.sendMessage(new TextComponentTranslation("commands.magikareborn.resetingall"));
            for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
                IOpusCapability opusCapability = player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                opusCapability.cloneFrom(new OpusCapability(player), true);
                PacketHandler.INSTANCE.sendTo(new OpusUpdatePacket(opusCapability), player);
            }

        } else {
            throw new WrongUsageException(getUsage(sender), new Object[0]);
        }
    }
}
