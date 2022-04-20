package net.minecraft.client.resources;

import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class LanguageManager implements IResourceManagerReloadListener
{
    private String currentLanguage;
    protected static final Locale currentLocale;
    private static final Logger logger;
    private Map languageMap;
    private final IMetadataSerializer theMetadataSerializer;
    
    public Language getCurrentLanguage() {
        return this.languageMap.containsKey(this.currentLanguage) ? this.languageMap.get(this.currentLanguage) : this.languageMap.get("en_US");
    }
    
    public boolean isCurrentLanguageBidirectional() {
        return this.getCurrentLanguage() != null && this.getCurrentLanguage().isBidirectional();
    }
    
    public LanguageManager(final IMetadataSerializer theMetadataSerializer, final String currentLanguage) {
        this.languageMap = Maps.newHashMap();
        this.theMetadataSerializer = theMetadataSerializer;
        this.currentLanguage = currentLanguage;
        I18n.setLocale(LanguageManager.currentLocale);
    }
    
    public SortedSet getLanguages() {
        return Sets.newTreeSet((Iterable)this.languageMap.values());
    }
    
    public boolean isCurrentLocaleUnicode() {
        return LanguageManager.currentLocale.isUnicode();
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        final ArrayList arrayList = Lists.newArrayList((Object[])new String[] { "en_US" });
        if (!"en_US".equals(this.currentLanguage)) {
            arrayList.add(this.currentLanguage);
        }
        LanguageManager.currentLocale.loadLocaleDataFiles(resourceManager, arrayList);
        StringTranslate.replaceWith(LanguageManager.currentLocale.properties);
    }
    
    public void setCurrentLanguage(final Language language) {
        this.currentLanguage = language.getLanguageCode();
    }
    
    public void parseLanguageMetadata(final List list) {
        this.languageMap.clear();
        final Iterator<IResourcePack> iterator = list.iterator();
        while (iterator.hasNext()) {
            final LanguageMetadataSection languageMetadataSection = (LanguageMetadataSection)iterator.next().getPackMetadata(this.theMetadataSerializer, "language");
            if (languageMetadataSection != null) {
                for (final Language language : languageMetadataSection.getLanguages()) {
                    if (!this.languageMap.containsKey(language.getLanguageCode())) {
                        this.languageMap.put(language.getLanguageCode(), language);
                    }
                }
            }
        }
    }
    
    static {
        logger = LogManager.getLogger();
        currentLocale = new Locale();
    }
}
