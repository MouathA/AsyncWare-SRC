package com.nquantum.util.time;

public class Stopwatch
{
    private long prevTime;
    
    public void setTime(final long n) {
        this.prevTime -= n;
    }
    
    public double getPassed() {
        return (double)(this.getTime() - this.prevTime);
    }
    
    public boolean hasPassed(final double n, final boolean b) {
        final boolean b2 = this.getTime() - this.prevTime >= n;
        if (b) {
            this.reset();
        }
        return b2;
    }
    
    public void reset() {
        this.prevTime = this.getTime();
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean hasPassed(final double n) {
        return this.getTime() - this.prevTime >= n;
    }
    
    public Stopwatch() {
        this.reset();
    }
}
