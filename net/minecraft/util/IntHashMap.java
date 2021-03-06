package net.minecraft.util;

public class IntHashMap
{
    private final float growFactor = 0.75f;
    private int threshold;
    private transient Entry[] slots;
    private transient int count;
    
    private void grow(final int n) {
        if (this.slots.length == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
        }
        else {
            final Entry[] slots = new Entry[n];
            this.copyTo(slots);
            this.slots = slots;
            final float n2 = (float)n;
            this.getClass();
            this.threshold = (int)(n2 * 0.75f);
        }
    }
    
    public boolean containsItem(final int n) {
        return this.lookupEntry(n) != null;
    }
    
    public Object lookup(final int n) {
        for (Entry nextEntry = this.slots[getSlotIndex(computeHash(n), this.slots.length)]; nextEntry != null; nextEntry = nextEntry.nextEntry) {
            if (nextEntry.hashEntry == n) {
                return nextEntry.valueEntry;
            }
        }
        return null;
    }
    
    public Object removeObject(final int n) {
        final Entry removeEntry = this.removeEntry(n);
        return (removeEntry == null) ? null : removeEntry.valueEntry;
    }
    
    final Entry removeEntry(final int n) {
        final int slotIndex = getSlotIndex(computeHash(n), this.slots.length);
        Entry entry2;
        Entry nextEntry;
        for (Entry entry = entry2 = this.slots[slotIndex]; entry2 != null; entry2 = nextEntry) {
            nextEntry = entry2.nextEntry;
            if (entry2.hashEntry == n) {
                --this.count;
                if (entry == entry2) {
                    this.slots[slotIndex] = nextEntry;
                }
                else {
                    entry.nextEntry = nextEntry;
                }
                return entry2;
            }
            entry = entry2;
        }
        return entry2;
    }
    
    public void addKey(final int n, final Object valueEntry) {
        final int computeHash = computeHash(n);
        final int slotIndex = getSlotIndex(computeHash, this.slots.length);
        for (Entry nextEntry = this.slots[slotIndex]; nextEntry != null; nextEntry = nextEntry.nextEntry) {
            if (nextEntry.hashEntry == n) {
                nextEntry.valueEntry = valueEntry;
                return;
            }
        }
        this.insert(computeHash, n, valueEntry, slotIndex);
    }
    
    public IntHashMap() {
        this.slots = new Entry[16];
        this.threshold = 12;
    }
    
    private static int getSlotIndex(final int n, final int n2) {
        return n & n2 - 1;
    }
    
    public void clearMap() {
        final Entry[] slots = this.slots;
        while (0 < slots.length) {
            slots[0] = null;
            int n = 0;
            ++n;
        }
        this.count = 0;
    }
    
    final Entry lookupEntry(final int n) {
        for (Entry nextEntry = this.slots[getSlotIndex(computeHash(n), this.slots.length)]; nextEntry != null; nextEntry = nextEntry.nextEntry) {
            if (nextEntry.hashEntry == n) {
                return nextEntry;
            }
        }
        return null;
    }
    
    private static int computeHash(int n) {
        n = (n ^ n >>> 20 ^ n >>> 12);
        return n ^ n >>> 7 ^ n >>> 4;
    }
    
    private void copyTo(final Entry[] array) {
        final Entry[] slots = this.slots;
        final int length = array.length;
        while (0 < slots.length) {
            Entry entry = slots[0];
            if (entry != null) {
                slots[0] = null;
                Entry nextEntry;
                do {
                    nextEntry = entry.nextEntry;
                    final int slotIndex = getSlotIndex(entry.slotHash, length);
                    entry.nextEntry = array[slotIndex];
                    array[slotIndex] = entry;
                    entry = nextEntry;
                } while (nextEntry != null);
            }
            int n = 0;
            ++n;
        }
    }
    
    private void insert(final int n, final int n2, final Object o, final int n3) {
        this.slots[n3] = new Entry(n, n2, o, this.slots[n3]);
        if (this.count++ >= this.threshold) {
            this.grow(2 * this.slots.length);
        }
    }
    
    static int access$000(final int n) {
        return computeHash(n);
    }
    
    static class Entry
    {
        final int hashEntry;
        Entry nextEntry;
        Object valueEntry;
        final int slotHash;
        
        Entry(final int slotHash, final int hashEntry, final Object valueEntry, final Entry nextEntry) {
            this.valueEntry = valueEntry;
            this.nextEntry = nextEntry;
            this.hashEntry = hashEntry;
            this.slotHash = slotHash;
        }
        
        @Override
        public final int hashCode() {
            return IntHashMap.access$000(this.hashEntry);
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry entry = (Entry)o;
            final Integer value = this.getHash();
            final Integer value2 = entry.getHash();
            if (value == value2 || (value != null && value.equals(value2))) {
                final Object value3 = this.getValue();
                final Object value4 = entry.getValue();
                if (value3 == value4 || (value3 != null && value3.equals(value4))) {
                    return true;
                }
            }
            return false;
        }
        
        public final int getHash() {
            return this.hashEntry;
        }
        
        public final Object getValue() {
            return this.valueEntry;
        }
        
        @Override
        public final String toString() {
            return this.getHash() + "=" + this.getValue();
        }
    }
}
