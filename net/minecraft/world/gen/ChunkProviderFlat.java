package net.minecraft.world.gen;

import net.minecraft.block.state.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.gen.feature.*;

public class ChunkProviderFlat implements IChunkProvider
{
    private WorldGenLakes lavaLakeGenerator;
    private final IBlockState[] cachedBlockIDs;
    private final boolean hasDecoration;
    private World worldObj;
    private final boolean hasDungeons;
    private WorldGenLakes waterLakeGenerator;
    private final List structureGenerators;
    private Random random;
    private final FlatGeneratorInfo flatWorldGenInfo;
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return true;
    }
    
    @Override
    public void saveExtraData() {
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        int n3 = 0;
        while (0 < this.cachedBlockIDs.length) {
            final IBlockState blockState = this.cachedBlockIDs[0];
            if (blockState != null) {
                while (true) {
                    chunkPrimer.setBlockState(0, 0, 0, blockState);
                    ++n3;
                }
            }
            else {
                int n4 = 0;
                ++n4;
            }
        }
        final Iterator<MapGenBase> iterator = this.structureGenerators.iterator();
        while (iterator.hasNext()) {
            iterator.next().generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        final Chunk chunk = new Chunk(this.worldObj, chunkPrimer, n, n2);
        final BiomeGenBase[] loadBlockGeneratorData = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, n * 16, n2 * 16, 16, 16);
        final byte[] biomeArray = chunk.getBiomeArray();
        while (0 < biomeArray.length) {
            biomeArray[0] = (byte)loadBlockGeneratorData[0].biomeID;
            ++n3;
        }
        chunk.generateSkylightMap();
        return chunk;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return this.worldObj.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return true;
    }
    
    @Override
    public String makeString() {
        return "FlatLevelSource";
    }
    
    public ChunkProviderFlat(final World worldObj, final long n, final boolean b, final String s) {
        this.cachedBlockIDs = new IBlockState[256];
        this.structureGenerators = Lists.newArrayList();
        this.worldObj = worldObj;
        this.random = new Random(n);
        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(s);
        if (b) {
            final Map worldFeatures = this.flatWorldGenInfo.getWorldFeatures();
            if (worldFeatures.containsKey("village")) {
                final Map<String, String> map = worldFeatures.get("village");
                if (!map.containsKey("size")) {
                    map.put("size", "1");
                }
                this.structureGenerators.add(new MapGenVillage(map));
            }
            if (worldFeatures.containsKey("biome_1")) {
                this.structureGenerators.add(new MapGenScatteredFeature(worldFeatures.get("biome_1")));
            }
            if (worldFeatures.containsKey("mineshaft")) {
                this.structureGenerators.add(new MapGenMineshaft(worldFeatures.get("mineshaft")));
            }
            if (worldFeatures.containsKey("stronghold")) {
                this.structureGenerators.add(new MapGenStronghold(worldFeatures.get("stronghold")));
            }
            if (worldFeatures.containsKey("oceanmonument")) {
                this.structureGenerators.add(new StructureOceanMonument(worldFeatures.get("oceanmonument")));
            }
        }
        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake")) {
            this.waterLakeGenerator = new WorldGenLakes(Blocks.water);
        }
        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake")) {
            this.lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
        }
        this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
        for (final FlatLayerInfo flatLayerInfo : this.flatWorldGenInfo.getFlatLayers()) {
            for (int i = flatLayerInfo.getMinY(); i < flatLayerInfo.getMinY() + flatLayerInfo.getLayerCount(); ++i) {
                final IBlockState func_175900_c = flatLayerInfo.func_175900_c();
                if (func_175900_c.getBlock() != Blocks.air) {
                    this.cachedBlockIDs[i] = func_175900_c;
                }
            }
            if (flatLayerInfo.func_175900_c().getBlock() == Blocks.air) {
                final int n2 = 0 + flatLayerInfo.getLayerCount();
            }
            else {
                final int n3 = 0 + (flatLayerInfo.getLayerCount() + 0);
            }
        }
        worldObj.func_181544_b(0);
        this.hasDecoration = this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
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
        final int n3 = n * 16;
        final int n4 = n2 * 16;
        final BlockPos blockPos = new BlockPos(n3, 0, n4);
        final BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(new BlockPos(n3 + 16, 0, n4 + 16));
        this.random.setSeed(this.worldObj.getSeed());
        this.random.setSeed(n * (this.random.nextLong() / 2L * 2L + 1L) + n2 * (this.random.nextLong() / 2L * 2L + 1L) ^ this.worldObj.getSeed());
        final ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(n, n2);
        for (final MapGenStructure mapGenStructure : this.structureGenerators) {
            mapGenStructure.generateStructure(this.worldObj, this.random, chunkCoordIntPair);
            if (mapGenStructure instanceof MapGenVillage) {
                continue;
            }
        }
        if (this.waterLakeGenerator != null && this.random.nextInt(4) == 0) {
            this.waterLakeGenerator.generate(this.worldObj, this.random, blockPos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
        }
        if (this.lavaLakeGenerator != null && this.random.nextInt(8) == 0) {
            final BlockPos add = blockPos.add(this.random.nextInt(16) + 8, this.random.nextInt(this.random.nextInt(248) + 8), this.random.nextInt(16) + 8);
            if (add.getY() < this.worldObj.func_181545_F() || this.random.nextInt(10) == 0) {
                this.lavaLakeGenerator.generate(this.worldObj, this.random, add);
            }
        }
        if (!this.hasDungeons) {
            if (this.hasDecoration) {
                biomeGenForCoords.decorate(this.worldObj, this.random, blockPos);
            }
            return;
        }
        while (true) {
            new WorldGenDungeons().generate(this.worldObj, this.random, blockPos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
            int n5 = 0;
            ++n5;
        }
    }
    
    @Override
    public boolean canSave() {
        return true;
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
        final Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();
        while (iterator.hasNext()) {
            iterator.next().generate(this, this.worldObj, n, n2, null);
        }
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        if ("Stronghold".equals(s)) {
            for (final MapGenStructure mapGenStructure : this.structureGenerators) {
                if (mapGenStructure instanceof MapGenStronghold) {
                    return mapGenStructure.getClosestStrongholdPos(world, blockPos);
                }
            }
        }
        return null;
    }
}
