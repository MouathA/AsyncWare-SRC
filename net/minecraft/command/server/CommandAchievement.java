package net.minecraft.command.server;

import net.minecraft.stats.*;
import net.minecraft.command.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import java.util.*;

public class CommandAchievement extends CommandBase
{
    @Override
    public String getCommandName() {
        return "achievement";
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        return n == 2;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.achievement.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < 2) {
            throw new WrongUsageException("commands.achievement.usage", new Object[0]);
        }
        final StatBase oneShotStat = StatList.getOneShotStat(array[1]);
        if (oneShotStat == null && !array[1].equals("*")) {
            throw new CommandException("commands.achievement.unknownAchievement", new Object[] { array[1] });
        }
        final EntityPlayerMP entityPlayerMP = (array.length >= 3) ? CommandBase.getPlayer(commandSender, array[2]) : CommandBase.getCommandSenderAsPlayer(commandSender);
        final boolean equalsIgnoreCase = array[0].equalsIgnoreCase("give");
        final boolean equalsIgnoreCase2 = array[0].equalsIgnoreCase("take");
        if (equalsIgnoreCase || equalsIgnoreCase2) {
            if (oneShotStat == null) {
                if (equalsIgnoreCase) {
                    final Iterator<Achievement> iterator = (Iterator<Achievement>)AchievementList.achievementList.iterator();
                    while (iterator.hasNext()) {
                        entityPlayerMP.triggerAchievement(iterator.next());
                    }
                    CommandBase.notifyOperators(commandSender, this, "commands.achievement.give.success.all", entityPlayerMP.getName());
                }
                else if (equalsIgnoreCase2) {
                    final Iterator<Achievement> iterator2 = (Iterator<Achievement>)Lists.reverse(AchievementList.achievementList).iterator();
                    while (iterator2.hasNext()) {
                        entityPlayerMP.func_175145_a(iterator2.next());
                    }
                    CommandBase.notifyOperators(commandSender, this, "commands.achievement.take.success.all", entityPlayerMP.getName());
                }
            }
            else {
                if (oneShotStat instanceof Achievement) {
                    Achievement parentAchievement = (Achievement)oneShotStat;
                    if (equalsIgnoreCase) {
                        if (entityPlayerMP.getStatFile().hasAchievementUnlocked(parentAchievement)) {
                            throw new CommandException("commands.achievement.alreadyHave", new Object[] { entityPlayerMP.getName(), oneShotStat.func_150955_j() });
                        }
                        final ArrayList arrayList = Lists.newArrayList();
                        while (parentAchievement.parentAchievement != null && !entityPlayerMP.getStatFile().hasAchievementUnlocked(parentAchievement.parentAchievement)) {
                            arrayList.add(parentAchievement.parentAchievement);
                            parentAchievement = parentAchievement.parentAchievement;
                        }
                        final Iterator iterator3 = Lists.reverse((List)arrayList).iterator();
                        while (iterator3.hasNext()) {
                            entityPlayerMP.triggerAchievement(iterator3.next());
                        }
                    }
                    else if (equalsIgnoreCase2) {
                        if (!entityPlayerMP.getStatFile().hasAchievementUnlocked(parentAchievement)) {
                            throw new CommandException("commands.achievement.dontHave", new Object[] { entityPlayerMP.getName(), oneShotStat.func_150955_j() });
                        }
                        final ArrayList arrayList2 = Lists.newArrayList((Iterator)Iterators.filter((Iterator)AchievementList.achievementList.iterator(), (Predicate)new Predicate(this, entityPlayerMP, oneShotStat) {
                            final StatBase val$statbase;
                            final EntityPlayerMP val$entityplayermp;
                            final CommandAchievement this$0;
                            
                            public boolean apply(final Object o) {
                                return this.apply((Achievement)o);
                            }
                            
                            public boolean apply(final Achievement achievement) {
                                return this.val$entityplayermp.getStatFile().hasAchievementUnlocked(achievement) && achievement != this.val$statbase;
                            }
                        }));
                        final ArrayList arrayList3 = Lists.newArrayList((Iterable)arrayList2);
                        for (Achievement parentAchievement2 : arrayList2) {
                            while (parentAchievement2 != null) {
                                if (parentAchievement2 == oneShotStat) {}
                                parentAchievement2 = parentAchievement2.parentAchievement;
                            }
                        }
                        final Iterator<Achievement> iterator5 = (Iterator<Achievement>)arrayList3.iterator();
                        while (iterator5.hasNext()) {
                            entityPlayerMP.func_175145_a(iterator5.next());
                        }
                    }
                }
                if (equalsIgnoreCase) {
                    entityPlayerMP.triggerAchievement(oneShotStat);
                    CommandBase.notifyOperators(commandSender, this, "commands.achievement.give.success.one", entityPlayerMP.getName(), oneShotStat.func_150955_j());
                }
                else if (equalsIgnoreCase2) {
                    entityPlayerMP.func_175145_a(oneShotStat);
                    CommandBase.notifyOperators(commandSender, this, "commands.achievement.take.success.one", oneShotStat.func_150955_j(), entityPlayerMP.getName());
                }
            }
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(array, "give", "take");
        }
        if (array.length != 2) {
            return (array.length == 3) ? CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames()) : null;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<StatBase> iterator = StatList.allStats.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().statId);
        }
        return CommandBase.getListOfStringsMatchingLastWord(array, arrayList);
    }
}
