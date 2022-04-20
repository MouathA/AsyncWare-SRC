package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;

public enum EnumParticleTypes
{
    SPELL("SPELL", 13, "spell", 13, false), 
    NOTE("NOTE", 23, "note", 23, false);
    
    private final String particleName;
    
    FLAME("FLAME", 26, "flame", 26, false), 
    WATER_BUBBLE("WATER_BUBBLE", 4, "bubble", 4, false), 
    EXPLOSION_HUGE("EXPLOSION_HUGE", 2, "hugeexplosion", 2, true), 
    BLOCK_CRACK("BLOCK_CRACK", 37, "blockcrack_", 37, false, 1), 
    REDSTONE("REDSTONE", 30, "reddust", 30, false), 
    BLOCK_DUST("BLOCK_DUST", 38, "blockdust_", 38, false, 1), 
    WATER_SPLASH("WATER_SPLASH", 5, "splash", 5, false), 
    SNOWBALL("SNOWBALL", 31, "snowballpoof", 31, false), 
    BARRIER("BARRIER", 35, "barrier", 35, false), 
    TOWN_AURA("TOWN_AURA", 22, "townaura", 22, false), 
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 25, "enchantmenttable", 25, false), 
    SUSPENDED_DEPTH("SUSPENDED_DEPTH", 8, "depthsuspend", 8, false), 
    WATER_DROP("WATER_DROP", 39, "droplet", 39, false), 
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", 16, "mobSpellAmbient", 16, false), 
    FOOTSTEP("FOOTSTEP", 28, "footstep", 28, false);
    
    private static final Map PARTICLES;
    
    CRIT_MAGIC("CRIT_MAGIC", 10, "magicCrit", 10, false);
    
    private final boolean shouldIgnoreRange;
    
    DRIP_WATER("DRIP_WATER", 18, "dripWater", 18, false), 
    DRIP_LAVA("DRIP_LAVA", 19, "dripLava", 19, false);
    
    private final int argumentCount;
    
    VILLAGER_ANGRY("VILLAGER_ANGRY", 20, "angryVillager", 20, false), 
    WATER_WAKE("WATER_WAKE", 6, "wake", 6, false), 
    PORTAL("PORTAL", 24, "portal", 24, false), 
    FIREWORKS_SPARK("FIREWORKS_SPARK", 3, "fireworksSpark", 3, false), 
    SNOW_SHOVEL("SNOW_SHOVEL", 32, "snowshovel", 32, false), 
    SMOKE_NORMAL("SMOKE_NORMAL", 11, "smoke", 11, false), 
    VILLAGER_HAPPY("VILLAGER_HAPPY", 21, "happyVillager", 21, false), 
    EXPLOSION_LARGE("EXPLOSION_LARGE", 1, "largeexplode", 1, true), 
    SPELL_WITCH("SPELL_WITCH", 17, "witchMagic", 17, false), 
    SMOKE_LARGE("SMOKE_LARGE", 12, "largesmoke", 12, false), 
    CLOUD("CLOUD", 29, "cloud", 29, false), 
    EXPLOSION_NORMAL("EXPLOSION_NORMAL", 0, "explode", 0, true);
    
    private final int particleID;
    
    MOB_APPEARANCE("MOB_APPEARANCE", 41, "mobappearance", 41, true), 
    SPELL_INSTANT("SPELL_INSTANT", 14, "instantSpell", 14, false), 
    HEART("HEART", 34, "heart", 34, false), 
    SUSPENDED("SUSPENDED", 7, "suspended", 7, false), 
    SLIME("SLIME", 33, "slime", 33, false), 
    ITEM_CRACK("ITEM_CRACK", 36, "iconcrack_", 36, false, 2), 
    CRIT("CRIT", 9, "crit", 9, false), 
    ITEM_TAKE("ITEM_TAKE", 40, "take", 40, false), 
    SPELL_MOB("SPELL_MOB", 15, "mobSpell", 15, false), 
    LAVA("LAVA", 27, "lava", 27, false);
    
    private static final EnumParticleTypes[] $VALUES;
    
    public boolean hasArguments() {
        return this.argumentCount > 0;
    }
    
    public int getParticleID() {
        return this.particleID;
    }
    
    public static String[] getParticleNames() {
        return EnumParticleTypes.PARTICLE_NAMES;
    }
    
    public int getArgumentCount() {
        return this.argumentCount;
    }
    
    private EnumParticleTypes(final String s, final int n, final String s2, final int n2, final boolean b) {
        this(s, n, s2, n2, b, 0);
    }
    
    public boolean getShouldIgnoreRange() {
        return this.shouldIgnoreRange;
    }
    
    public static EnumParticleTypes getParticleFromId(final int n) {
        return EnumParticleTypes.PARTICLES.get(n);
    }
    
    static {
        $VALUES = new EnumParticleTypes[] { EnumParticleTypes.EXPLOSION_NORMAL, EnumParticleTypes.EXPLOSION_LARGE, EnumParticleTypes.EXPLOSION_HUGE, EnumParticleTypes.FIREWORKS_SPARK, EnumParticleTypes.WATER_BUBBLE, EnumParticleTypes.WATER_SPLASH, EnumParticleTypes.WATER_WAKE, EnumParticleTypes.SUSPENDED, EnumParticleTypes.SUSPENDED_DEPTH, EnumParticleTypes.CRIT, EnumParticleTypes.CRIT_MAGIC, EnumParticleTypes.SMOKE_NORMAL, EnumParticleTypes.SMOKE_LARGE, EnumParticleTypes.SPELL, EnumParticleTypes.SPELL_INSTANT, EnumParticleTypes.SPELL_MOB, EnumParticleTypes.SPELL_MOB_AMBIENT, EnumParticleTypes.SPELL_WITCH, EnumParticleTypes.DRIP_WATER, EnumParticleTypes.DRIP_LAVA, EnumParticleTypes.VILLAGER_ANGRY, EnumParticleTypes.VILLAGER_HAPPY, EnumParticleTypes.TOWN_AURA, EnumParticleTypes.NOTE, EnumParticleTypes.PORTAL, EnumParticleTypes.ENCHANTMENT_TABLE, EnumParticleTypes.FLAME, EnumParticleTypes.LAVA, EnumParticleTypes.FOOTSTEP, EnumParticleTypes.CLOUD, EnumParticleTypes.REDSTONE, EnumParticleTypes.SNOWBALL, EnumParticleTypes.SNOW_SHOVEL, EnumParticleTypes.SLIME, EnumParticleTypes.HEART, EnumParticleTypes.BARRIER, EnumParticleTypes.ITEM_CRACK, EnumParticleTypes.BLOCK_CRACK, EnumParticleTypes.BLOCK_DUST, EnumParticleTypes.WATER_DROP, EnumParticleTypes.ITEM_TAKE, EnumParticleTypes.MOB_APPEARANCE };
        PARTICLES = Maps.newHashMap();
        final ArrayList arrayList = Lists.newArrayList();
        final EnumParticleTypes[] values = values();
        while (0 < values.length) {
            final EnumParticleTypes enumParticleTypes = values[0];
            EnumParticleTypes.PARTICLES.put(enumParticleTypes.getParticleID(), enumParticleTypes);
            if (!enumParticleTypes.getParticleName().endsWith("_")) {
                arrayList.add(enumParticleTypes.getParticleName());
            }
            int n = 0;
            ++n;
        }
        EnumParticleTypes.PARTICLE_NAMES = arrayList.toArray(new String[arrayList.size()]);
    }
    
    private EnumParticleTypes(final String s, final int n, final String particleName, final int particleID, final boolean shouldIgnoreRange, final int argumentCount) {
        this.particleName = particleName;
        this.particleID = particleID;
        this.shouldIgnoreRange = shouldIgnoreRange;
        this.argumentCount = argumentCount;
    }
    
    public String getParticleName() {
        return this.particleName;
    }
}
