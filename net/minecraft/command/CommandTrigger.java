package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.server.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;

public class CommandTrigger extends CommandBase
{
    @Override
    public String getCommandName() {
        return "trigger";
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
            final ArrayList arrayList = Lists.newArrayList();
            for (final ScoreObjective scoreObjective : scoreboard.getScoreObjectives()) {
                if (scoreObjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
                    arrayList.add(scoreObjective.getName());
                }
            }
            return CommandBase.getListOfStringsMatchingLastWord(array, (String[])arrayList.toArray(new String[arrayList.size()]));
        }
        return (array.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(array, "add", "set") : null;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.trigger.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 3) {
            throw new WrongUsageException("commands.trigger.usage", new Object[0]);
        }
        EntityPlayerMP entityPlayerMP;
        if (commandSender instanceof EntityPlayerMP) {
            entityPlayerMP = (EntityPlayerMP)commandSender;
        }
        else {
            final Entity commandSenderEntity = commandSender.getCommandSenderEntity();
            if (!(commandSenderEntity instanceof EntityPlayerMP)) {
                throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
            }
            entityPlayerMP = (EntityPlayerMP)commandSenderEntity;
        }
        final Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
        final ScoreObjective objective = scoreboard.getObjective(array[0]);
        if (objective == null || objective.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
            throw new CommandException("commands.trigger.invalidObjective", new Object[] { array[0] });
        }
        final int int1 = CommandBase.parseInt(array[2]);
        if (!scoreboard.entityHasObjective(entityPlayerMP.getName(), objective)) {
            throw new CommandException("commands.trigger.invalidObjective", new Object[] { array[0] });
        }
        final Score valueFromObjective = scoreboard.getValueFromObjective(entityPlayerMP.getName(), objective);
        if (valueFromObjective.isLocked()) {
            throw new CommandException("commands.trigger.disabled", new Object[] { array[0] });
        }
        if ("set".equals(array[1])) {
            valueFromObjective.setScorePoints(int1);
        }
        else {
            if (!"add".equals(array[1])) {
                throw new CommandException("commands.trigger.invalidMode", new Object[] { array[1] });
            }
            valueFromObjective.increseScore(int1);
        }
        valueFromObjective.setLocked(true);
        if (entityPlayerMP.theItemInWorldManager.isCreative()) {
            CommandBase.notifyOperators(commandSender, this, "commands.trigger.success", array[0], array[1], array[2]);
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
