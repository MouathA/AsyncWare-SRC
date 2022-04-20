package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.server.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class CommandStats extends CommandBase
{
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.stats.usage";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        return (array.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(array, "entity", "block") : ((array.length == 2 && array[0].equals("entity")) ? CommandBase.getListOfStringsMatchingLastWord(array, this.func_175776_d()) : ((array.length >= 2 && array.length <= 4 && array[0].equals("block")) ? CommandBase.func_175771_a(array, 1, blockPos) : (((array.length != 3 || !array[0].equals("entity")) && (array.length != 5 || !array[0].equals("block"))) ? (((array.length != 4 || !array[0].equals("entity")) && (array.length != 6 || !array[0].equals("block"))) ? (((array.length != 6 || !array[0].equals("entity")) && (array.length != 8 || !array[0].equals("block"))) ? null : CommandBase.getListOfStringsMatchingLastWord(array, this.func_175777_e())) : CommandBase.getListOfStringsMatchingLastWord(array, CommandResultStats.Type.getTypeNames())) : CommandBase.getListOfStringsMatchingLastWord(array, "set", "clear"))));
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return array.length > 0 && array[0].equals("entity") && n == 1;
    }
    
    protected List func_175777_e() {
        final Collection scoreObjectives = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        for (final ScoreObjective scoreObjective : scoreObjectives) {
            if (!scoreObjective.getCriteria().isReadOnly()) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return arrayList;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 1) {
            throw new WrongUsageException("commands.stats.usage", new Object[0]);
        }
        if (!array[0].equals("entity")) {
            if (!array[0].equals("block")) {
                throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }
        }
        if (array.length < 5) {
            throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
        }
        final int n = 2;
        int n2 = 0;
        ++n2;
        final String s = array[n];
        if ("set".equals(s)) {
            if (array.length < 5) {
                throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
            }
        }
        else {
            if (!"clear".equals(s)) {
                throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }
            if (array.length < 3) {
                throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
            }
        }
        final int n3 = 2;
        ++n2;
        final CommandResultStats.Type typeByName = CommandResultStats.Type.getTypeByName(array[n3]);
        if (typeByName == null) {
            throw new CommandException("commands.stats.failed", new Object[0]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, 1, false);
        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
        if (tileEntity == null) {
            throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { blockPos.getX(), blockPos.getY(), blockPos.getZ() });
        }
        CommandResultStats commandResultStats;
        if (tileEntity instanceof TileEntityCommandBlock) {
            commandResultStats = ((TileEntityCommandBlock)tileEntity).getCommandResultStats();
        }
        else {
            if (!(tileEntity instanceof TileEntitySign)) {
                throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { blockPos.getX(), blockPos.getY(), blockPos.getZ() });
            }
            commandResultStats = ((TileEntitySign)tileEntity).getStats();
        }
        if ("set".equals(s)) {
            final int n4 = 2;
            ++n2;
            final String s2 = array[n4];
            final String s3 = array[2];
            if (s2.length() == 0 || s3.length() == 0) {
                throw new CommandException("commands.stats.failed", new Object[0]);
            }
            CommandResultStats.func_179667_a(commandResultStats, typeByName, s2, s3);
            CommandBase.notifyOperators(commandSender, this, "commands.stats.success", typeByName.getTypeName(), s3, s2);
        }
        else if ("clear".equals(s)) {
            CommandResultStats.func_179667_a(commandResultStats, typeByName, null, null);
            CommandBase.notifyOperators(commandSender, this, "commands.stats.cleared", typeByName.getTypeName());
        }
        entityWorld.getTileEntity(CommandBase.parseBlockPos(commandSender, array, 1, false)).markDirty();
    }
    
    protected String[] func_175776_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public String getCommandName() {
        return "stats";
    }
}
