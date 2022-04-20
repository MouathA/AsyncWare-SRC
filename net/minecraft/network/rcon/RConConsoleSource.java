package net.minecraft.network.rcon;

import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;

public class RConConsoleSource implements ICommandSender
{
    private StringBuffer buffer;
    private static final RConConsoleSource instance;
    
    @Override
    public Vec3 getPositionVector() {
        return new Vec3(0.0, 0.0, 0.0);
    }
    
    @Override
    public void setCommandStat(final CommandResultStats.Type type, final int n) {
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return true;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        this.buffer.append(chatComponent.getUnformattedText());
    }
    
    @Override
    public Entity getCommandSenderEntity() {
        return null;
    }
    
    @Override
    public String getName() {
        return "Rcon";
    }
    
    public RConConsoleSource() {
        this.buffer = new StringBuffer();
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(0, 0, 0);
    }
    
    static {
        instance = new RConConsoleSource();
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return true;
    }
    
    @Override
    public World getEntityWorld() {
        return MinecraftServer.getServer().getEntityWorld();
    }
}
