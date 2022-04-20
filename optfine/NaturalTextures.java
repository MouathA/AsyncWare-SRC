package optfine;

import java.util.*;
import java.io.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;

public class NaturalTextures
{
    private static NaturalProperties[] propertiesByIndex;
    
    private static NaturalProperties[] makeDefaultProperties() {
        Config.dbg("NaturalTextures: Creating default configuration.");
        final ArrayList list = new ArrayList();
        setIconProperties(list, "coarse_dirt", "4F");
        setIconProperties(list, "grass_side", "F");
        setIconProperties(list, "grass_side_overlay", "F");
        setIconProperties(list, "stone_slab_top", "F");
        setIconProperties(list, "gravel", "2");
        setIconProperties(list, "log_oak", "2F");
        setIconProperties(list, "log_spruce", "2F");
        setIconProperties(list, "log_birch", "F");
        setIconProperties(list, "log_jungle", "2F");
        setIconProperties(list, "log_acacia", "2F");
        setIconProperties(list, "log_big_oak", "2F");
        setIconProperties(list, "log_oak_top", "4F");
        setIconProperties(list, "log_spruce_top", "4F");
        setIconProperties(list, "log_birch_top", "4F");
        setIconProperties(list, "log_jungle_top", "4F");
        setIconProperties(list, "log_acacia_top", "4F");
        setIconProperties(list, "log_big_oak_top", "4F");
        setIconProperties(list, "leaves_oak", "2F");
        setIconProperties(list, "leaves_spruce", "2F");
        setIconProperties(list, "leaves_birch", "2F");
        setIconProperties(list, "leaves_jungle", "2");
        setIconProperties(list, "leaves_big_oak", "2F");
        setIconProperties(list, "leaves_acacia", "2F");
        setIconProperties(list, "gold_ore", "2F");
        setIconProperties(list, "iron_ore", "2F");
        setIconProperties(list, "coal_ore", "2F");
        setIconProperties(list, "diamond_ore", "2F");
        setIconProperties(list, "redstone_ore", "2F");
        setIconProperties(list, "lapis_ore", "2F");
        setIconProperties(list, "obsidian", "4F");
        setIconProperties(list, "snow", "4F");
        setIconProperties(list, "grass_side_snowed", "F");
        setIconProperties(list, "cactus_side", "2F");
        setIconProperties(list, "clay", "4F");
        setIconProperties(list, "mycelium_side", "F");
        setIconProperties(list, "mycelium_top", "4F");
        setIconProperties(list, "farmland_wet", "2F");
        setIconProperties(list, "farmland_dry", "2F");
        setIconProperties(list, "netherrack", "4F");
        setIconProperties(list, "soul_sand", "4F");
        setIconProperties(list, "glowstone", "4");
        setIconProperties(list, "end_stone", "4");
        setIconProperties(list, "sandstone_top", "4");
        setIconProperties(list, "sandstone_bottom", "4F");
        setIconProperties(list, "redstone_lamp_on", "4F");
        setIconProperties(list, "redstone_lamp_off", "4F");
        return (NaturalProperties[])list.toArray(new NaturalProperties[list.size()]);
    }
    
    private static void setIconProperties(final List list, final String s, final String s2) {
        final TextureAtlasSprite spriteSafe = TextureUtils.getTextureMapBlocks().getSpriteSafe("minecraft:blocks/" + s);
        if (spriteSafe == null) {
            Config.warn("*** NaturalProperties: Icon not found: " + s + " ***");
        }
        else if (!(spriteSafe instanceof TextureAtlasSprite)) {
            Config.warn("*** NaturalProperties: Icon is not IconStitched: " + s + ": " + spriteSafe.getClass().getName() + " ***");
        }
        else {
            final int i = spriteSafe.getIndexInMap();
            if (i < 0) {
                Config.warn("*** NaturalProperties: Invalid index for icon: " + s + ": " + i + " ***");
            }
            else if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + s + ".png"))) {
                while (i >= list.size()) {
                    list.add(null);
                }
                list.set(i, new NaturalProperties(s2));
                Config.dbg("NaturalTextures: " + s + " = " + s2);
            }
        }
    }
    
    public static NaturalProperties getNaturalProperties(final TextureAtlasSprite textureAtlasSprite) {
        if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
            return null;
        }
        final int indexInMap = textureAtlasSprite.getIndexInMap();
        if (indexInMap >= 0 && indexInMap < NaturalTextures.propertiesByIndex.length) {
            return NaturalTextures.propertiesByIndex[indexInMap];
        }
        return null;
    }
    
    static {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
    }
    
    public static void update() {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
        if (Config.isNaturalTextures()) {
            final String s = "optifine/natural.properties";
            final ResourceLocation resourceLocation = new ResourceLocation(s);
            if (!Config.hasResource(resourceLocation)) {
                Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
                NaturalTextures.propertiesByIndex = makeDefaultProperties();
                return;
            }
            final InputStream resourceStream = Config.getResourceStream(resourceLocation);
            final ArrayList<NaturalProperties> list = new ArrayList<NaturalProperties>(256);
            final String inputStream = Config.readInputStream(resourceStream);
            resourceStream.close();
            final String[] tokenize = Config.tokenize(inputStream, "\n\r");
            Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
            final TextureMap textureMapBlocks = TextureUtils.getTextureMapBlocks();
            while (0 < tokenize.length) {
                final String trim = tokenize[0].trim();
                if (!trim.startsWith("#")) {
                    final String[] tokenize2 = Config.tokenize(trim, "=");
                    if (tokenize2.length != 2) {
                        Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + trim);
                    }
                    else {
                        final String trim2 = tokenize2[0].trim();
                        final String trim3 = tokenize2[1].trim();
                        final TextureAtlasSprite spriteSafe = textureMapBlocks.getSpriteSafe("minecraft:blocks/" + trim2);
                        if (spriteSafe == null) {
                            Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + trim);
                        }
                        else {
                            final int indexInMap = spriteSafe.getIndexInMap();
                            if (indexInMap < 0) {
                                Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + trim);
                            }
                            else {
                                final NaturalProperties naturalProperties = new NaturalProperties(trim3);
                                if (naturalProperties.isValid()) {
                                    while (list.size() <= indexInMap) {
                                        list.add(null);
                                    }
                                    list.set(indexInMap, naturalProperties);
                                    Config.dbg("NaturalTextures: " + trim2 + " = " + trim3);
                                }
                            }
                        }
                    }
                }
                int n = 0;
                ++n;
            }
            NaturalTextures.propertiesByIndex = list.toArray(new NaturalProperties[list.size()]);
        }
    }
    
    public static BakedQuad getNaturalTexture(final BlockPos blockPos, final BakedQuad bakedQuad) {
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        if (sprite == null) {
            return bakedQuad;
        }
        final NaturalProperties naturalProperties = getNaturalProperties(sprite);
        if (naturalProperties == null) {
            return bakedQuad;
        }
        final int random = Config.getRandom(blockPos, ConnectedTextures.getSide(bakedQuad.getFace()));
        if (naturalProperties.rotation > 1) {}
        if (naturalProperties.rotation == 2) {}
        if (naturalProperties.flip) {
            final boolean b = (random & 0x4) != 0x0;
        }
        return naturalProperties.getQuad(bakedQuad, 0, false);
    }
}
