package net.minecraft.scoreboard;

import java.util.*;

public class Score
{
    private final ScoreObjective theScoreObjective;
    private final Scoreboard theScoreboard;
    public static final Comparator scoreComparator;
    private final String scorePlayerName;
    private boolean field_178818_g;
    private boolean locked;
    private int scorePoints;
    
    public String getPlayerName() {
        return this.scorePlayerName;
    }
    
    public int getScorePoints() {
        return this.scorePoints;
    }
    
    public void setLocked(final boolean locked) {
        this.locked = locked;
    }
    
    public boolean isLocked() {
        return this.locked;
    }
    
    public void decreaseScore(final int n) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.setScorePoints(this.getScorePoints() - n);
    }
    
    public Scoreboard getScoreScoreboard() {
        return this.theScoreboard;
    }
    
    public void setScorePoints(final int scorePoints) {
        final int scorePoints2 = this.scorePoints;
        this.scorePoints = scorePoints;
        if (scorePoints2 != scorePoints || this.field_178818_g) {
            this.field_178818_g = false;
            this.getScoreScoreboard().func_96536_a(this);
        }
    }
    
    public void increseScore(final int n) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.setScorePoints(this.getScorePoints() + n);
    }
    
    public void func_96651_a(final List list) {
        this.setScorePoints(this.theScoreObjective.getCriteria().func_96635_a(list));
    }
    
    public ScoreObjective getObjective() {
        return this.theScoreObjective;
    }
    
    public Score(final Scoreboard theScoreboard, final ScoreObjective theScoreObjective, final String scorePlayerName) {
        this.theScoreboard = theScoreboard;
        this.theScoreObjective = theScoreObjective;
        this.scorePlayerName = scorePlayerName;
        this.field_178818_g = true;
    }
    
    public void func_96648_a() {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.increseScore(1);
    }
    
    static {
        scoreComparator = new Comparator() {
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((Score)o, (Score)o2);
            }
            
            public int compare(final Score score, final Score score2) {
                return (score.getScorePoints() > score2.getScorePoints()) ? 1 : ((score.getScorePoints() < score2.getScorePoints()) ? -1 : score2.getPlayerName().compareToIgnoreCase(score.getPlayerName()));
            }
        };
    }
}
