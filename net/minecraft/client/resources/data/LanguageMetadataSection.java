package net.minecraft.client.resources.data;

import java.util.*;

public class LanguageMetadataSection implements IMetadataSection
{
    private final Collection languages;
    
    public Collection getLanguages() {
        return this.languages;
    }
    
    public LanguageMetadataSection(final Collection languages) {
        this.languages = languages;
    }
}
