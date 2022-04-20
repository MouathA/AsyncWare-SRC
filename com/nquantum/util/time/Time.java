package com.nquantum.util.time;

import java.text.*;
import java.util.*;

public class Time
{
    public static long getTimestamp(final Add... array) {
        final Calendar instance = Calendar.getInstance();
        while (0 < array.length) {
            instance.add(array[0].getField(), array[0].getAmount());
            int n = 0;
            ++n;
        }
        return instance.getTimeInMillis();
    }
    
    public static String getTime() {
        return getTime(Calendar.getInstance().getTimeInMillis());
    }
    
    public static String getTime(final long n) {
        return new SimpleDateFormat("HH:mm:ss (dd/MM/yyyy)").format(n);
    }
    
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }
    
    public static String getTime(final long n, final String s) {
        return new SimpleDateFormat(s).format(n);
    }
    
    public static Timer delayExecute(final Runnable runnable, final int n) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask(runnable, timer) {
            final Timer val$t;
            final Runnable val$runnable;
            
            @Override
            public void run() {
                this.val$runnable.run();
                this.val$t.cancel();
            }
        }, n);
        return timer;
    }
    
    public static class Add
    {
        private int amount;
        private int field;
        
        public int getAmount() {
            return this.amount;
        }
        
        public int getField() {
            return this.field;
        }
        
        public Add(final int field, final int amount) {
            this.field = field;
            this.amount = amount;
        }
    }
}
