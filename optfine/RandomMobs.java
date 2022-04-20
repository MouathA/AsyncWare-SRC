package optfine;

import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import java.io.*;
import net.minecraft.world.*;

public class RandomMobs
{
    private static RenderGlobal renderGlobal;
    public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
    private static Random random;
    private static boolean initialized;
    public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
    private static Map locationProperties;
    public static final String SUFFIX_PROPERTIES = ".properties";
    public static final String SUFFIX_PNG = ".png";
    private static boolean working;
    
    private static RandomMobsProperties getProperties(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        RandomMobsProperties properties = RandomMobs.locationProperties.get(resourcePath);
        if (properties == null) {
            properties = makeProperties(resourceLocation);
            RandomMobs.locationProperties.put(resourcePath, properties);
        }
        return properties;
    }
    
    private static String getParentPath(final String s) {
        while (0 < RandomMobs.DEPENDANT_SUFFIXES.length) {
            final String s2 = RandomMobs.DEPENDANT_SUFFIXES[0];
            if (s.endsWith(s2)) {
                return s.substring(0, s.length() - s2.length());
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static void resetTextures() {
        RandomMobs.locationProperties.clear();
        Config.isRandomMobs();
    }
    
    private static ResourceLocation getPropertyLocation(final ResourceLocation resourceLocation) {
        final ResourceLocation mcpatcherLocation = getMcpatcherLocation(resourceLocation);
        if (mcpatcherLocation == null) {
            return null;
        }
        final String resourceDomain = mcpatcherLocation.getResourceDomain();
        String s2;
        final String s = s2 = mcpatcherLocation.getResourcePath();
        if (s.endsWith(".png")) {
            s2 = s.substring(0, s.length() - 4);
        }
        final ResourceLocation resourceLocation2 = new ResourceLocation(resourceDomain, s2 + ".properties");
        if (Config.hasResource(resourceLocation2)) {
            return resourceLocation2;
        }
        final String parentPath = getParentPath(s2);
        if (parentPath == null) {
            return null;
        }
        final ResourceLocation resourceLocation3 = new ResourceLocation(resourceDomain, parentPath + ".properties");
        return Config.hasResource(resourceLocation3) ? resourceLocation3 : null;
    }
    
    public static void worldChanged(final World world, final World world2) {
        if (world2 != null) {
            final List loadedEntityList = world2.getLoadedEntityList();
            while (0 < loadedEntityList.size()) {
                entityLoaded(loadedEntityList.get(0));
                int n = 0;
                ++n;
            }
        }
    }
    
    private static ResourceLocation[] getTextureVariants(final ResourceLocation resourceLocation) {
        final ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
        list.add(resourceLocation);
        final ResourceLocation mcpatcherLocation = getMcpatcherLocation(resourceLocation);
        if (mcpatcherLocation == null) {
            return null;
        }
        while (1 < list.size() + 10) {
            final ResourceLocation locationIndexed = getLocationIndexed(mcpatcherLocation, 2);
            if (Config.hasResource(locationIndexed)) {
                list.add(locationIndexed);
            }
            int n = 0;
            ++n;
        }
        if (list.size() <= 1) {
            return null;
        }
        final ResourceLocation[] array = list.toArray(new ResourceLocation[list.size()]);
        Config.dbg("RandomMobs: " + resourceLocation.getResourcePath() + ", variants: " + array.length);
        return array;
    }
    
    private static void initialize() {
        RandomMobs.renderGlobal = Config.getRenderGlobal();
        if (RandomMobs.renderGlobal != null) {
            RandomMobs.initialized = true;
            final ArrayList<String> list = new ArrayList<String>();
            list.add("bat");
            list.add("blaze");
            list.add("cat/black");
            list.add("cat/ocelot");
            list.add("cat/red");
            list.add("cat/siamese");
            list.add("chicken");
            list.add("cow/cow");
            list.add("cow/mooshroom");
            list.add("creeper/creeper");
            list.add("enderman/enderman");
            list.add("enderman/enderman_eyes");
            list.add("ghast/ghast");
            list.add("ghast/ghast_shooting");
            list.add("iron_golem");
            list.add("pig/pig");
            list.add("sheep/sheep");
            list.add("sheep/sheep_fur");
            list.add("silverfish");
            list.add("skeleton/skeleton");
            list.add("skeleton/wither_skeleton");
            list.add("slime/slime");
            list.add("slime/magmacube");
            list.add("snowman");
            list.add("spider/cave_spider");
            list.add("spider/spider");
            list.add("spider_eyes");
            list.add("squid");
            list.add("villager/villager");
            list.add("villager/butcher");
            list.add("villager/farmer");
            list.add("villager/librarian");
            list.add("villager/priest");
            list.add("villager/smith");
            list.add("wither/wither");
            list.add("wither/wither_armor");
            list.add("wither/wither_invulnerable");
            list.add("wolf/wolf");
            list.add("wolf/wolf_angry");
            list.add("wolf/wolf_collar");
            list.add("wolf/wolf_tame");
            list.add("zombie_pigman");
            list.add("zombie/zombie");
            list.add("zombie/zombie_villager");
            while (0 < list.size()) {
                final ResourceLocation resourceLocation = new ResourceLocation("textures/entity/" + list.get(0) + ".png");
                if (!Config.hasResource(resourceLocation)) {
                    Config.warn("Not found: " + resourceLocation);
                }
                getProperties(resourceLocation);
                int n = 0;
                ++n;
            }
        }
    }
    
    private static RandomMobsProperties makeProperties(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        final ResourceLocation propertyLocation = getPropertyLocation(resourceLocation);
        if (propertyLocation != null) {
            final RandomMobsProperties properties = parseProperties(propertyLocation, resourceLocation);
            if (properties != null) {
                return properties;
            }
        }
        return new RandomMobsProperties(resourcePath, getTextureVariants(resourceLocation));
    }
    
    static {
        RandomMobs.locationProperties = new HashMap();
        RandomMobs.renderGlobal = null;
        RandomMobs.initialized = false;
        RandomMobs.random = new Random();
        RandomMobs.working = false;
        RandomMobs.DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };
    }
    
    public static ResourceLocation getMcpatcherLocation(final ResourceLocation resourceLocation) {
        final String resourcePath = resourceLocation.getResourcePath();
        if (!resourcePath.startsWith("textures/entity/")) {
            return null;
        }
        return new ResourceLocation(resourceLocation.getResourceDomain(), "mcpatcher/mob/" + resourcePath.substring(16));
    }
    
    public static ResourceLocation getLocationIndexed(final ResourceLocation resourceLocation, final int n) {
        if (resourceLocation == null) {
            return null;
        }
        final String resourcePath = resourceLocation.getResourcePath();
        final int lastIndex = resourcePath.lastIndexOf(46);
        if (lastIndex < 0) {
            return null;
        }
        return new ResourceLocation(resourceLocation.getResourceDomain(), resourcePath.substring(0, lastIndex) + n + resourcePath.substring(lastIndex));
    }
    
    public static ResourceLocation getTextureLocation(final ResourceLocation resourceLocation) {
        if (RandomMobs.working) {
            return resourceLocation;
        }
        RandomMobs.working = true;
        RandomMobs.initialized;
        if (RandomMobs.renderGlobal == null) {
            RandomMobs.working = false;
            return resourceLocation;
        }
        final Entity renderedEntity = RandomMobs.renderGlobal.renderedEntity;
        if (!(renderedEntity instanceof EntityLiving)) {
            RandomMobs.working = false;
            return resourceLocation;
        }
        final EntityLiving entityLiving = (EntityLiving)renderedEntity;
        if (!resourceLocation.getResourcePath().startsWith("textures/entity/")) {
            RandomMobs.working = false;
            return resourceLocation;
        }
        final RandomMobsProperties properties = getProperties(resourceLocation);
        if (properties == null) {
            RandomMobs.working = false;
            return resourceLocation;
        }
        final ResourceLocation textureLocation = properties.getTextureLocation(resourceLocation, entityLiving);
        RandomMobs.working = false;
        return textureLocation;
    }
    
    private static RandomMobsProperties parseProperties(final ResourceLocation resourceLocation, final ResourceLocation resourceLocation2) {
        final String resourcePath = resourceLocation.getResourcePath();
        Config.dbg("RandomMobs: " + resourceLocation2.getResourcePath() + ", variants: " + resourcePath);
        final InputStream resourceStream = Config.getResourceStream(resourceLocation);
        if (resourceStream == null) {
            Config.warn("RandomMobs properties not found: " + resourcePath);
            return null;
        }
        final Properties properties = new Properties();
        properties.load(resourceStream);
        final RandomMobsProperties randomMobsProperties = new RandomMobsProperties(properties, resourcePath, resourceLocation2);
        return randomMobsProperties.isValid(resourcePath) ? randomMobsProperties : null;
    }
    
    public static void entityLoaded(final Entity entity) {
        if (entity instanceof EntityLiving) {
            final EntityLiving entityLiving = (EntityLiving)entity;
            final WorldServer worldServer = Config.getWorldServer();
            if (worldServer != null) {
                final Entity entityByID = worldServer.getEntityByID(entity.getEntityId());
                if (entityByID instanceof EntityLiving) {
                    entityLiving.randomMobsId = (int)(((EntityLiving)entityByID).getUniqueID().getLeastSignificantBits() & 0x7FFFFFFFL);
                    entityLiving.spawnPosition = entityLiving.getPosition();
                    entityLiving.spawnBiome = worldServer.getBiomeGenForCoords(entityLiving.spawnPosition);
                }
            }
        }
    }
}
