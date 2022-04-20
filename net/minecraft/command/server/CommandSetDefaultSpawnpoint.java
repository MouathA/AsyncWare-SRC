package net.minecraft.command.server;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.command.*;

public class CommandSetDefaultSpawnpoint extends CommandBase
{
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : null;
    }
    
    @Override
    public String getCommandName() {
        return "setworldspawn";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        BlockPos spawnPoint;
        if (array.length == 0) {
            spawnPoint = CommandBase.getCommandSenderAsPlayer(commandSender).getPosition();
        }
        else {
            if (array.length != 3 || commandSender.getEntityWorld() == null) {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }
            spawnPoint = CommandBase.parseBlockPos(commandSender, array, 0, true);
        }
        commandSender.getEntityWorld().setSpawnPoint(spawnPoint);
        MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new S05PacketSpawnPosition(spawnPoint));
        CommandBase.notifyOperators(commandSender, this, "commands.setworldspawn.success", spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ());
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.setworldspawn.usage";
    }
}
