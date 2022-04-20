package com.nquantum.util.auth;

public enum OS
{
    LINUX("LINUX", 1), 
    WINDOWS("WINDOWS", 2), 
    MAC("MAC", 0);
    
    private static final OS[] $VALUES;
    
    private OS(final String s, final int n) {
    }
    
    static {
        $VALUES = new OS[] { OS.MAC, OS.LINUX, OS.WINDOWS };
    }
}
