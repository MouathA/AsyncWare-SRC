package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;

public class CommandDefaultGameMode extends CommandGameMode
{
    @Override
    public String getCommandName() {
        return "defaultgamemode";
    }
    
    protected void setGameType(final WorldSettings.GameType gameType) {
        final MinecraftServer server = MinecraftServer.getServer();
        server.setGameType(gameType);
        if (server.getForceGamemode()) {
            for (final EntityPlayerMP entityPlayerMP : MinecraftServer.getServer().getConfigurationManager().func_181057_v()) {
                entityPlayerMP.setGameType(gameType);
                entityPlayerMP.fallDistance = 0.0f;
            }
        }
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.defaultgamemode.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length <= 0) {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
        final WorldSettings.GameType gameModeFromCommand = this.getGameModeFromCommand(commandSender, array[0]);
        this.setGameType(gameModeFromCommand);
        CommandBase.notifyOperators(commandSender, this, "commands.defaultgamemode.success", new ChatComponentTranslation("gameMode." + gameModeFromCommand.getName(), new Object[0]));
    }
}
