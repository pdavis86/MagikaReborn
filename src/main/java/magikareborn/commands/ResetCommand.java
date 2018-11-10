package magikareborn.commands;

import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import magikareborn.network.OpusUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class ResetCommand extends CommandBase {

    //todo: finish this

    @Override
    public String getName() {
        //todo: localise
        return "magikareborn";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        //todo: finish and localise
        return "Just run it";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1 && args[0] == "reset") {
            IOpusCapability newOpusCapability = new OpusCapability();
            for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
                player.getCapability(OpusCapabilityStorage.CAPABILITY, null).cloneFrom(newOpusCapability);
                PacketHandler.INSTANCE.sendTo(new OpusUpdatePacket(newOpusCapability), player);
            }
        }
    }
}
