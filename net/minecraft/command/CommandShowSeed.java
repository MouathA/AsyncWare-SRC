package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.util.*;

public class CommandShowSeed extends CommandBase
{
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        commandSender.addChatMessage(new ChatComponentTranslation("commands.seed.success", new Object[] { ((commandSender instanceof EntityPlayer) ? ((EntityPlayer)commandSender).worldObj : MinecraftServer.getServer().worldServerForDimension(0)).getSeed() }));
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.seed.usage";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender commandSender) {
        return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(commandSender);
    }
    
    @Override
    public String getCommandName() {
        return "seed";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
