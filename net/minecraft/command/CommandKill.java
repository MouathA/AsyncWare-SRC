package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandKill extends CommandBase
{
    @Override
    public String getCommandName() {
        return "kill";
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.kill.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length == 0) {
            final EntityPlayerMP commandSenderAsPlayer = CommandBase.getCommandSenderAsPlayer(commandSender);
            commandSenderAsPlayer.onKillCommand();
            CommandBase.notifyOperators(commandSender, this, "commands.kill.successful", commandSenderAsPlayer.getDisplayName());
        }
        else {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[0]);
            func_175768_b.onKillCommand();
            CommandBase.notifyOperators(commandSender, this, "commands.kill.successful", func_175768_b.getDisplayName());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
