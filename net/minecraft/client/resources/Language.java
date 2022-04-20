package net.minecraft.client.resources;

public class Language implements Comparable
{
    private final String languageCode;
    private final String name;
    private final String region;
    private final boolean bidirectional;
    
    public Language(final String languageCode, final String region, final String name, final boolean bidirectional) {
        this.languageCode = languageCode;
        this.region = region;
        this.name = name;
        this.bidirectional = bidirectional;
    }
    
    public String getLanguageCode() {
        return this.languageCode;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof Language && this.languageCode.equals(((Language)o).languageCode));
    }
    
    public boolean isBidirectional() {
        return this.bidirectional;
    }
    
    public int compareTo(final Language language) {
        return this.languageCode.compareTo(language.languageCode);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((Language)o);
    }
    
    @Override
    public int hashCode() {
        return this.languageCode.hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.region);
    }
}
