package optfine;

public class RangeInt
{
    private int max;
    private int min;
    
    public boolean isInRange(final int n) {
        return n >= this.min && n <= this.max;
    }
    
    public RangeInt(final int n, final int n2) {
        this.min = Math.min(n, n2);
        this.max = Math.max(n, n2);
    }
    
    @Override
    public String toString() {
        return "min: " + this.min + ", max: " + this.max;
    }
}
