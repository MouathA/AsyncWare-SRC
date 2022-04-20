package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class CommandBlockData extends CommandBase
{
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.blockdata.usage";
    }
    
    @Override
    public String getCommandName() {
        return "blockdata";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : null;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 4) {
            throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, 0, false);
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(blockPos)) {
            throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
        }
        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
        if (tileEntity == null) {
            throw new CommandException("commands.blockdata.notValid", new Object[0]);
        }
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        tileEntity.writeToNBT(nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtTagCompound.copy();
        nbtTagCompound.merge(JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, 3).getUnformattedText()));
        nbtTagCompound.setInteger("x", blockPos.getX());
        nbtTagCompound.setInteger("y", blockPos.getY());
        nbtTagCompound.setInteger("z", blockPos.getZ());
        if (nbtTagCompound.equals(nbtTagCompound2)) {
            throw new CommandException("commands.blockdata.failed", new Object[] { nbtTagCompound.toString() });
        }
        tileEntity.readFromNBT(nbtTagCompound);
        tileEntity.markDirty();
        entityWorld.markBlockForUpdate(blockPos);
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        CommandBase.notifyOperators(commandSender, this, "commands.blockdata.success", nbtTagCompound.toString());
    }
}
