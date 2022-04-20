package net.minecraft.command.server;

import java.text.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import java.util.*;
import io.netty.buffer.*;

public abstract class CommandBlockLogic implements ICommandSender
{
    private String commandStored;
    private IChatComponent lastOutput;
    private final CommandResultStats resultStats;
    private boolean trackOutput;
    private String customName;
    private int successCount;
    private static final SimpleDateFormat timestampFormat;
    
    public CommandResultStats getCommandResultStats() {
        return this.resultStats;
    }
    
    public CommandBlockLogic() {
        this.trackOutput = true;
        this.lastOutput = null;
        this.commandStored = "";
        this.customName = "@";
        this.resultStats = new CommandResultStats();
    }
    
    @Override
    public String getName() {
        return this.customName;
    }
    
    public void trigger(final World world) {
        if (world.isRemote) {
            this.successCount = 0;
        }
        final MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.isAnvilFileSet() && server.isCommandBlockEnabled()) {
            final ICommandManager commandManager = server.getCommandManager();
            this.lastOutput = null;
            this.successCount = commandManager.executeCommand(this, this.commandStored);
        }
        else {
            this.successCount = 0;
        }
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }
    
    public String getCommand() {
        return this.commandStored;
    }
    
    public boolean tryOpenEditCommandBlock(final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            return false;
        }
        if (entityPlayer.getEntityWorld().isRemote) {
            entityPlayer.openEditCommandBlock(this);
        }
        return true;
    }
    
    public void setName(final String customName) {
        this.customName = customName;
    }
    
    public void setTrackOutput(final boolean trackOutput) {
        this.trackOutput = trackOutput;
    }
    
    public void setCommand(final String commandStored) {
        this.commandStored = commandStored;
        this.successCount = 0;
    }
    
    public void writeDataToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setString("Command", this.commandStored);
        nbtTagCompound.setInteger("SuccessCount", this.successCount);
        nbtTagCompound.setString("CustomName", this.customName);
        nbtTagCompound.setBoolean("TrackOutput", this.trackOutput);
        if (this.lastOutput != null && this.trackOutput) {
            nbtTagCompound.setString("LastOutput", IChatComponent.Serializer.componentToJson(this.lastOutput));
        }
        this.resultStats.writeStatsToNBT(nbtTagCompound);
    }
    
    public boolean shouldTrackOutput() {
        return this.trackOutput;
    }
    
    static {
        timestampFormat = new SimpleDateFormat("HH:mm:ss");
    }
    
    public abstract int func_145751_f();
    
    public void setLastOutput(final IChatComponent lastOutput) {
        this.lastOutput = lastOutput;
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        if (this.trackOutput && this.getEntityWorld() != null && !this.getEntityWorld().isRemote) {
            this.lastOutput = new ChatComponentText("[" + CommandBlockLogic.timestampFormat.format(new Date()) + "] ").appendSibling(chatComponent);
            this.updateCommand();
        }
    }
    
    @Override
    public void setCommandStat(final CommandResultStats.Type type, final int n) {
        this.resultStats.func_179672_a(this, type, n);
    }
    
    public int getSuccessCount() {
        return this.successCount;
    }
    
    public void readDataFromNBT(final NBTTagCompound nbtTagCompound) {
        this.commandStored = nbtTagCompound.getString("Command");
        this.successCount = nbtTagCompound.getInteger("SuccessCount");
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }
        if (nbtTagCompound.hasKey("TrackOutput", 1)) {
            this.trackOutput = nbtTagCompound.getBoolean("TrackOutput");
        }
        if (nbtTagCompound.hasKey("LastOutput", 8) && this.trackOutput) {
            this.lastOutput = IChatComponent.Serializer.jsonToComponent(nbtTagCompound.getString("LastOutput"));
        }
        this.resultStats.readStatsFromNBT(nbtTagCompound);
    }
    
    @Override
    public boolean sendCommandFeedback() {
        final MinecraftServer server = MinecraftServer.getServer();
        return server == null || !server.isAnvilFileSet() || server.worldServers[0].getGameRules().getBoolean("commandBlockOutput");
    }
    
    public abstract void func_145757_a(final ByteBuf p0);
    
    public IChatComponent getLastOutput() {
        return this.lastOutput;
    }
    
    public abstract void updateCommand();
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        return n <= 2;
    }
}
