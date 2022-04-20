package optfine;

import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;

public class PlayerConfigurations
{
    private static Map mapConfigurations;
    
    private static Map getMapConfigurations() {
        if (PlayerConfigurations.mapConfigurations == null) {
            PlayerConfigurations.mapConfigurations = new HashMap();
        }
        return PlayerConfigurations.mapConfigurations;
    }
    
    static {
        PlayerConfigurations.mapConfigurations = null;
    }
    
    public static synchronized void setPlayerConfiguration(final String s, final PlayerConfiguration playerConfiguration) {
        getMapConfigurations().put(s, playerConfiguration);
    }
    
    public static String getPlayerName(final AbstractClientPlayer abstractClientPlayer) {
        String s = abstractClientPlayer.getName();
        if (s != null && !s.isEmpty()) {
            s = StringUtils.stripControlCodes(s);
        }
        return s;
    }
    
    public static synchronized PlayerConfiguration getPlayerConfiguration(final AbstractClientPlayer abstractClientPlayer) {
        final String playerName = getPlayerName(abstractClientPlayer);
        PlayerConfiguration playerConfiguration = getMapConfigurations().get(playerName);
        if (playerConfiguration == null) {
            playerConfiguration = new PlayerConfiguration();
            getMapConfigurations().put(playerName, playerConfiguration);
            new FileDownloadThread("http://s.optifine.net/users/" + playerName + ".cfg", new PlayerConfigurationReceiver(playerName)).start();
        }
        return playerConfiguration;
    }
    
    public static void renderPlayerItems(final ModelBiped modelBiped, final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        final PlayerConfiguration playerConfiguration = getPlayerConfiguration(abstractClientPlayer);
        if (playerConfiguration != null) {
            playerConfiguration.renderPlayerItems(modelBiped, abstractClientPlayer, n, n2);
        }
    }
}
