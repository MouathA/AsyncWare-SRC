package net.minecraft.client.multiplayer;

import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class ServerData
{
    private boolean field_181042_l;
    public String serverMOTD;
    public String serverName;
    public String gameVersion;
    public String serverIP;
    public long pingToServer;
    private ServerResourceMode resourceMode;
    public String populationInfo;
    public int version;
    private String serverIcon;
    public boolean field_78841_f;
    public String playerList;
    
    public ServerResourceMode getResourceMode() {
        return this.resourceMode;
    }
    
    public String getBase64EncodedIconData() {
        return this.serverIcon;
    }
    
    public boolean func_181041_d() {
        return this.field_181042_l;
    }
    
    public NBTTagCompound getNBTCompound() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("name", this.serverName);
        nbtTagCompound.setString("ip", this.serverIP);
        if (this.serverIcon != null) {
            nbtTagCompound.setString("icon", this.serverIcon);
        }
        if (this.resourceMode == ServerResourceMode.ENABLED) {
            nbtTagCompound.setBoolean("acceptTextures", true);
        }
        else if (this.resourceMode == ServerResourceMode.DISABLED) {
            nbtTagCompound.setBoolean("acceptTextures", false);
        }
        return nbtTagCompound;
    }
    
    public void setResourceMode(final ServerResourceMode resourceMode) {
        this.resourceMode = resourceMode;
    }
    
    public void copyFrom(final ServerData serverData) {
        this.serverIP = serverData.serverIP;
        this.serverName = serverData.serverName;
        this.setResourceMode(serverData.getResourceMode());
        this.serverIcon = serverData.serverIcon;
        this.field_181042_l = serverData.field_181042_l;
    }
    
    public static ServerData getServerDataFromNBTCompound(final NBTTagCompound nbtTagCompound) {
        final ServerData serverData = new ServerData(nbtTagCompound.getString("name"), nbtTagCompound.getString("ip"), false);
        if (nbtTagCompound.hasKey("icon", 8)) {
            serverData.setBase64EncodedIconData(nbtTagCompound.getString("icon"));
        }
        if (nbtTagCompound.hasKey("acceptTextures", 1)) {
            if (nbtTagCompound.getBoolean("acceptTextures")) {
                serverData.setResourceMode(ServerResourceMode.ENABLED);
            }
            else {
                serverData.setResourceMode(ServerResourceMode.DISABLED);
            }
        }
        else {
            serverData.setResourceMode(ServerResourceMode.PROMPT);
        }
        return serverData;
    }
    
    public void setBase64EncodedIconData(final String serverIcon) {
        this.serverIcon = serverIcon;
    }
    
    public ServerData(final String serverName, final String serverIP, final boolean field_181042_l) {
        this.version = 47;
        this.gameVersion = "1.8.8";
        this.resourceMode = ServerResourceMode.PROMPT;
        this.serverName = serverName;
        this.serverIP = serverIP;
        this.field_181042_l = field_181042_l;
    }
    
    public enum ServerResourceMode
    {
        PROMPT("PROMPT", 2, "prompt"), 
        ENABLED("ENABLED", 0, "enabled"), 
        DISABLED("DISABLED", 1, "disabled");
        
        private final IChatComponent motd;
        private static final ServerResourceMode[] $VALUES;
        
        private ServerResourceMode(final String s, final int n, final String s2) {
            this.motd = new ChatComponentTranslation("addServer.resourcePack." + s2, new Object[0]);
        }
        
        public IChatComponent getMotd() {
            return this.motd;
        }
        
        static {
            $VALUES = new ServerResourceMode[] { ServerResourceMode.ENABLED, ServerResourceMode.DISABLED, ServerResourceMode.PROMPT };
        }
    }
}
