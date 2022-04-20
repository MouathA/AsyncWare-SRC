package net.minecraft.world.gen;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;

public class ChunkProviderDebug implements IChunkProvider
{
    private static final List field_177464_a;
    private static final int field_181039_c;
    private static final int field_177462_b;
    private final World world;
    
    public static IBlockState func_177461_b(int n, int n2) {
        IBlockState blockState = null;
        if (n > 0 && n2 > 0 && n % 2 != 0 && n2 % 2 != 0) {
            n /= 2;
            n2 /= 2;
            if (n <= ChunkProviderDebug.field_177462_b && n2 <= ChunkProviderDebug.field_181039_c) {
                final int abs_int = MathHelper.abs_int(n * ChunkProviderDebug.field_177462_b + n2);
                if (abs_int < ChunkProviderDebug.field_177464_a.size()) {
                    blockState = (IBlockState)ChunkProviderDebug.field_177464_a.get(abs_int);
                }
            }
        }
        return blockState;
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return this.world.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return true;
    }
    
    @Override
    public void saveExtraData() {
    }
    
    static {
        field_177464_a = Lists.newArrayList();
        final Iterator iterator = Block.blockRegistry.iterator();
        while (iterator.hasNext()) {
            ChunkProviderDebug.field_177464_a.addAll((Collection)iterator.next().getBlockState().getValidStates());
        }
        field_177462_b = MathHelper.ceiling_float_int(MathHelper.sqrt_float((float)ChunkProviderDebug.field_177464_a.size()));
        field_181039_c = MathHelper.ceiling_float_int(ChunkProviderDebug.field_177464_a.size() / (float)ChunkProviderDebug.field_177462_b);
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return false;
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public String makeString() {
        return "DebugLevelSource";
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return true;
    }
    
    @Override
    public boolean canSave() {
        return true;
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
    }
    
    public ChunkProviderDebug(final World world) {
        this.world = world;
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        while (true) {
            final int n3 = n * 16 + 0;
            chunkPrimer.setBlockState(0, 60, 0, Blocks.barrier.getDefaultState());
            final IBlockState func_177461_b = func_177461_b(n3, 0);
            if (func_177461_b != null) {
                chunkPrimer.setBlockState(0, 70, 0, func_177461_b);
            }
            int n4 = 0;
            ++n4;
        }
    }
}
