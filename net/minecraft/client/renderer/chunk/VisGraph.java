package net.minecraft.client.renderer.chunk;

import net.minecraft.util.*;
import java.util.*;
import optfine.*;
import com.nquantum.*;

public class VisGraph
{
    private int field_178611_f;
    private static final String __OBFID = "CL_00002450";
    private static final int field_178615_c;
    private static final int field_178616_a;
    private static final int field_178614_b;
    private final BitSet field_178612_d;
    
    static {
        field_178616_a = (int)Math.pow(16.0, 0.0);
        field_178614_b = (int)Math.pow(16.0, 1.0);
        field_178615_c = (int)Math.pow(16.0, 2.0);
        VisGraph.field_178613_e = new int[1352];
        while (true) {
            final int[] field_178613_e = VisGraph.field_178613_e;
            final int n = 0;
            int n2 = 0;
            ++n2;
            field_178613_e[n] = getIndex(0, 0, 0);
            int n3 = 0;
            ++n3;
        }
    }
    
    public VisGraph() {
        this.field_178612_d = new BitSet(4096);
        this.field_178611_f = 4096;
    }
    
    public Set func_178609_b(final BlockPos blockPos) {
        return this.func_178604_a(getIndex(blockPos));
    }
    
    public SetVisibility computeVisibility() {
        final SetVisibility setVisibility = new SetVisibility();
        if (4096 - this.field_178611_f < 256) {
            setVisibility.setAllVisible(true);
        }
        else if (this.field_178611_f == 0) {
            setVisibility.setAllVisible(false);
        }
        else {
            final int[] field_178613_e = VisGraph.field_178613_e;
            while (0 < field_178613_e.length) {
                final int n = field_178613_e[0];
                if (!this.field_178612_d.get(n)) {
                    setVisibility.setManyVisible(this.func_178604_a(n));
                }
                int n2 = 0;
                ++n2;
            }
        }
        return setVisibility;
    }
    
    private static int getIndex(final BlockPos blockPos) {
        return getIndex(blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF);
    }
    
    private Set func_178604_a(final int n) {
        final EnumSet<EnumFacing> none = EnumSet.noneOf(EnumFacing.class);
        final ArrayDeque<Integer> arrayDeque = new ArrayDeque<Integer>(384);
        arrayDeque.add(IntegerCache.valueOf(n));
        this.field_178612_d.set(n, true);
        while (!arrayDeque.isEmpty()) {
            final int intValue = arrayDeque.poll();
            this.func_178610_a(intValue, none);
            final EnumFacing[] values = EnumFacing.VALUES;
            while (0 < values.length) {
                final int func_178603_a = this.func_178603_a(intValue, values[0]);
                if (func_178603_a >= 0 && !this.field_178612_d.get(func_178603_a)) {
                    this.field_178612_d.set(func_178603_a, true);
                    arrayDeque.add(IntegerCache.valueOf(func_178603_a));
                }
                int n2 = 0;
                ++n2;
            }
        }
        return none;
    }
    
    private int func_178603_a(final int n, final EnumFacing enumFacing) {
        switch (VisGraph$1.field_178617_a[enumFacing.ordinal()]) {
            case 1: {
                if ((n >> 8 & 0xF) == 0x0) {
                    return -1;
                }
                return n - VisGraph.field_178615_c;
            }
            case 2: {
                if ((n >> 8 & 0xF) == 0xF) {
                    return -1;
                }
                return n + VisGraph.field_178615_c;
            }
            case 3: {
                if ((n >> 4 & 0xF) == 0x0) {
                    return -1;
                }
                return n - VisGraph.field_178614_b;
            }
            case 4: {
                if ((n >> 4 & 0xF) == 0xF) {
                    return -1;
                }
                return n + VisGraph.field_178614_b;
            }
            case 5: {
                if ((n >> 0 & 0xF) == 0x0) {
                    return -1;
                }
                return n - VisGraph.field_178616_a;
            }
            case 6: {
                if ((n >> 0 & 0xF) == 0xF) {
                    return -1;
                }
                return n + VisGraph.field_178616_a;
            }
            default: {
                return -1;
            }
        }
    }
    
    private static int getIndex(final int n, final int n2, final int n3) {
        return n << 0 | n2 << 8 | n3 << 4;
    }
    
    public void func_178606_a(final BlockPos blockPos) {
        if (Asyncware.instance.moduleManager.getModuleByName("Xray").isToggled()) {
            return;
        }
        this.field_178612_d.set(getIndex(blockPos), true);
        --this.field_178611_f;
    }
    
    private void func_178610_a(final int n, final Set set) {
        final int n2 = n >> 0 & 0xF;
        if (n2 == 0) {
            set.add(EnumFacing.WEST);
        }
        else if (n2 == 15) {
            set.add(EnumFacing.EAST);
        }
        final int n3 = n >> 8 & 0xF;
        if (n3 == 0) {
            set.add(EnumFacing.DOWN);
        }
        else if (n3 == 15) {
            set.add(EnumFacing.UP);
        }
        final int n4 = n >> 4 & 0xF;
        if (n4 == 0) {
            set.add(EnumFacing.NORTH);
        }
        else if (n4 == 15) {
            set.add(EnumFacing.SOUTH);
        }
    }
    
    static final class VisGraph$1
    {
        private static final String __OBFID = "CL_00002449";
        
        static {
            (VisGraph$1.field_178617_a = new int[EnumFacing.values().length])[EnumFacing.DOWN.ordinal()] = 1;
            VisGraph$1.field_178617_a[EnumFacing.UP.ordinal()] = 2;
            VisGraph$1.field_178617_a[EnumFacing.NORTH.ordinal()] = 3;
            VisGraph$1.field_178617_a[EnumFacing.SOUTH.ordinal()] = 4;
            VisGraph$1.field_178617_a[EnumFacing.WEST.ordinal()] = 5;
            VisGraph$1.field_178617_a[EnumFacing.EAST.ordinal()] = 6;
        }
    }
}
