package com.nquantum.util.time;

public class Timer
{
    private long tim2e;
    public long lastMS;
    private long prevMS;
    private long time2;
    
    public boolean reach(final long n) {
        return this.time() >= n;
    }
    
    public void clear() {
    }
    
    public boolean hasTimeElapsed(final long n, final boolean b) {
        if (System.currentTimeMillis() - this.lastMS > n) {
            if (b) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public long time() {
        return System.nanoTime() / 1000000L - this.tim2e;
    }
    
    public boolean delay(final float n) {
        return this.time() - this.prevMS >= n;
    }
    
    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public Timer() {
        this.lastMS = System.currentTimeMillis();
        this.time2 = System.nanoTime() / 1000000L;
        this.prevMS = 0L;
    }
}
