package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.command.*;

public class CommandPublishLocalServer extends CommandBase
{
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.publish.usage";
    }
    
    @Override
    public String getCommandName() {
        return "publish";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final String shareToLAN = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);
        if (shareToLAN != null) {
            CommandBase.notifyOperators(commandSender, this, "commands.publish.started", shareToLAN);
        }
        else {
            CommandBase.notifyOperators(commandSender, this, "commands.publish.failed", new Object[0]);
        }
    }
}
