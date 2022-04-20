package net.minecraft.block.state.pattern;

import net.minecraft.world.*;
import com.google.common.cache.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;

public class BlockPattern
{
    private final int thumbLength;
    private final int fingerLength;
    private final int palmLength;
    private final Predicate[][][] blockMatches;
    
    public int getThumbLength() {
        return this.thumbLength;
    }
    
    public int getPalmLength() {
        return this.palmLength;
    }
    
    public static LoadingCache func_181627_a(final World world, final boolean b) {
        return CacheBuilder.newBuilder().build((com.google.common.cache.CacheLoader)new CacheLoader(world, b));
    }
    
    private PatternHelper checkPatternAt(final BlockPos blockPos, final EnumFacing enumFacing, final EnumFacing enumFacing2, final LoadingCache loadingCache) {
        while (0 < this.palmLength) {
            while (0 < this.thumbLength) {
                while (0 < this.fingerLength) {
                    if (!this.blockMatches[0][0][0].apply(loadingCache.getUnchecked((Object)translateOffset(blockPos, enumFacing, enumFacing2, 0, 0, 0)))) {
                        return null;
                    }
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return new PatternHelper(blockPos, enumFacing, enumFacing2, loadingCache, this.palmLength, this.thumbLength, this.fingerLength);
    }
    
    public PatternHelper match(final World world, final BlockPos blockPos) {
        final LoadingCache func_181627_a = func_181627_a(world, false);
        final int max = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);
        for (final BlockPos blockPos2 : BlockPos.getAllInBox(blockPos, blockPos.add(max - 1, max - 1, max - 1))) {
            final EnumFacing[] values = EnumFacing.values();
            while (0 < values.length) {
                final EnumFacing enumFacing = values[0];
                final EnumFacing[] values2 = EnumFacing.values();
                while (0 < values2.length) {
                    final EnumFacing enumFacing2 = values2[0];
                    if (enumFacing2 != enumFacing && enumFacing2 != enumFacing.getOpposite()) {
                        final PatternHelper checkPattern = this.checkPatternAt(blockPos2, enumFacing, enumFacing2, func_181627_a);
                        if (checkPattern != null) {
                            return checkPattern;
                        }
                    }
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
        }
        return null;
    }
    
    protected static BlockPos translateOffset(final BlockPos blockPos, final EnumFacing enumFacing, final EnumFacing enumFacing2, final int n, final int n2, final int n3) {
        if (enumFacing != enumFacing2 && enumFacing != enumFacing2.getOpposite()) {
            final Vec3i vec3i = new Vec3i(enumFacing.getFrontOffsetX(), enumFacing.getFrontOffsetY(), enumFacing.getFrontOffsetZ());
            final Vec3i vec3i2 = new Vec3i(enumFacing2.getFrontOffsetX(), enumFacing2.getFrontOffsetY(), enumFacing2.getFrontOffsetZ());
            final Vec3i crossProduct = vec3i.crossProduct(vec3i2);
            return blockPos.add(vec3i2.getX() * -n2 + crossProduct.getX() * n + vec3i.getX() * n3, vec3i2.getY() * -n2 + crossProduct.getY() * n + vec3i.getY() * n3, vec3i2.getZ() * -n2 + crossProduct.getZ() * n + vec3i.getZ() * n3);
        }
        throw new IllegalArgumentException("Invalid forwards & up combination");
    }
    
    public BlockPattern(final Predicate[][][] blockMatches) {
        this.blockMatches = blockMatches;
        this.fingerLength = blockMatches.length;
        if (this.fingerLength > 0) {
            this.thumbLength = blockMatches[0].length;
            if (this.thumbLength > 0) {
                this.palmLength = blockMatches[0][0].length;
            }
            else {
                this.palmLength = 0;
            }
        }
        else {
            this.thumbLength = 0;
            this.palmLength = 0;
        }
    }
    
    static class CacheLoader extends com.google.common.cache.CacheLoader
    {
        private final World world;
        private final boolean field_181626_b;
        
        public BlockWorldState load(final BlockPos blockPos) throws Exception {
            return new BlockWorldState(this.world, blockPos, this.field_181626_b);
        }
        
        public CacheLoader(final World world, final boolean field_181626_b) {
            this.world = world;
            this.field_181626_b = field_181626_b;
        }
        
        public Object load(final Object o) throws Exception {
            return this.load((BlockPos)o);
        }
    }
    
    public static class PatternHelper
    {
        private final int field_181121_f;
        private final EnumFacing thumb;
        private final EnumFacing finger;
        private final LoadingCache lcache;
        private final int field_181122_g;
        private final BlockPos pos;
        private final int field_181120_e;
        
        public BlockWorldState translateOffset(final int n, final int n2, final int n3) {
            return (BlockWorldState)this.lcache.getUnchecked((Object)BlockPattern.translateOffset(this.pos, this.getFinger(), this.getThumb(), n, n2, n3));
        }
        
        public EnumFacing getFinger() {
            return this.finger;
        }
        
        public BlockPos func_181117_a() {
            return this.pos;
        }
        
        public int func_181119_e() {
            return this.field_181121_f;
        }
        
        public PatternHelper(final BlockPos pos, final EnumFacing finger, final EnumFacing thumb, final LoadingCache lcache, final int field_181120_e, final int field_181121_f, final int field_181122_g) {
            this.pos = pos;
            this.finger = finger;
            this.thumb = thumb;
            this.lcache = lcache;
            this.field_181120_e = field_181120_e;
            this.field_181121_f = field_181121_f;
            this.field_181122_g = field_181122_g;
        }
        
        public EnumFacing getThumb() {
            return this.thumb;
        }
        
        @Override
        public String toString() {
            return Objects.toStringHelper((Object)this).add("up", (Object)this.thumb).add("forwards", (Object)this.finger).add("frontTopLeft", (Object)this.pos).toString();
        }
        
        public int func_181118_d() {
            return this.field_181120_e;
        }
    }
}
