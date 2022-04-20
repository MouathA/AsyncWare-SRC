package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.command.*;

public class CommandListPlayers extends CommandBase
{
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.players.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        final int currentPlayerCount = MinecraftServer.getServer().getCurrentPlayerCount();
        commandSender.addChatMessage(new ChatComponentTranslation("commands.players.list", new Object[] { currentPlayerCount, MinecraftServer.getServer().getMaxPlayers() }));
        commandSender.addChatMessage(new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_181058_b(array.length > 0 && "uuids".equalsIgnoreCase(array[0]))));
        commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, currentPlayerCount);
    }
    
    @Override
    public String getCommandName() {
        return "list";
    }
}
