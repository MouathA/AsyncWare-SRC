package net.minecraft.util;

import com.google.common.base.*;
import java.util.regex.*;
import org.apache.commons.io.*;
import com.google.common.collect.*;
import java.util.*;

public class StringTranslate
{
    private long lastUpdateTimeInMilliseconds;
    private static StringTranslate instance;
    private static final Splitter equalSignSplitter;
    private static final Pattern numericVariablePattern;
    private final Map languageList;
    
    public StringTranslate() {
        this.languageList = Maps.newHashMap();
        for (final String s : IOUtils.readLines(StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang"), Charsets.UTF_8)) {
            if (!s.isEmpty() && s.charAt(0) != '#') {
                final String[] array = (String[])Iterables.toArray(StringTranslate.equalSignSplitter.split((CharSequence)s), (Class)String.class);
                if (array == null || array.length != 2) {
                    continue;
                }
                this.languageList.put(array[0], StringTranslate.numericVariablePattern.matcher(array[1]).replaceAll("%$1s"));
            }
        }
        this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
    }
    
    public synchronized String translateKeyFormat(final String s, final Object... array) {
        return String.format(this.tryTranslateKey(s), array);
    }
    
    private String tryTranslateKey(final String s) {
        final String s2 = this.languageList.get(s);
        return (s2 == null) ? s : s2;
    }
    
    static {
        numericVariablePattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
        equalSignSplitter = Splitter.on('=').limit(2);
        StringTranslate.instance = new StringTranslate();
    }
    
    public synchronized String translateKey(final String s) {
        return this.tryTranslateKey(s);
    }
    
    public synchronized boolean isKeyTranslated(final String s) {
        return this.languageList.containsKey(s);
    }
    
    public static synchronized void replaceWith(final Map map) {
        StringTranslate.instance.languageList.clear();
        StringTranslate.instance.languageList.putAll(map);
        StringTranslate.instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
    }
    
    static StringTranslate getInstance() {
        return StringTranslate.instance;
    }
    
    public long getLastUpdateTimeInMilliseconds() {
        return this.lastUpdateTimeInMilliseconds;
    }
}
