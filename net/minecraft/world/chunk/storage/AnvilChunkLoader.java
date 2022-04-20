package net.minecraft.world.chunk.storage;

import net.minecraft.world.chunk.*;
import java.io.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.storage.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO
{
    private static final Logger logger;
    private final File chunkSaveLocation;
    private Map chunksToRemove;
    private boolean field_183014_e;
    private Set pendingAnvilChunksCoordinates;
    
    @Override
    public Chunk loadChunk(final World world, final int n, final int n2) throws IOException {
        NBTTagCompound read = this.chunksToRemove.get(new ChunkCoordIntPair(n, n2));
        if (read == null) {
            final DataInputStream chunkInputStream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, n, n2);
            if (chunkInputStream == null) {
                return null;
            }
            read = CompressedStreamTools.read(chunkInputStream);
        }
        return this.checkedReadChunkFromNBT(world, n, n2, read);
    }
    
    private void func_183013_b(final ChunkCoordIntPair chunkCoordIntPair, final NBTTagCompound nbtTagCompound) throws IOException {
        final DataOutputStream chunkOutputStream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);
        CompressedStreamTools.write(nbtTagCompound, chunkOutputStream);
        chunkOutputStream.close();
    }
    
    @Override
    public void saveChunk(final World world, final Chunk chunk) throws MinecraftException, IOException {
        world.checkSessionLock();
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound.setTag("Level", nbtTagCompound2);
        this.writeChunkToNBT(chunk, world, nbtTagCompound2);
        this.addChunkToPending(chunk.getChunkCoordIntPair(), nbtTagCompound);
    }
    
    public AnvilChunkLoader(final File chunkSaveLocation) {
        this.chunksToRemove = new ConcurrentHashMap();
        this.pendingAnvilChunksCoordinates = Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());
        this.field_183014_e = false;
        this.chunkSaveLocation = chunkSaveLocation;
    }
    
    private Chunk readChunkFromNBT(final World p0, final NBTTagCompound p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "xPos"
        //     3: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //     6: istore_3       
        //     7: aload_2        
        //     8: ldc             "zPos"
        //    10: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    13: istore          4
        //    15: new             Lnet/minecraft/world/chunk/Chunk;
        //    18: dup            
        //    19: aload_1        
        //    20: iload_3        
        //    21: iload           4
        //    23: invokespecial   net/minecraft/world/chunk/Chunk.<init>:(Lnet/minecraft/world/World;II)V
        //    26: astore          5
        //    28: aload           5
        //    30: aload_2        
        //    31: ldc             "HeightMap"
        //    33: invokevirtual   net/minecraft/nbt/NBTTagCompound.getIntArray:(Ljava/lang/String;)[I
        //    36: invokevirtual   net/minecraft/world/chunk/Chunk.setHeightMap:([I)V
        //    39: aload           5
        //    41: aload_2        
        //    42: ldc             "TerrainPopulated"
        //    44: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    47: invokevirtual   net/minecraft/world/chunk/Chunk.setTerrainPopulated:(Z)V
        //    50: aload           5
        //    52: aload_2        
        //    53: ldc             "LightPopulated"
        //    55: invokevirtual   net/minecraft/nbt/NBTTagCompound.getBoolean:(Ljava/lang/String;)Z
        //    58: invokevirtual   net/minecraft/world/chunk/Chunk.setLightPopulated:(Z)V
        //    61: aload           5
        //    63: aload_2        
        //    64: ldc             "InhabitedTime"
        //    66: invokevirtual   net/minecraft/nbt/NBTTagCompound.getLong:(Ljava/lang/String;)J
        //    69: invokevirtual   net/minecraft/world/chunk/Chunk.setInhabitedTime:(J)V
        //    72: aload_2        
        //    73: ldc             "Sections"
        //    75: bipush          10
        //    77: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //    80: astore          6
        //    82: bipush          16
        //    84: anewarray       Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //    87: astore          8
        //    89: aload_1        
        //    90: getfield        net/minecraft/world/World.provider:Lnet/minecraft/world/WorldProvider;
        //    93: invokevirtual   net/minecraft/world/WorldProvider.getHasNoSky:()Z
        //    96: ifne            103
        //    99: iconst_1       
        //   100: goto            104
        //   103: iconst_0       
        //   104: istore          9
        //   106: iconst_0       
        //   107: aload           6
        //   109: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   112: if_icmpge       337
        //   115: aload           6
        //   117: iconst_0       
        //   118: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   121: astore          11
        //   123: aload           11
        //   125: ldc             "Y"
        //   127: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByte:(Ljava/lang/String;)B
        //   130: istore          12
        //   132: new             Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;
        //   135: dup            
        //   136: iconst_0       
        //   137: iload           9
        //   139: invokespecial   net/minecraft/world/chunk/storage/ExtendedBlockStorage.<init>:(IZ)V
        //   142: astore          13
        //   144: aload           11
        //   146: ldc             "Blocks"
        //   148: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   151: astore          14
        //   153: new             Lnet/minecraft/world/chunk/NibbleArray;
        //   156: dup            
        //   157: aload           11
        //   159: ldc             "Data"
        //   161: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   164: invokespecial   net/minecraft/world/chunk/NibbleArray.<init>:([B)V
        //   167: astore          15
        //   169: aload           11
        //   171: ldc             "Add"
        //   173: bipush          7
        //   175: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   178: ifeq            198
        //   181: new             Lnet/minecraft/world/chunk/NibbleArray;
        //   184: dup            
        //   185: aload           11
        //   187: ldc             "Add"
        //   189: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   192: invokespecial   net/minecraft/world/chunk/NibbleArray.<init>:([B)V
        //   195: goto            199
        //   198: aconst_null    
        //   199: astore          16
        //   201: aload           14
        //   203: arraylength    
        //   204: newarray        C
        //   206: astore          17
        //   208: iconst_0       
        //   209: aload           17
        //   211: arraylength    
        //   212: if_icmpge       270
        //   215: aload           16
        //   217: ifnull          231
        //   220: aload           16
        //   222: iconst_0       
        //   223: iconst_0       
        //   224: iconst_0       
        //   225: invokevirtual   net/minecraft/world/chunk/NibbleArray.get:(III)I
        //   228: goto            232
        //   231: iconst_0       
        //   232: istore          22
        //   234: aload           17
        //   236: iconst_0       
        //   237: iload           22
        //   239: bipush          12
        //   241: ishl           
        //   242: aload           14
        //   244: iconst_0       
        //   245: baload         
        //   246: sipush          255
        //   249: iand           
        //   250: iconst_4       
        //   251: ishl           
        //   252: ior            
        //   253: aload           15
        //   255: iconst_0       
        //   256: iconst_0       
        //   257: iconst_0       
        //   258: invokevirtual   net/minecraft/world/chunk/NibbleArray.get:(III)I
        //   261: ior            
        //   262: i2c            
        //   263: castore        
        //   264: iinc            18, 1
        //   267: goto            208
        //   270: aload           13
        //   272: aload           17
        //   274: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.setData:([C)V
        //   277: aload           13
        //   279: new             Lnet/minecraft/world/chunk/NibbleArray;
        //   282: dup            
        //   283: aload           11
        //   285: ldc             "BlockLight"
        //   287: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   290: invokespecial   net/minecraft/world/chunk/NibbleArray.<init>:([B)V
        //   293: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.setBlocklightArray:(Lnet/minecraft/world/chunk/NibbleArray;)V
        //   296: iload           9
        //   298: ifeq            320
        //   301: aload           13
        //   303: new             Lnet/minecraft/world/chunk/NibbleArray;
        //   306: dup            
        //   307: aload           11
        //   309: ldc             "SkyLight"
        //   311: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   314: invokespecial   net/minecraft/world/chunk/NibbleArray.<init>:([B)V
        //   317: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.setSkylightArray:(Lnet/minecraft/world/chunk/NibbleArray;)V
        //   320: aload           13
        //   322: invokevirtual   net/minecraft/world/chunk/storage/ExtendedBlockStorage.removeInvalidBlocks:()V
        //   325: aload           8
        //   327: iconst_0       
        //   328: aload           13
        //   330: aastore        
        //   331: iinc            10, 1
        //   334: goto            106
        //   337: aload           5
        //   339: aload           8
        //   341: invokevirtual   net/minecraft/world/chunk/Chunk.setStorageArrays:([Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)V
        //   344: aload_2        
        //   345: ldc_w           "Biomes"
        //   348: bipush          7
        //   350: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   353: ifeq            368
        //   356: aload           5
        //   358: aload_2        
        //   359: ldc_w           "Biomes"
        //   362: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByteArray:(Ljava/lang/String;)[B
        //   365: invokevirtual   net/minecraft/world/chunk/Chunk.setBiomeArray:([B)V
        //   368: aload_2        
        //   369: ldc_w           "Entities"
        //   372: bipush          10
        //   374: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   377: astore          10
        //   379: aload           10
        //   381: ifnull          504
        //   384: iconst_0       
        //   385: aload           10
        //   387: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   390: if_icmpge       504
        //   393: aload           10
        //   395: iconst_0       
        //   396: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   399: astore          12
        //   401: aload           12
        //   403: aload_1        
        //   404: invokestatic    net/minecraft/entity/EntityList.createEntityFromNBT:(Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;
        //   407: astore          13
        //   409: aload           5
        //   411: iconst_1       
        //   412: invokevirtual   net/minecraft/world/chunk/Chunk.setHasEntities:(Z)V
        //   415: aload           13
        //   417: ifnull          498
        //   420: aload           5
        //   422: aload           13
        //   424: invokevirtual   net/minecraft/world/chunk/Chunk.addEntity:(Lnet/minecraft/entity/Entity;)V
        //   427: aload           13
        //   429: astore          14
        //   431: aload           12
        //   433: astore          15
        //   435: aload           15
        //   437: ldc_w           "Riding"
        //   440: bipush          10
        //   442: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   445: ifeq            498
        //   448: aload           15
        //   450: ldc_w           "Riding"
        //   453: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   456: aload_1        
        //   457: invokestatic    net/minecraft/entity/EntityList.createEntityFromNBT:(Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;
        //   460: astore          16
        //   462: aload           16
        //   464: ifnull          481
        //   467: aload           5
        //   469: aload           16
        //   471: invokevirtual   net/minecraft/world/chunk/Chunk.addEntity:(Lnet/minecraft/entity/Entity;)V
        //   474: aload           14
        //   476: aload           16
        //   478: invokevirtual   net/minecraft/entity/Entity.mountEntity:(Lnet/minecraft/entity/Entity;)V
        //   481: aload           16
        //   483: astore          14
        //   485: aload           15
        //   487: ldc_w           "Riding"
        //   490: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   493: astore          15
        //   495: goto            435
        //   498: iinc            11, 1
        //   501: goto            384
        //   504: aload_2        
        //   505: ldc_w           "TileEntities"
        //   508: bipush          10
        //   510: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   513: astore          11
        //   515: aload           11
        //   517: ifnull          562
        //   520: iconst_0       
        //   521: aload           11
        //   523: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   526: if_icmpge       562
        //   529: aload           11
        //   531: iconst_0       
        //   532: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   535: astore          13
        //   537: aload           13
        //   539: invokestatic    net/minecraft/tileentity/TileEntity.createAndLoadEntity:(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/tileentity/TileEntity;
        //   542: astore          14
        //   544: aload           14
        //   546: ifnull          556
        //   549: aload           5
        //   551: aload           14
        //   553: invokevirtual   net/minecraft/world/chunk/Chunk.addTileEntity:(Lnet/minecraft/tileentity/TileEntity;)V
        //   556: iinc            12, 1
        //   559: goto            520
        //   562: aload_2        
        //   563: ldc_w           "TileTicks"
        //   566: bipush          9
        //   568: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   571: ifeq            708
        //   574: aload_2        
        //   575: ldc_w           "TileTicks"
        //   578: bipush          10
        //   580: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   583: astore          12
        //   585: aload           12
        //   587: ifnull          708
        //   590: iconst_0       
        //   591: aload           12
        //   593: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   596: if_icmpge       708
        //   599: aload           12
        //   601: iconst_0       
        //   602: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   605: astore          14
        //   607: aload           14
        //   609: ldc_w           "i"
        //   612: bipush          8
        //   614: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   617: ifeq            636
        //   620: aload           14
        //   622: ldc_w           "i"
        //   625: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   628: invokestatic    net/minecraft/block/Block.getBlockFromName:(Ljava/lang/String;)Lnet/minecraft/block/Block;
        //   631: astore          15
        //   633: goto            649
        //   636: aload           14
        //   638: ldc_w           "i"
        //   641: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   644: invokestatic    net/minecraft/block/Block.getBlockById:(I)Lnet/minecraft/block/Block;
        //   647: astore          15
        //   649: aload_1        
        //   650: new             Lnet/minecraft/util/BlockPos;
        //   653: dup            
        //   654: aload           14
        //   656: ldc_w           "x"
        //   659: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   662: aload           14
        //   664: ldc_w           "y"
        //   667: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   670: aload           14
        //   672: ldc_w           "z"
        //   675: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   678: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   681: aload           15
        //   683: aload           14
        //   685: ldc_w           "t"
        //   688: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   691: aload           14
        //   693: ldc_w           "p"
        //   696: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   699: invokevirtual   net/minecraft/world/World.scheduleBlockUpdate:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;II)V
        //   702: iinc            13, 1
        //   705: goto            590
        //   708: aload           5
        //   710: areturn        
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
    
    static {
        logger = LogManager.getLogger();
    }
    
    @Override
    public void saveExtraData() {
        this.field_183014_e = true;
        while (true) {
            if (this != 0) {
                continue;
            }
        }
    }
    
    protected void addChunkToPending(final ChunkCoordIntPair chunkCoordIntPair, final NBTTagCompound nbtTagCompound) {
        if (!this.pendingAnvilChunksCoordinates.contains(chunkCoordIntPair)) {
            this.chunksToRemove.put(chunkCoordIntPair, nbtTagCompound);
        }
        ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
    }
    
    private void writeChunkToNBT(final Chunk chunk, final World world, final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("V", (byte)1);
        nbtTagCompound.setInteger("xPos", chunk.xPosition);
        nbtTagCompound.setInteger("zPos", chunk.zPosition);
        nbtTagCompound.setLong("LastUpdate", world.getTotalWorldTime());
        nbtTagCompound.setIntArray("HeightMap", chunk.getHeightMap());
        nbtTagCompound.setBoolean("TerrainPopulated", chunk.isTerrainPopulated());
        nbtTagCompound.setBoolean("LightPopulated", chunk.isLightPopulated());
        nbtTagCompound.setLong("InhabitedTime", chunk.getInhabitedTime());
        final ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        final NBTTagList list = new NBTTagList();
        final boolean b = !world.provider.getHasNoSky();
        int length = blockStorageArray.length;
        nbtTagCompound.setTag("Sections", list);
        nbtTagCompound.setByteArray("Biomes", chunk.getBiomeArray());
        chunk.setHasEntities(false);
        final NBTTagList list2 = new NBTTagList();
        while (0 < chunk.getEntityLists().length) {
            for (final Entity entity : chunk.getEntityLists()[0]) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                if (entity.writeToNBTOptional(nbtTagCompound2)) {
                    chunk.setHasEntities(true);
                    list2.appendTag(nbtTagCompound2);
                }
            }
            ++length;
        }
        nbtTagCompound.setTag("Entities", list2);
        final NBTTagList list3 = new NBTTagList();
        for (final TileEntity tileEntity : chunk.getTileEntityMap().values()) {
            final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound3);
            list3.appendTag(nbtTagCompound3);
        }
        nbtTagCompound.setTag("TileEntities", list3);
        final List pendingBlockUpdates = world.getPendingBlockUpdates(chunk, false);
        if (pendingBlockUpdates != null) {
            final long totalWorldTime = world.getTotalWorldTime();
            final NBTTagList list4 = new NBTTagList();
            for (final NextTickListEntry nextTickListEntry : pendingBlockUpdates) {
                final NBTTagCompound nbtTagCompound4 = new NBTTagCompound();
                final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(nextTickListEntry.getBlock());
                nbtTagCompound4.setString("i", (resourceLocation == null) ? "" : resourceLocation.toString());
                nbtTagCompound4.setInteger("x", nextTickListEntry.position.getX());
                nbtTagCompound4.setInteger("y", nextTickListEntry.position.getY());
                nbtTagCompound4.setInteger("z", nextTickListEntry.position.getZ());
                nbtTagCompound4.setInteger("t", (int)(nextTickListEntry.scheduledTime - totalWorldTime));
                nbtTagCompound4.setInteger("p", nextTickListEntry.priority);
                list4.appendTag(nbtTagCompound4);
            }
            nbtTagCompound.setTag("TileTicks", list4);
        }
    }
    
    @Override
    public void saveExtraChunkData(final World world, final Chunk chunk) throws IOException {
    }
    
    @Override
    public void chunkTick() {
    }
    
    protected Chunk checkedReadChunkFromNBT(final World world, final int n, final int n2, final NBTTagCompound nbtTagCompound) {
        if (!nbtTagCompound.hasKey("Level", 10)) {
            AnvilChunkLoader.logger.error("Chunk file at " + n + "," + n2 + " is missing level data, skipping");
            return null;
        }
        final NBTTagCompound compoundTag = nbtTagCompound.getCompoundTag("Level");
        if (!compoundTag.hasKey("Sections", 9)) {
            AnvilChunkLoader.logger.error("Chunk file at " + n + "," + n2 + " is missing block data, skipping");
            return null;
        }
        Chunk chunk = this.readChunkFromNBT(world, compoundTag);
        if (!chunk.isAtLocation(n, n2)) {
            AnvilChunkLoader.logger.error("Chunk file at " + n + "," + n2 + " is in the wrong location; relocating. (Expected " + n + ", " + n2 + ", got " + chunk.xPosition + ", " + chunk.zPosition + ")");
            compoundTag.setInteger("xPos", n);
            compoundTag.setInteger("zPos", n2);
            chunk = this.readChunkFromNBT(world, compoundTag);
        }
        return chunk;
    }
}
