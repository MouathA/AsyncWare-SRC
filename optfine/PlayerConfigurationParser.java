package optfine;

import java.awt.image.*;
import java.net.*;
import javax.imageio.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class PlayerConfigurationParser
{
    public static final String ITEM_TYPE = "type";
    public static final String CONFIG_ITEMS = "items";
    public static final String ITEM_ACTIVE = "active";
    private String player;
    
    private PlayerItemModel downloadModel(final String s) {
        final JsonObject jsonObject = (JsonObject)new JsonParser().parse(new String(HttpUtils.get("http://s.optifine.net/" + s), "ASCII"));
        final PlayerItemParser playerItemParser = new PlayerItemParser();
        return PlayerItemParser.parseItemModel(jsonObject);
    }
    
    private BufferedImage downloadTextureImage(final String s) {
        return ImageIO.read(new URL("http://s.optifine.net/" + s));
    }
    
    public PlayerConfigurationParser(final String player) {
        this.player = null;
        this.player = player;
    }
    
    public PlayerConfiguration parsePlayerConfiguration(final JsonElement jsonElement) {
        if (jsonElement == null) {
            throw new JsonParseException("JSON object is null, player: " + this.player);
        }
        final JsonObject jsonObject = (JsonObject)jsonElement;
        final PlayerConfiguration playerConfiguration = new PlayerConfiguration();
        final JsonArray jsonArray = (JsonArray)jsonObject.get("items");
        if (jsonArray != null) {
            while (0 < jsonArray.size()) {
                final JsonObject jsonObject2 = (JsonObject)jsonArray.get(0);
                Label_0304: {
                    if (Json.getBoolean(jsonObject2, "active", true)) {
                        final String string = Json.getString(jsonObject2, "type");
                        if (string == null) {
                            Config.warn("Item type is null, player: " + this.player);
                        }
                        else {
                            String s = Json.getString(jsonObject2, "model");
                            if (s == null) {
                                s = "items/" + string + "/model.cfg";
                            }
                            final PlayerItemModel downloadModel = this.downloadModel(s);
                            if (downloadModel != null) {
                                if (!downloadModel.isUsePlayerTexture()) {
                                    String s2 = Json.getString(jsonObject2, "texture");
                                    if (s2 == null) {
                                        s2 = "items/" + string + "/users/" + this.player + ".png";
                                    }
                                    final BufferedImage downloadTextureImage = this.downloadTextureImage(s2);
                                    if (downloadTextureImage == null) {
                                        break Label_0304;
                                    }
                                    downloadModel.setTextureImage(downloadTextureImage);
                                    downloadModel.setTextureLocation(new ResourceLocation("optifine.net", s2));
                                }
                                playerConfiguration.addPlayerItemModel(downloadModel);
                            }
                        }
                    }
                }
                int n = 0;
                ++n;
            }
        }
        return playerConfiguration;
    }
}
