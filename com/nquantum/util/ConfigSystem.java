package com.nquantum.util;

import com.nquantum.*;
import com.nquantum.module.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import java.util.*;
import java.io.*;
import java.util.function.*;

public class ConfigSystem
{
    public static List results;
    
    private static void lambda$load$0(final String s) {
        if (s.split(":")[0].startsWith("module") && s.split(":")[0].startsWith("setting") && Asyncware.instance.settingsManager.getSettingByName(s.split(":")[1]) != null) {
            if (Asyncware.instance.settingsManager.getSettingByName(s.split(":")[1]).isCheck()) {
                Asyncware.instance.settingsManager.getSettingByName(s.split(":")[1]).setValBoolean(Boolean.parseBoolean(s.split(":")[2]));
            }
            if (Asyncware.instance.settingsManager.getSettingByName(s.split(":")[1]).isSlider()) {
                Asyncware.instance.settingsManager.getSettingByName(s.split(":")[1]).setValDouble(Double.parseDouble(s.split(":")[2]));
            }
            if (Asyncware.instance.settingsManager.getSettingByName(s.split(":")[1]).isCombo()) {
                Asyncware.instance.settingsManager.getSettingByName(s.split(":")[1]).setValString(s.split(":")[2]);
            }
        }
    }
    
    static {
        ConfigSystem.results = new ArrayList();
    }
    
    public static void getConfigs() {
        final File[] listFiles = new File("Asyncware/configs/").listFiles();
        while (0 < listFiles.length) {
            final File file = listFiles[0];
            if (file.isFile()) {
                ConfigSystem.results.add(file.getName());
            }
            int n = 0;
            ++n;
        }
    }
    
    public static void save(final List list, final String s) {
        final File file = new File("Asyncware/configs/" + s + ".cfg");
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
        if (new File("Asyncware/configs/").mkdir() || !file.exists()) {
            file.createNewFile();
        }
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (final Module module : list) {
            if (!module.getCategory().equals(Category.RENDER)) {
                bufferedWriter.write("module:" + module.getName() + ":" + module.isToggled() + "\n");
            }
            bufferedWriter.flush();
        }
        for (final Setting setting : Asyncware.instance.settingsManager.getSettings()) {
            if (!setting.getParentMod().getCategory().equals(Category.RENDER)) {
                if (setting.isCheck()) {
                    bufferedWriter.write("setting:" + setting.getName() + ":" + setting.getValBoolean() + "\n");
                }
                if (setting.isCombo()) {
                    bufferedWriter.write("setting:" + setting.getName() + ":" + setting.getValString() + "\n");
                }
                if (setting.isSlider()) {
                    bufferedWriter.write("setting:" + setting.getName() + ":" + setting.getValDouble() + "\n");
                }
            }
            bufferedWriter.flush();
        }
        bufferedWriter.close();
    }
    
    public static void load(final String s) {
        final File file = new File("Asyncware/configs/" + s + ".cfg");
        final File file2 = new File("Asyncware/configs/");
        if (new File("Asyncware/configs/").mkdir()) {
            file2.createNewFile();
            return;
        }
        file.exists();
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        final Object o = null;
        bufferedReader.lines().forEach(ConfigSystem::lambda$load$0);
        if (bufferedReader != null) {
            if (o != null) {
                bufferedReader.close();
            }
            else {
                bufferedReader.close();
            }
        }
    }
}
