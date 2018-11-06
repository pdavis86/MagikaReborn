package magikareborn.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class ResetCommand extends CommandBase {

    //todo: finish this

    @Override
    public String getName() {
        //todo: localise
        return "Reset Magika Reborn";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        //todo: finish and localise
        return "Just run it";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        //todo: make teh command do something
    }
}
