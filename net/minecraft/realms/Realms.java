package net.minecraft.realms;

import net.minecraft.world.*;
import net.minecraft.client.*;
import com.mojang.util.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import java.net.*;
import com.google.common.util.concurrent.*;
import net.minecraft.client.gui.*;

public class Realms
{
    public static int adventureId() {
        return WorldSettings.GameType.ADVENTURE.getID();
    }
    
    public static void setConnectedToRealms(final boolean b) {
        Minecraft.getMinecraft().func_181537_a(b);
    }
    
    public static int survivalId() {
        return WorldSettings.GameType.SURVIVAL.getID();
    }
    
    public static int creativeId() {
        return WorldSettings.GameType.CREATIVE.getID();
    }
    
    public static String uuidToName(final String s) {
        return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(s), (String)null), false).getName();
    }
    
    public static String sessionId() {
        final Session session = Minecraft.getMinecraft().getSession();
        return (session == null) ? null : session.getSessionID();
    }
    
    public static int spectatorId() {
        return WorldSettings.GameType.SPECTATOR.getID();
    }
    
    public static Proxy getProxy() {
        return Minecraft.getMinecraft().getProxy();
    }
    
    public static String getSessionId() {
        return Minecraft.getMinecraft().getSession().getSessionID();
    }
    
    public static ListenableFuture downloadResourcePack(final String s, final String s2) {
        return Minecraft.getMinecraft().getResourcePackRepository().downloadResourcePack(s, s2);
    }
    
    public static long currentTimeMillis() {
        return Minecraft.getSystemTime();
    }
    
    public static boolean isTouchScreen() {
        return Minecraft.getMinecraft().gameSettings.touchscreen;
    }
    
    public static String getName() {
        return Minecraft.getMinecraft().getSession().getUsername();
    }
    
    public static void setScreen(final RealmsScreen realmsScreen) {
        Minecraft.getMinecraft().displayGuiScreen(realmsScreen.getProxy());
    }
    
    public static String userName() {
        final Session session = Minecraft.getMinecraft().getSession();
        return (session == null) ? null : session.getUsername();
    }
    
    public static String getUUID() {
        return Minecraft.getMinecraft().getSession().getPlayerID();
    }
    
    public static String getGameDirectoryPath() {
        return Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
    }
    
    public static void clearResourcePack() {
        Minecraft.getMinecraft().getResourcePackRepository().func_148529_f();
    }
}
