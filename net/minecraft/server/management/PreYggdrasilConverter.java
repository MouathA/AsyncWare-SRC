package net.minecraft.server.management;

import java.io.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import org.apache.logging.log4j.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import com.mojang.authlib.*;

public class PreYggdrasilConverter
{
    public static final File OLD_PLAYERBAN_FILE;
    public static final File OLD_IPBAN_FILE;
    public static final File OLD_OPS_FILE;
    public static final File OLD_WHITELIST_FILE;
    private static final Logger LOGGER;
    
    public static String getStringUUIDFromName(final String s) {
        if (StringUtils.isNullOrEmpty(s) || s.length() > 16) {
            return s;
        }
        final MinecraftServer server = MinecraftServer.getServer();
        final GameProfile gameProfileForUsername = server.getPlayerProfileCache().getGameProfileForUsername(s);
        if (gameProfileForUsername != null && gameProfileForUsername.getId() != null) {
            return gameProfileForUsername.getId().toString();
        }
        if (!server.isSinglePlayer() && server.isServerInOnlineMode()) {
            final ArrayList arrayList = Lists.newArrayList();
            lookupNames(server, Lists.newArrayList((Object[])new String[] { s }), (ProfileLookupCallback)new ProfileLookupCallback(server, arrayList) {
                final List val$list;
                final MinecraftServer val$minecraftserver;
                
                public void onProfileLookupSucceeded(final GameProfile gameProfile) {
                    this.val$minecraftserver.getPlayerProfileCache().addEntry(gameProfile);
                    this.val$list.add(gameProfile);
                }
                
                public void onProfileLookupFailed(final GameProfile gameProfile, final Exception ex) {
                    PreYggdrasilConverter.access$000().warn("Could not lookup user whitelist entry for " + gameProfile.getName(), (Throwable)ex);
                }
            });
            return (arrayList.size() > 0 && ((GameProfile)arrayList.get(0)).getId() != null) ? ((GameProfile)arrayList.get(0)).getId().toString() : "";
        }
        return EntityPlayer.getUUID(new GameProfile((UUID)null, s)).toString();
    }
    
    static Logger access$000() {
        return PreYggdrasilConverter.LOGGER;
    }
    
    static {
        LOGGER = LogManager.getLogger();
        OLD_IPBAN_FILE = new File("banned-ips.txt");
        OLD_PLAYERBAN_FILE = new File("banned-players.txt");
        OLD_OPS_FILE = new File("ops.txt");
        OLD_WHITELIST_FILE = new File("white-list.txt");
    }
    
    private static void lookupNames(final MinecraftServer minecraftServer, final Collection collection, final ProfileLookupCallback profileLookupCallback) {
        final String[] array = (String[])Iterators.toArray((Iterator)Iterators.filter((Iterator)collection.iterator(), (Predicate)new Predicate() {
            public boolean apply(final Object o) {
                return this.apply((String)o);
            }
            
            public boolean apply(final String s) {
                return !StringUtils.isNullOrEmpty(s);
            }
        }), (Class)String.class);
        if (minecraftServer.isServerInOnlineMode()) {
            minecraftServer.getGameProfileRepository().findProfilesByNames(array, Agent.MINECRAFT, profileLookupCallback);
        }
        else {
            final String[] array2 = array;
            while (0 < array2.length) {
                final String s = array2[0];
                profileLookupCallback.onProfileLookupSucceeded(new GameProfile(EntityPlayer.getUUID(new GameProfile((UUID)null, s)), s));
                int n = 0;
                ++n;
            }
        }
    }
}
