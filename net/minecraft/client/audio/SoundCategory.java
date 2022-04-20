package net.minecraft.client.audio;

import java.util.*;
import com.google.common.collect.*;

public enum SoundCategory
{
    private final int categoryId;
    
    MOBS("MOBS", 5, "hostile", 5), 
    MASTER("MASTER", 0, "master", 0), 
    AMBIENT("AMBIENT", 8, "ambient", 8), 
    WEATHER("WEATHER", 3, "weather", 3), 
    BLOCKS("BLOCKS", 4, "block", 4);
    
    private final String categoryName;
    
    ANIMALS("ANIMALS", 6, "neutral", 6);
    
    private static final Map ID_CATEGORY_MAP;
    private static final Map NAME_CATEGORY_MAP;
    
    RECORDS("RECORDS", 2, "record", 2);
    
    private static final SoundCategory[] $VALUES;
    
    MUSIC("MUSIC", 1, "music", 1), 
    PLAYERS("PLAYERS", 7, "player", 7);
    
    private SoundCategory(final String s, final int n, final String categoryName, final int categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }
    
    public static SoundCategory getCategory(final String s) {
        return SoundCategory.NAME_CATEGORY_MAP.get(s);
    }
    
    public int getCategoryId() {
        return this.categoryId;
    }
    
    public String getCategoryName() {
        return this.categoryName;
    }
    
    static {
        $VALUES = new SoundCategory[] { SoundCategory.MASTER, SoundCategory.MUSIC, SoundCategory.RECORDS, SoundCategory.WEATHER, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.ANIMALS, SoundCategory.PLAYERS, SoundCategory.AMBIENT };
        NAME_CATEGORY_MAP = Maps.newHashMap();
        ID_CATEGORY_MAP = Maps.newHashMap();
        final SoundCategory[] values = values();
        while (0 < values.length) {
            final SoundCategory soundCategory = values[0];
            if (SoundCategory.NAME_CATEGORY_MAP.containsKey(soundCategory.getCategoryName()) || SoundCategory.ID_CATEGORY_MAP.containsKey(soundCategory.getCategoryId())) {
                throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + soundCategory);
            }
            SoundCategory.NAME_CATEGORY_MAP.put(soundCategory.getCategoryName(), soundCategory);
            SoundCategory.ID_CATEGORY_MAP.put(soundCategory.getCategoryId(), soundCategory);
            int n = 0;
            ++n;
        }
    }
}
