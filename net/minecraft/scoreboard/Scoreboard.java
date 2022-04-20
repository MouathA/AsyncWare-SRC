package net.minecraft.scoreboard;

import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class Scoreboard
{
    private final Map teams;
    private final Map scoreObjectiveCriterias;
    private final Map scoreObjectives;
    private final Map teamMemberships;
    private final ScoreObjective[] objectiveDisplaySlots;
    private final Map entitiesScoreObjectives;
    
    public Map getObjectivesForEntity(final String s) {
        Map hashMap = this.entitiesScoreObjectives.get(s);
        if (hashMap == null) {
            hashMap = Maps.newHashMap();
        }
        return hashMap;
    }
    
    public void func_96533_c(final ScoreObjective scoreObjective) {
    }
    
    public Collection getObjectiveNames() {
        return this.entitiesScoreObjectives.keySet();
    }
    
    public ScoreObjective getObjective(final String s) {
        return this.scoreObjectives.get(s);
    }
    
    public ScorePlayerTeam createTeam(final String s) {
        if (s.length() > 16) {
            throw new IllegalArgumentException("The team name '" + s + "' is too long!");
        }
        if (this.getTeam(s) != null) {
            throw new IllegalArgumentException("A team with the name '" + s + "' already exists!");
        }
        final ScorePlayerTeam scorePlayerTeam = new ScorePlayerTeam(this, s);
        this.teams.put(s, scorePlayerTeam);
        this.broadcastTeamCreated(scorePlayerTeam);
        return scorePlayerTeam;
    }
    
    public Scoreboard() {
        this.scoreObjectives = Maps.newHashMap();
        this.scoreObjectiveCriterias = Maps.newHashMap();
        this.entitiesScoreObjectives = Maps.newHashMap();
        this.objectiveDisplaySlots = new ScoreObjective[19];
        this.teams = Maps.newHashMap();
        this.teamMemberships = Maps.newHashMap();
    }
    
    public void removeObjective(final ScoreObjective scoreObjective) {
        this.scoreObjectives.remove(scoreObjective.getName());
        while (true) {
            if (this.getObjectiveInDisplaySlot(0) == scoreObjective) {
                this.setObjectiveInDisplaySlot(0, null);
            }
            int n = 0;
            ++n;
        }
    }
    
    public boolean addPlayerToTeam(final String s, final String s2) {
        if (s.length() > 40) {
            throw new IllegalArgumentException("The player name '" + s + "' is too long!");
        }
        if (!this.teams.containsKey(s2)) {
            return false;
        }
        final ScorePlayerTeam team = this.getTeam(s2);
        if (this.getPlayersTeam(s) != null) {
            this.removePlayerFromTeams(s);
        }
        this.teamMemberships.put(s, team);
        team.getMembershipCollection().add(s);
        return true;
    }
    
    public Collection getTeamNames() {
        return this.teams.keySet();
    }
    
    public Collection getScores() {
        final Collection<Map<Object, ? extends E>> values = this.entitiesScoreObjectives.values();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Map<Object, ? extends E>> iterator = values.iterator();
        while (iterator.hasNext()) {
            arrayList.addAll(iterator.next().values());
        }
        return arrayList;
    }
    
    public void removePlayerFromTeam(final String s, final ScorePlayerTeam scorePlayerTeam) {
        if (this.getPlayersTeam(s) != scorePlayerTeam) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + scorePlayerTeam.getRegisteredName() + "'.");
        }
        this.teamMemberships.remove(s);
        scorePlayerTeam.getMembershipCollection().remove(s);
    }
    
    public void removeTeam(final ScorePlayerTeam scorePlayerTeam) {
        this.teams.remove(scorePlayerTeam.getRegisteredName());
        final Iterator<String> iterator = scorePlayerTeam.getMembershipCollection().iterator();
        while (iterator.hasNext()) {
            this.teamMemberships.remove(iterator.next());
        }
        this.func_96513_c(scorePlayerTeam);
    }
    
    public void func_96513_c(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public void func_178820_a(final String s, final ScoreObjective scoreObjective) {
    }
    
    public boolean entityHasObjective(final String s, final ScoreObjective scoreObjective) {
        final Map<Object, Score> map = this.entitiesScoreObjectives.get(s);
        return map != null && map.get(scoreObjective) != null;
    }
    
    public static String getObjectiveDisplaySlot(final int n) {
        switch (n) {
            case 0: {
                return "list";
            }
            case 1: {
                return "sidebar";
            }
            case 2: {
                return "belowName";
            }
            default: {
                if (n >= 3 && n <= 18) {
                    final EnumChatFormatting func_175744_a = EnumChatFormatting.func_175744_a(n - 3);
                    if (func_175744_a != null && func_175744_a != EnumChatFormatting.RESET) {
                        return "sidebar.team." + func_175744_a.getFriendlyName();
                    }
                }
                return null;
            }
        }
    }
    
    public void func_96516_a(final String s) {
    }
    
    public void func_96532_b(final ScoreObjective scoreObjective) {
    }
    
    public ScoreObjective addScoreObjective(final String s, final IScoreObjectiveCriteria scoreObjectiveCriteria) {
        if (s.length() > 16) {
            throw new IllegalArgumentException("The objective name '" + s + "' is too long!");
        }
        if (this.getObjective(s) != null) {
            throw new IllegalArgumentException("An objective with the name '" + s + "' already exists!");
        }
        final ScoreObjective scoreObjective = new ScoreObjective(this, s, scoreObjectiveCriteria);
        List<ScoreObjective> arrayList = this.scoreObjectiveCriterias.get(scoreObjectiveCriteria);
        if (arrayList == null) {
            arrayList = (List<ScoreObjective>)Lists.newArrayList();
            this.scoreObjectiveCriterias.put(scoreObjectiveCriteria, arrayList);
        }
        arrayList.add(scoreObjective);
        this.scoreObjectives.put(s, scoreObjective);
        this.onScoreObjectiveAdded(scoreObjective);
        return scoreObjective;
    }
    
    public static int getObjectiveDisplaySlotNumber(final String s) {
        if (s.equalsIgnoreCase("list")) {
            return 0;
        }
        if (s.equalsIgnoreCase("sidebar")) {
            return 1;
        }
        if (s.equalsIgnoreCase("belowName")) {
            return 2;
        }
        if (s.startsWith("sidebar.team.")) {
            final EnumChatFormatting valueByName = EnumChatFormatting.getValueByName(s.substring(13));
            if (valueByName != null && valueByName.getColorIndex() >= 0) {
                return valueByName.getColorIndex() + 3;
            }
        }
        return -1;
    }
    
    public void func_181140_a(final Entity entity) {
        if (entity != null && !(entity instanceof EntityPlayer) && !entity.isEntityAlive()) {
            final String string = entity.getUniqueID().toString();
            this.removeObjectiveFromEntity(string, null);
            this.removePlayerFromTeams(string);
        }
    }
    
    public static String[] getDisplaySlotStrings() {
        if (Scoreboard.field_178823_g != null) {
            return Scoreboard.field_178823_g;
        }
        Scoreboard.field_178823_g = new String[19];
        while (true) {
            Scoreboard.field_178823_g[0] = getObjectiveDisplaySlot(0);
            int n = 0;
            ++n;
        }
    }
    
    public ScorePlayerTeam getTeam(final String s) {
        return this.teams.get(s);
    }
    
    public void sendTeamUpdate(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public void setObjectiveInDisplaySlot(final int n, final ScoreObjective scoreObjective) {
        this.objectiveDisplaySlots[n] = scoreObjective;
    }
    
    public void broadcastTeamCreated(final ScorePlayerTeam scorePlayerTeam) {
    }
    
    public Score getValueFromObjective(final String s, final ScoreObjective scoreObjective) {
        if (s.length() > 40) {
            throw new IllegalArgumentException("The player name '" + s + "' is too long!");
        }
        Map<Object, Score> hashMap = this.entitiesScoreObjectives.get(s);
        if (hashMap == null) {
            hashMap = (Map<Object, Score>)Maps.newHashMap();
            this.entitiesScoreObjectives.put(s, hashMap);
        }
        Score score = hashMap.get(scoreObjective);
        if (score == null) {
            score = new Score(this, scoreObjective, s);
            hashMap.put(scoreObjective, score);
        }
        return score;
    }
    
    public Collection getTeams() {
        return this.teams.values();
    }
    
    public Collection getObjectivesFromCriteria(final IScoreObjectiveCriteria scoreObjectiveCriteria) {
        final Collection collection = this.scoreObjectiveCriterias.get(scoreObjectiveCriteria);
        return (collection == null) ? Lists.newArrayList() : Lists.newArrayList((Iterable)collection);
    }
    
    public Collection getSortedScores(final ScoreObjective scoreObjective) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Map<Object, Score>> iterator = this.entitiesScoreObjectives.values().iterator();
        while (iterator.hasNext()) {
            final Score score = iterator.next().get(scoreObjective);
            if (score != null) {
                arrayList.add(score);
            }
        }
        Collections.sort((List<Object>)arrayList, Score.scoreComparator);
        return arrayList;
    }
    
    public void onScoreObjectiveAdded(final ScoreObjective scoreObjective) {
    }
    
    public void func_96536_a(final Score score) {
    }
    
    public ScoreObjective getObjectiveInDisplaySlot(final int n) {
        return this.objectiveDisplaySlots[n];
    }
    
    public void removeObjectiveFromEntity(final String s, final ScoreObjective scoreObjective) {
        if (scoreObjective == null) {
            if (this.entitiesScoreObjectives.remove(s) != null) {
                this.func_96516_a(s);
            }
        }
        else {
            final Map<Object, Score> map = this.entitiesScoreObjectives.get(s);
            if (map != null) {
                final Score score = map.remove(scoreObjective);
                if (map.size() < 1) {
                    if (this.entitiesScoreObjectives.remove(s) != null) {
                        this.func_96516_a(s);
                    }
                }
                else if (score != null) {
                    this.func_178820_a(s, scoreObjective);
                }
            }
        }
    }
    
    public boolean removePlayerFromTeams(final String s) {
        final ScorePlayerTeam playersTeam = this.getPlayersTeam(s);
        if (playersTeam != null) {
            this.removePlayerFromTeam(s, playersTeam);
            return true;
        }
        return false;
    }
    
    public ScorePlayerTeam getPlayersTeam(final String s) {
        return this.teamMemberships.get(s);
    }
    
    static {
        Scoreboard.field_178823_g = null;
    }
    
    public Collection getScoreObjectives() {
        return this.scoreObjectives.values();
    }
}
