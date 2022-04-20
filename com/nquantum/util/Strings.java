package com.nquantum.util;

public class Strings
{
    public static String upperCaseFirst(final String s) {
        final char[] charArray = s.toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        return new String(charArray);
    }
    
    public static String capitalizeOnlyFirstLetter(final String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
    }
}
