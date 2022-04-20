package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;

public class CommandEntityData extends CommandBase
{
    @Override
    public String getCommandName() {
        return "entitydata";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.entitydata.usage";
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 0;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
        }
        final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[0]);
        if (func_175768_b instanceof EntityPlayer) {
            throw new CommandException("commands.entitydata.noPlayers", new Object[] { func_175768_b.getDisplayName() });
        }
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        func_175768_b.writeToNBT(nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtTagCompound.copy();
        final NBTTagCompound tagFromJson = JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, 1).getUnformattedText());
        tagFromJson.removeTag("UUIDMost");
        tagFromJson.removeTag("UUIDLeast");
        nbtTagCompound.merge(tagFromJson);
        if (nbtTagCompound.equals(nbtTagCompound2)) {
            throw new CommandException("commands.entitydata.failed", new Object[] { nbtTagCompound.toString() });
        }
        func_175768_b.readFromNBT(nbtTagCompound);
        CommandBase.notifyOperators(commandSender, this, "commands.entitydata.success", nbtTagCompound.toString());
    }
}
