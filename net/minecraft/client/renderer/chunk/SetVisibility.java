package net.minecraft.client.renderer.chunk;

import net.minecraft.util.*;
import java.util.*;

public class SetVisibility
{
    private final BitSet bitSet;
    private static final int COUNT_FACES;
    
    public SetVisibility() {
        this.bitSet = new BitSet(SetVisibility.COUNT_FACES * SetVisibility.COUNT_FACES);
    }
    
    public void setVisible(final EnumFacing enumFacing, final EnumFacing enumFacing2, final boolean b) {
        this.bitSet.set(enumFacing.ordinal() + enumFacing2.ordinal() * SetVisibility.COUNT_FACES, b);
        this.bitSet.set(enumFacing2.ordinal() + enumFacing.ordinal() * SetVisibility.COUNT_FACES, b);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(' ');
        final EnumFacing[] values = EnumFacing.values();
        int n = 0;
        while (0 < values.length) {
            sb.append(' ').append(values[0].toString().toUpperCase().charAt(0));
            ++n;
        }
        sb.append('\n');
        final EnumFacing[] values2 = EnumFacing.values();
        while (0 < values2.length) {
            final EnumFacing enumFacing = values2[0];
            sb.append(enumFacing.toString().toUpperCase().charAt(0));
            final EnumFacing[] values3 = EnumFacing.values();
            while (0 < values3.length) {
                final EnumFacing enumFacing2 = values3[0];
                if (enumFacing == enumFacing2) {
                    sb.append("  ");
                }
                else {
                    sb.append(' ').append((char)(this.isVisible(enumFacing, enumFacing2) ? 89 : 110));
                }
                int n2 = 0;
                ++n2;
            }
            sb.append('\n');
            ++n;
        }
        return sb.toString();
    }
    
    public void setManyVisible(final Set set) {
        for (final EnumFacing enumFacing : set) {
            final Iterator<EnumFacing> iterator2 = set.iterator();
            while (iterator2.hasNext()) {
                this.setVisible(enumFacing, iterator2.next(), true);
            }
        }
    }
    
    public boolean isVisible(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
        return this.bitSet.get(enumFacing.ordinal() + enumFacing2.ordinal() * SetVisibility.COUNT_FACES);
    }
    
    public void setAllVisible(final boolean b) {
        this.bitSet.set(0, this.bitSet.size(), b);
    }
    
    static {
        COUNT_FACES = EnumFacing.values().length;
    }
}
