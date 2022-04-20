package net.minecraft.client.main;

import com.mojang.authlib.properties.*;
import net.minecraft.util.*;
import java.net.*;
import java.io.*;

public class GameConfiguration
{
    public final FolderInformation folderInfo;
    public final ServerInformation serverInfo;
    public final UserInformation userInfo;
    public final GameInformation gameInfo;
    public final DisplayInformation displayInfo;
    
    public GameConfiguration(final UserInformation userInfo, final DisplayInformation displayInfo, final FolderInformation folderInfo, final GameInformation gameInfo, final ServerInformation serverInfo) {
        this.userInfo = userInfo;
        this.displayInfo = displayInfo;
        this.folderInfo = folderInfo;
        this.gameInfo = gameInfo;
        this.serverInfo = serverInfo;
    }
    
    public static class GameInformation
    {
        public final boolean isDemo;
        public final String version;
        
        public GameInformation(final boolean isDemo, final String version) {
            this.isDemo = isDemo;
            this.version = version;
        }
    }
    
    public static class UserInformation
    {
        public final PropertyMap userProperties;
        public final PropertyMap field_181172_c;
        public final Session session;
        public final Proxy proxy;
        
        public UserInformation(final Session session, final PropertyMap userProperties, final PropertyMap field_181172_c, final Proxy proxy) {
            this.session = session;
            this.userProperties = userProperties;
            this.field_181172_c = field_181172_c;
            this.proxy = proxy;
        }
    }
    
    public static class ServerInformation
    {
        public final String serverName;
        public final int serverPort;
        
        public ServerInformation(final String serverName, final int serverPort) {
            this.serverName = serverName;
            this.serverPort = serverPort;
        }
    }
    
    public static class FolderInformation
    {
        public final File assetsDir;
        public final String assetIndex;
        public final File mcDataDir;
        public final File resourcePacksDir;
        
        public FolderInformation(final File mcDataDir, final File resourcePacksDir, final File assetsDir, final String assetIndex) {
            this.mcDataDir = mcDataDir;
            this.resourcePacksDir = resourcePacksDir;
            this.assetsDir = assetsDir;
            this.assetIndex = assetIndex;
        }
    }
    
    public static class DisplayInformation
    {
        public final boolean checkGlErrors;
        public final boolean fullscreen;
        public final int height;
        public final int width;
        
        public DisplayInformation(final int width, final int height, final boolean fullscreen, final boolean checkGlErrors) {
            this.width = width;
            this.height = height;
            this.fullscreen = fullscreen;
            this.checkGlErrors = checkGlErrors;
        }
    }
}
