package net.minecraft.world.chunk;

import net.minecraft.world.chunk.storage.*;
import java.util.concurrent.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.world.biome.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class Chunk
{
    private final ExtendedBlockStorage[] storageArrays;
    private long lastSaveTime;
    private boolean isLightPopulated;
    private boolean isChunkLoaded;
    private boolean field_150815_m;
    public final int zPosition;
    private final boolean[] updateSkylightColumns;
    private final int[] heightMap;
    private final byte[] blockBiomeArray;
    private long inhabitedTime;
    private boolean isGapLightingUpdated;
    private final ClassInheritanceMultiMap[] entityLists;
    private boolean isModified;
    private boolean hasEntities;
    private ConcurrentLinkedQueue tileEntityPosQueue;
    private final Map chunkTileEntityMap;
    public final int xPosition;
    private final int[] precipitationHeightMap;
    private final World worldObj;
    private static final Logger logger;
    private boolean isTerrainPopulated;
    private int queuedLightChecks;
    private int heightMapMinimum;
    
    public World getWorld() {
        return this.worldObj;
    }
    
    public Block getBlock(final BlockPos blockPos) {
        return this.getBlock0(blockPos.getX() & 0xF, blockPos.getY(), blockPos.getZ() & 0xF);
    }
    
    public void setHeightMap(final int[] array) {
        if (this.heightMap.length != array.length) {
            Chunk.logger.warn("Could not set level chunk heightmap, array length is " + array.length + " instead of " + this.heightMap.length);
        }
        else {
            while (0 < this.heightMap.length) {
                this.heightMap[0] = array[0];
                int n = 0;
                ++n;
            }
        }
    }
    
    private int getBlockLightOpacity(final int n, final int n2, final int n3) {
        return this.getBlock0(n, n2, n3).getLightOpacity();
    }
    
    public boolean isLoaded() {
        return this.isChunkLoaded;
    }
    
    public void setTerrainPopulated(final boolean isTerrainPopulated) {
        this.isTerrainPopulated = isTerrainPopulated;
    }
    
    public int getTopFilledSegment() {
        for (int i = this.storageArrays.length - 1; i >= 0; --i) {
            if (this.storageArrays[i] != null) {
                return this.storageArrays[i].getYLocation();
            }
        }
        return 0;
    }
    
    public void removeEntityAtIndex(final Entity entity, int n) {
        if (0 >= this.entityLists.length) {
            n = this.entityLists.length - 1;
        }
        this.entityLists[0].remove(entity);
    }
    
    public boolean getAreLevelsEmpty(final int n, final int n2) {
        while (true) {
            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[0];
            if (extendedBlockStorage != null && !extendedBlockStorage.isEmpty()) {
                break;
            }
            final int n3;
            n3 += 16;
        }
        return false;
    }
    
    public boolean isLightPopulated() {
        return this.isLightPopulated;
    }
    
    public void setModified(final boolean isModified) {
        this.isModified = isModified;
    }
    
    public void setChunkLoaded(final boolean isChunkLoaded) {
        this.isChunkLoaded = isChunkLoaded;
    }
    
    private void recheckGaps(final boolean b) {
        this.worldObj.theProfiler.startSection("recheckGaps");
        if (this.worldObj.isAreaLoaded(new BlockPos(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8), 16)) {
            while (true) {
                if (this.updateSkylightColumns[0]) {
                    this.updateSkylightColumns[0] = false;
                    final int heightValue = this.getHeightValue(0, 0);
                    final int n = this.xPosition * 16 + 0;
                    final int n2 = this.zPosition * 16 + 0;
                    for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
                        Math.min(Integer.MAX_VALUE, this.worldObj.getChunksLowestHorizon(n + enumFacing.getFrontOffsetX(), n2 + enumFacing.getFrontOffsetZ()));
                    }
                    this.checkSkylightNeighborHeight(n, n2, Integer.MAX_VALUE);
                    for (final EnumFacing enumFacing2 : EnumFacing.Plane.HORIZONTAL) {
                        this.checkSkylightNeighborHeight(n + enumFacing2.getFrontOffsetX(), n2 + enumFacing2.getFrontOffsetZ(), heightValue);
                    }
                    if (b) {
                        break;
                    }
                }
                int n3 = 0;
                ++n3;
            }
            this.worldObj.theProfiler.endSection();
            return;
        }
        this.worldObj.theProfiler.endSection();
    }
    
    public void populateChunk(final IChunkProvider chunkProvider, final IChunkProvider chunkProvider2, final int n, final int n2) {
        final boolean chunkExists = chunkProvider.chunkExists(n, n2 - 1);
        final boolean chunkExists2 = chunkProvider.chunkExists(n + 1, n2);
        final boolean chunkExists3 = chunkProvider.chunkExists(n, n2 + 1);
        final boolean chunkExists4 = chunkProvider.chunkExists(n - 1, n2);
        final boolean chunkExists5 = chunkProvider.chunkExists(n - 1, n2 - 1);
        final boolean chunkExists6 = chunkProvider.chunkExists(n + 1, n2 + 1);
        final boolean chunkExists7 = chunkProvider.chunkExists(n - 1, n2 + 1);
        final boolean chunkExists8 = chunkProvider.chunkExists(n + 1, n2 - 1);
        if (chunkExists2 && chunkExists3 && chunkExists6) {
            if (!this.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n, n2);
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, this, n, n2);
            }
        }
        if (chunkExists4 && chunkExists3 && chunkExists7) {
            final Chunk provideChunk = chunkProvider.provideChunk(n - 1, n2);
            if (!provideChunk.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n - 1, n2);
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk, n - 1, n2);
            }
        }
        if (chunkExists && chunkExists2 && chunkExists8) {
            final Chunk provideChunk2 = chunkProvider.provideChunk(n, n2 - 1);
            if (!provideChunk2.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n, n2 - 1);
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk2, n, n2 - 1);
            }
        }
        if (chunkExists5 && chunkExists && chunkExists4) {
            final Chunk provideChunk3 = chunkProvider.provideChunk(n - 1, n2 - 1);
            if (!provideChunk3.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n - 1, n2 - 1);
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk3, n - 1, n2 - 1);
            }
        }
    }
    
    protected void generateHeightMap() {
        final int topFilledSegment = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        while (true) {
            this.precipitationHeightMap[0] = -999;
            int i = topFilledSegment + 16;
            while (i > 0) {
                if (this.getBlock0(0, i - 1, 0).getLightOpacity() != 0) {
                    if ((this.heightMap[0] = i) < this.heightMapMinimum) {
                        this.heightMapMinimum = i;
                        break;
                    }
                    break;
                }
                else {
                    --i;
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    public ChunkCoordIntPair getChunkCoordIntPair() {
        return new ChunkCoordIntPair(this.xPosition, this.zPosition);
    }
    
    public void addTileEntity(final BlockPos pos, final TileEntity tileEntity) {
        tileEntity.setWorldObj(this.worldObj);
        tileEntity.setPos(pos);
        if (this.getBlock(pos) instanceof ITileEntityProvider) {
            if (this.chunkTileEntityMap.containsKey(pos)) {
                this.chunkTileEntityMap.get(pos).invalidate();
            }
            tileEntity.validate();
            this.chunkTileEntityMap.put(pos, tileEntity);
        }
    }
    
    public void setStorageArrays(final ExtendedBlockStorage[] array) {
        if (this.storageArrays.length != array.length) {
            Chunk.logger.warn("Could not set level chunk sections, array length is " + array.length + " instead of " + this.storageArrays.length);
        }
        else {
            while (0 < this.storageArrays.length) {
                this.storageArrays[0] = array[0];
                int n = 0;
                ++n;
            }
        }
    }
    
    public void removeTileEntity(final BlockPos blockPos) {
        if (this.isChunkLoaded) {
            final TileEntity tileEntity = this.chunkTileEntityMap.remove(blockPos);
            if (tileEntity != null) {
                tileEntity.invalidate();
            }
        }
    }
    
    public Map getTileEntityMap() {
        return this.chunkTileEntityMap;
    }
    
    public boolean isTerrainPopulated() {
        return this.isTerrainPopulated;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public void resetRelightChecks() {
        this.queuedLightChecks = 0;
    }
    
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
        final int n2 = blockPos.getX() & 0xF;
        final int y = blockPos.getY();
        final int n3 = blockPos.getZ() & 0xF;
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> 4];
        if (extendedBlockStorage == null) {
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n4 = y >> 4;
            final ExtendedBlockStorage extendedBlockStorage2 = new ExtendedBlockStorage(y >> 4 << 4, !this.worldObj.provider.getHasNoSky());
            storageArrays[n4] = extendedBlockStorage2;
            extendedBlockStorage = extendedBlockStorage2;
            this.generateSkylightMap();
        }
        this.isModified = true;
        if (enumSkyBlock == EnumSkyBlock.SKY) {
            if (!this.worldObj.provider.getHasNoSky()) {
                extendedBlockStorage.setExtSkylightValue(n2, y & 0xF, n3, n);
            }
        }
        else if (enumSkyBlock == EnumSkyBlock.BLOCK) {
            extendedBlockStorage.setExtBlocklightValue(n2, y & 0xF, n3, n);
        }
    }
    
    public int getLowestHeight() {
        return this.heightMapMinimum;
    }
    
    public BiomeGenBase getBiome(final BlockPos blockPos, final WorldChunkManager worldChunkManager) {
        final int n = blockPos.getX() & 0xF;
        final int n2 = blockPos.getZ() & 0xF;
        int biomeID = this.blockBiomeArray[n2 << 4 | n] & 0xFF;
        if (biomeID == 255) {
            biomeID = worldChunkManager.getBiomeGenerator(blockPos, BiomeGenBase.plains).biomeID;
            this.blockBiomeArray[n2 << 4 | n] = (byte)(biomeID & 0xFF);
        }
        final BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
        return (biome == null) ? BiomeGenBase.plains : biome;
    }
    
    public ExtendedBlockStorage[] getBlockStorageArray() {
        return this.storageArrays;
    }
    
    public void setHasEntities(final boolean hasEntities) {
        this.hasEntities = hasEntities;
    }
    
    public void setChunkModified() {
        this.isModified = true;
    }
    
    public void getEntitiesOfTypeWithinAAAB(final Class clazz, final AxisAlignedBB axisAlignedBB, final List list, final Predicate predicate) {
        final int floor_double = MathHelper.floor_double((axisAlignedBB.minY - 2.0) / 16.0);
        final int floor_double2 = MathHelper.floor_double((axisAlignedBB.maxY + 2.0) / 16.0);
        final int clamp_int = MathHelper.clamp_int(floor_double, 0, this.entityLists.length - 1);
        for (int clamp_int2 = MathHelper.clamp_int(floor_double2, 0, this.entityLists.length - 1), i = clamp_int; i <= clamp_int2; ++i) {
            for (final Entity entity : this.entityLists[i].getByClass(clazz)) {
                if (entity.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply((Object)entity))) {
                    list.add(entity);
                }
            }
        }
    }
    
    public int getLightSubtracted(final BlockPos blockPos, final int n) {
        final int n2 = blockPos.getX() & 0xF;
        final int y = blockPos.getY();
        final int n3 = blockPos.getZ() & 0xF;
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> 4];
        if (extendedBlockStorage == null) {
            return (!this.worldObj.provider.getHasNoSky() && n < EnumSkyBlock.SKY.defaultLightValue) ? (EnumSkyBlock.SKY.defaultLightValue - n) : 0;
        }
        int n4 = (this.worldObj.provider.getHasNoSky() ? 0 : extendedBlockStorage.getExtSkylightValue(n2, y & 0xF, n3)) - n;
        final int extBlocklightValue = extendedBlockStorage.getExtBlocklightValue(n2, y & 0xF, n3);
        if (extBlocklightValue > n4) {
            n4 = extBlocklightValue;
        }
        return n4;
    }
    
    public TileEntity getTileEntity(final BlockPos blockPos, final EnumCreateEntityType enumCreateEntityType) {
        TileEntity newTileEntity = this.chunkTileEntityMap.get(blockPos);
        if (newTileEntity == null) {
            if (enumCreateEntityType == EnumCreateEntityType.IMMEDIATE) {
                newTileEntity = this.createNewTileEntity(blockPos);
                this.worldObj.setTileEntity(blockPos, newTileEntity);
            }
            else if (enumCreateEntityType == EnumCreateEntityType.QUEUED) {
                this.tileEntityPosQueue.add(blockPos);
            }
        }
        else if (newTileEntity.isInvalid()) {
            this.chunkTileEntityMap.remove(blockPos);
            return null;
        }
        return newTileEntity;
    }
    
    public void setBiomeArray(final byte[] array) {
        if (this.blockBiomeArray.length != array.length) {
            Chunk.logger.warn("Could not set level chunk biomes, array length is " + array.length + " instead of " + this.blockBiomeArray.length);
        }
        else {
            while (0 < this.blockBiomeArray.length) {
                this.blockBiomeArray[0] = array[0];
                int n = 0;
                ++n;
            }
        }
    }
    
    public ClassInheritanceMultiMap[] getEntityLists() {
        return this.entityLists;
    }
    
    private Block getBlock0(final int n, final int n2, final int n3) {
        Block block = Blocks.air;
        if (n2 >= 0 && n2 >> 4 < this.storageArrays.length) {
            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> 4];
            if (extendedBlockStorage != null) {
                block = extendedBlockStorage.getBlockByExtId(n, n2 & 0xF, n3);
            }
        }
        return block;
    }
    
    private void updateSkylightNeighborHeight(final int n, final int n2, final int n3, final int n4) {
        if (n4 > n3 && this.worldObj.isAreaLoaded(new BlockPos(n, 0, n2), 16)) {
            for (int i = n3; i < n4; ++i) {
                this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(n, i, n2));
            }
            this.isModified = true;
        }
    }
    
    public int getBlockLightOpacity(final BlockPos blockPos) {
        return this.getBlock(blockPos).getLightOpacity();
    }
    
    public void fillChunk(final byte[] p0, final int p1, final boolean p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.provider:Lnet/minecraft/world/WorldProvider;
        //     7: invokevirtual   net/minecraft/world/WorldProvider.getHasNoSky:()Z
        //    10: ifne            17
        //    13: iconst_1       
        //    14: goto            18
        //    17: iconst_0       
        //    18: istore          5
        //    20: iconst_0       
        //    21: aload_0        
        //    22: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //    25: arraylength    
        //    26: if_icmpge       139
        //    29: iload_2        
        //    30: iconst_1       
        //    31: iand           
        //    32: ifeq            113
        //    35: aload_0        
        //    36: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //    39: iconst_0       
        //    40: aaload         
        //    41: ifnonnull       60
        //    44: aload_0        
        //    45: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //    48: iconst_0       
        //    49: new             Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //    52: dup            
        //    53: iconst_0       
        //    54: iload           5
        //    56: invokespecial   net/minecraft/world/chunk/storage/ExtendedBlockStorage.<init>:(IZ)V
        //    59: aastore        
        //    60: aload_0        
        //    61: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //    64: iconst_0       
        //    65: aaload         
        //    66: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.getData:()[C
        //    69: astore          7
        //    71: iconst_0       
        //    72: aload           7
        //    74: arraylength    
        //    75: if_icmpge       110
        //    78: aload           7
        //    80: iconst_0       
        //    81: aload_1        
        //    82: iconst_1       
        //    83: baload         
        //    84: sipush          255
        //    87: iand           
        //    88: bipush          8
        //    90: ishl           
        //    91: aload_1        
        //    92: iconst_0       
        //    93: baload         
        //    94: sipush          255
        //    97: iand           
        //    98: ior            
        //    99: i2c            
        //   100: castore        
        //   101: iinc            4, 2
        //   104: iinc            8, 1
        //   107: goto            71
        //   110: goto            133
        //   113: iload_3        
        //   114: ifeq            133
        //   117: aload_0        
        //   118: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   121: iconst_0       
        //   122: aaload         
        //   123: ifnull          133
        //   126: aload_0        
        //   127: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   130: iconst_0       
        //   131: aconst_null    
        //   132: aastore        
        //   133: iinc            6, 1
        //   136: goto            20
        //   139: iconst_0       
        //   140: aload_0        
        //   141: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   144: arraylength    
        //   145: if_icmpge       207
        //   148: iload_2        
        //   149: iconst_1       
        //   150: iand           
        //   151: ifeq            201
        //   154: aload_0        
        //   155: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   158: iconst_0       
        //   159: aaload         
        //   160: ifnull          201
        //   163: aload_0        
        //   164: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   167: iconst_0       
        //   168: aaload         
        //   169: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.getBlocklightArray:()Lnet/minecraft/world/chunk/NibbleArray;
        //   172: astore          7
        //   174: aload_1        
        //   175: iconst_0       
        //   176: aload           7
        //   178: invokevirtual   net/minecraft/world/chunk/NibbleArray.getData:()[B
        //   181: iconst_0       
        //   182: aload           7
        //   184: invokevirtual   net/minecraft/world/chunk/NibbleArray.getData:()[B
        //   187: arraylength    
        //   188: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   191: iconst_0       
        //   192: aload           7
        //   194: invokevirtual   net/minecraft/world/chunk/NibbleArray.getData:()[B
        //   197: arraylength    
        //   198: iadd           
        //   199: istore          4
        //   201: iinc            6, 1
        //   204: goto            139
        //   207: iload           5
        //   209: ifeq            280
        //   212: iconst_0       
        //   213: aload_0        
        //   214: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   217: arraylength    
        //   218: if_icmpge       280
        //   221: iload_2        
        //   222: iconst_1       
        //   223: iand           
        //   224: ifeq            274
        //   227: aload_0        
        //   228: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   231: iconst_0       
        //   232: aaload         
        //   233: ifnull          274
        //   236: aload_0        
        //   237: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   240: iconst_0       
        //   241: aaload         
        //   242: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.getSkylightArray:()Lnet/minecraft/world/chunk/NibbleArray;
        //   245: astore          7
        //   247: aload_1        
        //   248: iconst_0       
        //   249: aload           7
        //   251: invokevirtual   net/minecraft/world/chunk/NibbleArray.getData:()[B
        //   254: iconst_0       
        //   255: aload           7
        //   257: invokevirtual   net/minecraft/world/chunk/NibbleArray.getData:()[B
        //   260: arraylength    
        //   261: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   264: iconst_0       
        //   265: aload           7
        //   267: invokevirtual   net/minecraft/world/chunk/NibbleArray.getData:()[B
        //   270: arraylength    
        //   271: iadd           
        //   272: istore          4
        //   274: iinc            6, 1
        //   277: goto            212
        //   280: iload_3        
        //   281: ifeq            308
        //   284: aload_1        
        //   285: iconst_0       
        //   286: aload_0        
        //   287: getfield        net/minecraft/world/chunk/Chunk.blockBiomeArray:[B
        //   290: iconst_0       
        //   291: aload_0        
        //   292: getfield        net/minecraft/world/chunk/Chunk.blockBiomeArray:[B
        //   295: arraylength    
        //   296: invokestatic    java/lang/System.arraycopy:(Ljava/lang/Object;ILjava/lang/Object;II)V
        //   299: iconst_0       
        //   300: aload_0        
        //   301: getfield        net/minecraft/world/chunk/Chunk.blockBiomeArray:[B
        //   304: arraylength    
        //   305: iadd           
        //   306: istore          6
        //   308: iconst_0       
        //   309: aload_0        
        //   310: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   313: arraylength    
        //   314: if_icmpge       347
        //   317: aload_0        
        //   318: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   321: iconst_0       
        //   322: aaload         
        //   323: ifnull          341
        //   326: iload_2        
        //   327: iconst_1       
        //   328: iand           
        //   329: ifeq            341
        //   332: aload_0        
        //   333: getfield        net/minecraft/world/chunk/Chunk.storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   336: iconst_0       
        //   337: aaload         
        //   338: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.removeInvalidBlocks:()V
        //   341: iinc            6, 1
        //   344: goto            308
        //   347: aload_0        
        //   348: iconst_1       
        //   349: putfield        net/minecraft/world/chunk/Chunk.isLightPopulated:Z
        //   352: aload_0        
        //   353: iconst_1       
        //   354: putfield        net/minecraft/world/chunk/Chunk.isTerrainPopulated:Z
        //   357: aload_0        
        //   358: invokevirtual   net/minecraft/world/chunk/Chunk.generateHeightMap:()V
        //   361: aload_0        
        //   362: getfield        net/minecraft/world/chunk/Chunk.chunkTileEntityMap:Ljava/util/Map;
        //   365: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //   370: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   375: astore          6
        //   377: aload           6
        //   379: invokeinterface java/util/Iterator.hasNext:()Z
        //   384: ifeq            407
        //   387: aload           6
        //   389: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   394: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //   397: astore          7
        //   399: aload           7
        //   401: invokevirtual   net/minecraft/tileentity/TileEntity.updateContainingBlockInfo:()V
        //   404: goto            377
        //   407: return         
        // 
        // The error that occurred was:
        // 
        // java.util.ConcurrentModificationException
        //     at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
        //     at java.util.ArrayList$Itr.next(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2863)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Block getBlock(final int n, final int n2, final int n3) {
        return this.getBlock0(n & 0xF, n2, n3 & 0xF);
    }
    
    public boolean isPopulated() {
        return this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated;
    }
    
    public BlockPos getPrecipitationHeight(final BlockPos blockPos) {
        final int n = (blockPos.getX() & 0xF) | (blockPos.getZ() & 0xF) << 4;
        if (new BlockPos(blockPos.getX(), this.precipitationHeightMap[n], blockPos.getZ()).getY() == -999) {
            BlockPos down = new BlockPos(blockPos.getX(), this.getTopFilledSegment() + 15, blockPos.getZ());
            while (down.getY() > 0) {
                final Material material = this.getBlock(down).getMaterial();
                if (!material.blocksMovement() && !material.isLiquid()) {
                    down = down.down();
                }
                else {
                    final int n2 = down.getY() + 1;
                }
            }
            this.precipitationHeightMap[n] = -1;
        }
        return new BlockPos(blockPos.getX(), this.precipitationHeightMap[n], blockPos.getZ());
    }
    
    public void setInhabitedTime(final long inhabitedTime) {
        this.inhabitedTime = inhabitedTime;
    }
    
    public void enqueueRelightChecks() {
        final BlockPos blockPos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
        if (this.queuedLightChecks >= 4096) {
            return;
        }
        final int n = this.queuedLightChecks % 16;
        final int n2 = this.queuedLightChecks / 16 % 16;
        final int n3 = this.queuedLightChecks / 256;
        ++this.queuedLightChecks;
        while (true) {
            final BlockPos add = blockPos.add(n2, (n << 4) + 0, n3);
            final boolean b = true;
            if ((this.storageArrays[n] == null && b) || (this.storageArrays[n] != null && this.storageArrays[n].getBlockByExtId(n2, 0, n3).getMaterial() == Material.air)) {
                final EnumFacing[] values = EnumFacing.values();
                while (0 < values.length) {
                    final BlockPos offset = add.offset(values[0]);
                    if (this.worldObj.getBlockState(offset).getBlock().getLightValue() > 0) {
                        this.worldObj.checkLight(offset);
                    }
                    int n4 = 0;
                    ++n4;
                }
                this.worldObj.checkLight(add);
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    public Random getRandomWithSeed(final long n) {
        return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ n);
    }
    
    private void checkSkylightNeighborHeight(final int n, final int n2, final int n3) {
        final int y = this.worldObj.getHeight(new BlockPos(n, 0, n2)).getY();
        if (y > n3) {
            this.updateSkylightNeighborHeight(n, n2, n3, y + 1);
        }
        else if (y < n3) {
            this.updateSkylightNeighborHeight(n, n2, y, n3 + 1);
        }
    }
    
    public void onChunkLoad() {
        this.isChunkLoaded = true;
        this.worldObj.addTileEntities(this.chunkTileEntityMap.values());
        while (0 < this.entityLists.length) {
            final Iterator iterator = this.entityLists[0].iterator();
            while (iterator.hasNext()) {
                iterator.next().onChunkLoad();
            }
            this.worldObj.loadEntities(this.entityLists[0]);
            int n = 0;
            ++n;
        }
    }
    
    private TileEntity createNewTileEntity(final BlockPos blockPos) {
        final Block block = this.getBlock(blockPos);
        return block.hasTileEntity() ? ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, this.getBlockMetadata(blockPos)) : null;
    }
    
    private void propagateSkylightOcclusion(final int n, final int n2) {
        this.updateSkylightColumns[n + n2 * 16] = true;
        this.isGapLightingUpdated = true;
    }
    
    public int getLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        final int n = blockPos.getX() & 0xF;
        final int y = blockPos.getY();
        final int n2 = blockPos.getZ() & 0xF;
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> 4];
        return (extendedBlockStorage == null) ? ((this >= blockPos) ? enumSkyBlock.defaultLightValue : 0) : ((enumSkyBlock == EnumSkyBlock.SKY) ? (this.worldObj.provider.getHasNoSky() ? 0 : extendedBlockStorage.getExtSkylightValue(n, y & 0xF, n2)) : ((enumSkyBlock == EnumSkyBlock.BLOCK) ? extendedBlockStorage.getExtBlocklightValue(n, y & 0xF, n2) : enumSkyBlock.defaultLightValue));
    }
    
    public int getHeight(final BlockPos blockPos) {
        return this.getHeightValue(blockPos.getX() & 0xF, blockPos.getZ() & 0xF);
    }
    
    public void setLastSaveTime(final long lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }
    
    public void addTileEntity(final TileEntity tileEntity) {
        this.addTileEntity(tileEntity.getPos(), tileEntity);
        if (this.isChunkLoaded) {
            this.worldObj.addTileEntity(tileEntity);
        }
    }
    
    public void addEntity(final Entity entity) {
        this.hasEntities = true;
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        if (floor_double != this.xPosition || floor_double2 != this.zPosition) {
            Chunk.logger.warn("Wrong location! (" + floor_double + ", " + floor_double2 + ") should be (" + this.xPosition + ", " + this.zPosition + "), " + entity, new Object[] { entity });
            entity.setDead();
        }
        MathHelper.floor_double(entity.posY / 16.0);
        if (0 >= this.entityLists.length) {
            final int n = this.entityLists.length - 1;
        }
        entity.addedToChunk = true;
        entity.chunkCoordX = this.xPosition;
        entity.chunkCoordY = 0;
        entity.chunkCoordZ = this.zPosition;
        this.entityLists[0].add(entity);
    }
    
    public void setLightPopulated(final boolean isLightPopulated) {
        this.isLightPopulated = isLightPopulated;
    }
    
    public void generateSkylightMap() {
        final int topFilledSegment = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        while (true) {
            this.precipitationHeightMap[0] = -999;
            int n = topFilledSegment + 16;
            while (this.getBlockLightOpacity(0, 14, 0) == 0) {
                --n;
            }
            this.heightMap[0] = 15;
            if (15 < this.heightMapMinimum) {
                this.heightMapMinimum = 15;
            }
            if (!this.worldObj.provider.getHasNoSky()) {
                int n2 = topFilledSegment + 16 - 1;
                do {
                    this.getBlockLightOpacity(0, n2, 0);
                    final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> 4];
                    if (extendedBlockStorage != null) {
                        extendedBlockStorage.setExtSkylightValue(0, n2 & 0xF, 0, 15);
                        this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + 0, n2, (this.zPosition << 4) + 0));
                    }
                } while (--n2 > 0);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public int[] getHeightMap() {
        return this.heightMap;
    }
    
    public long getInhabitedTime() {
        return this.inhabitedTime;
    }
    
    public int getBlockMetadata(final BlockPos blockPos) {
        return this.getBlockMetadata(blockPos.getX() & 0xF, blockPos.getY(), blockPos.getZ() & 0xF);
    }
    
    public void removeEntity(final Entity entity) {
        this.removeEntityAtIndex(entity, entity.chunkCoordY);
    }
    
    public IBlockState setBlockState(final BlockPos blockPos, final IBlockState blockState) {
        final int n = blockPos.getX() & 0xF;
        final int y = blockPos.getY();
        final int n2 = blockPos.getZ() & 0xF;
        final int n3 = n2 << 4 | n;
        if (y >= this.precipitationHeightMap[n3] - 1) {
            this.precipitationHeightMap[n3] = -999;
        }
        final int n4 = this.heightMap[n3];
        final IBlockState blockState2 = this.getBlockState(blockPos);
        if (blockState2 == blockState) {
            return null;
        }
        final Block block = blockState.getBlock();
        final Block block2 = blockState2.getBlock();
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> 4];
        if (extendedBlockStorage == null) {
            if (block == Blocks.air) {
                return null;
            }
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n5 = y >> 4;
            final ExtendedBlockStorage extendedBlockStorage2 = new ExtendedBlockStorage(y >> 4 << 4, !this.worldObj.provider.getHasNoSky());
            storageArrays[n5] = extendedBlockStorage2;
            extendedBlockStorage = extendedBlockStorage2;
            final boolean b = y >= n4;
        }
        extendedBlockStorage.set(n, y & 0xF, n2, blockState);
        if (block2 != block) {
            if (!this.worldObj.isRemote) {
                block2.breakBlock(this.worldObj, blockPos, blockState2);
            }
            else if (block2 instanceof ITileEntityProvider) {
                this.worldObj.removeTileEntity(blockPos);
            }
        }
        if (extendedBlockStorage.getBlockByExtId(n, y & 0xF, n2) != block) {
            return null;
        }
        final int lightOpacity = block.getLightOpacity();
        final int lightOpacity2 = block2.getLightOpacity();
        if (lightOpacity > 0) {
            if (y >= n4) {
                this.relightBlock(n, y + 1, n2);
            }
        }
        else if (y == n4 - 1) {
            this.relightBlock(n, y, n2);
        }
        if (lightOpacity != lightOpacity2 && (lightOpacity < lightOpacity2 || this.getLightFor(EnumSkyBlock.SKY, blockPos) > 0 || this.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 0)) {
            this.propagateSkylightOcclusion(n, n2);
        }
        if (block2 instanceof ITileEntityProvider) {
            final TileEntity tileEntity = this.getTileEntity(blockPos, EnumCreateEntityType.CHECK);
            if (tileEntity != null) {
                tileEntity.updateContainingBlockInfo();
            }
        }
        if (!this.worldObj.isRemote && block2 != block) {
            block.onBlockAdded(this.worldObj, blockPos, blockState);
        }
        if (block instanceof ITileEntityProvider) {
            TileEntity tileEntity2 = this.getTileEntity(blockPos, EnumCreateEntityType.CHECK);
            if (tileEntity2 == null) {
                tileEntity2 = ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, block.getMetaFromState(blockState));
                this.worldObj.setTileEntity(blockPos, tileEntity2);
            }
            if (tileEntity2 != null) {
                tileEntity2.updateContainingBlockInfo();
            }
        }
        this.isModified = true;
        return blockState2;
    }
    
    public byte[] getBiomeArray() {
        return this.blockBiomeArray;
    }
    
    public void getEntitiesWithinAABBForEntity(final Entity entity, final AxisAlignedBB axisAlignedBB, final List list, final Predicate predicate) {
        final int floor_double = MathHelper.floor_double((axisAlignedBB.minY - 2.0) / 16.0);
        final int floor_double2 = MathHelper.floor_double((axisAlignedBB.maxY + 2.0) / 16.0);
        final int clamp_int = MathHelper.clamp_int(floor_double, 0, this.entityLists.length - 1);
        for (int clamp_int2 = MathHelper.clamp_int(floor_double2, 0, this.entityLists.length - 1), i = clamp_int; i <= clamp_int2; ++i) {
            if (!this.entityLists[i].isEmpty()) {
                for (final Entity entity2 : this.entityLists[i]) {
                    if (entity2.getEntityBoundingBox().intersectsWith(axisAlignedBB) && entity2 != entity) {
                        if (predicate == null || predicate.apply((Object)entity2)) {
                            list.add(entity2);
                        }
                        final Entity[] parts = entity2.getParts();
                        if (parts == null) {
                            continue;
                        }
                        while (0 < parts.length) {
                            final Entity entity3 = parts[0];
                            if (entity3 != entity && entity3.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply((Object)entity3))) {
                                list.add(entity3);
                            }
                            int n = 0;
                            ++n;
                        }
                    }
                }
            }
        }
    }
    
    private int getBlockMetadata(final int n, final int n2, final int n3) {
        if (n2 >> 4 >= this.storageArrays.length) {
            return 0;
        }
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> 4];
        return (extendedBlockStorage != null) ? extendedBlockStorage.getExtBlockMetadata(n, n2 & 0xF, n3) : 0;
    }
    
    public void onChunkUnload() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_0       
        //     2: putfield        net/minecraft/world/chunk/Chunk.isChunkLoaded:Z
        //     5: aload_0        
        //     6: getfield        net/minecraft/world/chunk/Chunk.chunkTileEntityMap:Ljava/util/Map;
        //     9: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //    14: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    19: astore_1       
        //    20: aload_1        
        //    21: invokeinterface java/util/Iterator.hasNext:()Z
        //    26: ifeq            50
        //    29: aload_1        
        //    30: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    35: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //    38: astore_2       
        //    39: aload_0        
        //    40: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //    43: aload_2        
        //    44: invokevirtual   net/minecraft/world/World.markTileEntityForRemoval:(Lnet/minecraft/tileentity/TileEntity;)V
        //    47: goto            20
        //    50: iconst_0       
        //    51: aload_0        
        //    52: getfield        net/minecraft/world/chunk/Chunk.entityLists:[Lnet/minecraft/util/ClassInheritanceMultiMap;
        //    55: arraylength    
        //    56: if_icmpge       78
        //    59: aload_0        
        //    60: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //    63: aload_0        
        //    64: getfield        net/minecraft/world/chunk/Chunk.entityLists:[Lnet/minecraft/util/ClassInheritanceMultiMap;
        //    67: iconst_0       
        //    68: aaload         
        //    69: invokevirtual   net/minecraft/world/World.unloadEntities:(Ljava/util/Collection;)V
        //    72: iinc            1, 1
        //    75: goto            50
        //    78: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public IBlockState getBlockState(final BlockPos blockPos) {
        if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
            IBlockState blockState = null;
            if (blockPos.getY() == 60) {
                blockState = Blocks.barrier.getDefaultState();
            }
            if (blockPos.getY() == 70) {
                blockState = ChunkProviderDebug.func_177461_b(blockPos.getX(), blockPos.getZ());
            }
            return (blockState == null) ? Blocks.air.getDefaultState() : blockState;
        }
        if (blockPos.getY() >= 0 && blockPos.getY() >> 4 < this.storageArrays.length) {
            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[blockPos.getY() >> 4];
            if (extendedBlockStorage != null) {
                return extendedBlockStorage.get(blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF);
            }
        }
        return Blocks.air.getDefaultState();
    }
    
    public boolean isAtLocation(final int n, final int n2) {
        return n == this.xPosition && n2 == this.zPosition;
    }
    
    private void func_177441_y() {
        while (0 < this.updateSkylightColumns.length) {
            this.updateSkylightColumns[0] = true;
            int n = 0;
            ++n;
        }
        this.recheckGaps(false);
    }
    
    public Chunk(final World worldObj, final int xPosition, final int zPosition) {
        this.storageArrays = new ExtendedBlockStorage[16];
        this.blockBiomeArray = new byte[256];
        this.precipitationHeightMap = new int[256];
        this.updateSkylightColumns = new boolean[256];
        this.chunkTileEntityMap = Maps.newHashMap();
        this.queuedLightChecks = 4096;
        this.tileEntityPosQueue = Queues.newConcurrentLinkedQueue();
        this.entityLists = new ClassInheritanceMultiMap[16];
        this.worldObj = worldObj;
        this.xPosition = xPosition;
        this.zPosition = zPosition;
        this.heightMap = new int[256];
        while (0 < this.entityLists.length) {
            this.entityLists[0] = new ClassInheritanceMultiMap(Entity.class);
            int n = 0;
            ++n;
        }
        Arrays.fill(this.precipitationHeightMap, -999);
        Arrays.fill(this.blockBiomeArray, (byte)(-1));
    }
    
    public void func_150809_p() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_1       
        //     2: putfield        net/minecraft/world/chunk/Chunk.isTerrainPopulated:Z
        //     5: aload_0        
        //     6: iconst_1       
        //     7: putfield        net/minecraft/world/chunk/Chunk.isLightPopulated:Z
        //    10: new             Lnet/minecraft/util/BlockPos;
        //    13: dup            
        //    14: aload_0        
        //    15: getfield        net/minecraft/world/chunk/Chunk.xPosition:I
        //    18: iconst_4       
        //    19: ishl           
        //    20: iconst_0       
        //    21: aload_0        
        //    22: getfield        net/minecraft/world/chunk/Chunk.zPosition:I
        //    25: iconst_4       
        //    26: ishl           
        //    27: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    30: astore_1       
        //    31: aload_0        
        //    32: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //    35: getfield        net/minecraft/world/World.provider:Lnet/minecraft/world/WorldProvider;
        //    38: invokevirtual   net/minecraft/world/WorldProvider.getHasNoSky:()Z
        //    41: ifne            190
        //    44: aload_0        
        //    45: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //    48: aload_1        
        //    49: iconst_m1      
        //    50: iconst_0       
        //    51: iconst_m1      
        //    52: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    55: aload_1        
        //    56: bipush          16
        //    58: aload_0        
        //    59: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //    62: invokevirtual   net/minecraft/world/World.func_181545_F:()I
        //    65: bipush          16
        //    67: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    70: invokevirtual   net/minecraft/world/World.isAreaLoaded:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Z
        //    73: ifeq            185
        //    76: aload_0        
        //    77: aload_0        
        //    78: iconst_0       
        //    79: putfield        net/minecraft/world/chunk/Chunk.isLightPopulated:Z
        //    82: goto            97
        //    85: iinc            3, 1
        //    88: goto            76
        //    91: iinc            2, 1
        //    94: goto            76
        //    97: aload_0        
        //    98: getfield        net/minecraft/world/chunk/Chunk.isLightPopulated:Z
        //   101: ifeq            190
        //   104: getstatic       net/minecraft/util/EnumFacing$Plane.HORIZONTAL:Lnet/minecraft/util/EnumFacing$Plane;
        //   107: invokevirtual   net/minecraft/util/EnumFacing$Plane.iterator:()Ljava/util/Iterator;
        //   110: astore_2       
        //   111: aload_2        
        //   112: invokeinterface java/util/Iterator.hasNext:()Z
        //   117: ifeq            178
        //   120: aload_2        
        //   121: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   126: astore_3       
        //   127: aload_3        
        //   128: checkcast       Lnet/minecraft/util/EnumFacing;
        //   131: astore          4
        //   133: aload           4
        //   135: invokevirtual   net/minecraft/util/EnumFacing.getAxisDirection:()Lnet/minecraft/util/EnumFacing$AxisDirection;
        //   138: getstatic       net/minecraft/util/EnumFacing$AxisDirection.POSITIVE:Lnet/minecraft/util/EnumFacing$AxisDirection;
        //   141: if_acmpne       149
        //   144: bipush          16
        //   146: goto            150
        //   149: iconst_1       
        //   150: istore          5
        //   152: aload_0        
        //   153: getfield        net/minecraft/world/chunk/Chunk.worldObj:Lnet/minecraft/world/World;
        //   156: aload_1        
        //   157: aload           4
        //   159: iload           5
        //   161: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/BlockPos;
        //   164: invokevirtual   net/minecraft/world/World.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   167: aload           4
        //   169: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //   172: invokespecial   net/minecraft/world/chunk/Chunk.func_180700_a:(Lnet/minecraft/util/EnumFacing;)V
        //   175: goto            111
        //   178: aload_0        
        //   179: invokespecial   net/minecraft/world/chunk/Chunk.func_177441_y:()V
        //   182: goto            190
        //   185: aload_0        
        //   186: iconst_0       
        //   187: putfield        net/minecraft/world/chunk/Chunk.isLightPopulated:Z
        //   190: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0190 (coming from #0101).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void func_180700_a(final EnumFacing enumFacing) {
        if (this.isTerrainPopulated) {
            if (enumFacing == EnumFacing.EAST) {
                while (true) {
                    this.func_150811_f(15, 0);
                    int n = 0;
                    ++n;
                }
            }
            else if (enumFacing == EnumFacing.WEST) {
                while (true) {
                    this.func_150811_f(0, 0);
                    int n = 0;
                    ++n;
                }
            }
            else if (enumFacing == EnumFacing.SOUTH) {
                while (true) {
                    this.func_150811_f(0, 15);
                    int n = 0;
                    ++n;
                }
            }
            else if (enumFacing == EnumFacing.NORTH) {
                while (true) {
                    this.func_150811_f(0, 0);
                    int n = 0;
                    ++n;
                }
            }
        }
    }
    
    private void relightBlock(final int n, final int n2, final int n3) {
        int n5;
        final int n4 = n5 = (this.heightMap[n3 << 4 | n] & 0xFF);
        if (n2 > n4) {
            n5 = n2;
        }
        while (n5 > 0 && this.getBlockLightOpacity(n, n5 - 1, n3) == 0) {
            --n5;
        }
        if (n5 != n4) {
            this.worldObj.markBlocksDirtyVertical(n + this.xPosition * 16, n3 + this.zPosition * 16, n5, n4);
            this.heightMap[n3 << 4 | n] = n5;
            final int n6 = this.xPosition * 16 + n;
            final int n7 = this.zPosition * 16 + n3;
            if (!this.worldObj.provider.getHasNoSky()) {
                if (n5 < n4) {
                    int n8 = n5;
                    while (0 < n4) {
                        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[0];
                        if (extendedBlockStorage != null) {
                            extendedBlockStorage.setExtSkylightValue(n, 0, n3, 15);
                            this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + n, 0, (this.zPosition << 4) + n3));
                        }
                        ++n8;
                    }
                }
                else {
                    int n9 = n4;
                    while (0 < n5) {
                        final ExtendedBlockStorage extendedBlockStorage2 = this.storageArrays[0];
                        if (extendedBlockStorage2 != null) {
                            extendedBlockStorage2.setExtSkylightValue(n, 0, n3, 0);
                            this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + n, 0, (this.zPosition << 4) + n3));
                        }
                        ++n9;
                    }
                }
                if (n5 > 0) {}
            }
            final int n10 = this.heightMap[n3 << 4 | n];
            if (0 < n4) {}
            if (0 < this.heightMapMinimum) {
                this.heightMapMinimum = 0;
            }
            if (!this.worldObj.provider.getHasNoSky()) {
                for (final EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
                    this.updateSkylightNeighborHeight(n6 + enumFacing.getFrontOffsetX(), n7 + enumFacing.getFrontOffsetZ(), 1, 0);
                }
                this.updateSkylightNeighborHeight(n6, n7, 1, 0);
            }
            this.isModified = true;
        }
    }
    
    public boolean needsSaving(final boolean b) {
        if (b) {
            if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified) {
                return true;
            }
        }
        else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
            return true;
        }
        return this.isModified;
    }
    
    public void func_150804_b(final boolean b) {
        if (this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky() && !b) {
            this.recheckGaps(this.worldObj.isRemote);
        }
        this.field_150815_m = true;
        if (!this.isLightPopulated && this.isTerrainPopulated) {
            this.func_150809_p();
        }
        while (!this.tileEntityPosQueue.isEmpty()) {
            final BlockPos blockPos = this.tileEntityPosQueue.poll();
            if (this.getTileEntity(blockPos, EnumCreateEntityType.CHECK) == null && this.getBlock(blockPos).hasTileEntity()) {
                this.worldObj.setTileEntity(blockPos, this.createNewTileEntity(blockPos));
                this.worldObj.markBlockRangeForRenderUpdate(blockPos, blockPos);
            }
        }
    }
    
    public Chunk(final World world, final ChunkPrimer chunkPrimer, final int n, final int n2) {
        this(world, n, n2);
        final boolean b = !world.provider.getHasNoSky();
        while (true) {
            final IBlockState blockState = chunkPrimer.getBlockState(0);
            if (blockState.getBlock().getMaterial() != Material.air) {
                if (this.storageArrays[0] == null) {
                    this.storageArrays[0] = new ExtendedBlockStorage(0, b);
                }
                this.storageArrays[0].set(0, 0, 0, blockState);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public int getHeightValue(final int n, final int n2) {
        return this.heightMap[n2 << 4 | n];
    }
    
    public enum EnumCreateEntityType
    {
        QUEUED("QUEUED", 1), 
        CHECK("CHECK", 2), 
        IMMEDIATE("IMMEDIATE", 0);
        
        private static final EnumCreateEntityType[] $VALUES;
        
        private EnumCreateEntityType(final String s, final int n) {
        }
        
        static {
            $VALUES = new EnumCreateEntityType[] { EnumCreateEntityType.IMMEDIATE, EnumCreateEntityType.QUEUED, EnumCreateEntityType.CHECK };
        }
    }
}
