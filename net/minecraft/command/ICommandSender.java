package net.minecraft.command;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public interface ICommandSender
{
    void addChatMessage(final IChatComponent p0);
    
    boolean canCommandSenderUseCommand(final int p0, final String p1);
    
    BlockPos getPosition();
    
    World getEntityWorld();
    
    void setCommandStat(final CommandResultStats.Type p0, final int p1);
    
    IChatComponent getDisplayName();
    
    Vec3 getPositionVector();
    
    String getName();
    
    Entity getCommandSenderEntity();
    
    boolean sendCommandFeedback();
}
