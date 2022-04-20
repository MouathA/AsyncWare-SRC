package com.nquantum.util.color;

import java.awt.*;

public enum Clr
{
    DARKGREEN("DARKGREEN", 4, -9320847), 
    WHITE("WHITE", 5, -65794), 
    DARKRED("DARKRED", 11, -8388608);
    
    public int c;
    
    GREY("GREY", 8, -9868951), 
    DARKMAGENTA("DARKMAGENTA", 17, -2252579), 
    MAGENTA("MAGENTA", 16, -18751), 
    BLACK("BLACK", 0, -16711423);
    
    private static final Clr[] $VALUES;
    
    GREEN("GREEN", 3, -9830551), 
    ORANGE("ORANGE", 12, -29696), 
    DARKGREY("DARKGREY", 9, -14342875), 
    BLUE("BLUE", 1, -12028161), 
    DARKYELLOW("DARKYELLOW", 15, -2702025), 
    DARKBLUE("DARKBLUE", 2, -12621684), 
    DARKORANGE("DARKORANGE", 13, -2263808), 
    RED("RED", 10, -65536), 
    AQUA("AQUA", 6, -7820064), 
    YELLOW("YELLOW", 14, -256), 
    DARKAQUA("DARKAQUA", 7, -12621684);
    
    public static int getColor(final int n) {
        return Colors.getColor(n, n, n, 255);
    }
    
    static {
        $VALUES = new Clr[] { Clr.BLACK, Clr.BLUE, Clr.DARKBLUE, Clr.GREEN, Clr.DARKGREEN, Clr.WHITE, Clr.AQUA, Clr.DARKAQUA, Clr.GREY, Clr.DARKGREY, Clr.RED, Clr.DARKRED, Clr.ORANGE, Clr.DARKORANGE, Clr.YELLOW, Clr.DARKYELLOW, Clr.MAGENTA, Clr.DARKMAGENTA };
    }
    
    private Clr(final String s, final int n, final int c) {
        this.c = c;
    }
    
    public static int getColor(final int n, final int n2, final int n3, final int n4) {
        return 0x0 | n4 << 24 | n << 16 | n2 << 8 | n3;
    }
    
    public static int getColor(final int n, final int n2) {
        return Colors.getColor(n, n, n, n2);
    }
    
    public static int getColor(final Color color) {
        return Colors.getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static int getColor(final int n, final int n2, final int n3) {
        return Colors.getColor(n, n2, n3, 255);
    }
}
