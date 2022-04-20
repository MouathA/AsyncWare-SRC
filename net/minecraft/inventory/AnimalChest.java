package net.minecraft.inventory;

import net.minecraft.util.*;

public class AnimalChest extends InventoryBasic
{
    public AnimalChest(final IChatComponent chatComponent, final int n) {
        super(chatComponent, n);
    }
    
    public AnimalChest(final String s, final int n) {
        super(s, false, n);
    }
}
