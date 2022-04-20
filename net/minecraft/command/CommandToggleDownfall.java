package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.world.storage.*;

public class CommandToggleDownfall extends CommandBase
{
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    protected void toggleDownfall() {
        final WorldInfo worldInfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
        worldInfo.setRaining(!worldInfo.isRaining());
    }
    
    @Override
    public String getCommandName() {
        return "toggledownfall";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.downfall.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        this.toggleDownfall();
        CommandBase.notifyOperators(commandSender, this, "commands.downfall.success", new Object[0]);
    }
}
