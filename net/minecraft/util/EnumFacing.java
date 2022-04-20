package net.minecraft.util;

import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

public enum EnumFacing implements IStringSerializable
{
    private static final EnumFacing[] $VALUES$;
    private final String name;
    
    WEST("WEST", 4, "WEST", 4, 4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0));
    
    private static final EnumFacing[] $VALUES;
    private final AxisDirection axisDirection;
    
    NORTH("NORTH", 2, "NORTH", 2, 2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1));
    
    private static final EnumFacing[] HORIZONTALS;
    
    UP("UP", 1, "UP", 1, 1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0));
    
    private final int opposite;
    
    DOWN("DOWN", 0, "DOWN", 0, 0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)), 
    EAST("EAST", 5, "EAST", 5, 5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));
    
    private final Axis axis;
    public static final EnumFacing[] VALUES;
    private final Vec3i directionVec;
    private final int index;
    private static final Map NAME_LOOKUP;
    
    SOUTH("SOUTH", 3, "SOUTH", 3, 3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1));
    
    private final int horizontalIndex;
    private static final String __OBFID = "CL_00001201";
    
    public static EnumFacing getHorizontal(final int n) {
        return EnumFacing.HORIZONTALS[MathHelper.abs_int(n % EnumFacing.HORIZONTALS.length)];
    }
    
    public EnumFacing rotateY() {
        switch (EnumFacing$1.field_179513_b[this.ordinal()]) {
            case 1: {
                return EnumFacing.EAST;
            }
            case 2: {
                return EnumFacing.SOUTH;
            }
            case 3: {
                return EnumFacing.WEST;
            }
            case 4: {
                return EnumFacing.NORTH;
            }
            default: {
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
            }
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    static {
        $VALUES$ = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST };
        VALUES = new EnumFacing[6];
        HORIZONTALS = new EnumFacing[4];
        NAME_LOOKUP = Maps.newHashMap();
        $VALUES = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST };
        final EnumFacing[] values = values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            EnumFacing.VALUES[enumFacing.index] = enumFacing;
            if (enumFacing.getAxis().isHorizontal()) {
                EnumFacing.HORIZONTALS[enumFacing.horizontalIndex] = enumFacing;
            }
            EnumFacing.NAME_LOOKUP.put(enumFacing.getName2().toLowerCase(), enumFacing);
            int n = 0;
            ++n;
        }
    }
    
    public static EnumFacing random(final Random random) {
        return values()[random.nextInt(values().length)];
    }
    
    public int getFrontOffsetY() {
        return (this.axis == Axis.Y) ? this.axisDirection.getOffset() : 0;
    }
    
    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }
    
    public Axis getAxis() {
        return this.axis;
    }
    
    public static EnumFacing func_181076_a(final AxisDirection axisDirection, final Axis axis) {
        final EnumFacing[] values = values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            if (enumFacing.getAxisDirection() == axisDirection && enumFacing.getAxis() == axis) {
                return enumFacing;
            }
            int n = 0;
            ++n;
        }
        throw new IllegalArgumentException("No such direction: " + axisDirection + " " + axis);
    }
    
    public int getFrontOffsetZ() {
        return (this.axis == Axis.Z) ? this.axisDirection.getOffset() : 0;
    }
    
    private EnumFacing(final String s, final int n, final String s2, final int n2, final int index, final int opposite, final int horizontalIndex, final String name, final AxisDirection axisDirection, final Axis axis, final Vec3i directionVec) {
        this.index = index;
        this.horizontalIndex = horizontalIndex;
        this.opposite = opposite;
        this.name = name;
        this.axis = axis;
        this.axisDirection = axisDirection;
        this.directionVec = directionVec;
    }
    
    public static EnumFacing fromAngle(final double n) {
        return getHorizontal(MathHelper.floor_double(n / 90.0 + 0.5) & 0x3);
    }
    
    public AxisDirection getAxisDirection() {
        return this.axisDirection;
    }
    
    public EnumFacing rotateYCCW() {
        switch (EnumFacing$1.field_179513_b[this.ordinal()]) {
            case 1: {
                return EnumFacing.WEST;
            }
            case 2: {
                return EnumFacing.NORTH;
            }
            case 3: {
                return EnumFacing.EAST;
            }
            case 4: {
                return EnumFacing.SOUTH;
            }
            default: {
                throw new IllegalStateException("Unable to get CCW facing of " + this);
            }
        }
    }
    
    public int getFrontOffsetX() {
        return (this.axis == Axis.X) ? this.axisDirection.getOffset() : 0;
    }
    
    public String getName2() {
        return this.name;
    }
    
    public static EnumFacing getFacingFromVector(final float n, final float n2, final float n3) {
        EnumFacing north = EnumFacing.NORTH;
        float n4 = Float.MIN_VALUE;
        final EnumFacing[] values = values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            final float n5 = n * enumFacing.directionVec.getX() + n2 * enumFacing.directionVec.getY() + n3 * enumFacing.directionVec.getZ();
            if (n5 > n4) {
                n4 = n5;
                north = enumFacing;
            }
            int n6 = 0;
            ++n6;
        }
        return north;
    }
    
    public static EnumFacing getFront(final int n) {
        return EnumFacing.VALUES[MathHelper.abs_int(n % EnumFacing.VALUES.length)];
    }
    
    public EnumFacing rotateAround(final Axis axis) {
        switch (EnumFacing$1.field_179515_a[axis.ordinal()]) {
            case 1: {
                if (this != EnumFacing.WEST && this != EnumFacing.EAST) {
                    return this.rotateX();
                }
                return this;
            }
            case 2: {
                if (this != EnumFacing.UP && this != EnumFacing.DOWN) {
                    return this.rotateY();
                }
                return this;
            }
            case 3: {
                if (this != EnumFacing.NORTH && this != EnumFacing.SOUTH) {
                    return this.rotateZ();
                }
                return this;
            }
            default: {
                throw new IllegalStateException("Unable to get CW facing for axis " + axis);
            }
        }
    }
    
    public static EnumFacing byName(final String s) {
        return (s == null) ? null : EnumFacing.NAME_LOOKUP.get(s.toLowerCase());
    }
    
    private EnumFacing rotateZ() {
        switch (EnumFacing$1.field_179513_b[this.ordinal()]) {
            case 2: {
                return EnumFacing.DOWN;
            }
            default: {
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            }
            case 4: {
                return EnumFacing.UP;
            }
            case 5: {
                return EnumFacing.EAST;
            }
            case 6: {
                return EnumFacing.WEST;
            }
        }
    }
    
    public Vec3i getDirectionVec() {
        return this.directionVec;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    private EnumFacing rotateX() {
        switch (EnumFacing$1.field_179513_b[this.ordinal()]) {
            case 1: {
                return EnumFacing.DOWN;
            }
            default: {
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
            }
            case 3: {
                return EnumFacing.UP;
            }
            case 5: {
                return EnumFacing.NORTH;
            }
            case 6: {
                return EnumFacing.SOUTH;
            }
        }
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public EnumFacing getOpposite() {
        return getFront(this.opposite);
    }
    
    public enum Axis implements IStringSerializable, Predicate
    {
        private static final String __OBFID = "CL_00002321";
        
        X("X", 0, "X", 0, "x", Plane.HORIZONTAL), 
        Y("Y", 1, "Y", 1, "y", Plane.VERTICAL);
        
        private static final Axis[] $VALUES$;
        private final String name;
        private static final Map NAME_LOOKUP;
        private final Plane plane;
        
        Z("Z", 2, "Z", 2, "z", Plane.HORIZONTAL);
        
        private static final Axis[] $VALUES;
        
        public static Axis byName(final String s) {
            return (s == null) ? null : Axis.NAME_LOOKUP.get(s.toLowerCase());
        }
        
        public Plane getPlane() {
            return this.plane;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public boolean isHorizontal() {
            return this.plane == Plane.HORIZONTAL;
        }
        
        private Axis(final String s, final int n, final String s2, final int n2, final String name, final Plane plane) {
            this.name = name;
            this.plane = plane;
        }
        
        public boolean apply(final EnumFacing enumFacing) {
            return enumFacing != null && enumFacing.getAxis() == this;
        }
        
        static {
            $VALUES$ = new Axis[] { Axis.X, Axis.Y, Axis.Z };
            NAME_LOOKUP = Maps.newHashMap();
            $VALUES = new Axis[] { Axis.X, Axis.Y, Axis.Z };
            final Axis[] values = values();
            while (0 < values.length) {
                final Axis axis = values[0];
                Axis.NAME_LOOKUP.put(axis.getName2().toLowerCase(), axis);
                int n = 0;
                ++n;
            }
        }
        
        public boolean apply(final Object o) {
            return this.apply((EnumFacing)o);
        }
        
        public boolean isVertical() {
            return this.plane == Plane.VERTICAL;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public String getName2() {
            return this.name;
        }
    }
    
    public enum Plane implements Iterable, Predicate
    {
        private static final String __OBFID = "CL_00002319";
        
        VERTICAL("VERTICAL", 1, "VERTICAL", 1);
        
        private static final Plane[] $VALUES;
        
        HORIZONTAL("HORIZONTAL", 0, "HORIZONTAL", 0);
        
        private static final Plane[] $VALUES$;
        
        public boolean apply(final EnumFacing enumFacing) {
            return enumFacing != null && enumFacing.getAxis().getPlane() == this;
        }
        
        @Override
        public Iterator iterator() {
            return (Iterator)Iterators.forArray((Object[])this.facings());
        }
        
        public EnumFacing random(final Random random) {
            final EnumFacing[] facings = this.facings();
            return facings[random.nextInt(facings.length)];
        }
        
        public boolean apply(final Object o) {
            return this.apply((EnumFacing)o);
        }
        
        static {
            $VALUES$ = new Plane[] { Plane.HORIZONTAL, Plane.VERTICAL };
            $VALUES = new Plane[] { Plane.HORIZONTAL, Plane.VERTICAL };
        }
        
        private Plane(final String s, final int n, final String s2, final int n2) {
        }
        
        public EnumFacing[] facings() {
            switch (EnumFacing$1.field_179514_c[this.ordinal()]) {
                case 1: {
                    return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
                }
                case 2: {
                    return new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
                }
                default: {
                    throw new Error("Someone's been tampering with the universe!");
                }
            }
        }
    }
    
    static final class EnumFacing$1
    {
        static final int[] field_179515_a;
        static final int[] field_179513_b;
        private static final String __OBFID = "CL_00002322";
        
        static {
            (EnumFacing$1.field_179514_c = new int[Plane.values().length])[Plane.HORIZONTAL.ordinal()] = 1;
            EnumFacing$1.field_179514_c[Plane.VERTICAL.ordinal()] = 2;
            (field_179513_b = new int[EnumFacing.values().length])[EnumFacing.NORTH.ordinal()] = 1;
            EnumFacing$1.field_179513_b[EnumFacing.EAST.ordinal()] = 2;
            EnumFacing$1.field_179513_b[EnumFacing.SOUTH.ordinal()] = 3;
            EnumFacing$1.field_179513_b[EnumFacing.WEST.ordinal()] = 4;
            EnumFacing$1.field_179513_b[EnumFacing.UP.ordinal()] = 5;
            EnumFacing$1.field_179513_b[EnumFacing.DOWN.ordinal()] = 6;
            (field_179515_a = new int[Axis.values().length])[Axis.X.ordinal()] = 1;
            EnumFacing$1.field_179515_a[Axis.Y.ordinal()] = 2;
            EnumFacing$1.field_179515_a[Axis.Z.ordinal()] = 3;
        }
    }
    
    public enum AxisDirection
    {
        private static final AxisDirection[] $VALUES$;
        private static final AxisDirection[] $VALUES;
        private final int offset;
        private static final String __OBFID = "CL_00002320";
        
        NEGATIVE("NEGATIVE", 1, "NEGATIVE", 1, -1, "Towards negative");
        
        private final String description;
        
        POSITIVE("POSITIVE", 0, "POSITIVE", 0, 1, "Towards positive");
        
        public int getOffset() {
            return this.offset;
        }
        
        private AxisDirection(final String s, final int n, final String s2, final int n2, final int offset, final String description) {
            this.offset = offset;
            this.description = description;
        }
        
        @Override
        public String toString() {
            return this.description;
        }
        
        static {
            $VALUES$ = new AxisDirection[] { AxisDirection.POSITIVE, AxisDirection.NEGATIVE };
            $VALUES = new AxisDirection[] { AxisDirection.POSITIVE, AxisDirection.NEGATIVE };
        }
    }
}
