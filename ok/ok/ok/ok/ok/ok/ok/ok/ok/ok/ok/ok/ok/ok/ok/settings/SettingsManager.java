package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings;

import com.nquantum.module.*;
import java.util.*;

public class SettingsManager
{
    private ArrayList settings;
    
    public ArrayList getSettingsByMod(final Module module) {
        final ArrayList<Setting> list = new ArrayList<Setting>();
        for (final Setting setting : this.getSettings()) {
            if (setting.getParentMod().equals(module)) {
                list.add(setting);
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public SettingsManager() {
        this.settings = new ArrayList();
    }
    
    public ArrayList getSettings() {
        return this.settings;
    }
    
    public Setting getSettingByName(final String s) {
        for (final Setting setting : this.getSettings()) {
            if (setting.getName().equalsIgnoreCase(s)) {
                return setting;
            }
        }
        System.err.println("[nigga] Error Setting NOT found: '" + s + "'!");
        return null;
    }
    
    public void rSetting(final Setting setting) {
        this.settings.add(setting);
    }
}
