package net.minecraft.world.gen;

import java.lang.reflect.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import com.google.gson.*;

public class ChunkProviderSettings
{
    public final int dirtSize;
    public final int dioriteSize;
    public final boolean useRavines;
    public final int andesiteSize;
    public final float biomeDepthWeight;
    public final int gravelSize;
    public final int seaLevel;
    public final int lavaLakeChance;
    public final int graniteCount;
    public final float coordinateScale;
    public final float depthNoiseScaleX;
    public final int dioriteCount;
    public final int redstoneCount;
    public final int redstoneMaxHeight;
    public final int fixedBiome;
    public final int lapisCount;
    public final int goldMaxHeight;
    public final int dioriteMaxHeight;
    public final boolean useLavaOceans;
    public final int redstoneMinHeight;
    public final int gravelMinHeight;
    public final float stretchY;
    public final int graniteMinHeight;
    public final int biomeSize;
    public final int goldMinHeight;
    public final int goldCount;
    public final int andesiteMinHeight;
    public final int gravelMaxHeight;
    public final float mainNoiseScaleX;
    public final boolean useWaterLakes;
    public final boolean useVillages;
    public final boolean useDungeons;
    public final int diamondSize;
    public final float biomeScaleOffset;
    public final int lapisSpread;
    public final int coalSize;
    public final float heightScale;
    public final int dirtCount;
    public final int lapisSize;
    public final int graniteMaxHeight;
    public final float baseSize;
    public final boolean useStrongholds;
    public final boolean useCaves;
    public final float biomeDepthOffSet;
    public final int dirtMinHeight;
    public final int ironMaxHeight;
    public final int graniteSize;
    public final int lapisCenterHeight;
    public final float depthNoiseScaleZ;
    public final int dioriteMinHeight;
    public final int coalMinHeight;
    public final int coalCount;
    public final boolean useTemples;
    public final int ironMinHeight;
    public final boolean useLavaLakes;
    public final int riverSize;
    public final int diamondCount;
    public final int dungeonChance;
    public final int redstoneSize;
    public final int dirtMaxHeight;
    public final int ironCount;
    public final int goldSize;
    public final float depthNoiseScaleExponent;
    public final float mainNoiseScaleZ;
    public final boolean useMonuments;
    public final int andesiteCount;
    public final int coalMaxHeight;
    public final float upperLimitScale;
    public final int diamondMaxHeight;
    public final int waterLakeChance;
    public final int andesiteMaxHeight;
    public final boolean useMineShafts;
    public final float biomeScaleWeight;
    public final int ironSize;
    public final float mainNoiseScaleY;
    public final int diamondMinHeight;
    public final float lowerLimitScale;
    public final int gravelCount;
    
    private ChunkProviderSettings(final Factory factory) {
        this.coordinateScale = factory.coordinateScale;
        this.heightScale = factory.heightScale;
        this.upperLimitScale = factory.upperLimitScale;
        this.lowerLimitScale = factory.lowerLimitScale;
        this.depthNoiseScaleX = factory.depthNoiseScaleX;
        this.depthNoiseScaleZ = factory.depthNoiseScaleZ;
        this.depthNoiseScaleExponent = factory.depthNoiseScaleExponent;
        this.mainNoiseScaleX = factory.mainNoiseScaleX;
        this.mainNoiseScaleY = factory.mainNoiseScaleY;
        this.mainNoiseScaleZ = factory.mainNoiseScaleZ;
        this.baseSize = factory.baseSize;
        this.stretchY = factory.stretchY;
        this.biomeDepthWeight = factory.biomeDepthWeight;
        this.biomeDepthOffSet = factory.biomeDepthOffset;
        this.biomeScaleWeight = factory.biomeScaleWeight;
        this.biomeScaleOffset = factory.biomeScaleOffset;
        this.seaLevel = factory.seaLevel;
        this.useCaves = factory.useCaves;
        this.useDungeons = factory.useDungeons;
        this.dungeonChance = factory.dungeonChance;
        this.useStrongholds = factory.useStrongholds;
        this.useVillages = factory.useVillages;
        this.useMineShafts = factory.useMineShafts;
        this.useTemples = factory.useTemples;
        this.useMonuments = factory.useMonuments;
        this.useRavines = factory.useRavines;
        this.useWaterLakes = factory.useWaterLakes;
        this.waterLakeChance = factory.waterLakeChance;
        this.useLavaLakes = factory.useLavaLakes;
        this.lavaLakeChance = factory.lavaLakeChance;
        this.useLavaOceans = factory.useLavaOceans;
        this.fixedBiome = factory.fixedBiome;
        this.biomeSize = factory.biomeSize;
        this.riverSize = factory.riverSize;
        this.dirtSize = factory.dirtSize;
        this.dirtCount = factory.dirtCount;
        this.dirtMinHeight = factory.dirtMinHeight;
        this.dirtMaxHeight = factory.dirtMaxHeight;
        this.gravelSize = factory.gravelSize;
        this.gravelCount = factory.gravelCount;
        this.gravelMinHeight = factory.gravelMinHeight;
        this.gravelMaxHeight = factory.gravelMaxHeight;
        this.graniteSize = factory.graniteSize;
        this.graniteCount = factory.graniteCount;
        this.graniteMinHeight = factory.graniteMinHeight;
        this.graniteMaxHeight = factory.graniteMaxHeight;
        this.dioriteSize = factory.dioriteSize;
        this.dioriteCount = factory.dioriteCount;
        this.dioriteMinHeight = factory.dioriteMinHeight;
        this.dioriteMaxHeight = factory.dioriteMaxHeight;
        this.andesiteSize = factory.andesiteSize;
        this.andesiteCount = factory.andesiteCount;
        this.andesiteMinHeight = factory.andesiteMinHeight;
        this.andesiteMaxHeight = factory.andesiteMaxHeight;
        this.coalSize = factory.coalSize;
        this.coalCount = factory.coalCount;
        this.coalMinHeight = factory.coalMinHeight;
        this.coalMaxHeight = factory.coalMaxHeight;
        this.ironSize = factory.ironSize;
        this.ironCount = factory.ironCount;
        this.ironMinHeight = factory.ironMinHeight;
        this.ironMaxHeight = factory.ironMaxHeight;
        this.goldSize = factory.goldSize;
        this.goldCount = factory.goldCount;
        this.goldMinHeight = factory.goldMinHeight;
        this.goldMaxHeight = factory.goldMaxHeight;
        this.redstoneSize = factory.redstoneSize;
        this.redstoneCount = factory.redstoneCount;
        this.redstoneMinHeight = factory.redstoneMinHeight;
        this.redstoneMaxHeight = factory.redstoneMaxHeight;
        this.diamondSize = factory.diamondSize;
        this.diamondCount = factory.diamondCount;
        this.diamondMinHeight = factory.diamondMinHeight;
        this.diamondMaxHeight = factory.diamondMaxHeight;
        this.lapisSize = factory.lapisSize;
        this.lapisCount = factory.lapisCount;
        this.lapisCenterHeight = factory.lapisCenterHeight;
        this.lapisSpread = factory.lapisSpread;
    }
    
    ChunkProviderSettings(final Factory factory, final ChunkProviderSettings$1 object) {
        this(factory);
    }
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((Factory)o, type, jsonSerializationContext);
        }
        
        public Factory deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final Factory factory = new Factory();
            factory.coordinateScale = JsonUtils.getFloat(asJsonObject, "coordinateScale", factory.coordinateScale);
            factory.heightScale = JsonUtils.getFloat(asJsonObject, "heightScale", factory.heightScale);
            factory.lowerLimitScale = JsonUtils.getFloat(asJsonObject, "lowerLimitScale", factory.lowerLimitScale);
            factory.upperLimitScale = JsonUtils.getFloat(asJsonObject, "upperLimitScale", factory.upperLimitScale);
            factory.depthNoiseScaleX = JsonUtils.getFloat(asJsonObject, "depthNoiseScaleX", factory.depthNoiseScaleX);
            factory.depthNoiseScaleZ = JsonUtils.getFloat(asJsonObject, "depthNoiseScaleZ", factory.depthNoiseScaleZ);
            factory.depthNoiseScaleExponent = JsonUtils.getFloat(asJsonObject, "depthNoiseScaleExponent", factory.depthNoiseScaleExponent);
            factory.mainNoiseScaleX = JsonUtils.getFloat(asJsonObject, "mainNoiseScaleX", factory.mainNoiseScaleX);
            factory.mainNoiseScaleY = JsonUtils.getFloat(asJsonObject, "mainNoiseScaleY", factory.mainNoiseScaleY);
            factory.mainNoiseScaleZ = JsonUtils.getFloat(asJsonObject, "mainNoiseScaleZ", factory.mainNoiseScaleZ);
            factory.baseSize = JsonUtils.getFloat(asJsonObject, "baseSize", factory.baseSize);
            factory.stretchY = JsonUtils.getFloat(asJsonObject, "stretchY", factory.stretchY);
            factory.biomeDepthWeight = JsonUtils.getFloat(asJsonObject, "biomeDepthWeight", factory.biomeDepthWeight);
            factory.biomeDepthOffset = JsonUtils.getFloat(asJsonObject, "biomeDepthOffset", factory.biomeDepthOffset);
            factory.biomeScaleWeight = JsonUtils.getFloat(asJsonObject, "biomeScaleWeight", factory.biomeScaleWeight);
            factory.biomeScaleOffset = JsonUtils.getFloat(asJsonObject, "biomeScaleOffset", factory.biomeScaleOffset);
            factory.seaLevel = JsonUtils.getInt(asJsonObject, "seaLevel", factory.seaLevel);
            factory.useCaves = JsonUtils.getBoolean(asJsonObject, "useCaves", factory.useCaves);
            factory.useDungeons = JsonUtils.getBoolean(asJsonObject, "useDungeons", factory.useDungeons);
            factory.dungeonChance = JsonUtils.getInt(asJsonObject, "dungeonChance", factory.dungeonChance);
            factory.useStrongholds = JsonUtils.getBoolean(asJsonObject, "useStrongholds", factory.useStrongholds);
            factory.useVillages = JsonUtils.getBoolean(asJsonObject, "useVillages", factory.useVillages);
            factory.useMineShafts = JsonUtils.getBoolean(asJsonObject, "useMineShafts", factory.useMineShafts);
            factory.useTemples = JsonUtils.getBoolean(asJsonObject, "useTemples", factory.useTemples);
            factory.useMonuments = JsonUtils.getBoolean(asJsonObject, "useMonuments", factory.useMonuments);
            factory.useRavines = JsonUtils.getBoolean(asJsonObject, "useRavines", factory.useRavines);
            factory.useWaterLakes = JsonUtils.getBoolean(asJsonObject, "useWaterLakes", factory.useWaterLakes);
            factory.waterLakeChance = JsonUtils.getInt(asJsonObject, "waterLakeChance", factory.waterLakeChance);
            factory.useLavaLakes = JsonUtils.getBoolean(asJsonObject, "useLavaLakes", factory.useLavaLakes);
            factory.lavaLakeChance = JsonUtils.getInt(asJsonObject, "lavaLakeChance", factory.lavaLakeChance);
            factory.useLavaOceans = JsonUtils.getBoolean(asJsonObject, "useLavaOceans", factory.useLavaOceans);
            factory.fixedBiome = JsonUtils.getInt(asJsonObject, "fixedBiome", factory.fixedBiome);
            if (factory.fixedBiome < 38 && factory.fixedBiome >= -1) {
                if (factory.fixedBiome >= BiomeGenBase.hell.biomeID) {
                    final Factory factory2 = factory;
                    factory2.fixedBiome += 2;
                }
            }
            else {
                factory.fixedBiome = -1;
            }
            factory.biomeSize = JsonUtils.getInt(asJsonObject, "biomeSize", factory.biomeSize);
            factory.riverSize = JsonUtils.getInt(asJsonObject, "riverSize", factory.riverSize);
            factory.dirtSize = JsonUtils.getInt(asJsonObject, "dirtSize", factory.dirtSize);
            factory.dirtCount = JsonUtils.getInt(asJsonObject, "dirtCount", factory.dirtCount);
            factory.dirtMinHeight = JsonUtils.getInt(asJsonObject, "dirtMinHeight", factory.dirtMinHeight);
            factory.dirtMaxHeight = JsonUtils.getInt(asJsonObject, "dirtMaxHeight", factory.dirtMaxHeight);
            factory.gravelSize = JsonUtils.getInt(asJsonObject, "gravelSize", factory.gravelSize);
            factory.gravelCount = JsonUtils.getInt(asJsonObject, "gravelCount", factory.gravelCount);
            factory.gravelMinHeight = JsonUtils.getInt(asJsonObject, "gravelMinHeight", factory.gravelMinHeight);
            factory.gravelMaxHeight = JsonUtils.getInt(asJsonObject, "gravelMaxHeight", factory.gravelMaxHeight);
            factory.graniteSize = JsonUtils.getInt(asJsonObject, "graniteSize", factory.graniteSize);
            factory.graniteCount = JsonUtils.getInt(asJsonObject, "graniteCount", factory.graniteCount);
            factory.graniteMinHeight = JsonUtils.getInt(asJsonObject, "graniteMinHeight", factory.graniteMinHeight);
            factory.graniteMaxHeight = JsonUtils.getInt(asJsonObject, "graniteMaxHeight", factory.graniteMaxHeight);
            factory.dioriteSize = JsonUtils.getInt(asJsonObject, "dioriteSize", factory.dioriteSize);
            factory.dioriteCount = JsonUtils.getInt(asJsonObject, "dioriteCount", factory.dioriteCount);
            factory.dioriteMinHeight = JsonUtils.getInt(asJsonObject, "dioriteMinHeight", factory.dioriteMinHeight);
            factory.dioriteMaxHeight = JsonUtils.getInt(asJsonObject, "dioriteMaxHeight", factory.dioriteMaxHeight);
            factory.andesiteSize = JsonUtils.getInt(asJsonObject, "andesiteSize", factory.andesiteSize);
            factory.andesiteCount = JsonUtils.getInt(asJsonObject, "andesiteCount", factory.andesiteCount);
            factory.andesiteMinHeight = JsonUtils.getInt(asJsonObject, "andesiteMinHeight", factory.andesiteMinHeight);
            factory.andesiteMaxHeight = JsonUtils.getInt(asJsonObject, "andesiteMaxHeight", factory.andesiteMaxHeight);
            factory.coalSize = JsonUtils.getInt(asJsonObject, "coalSize", factory.coalSize);
            factory.coalCount = JsonUtils.getInt(asJsonObject, "coalCount", factory.coalCount);
            factory.coalMinHeight = JsonUtils.getInt(asJsonObject, "coalMinHeight", factory.coalMinHeight);
            factory.coalMaxHeight = JsonUtils.getInt(asJsonObject, "coalMaxHeight", factory.coalMaxHeight);
            factory.ironSize = JsonUtils.getInt(asJsonObject, "ironSize", factory.ironSize);
            factory.ironCount = JsonUtils.getInt(asJsonObject, "ironCount", factory.ironCount);
            factory.ironMinHeight = JsonUtils.getInt(asJsonObject, "ironMinHeight", factory.ironMinHeight);
            factory.ironMaxHeight = JsonUtils.getInt(asJsonObject, "ironMaxHeight", factory.ironMaxHeight);
            factory.goldSize = JsonUtils.getInt(asJsonObject, "goldSize", factory.goldSize);
            factory.goldCount = JsonUtils.getInt(asJsonObject, "goldCount", factory.goldCount);
            factory.goldMinHeight = JsonUtils.getInt(asJsonObject, "goldMinHeight", factory.goldMinHeight);
            factory.goldMaxHeight = JsonUtils.getInt(asJsonObject, "goldMaxHeight", factory.goldMaxHeight);
            factory.redstoneSize = JsonUtils.getInt(asJsonObject, "redstoneSize", factory.redstoneSize);
            factory.redstoneCount = JsonUtils.getInt(asJsonObject, "redstoneCount", factory.redstoneCount);
            factory.redstoneMinHeight = JsonUtils.getInt(asJsonObject, "redstoneMinHeight", factory.redstoneMinHeight);
            factory.redstoneMaxHeight = JsonUtils.getInt(asJsonObject, "redstoneMaxHeight", factory.redstoneMaxHeight);
            factory.diamondSize = JsonUtils.getInt(asJsonObject, "diamondSize", factory.diamondSize);
            factory.diamondCount = JsonUtils.getInt(asJsonObject, "diamondCount", factory.diamondCount);
            factory.diamondMinHeight = JsonUtils.getInt(asJsonObject, "diamondMinHeight", factory.diamondMinHeight);
            factory.diamondMaxHeight = JsonUtils.getInt(asJsonObject, "diamondMaxHeight", factory.diamondMaxHeight);
            factory.lapisSize = JsonUtils.getInt(asJsonObject, "lapisSize", factory.lapisSize);
            factory.lapisCount = JsonUtils.getInt(asJsonObject, "lapisCount", factory.lapisCount);
            factory.lapisCenterHeight = JsonUtils.getInt(asJsonObject, "lapisCenterHeight", factory.lapisCenterHeight);
            factory.lapisSpread = JsonUtils.getInt(asJsonObject, "lapisSpread", factory.lapisSpread);
            return factory;
        }
        
        public JsonElement serialize(final Factory factory, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("coordinateScale", (Number)factory.coordinateScale);
            jsonObject.addProperty("heightScale", (Number)factory.heightScale);
            jsonObject.addProperty("lowerLimitScale", (Number)factory.lowerLimitScale);
            jsonObject.addProperty("upperLimitScale", (Number)factory.upperLimitScale);
            jsonObject.addProperty("depthNoiseScaleX", (Number)factory.depthNoiseScaleX);
            jsonObject.addProperty("depthNoiseScaleZ", (Number)factory.depthNoiseScaleZ);
            jsonObject.addProperty("depthNoiseScaleExponent", (Number)factory.depthNoiseScaleExponent);
            jsonObject.addProperty("mainNoiseScaleX", (Number)factory.mainNoiseScaleX);
            jsonObject.addProperty("mainNoiseScaleY", (Number)factory.mainNoiseScaleY);
            jsonObject.addProperty("mainNoiseScaleZ", (Number)factory.mainNoiseScaleZ);
            jsonObject.addProperty("baseSize", (Number)factory.baseSize);
            jsonObject.addProperty("stretchY", (Number)factory.stretchY);
            jsonObject.addProperty("biomeDepthWeight", (Number)factory.biomeDepthWeight);
            jsonObject.addProperty("biomeDepthOffset", (Number)factory.biomeDepthOffset);
            jsonObject.addProperty("biomeScaleWeight", (Number)factory.biomeScaleWeight);
            jsonObject.addProperty("biomeScaleOffset", (Number)factory.biomeScaleOffset);
            jsonObject.addProperty("seaLevel", (Number)factory.seaLevel);
            jsonObject.addProperty("useCaves", Boolean.valueOf(factory.useCaves));
            jsonObject.addProperty("useDungeons", Boolean.valueOf(factory.useDungeons));
            jsonObject.addProperty("dungeonChance", (Number)factory.dungeonChance);
            jsonObject.addProperty("useStrongholds", Boolean.valueOf(factory.useStrongholds));
            jsonObject.addProperty("useVillages", Boolean.valueOf(factory.useVillages));
            jsonObject.addProperty("useMineShafts", Boolean.valueOf(factory.useMineShafts));
            jsonObject.addProperty("useTemples", Boolean.valueOf(factory.useTemples));
            jsonObject.addProperty("useMonuments", Boolean.valueOf(factory.useMonuments));
            jsonObject.addProperty("useRavines", Boolean.valueOf(factory.useRavines));
            jsonObject.addProperty("useWaterLakes", Boolean.valueOf(factory.useWaterLakes));
            jsonObject.addProperty("waterLakeChance", (Number)factory.waterLakeChance);
            jsonObject.addProperty("useLavaLakes", Boolean.valueOf(factory.useLavaLakes));
            jsonObject.addProperty("lavaLakeChance", (Number)factory.lavaLakeChance);
            jsonObject.addProperty("useLavaOceans", Boolean.valueOf(factory.useLavaOceans));
            jsonObject.addProperty("fixedBiome", (Number)factory.fixedBiome);
            jsonObject.addProperty("biomeSize", (Number)factory.biomeSize);
            jsonObject.addProperty("riverSize", (Number)factory.riverSize);
            jsonObject.addProperty("dirtSize", (Number)factory.dirtSize);
            jsonObject.addProperty("dirtCount", (Number)factory.dirtCount);
            jsonObject.addProperty("dirtMinHeight", (Number)factory.dirtMinHeight);
            jsonObject.addProperty("dirtMaxHeight", (Number)factory.dirtMaxHeight);
            jsonObject.addProperty("gravelSize", (Number)factory.gravelSize);
            jsonObject.addProperty("gravelCount", (Number)factory.gravelCount);
            jsonObject.addProperty("gravelMinHeight", (Number)factory.gravelMinHeight);
            jsonObject.addProperty("gravelMaxHeight", (Number)factory.gravelMaxHeight);
            jsonObject.addProperty("graniteSize", (Number)factory.graniteSize);
            jsonObject.addProperty("graniteCount", (Number)factory.graniteCount);
            jsonObject.addProperty("graniteMinHeight", (Number)factory.graniteMinHeight);
            jsonObject.addProperty("graniteMaxHeight", (Number)factory.graniteMaxHeight);
            jsonObject.addProperty("dioriteSize", (Number)factory.dioriteSize);
            jsonObject.addProperty("dioriteCount", (Number)factory.dioriteCount);
            jsonObject.addProperty("dioriteMinHeight", (Number)factory.dioriteMinHeight);
            jsonObject.addProperty("dioriteMaxHeight", (Number)factory.dioriteMaxHeight);
            jsonObject.addProperty("andesiteSize", (Number)factory.andesiteSize);
            jsonObject.addProperty("andesiteCount", (Number)factory.andesiteCount);
            jsonObject.addProperty("andesiteMinHeight", (Number)factory.andesiteMinHeight);
            jsonObject.addProperty("andesiteMaxHeight", (Number)factory.andesiteMaxHeight);
            jsonObject.addProperty("coalSize", (Number)factory.coalSize);
            jsonObject.addProperty("coalCount", (Number)factory.coalCount);
            jsonObject.addProperty("coalMinHeight", (Number)factory.coalMinHeight);
            jsonObject.addProperty("coalMaxHeight", (Number)factory.coalMaxHeight);
            jsonObject.addProperty("ironSize", (Number)factory.ironSize);
            jsonObject.addProperty("ironCount", (Number)factory.ironCount);
            jsonObject.addProperty("ironMinHeight", (Number)factory.ironMinHeight);
            jsonObject.addProperty("ironMaxHeight", (Number)factory.ironMaxHeight);
            jsonObject.addProperty("goldSize", (Number)factory.goldSize);
            jsonObject.addProperty("goldCount", (Number)factory.goldCount);
            jsonObject.addProperty("goldMinHeight", (Number)factory.goldMinHeight);
            jsonObject.addProperty("goldMaxHeight", (Number)factory.goldMaxHeight);
            jsonObject.addProperty("redstoneSize", (Number)factory.redstoneSize);
            jsonObject.addProperty("redstoneCount", (Number)factory.redstoneCount);
            jsonObject.addProperty("redstoneMinHeight", (Number)factory.redstoneMinHeight);
            jsonObject.addProperty("redstoneMaxHeight", (Number)factory.redstoneMaxHeight);
            jsonObject.addProperty("diamondSize", (Number)factory.diamondSize);
            jsonObject.addProperty("diamondCount", (Number)factory.diamondCount);
            jsonObject.addProperty("diamondMinHeight", (Number)factory.diamondMinHeight);
            jsonObject.addProperty("diamondMaxHeight", (Number)factory.diamondMaxHeight);
            jsonObject.addProperty("lapisSize", (Number)factory.lapisSize);
            jsonObject.addProperty("lapisCount", (Number)factory.lapisCount);
            jsonObject.addProperty("lapisCenterHeight", (Number)factory.lapisCenterHeight);
            jsonObject.addProperty("lapisSpread", (Number)factory.lapisSpread);
            return (JsonElement)jsonObject;
        }
    }
    
    public static class Factory
    {
        public int redstoneMinHeight;
        static final Gson JSON_ADAPTER;
        public float stretchY;
        public int redstoneCount;
        public float depthNoiseScaleX;
        public int redstoneSize;
        public int andesiteMinHeight;
        public int lavaLakeChance;
        public boolean useDungeons;
        public float mainNoiseScaleY;
        public int graniteMinHeight;
        public int goldSize;
        public int dioriteSize;
        public float biomeScaleOffset;
        public boolean useStrongholds;
        public boolean useTemples;
        public int diamondSize;
        public boolean useVillages;
        public boolean useRavines;
        public int dioriteCount;
        public int ironMinHeight;
        public float coordinateScale;
        public int dioriteMaxHeight;
        public int gravelSize;
        public float heightScale;
        public int gravelCount;
        public int dungeonChance;
        public int lapisCenterHeight;
        public int coalMinHeight;
        public int gravelMinHeight;
        public float mainNoiseScaleX;
        public int lapisSpread;
        public float upperLimitScale;
        public int ironCount;
        public boolean useWaterLakes;
        public int redstoneMaxHeight;
        public int gravelMaxHeight;
        public float depthNoiseScaleExponent;
        public float biomeDepthOffset;
        public float depthNoiseScaleZ;
        public int graniteMaxHeight;
        public int waterLakeChance;
        public int andesiteCount;
        public int lapisCount;
        public int seaLevel;
        public int dirtMaxHeight;
        public int andesiteMaxHeight;
        public float biomeScaleWeight;
        public int goldCount;
        public int fixedBiome;
        public int coalCount;
        public int andesiteSize;
        public int coalSize;
        public int dirtMinHeight;
        public float lowerLimitScale;
        public int graniteSize;
        public int goldMinHeight;
        public int diamondCount;
        public boolean useMineShafts;
        public int dioriteMinHeight;
        public float biomeDepthWeight;
        public int goldMaxHeight;
        public int ironMaxHeight;
        public int ironSize;
        public boolean useLavaOceans;
        public boolean useLavaLakes;
        public int diamondMinHeight;
        public float baseSize;
        public boolean useCaves;
        public float mainNoiseScaleZ;
        public int coalMaxHeight;
        public int diamondMaxHeight;
        public int dirtSize;
        public int lapisSize;
        public int graniteCount;
        public boolean useMonuments;
        public int riverSize;
        public int biomeSize;
        public int dirtCount;
        
        @Override
        public int hashCode() {
            return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * ((this.coordinateScale != 0.0f) ? Float.floatToIntBits(this.coordinateScale) : 0) + ((this.heightScale != 0.0f) ? Float.floatToIntBits(this.heightScale) : 0)) + ((this.upperLimitScale != 0.0f) ? Float.floatToIntBits(this.upperLimitScale) : 0)) + ((this.lowerLimitScale != 0.0f) ? Float.floatToIntBits(this.lowerLimitScale) : 0)) + ((this.depthNoiseScaleX != 0.0f) ? Float.floatToIntBits(this.depthNoiseScaleX) : 0)) + ((this.depthNoiseScaleZ != 0.0f) ? Float.floatToIntBits(this.depthNoiseScaleZ) : 0)) + ((this.depthNoiseScaleExponent != 0.0f) ? Float.floatToIntBits(this.depthNoiseScaleExponent) : 0)) + ((this.mainNoiseScaleX != 0.0f) ? Float.floatToIntBits(this.mainNoiseScaleX) : 0)) + ((this.mainNoiseScaleY != 0.0f) ? Float.floatToIntBits(this.mainNoiseScaleY) : 0)) + ((this.mainNoiseScaleZ != 0.0f) ? Float.floatToIntBits(this.mainNoiseScaleZ) : 0)) + ((this.baseSize != 0.0f) ? Float.floatToIntBits(this.baseSize) : 0)) + ((this.stretchY != 0.0f) ? Float.floatToIntBits(this.stretchY) : 0)) + ((this.biomeDepthWeight != 0.0f) ? Float.floatToIntBits(this.biomeDepthWeight) : 0)) + ((this.biomeDepthOffset != 0.0f) ? Float.floatToIntBits(this.biomeDepthOffset) : 0)) + ((this.biomeScaleWeight != 0.0f) ? Float.floatToIntBits(this.biomeScaleWeight) : 0)) + ((this.biomeScaleOffset != 0.0f) ? Float.floatToIntBits(this.biomeScaleOffset) : 0)) + this.seaLevel) + (this.useCaves ? 1 : 0)) + (this.useDungeons ? 1 : 0)) + this.dungeonChance) + (this.useStrongholds ? 1 : 0)) + (this.useVillages ? 1 : 0)) + (this.useMineShafts ? 1 : 0)) + (this.useTemples ? 1 : 0)) + (this.useMonuments ? 1 : 0)) + (this.useRavines ? 1 : 0)) + (this.useWaterLakes ? 1 : 0)) + this.waterLakeChance) + (this.useLavaLakes ? 1 : 0)) + this.lavaLakeChance) + (this.useLavaOceans ? 1 : 0)) + this.fixedBiome) + this.biomeSize) + this.riverSize) + this.dirtSize) + this.dirtCount) + this.dirtMinHeight) + this.dirtMaxHeight) + this.gravelSize) + this.gravelCount) + this.gravelMinHeight) + this.gravelMaxHeight) + this.graniteSize) + this.graniteCount) + this.graniteMinHeight) + this.graniteMaxHeight) + this.dioriteSize) + this.dioriteCount) + this.dioriteMinHeight) + this.dioriteMaxHeight) + this.andesiteSize) + this.andesiteCount) + this.andesiteMinHeight) + this.andesiteMaxHeight) + this.coalSize) + this.coalCount) + this.coalMinHeight) + this.coalMaxHeight) + this.ironSize) + this.ironCount) + this.ironMinHeight) + this.ironMaxHeight) + this.goldSize) + this.goldCount) + this.goldMinHeight) + this.goldMaxHeight) + this.redstoneSize) + this.redstoneCount) + this.redstoneMinHeight) + this.redstoneMaxHeight) + this.diamondSize) + this.diamondCount) + this.diamondMinHeight) + this.diamondMaxHeight) + this.lapisSize) + this.lapisCount) + this.lapisCenterHeight) + this.lapisSpread;
        }
        
        static {
            JSON_ADAPTER = new GsonBuilder().registerTypeAdapter((Type)Factory.class, (Object)new Serializer()).create();
        }
        
        public ChunkProviderSettings func_177864_b() {
            return new ChunkProviderSettings(this, null);
        }
        
        public static Factory jsonToFactory(final String s) {
            if (s.length() == 0) {
                return new Factory();
            }
            return (Factory)Factory.JSON_ADAPTER.fromJson(s, (Class)Factory.class);
        }
        
        public void func_177863_a() {
            this.coordinateScale = 684.412f;
            this.heightScale = 684.412f;
            this.upperLimitScale = 512.0f;
            this.lowerLimitScale = 512.0f;
            this.depthNoiseScaleX = 200.0f;
            this.depthNoiseScaleZ = 200.0f;
            this.depthNoiseScaleExponent = 0.5f;
            this.mainNoiseScaleX = 80.0f;
            this.mainNoiseScaleY = 160.0f;
            this.mainNoiseScaleZ = 80.0f;
            this.baseSize = 8.5f;
            this.stretchY = 12.0f;
            this.biomeDepthWeight = 1.0f;
            this.biomeDepthOffset = 0.0f;
            this.biomeScaleWeight = 1.0f;
            this.biomeScaleOffset = 0.0f;
            this.seaLevel = 63;
            this.useCaves = true;
            this.useDungeons = true;
            this.dungeonChance = 8;
            this.useStrongholds = true;
            this.useVillages = true;
            this.useMineShafts = true;
            this.useTemples = true;
            this.useMonuments = true;
            this.useRavines = true;
            this.useWaterLakes = true;
            this.waterLakeChance = 4;
            this.useLavaLakes = true;
            this.lavaLakeChance = 80;
            this.useLavaOceans = false;
            this.fixedBiome = -1;
            this.biomeSize = 4;
            this.riverSize = 4;
            this.dirtSize = 33;
            this.dirtCount = 10;
            this.dirtMinHeight = 0;
            this.dirtMaxHeight = 256;
            this.gravelSize = 33;
            this.gravelCount = 8;
            this.gravelMinHeight = 0;
            this.gravelMaxHeight = 256;
            this.graniteSize = 33;
            this.graniteCount = 10;
            this.graniteMinHeight = 0;
            this.graniteMaxHeight = 80;
            this.dioriteSize = 33;
            this.dioriteCount = 10;
            this.dioriteMinHeight = 0;
            this.dioriteMaxHeight = 80;
            this.andesiteSize = 33;
            this.andesiteCount = 10;
            this.andesiteMinHeight = 0;
            this.andesiteMaxHeight = 80;
            this.coalSize = 17;
            this.coalCount = 20;
            this.coalMinHeight = 0;
            this.coalMaxHeight = 128;
            this.ironSize = 9;
            this.ironCount = 20;
            this.ironMinHeight = 0;
            this.ironMaxHeight = 64;
            this.goldSize = 9;
            this.goldCount = 2;
            this.goldMinHeight = 0;
            this.goldMaxHeight = 32;
            this.redstoneSize = 8;
            this.redstoneCount = 8;
            this.redstoneMinHeight = 0;
            this.redstoneMaxHeight = 16;
            this.diamondSize = 8;
            this.diamondCount = 1;
            this.diamondMinHeight = 0;
            this.diamondMaxHeight = 16;
            this.lapisSize = 7;
            this.lapisCount = 1;
            this.lapisCenterHeight = 16;
            this.lapisSpread = 16;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o != null && this.getClass() == o.getClass()) {
                final Factory factory = (Factory)o;
                return this.andesiteCount == factory.andesiteCount && this.andesiteMaxHeight == factory.andesiteMaxHeight && this.andesiteMinHeight == factory.andesiteMinHeight && this.andesiteSize == factory.andesiteSize && Float.compare(factory.baseSize, this.baseSize) == 0 && Float.compare(factory.biomeDepthOffset, this.biomeDepthOffset) == 0 && Float.compare(factory.biomeDepthWeight, this.biomeDepthWeight) == 0 && Float.compare(factory.biomeScaleOffset, this.biomeScaleOffset) == 0 && Float.compare(factory.biomeScaleWeight, this.biomeScaleWeight) == 0 && this.biomeSize == factory.biomeSize && this.coalCount == factory.coalCount && this.coalMaxHeight == factory.coalMaxHeight && this.coalMinHeight == factory.coalMinHeight && this.coalSize == factory.coalSize && Float.compare(factory.coordinateScale, this.coordinateScale) == 0 && Float.compare(factory.depthNoiseScaleExponent, this.depthNoiseScaleExponent) == 0 && Float.compare(factory.depthNoiseScaleX, this.depthNoiseScaleX) == 0 && Float.compare(factory.depthNoiseScaleZ, this.depthNoiseScaleZ) == 0 && this.diamondCount == factory.diamondCount && this.diamondMaxHeight == factory.diamondMaxHeight && this.diamondMinHeight == factory.diamondMinHeight && this.diamondSize == factory.diamondSize && this.dioriteCount == factory.dioriteCount && this.dioriteMaxHeight == factory.dioriteMaxHeight && this.dioriteMinHeight == factory.dioriteMinHeight && this.dioriteSize == factory.dioriteSize && this.dirtCount == factory.dirtCount && this.dirtMaxHeight == factory.dirtMaxHeight && this.dirtMinHeight == factory.dirtMinHeight && this.dirtSize == factory.dirtSize && this.dungeonChance == factory.dungeonChance && this.fixedBiome == factory.fixedBiome && this.goldCount == factory.goldCount && this.goldMaxHeight == factory.goldMaxHeight && this.goldMinHeight == factory.goldMinHeight && this.goldSize == factory.goldSize && this.graniteCount == factory.graniteCount && this.graniteMaxHeight == factory.graniteMaxHeight && this.graniteMinHeight == factory.graniteMinHeight && this.graniteSize == factory.graniteSize && this.gravelCount == factory.gravelCount && this.gravelMaxHeight == factory.gravelMaxHeight && this.gravelMinHeight == factory.gravelMinHeight && this.gravelSize == factory.gravelSize && Float.compare(factory.heightScale, this.heightScale) == 0 && this.ironCount == factory.ironCount && this.ironMaxHeight == factory.ironMaxHeight && this.ironMinHeight == factory.ironMinHeight && this.ironSize == factory.ironSize && this.lapisCenterHeight == factory.lapisCenterHeight && this.lapisCount == factory.lapisCount && this.lapisSize == factory.lapisSize && this.lapisSpread == factory.lapisSpread && this.lavaLakeChance == factory.lavaLakeChance && Float.compare(factory.lowerLimitScale, this.lowerLimitScale) == 0 && Float.compare(factory.mainNoiseScaleX, this.mainNoiseScaleX) == 0 && Float.compare(factory.mainNoiseScaleY, this.mainNoiseScaleY) == 0 && Float.compare(factory.mainNoiseScaleZ, this.mainNoiseScaleZ) == 0 && this.redstoneCount == factory.redstoneCount && this.redstoneMaxHeight == factory.redstoneMaxHeight && this.redstoneMinHeight == factory.redstoneMinHeight && this.redstoneSize == factory.redstoneSize && this.riverSize == factory.riverSize && this.seaLevel == factory.seaLevel && Float.compare(factory.stretchY, this.stretchY) == 0 && Float.compare(factory.upperLimitScale, this.upperLimitScale) == 0 && this.useCaves == factory.useCaves && this.useDungeons == factory.useDungeons && this.useLavaLakes == factory.useLavaLakes && this.useLavaOceans == factory.useLavaOceans && this.useMineShafts == factory.useMineShafts && this.useRavines == factory.useRavines && this.useStrongholds == factory.useStrongholds && this.useTemples == factory.useTemples && this.useMonuments == factory.useMonuments && this.useVillages == factory.useVillages && this.useWaterLakes == factory.useWaterLakes && this.waterLakeChance == factory.waterLakeChance;
            }
            return false;
        }
        
        public Factory() {
            this.coordinateScale = 684.412f;
            this.heightScale = 684.412f;
            this.upperLimitScale = 512.0f;
            this.lowerLimitScale = 512.0f;
            this.depthNoiseScaleX = 200.0f;
            this.depthNoiseScaleZ = 200.0f;
            this.depthNoiseScaleExponent = 0.5f;
            this.mainNoiseScaleX = 80.0f;
            this.mainNoiseScaleY = 160.0f;
            this.mainNoiseScaleZ = 80.0f;
            this.baseSize = 8.5f;
            this.stretchY = 12.0f;
            this.biomeDepthWeight = 1.0f;
            this.biomeDepthOffset = 0.0f;
            this.biomeScaleWeight = 1.0f;
            this.biomeScaleOffset = 0.0f;
            this.seaLevel = 63;
            this.useCaves = true;
            this.useDungeons = true;
            this.dungeonChance = 8;
            this.useStrongholds = true;
            this.useVillages = true;
            this.useMineShafts = true;
            this.useTemples = true;
            this.useMonuments = true;
            this.useRavines = true;
            this.useWaterLakes = true;
            this.waterLakeChance = 4;
            this.useLavaLakes = true;
            this.lavaLakeChance = 80;
            this.useLavaOceans = false;
            this.fixedBiome = -1;
            this.biomeSize = 4;
            this.riverSize = 4;
            this.dirtSize = 33;
            this.dirtCount = 10;
            this.dirtMinHeight = 0;
            this.dirtMaxHeight = 256;
            this.gravelSize = 33;
            this.gravelCount = 8;
            this.gravelMinHeight = 0;
            this.gravelMaxHeight = 256;
            this.graniteSize = 33;
            this.graniteCount = 10;
            this.graniteMinHeight = 0;
            this.graniteMaxHeight = 80;
            this.dioriteSize = 33;
            this.dioriteCount = 10;
            this.dioriteMinHeight = 0;
            this.dioriteMaxHeight = 80;
            this.andesiteSize = 33;
            this.andesiteCount = 10;
            this.andesiteMinHeight = 0;
            this.andesiteMaxHeight = 80;
            this.coalSize = 17;
            this.coalCount = 20;
            this.coalMinHeight = 0;
            this.coalMaxHeight = 128;
            this.ironSize = 9;
            this.ironCount = 20;
            this.ironMinHeight = 0;
            this.ironMaxHeight = 64;
            this.goldSize = 9;
            this.goldCount = 2;
            this.goldMinHeight = 0;
            this.goldMaxHeight = 32;
            this.redstoneSize = 8;
            this.redstoneCount = 8;
            this.redstoneMinHeight = 0;
            this.redstoneMaxHeight = 16;
            this.diamondSize = 8;
            this.diamondCount = 1;
            this.diamondMinHeight = 0;
            this.diamondMaxHeight = 16;
            this.lapisSize = 7;
            this.lapisCount = 1;
            this.lapisCenterHeight = 16;
            this.lapisSpread = 16;
            this.func_177863_a();
        }
        
        @Override
        public String toString() {
            return Factory.JSON_ADAPTER.toJson((Object)this);
        }
    }
}
