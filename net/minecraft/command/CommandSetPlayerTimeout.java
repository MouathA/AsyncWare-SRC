package net.minecraft.command;

import net.minecraft.server.*;

public class CommandSetPlayerTimeout extends CommandBase
{
    @Override
    public String getCommandName() {
        return "setidletimeout";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.setidletimeout.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length != 1) {
            throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
        }
        final int int1 = CommandBase.parseInt(array[0], 0);
        MinecraftServer.getServer().setPlayerIdleTimeout(int1);
        CommandBase.notifyOperators(commandSender, this, "commands.setidletimeout.success", int1);
    }
}
