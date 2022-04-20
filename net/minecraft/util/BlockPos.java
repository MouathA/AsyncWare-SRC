package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;

public class BlockPos extends Vec3i
{
    public static final BlockPos ORIGIN;
    private static final long Y_MASK;
    private static final int NUM_X_BITS;
    private static final int NUM_Z_BITS;
    private static final long Z_MASK;
    private static final long X_MASK;
    private static final int Y_SHIFT;
    private static final int X_SHIFT;
    private static final int NUM_Y_BITS;
    
    @Override
    public Vec3i crossProduct(final Vec3i vec3i) {
        return this.crossProduct(vec3i);
    }
    
    public static Iterable getAllInBox(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ())), new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            final BlockPos val$blockpos1;
            final BlockPos val$blockpos;
            
            @Override
            public Iterator iterator() {
                return (Iterator)new AbstractIterator(this) {
                    final BlockPos$1 this$0;
                    private BlockPos lastReturned = null;
                    
                    protected BlockPos computeNext() {
                        if (this.lastReturned == null) {
                            return this.lastReturned = this.this$0.val$blockpos;
                        }
                        if (this.lastReturned.equals(this.this$0.val$blockpos1)) {
                            return (BlockPos)this.endOfData();
                        }
                        int n = this.lastReturned.getX();
                        int n2 = this.lastReturned.getY();
                        int z = this.lastReturned.getZ();
                        if (n < this.this$0.val$blockpos1.getX()) {
                            ++n;
                        }
                        else if (n2 < this.this$0.val$blockpos1.getY()) {
                            n = this.this$0.val$blockpos.getX();
                            ++n2;
                        }
                        else if (z < this.this$0.val$blockpos1.getZ()) {
                            n = this.this$0.val$blockpos.getX();
                            n2 = this.this$0.val$blockpos.getY();
                            ++z;
                        }
                        return this.lastReturned = new BlockPos(n, n2, z);
                    }
                    
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
        };
    }
    
    public BlockPos east() {
        return this.east(1);
    }
    
    public BlockPos offset(final EnumFacing enumFacing) {
        return this.offset(enumFacing, 1);
    }
    
    public BlockPos south() {
        return this.south(1);
    }
    
    public BlockPos south(final int n) {
        return this.offset(EnumFacing.SOUTH, n);
    }
    
    public long toLong() {
        return ((long)this.getX() & BlockPos.X_MASK) << BlockPos.X_SHIFT | ((long)this.getY() & BlockPos.Y_MASK) << BlockPos.Y_SHIFT | ((long)this.getZ() & BlockPos.Z_MASK) << 0;
    }
    
    public BlockPos add(final Vec3i vec3i) {
        return (vec3i.getX() == 0 && vec3i.getY() == 0 && vec3i.getZ() == 0) ? this : new BlockPos(this.getX() + vec3i.getX(), this.getY() + vec3i.getY(), this.getZ() + vec3i.getZ());
    }
    
    static {
        ORIGIN = new BlockPos(0, 0, 0);
        NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
        NUM_Z_BITS = BlockPos.NUM_X_BITS;
        NUM_Y_BITS = 64 - BlockPos.NUM_X_BITS - BlockPos.NUM_Z_BITS;
        Y_SHIFT = 0 + BlockPos.NUM_Z_BITS;
        X_SHIFT = BlockPos.Y_SHIFT + BlockPos.NUM_Y_BITS;
        X_MASK = (1L << BlockPos.NUM_X_BITS) - 1L;
        Y_MASK = (1L << BlockPos.NUM_Y_BITS) - 1L;
        Z_MASK = (1L << BlockPos.NUM_Z_BITS) - 1L;
    }
    
    public BlockPos down() {
        return this.down(1);
    }
    
    public BlockPos up() {
        return this.up(1);
    }
    
    public BlockPos subtract(final Vec3i vec3i) {
        return (vec3i.getX() == 0 && vec3i.getY() == 0 && vec3i.getZ() == 0) ? this : new BlockPos(this.getX() - vec3i.getX(), this.getY() - vec3i.getY(), this.getZ() - vec3i.getZ());
    }
    
    public BlockPos west() {
        return this.west(1);
    }
    
    public static Iterable getAllInBoxMutable(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ())), new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            final BlockPos val$blockpos;
            final BlockPos val$blockpos1;
            
            @Override
            public Iterator iterator() {
                return (Iterator)new AbstractIterator(this) {
                    private MutableBlockPos theBlockPos = null;
                    final BlockPos$2 this$0;
                    
                    protected MutableBlockPos computeNext() {
                        if (this.theBlockPos == null) {
                            return this.theBlockPos = new MutableBlockPos(this.this$0.val$blockpos.getX(), this.this$0.val$blockpos.getY(), this.this$0.val$blockpos.getZ());
                        }
                        if (this.theBlockPos.equals(this.this$0.val$blockpos1)) {
                            return (MutableBlockPos)this.endOfData();
                        }
                        int n = this.theBlockPos.getX();
                        int n2 = this.theBlockPos.getY();
                        int z = this.theBlockPos.getZ();
                        if (n < this.this$0.val$blockpos1.getX()) {
                            ++n;
                        }
                        else if (n2 < this.this$0.val$blockpos1.getY()) {
                            n = this.this$0.val$blockpos.getX();
                            ++n2;
                        }
                        else if (z < this.this$0.val$blockpos1.getZ()) {
                            n = this.this$0.val$blockpos.getX();
                            n2 = this.this$0.val$blockpos.getY();
                            ++z;
                        }
                        MutableBlockPos.access$002(this.theBlockPos, n);
                        MutableBlockPos.access$102(this.theBlockPos, n2);
                        MutableBlockPos.access$202(this.theBlockPos, z);
                        return this.theBlockPos;
                    }
                    
                    protected Object computeNext() {
                        return this.computeNext();
                    }
                };
            }
        };
    }
    
    public BlockPos(final Vec3i vec3i) {
        this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }
    
    public Block getBlock() {
        return Minecraft.getMinecraft().theWorld.getBlockState(this).getBlock();
    }
    
    public BlockPos east(final int n) {
        return this.offset(EnumFacing.EAST, n);
    }
    
    public BlockPos(final Entity entity) {
        this(entity.posX, entity.posY, entity.posZ);
    }
    
    @Override
    public BlockPos crossProduct(final Vec3i vec3i) {
        return new BlockPos(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(), this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(), this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
    }
    
    public BlockPos west(final int n) {
        return this.offset(EnumFacing.WEST, n);
    }
    
    public BlockPos(final Vec3 vec3) {
        this(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }
    
    public static BlockPos fromLong(final long n) {
        return new BlockPos((int)(n << 64 - BlockPos.X_SHIFT - BlockPos.NUM_X_BITS >> 64 - BlockPos.NUM_X_BITS), (int)(n << 64 - BlockPos.Y_SHIFT - BlockPos.NUM_Y_BITS >> 64 - BlockPos.NUM_Y_BITS), (int)(n << 64 - BlockPos.NUM_Z_BITS >> 64 - BlockPos.NUM_Z_BITS));
    }
    
    public BlockPos up(final int n) {
        return this.offset(EnumFacing.UP, n);
    }
    
    public BlockPos add(final int n, final int n2, final int n3) {
        return (n == 0 && n2 == 0 && n3 == 0) ? this : new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
    }
    
    public BlockPos down(final int n) {
        return this.offset(EnumFacing.DOWN, n);
    }
    
    public BlockPos north(final int n) {
        return this.offset(EnumFacing.NORTH, n);
    }
    
    public BlockPos add(final double n, final double n2, final double n3) {
        return (n == 0.0 && n2 == 0.0 && n3 == 0.0) ? this : new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
    }
    
    public BlockPos north() {
        return this.north(1);
    }
    
    public BlockPos(final double n, final double n2, final double n3) {
        super(n, n2, n3);
    }
    
    public BlockPos(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    public BlockPos offset(final EnumFacing enumFacing, final int n) {
        return (n == 0) ? this : new BlockPos(this.getX() + enumFacing.getFrontOffsetX() * n, this.getY() + enumFacing.getFrontOffsetY() * n, this.getZ() + enumFacing.getFrontOffsetZ() * n);
    }
    
    public static final class MutableBlockPos extends BlockPos
    {
        private int z;
        private int x;
        private int y;
        
        public MutableBlockPos() {
            this(0, 0, 0);
        }
        
        @Override
        public int getZ() {
            return this.z;
        }
        
        static int access$202(final MutableBlockPos mutableBlockPos, final int z) {
            return mutableBlockPos.z = z;
        }
        
        @Override
        public Vec3i crossProduct(final Vec3i vec3i) {
            return super.crossProduct(vec3i);
        }
        
        @Override
        public int getY() {
            return this.y;
        }
        
        static int access$002(final MutableBlockPos mutableBlockPos, final int x) {
            return mutableBlockPos.x = x;
        }
        
        @Override
        public int getX() {
            return this.x;
        }
        
        static int access$102(final MutableBlockPos mutableBlockPos, final int y) {
            return mutableBlockPos.y = y;
        }
        
        public MutableBlockPos func_181079_c(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }
        
        public MutableBlockPos(final int x, final int y, final int z) {
            super(0, 0, 0);
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
