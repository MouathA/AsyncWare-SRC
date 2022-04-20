package net.minecraft.realms;

import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.world.storage.*;
import java.util.*;
import net.minecraft.client.*;

public class RealmsAnvilLevelStorageSource
{
    private ISaveFormat levelStorageSource;
    
    public String getName() {
        return this.levelStorageSource.getName();
    }
    
    public void renameLevel(final String s, final String s2) {
        this.levelStorageSource.renameWorld(s, s2);
    }
    
    public boolean deleteLevel(final String s) {
        return this.levelStorageSource.deleteWorldDirectory(s);
    }
    
    public boolean levelExists(final String s) {
        return this.levelStorageSource.canLoadWorld(s);
    }
    
    public void clearAll() {
        this.levelStorageSource.flushCache();
    }
    
    public boolean isNewLevelIdAcceptable(final String s) {
        return this.levelStorageSource.func_154335_d(s);
    }
    
    public boolean requiresConversion(final String s) {
        return this.levelStorageSource.isOldMapFormat(s);
    }
    
    public boolean isConvertible(final String s) {
        return this.levelStorageSource.func_154334_a(s);
    }
    
    public RealmsAnvilLevelStorageSource(final ISaveFormat levelStorageSource) {
        this.levelStorageSource = levelStorageSource;
    }
    
    public boolean convertLevel(final String s, final IProgressUpdate progressUpdate) {
        return this.levelStorageSource.convertMapFormat(s, progressUpdate);
    }
    
    public List getLevelList() throws AnvilConverterException {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<SaveFormatComparator> iterator = this.levelStorageSource.getSaveList().iterator();
        while (iterator.hasNext()) {
            arrayList.add(new RealmsLevelSummary(iterator.next()));
        }
        return arrayList;
    }
}
