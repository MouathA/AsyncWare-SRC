package net.minecraft.world.storage;

import net.minecraft.world.*;

public class SaveDataMemoryStorage extends MapStorage
{
    @Override
    public void saveAllData() {
    }
    
    @Override
    public void setData(final String s, final WorldSavedData worldSavedData) {
        this.loadedDataMap.put(s, worldSavedData);
    }
    
    public SaveDataMemoryStorage() {
        super(null);
    }
    
    @Override
    public WorldSavedData loadData(final Class clazz, final String s) {
        return this.loadedDataMap.get(s);
    }
    
    @Override
    public int getUniqueDataId(final String s) {
        return 0;
    }
}
