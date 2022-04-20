package net.minecraft.command.server;

import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class CommandTestForBlock extends CommandBase
{
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length > 0 && array.length <= 3) ? CommandBase.func_175771_a(array, 0, blockPos) : ((array.length == 4) ? CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys()) : null);
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 4) {
            throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, 0, false);
        final Block blockFromName = Block.getBlockFromName(array[3]);
        if (blockFromName == null) {
            throw new NumberInvalidException("commands.setblock.notFound", new Object[] { array[3] });
        }
        if (array.length >= 5) {
            CommandBase.parseInt(array[4], -1, 15);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(blockPos)) {
            throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound tagFromJson = new NBTTagCompound();
        if (array.length >= 6 && blockFromName.hasTileEntity()) {
            tagFromJson = JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, 5).getUnformattedText());
        }
        final Block block = entityWorld.getBlockState(blockPos).getBlock();
        if (block != blockFromName) {
            throw new CommandException("commands.testforblock.failed.tile", new Object[] { blockPos.getX(), blockPos.getY(), blockPos.getZ(), block.getLocalizedName(), blockFromName.getLocalizedName() });
        }
        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
        if (tileEntity == null) {
            throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { blockPos.getX(), blockPos.getY(), blockPos.getZ() });
        }
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        tileEntity.writeToNBT(nbtTagCompound);
        if (!NBTUtil.func_181123_a((NBTBase)tagFromJson, (NBTBase)nbtTagCompound, true)) {
            throw new CommandException("commands.testforblock.failed.nbt", new Object[] { blockPos.getX(), blockPos.getY(), blockPos.getZ() });
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
        CommandBase.notifyOperators(commandSender, this, "commands.testforblock.success", blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandName() {
        return "testforblock";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.testforblock.usage";
    }
}
