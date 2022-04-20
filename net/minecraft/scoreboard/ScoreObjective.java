package net.minecraft.scoreboard;

public class ScoreObjective
{
    private final Scoreboard theScoreboard;
    private IScoreObjectiveCriteria.EnumRenderType renderType;
    private final String name;
    private String displayName;
    private final IScoreObjectiveCriteria objectiveCriteria;
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public ScoreObjective(final Scoreboard theScoreboard, final String s, final IScoreObjectiveCriteria objectiveCriteria) {
        this.theScoreboard = theScoreboard;
        this.name = s;
        this.objectiveCriteria = objectiveCriteria;
        this.displayName = s;
        this.renderType = objectiveCriteria.getRenderType();
    }
    
    public void setRenderType(final IScoreObjectiveCriteria.EnumRenderType renderType) {
        this.renderType = renderType;
        this.theScoreboard.func_96532_b(this);
    }
    
    public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
        return this.renderType;
    }
    
    public Scoreboard getScoreboard() {
        return this.theScoreboard;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
        this.theScoreboard.func_96532_b(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public IScoreObjectiveCriteria getCriteria() {
        return this.objectiveCriteria;
    }
}
