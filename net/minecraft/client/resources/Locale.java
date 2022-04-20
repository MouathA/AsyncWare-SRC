package net.minecraft.client.resources;

import java.util.regex.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import java.util.*;
import org.apache.commons.io.*;
import java.io.*;
import com.google.common.collect.*;

public class Locale
{
    private static final Pattern pattern;
    Map properties;
    private boolean unicode;
    private static final Splitter splitter;
    
    public synchronized void loadLocaleDataFiles(final IResourceManager resourceManager, final List list) {
        this.properties.clear();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            final String format = String.format("lang/%s.lang", iterator.next());
            final Iterator<String> iterator2 = resourceManager.getResourceDomains().iterator();
            while (iterator2.hasNext()) {
                this.loadLocaleData(resourceManager.getAllResources(new ResourceLocation(iterator2.next(), format)));
            }
        }
        this.checkUnicode();
    }
    
    public boolean isUnicode() {
        return this.unicode;
    }
    
    public String formatMessage(final String s, final Object[] array) {
        return String.format(this.translateKeyPrivate(s), array);
    }
    
    private void loadLocaleData(final InputStream inputStream) throws IOException {
        for (final String s : IOUtils.readLines(inputStream, Charsets.UTF_8)) {
            if (!s.isEmpty() && s.charAt(0) != '#') {
                final String[] array = (String[])Iterables.toArray(Locale.splitter.split((CharSequence)s), (Class)String.class);
                if (array == null || array.length != 2) {
                    continue;
                }
                this.properties.put(array[0], Locale.pattern.matcher(array[1]).replaceAll("%$1s"));
            }
        }
    }
    
    private void checkUnicode() {
        this.unicode = false;
        for (final String s : this.properties.values()) {
            while (0 < s.length()) {
                if (s.charAt(0) >= '\u0100') {
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        this.unicode = (0 / (float)0 > 0.1);
    }
    
    static {
        splitter = Splitter.on('=').limit(2);
        pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    }
    
    public Locale() {
        this.properties = Maps.newHashMap();
    }
    
    private void loadLocaleData(final List list) throws IOException {
        final Iterator<IResource> iterator = list.iterator();
        while (iterator.hasNext()) {
            final InputStream inputStream = iterator.next().getInputStream();
            this.loadLocaleData(inputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }
    
    private String translateKeyPrivate(final String s) {
        final String s2 = this.properties.get(s);
        return (s2 == null) ? s : s2;
    }
}
