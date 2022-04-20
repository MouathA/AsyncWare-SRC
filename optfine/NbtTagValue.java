package optfine;

public class NbtTagValue
{
    private String tag;
    private String value;
    
    public NbtTagValue(final String tag, final String value) {
        this.tag = null;
        this.value = null;
        this.tag = tag;
        this.value = value;
    }
    
    public boolean matches(final String s, final String s2) {
        return false;
    }
}
