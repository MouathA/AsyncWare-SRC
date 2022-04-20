package net.minecraft.block.state.pattern;

import java.util.*;
import java.lang.reflect.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public class FactoryBlockPattern
{
    private int aisleHeight;
    private static final Joiner COMMA_JOIN;
    private final Map symbolMap;
    private final List depth;
    private int rowWidth;
    
    private void checkMissingPredicates() {
        final ArrayList arrayList = Lists.newArrayList();
        for (final Map.Entry<Object, V> entry : this.symbolMap.entrySet()) {
            if (entry.getValue() == null) {
                arrayList.add(entry.getKey());
            }
        }
        if (!arrayList.isEmpty()) {
            throw new IllegalStateException("Predicates for character(s) " + FactoryBlockPattern.COMMA_JOIN.join((Iterable)arrayList) + " are missing");
        }
    }
    
    static {
        COMMA_JOIN = Joiner.on(",");
    }
    
    public BlockPattern build() {
        return new BlockPattern(this.makePredicateArray());
    }
    
    public static FactoryBlockPattern start() {
        return new FactoryBlockPattern();
    }
    
    private Predicate[][][] makePredicateArray() {
        this.checkMissingPredicates();
        final Predicate[][][] array = (Predicate[][][])Array.newInstance(Predicate.class, this.depth.size(), this.aisleHeight, this.rowWidth);
        while (0 < this.depth.size()) {
            while (0 < this.aisleHeight) {
                while (0 < this.rowWidth) {
                    array[0][0][0] = this.symbolMap.get(((String[])this.depth.get(0))[0].charAt(0));
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return array;
    }
    
    public FactoryBlockPattern aisle(final String... array) {
        if (ArrayUtils.isEmpty((Object[])array) || StringUtils.isEmpty((CharSequence)array[0])) {
            throw new IllegalArgumentException("Empty pattern for aisle");
        }
        if (this.depth.isEmpty()) {
            this.aisleHeight = array.length;
            this.rowWidth = array[0].length();
        }
        if (array.length != this.aisleHeight) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.aisleHeight + ", but was given one with a height of " + array.length + ")");
        }
        while (0 < array.length) {
            final String s = array[0];
            if (s.length() != this.rowWidth) {
                throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.rowWidth + ", found one with " + s.length() + ")");
            }
            final char[] charArray = s.toCharArray();
            while (0 < charArray.length) {
                final char c = charArray[0];
                if (!this.symbolMap.containsKey(c)) {
                    this.symbolMap.put(c, null);
                }
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        this.depth.add(array);
        return this;
    }
    
    private FactoryBlockPattern() {
        this.depth = Lists.newArrayList();
        (this.symbolMap = Maps.newHashMap()).put(' ', Predicates.alwaysTrue());
    }
    
    public FactoryBlockPattern where(final char c, final Predicate predicate) {
        this.symbolMap.put(c, predicate);
        return this;
    }
}
