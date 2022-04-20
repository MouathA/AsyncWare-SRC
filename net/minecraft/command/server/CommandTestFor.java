package net.minecraft.command.server;

import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandTestFor extends CommandBase
{
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public String getCommandName() {
        return "testfor";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.testfor.usage", new Object[0]);
        }
        final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[0]);
        NBTBase tagFromJson = null;
        if (array.length >= 2) {
            tagFromJson = JsonToNBT.getTagFromJson(CommandBase.buildString(array, 1));
        }
        if (tagFromJson != null) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            func_175768_b.writeToNBT(nbtTagCompound);
            if (!NBTUtil.func_181123_a(tagFromJson, (NBTBase)nbtTagCompound, true)) {
                throw new CommandException("commands.testfor.failure", new Object[] { func_175768_b.getName() });
            }
        }
        CommandBase.notifyOperators(commandSender, this, "commands.testfor.success", func_175768_b.getName());
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.testfor.usage";
    }
}
