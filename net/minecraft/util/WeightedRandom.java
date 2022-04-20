package net.minecraft.util;

import java.util.*;

public class WeightedRandom
{
    public static Item getRandomItem(final Random random, final Collection collection, final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        return getRandomItem(collection, random.nextInt(n));
    }
    
    public static Item getRandomItem(final Collection collection, int n) {
        for (final Item item : collection) {
            n -= item.itemWeight;
            if (n < 0) {
                return item;
            }
        }
        return null;
    }
    
    public static int getTotalWeight(final Collection collection) {
        final Iterator<Item> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final int n = 0 + iterator.next().itemWeight;
        }
        return 0;
    }
    
    public static Item getRandomItem(final Random random, final Collection collection) {
        return getRandomItem(random, collection, getTotalWeight(collection));
    }
    
    public static class Item
    {
        protected int itemWeight;
        
        public Item(final int itemWeight) {
            this.itemWeight = itemWeight;
        }
    }
}
