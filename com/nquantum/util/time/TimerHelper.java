package com.nquantum.util.time;

public class TimerHelper
{
    private long lastMS;
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public TimerHelper() {
        this.lastMS = 0L;
    }
    
    public long getDelay() {
        return System.currentTimeMillis() - this.lastMS;
    }
    
    public void setlastMS(final long lastMS) {
        this.lastMS = lastMS;
    }
    
    public boolean invCooldownElapsed(final long n) {
        return false;
    }
    
    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public int convertToMS(final int n) {
        return 1000 / n;
    }
    
    public boolean hasReacjed(final long n) {
        return this.getCurrentMS() - this.lastMS >= n;
    }
    
    public boolean hasTimeElapsed(final double n) {
        return this.hasTimeReached((long)n);
    }
    
    public boolean hasTimeReached(final long n) {
        return System.currentTimeMillis() - this.lastMS >= n;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
}
