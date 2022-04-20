package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;

public interface ICommand extends Comparable
{
    List addTabCompletionOptions(final ICommandSender p0, final String[] p1, final BlockPos p2);
    
    String getCommandName();
    
    boolean isUsernameIndex(final String[] p0, final int p1);
    
    String getCommandUsage(final ICommandSender p0);
    
    boolean canCommandSenderUseCommand(final ICommandSender p0);
    
    void processCommand(final ICommandSender p0, final String[] p1) throws CommandException;
    
    List getCommandAliases();
}
