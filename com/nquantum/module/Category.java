package com.nquantum.module;

public enum Category
{
    MISC("MISC", 5);
    
    private static final Category[] $VALUES;
    
    CUSTOMIZE("CUSTOMIZE", 6), 
    EXPLOIT("EXPLOIT", 4), 
    COMBAT("COMBAT", 0), 
    PLAYER("PLAYER", 3), 
    RENDER("RENDER", 2), 
    MOVEMENT("MOVEMENT", 1);
    
    private Category(final String s, final int n) {
    }
    
    static {
        $VALUES = new Category[] { Category.COMBAT, Category.MOVEMENT, Category.RENDER, Category.PLAYER, Category.EXPLOIT, Category.MISC, Category.CUSTOMIZE };
    }
}
