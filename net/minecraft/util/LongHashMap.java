package net.minecraft.util;

public class LongHashMap
{
    private transient volatile int modCount;
    private static final String __OBFID = "CL_00001492";
    private int capacity;
    private transient Entry[] hashArray;
    private transient int numHashElements;
    private final float percentUseable = 0.75f;
    private int mask;
    
    public boolean containsItem(final long n) {
        return this.getEntry(n) != null;
    }
    
    private static int getHashedKey(final long n) {
        return (int)(n ^ n >>> 27);
    }
    
    private void copyHashTableTo(final Entry[] array) {
        final Entry[] hashArray = this.hashArray;
        final int length = array.length;
        while (0 < hashArray.length) {
            Entry entry = hashArray[0];
            if (entry != null) {
                hashArray[0] = null;
                Entry nextEntry;
                do {
                    nextEntry = entry.nextEntry;
                    final int hashIndex = getHashIndex(entry.hash, length - 1);
                    entry.nextEntry = array[hashIndex];
                    array[hashIndex] = entry;
                    entry = nextEntry;
                } while (nextEntry != null);
            }
            int n = 0;
            ++n;
        }
    }
    
    public Object getValueByKey(final long n) {
        for (Entry nextEntry = this.hashArray[getHashIndex(getHashedKey(n), this.mask)]; nextEntry != null; nextEntry = nextEntry.nextEntry) {
            if (nextEntry.key == n) {
                return nextEntry.value;
            }
        }
        return null;
    }
    
    public void add(final long n, final Object value) {
        final int hashedKey = getHashedKey(n);
        final int hashIndex = getHashIndex(hashedKey, this.mask);
        for (Entry nextEntry = this.hashArray[hashIndex]; nextEntry != null; nextEntry = nextEntry.nextEntry) {
            if (nextEntry.key == n) {
                nextEntry.value = value;
                return;
            }
        }
        ++this.modCount;
        this.createKey(hashedKey, n, value, hashIndex);
    }
    
    static int access$000(final long n) {
        return getHashedKey(n);
    }
    
    private static int getHashIndex(final int n, final int n2) {
        return n & n2;
    }
    
    private static int hash(int n) {
        n = (n ^ n >>> 20 ^ n >>> 12);
        return n ^ n >>> 7 ^ n >>> 4;
    }
    
    final Entry getEntry(final long n) {
        for (Entry nextEntry = this.hashArray[getHashIndex(getHashedKey(n), this.mask)]; nextEntry != null; nextEntry = nextEntry.nextEntry) {
            if (nextEntry.key == n) {
                return nextEntry;
            }
        }
        return null;
    }
    
    public double getKeyDistribution() {
        while (0 < this.hashArray.length) {
            if (this.hashArray[0] != null) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 1.0 * 0 / this.numHashElements;
    }
    
    final Entry removeKey(final long n) {
        final int hashIndex = getHashIndex(getHashedKey(n), this.mask);
        Entry entry2;
        Entry nextEntry;
        for (Entry entry = entry2 = this.hashArray[hashIndex]; entry2 != null; entry2 = nextEntry) {
            nextEntry = entry2.nextEntry;
            if (entry2.key == n) {
                ++this.modCount;
                --this.numHashElements;
                if (entry == entry2) {
                    this.hashArray[hashIndex] = nextEntry;
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
    
    public Object remove(final long n) {
        final Entry removeKey = this.removeKey(n);
        return (removeKey == null) ? null : removeKey.value;
    }
    
    private void createKey(final int n, final long n2, final Object o, final int n3) {
        this.hashArray[n3] = new Entry(n, n2, o, this.hashArray[n3]);
        if (this.numHashElements++ >= this.capacity) {
            this.resizeTable(2 * this.hashArray.length);
        }
    }
    
    public LongHashMap() {
        this.hashArray = new Entry[4096];
        this.capacity = 3072;
        this.mask = this.hashArray.length - 1;
    }
    
    public int getNumHashElements() {
        return this.numHashElements;
    }
    
    private void resizeTable(final int n) {
        if (this.hashArray.length == 1073741824) {
            this.capacity = Integer.MAX_VALUE;
        }
        else {
            final Entry[] hashArray = new Entry[n];
            this.copyHashTableTo(hashArray);
            this.hashArray = hashArray;
            this.mask = this.hashArray.length - 1;
            final float n2 = (float)n;
            this.getClass();
            this.capacity = (int)(n2 * 0.75f);
        }
    }
    
    static class Entry
    {
        private static final String __OBFID = "CL_00001493";
        final long key;
        Object value;
        final int hash;
        Entry nextEntry;
        
        @Override
        public final int hashCode() {
            return LongHashMap.access$000(this.key);
        }
        
        public final Object getValue() {
            return this.value;
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            final Entry entry = (Entry)o;
            final Long value = this.getKey();
            final Long value2 = entry.getKey();
            if (value == value2 || (value != null && value.equals(value2))) {
                final Object value3 = this.getValue();
                final Object value4 = entry.getValue();
                if (value3 == value4 || (value3 != null && value3.equals(value4))) {
                    return true;
                }
            }
            return false;
        }
        
        Entry(final int hash, final long key, final Object value, final Entry nextEntry) {
            this.value = value;
            this.nextEntry = nextEntry;
            this.key = key;
            this.hash = hash;
        }
        
        @Override
        public final String toString() {
            return this.getKey() + "=" + this.getValue();
        }
        
        public final long getKey() {
            return this.key;
        }
    }
}
