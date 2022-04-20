package net.minecraft.util;

import java.util.*;
import net.minecraft.server.*;
import net.minecraft.scoreboard.*;

public class ChatComponentScore extends ChatComponentStyle
{
    private final String objective;
    private String value;
    private final String name;
    
    @Override
    public ChatComponentScore createCopy() {
        final ChatComponentScore chatComponentScore = new ChatComponentScore(this.name, this.objective);
        chatComponentScore.setValue(this.value);
        chatComponentScore.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        while (iterator.hasNext()) {
            chatComponentScore.appendSibling(iterator.next().createCopy());
        }
        return chatComponentScore;
    }
    
    @Override
    public String toString() {
        return "ScoreComponent{name='" + this.name + '\'' + "objective='" + this.objective + '\'' + ", siblings=" + this.siblings + ", style=" + this.getChatStyle() + '}';
    }
    
    public String getObjective() {
        return this.objective;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.createCopy();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatComponentScore)) {
            return false;
        }
        final ChatComponentScore chatComponentScore = (ChatComponentScore)o;
        return this.name.equals(chatComponentScore.name) && this.objective.equals(chatComponentScore.objective) && super.equals(o);
    }
    
    @Override
    public String getUnformattedTextForChat() {
        final MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.isAnvilFileSet() && StringUtils.isNullOrEmpty(this.value)) {
            final Scoreboard scoreboard = server.worldServerForDimension(0).getScoreboard();
            final ScoreObjective objective = scoreboard.getObjective(this.objective);
            if (scoreboard.entityHasObjective(this.name, objective)) {
                this.setValue(String.format("%d", scoreboard.getValueFromObjective(this.name, objective).getScorePoints()));
            }
            else {
                this.value = "";
            }
        }
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public ChatComponentScore(final String name, final String objective) {
        this.value = "";
        this.name = name;
        this.objective = objective;
    }
}
