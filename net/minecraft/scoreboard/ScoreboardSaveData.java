package net.minecraft.scoreboard;

import net.minecraft.world.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class ScoreboardSaveData extends WorldSavedData
{
    private static final Logger logger;
    private NBTTagCompound delayedInitNbt;
    private Scoreboard theScoreboard;
    
    @Override
    public void readFromNBT(final NBTTagCompound delayedInitNbt) {
        if (this.theScoreboard == null) {
            this.delayedInitNbt = delayedInitNbt;
        }
        else {
            this.readObjectives(delayedInitNbt.getTagList("Objectives", 10));
            this.readScores(delayedInitNbt.getTagList("PlayerScores", 10));
            if (delayedInitNbt.hasKey("DisplaySlots", 10)) {
                this.readDisplayConfig(delayedInitNbt.getCompoundTag("DisplaySlots"));
            }
            if (delayedInitNbt.hasKey("Teams", 9)) {
                this.readTeams(delayedInitNbt.getTagList("Teams", 10));
            }
        }
    }
    
    public void setScoreboard(final Scoreboard theScoreboard) {
        this.theScoreboard = theScoreboard;
        if (this.delayedInitNbt != null) {
            this.readFromNBT(this.delayedInitNbt);
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    protected NBTTagList scoresToNbt() {
        final NBTTagList list = new NBTTagList();
        for (final Score score : this.theScoreboard.getScores()) {
            if (score.getObjective() != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString("Name", score.getPlayerName());
                nbtTagCompound.setString("Objective", score.getObjective().getName());
                nbtTagCompound.setInteger("Score", score.getScorePoints());
                nbtTagCompound.setBoolean("Locked", score.isLocked());
                list.appendTag(nbtTagCompound);
            }
        }
        return list;
    }
    
    protected void readScores(final NBTTagList list) {
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final ScoreObjective objective = this.theScoreboard.getObjective(compoundTag.getString("Objective"));
            String s = compoundTag.getString("Name");
            if (s.length() > 40) {
                s = s.substring(0, 40);
            }
            final Score valueFromObjective = this.theScoreboard.getValueFromObjective(s, objective);
            valueFromObjective.setScorePoints(compoundTag.getInteger("Score"));
            if (compoundTag.hasKey("Locked")) {
                valueFromObjective.setLocked(compoundTag.getBoolean("Locked"));
            }
            int n = 0;
            ++n;
        }
    }
    
    public ScoreboardSaveData(final String s) {
        super(s);
    }
    
    protected NBTTagList objectivesToNbt() {
        final NBTTagList list = new NBTTagList();
        for (final ScoreObjective scoreObjective : this.theScoreboard.getScoreObjectives()) {
            if (scoreObjective.getCriteria() != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setString("Name", scoreObjective.getName());
                nbtTagCompound.setString("CriteriaName", scoreObjective.getCriteria().getName());
                nbtTagCompound.setString("DisplayName", scoreObjective.getDisplayName());
                nbtTagCompound.setString("RenderType", scoreObjective.getRenderType().func_178796_a());
                list.appendTag(nbtTagCompound);
            }
        }
        return list;
    }
    
    protected void readObjectives(final NBTTagList list) {
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            final IScoreObjectiveCriteria scoreObjectiveCriteria = IScoreObjectiveCriteria.INSTANCES.get(compoundTag.getString("CriteriaName"));
            if (scoreObjectiveCriteria != null) {
                String s = compoundTag.getString("Name");
                if (s.length() > 16) {
                    s = s.substring(0, 16);
                }
                final ScoreObjective addScoreObjective = this.theScoreboard.addScoreObjective(s, scoreObjectiveCriteria);
                addScoreObjective.setDisplayName(compoundTag.getString("DisplayName"));
                addScoreObjective.setRenderType(IScoreObjectiveCriteria.EnumRenderType.func_178795_a(compoundTag.getString("RenderType")));
            }
            int n = 0;
            ++n;
        }
    }
    
    public ScoreboardSaveData() {
        this("scoreboard");
    }
    
    protected NBTTagList func_96496_a() {
        final NBTTagList list = new NBTTagList();
        for (final ScorePlayerTeam scorePlayerTeam : this.theScoreboard.getTeams()) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setString("Name", scorePlayerTeam.getRegisteredName());
            nbtTagCompound.setString("DisplayName", scorePlayerTeam.getTeamName());
            if (scorePlayerTeam.getChatFormat().getColorIndex() >= 0) {
                nbtTagCompound.setString("TeamColor", scorePlayerTeam.getChatFormat().getFriendlyName());
            }
            nbtTagCompound.setString("Prefix", scorePlayerTeam.getColorPrefix());
            nbtTagCompound.setString("Suffix", scorePlayerTeam.getColorSuffix());
            nbtTagCompound.setBoolean("AllowFriendlyFire", scorePlayerTeam.getAllowFriendlyFire());
            nbtTagCompound.setBoolean("SeeFriendlyInvisibles", scorePlayerTeam.getSeeFriendlyInvisiblesEnabled());
            nbtTagCompound.setString("NameTagVisibility", scorePlayerTeam.getNameTagVisibility().field_178830_e);
            nbtTagCompound.setString("DeathMessageVisibility", scorePlayerTeam.getDeathMessageVisibility().field_178830_e);
            final NBTTagList list2 = new NBTTagList();
            final Iterator iterator2 = scorePlayerTeam.getMembershipCollection().iterator();
            while (iterator2.hasNext()) {
                list2.appendTag(new NBTTagString(iterator2.next()));
            }
            nbtTagCompound.setTag("Players", list2);
            list.appendTag(nbtTagCompound);
        }
        return list;
    }
    
    protected void readTeams(final NBTTagList list) {
        while (0 < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(0);
            String s = compoundTag.getString("Name");
            if (s.length() > 16) {
                s = s.substring(0, 16);
            }
            final ScorePlayerTeam team = this.theScoreboard.createTeam(s);
            String teamName = compoundTag.getString("DisplayName");
            if (teamName.length() > 32) {
                teamName = teamName.substring(0, 32);
            }
            team.setTeamName(teamName);
            if (compoundTag.hasKey("TeamColor", 8)) {
                team.setChatFormat(EnumChatFormatting.getValueByName(compoundTag.getString("TeamColor")));
            }
            team.setNamePrefix(compoundTag.getString("Prefix"));
            team.setNameSuffix(compoundTag.getString("Suffix"));
            if (compoundTag.hasKey("AllowFriendlyFire", 99)) {
                team.setAllowFriendlyFire(compoundTag.getBoolean("AllowFriendlyFire"));
            }
            if (compoundTag.hasKey("SeeFriendlyInvisibles", 99)) {
                team.setSeeFriendlyInvisiblesEnabled(compoundTag.getBoolean("SeeFriendlyInvisibles"));
            }
            if (compoundTag.hasKey("NameTagVisibility", 8)) {
                final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(compoundTag.getString("NameTagVisibility"));
                if (func_178824_a != null) {
                    team.setNameTagVisibility(func_178824_a);
                }
            }
            if (compoundTag.hasKey("DeathMessageVisibility", 8)) {
                final Team.EnumVisible func_178824_a2 = Team.EnumVisible.func_178824_a(compoundTag.getString("DeathMessageVisibility"));
                if (func_178824_a2 != null) {
                    team.setDeathMessageVisibility(func_178824_a2);
                }
            }
            this.func_96502_a(team, compoundTag.getTagList("Players", 8));
            int n = 0;
            ++n;
        }
    }
    
    protected void func_96502_a(final ScorePlayerTeam scorePlayerTeam, final NBTTagList list) {
        while (0 < list.tagCount()) {
            this.theScoreboard.addPlayerToTeam(list.getStringTagAt(0), scorePlayerTeam.getRegisteredName());
            int n = 0;
            ++n;
        }
    }
    
    protected void func_96497_d(final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        while (true) {
            final ScoreObjective objectiveInDisplaySlot = this.theScoreboard.getObjectiveInDisplaySlot(0);
            if (objectiveInDisplaySlot != null) {
                nbtTagCompound2.setString("slot_" + 0, objectiveInDisplaySlot.getName());
            }
            int n = 0;
            ++n;
        }
    }
    
    protected void readDisplayConfig(final NBTTagCompound nbtTagCompound) {
        while (true) {
            if (nbtTagCompound.hasKey("slot_" + 0, 8)) {
                this.theScoreboard.setObjectiveInDisplaySlot(0, this.theScoreboard.getObjective(nbtTagCompound.getString("slot_" + 0)));
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        if (this.theScoreboard == null) {
            ScoreboardSaveData.logger.warn("Tried to save scoreboard without having a scoreboard...");
        }
        else {
            nbtTagCompound.setTag("Objectives", this.objectivesToNbt());
            nbtTagCompound.setTag("PlayerScores", this.scoresToNbt());
            nbtTagCompound.setTag("Teams", this.func_96496_a());
            this.func_96497_d(nbtTagCompound);
        }
    }
}
