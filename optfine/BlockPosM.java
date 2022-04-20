package optfine;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class BlockPosM extends BlockPos
{
    private int level;
    private int mx;
    private boolean needsUpdate;
    private int my;
    private int mz;
    private BlockPosM[] facings;
    
    private void update() {
        while (true) {
            final BlockPosM blockPosM = this.facings[0];
            if (blockPosM != null) {
                final EnumFacing enumFacing = EnumFacing.VALUES[0];
                blockPosM.setXyz(this.mx + enumFacing.getFrontOffsetX(), this.my + enumFacing.getFrontOffsetY(), this.mz + enumFacing.getFrontOffsetZ());
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int getX() {
        return this.mx;
    }
    
    public void setXyz(final double n, final double n2, final double n3) {
        this.setXyz(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
    }
    
    @Override
    public BlockPos offset(final EnumFacing enumFacing) {
        if (this.level <= 0) {
            return super.offset(enumFacing, 1);
        }
        if (this.facings == null) {
            this.facings = new BlockPosM[EnumFacing.VALUES.length];
        }
        if (this.needsUpdate) {
            this.update();
        }
        final int index = enumFacing.getIndex();
        BlockPosM blockPosM = this.facings[index];
        if (blockPosM == null) {
            blockPosM = new BlockPosM(this.mx + enumFacing.getFrontOffsetX(), this.my + enumFacing.getFrontOffsetY(), this.mz + enumFacing.getFrontOffsetZ(), this.level - 1);
            this.facings[index] = blockPosM;
        }
        return blockPosM;
    }
    
    @Override
    public int getZ() {
        return this.mz;
    }
    
    public BlockPosM(final int mx, final int my, final int mz, final int level) {
        super(0, 0, 0);
        this.mx = mx;
        this.my = my;
        this.mz = mz;
        this.level = level;
    }
    
    @Override
    public BlockPos offset(final EnumFacing enumFacing, final int n) {
        return (n == 1) ? this.offset(enumFacing) : super.offset(enumFacing, n);
    }
    
    public BlockPosM(final int n, final int n2, final int n3) {
        this(n, n2, n3, 0);
    }
    
    public BlockPosM(final double n, final double n2, final double n3) {
        this(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3));
    }
    
    public void setXyz(final int mx, final int my, final int mz) {
        this.mx = mx;
        this.my = my;
        this.mz = mz;
        this.needsUpdate = true;
    }
    
    public static Iterable getAllInBoxMutable(final BlockPos blockPos, final BlockPos blockPos2) {
        return new Iterable(new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ())), new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()))) {
            final BlockPos val$blockpos;
            final BlockPos val$blockpos1;
            
            @Override
            public Iterator iterator() {
                return (Iterator)new AbstractIterator(this) {
                    final BlockPosM$1 this$0;
                    private BlockPosM theBlockPosM = null;
                    
                    protected Object computeNext() {
                        return this.computeNext0();
                    }
                    
                    protected BlockPosM computeNext0() {
                        if (this.theBlockPosM == null) {
                            return this.theBlockPosM = new BlockPosM(this.this$0.val$blockpos.getX(), this.this$0.val$blockpos.getY(), this.this$0.val$blockpos.getZ(), 3);
                        }
                        if (this.theBlockPosM.equals(this.this$0.val$blockpos1)) {
                            return (BlockPosM)this.endOfData();
                        }
                        int n = this.theBlockPosM.getX();
                        int n2 = this.theBlockPosM.getY();
                        int z = this.theBlockPosM.getZ();
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
                        this.theBlockPosM.setXyz(n, n2, z);
                        return this.theBlockPosM;
                    }
                };
            }
        };
    }
    
    @Override
    public int getY() {
        return this.my;
    }
}
