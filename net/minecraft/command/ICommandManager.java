package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;

public interface ICommandManager
{
    List getPossibleCommands(final ICommandSender p0);
    
    List getTabCompletionOptions(final ICommandSender p0, final String p1, final BlockPos p2);
    
    int executeCommand(final ICommandSender p0, final String p1);
    
    Map getCommands();
}
