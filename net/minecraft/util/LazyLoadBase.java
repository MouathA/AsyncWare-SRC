package net.minecraft.util;

public abstract class LazyLoadBase
{
    private boolean isLoaded;
    private Object value;
    
    public Object getValue() {
        if (!this.isLoaded) {
            this.isLoaded = true;
            this.value = this.load();
        }
        return this.value;
    }
    
    public LazyLoadBase() {
        this.isLoaded = false;
    }
    
    protected abstract Object load();
}
