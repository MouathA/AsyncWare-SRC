package net.minecraft.command.server;

import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;

public class CommandScoreboard extends CommandBase
{
    protected void setPlayer(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n - 1];
        final int n2 = n;
        final String entityName = CommandBase.getEntityName(commandSender, array[n++]);
        if (entityName.length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { entityName, 40 });
        }
        final ScoreObjective objective = this.getObjective(array[n++], true);
        final int scorePoints = s.equalsIgnoreCase("set") ? CommandBase.parseInt(array[n++]) : CommandBase.parseInt(array[n++], 0);
        if (array.length > n) {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[n2]);
            final NBTTagCompound tagFromJson = JsonToNBT.getTagFromJson(CommandBase.buildString(array, n));
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            func_175768_b.writeToNBT(nbtTagCompound);
            if (!NBTUtil.func_181123_a((NBTBase)tagFromJson, (NBTBase)nbtTagCompound, true)) {
                throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { entityName });
            }
        }
        final Score valueFromObjective = this.getScoreboard().getValueFromObjective(entityName, objective);
        if (s.equalsIgnoreCase("set")) {
            valueFromObjective.setScorePoints(scorePoints);
        }
        else if (s.equalsIgnoreCase("add")) {
            valueFromObjective.increseScore(scorePoints);
        }
        else {
            valueFromObjective.decreaseScore(scorePoints);
        }
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.set.success", objective.getName(), entityName, valueFromObjective.getScorePoints());
    }
    
    protected void setObjectiveDisplay(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String s = array[n++];
        final int objectiveDisplaySlotNumber = Scoreboard.getObjectiveDisplaySlotNumber(s);
        ScoreObjective objective = null;
        if (array.length == 4) {
            objective = this.getObjective(array[n], false);
        }
        if (objectiveDisplaySlotNumber < 0) {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { s });
        }
        scoreboard.setObjectiveInDisplaySlot(objectiveDisplaySlotNumber, objective);
        if (objective != null) {
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.objectives.setdisplay.successSet", Scoreboard.getObjectiveDisplaySlot(objectiveDisplaySlotNumber), objective.getName());
        }
        else {
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.objectives.setdisplay.successCleared", Scoreboard.getObjectiveDisplaySlot(objectiveDisplaySlotNumber));
        }
    }
    
    protected List func_147184_a(final boolean b) {
        final Collection scoreObjectives = this.getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        for (final ScoreObjective scoreObjective : scoreObjectives) {
            if (!b || !scoreObjective.getCriteria().isReadOnly()) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return arrayList;
    }
    
    protected void emptyTeam(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final ScorePlayerTeam team = this.getTeam(array[n]);
        if (team != null) {
            final ArrayList arrayList = Lists.newArrayList((Iterable)team.getMembershipCollection());
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, arrayList.size());
            if (arrayList.isEmpty()) {
                throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { team.getRegisteredName() });
            }
            final Iterator<String> iterator = (Iterator<String>)arrayList.iterator();
            while (iterator.hasNext()) {
                scoreboard.removePlayerFromTeam(iterator.next(), team);
            }
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.empty.success", arrayList.size(), team.getRegisteredName());
        }
    }
    
    protected void listObjectives(final ICommandSender commandSender) throws CommandException {
        final Collection scoreObjectives = this.getScoreboard().getScoreObjectives();
        if (scoreObjectives.size() <= 0) {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { scoreObjectives.size() });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        commandSender.addChatMessage(chatComponentTranslation);
        for (final ScoreObjective scoreObjective : scoreObjectives) {
            commandSender.addChatMessage(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { scoreObjective.getName(), scoreObjective.getDisplayName(), scoreObjective.getCriteria().getName() }));
        }
    }
    
    protected void addObjective(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n++];
        final String s2 = array[n++];
        final Scoreboard scoreboard = this.getScoreboard();
        final IScoreObjectiveCriteria scoreObjectiveCriteria = IScoreObjectiveCriteria.INSTANCES.get(s2);
        if (scoreObjectiveCriteria == null) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { s2 });
        }
        if (scoreboard.getObjective(s) != null) {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { s });
        }
        if (s.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { s, 16 });
        }
        if (s.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
        }
        if (array.length > n) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, n).getUnformattedText();
            if (unformattedText.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { unformattedText, 32 });
            }
            if (unformattedText.length() > 0) {
                scoreboard.addScoreObjective(s, scoreObjectiveCriteria).setDisplayName(unformattedText);
            }
            else {
                scoreboard.addScoreObjective(s, scoreObjectiveCriteria);
            }
        }
        else {
            scoreboard.addScoreObjective(s, scoreObjectiveCriteria);
        }
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.objectives.add.success", s);
    }
    
    protected void listTeams(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        if (array.length > n) {
            final ScorePlayerTeam team = this.getTeam(array[n]);
            if (team == null) {
                return;
            }
            final Collection membershipCollection = team.getMembershipCollection();
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, membershipCollection.size());
            if (membershipCollection.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { team.getRegisteredName() });
            }
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { membershipCollection.size(), team.getRegisteredName() });
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation);
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(membershipCollection.toArray())));
        }
        else {
            final Collection teams = scoreboard.getTeams();
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, teams.size());
            if (teams.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { teams.size() });
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation2);
            for (final ScorePlayerTeam scorePlayerTeam : teams) {
                commandSender.addChatMessage(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { scorePlayerTeam.getRegisteredName(), scorePlayerTeam.getTeamName(), scorePlayerTeam.getMembershipCollection().size() }));
            }
        }
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (commandSender < array) {
            if (array.length < 1) {
                throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
            }
            if (array[0].equalsIgnoreCase("objectives")) {
                if (array.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                }
                if (array[1].equalsIgnoreCase("list")) {
                    this.listObjectives(commandSender);
                }
                else if (array[1].equalsIgnoreCase("add")) {
                    if (array.length < 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                    }
                    this.addObjective(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("remove")) {
                    if (array.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                    }
                    this.removeObjective(commandSender, array[2]);
                }
                else {
                    if (!array[1].equalsIgnoreCase("setdisplay")) {
                        throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                    }
                    if (array.length != 3 && array.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                    }
                    this.setObjectiveDisplay(commandSender, array, 2);
                }
            }
            else if (array[0].equalsIgnoreCase("players")) {
                if (array.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                }
                if (array[1].equalsIgnoreCase("list")) {
                    if (array.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                    }
                    this.listPlayers(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("add")) {
                    if (array.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                    }
                    this.setPlayer(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("remove")) {
                    if (array.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                    }
                    this.setPlayer(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("set")) {
                    if (array.length < 5) {
                        throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                    }
                    this.setPlayer(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("reset")) {
                    if (array.length != 3 && array.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                    }
                    this.resetPlayers(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("enable")) {
                    if (array.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
                    }
                    this.func_175779_n(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("test")) {
                    if (array.length != 5 && array.length != 6) {
                        throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
                    }
                    this.func_175781_o(commandSender, array, 2);
                }
                else {
                    if (!array[1].equalsIgnoreCase("operation")) {
                        throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                    }
                    if (array.length != 7) {
                        throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
                    }
                    this.func_175778_p(commandSender, array, 2);
                }
            }
            else {
                if (!array[0].equalsIgnoreCase("teams")) {
                    throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
                }
                if (array.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                }
                if (array[1].equalsIgnoreCase("list")) {
                    if (array.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                    }
                    this.listTeams(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("add")) {
                    if (array.length < 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                    }
                    this.addTeam(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("remove")) {
                    if (array.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                    }
                    this.removeTeam(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("empty")) {
                    if (array.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                    }
                    this.emptyTeam(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("join")) {
                    if (array.length < 4 && (array.length != 3 || !(commandSender instanceof EntityPlayer))) {
                        throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                    }
                    this.joinTeam(commandSender, array, 2);
                }
                else if (array[1].equalsIgnoreCase("leave")) {
                    if (array.length < 3 && !(commandSender instanceof EntityPlayer)) {
                        throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                    }
                    this.leaveTeam(commandSender, array, 2);
                }
                else {
                    if (!array[1].equalsIgnoreCase("option")) {
                        throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                    }
                    if (array.length != 4 && array.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                    }
                    this.setTeamOption(commandSender, array, 2);
                }
            }
        }
    }
    
    protected void func_175778_p(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String entityName = CommandBase.getEntityName(commandSender, array[n++]);
        final ScoreObjective objective = this.getObjective(array[n++], true);
        final String s = array[n++];
        final String entityName2 = CommandBase.getEntityName(commandSender, array[n++]);
        final ScoreObjective objective2 = this.getObjective(array[n], false);
        if (entityName.length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { entityName, 40 });
        }
        if (entityName2.length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { entityName2, 40 });
        }
        final Score valueFromObjective = scoreboard.getValueFromObjective(entityName, objective);
        if (!scoreboard.entityHasObjective(entityName2, objective2)) {
            throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { objective2.getName(), entityName2 });
        }
        final Score valueFromObjective2 = scoreboard.getValueFromObjective(entityName2, objective2);
        if (s.equals("+=")) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() + valueFromObjective2.getScorePoints());
        }
        else if (s.equals("-=")) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() - valueFromObjective2.getScorePoints());
        }
        else if (s.equals("*=")) {
            valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() * valueFromObjective2.getScorePoints());
        }
        else if (s.equals("/=")) {
            if (valueFromObjective2.getScorePoints() != 0) {
                valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() / valueFromObjective2.getScorePoints());
            }
        }
        else if (s.equals("%=")) {
            if (valueFromObjective2.getScorePoints() != 0) {
                valueFromObjective.setScorePoints(valueFromObjective.getScorePoints() % valueFromObjective2.getScorePoints());
            }
        }
        else if (s.equals("=")) {
            valueFromObjective.setScorePoints(valueFromObjective2.getScorePoints());
        }
        else if (s.equals("<")) {
            valueFromObjective.setScorePoints(Math.min(valueFromObjective.getScorePoints(), valueFromObjective2.getScorePoints()));
        }
        else if (s.equals(">")) {
            valueFromObjective.setScorePoints(Math.max(valueFromObjective.getScorePoints(), valueFromObjective2.getScorePoints()));
        }
        else {
            if (!s.equals("><")) {
                throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { s });
            }
            final int scorePoints = valueFromObjective.getScorePoints();
            valueFromObjective.setScorePoints(valueFromObjective2.getScorePoints());
            valueFromObjective2.setScorePoints(scorePoints);
        }
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.operation.success", new Object[0]);
    }
    
    protected void func_175779_n(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String playerName = CommandBase.getPlayerName(commandSender, array[n++]);
        if (playerName.length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { playerName, 40 });
        }
        final ScoreObjective objective = this.getObjective(array[n], false);
        if (objective.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
            throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { objective.getName() });
        }
        scoreboard.getValueFromObjective(playerName, objective).setLocked(false);
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.enable.success", objective.getName(), playerName);
    }
    
    protected Scoreboard getScoreboard() {
        return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
    }
    
    protected void leaveTeam(final ICommandSender commandSender, final String[] array, int i) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final HashSet hashSet = Sets.newHashSet();
        final HashSet hashSet2 = Sets.newHashSet();
        if (commandSender instanceof EntityPlayer && i == array.length) {
            final String name = CommandBase.getCommandSenderAsPlayer(commandSender).getName();
            if (scoreboard.removePlayerFromTeams(name)) {
                hashSet.add(name);
            }
            else {
                hashSet2.add(name);
            }
        }
        else {
            while (i < array.length) {
                final String s = array[i++];
                if (s.startsWith("@")) {
                    final Iterator iterator = CommandBase.func_175763_c(commandSender, s).iterator();
                    while (iterator.hasNext()) {
                        final String entityName = CommandBase.getEntityName(commandSender, iterator.next().getUniqueID().toString());
                        if (scoreboard.removePlayerFromTeams(entityName)) {
                            hashSet.add(entityName);
                        }
                        else {
                            hashSet2.add(entityName);
                        }
                    }
                }
                else {
                    final String entityName2 = CommandBase.getEntityName(commandSender, s);
                    if (scoreboard.removePlayerFromTeams(entityName2)) {
                        hashSet.add(entityName2);
                    }
                    else {
                        hashSet2.add(entityName2);
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, hashSet.size());
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.leave.success", hashSet.size(), CommandBase.joinNiceString(hashSet.toArray(new String[hashSet.size()])));
        }
        if (!hashSet2.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { hashSet2.size(), CommandBase.joinNiceString(hashSet2.toArray(new String[hashSet2.size()])) });
        }
    }
    
    protected List func_175782_e() {
        final Collection scoreObjectives = this.getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        for (final ScoreObjective scoreObjective : scoreObjectives) {
            if (scoreObjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return arrayList;
    }
    
    protected void listPlayers(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        if (array.length > n) {
            final String entityName = CommandBase.getEntityName(commandSender, array[n]);
            final Map objectivesForEntity = scoreboard.getObjectivesForEntity(entityName);
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, objectivesForEntity.size());
            if (objectivesForEntity.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { entityName });
            }
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { objectivesForEntity.size(), entityName });
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation);
            for (final Score score : objectivesForEntity.values()) {
                commandSender.addChatMessage(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { score.getScorePoints(), score.getObjective().getDisplayName(), score.getObjective().getName() }));
            }
        }
        else {
            final Collection objectiveNames = scoreboard.getObjectiveNames();
            commandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, objectiveNames.size());
            if (objectiveNames.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }
            final ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { objectiveNames.size() });
            chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            commandSender.addChatMessage(chatComponentTranslation2);
            commandSender.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(objectiveNames.toArray())));
        }
    }
    
    protected void resetPlayers(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String entityName = CommandBase.getEntityName(commandSender, array[n++]);
        if (array.length > n) {
            final ScoreObjective objective = this.getObjective(array[n++], false);
            scoreboard.removeObjectiveFromEntity(entityName, objective);
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.resetscore.success", objective.getName(), entityName);
        }
        else {
            scoreboard.removeObjectiveFromEntity(entityName, null);
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.reset.success", entityName);
        }
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    protected void removeObjective(final ICommandSender commandSender, final String s) throws CommandException {
        this.getScoreboard().removeObjective(this.getObjective(s, false));
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.objectives.remove.success", s);
    }
    
    @Override
    public String getCommandName() {
        return "scoreboard";
    }
    
    protected void addTeam(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final String s = array[n++];
        final Scoreboard scoreboard = this.getScoreboard();
        if (scoreboard.getTeam(s) != null) {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { s });
        }
        if (s.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { s, 16 });
        }
        if (s.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
        }
        if (array.length > n) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, n).getUnformattedText();
            if (unformattedText.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { unformattedText, 32 });
            }
            if (unformattedText.length() > 0) {
                scoreboard.createTeam(s).setTeamName(unformattedText);
            }
            else {
                scoreboard.createTeam(s);
            }
        }
        else {
            scoreboard.createTeam(s);
        }
        CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.add.success", s);
    }
    
    protected void removeTeam(final ICommandSender commandSender, final String[] array, final int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final ScorePlayerTeam team = this.getTeam(array[n]);
        if (team != null) {
            scoreboard.removeTeam(team);
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.remove.success", team.getRegisteredName());
        }
    }
    
    protected ScoreObjective getObjective(final String s, final boolean b) throws CommandException {
        final ScoreObjective objective = this.getScoreboard().getObjective(s);
        if (objective == null) {
            throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { s });
        }
        if (b && objective.getCriteria().isReadOnly()) {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { s });
        }
        return objective;
    }
    
    protected void func_175781_o(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String entityName = CommandBase.getEntityName(commandSender, array[n++]);
        if (entityName.length() > 40) {
            throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { entityName, 40 });
        }
        final ScoreObjective objective = this.getObjective(array[n++], false);
        if (!scoreboard.entityHasObjective(entityName, objective)) {
            throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { objective.getName(), entityName });
        }
        final int n2 = array[n].equals("*") ? Integer.MIN_VALUE : CommandBase.parseInt(array[n]);
        final int n3 = (++n < array.length && !array[n].equals("*")) ? CommandBase.parseInt(array[n], n2) : Integer.MAX_VALUE;
        final Score valueFromObjective = scoreboard.getValueFromObjective(entityName, objective);
        if (valueFromObjective.getScorePoints() >= n2 && valueFromObjective.getScorePoints() <= n3) {
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.players.test.success", valueFromObjective.getScorePoints(), n2, n3);
            return;
        }
        throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { valueFromObjective.getScorePoints(), n2, n3 });
    }
    
    protected void setTeamOption(final ICommandSender commandSender, final String[] array, int n) throws CommandException {
        final ScorePlayerTeam team = this.getTeam(array[n++]);
        if (team != null) {
            final String lowerCase = array[n++].toLowerCase();
            if (!lowerCase.equalsIgnoreCase("color") && !lowerCase.equalsIgnoreCase("friendlyfire") && !lowerCase.equalsIgnoreCase("seeFriendlyInvisibles") && !lowerCase.equalsIgnoreCase("nametagVisibility") && !lowerCase.equalsIgnoreCase("deathMessageVisibility")) {
                throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
            }
            if (array.length == 4) {
                if (lowerCase.equalsIgnoreCase("color")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
                }
                if (lowerCase.equalsIgnoreCase("friendlyfire") || lowerCase.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
                }
                if (!lowerCase.equalsIgnoreCase("nametagVisibility") && !lowerCase.equalsIgnoreCase("deathMessageVisibility")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceString(Team.EnumVisible.func_178825_a()) });
            }
            else {
                final String s = array[n];
                if (lowerCase.equalsIgnoreCase("color")) {
                    final EnumChatFormatting valueByName = EnumChatFormatting.getValueByName(s);
                    if (valueByName == null || valueByName.isFancyStyling()) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
                    }
                    team.setChatFormat(valueByName);
                    team.setNamePrefix(valueByName.toString());
                    team.setNameSuffix(EnumChatFormatting.RESET.toString());
                }
                else if (lowerCase.equalsIgnoreCase("friendlyfire")) {
                    if (!s.equalsIgnoreCase("true") && !s.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
                    }
                    team.setAllowFriendlyFire(s.equalsIgnoreCase("true"));
                }
                else if (lowerCase.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!s.equalsIgnoreCase("true") && !s.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
                    }
                    team.setSeeFriendlyInvisiblesEnabled(s.equalsIgnoreCase("true"));
                }
                else if (lowerCase.equalsIgnoreCase("nametagVisibility")) {
                    final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(s);
                    if (func_178824_a == null) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceString(Team.EnumVisible.func_178825_a()) });
                    }
                    team.setNameTagVisibility(func_178824_a);
                }
                else if (lowerCase.equalsIgnoreCase("deathMessageVisibility")) {
                    final Team.EnumVisible func_178824_a2 = Team.EnumVisible.func_178824_a(s);
                    if (func_178824_a2 == null) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { lowerCase, CommandBase.joinNiceString(Team.EnumVisible.func_178825_a()) });
                    }
                    team.setDeathMessageVisibility(func_178824_a2);
                }
                CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.option.success", lowerCase, team.getRegisteredName(), s);
            }
        }
    }
    
    protected ScorePlayerTeam getTeam(final String s) throws CommandException {
        final ScorePlayerTeam team = this.getScoreboard().getTeam(s);
        if (team == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { s });
        }
        return team;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        if (array.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(array, "objectives", "players", "teams");
        }
        if (array[0].equalsIgnoreCase("objectives")) {
            if (array.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(array, "list", "add", "remove", "setdisplay");
            }
            if (array[1].equalsIgnoreCase("add")) {
                if (array.length == 4) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, IScoreObjectiveCriteria.INSTANCES.keySet());
                }
            }
            else if (array[1].equalsIgnoreCase("remove")) {
                if (array.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a(false));
                }
            }
            else if (array[1].equalsIgnoreCase("setdisplay")) {
                if (array.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, Scoreboard.getDisplaySlotStrings());
                }
                if (array.length == 4) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a(false));
                }
            }
        }
        else if (array[0].equalsIgnoreCase("players")) {
            if (array.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(array, "set", "add", "remove", "reset", "list", "enable", "test", "operation");
            }
            if (!array[1].equalsIgnoreCase("set") && !array[1].equalsIgnoreCase("add") && !array[1].equalsIgnoreCase("remove") && !array[1].equalsIgnoreCase("reset")) {
                if (array[1].equalsIgnoreCase("enable")) {
                    if (array.length == 3) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                    }
                    if (array.length == 4) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, this.func_175782_e());
                    }
                }
                else if (!array[1].equalsIgnoreCase("list") && !array[1].equalsIgnoreCase("test")) {
                    if (array[1].equalsIgnoreCase("operation")) {
                        if (array.length == 3) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getObjectiveNames());
                        }
                        if (array.length == 4) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a(true));
                        }
                        if (array.length == 5) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><");
                        }
                        if (array.length == 6) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                        }
                        if (array.length == 7) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a(false));
                        }
                    }
                }
                else {
                    if (array.length == 3) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getObjectiveNames());
                    }
                    if (array.length == 4 && array[1].equalsIgnoreCase("test")) {
                        return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a(false));
                    }
                }
            }
            else {
                if (array.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
                if (array.length == 4) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.func_147184_a(true));
                }
            }
        }
        else if (array[0].equalsIgnoreCase("teams")) {
            if (array.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(array, "add", "remove", "join", "leave", "empty", "list", "option");
            }
            if (array[1].equalsIgnoreCase("join")) {
                if (array.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getTeamNames());
                }
                if (array.length >= 4) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
            }
            else {
                if (array[1].equalsIgnoreCase("leave")) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
                }
                if (!array[1].equalsIgnoreCase("empty") && !array[1].equalsIgnoreCase("list") && !array[1].equalsIgnoreCase("remove")) {
                    if (array[1].equalsIgnoreCase("option")) {
                        if (array.length == 3) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getTeamNames());
                        }
                        if (array.length == 4) {
                            return CommandBase.getListOfStringsMatchingLastWord(array, "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility");
                        }
                        if (array.length == 5) {
                            if (array[3].equalsIgnoreCase("color")) {
                                return CommandBase.getListOfStringsMatchingLastWord(array, EnumChatFormatting.getValidValues(true, false));
                            }
                            if (array[3].equalsIgnoreCase("nametagVisibility") || array[3].equalsIgnoreCase("deathMessageVisibility")) {
                                return CommandBase.getListOfStringsMatchingLastWord(array, Team.EnumVisible.func_178825_a());
                            }
                            if (array[3].equalsIgnoreCase("friendlyfire") || array[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                return CommandBase.getListOfStringsMatchingLastWord(array, "true", "false");
                            }
                        }
                    }
                }
                else if (array.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(array, this.getScoreboard().getTeamNames());
                }
            }
        }
        return null;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return "commands.scoreboard.usage";
    }
    
    protected void joinTeam(final ICommandSender commandSender, final String[] array, int i) throws CommandException {
        final Scoreboard scoreboard = this.getScoreboard();
        final String s = array[i++];
        final HashSet hashSet = Sets.newHashSet();
        final HashSet hashSet2 = Sets.newHashSet();
        if (commandSender instanceof EntityPlayer && i == array.length) {
            final String name = CommandBase.getCommandSenderAsPlayer(commandSender).getName();
            if (scoreboard.addPlayerToTeam(name, s)) {
                hashSet.add(name);
            }
            else {
                hashSet2.add(name);
            }
        }
        else {
            while (i < array.length) {
                final String s2 = array[i++];
                if (s2.startsWith("@")) {
                    final Iterator iterator = CommandBase.func_175763_c(commandSender, s2).iterator();
                    while (iterator.hasNext()) {
                        final String entityName = CommandBase.getEntityName(commandSender, iterator.next().getUniqueID().toString());
                        if (scoreboard.addPlayerToTeam(entityName, s)) {
                            hashSet.add(entityName);
                        }
                        else {
                            hashSet2.add(entityName);
                        }
                    }
                }
                else {
                    final String entityName2 = CommandBase.getEntityName(commandSender, s2);
                    if (scoreboard.addPlayerToTeam(entityName2, s)) {
                        hashSet.add(entityName2);
                    }
                    else {
                        hashSet2.add(entityName2);
                    }
                }
            }
        }
        if (!hashSet.isEmpty()) {
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, hashSet.size());
            CommandBase.notifyOperators(commandSender, this, "commands.scoreboard.teams.join.success", hashSet.size(), s, CommandBase.joinNiceString(hashSet.toArray(new String[hashSet.size()])));
        }
        if (!hashSet2.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { hashSet2.size(), s, CommandBase.joinNiceString(hashSet2.toArray(new String[hashSet2.size()])) });
        }
    }
}
