package net.minecraft.world;

import net.minecraft.scoreboard.*;
import net.minecraft.profiler.*;
import net.minecraft.world.border.*;
import net.minecraft.village.*;
import net.minecraft.world.storage.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.server.*;

public abstract class World implements IBlockAccess
{
    protected float thunderingStrength;
    protected int updateLCG;
    protected Scoreboard worldScoreboard;
    private int skylightSubtracted;
    public final List playerEntities;
    protected boolean findingSpawnPoint;
    protected final ISaveHandler saveHandler;
    protected boolean spawnPeacefulMobs;
    protected float prevRainingStrength;
    public final Profiler theProfiler;
    public final List tickableTileEntities;
    private final Calendar theCalendar;
    protected boolean scheduledUpdatesAreImmediate;
    private int lastLightningBolt;
    protected boolean spawnHostileMobs;
    protected float prevThunderingStrength;
    protected float rainingStrength;
    private boolean processingLoadedTiles;
    protected final List unloadedEntityList;
    public final boolean isRemote;
    private int field_181546_a;
    protected final IntHashMap entitiesById;
    public final WorldProvider provider;
    public final List weatherEffects;
    protected IChunkProvider chunkProvider;
    public final List loadedEntityList;
    private int ambientTickCountdown;
    protected List worldAccesses;
    private final WorldBorder worldBorder;
    protected final int DIST_HASH_MAGIC = 1013904223;
    public final Random rand;
    protected MapStorage mapStorage;
    private long cloudColour;
    protected VillageCollection villageCollectionObj;
    int[] lightUpdateBlockList;
    private final List tileEntitiesToBeRemoved;
    public final List loadedTileEntityList;
    protected WorldInfo worldInfo;
    protected Set activeChunkSet;
    private final List addedTileEntityList;
    
    public boolean canBlockFreezeWater(final BlockPos blockPos) {
        return this.canBlockFreeze(blockPos, false);
    }
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final BlockPos blockPos) {
        if (this.isBlockLoaded(blockPos)) {
            return this.getChunkFromBlockCoords(blockPos).getBiome(blockPos, this.provider.getWorldChunkManager());
        }
        return this.provider.getWorldChunkManager().getBiomeGenerator(blockPos, BiomeGenBase.plains);
    }
    
    public boolean isThundering() {
        return this.getThunderStrength(1.0f) > 0.9;
    }
    
    public int getLightFor(final EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (blockPos.getY() < 0) {
            blockPos = new BlockPos(blockPos.getX(), 0, blockPos.getZ());
        }
        if (this >= blockPos) {
            return enumSkyBlock.defaultLightValue;
        }
        if (!this.isBlockLoaded(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }
        return this.getChunkFromBlockCoords(blockPos).getLightFor(enumSkyBlock, blockPos);
    }
    
    public void updateEntityWithOptionalForce(final Entity p0, final boolean p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/Entity.posX:D
        //     4: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //     7: istore_3       
        //     8: aload_1        
        //     9: getfield        net/minecraft/entity/Entity.posZ:D
        //    12: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    15: istore          4
        //    17: iload_2        
        //    18: ifeq            42
        //    21: aload_0        
        //    22: iload_3        
        //    23: bipush          32
        //    25: isub           
        //    26: iconst_0       
        //    27: iload           4
        //    29: bipush          32
        //    31: isub           
        //    32: iload_3        
        //    33: bipush          32
        //    35: iadd           
        //    36: iconst_0       
        //    37: iload           4
        //    39: bipush          32
        //    41: iadd           
        //    42: aload_1        
        //    43: aload_1        
        //    44: getfield        net/minecraft/entity/Entity.posX:D
        //    47: putfield        net/minecraft/entity/Entity.lastTickPosX:D
        //    50: aload_1        
        //    51: aload_1        
        //    52: getfield        net/minecraft/entity/Entity.posY:D
        //    55: putfield        net/minecraft/entity/Entity.lastTickPosY:D
        //    58: aload_1        
        //    59: aload_1        
        //    60: getfield        net/minecraft/entity/Entity.posZ:D
        //    63: putfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //    66: aload_1        
        //    67: aload_1        
        //    68: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //    71: putfield        net/minecraft/entity/Entity.prevRotationYaw:F
        //    74: aload_1        
        //    75: aload_1        
        //    76: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //    79: putfield        net/minecraft/entity/Entity.prevRotationPitch:F
        //    82: iload_2        
        //    83: ifeq            121
        //    86: aload_1        
        //    87: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //    90: ifeq            121
        //    93: aload_1        
        //    94: dup            
        //    95: getfield        net/minecraft/entity/Entity.ticksExisted:I
        //    98: iconst_1       
        //    99: iadd           
        //   100: putfield        net/minecraft/entity/Entity.ticksExisted:I
        //   103: aload_1        
        //   104: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   107: ifnull          117
        //   110: aload_1        
        //   111: invokevirtual   net/minecraft/entity/Entity.updateRidden:()V
        //   114: goto            121
        //   117: aload_1        
        //   118: invokevirtual   net/minecraft/entity/Entity.onUpdate:()V
        //   121: aload_0        
        //   122: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   125: ldc_w           "chunkCheck"
        //   128: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   131: aload_1        
        //   132: getfield        net/minecraft/entity/Entity.posX:D
        //   135: invokestatic    java/lang/Double.isNaN:(D)Z
        //   138: ifne            151
        //   141: aload_1        
        //   142: getfield        net/minecraft/entity/Entity.posX:D
        //   145: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   148: ifeq            159
        //   151: aload_1        
        //   152: aload_1        
        //   153: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   156: putfield        net/minecraft/entity/Entity.posX:D
        //   159: aload_1        
        //   160: getfield        net/minecraft/entity/Entity.posY:D
        //   163: invokestatic    java/lang/Double.isNaN:(D)Z
        //   166: ifne            179
        //   169: aload_1        
        //   170: getfield        net/minecraft/entity/Entity.posY:D
        //   173: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   176: ifeq            187
        //   179: aload_1        
        //   180: aload_1        
        //   181: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   184: putfield        net/minecraft/entity/Entity.posY:D
        //   187: aload_1        
        //   188: getfield        net/minecraft/entity/Entity.posZ:D
        //   191: invokestatic    java/lang/Double.isNaN:(D)Z
        //   194: ifne            207
        //   197: aload_1        
        //   198: getfield        net/minecraft/entity/Entity.posZ:D
        //   201: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   204: ifeq            215
        //   207: aload_1        
        //   208: aload_1        
        //   209: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   212: putfield        net/minecraft/entity/Entity.posZ:D
        //   215: aload_1        
        //   216: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //   219: f2d            
        //   220: invokestatic    java/lang/Double.isNaN:(D)Z
        //   223: ifne            237
        //   226: aload_1        
        //   227: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //   230: f2d            
        //   231: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   234: ifeq            245
        //   237: aload_1        
        //   238: aload_1        
        //   239: getfield        net/minecraft/entity/Entity.prevRotationPitch:F
        //   242: putfield        net/minecraft/entity/Entity.rotationPitch:F
        //   245: aload_1        
        //   246: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   249: f2d            
        //   250: invokestatic    java/lang/Double.isNaN:(D)Z
        //   253: ifne            267
        //   256: aload_1        
        //   257: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   260: f2d            
        //   261: invokestatic    java/lang/Double.isInfinite:(D)Z
        //   264: ifeq            275
        //   267: aload_1        
        //   268: aload_1        
        //   269: getfield        net/minecraft/entity/Entity.prevRotationYaw:F
        //   272: putfield        net/minecraft/entity/Entity.rotationYaw:F
        //   275: aload_1        
        //   276: getfield        net/minecraft/entity/Entity.posX:D
        //   279: ldc2_w          16.0
        //   282: ddiv           
        //   283: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   286: istore          6
        //   288: aload_1        
        //   289: getfield        net/minecraft/entity/Entity.posY:D
        //   292: ldc2_w          16.0
        //   295: ddiv           
        //   296: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   299: istore          7
        //   301: aload_1        
        //   302: getfield        net/minecraft/entity/Entity.posZ:D
        //   305: ldc2_w          16.0
        //   308: ddiv           
        //   309: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   312: istore          8
        //   314: aload_1        
        //   315: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   318: ifeq            348
        //   321: aload_1        
        //   322: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   325: iload           6
        //   327: if_icmpne       348
        //   330: aload_1        
        //   331: getfield        net/minecraft/entity/Entity.chunkCoordY:I
        //   334: iload           7
        //   336: if_icmpne       348
        //   339: aload_1        
        //   340: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   343: iload           8
        //   345: if_icmpeq       414
        //   348: aload_1        
        //   349: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   352: ifeq            384
        //   355: aload_0        
        //   356: aload_1        
        //   357: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   360: aload_1        
        //   361: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   364: aload_0        
        //   365: aload_1        
        //   366: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   369: aload_1        
        //   370: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   373: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   376: aload_1        
        //   377: aload_1        
        //   378: getfield        net/minecraft/entity/Entity.chunkCoordY:I
        //   381: invokevirtual   net/minecraft/world/chunk/Chunk.removeEntityAtIndex:(Lnet/minecraft/entity/Entity;I)V
        //   384: aload_0        
        //   385: iload           6
        //   387: iload           8
        //   389: aload_1        
        //   390: iconst_1       
        //   391: putfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   394: aload_0        
        //   395: iload           6
        //   397: iload           8
        //   399: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   402: aload_1        
        //   403: invokevirtual   net/minecraft/world/chunk/Chunk.addEntity:(Lnet/minecraft/entity/Entity;)V
        //   406: goto            414
        //   409: aload_1        
        //   410: iconst_0       
        //   411: putfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   414: aload_0        
        //   415: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   418: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   421: iload_2        
        //   422: ifeq            484
        //   425: aload_1        
        //   426: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   429: ifeq            484
        //   432: aload_1        
        //   433: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   436: ifnull          484
        //   439: aload_1        
        //   440: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   443: getfield        net/minecraft/entity/Entity.isDead:Z
        //   446: ifne            471
        //   449: aload_1        
        //   450: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   453: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   456: aload_1        
        //   457: if_acmpne       471
        //   460: aload_0        
        //   461: aload_1        
        //   462: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   465: invokevirtual   net/minecraft/world/World.updateEntity:(Lnet/minecraft/entity/Entity;)V
        //   468: goto            484
        //   471: aload_1        
        //   472: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   475: aconst_null    
        //   476: putfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   479: aload_1        
        //   480: aconst_null    
        //   481: putfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   484: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0384 (coming from #0381).
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
    
    public void playSoundEffect(final double n, final double n2, final double n3, final String s, final float n4, final float n5) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).playSound(s, n, n2, n3, n4, n5);
            int n6 = 0;
            ++n6;
        }
    }
    
    public List getPlayers(final Class clazz, final Predicate predicate) {
        final ArrayList arrayList = Lists.newArrayList();
        for (final Entity entity : this.playerEntities) {
            if (clazz.isAssignableFrom(entity.getClass()) && predicate.apply((Object)entity)) {
                arrayList.add(entity);
            }
        }
        return arrayList;
    }
    
    public int getSkylightSubtracted() {
        return this.skylightSubtracted;
    }
    
    public boolean isAABBInMaterial(final AxisAlignedBB axisAlignedBB, final Material material) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double3; j < floor_double4; ++j) {
                for (int k = floor_double5; k < floor_double6; ++k) {
                    final IBlockState blockState = this.getBlockState(mutableBlockPos.func_181079_c(i, j, k));
                    if (blockState.getBlock().getMaterial() == material) {
                        final int intValue = (int)blockState.getValue(BlockLiquid.LEVEL);
                        double n = j + 1;
                        if (intValue < 8) {
                            n = j + 1 - intValue / 8.0;
                        }
                        if (n >= axisAlignedBB.minY) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public BlockPos getSpawnPoint() {
        BlockPos height = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
        if (!this.getWorldBorder().contains(height)) {
            height = this.getHeight(new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
        }
        return height;
    }
    
    protected abstract int getRenderDistanceChunks();
    
    public void scheduleUpdate(final BlockPos blockPos, final Block block, final int n) {
    }
    
    public Chunk getChunkFromBlockCoords(final BlockPos blockPos) {
        return this.getChunkFromChunkCoords(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }
    
    public boolean canBlockFreeze(final BlockPos blockPos, final boolean b) {
        if (this.getBiomeGenForCoords(blockPos).getFloatTemperature(blockPos) > 0.15f) {
            return false;
        }
        if (blockPos.getY() >= 0 && blockPos.getY() < 256 && this.getLightFor(EnumSkyBlock.BLOCK, blockPos) < 10) {
            final IBlockState blockState = this.getBlockState(blockPos);
            final Block block = blockState.getBlock();
            if ((block == Blocks.water || block == Blocks.flowing_water) && (int)blockState.getValue(BlockLiquid.LEVEL) == 0) {
                if (!b) {
                    return true;
                }
                if (this != blockPos.west() || this != blockPos.east() || this != blockPos.north() || this != blockPos.south()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int getLastLightningBolt() {
        return this.lastLightningBolt;
    }
    
    public boolean checkLight(final BlockPos blockPos) {
        if (!this.provider.getHasNoSky()) {
            final boolean b = false | this.checkLightFor(EnumSkyBlock.SKY, blockPos);
        }
        final boolean b2 = false | this.checkLightFor(EnumSkyBlock.BLOCK, blockPos);
        return false;
    }
    
    public WorldInfo getWorldInfo() {
        return this.worldInfo;
    }
    
    public void addWorldAccess(final IWorldAccess worldAccess) {
        this.worldAccesses.add(worldAccess);
    }
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return false;
    }
    
    public float getCelestialAngle(final float n) {
        return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), n);
    }
    
    public boolean isAreaLoaded(final StructureBoundingBox structureBoundingBox, final boolean b) {
        return this.isAreaLoaded(structureBoundingBox.minX, structureBoundingBox.minY, structureBoundingBox.minZ, structureBoundingBox.maxX, structureBoundingBox.maxY, structureBoundingBox.maxZ, b);
    }
    
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
        if (this >= blockPos && this.isBlockLoaded(blockPos)) {
            this.getChunkFromBlockCoords(blockPos).setLightFor(enumSkyBlock, blockPos, n);
            this.notifyLightSet(blockPos);
        }
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final boolean b, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.spawnParticle(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange() | b, n, n2, n3, n4, n5, n6, array);
    }
    
    public boolean isSidePowered(final BlockPos blockPos, final EnumFacing enumFacing) {
        return this.getRedstonePower(blockPos, enumFacing) > 0;
    }
    
    public void setAllowedSpawnTypes(final boolean spawnHostileMobs, final boolean spawnPeacefulMobs) {
        this.spawnHostileMobs = spawnHostileMobs;
        this.spawnPeacefulMobs = spawnPeacefulMobs;
    }
    
    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final BlockPos blockPos2, final boolean b) {
        return this.isAreaLoaded(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), b);
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos blockPos) {
        if (this >= blockPos) {
            return Blocks.air.getDefaultState();
        }
        return this.getChunkFromBlockCoords(blockPos).getBlockState(blockPos);
    }
    
    public void setTotalWorldTime(final long worldTotalTime) {
        this.worldInfo.setWorldTotalTime(worldTotalTime);
    }
    
    private void spawnParticle(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).spawnParticle(n, b, n2, n3, n4, n5, n6, n7, array);
            int n8 = 0;
            ++n8;
        }
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final int n, final boolean b) {
        return this.isAreaLoaded(blockPos.getX() - n, blockPos.getY() - n, blockPos.getZ() - n, blockPos.getX() + n, blockPos.getY() + n, blockPos.getZ() + n, b);
    }
    
    protected void updateWeather() {
        if (!this.provider.getHasNoSky() && !this.isRemote) {
            int cleanWeatherTime = this.worldInfo.getCleanWeatherTime();
            if (cleanWeatherTime > 0) {
                --cleanWeatherTime;
                this.worldInfo.setCleanWeatherTime(cleanWeatherTime);
                this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
                this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
            }
            int thunderTime = this.worldInfo.getThunderTime();
            if (thunderTime <= 0) {
                if (this.worldInfo.isThundering()) {
                    this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
                }
                else {
                    this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
                }
            }
            else {
                --thunderTime;
                this.worldInfo.setThunderTime(thunderTime);
                if (thunderTime <= 0) {
                    this.worldInfo.setThundering(!this.worldInfo.isThundering());
                }
            }
            this.prevThunderingStrength = this.thunderingStrength;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength += (float)0.01;
            }
            else {
                this.thunderingStrength -= (float)0.01;
            }
            this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0f, 1.0f);
            int rainTime = this.worldInfo.getRainTime();
            if (rainTime <= 0) {
                if (this.worldInfo.isRaining()) {
                    this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
                }
                else {
                    this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
                }
            }
            else {
                --rainTime;
                this.worldInfo.setRainTime(rainTime);
                if (rainTime <= 0) {
                    this.worldInfo.setRaining(!this.worldInfo.isRaining());
                }
            }
            this.prevRainingStrength = this.rainingStrength;
            if (this.worldInfo.isRaining()) {
                this.rainingStrength += (float)0.01;
            }
            else {
                this.rainingStrength -= (float)0.01;
            }
            this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0f, 1.0f);
        }
    }
    
    public List getEntitiesWithinAABBExcludingEntity(final Entity entity, final AxisAlignedBB axisAlignedBB) {
        return this.getEntitiesInAABBexcluding(entity, axisAlignedBB, EntitySelectors.NOT_SPECTATING);
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.spawnParticle(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange(), n, n2, n3, n4, n5, n6, array);
    }
    
    public boolean canBlockFreezeNoWater(final BlockPos blockPos) {
        return this.canBlockFreeze(blockPos, true);
    }
    
    public boolean setBlockState(final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (this >= blockPos) {
            return false;
        }
        if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return false;
        }
        final Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        final Block block = blockState.getBlock();
        final IBlockState setBlockState = chunkFromBlockCoords.setBlockState(blockPos, blockState);
        if (setBlockState == null) {
            return false;
        }
        final Block block2 = setBlockState.getBlock();
        if (block.getLightOpacity() != block2.getLightOpacity() || block.getLightValue() != block2.getLightValue()) {
            this.theProfiler.startSection("checkLight");
            this.checkLight(blockPos);
            this.theProfiler.endSection();
        }
        if ((n & 0x2) != 0x0 && (!this.isRemote || (n & 0x4) == 0x0) && chunkFromBlockCoords.isPopulated()) {
            this.markBlockForUpdate(blockPos);
        }
        if (!this.isRemote && (n & 0x1) != 0x0) {
            this.notifyNeighborsRespectDebug(blockPos, setBlockState.getBlock());
            if (block.hasComparatorInputOverride()) {
                this.updateComparatorOutputLevel(blockPos, block);
            }
        }
        return true;
    }
    
    public boolean isFlammableWithin(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double3; j < floor_double4; ++j) {
                for (int k = floor_double5; k < floor_double6; ++k) {
                    final Block block = this.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock();
                    if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void notifyNeighborsOfStateExcept(final BlockPos blockPos, final Block block, final EnumFacing enumFacing) {
        if (enumFacing != EnumFacing.WEST) {
            this.notifyBlockOfStateChange(blockPos.west(), block);
        }
        if (enumFacing != EnumFacing.EAST) {
            this.notifyBlockOfStateChange(blockPos.east(), block);
        }
        if (enumFacing != EnumFacing.DOWN) {
            this.notifyBlockOfStateChange(blockPos.down(), block);
        }
        if (enumFacing != EnumFacing.UP) {
            this.notifyBlockOfStateChange(blockPos.up(), block);
        }
        if (enumFacing != EnumFacing.NORTH) {
            this.notifyBlockOfStateChange(blockPos.north(), block);
        }
        if (enumFacing != EnumFacing.SOUTH) {
            this.notifyBlockOfStateChange(blockPos.south(), block);
        }
    }
    
    public void markBlockRangeForRenderUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).markBlockRangeForRenderUpdate(n, n2, n3, n4, n5, n6);
            int n7 = 0;
            ++n7;
        }
    }
    
    @Override
    public int getCombinedLight(final BlockPos blockPos, final int n) {
        final int lightFromNeighbors = this.getLightFromNeighborsFor(EnumSkyBlock.SKY, blockPos);
        int lightFromNeighbors2 = this.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, blockPos);
        if (lightFromNeighbors2 < n) {
            lightFromNeighbors2 = n;
        }
        return lightFromNeighbors << 20 | lightFromNeighbors2 << 4;
    }
    
    public boolean extinguishFire(final EntityPlayer entityPlayer, BlockPos offset, final EnumFacing enumFacing) {
        offset = offset.offset(enumFacing);
        if (this.getBlockState(offset).getBlock() == Blocks.fire) {
            this.playAuxSFXAtEntity(entityPlayer, 1004, offset, 0);
            this.setBlockToAir(offset);
            return true;
        }
        return false;
    }
    
    public Vec3 getFogColor(final float n) {
        return this.provider.getFogColor(this.getCelestialAngle(n), n);
    }
    
    public void playBroadcastSound(final int n, final BlockPos blockPos, final int n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).broadcastSound(n, blockPos, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public void playSoundAtEntity(final Entity entity, final String s, final float n, final float n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).playSound(s, entity.posX, entity.posY, entity.posZ, n, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public void makeFireworks(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final NBTTagCompound nbtTagCompound) {
    }
    
    public boolean isDaytime() {
        return this.skylightSubtracted < 4;
    }
    
    public World init() {
        return this;
    }
    
    public boolean isAnyPlayerWithinRangeAt(final double n, final double n2, final double n3, final double n4) {
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(0);
            if (EntitySelectors.NOT_SPECTATING.apply((Object)entityPlayer)) {
                final double distanceSq = entityPlayer.getDistanceSq(n, n2, n3);
                if (n4 < 0.0 || distanceSq < n4 * n4) {
                    return true;
                }
            }
            int n5 = 0;
            ++n5;
        }
        return false;
    }
    
    public MovingObjectPosition rayTraceBlocks(Vec3 vec3, final Vec3 vec4, final boolean b, final boolean b2, final boolean b3) {
        if (Double.isNaN(vec3.xCoord) || Double.isNaN(vec3.yCoord) || Double.isNaN(vec3.zCoord)) {
            return null;
        }
        if (Double.isNaN(vec4.xCoord) || Double.isNaN(vec4.yCoord) || Double.isNaN(vec4.zCoord)) {
            return null;
        }
        final int floor_double = MathHelper.floor_double(vec4.xCoord);
        final int floor_double2 = MathHelper.floor_double(vec4.yCoord);
        final int floor_double3 = MathHelper.floor_double(vec4.zCoord);
        int floor_double4 = MathHelper.floor_double(vec3.xCoord);
        int floor_double5 = MathHelper.floor_double(vec3.yCoord);
        int floor_double6 = MathHelper.floor_double(vec3.zCoord);
        final BlockPos blockPos = new BlockPos(floor_double4, floor_double5, floor_double6);
        final IBlockState blockState = this.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if ((!b2 || block.getCollisionBoundingBox(this, blockPos, blockState) != null) && block.canCollideCheck(blockState, b)) {
            final MovingObjectPosition collisionRayTrace = block.collisionRayTrace(this, blockPos, vec3, vec4);
            if (collisionRayTrace != null) {
                return collisionRayTrace;
            }
        }
        MovingObjectPosition movingObjectPosition = null;
        while (true) {
            final int n = 200;
            int n2 = 0;
            --n2;
            if (n < 0) {
                return b3 ? movingObjectPosition : null;
            }
            if (Double.isNaN(vec3.xCoord) || Double.isNaN(vec3.yCoord) || Double.isNaN(vec3.zCoord)) {
                return null;
            }
            if (floor_double4 == floor_double && floor_double5 == floor_double2 && floor_double6 == floor_double3) {
                return b3 ? movingObjectPosition : null;
            }
            double n3 = 999.0;
            double n4 = 999.0;
            double n5 = 999.0;
            if (floor_double > floor_double4) {
                n3 = floor_double4 + 1.0;
            }
            else if (floor_double < floor_double4) {
                n3 = floor_double4 + 0.0;
            }
            if (floor_double2 > floor_double5) {
                n4 = floor_double5 + 1.0;
            }
            else if (floor_double2 < floor_double5) {
                n4 = floor_double5 + 0.0;
            }
            if (floor_double3 > floor_double6) {
                n5 = floor_double6 + 1.0;
            }
            else if (floor_double3 < floor_double6) {
                n5 = floor_double6 + 0.0;
            }
            double n6 = 999.0;
            double n7 = 999.0;
            double n8 = 999.0;
            final double n9 = vec4.xCoord - vec3.xCoord;
            final double n10 = vec4.yCoord - vec3.yCoord;
            final double n11 = vec4.zCoord - vec3.zCoord;
            if (n6 == -0.0) {
                n6 = -1.0E-4;
            }
            if (n7 == -0.0) {
                n7 = -1.0E-4;
            }
            if (n8 == -0.0) {
                n8 = -1.0E-4;
            }
            EnumFacing enumFacing;
            if (n6 < n7 && n6 < n8) {
                enumFacing = ((floor_double > floor_double4) ? EnumFacing.WEST : EnumFacing.EAST);
                vec3 = new Vec3(n3, vec3.yCoord + n10 * n6, vec3.zCoord + n11 * n6);
            }
            else if (n7 < n8) {
                enumFacing = ((floor_double2 > floor_double5) ? EnumFacing.DOWN : EnumFacing.UP);
                vec3 = new Vec3(vec3.xCoord + n9 * n7, n4, vec3.zCoord + n11 * n7);
            }
            else {
                enumFacing = ((floor_double3 > floor_double6) ? EnumFacing.NORTH : EnumFacing.SOUTH);
                vec3 = new Vec3(vec3.xCoord + n9 * n8, vec3.yCoord + n10 * n8, n5);
            }
            floor_double4 = MathHelper.floor_double(vec3.xCoord) - ((enumFacing == EnumFacing.EAST) ? 1 : 0);
            floor_double5 = MathHelper.floor_double(vec3.yCoord) - ((enumFacing == EnumFacing.UP) ? 1 : 0);
            floor_double6 = MathHelper.floor_double(vec3.zCoord) - ((enumFacing == EnumFacing.SOUTH) ? 1 : 0);
            final BlockPos blockPos2 = new BlockPos(floor_double4, floor_double5, floor_double6);
            final IBlockState blockState2 = this.getBlockState(blockPos2);
            final Block block2 = blockState2.getBlock();
            if (b2 && block2.getCollisionBoundingBox(this, blockPos2, blockState2) == null) {
                continue;
            }
            if (block2.canCollideCheck(blockState2, b)) {
                final MovingObjectPosition collisionRayTrace2 = block2.collisionRayTrace(this, blockPos2, vec3, vec4);
                if (collisionRayTrace2 != null) {
                    return collisionRayTrace2;
                }
                continue;
            }
            else {
                movingObjectPosition = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec3, enumFacing, blockPos2);
            }
        }
    }
    
    public void sendBlockBreakProgress(final int n, final BlockPos blockPos, final int n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).sendBlockBreakProgress(n, blockPos, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public boolean setBlockState(final BlockPos blockPos, final IBlockState blockState) {
        return this.setBlockState(blockPos, blockState, 3);
    }
    
    public void setRainStrength(final float n) {
        this.prevRainingStrength = n;
        this.rainingStrength = n;
    }
    
    public long getTotalWorldTime() {
        return this.worldInfo.getWorldTotalTime();
    }
    
    public void checkSessionLock() throws MinecraftException {
        this.saveHandler.checkSessionLock();
    }
    
    public boolean checkLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        if (!this.isAreaLoaded(blockPos, 17, false)) {
            return false;
        }
        this.theProfiler.startSection("getBrightness");
        final int light = this.getLightFor(enumSkyBlock, blockPos);
        final int rawLight = this.getRawLight(blockPos, enumSkyBlock);
        blockPos.getX();
        blockPos.getY();
        blockPos.getZ();
        if (rawLight > light) {
            final int[] lightUpdateBlockList = this.lightUpdateBlockList;
            final int n = 0;
            int n2 = 0;
            ++n2;
            lightUpdateBlockList[n] = 133152;
        }
        else if (rawLight < light) {
            final int[] lightUpdateBlockList2 = this.lightUpdateBlockList;
            final int n3 = 0;
            int n2 = 0;
            ++n2;
            lightUpdateBlockList2[n3] = (0x20820 | light << 18);
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("checkedPosition < toCheckCount");
        this.theProfiler.endSection();
        return true;
    }
    
    public BlockPos getHeight(final BlockPos p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //     4: ldc_w           -30000000
        //     7: if_icmplt       93
        //    10: aload_1        
        //    11: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    14: ldc_w           -30000000
        //    17: if_icmplt       93
        //    20: aload_1        
        //    21: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    24: ldc_w           30000000
        //    27: if_icmpge       93
        //    30: aload_1        
        //    31: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    34: ldc_w           30000000
        //    37: if_icmpge       93
        //    40: aload_0        
        //    41: aload_1        
        //    42: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    45: iconst_4       
        //    46: ishr           
        //    47: aload_1        
        //    48: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    51: iconst_4       
        //    52: ishr           
        //    53: aload_0        
        //    54: aload_1        
        //    55: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    58: iconst_4       
        //    59: ishr           
        //    60: aload_1        
        //    61: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    64: iconst_4       
        //    65: ishr           
        //    66: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //    69: aload_1        
        //    70: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    73: bipush          15
        //    75: iand           
        //    76: aload_1        
        //    77: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    80: bipush          15
        //    82: iand           
        //    83: invokevirtual   net/minecraft/world/chunk/Chunk.getHeightValue:(II)I
        //    86: istore_2       
        //    87: goto            100
        //    90: goto            100
        //    93: aload_0        
        //    94: invokevirtual   net/minecraft/world/World.func_181545_F:()I
        //    97: iconst_1       
        //    98: iadd           
        //    99: istore_2       
        //   100: new             Lnet/minecraft/util/BlockPos;
        //   103: dup            
        //   104: aload_1        
        //   105: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   108: iconst_0       
        //   109: aload_1        
        //   110: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   113: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   116: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0100 (coming from #0087).
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
    
    public boolean isBlockNormalCube(final BlockPos blockPos, final boolean b) {
        if (this >= blockPos) {
            return b;
        }
        if (this.chunkProvider.provideChunk(blockPos).isEmpty()) {
            return b;
        }
        final Block block = this.getBlockState(blockPos).getBlock();
        return block.getMaterial().isOpaque() && block.isFullCube();
    }
    
    public List getEntitiesInAABBexcluding(final Entity p0, final AxisAlignedBB p1, final Predicate p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore          4
        //     5: aload_2        
        //     6: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //     9: ldc2_w          2.0
        //    12: dsub           
        //    13: ldc2_w          16.0
        //    16: ddiv           
        //    17: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    20: istore          5
        //    22: aload_2        
        //    23: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //    26: ldc2_w          2.0
        //    29: dadd           
        //    30: ldc2_w          16.0
        //    33: ddiv           
        //    34: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    37: istore          6
        //    39: aload_2        
        //    40: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //    43: ldc2_w          2.0
        //    46: dsub           
        //    47: ldc2_w          16.0
        //    50: ddiv           
        //    51: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    54: istore          7
        //    56: aload_2        
        //    57: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //    60: ldc2_w          2.0
        //    63: dadd           
        //    64: ldc2_w          16.0
        //    67: ddiv           
        //    68: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    71: istore          8
        //    73: iload           5
        //    75: istore          9
        //    77: iload           9
        //    79: iload           6
        //    81: if_icmpgt       128
        //    84: iload           7
        //    86: istore          10
        //    88: iload           10
        //    90: iload           8
        //    92: if_icmpgt       122
        //    95: aload_0        
        //    96: iload           9
        //    98: iload           10
        //   100: aload_0        
        //   101: iload           9
        //   103: iload           10
        //   105: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   108: aload_1        
        //   109: aload_2        
        //   110: aload           4
        //   112: aload_3        
        //   113: invokevirtual   net/minecraft/world/chunk/Chunk.getEntitiesWithinAABBForEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lcom/google/common/base/Predicate;)V
        //   116: iinc            10, 1
        //   119: goto            88
        //   122: iinc            9, 1
        //   125: goto            77
        //   128: aload           4
        //   130: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0088 (coming from #0119).
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
    
    protected World(final ISaveHandler saveHandler, final WorldInfo worldInfo, final WorldProvider provider, final Profiler theProfiler, final boolean isRemote) {
        this.field_181546_a = 63;
        this.loadedEntityList = Lists.newArrayList();
        this.unloadedEntityList = Lists.newArrayList();
        this.loadedTileEntityList = Lists.newArrayList();
        this.tickableTileEntities = Lists.newArrayList();
        this.addedTileEntityList = Lists.newArrayList();
        this.tileEntitiesToBeRemoved = Lists.newArrayList();
        this.playerEntities = Lists.newArrayList();
        this.weatherEffects = Lists.newArrayList();
        this.entitiesById = new IntHashMap();
        this.cloudColour = 16777215L;
        this.updateLCG = new Random().nextInt();
        this.rand = new Random();
        this.worldAccesses = Lists.newArrayList();
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.activeChunkSet = Sets.newHashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.lightUpdateBlockList = new int[32768];
        this.saveHandler = saveHandler;
        this.theProfiler = theProfiler;
        this.worldInfo = worldInfo;
        this.provider = provider;
        this.isRemote = isRemote;
        this.worldBorder = provider.getWorldBorder();
    }
    
    public EntityPlayer getClosestPlayer(final double n, final double n2, final double n3, final double n4) {
        double n5 = -1.0;
        EntityPlayer entityPlayer = null;
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer2 = this.playerEntities.get(0);
            if (EntitySelectors.NOT_SPECTATING.apply((Object)entityPlayer2)) {
                final double distanceSq = entityPlayer2.getDistanceSq(n, n2, n3);
                if ((n4 < 0.0 || distanceSq < n4 * n4) && (n5 == -1.0 || distanceSq < n5)) {
                    n5 = distanceSq;
                    entityPlayer = entityPlayer2;
                }
            }
            int n6 = 0;
            ++n6;
        }
        return entityPlayer;
    }
    
    public List getEntities(final Class clazz, final Predicate predicate) {
        final ArrayList arrayList = Lists.newArrayList();
        for (final Entity entity : this.loadedEntityList) {
            if (clazz.isAssignableFrom(entity.getClass()) && predicate.apply((Object)entity)) {
                arrayList.add(entity);
            }
        }
        return arrayList;
    }
    
    public void markBlocksDirtyVertical(final int n, final int n2, int n3, int n4) {
        if (n3 > n4) {
            final int n5 = n4;
            n4 = n3;
            n3 = n5;
        }
        if (!this.provider.getHasNoSky()) {
            for (int i = n3; i <= n4; ++i) {
                this.checkLightFor(EnumSkyBlock.SKY, new BlockPos(n, i, n2));
            }
        }
        this.markBlockRangeForRenderUpdate(n, n3, n2, n, n4, n2);
    }
    
    public void setSpawnPoint(final BlockPos spawn) {
        this.worldInfo.setSpawn(spawn);
    }
    
    public float getThunderStrength(final float n) {
        return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * n) * this.getRainStrength(n);
    }
    
    public void removeWorldAccess(final IWorldAccess worldAccess) {
        this.worldAccesses.remove(worldAccess);
    }
    
    public boolean checkNoEntityCollision(final AxisAlignedBB axisAlignedBB) {
        return this.checkNoEntityCollision(axisAlignedBB, (Entity)null);
    }
    
    public boolean canBlockBePlaced(final Block p0, final BlockPos p1, final boolean p2, final EnumFacing p3, final Entity p4, final ItemStack p5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //     5: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //    10: astore          7
        //    12: iload_3        
        //    13: ifeq            20
        //    16: aconst_null    
        //    17: goto            30
        //    20: aload_1        
        //    21: aload_0        
        //    22: aload_2        
        //    23: aload_1        
        //    24: invokevirtual   net/minecraft/block/Block.getDefaultState:()Lnet/minecraft/block/state/IBlockState;
        //    27: invokevirtual   net/minecraft/block/Block.getCollisionBoundingBox:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/util/AxisAlignedBB;
        //    30: astore          8
        //    32: aload           8
        //    34: ifnull          49
        //    37: aload_0        
        //    38: aload           8
        //    40: aload           5
        //    42: if_icmpge       49
        //    45: iconst_0       
        //    46: goto            100
        //    49: aload           7
        //    51: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    54: getstatic       net/minecraft/block/material/Material.circuits:Lnet/minecraft/block/material/Material;
        //    57: if_acmpne       71
        //    60: aload_1        
        //    61: getstatic       net/minecraft/init/Blocks.anvil:Lnet/minecraft/block/Block;
        //    64: if_acmpne       71
        //    67: iconst_1       
        //    68: goto            100
        //    71: aload           7
        //    73: invokevirtual   net/minecraft/block/Block.getMaterial:()Lnet/minecraft/block/material/Material;
        //    76: invokevirtual   net/minecraft/block/material/Material.isReplaceable:()Z
        //    79: ifeq            99
        //    82: aload_1        
        //    83: aload_0        
        //    84: aload_2        
        //    85: aload           4
        //    87: aload           6
        //    89: invokevirtual   net/minecraft/block/Block.canReplace:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/item/ItemStack;)Z
        //    92: ifeq            99
        //    95: iconst_1       
        //    96: goto            100
        //    99: iconst_0       
        //   100: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0049 (coming from #0042).
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
    
    public BlockPos getTopSolidOrLiquidBlock(final BlockPos blockPos) {
        final Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        BlockPos blockPos2;
        BlockPos down;
        for (blockPos2 = new BlockPos(blockPos.getX(), chunkFromBlockCoords.getTopFilledSegment() + 16, blockPos.getZ()); blockPos2.getY() >= 0; blockPos2 = down) {
            down = blockPos2.down();
            final Material material = chunkFromBlockCoords.getBlock(down).getMaterial();
            if (material.blocksMovement() && material != Material.leaves) {
                break;
            }
        }
        return blockPos2;
    }
    
    public float getSunBrightness(final float n) {
        return (float)((float)((1.0f - MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.2f), 0.0f, 1.0f)) * (1.0 - this.getRainStrength(n) * 5.0f / 16.0)) * (1.0 - this.getThunderStrength(n) * 5.0f / 16.0)) * 0.8f + 0.2f;
    }
    
    public void forceBlockUpdateTick(final Block block, final BlockPos blockPos, final Random random) {
        this.scheduledUpdatesAreImmediate = true;
        block.updateTick(this, blockPos, this.getBlockState(blockPos), random);
        this.scheduledUpdatesAreImmediate = false;
    }
    
    public WorldSavedData loadItemData(final Class clazz, final String s) {
        return this.mapStorage.loadData(clazz, s);
    }
    
    public List getEntitiesWithinAABB(final Class p0, final AxisAlignedBB p1, final Predicate p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //     4: ldc2_w          2.0
        //     7: dsub           
        //     8: ldc2_w          16.0
        //    11: ddiv           
        //    12: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    15: istore          4
        //    17: aload_2        
        //    18: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //    21: ldc2_w          2.0
        //    24: dadd           
        //    25: ldc2_w          16.0
        //    28: ddiv           
        //    29: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    32: istore          5
        //    34: aload_2        
        //    35: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //    38: ldc2_w          2.0
        //    41: dsub           
        //    42: ldc2_w          16.0
        //    45: ddiv           
        //    46: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    49: istore          6
        //    51: aload_2        
        //    52: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //    55: ldc2_w          2.0
        //    58: dadd           
        //    59: ldc2_w          16.0
        //    62: ddiv           
        //    63: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    66: istore          7
        //    68: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    71: astore          8
        //    73: iload           4
        //    75: istore          9
        //    77: iload           9
        //    79: iload           5
        //    81: if_icmpgt       128
        //    84: iload           6
        //    86: istore          10
        //    88: iload           10
        //    90: iload           7
        //    92: if_icmpgt       122
        //    95: aload_0        
        //    96: iload           9
        //    98: iload           10
        //   100: aload_0        
        //   101: iload           9
        //   103: iload           10
        //   105: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   108: aload_1        
        //   109: aload_2        
        //   110: aload           8
        //   112: aload_3        
        //   113: invokevirtual   net/minecraft/world/chunk/Chunk.getEntitiesOfTypeWithinAAAB:(Ljava/lang/Class;Lnet/minecraft/util/AxisAlignedBB;Ljava/util/List;Lcom/google/common/base/Predicate;)V
        //   116: iinc            10, 1
        //   119: goto            88
        //   122: iinc            9, 1
        //   125: goto            77
        //   128: aload           8
        //   130: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0088 (coming from #0119).
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
    
    public void playSound(final double n, final double n2, final double n3, final String s, final float n4, final float n5, final boolean b) {
    }
    
    public void removeTileEntity(final BlockPos blockPos) {
        final TileEntity tileEntity = this.getTileEntity(blockPos);
        if (tileEntity != null && this.processingLoadedTiles) {
            tileEntity.invalidate();
            this.addedTileEntityList.remove(tileEntity);
        }
        else {
            if (tileEntity != null) {
                this.addedTileEntityList.remove(tileEntity);
                this.loadedTileEntityList.remove(tileEntity);
                this.tickableTileEntities.remove(tileEntity);
            }
            this.getChunkFromBlockCoords(blockPos).removeTileEntity(blockPos);
        }
    }
    
    public void addTileEntities(final Collection collection) {
        if (this.processingLoadedTiles) {
            this.addedTileEntityList.addAll(collection);
        }
        else {
            for (final TileEntity tileEntity : collection) {
                this.loadedTileEntityList.add(tileEntity);
                if (tileEntity instanceof ITickable) {
                    this.tickableTileEntities.add(tileEntity);
                }
            }
        }
    }
    
    public void func_181544_b(final int field_181546_a) {
        this.field_181546_a = field_181546_a;
    }
    
    public boolean isBlockModifiable(final EntityPlayer entityPlayer, final BlockPos blockPos) {
        return true;
    }
    
    public boolean spawnEntityInWorld(final Entity entity) {
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        final boolean forceSpawn = entity.forceSpawn;
        if (entity instanceof EntityPlayer) {}
        if (entity instanceof EntityPlayer) {
            this.playerEntities.add(entity);
            this.updateAllPlayersSleepingFlag();
        }
        this.getChunkFromChunkCoords(floor_double, floor_double2).addEntity(entity);
        this.loadedEntityList.add(entity);
        this.onEntityAdded(entity);
        return true;
    }
    
    public int isBlockIndirectlyGettingPowered(final BlockPos blockPos) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            final int redstonePower = this.getRedstonePower(blockPos.offset(enumFacing), enumFacing);
            if (redstonePower >= 15) {
                return 15;
            }
            if (redstonePower > 0) {}
            int n = 0;
            ++n;
        }
        return 0;
    }
    
    public Vec3 getSkyColor(final Entity entity, final float n) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        final BlockPos blockPos = new BlockPos(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
        final BiomeGenBase biomeGenForCoords = this.getBiomeGenForCoords(blockPos);
        final int skyColorByTemp = biomeGenForCoords.getSkyColorByTemp(biomeGenForCoords.getFloatTemperature(blockPos));
        final float n2 = (skyColorByTemp >> 16 & 0xFF) / 255.0f;
        final float n3 = (skyColorByTemp >> 8 & 0xFF) / 255.0f;
        final float n4 = (skyColorByTemp & 0xFF) / 255.0f;
        float n5 = n2 * clamp_float;
        float n6 = n3 * clamp_float;
        float n7 = n4 * clamp_float;
        final float rainStrength = this.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n8 = (n5 * 0.3f + n6 * 0.59f + n7 * 0.11f) * 0.6f;
            final float n9 = 1.0f - rainStrength * 0.75f;
            n5 = n5 * n9 + n8 * (1.0f - n9);
            n6 = n6 * n9 + n8 * (1.0f - n9);
            n7 = n7 * n9 + n8 * (1.0f - n9);
        }
        final float thunderStrength = this.getThunderStrength(n);
        if (thunderStrength > 0.0f) {
            final float n10 = (n5 * 0.3f + n6 * 0.59f + n7 * 0.11f) * 0.2f;
            final float n11 = 1.0f - thunderStrength * 0.75f;
            n5 = n5 * n11 + n10 * (1.0f - n11);
            n6 = n6 * n11 + n10 * (1.0f - n11);
            n7 = n7 * n11 + n10 * (1.0f - n11);
        }
        if (this.lastLightningBolt > 0) {
            float n12 = this.lastLightningBolt - n;
            if (n12 > 1.0f) {
                n12 = 1.0f;
            }
            final float n13 = n12 * 0.45f;
            n5 = n5 * (1.0f - n13) + 0.8f * n13;
            n6 = n6 * (1.0f - n13) + 0.8f * n13;
            n7 = n7 * (1.0f - n13) + 1.0f * n13;
        }
        return new Vec3(n5, n6, n7);
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final int n) {
        return this.isAreaLoaded(blockPos, n, true);
    }
    
    protected void updateBlocks() {
        this.setActivePlayerChunksAndCheckLight();
    }
    
    public int getStrongPower(final BlockPos blockPos) {
        Math.max(0, this.getStrongPower(blockPos.down(), EnumFacing.DOWN));
        Math.max(0, this.getStrongPower(blockPos.up(), EnumFacing.UP));
        Math.max(0, this.getStrongPower(blockPos.north(), EnumFacing.NORTH));
        Math.max(0, this.getStrongPower(blockPos.south(), EnumFacing.SOUTH));
        Math.max(0, this.getStrongPower(blockPos.west(), EnumFacing.WEST));
        Math.max(0, this.getStrongPower(blockPos.east(), EnumFacing.EAST));
        return 0;
    }
    
    public EntityPlayer getPlayerEntityByName(final String s) {
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(0);
            if (s.equals(entityPlayer.getName())) {
                return entityPlayer;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public void markBlockForUpdate(final BlockPos blockPos) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).markBlockForUpdate(blockPos);
            int n = 0;
            ++n;
        }
    }
    
    public void setInitialSpawnLocation() {
        this.setSpawnPoint(new BlockPos(8, 64, 8));
    }
    
    protected void calculateInitialWeather() {
        if (this.worldInfo.isRaining()) {
            this.rainingStrength = 1.0f;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength = 1.0f;
            }
        }
    }
    
    public boolean handleMaterialAcceleration(final AxisAlignedBB axisAlignedBB, final Material material, final Entity entity) {
        MathHelper.floor_double(axisAlignedBB.minX);
        MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        MathHelper.floor_double(axisAlignedBB.minY);
        MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        MathHelper.floor_double(axisAlignedBB.minZ);
        MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        return false;
    }
    
    public boolean canSeeSky(final BlockPos blockPos) {
        return this.getChunkFromBlockCoords(blockPos).canSeeSky(blockPos);
    }
    
    public void updateAllPlayersSleepingFlag() {
    }
    
    public void setItemData(final String s, final WorldSavedData worldSavedData) {
        this.mapStorage.setData(s, worldSavedData);
    }
    
    public BlockPos getPrecipitationHeight(final BlockPos blockPos) {
        return this.getChunkFromBlockCoords(blockPos).getPrecipitationHeight(blockPos);
    }
    
    public int countEntities(final Class clazz) {
        for (final Entity entity : this.loadedEntityList) {
            if ((!(entity instanceof EntityLiving) || !((EntityLiving)entity).isNoDespawnRequired()) && clazz.isAssignableFrom(((EntityLiving)entity).getClass())) {
                int n = 0;
                ++n;
            }
        }
        return 0;
    }
    
    public boolean isFindingSpawnPoint() {
        return this.findingSpawnPoint;
    }
    
    @Override
    public int getStrongPower(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.getBlockState(blockPos);
        return blockState.getBlock().getStrongPower(this, blockPos, blockState, enumFacing);
    }
    
    protected abstract IChunkProvider createChunkProvider();
    
    public List func_147461_a(final AxisAlignedBB axisAlignedBB) {
        final ArrayList arrayList = Lists.newArrayList();
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double5; j < floor_double6; ++j) {
                if (this.isBlockLoaded(mutableBlockPos.func_181079_c(i, 64, j))) {
                    for (int k = floor_double3 - 1; k < floor_double4; ++k) {
                        mutableBlockPos.func_181079_c(i, k, j);
                        IBlockState blockState;
                        if (i >= -30000000 && i < 30000000 && j >= -30000000 && j < 30000000) {
                            blockState = this.getBlockState(mutableBlockPos);
                        }
                        else {
                            blockState = Blocks.bedrock.getDefaultState();
                        }
                        blockState.getBlock().addCollisionBoxesToList(this, mutableBlockPos, blockState, axisAlignedBB, arrayList, null);
                    }
                }
            }
        }
        return arrayList;
    }
    
    public void updateEntity(final Entity entity) {
        this.updateEntityWithOptionalForce(entity, true);
    }
    
    public int getLightFromNeighborsFor(final EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (this.provider.getHasNoSky() && enumSkyBlock == EnumSkyBlock.SKY) {
            return 0;
        }
        if (blockPos.getY() < 0) {
            blockPos = new BlockPos(blockPos.getX(), 0, blockPos.getZ());
        }
        if (this >= blockPos) {
            return enumSkyBlock.defaultLightValue;
        }
        if (!this.isBlockLoaded(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }
        if (this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int light = this.getLightFor(enumSkyBlock, blockPos.up());
            final int light2 = this.getLightFor(enumSkyBlock, blockPos.east());
            final int light3 = this.getLightFor(enumSkyBlock, blockPos.west());
            final int light4 = this.getLightFor(enumSkyBlock, blockPos.south());
            final int light5 = this.getLightFor(enumSkyBlock, blockPos.north());
            if (light2 > light) {
                light = light2;
            }
            if (light3 > light) {
                light = light3;
            }
            if (light4 > light) {
                light = light4;
            }
            if (light5 > light) {
                light = light5;
            }
            return light;
        }
        return this.getChunkFromBlockCoords(blockPos).getLightFor(enumSkyBlock, blockPos);
    }
    
    public boolean isBlockFullCube(final BlockPos blockPos) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final AxisAlignedBB collisionBoundingBox = blockState.getBlock().getCollisionBoundingBox(this, blockPos, blockState);
        return collisionBoundingBox != null && collisionBoundingBox.getAverageEdgeLength() >= 1.0;
    }
    
    public void playAuxSFX(final int n, final BlockPos blockPos, final int n2) {
        this.playAuxSFXAtEntity(null, n, blockPos, n2);
    }
    
    public int getMoonPhase() {
        return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
    }
    
    public void tick() {
        this.updateWeather();
    }
    
    protected void playMoodSoundAndCheckLight(final int n, final int n2, final Chunk chunk) {
        this.theProfiler.endStartSection("moodSound");
        if (this.ambientTickCountdown == 0 && !this.isRemote) {
            this.updateLCG = this.updateLCG * 3 + 1013904223;
            final int n3 = this.updateLCG >> 2;
            final int n4 = n3 & 0xF;
            final int n5 = n3 >> 8 & 0xF;
            final int n6 = n3 >> 16 & 0xFF;
            final BlockPos blockPos = new BlockPos(n4, n6, n5);
            final Block block = chunk.getBlock(blockPos);
            final int n7 = n4 + n;
            final int n8 = n5 + n2;
            if (block.getMaterial() == Material.air && this.getLight(blockPos) <= this.rand.nextInt(8) && this.getLightFor(EnumSkyBlock.SKY, blockPos) <= 0) {
                final EntityPlayer closestPlayer = this.getClosestPlayer(n7 + 0.5, n6 + 0.5, n8 + 0.5, 8.0);
                if (closestPlayer != null && closestPlayer.getDistanceSq(n7 + 0.5, n6 + 0.5, n8 + 0.5) > 4.0) {
                    this.playSoundEffect(n7 + 0.5, n6 + 0.5, n8 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.rand.nextFloat() * 0.2f);
                    this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
                }
            }
        }
        this.theProfiler.endStartSection("checkLight");
        chunk.enqueueRelightChecks();
    }
    
    public BlockPos getStrongholdPos(final String s, final BlockPos blockPos) {
        return this.getChunkProvider().getStrongholdGen(this, s, blockPos);
    }
    
    public void updateEntities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //     4: ldc_w           "entities"
        //     7: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    10: aload_0        
        //    11: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //    14: ldc_w           "global"
        //    17: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    20: iconst_0       
        //    21: aload_0        
        //    22: getfield        net/minecraft/world/World.weatherEffects:Ljava/util/List;
        //    25: invokeinterface java/util/List.size:()I
        //    30: if_icmpge       145
        //    33: aload_0        
        //    34: getfield        net/minecraft/world/World.weatherEffects:Ljava/util/List;
        //    37: iconst_0       
        //    38: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    43: checkcast       Lnet/minecraft/entity/Entity;
        //    46: astore_2       
        //    47: aload_2        
        //    48: dup            
        //    49: getfield        net/minecraft/entity/Entity.ticksExisted:I
        //    52: iconst_1       
        //    53: iadd           
        //    54: putfield        net/minecraft/entity/Entity.ticksExisted:I
        //    57: aload_2        
        //    58: invokevirtual   net/minecraft/entity/Entity.onUpdate:()V
        //    61: goto            118
        //    64: astore_3       
        //    65: aload_3        
        //    66: ldc_w           "Ticking entity"
        //    69: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //    72: astore          4
        //    74: aload           4
        //    76: ldc_w           "Entity being ticked"
        //    79: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //    82: astore          5
        //    84: aload_2        
        //    85: ifnonnull       102
        //    88: aload           5
        //    90: ldc_w           "Entity"
        //    93: ldc_w           "~~NULL~~"
        //    96: invokevirtual   net/minecraft/crash/CrashReportCategory.addCrashSection:(Ljava/lang/String;Ljava/lang/Object;)V
        //    99: goto            108
        //   102: aload_2        
        //   103: aload           5
        //   105: invokevirtual   net/minecraft/entity/Entity.addEntityCrashInfo:(Lnet/minecraft/crash/CrashReportCategory;)V
        //   108: new             Lnet/minecraft/util/ReportedException;
        //   111: dup            
        //   112: aload           4
        //   114: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   117: athrow         
        //   118: aload_2        
        //   119: getfield        net/minecraft/entity/Entity.isDead:Z
        //   122: ifeq            139
        //   125: aload_0        
        //   126: getfield        net/minecraft/world/World.weatherEffects:Ljava/util/List;
        //   129: iconst_0       
        //   130: iinc            1, -1
        //   133: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   138: pop            
        //   139: iinc            1, 1
        //   142: goto            20
        //   145: aload_0        
        //   146: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   149: ldc_w           "remove"
        //   152: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   155: aload_0        
        //   156: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //   159: aload_0        
        //   160: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   163: invokeinterface java/util/List.removeAll:(Ljava/util/Collection;)Z
        //   168: pop            
        //   169: iconst_0       
        //   170: aload_0        
        //   171: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   174: invokeinterface java/util/List.size:()I
        //   179: if_icmpge       235
        //   182: aload_0        
        //   183: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   186: iconst_0       
        //   187: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   192: checkcast       Lnet/minecraft/entity/Entity;
        //   195: astore_2       
        //   196: aload_2        
        //   197: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   200: istore_3       
        //   201: aload_2        
        //   202: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   205: istore          4
        //   207: aload_2        
        //   208: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   211: ifeq            229
        //   214: aload_0        
        //   215: iload_3        
        //   216: iload           4
        //   218: aload_0        
        //   219: iload_3        
        //   220: iload           4
        //   222: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   225: aload_2        
        //   226: invokevirtual   net/minecraft/world/chunk/Chunk.removeEntity:(Lnet/minecraft/entity/Entity;)V
        //   229: iinc            1, 1
        //   232: goto            169
        //   235: iconst_0       
        //   236: aload_0        
        //   237: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   240: invokeinterface java/util/List.size:()I
        //   245: if_icmpge       271
        //   248: aload_0        
        //   249: aload_0        
        //   250: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   253: iconst_0       
        //   254: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   259: checkcast       Lnet/minecraft/entity/Entity;
        //   262: invokevirtual   net/minecraft/world/World.onEntityRemoved:(Lnet/minecraft/entity/Entity;)V
        //   265: iinc            1, 1
        //   268: goto            235
        //   271: aload_0        
        //   272: getfield        net/minecraft/world/World.unloadedEntityList:Ljava/util/List;
        //   275: invokeinterface java/util/List.clear:()V
        //   280: aload_0        
        //   281: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   284: ldc_w           "regular"
        //   287: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   290: iconst_0       
        //   291: aload_0        
        //   292: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //   295: invokeinterface java/util/List.size:()I
        //   300: if_icmpge       511
        //   303: aload_0        
        //   304: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //   307: iconst_0       
        //   308: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   313: checkcast       Lnet/minecraft/entity/Entity;
        //   316: astore_2       
        //   317: aload_2        
        //   318: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   321: ifnull          361
        //   324: aload_2        
        //   325: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   328: getfield        net/minecraft/entity/Entity.isDead:Z
        //   331: ifne            348
        //   334: aload_2        
        //   335: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   338: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   341: aload_2        
        //   342: if_acmpne       348
        //   345: goto            505
        //   348: aload_2        
        //   349: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   352: aconst_null    
        //   353: putfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   356: aload_2        
        //   357: aconst_null    
        //   358: putfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   361: aload_0        
        //   362: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   365: ldc_w           "tick"
        //   368: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   371: aload_2        
        //   372: getfield        net/minecraft/entity/Entity.isDead:Z
        //   375: ifne            422
        //   378: aload_0        
        //   379: aload_2        
        //   380: invokevirtual   net/minecraft/world/World.updateEntity:(Lnet/minecraft/entity/Entity;)V
        //   383: goto            422
        //   386: astore_3       
        //   387: aload_3        
        //   388: ldc_w           "Ticking entity"
        //   391: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   394: astore          4
        //   396: aload           4
        //   398: ldc_w           "Entity being ticked"
        //   401: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   404: astore          5
        //   406: aload_2        
        //   407: aload           5
        //   409: invokevirtual   net/minecraft/entity/Entity.addEntityCrashInfo:(Lnet/minecraft/crash/CrashReportCategory;)V
        //   412: new             Lnet/minecraft/util/ReportedException;
        //   415: dup            
        //   416: aload           4
        //   418: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   421: athrow         
        //   422: aload_0        
        //   423: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   426: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   429: aload_0        
        //   430: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   433: ldc_w           "remove"
        //   436: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   439: aload_2        
        //   440: getfield        net/minecraft/entity/Entity.isDead:Z
        //   443: ifeq            498
        //   446: aload_2        
        //   447: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   450: istore_3       
        //   451: aload_2        
        //   452: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   455: istore          4
        //   457: aload_2        
        //   458: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //   461: ifeq            479
        //   464: aload_0        
        //   465: iload_3        
        //   466: iload           4
        //   468: aload_0        
        //   469: iload_3        
        //   470: iload           4
        //   472: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //   475: aload_2        
        //   476: invokevirtual   net/minecraft/world/chunk/Chunk.removeEntity:(Lnet/minecraft/entity/Entity;)V
        //   479: aload_0        
        //   480: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //   483: iconst_0       
        //   484: iinc            1, -1
        //   487: invokeinterface java/util/List.remove:(I)Ljava/lang/Object;
        //   492: pop            
        //   493: aload_0        
        //   494: aload_2        
        //   495: invokevirtual   net/minecraft/world/World.onEntityRemoved:(Lnet/minecraft/entity/Entity;)V
        //   498: aload_0        
        //   499: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   502: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   505: iinc            1, 1
        //   508: goto            290
        //   511: aload_0        
        //   512: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   515: ldc_w           "blockEntities"
        //   518: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   521: aload_0        
        //   522: iconst_1       
        //   523: putfield        net/minecraft/world/World.processingLoadedTiles:Z
        //   526: aload_0        
        //   527: getfield        net/minecraft/world/World.tickableTileEntities:Ljava/util/List;
        //   530: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   535: astore_1       
        //   536: aload_1        
        //   537: invokeinterface java/util/Iterator.hasNext:()Z
        //   542: ifeq            696
        //   545: aload_1        
        //   546: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   551: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //   554: astore_2       
        //   555: aload_2        
        //   556: invokevirtual   net/minecraft/tileentity/TileEntity.isInvalid:()Z
        //   559: ifne            643
        //   562: aload_2        
        //   563: invokevirtual   net/minecraft/tileentity/TileEntity.hasWorldObj:()Z
        //   566: ifeq            643
        //   569: aload_2        
        //   570: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   573: astore_3       
        //   574: aload_0        
        //   575: aload_3        
        //   576: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //   579: ifeq            643
        //   582: aload_0        
        //   583: getfield        net/minecraft/world/World.worldBorder:Lnet/minecraft/world/border/WorldBorder;
        //   586: aload_3        
        //   587: invokevirtual   net/minecraft/world/border/WorldBorder.contains:(Lnet/minecraft/util/BlockPos;)Z
        //   590: ifeq            643
        //   593: aload_2        
        //   594: checkcast       Lnet/minecraft/util/ITickable;
        //   597: invokeinterface net/minecraft/util/ITickable.update:()V
        //   602: goto            643
        //   605: astore          4
        //   607: aload           4
        //   609: ldc_w           "Ticking block entity"
        //   612: invokestatic    net/minecraft/crash/CrashReport.makeCrashReport:(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/crash/CrashReport;
        //   615: astore          5
        //   617: aload           5
        //   619: ldc_w           "Block entity being ticked"
        //   622: invokevirtual   net/minecraft/crash/CrashReport.makeCategory:(Ljava/lang/String;)Lnet/minecraft/crash/CrashReportCategory;
        //   625: astore          6
        //   627: aload_2        
        //   628: aload           6
        //   630: invokevirtual   net/minecraft/tileentity/TileEntity.addInfoToCrashReport:(Lnet/minecraft/crash/CrashReportCategory;)V
        //   633: new             Lnet/minecraft/util/ReportedException;
        //   636: dup            
        //   637: aload           5
        //   639: invokespecial   net/minecraft/util/ReportedException.<init>:(Lnet/minecraft/crash/CrashReport;)V
        //   642: athrow         
        //   643: aload_2        
        //   644: invokevirtual   net/minecraft/tileentity/TileEntity.isInvalid:()Z
        //   647: ifeq            693
        //   650: aload_1        
        //   651: invokeinterface java/util/Iterator.remove:()V
        //   656: aload_0        
        //   657: getfield        net/minecraft/world/World.loadedTileEntityList:Ljava/util/List;
        //   660: aload_2        
        //   661: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //   666: pop            
        //   667: aload_0        
        //   668: aload_2        
        //   669: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   672: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //   675: ifeq            693
        //   678: aload_0        
        //   679: aload_2        
        //   680: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   683: invokevirtual   net/minecraft/world/World.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   686: aload_2        
        //   687: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   690: invokevirtual   net/minecraft/world/chunk/Chunk.removeTileEntity:(Lnet/minecraft/util/BlockPos;)V
        //   693: goto            536
        //   696: aload_0        
        //   697: iconst_0       
        //   698: putfield        net/minecraft/world/World.processingLoadedTiles:Z
        //   701: aload_0        
        //   702: getfield        net/minecraft/world/World.tileEntitiesToBeRemoved:Ljava/util/List;
        //   705: invokeinterface java/util/List.isEmpty:()Z
        //   710: ifne            750
        //   713: aload_0        
        //   714: getfield        net/minecraft/world/World.tickableTileEntities:Ljava/util/List;
        //   717: aload_0        
        //   718: getfield        net/minecraft/world/World.tileEntitiesToBeRemoved:Ljava/util/List;
        //   721: invokeinterface java/util/List.removeAll:(Ljava/util/Collection;)Z
        //   726: pop            
        //   727: aload_0        
        //   728: getfield        net/minecraft/world/World.loadedTileEntityList:Ljava/util/List;
        //   731: aload_0        
        //   732: getfield        net/minecraft/world/World.tileEntitiesToBeRemoved:Ljava/util/List;
        //   735: invokeinterface java/util/List.removeAll:(Ljava/util/Collection;)Z
        //   740: pop            
        //   741: aload_0        
        //   742: getfield        net/minecraft/world/World.tileEntitiesToBeRemoved:Ljava/util/List;
        //   745: invokeinterface java/util/List.clear:()V
        //   750: aload_0        
        //   751: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   754: ldc_w           "pendingBlockEntities"
        //   757: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   760: aload_0        
        //   761: getfield        net/minecraft/world/World.addedTileEntityList:Ljava/util/List;
        //   764: invokeinterface java/util/List.isEmpty:()Z
        //   769: ifne            875
        //   772: iconst_0       
        //   773: aload_0        
        //   774: getfield        net/minecraft/world/World.addedTileEntityList:Ljava/util/List;
        //   777: invokeinterface java/util/List.size:()I
        //   782: if_icmpge       866
        //   785: aload_0        
        //   786: getfield        net/minecraft/world/World.addedTileEntityList:Ljava/util/List;
        //   789: iconst_0       
        //   790: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   795: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //   798: astore_3       
        //   799: aload_3        
        //   800: invokevirtual   net/minecraft/tileentity/TileEntity.isInvalid:()Z
        //   803: ifne            860
        //   806: aload_0        
        //   807: getfield        net/minecraft/world/World.loadedTileEntityList:Ljava/util/List;
        //   810: aload_3        
        //   811: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   816: ifne            825
        //   819: aload_0        
        //   820: aload_3        
        //   821: invokevirtual   net/minecraft/world/World.addTileEntity:(Lnet/minecraft/tileentity/TileEntity;)Z
        //   824: pop            
        //   825: aload_0        
        //   826: aload_3        
        //   827: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   830: invokevirtual   net/minecraft/world/World.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //   833: ifeq            852
        //   836: aload_0        
        //   837: aload_3        
        //   838: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   841: invokevirtual   net/minecraft/world/World.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   844: aload_3        
        //   845: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   848: aload_3        
        //   849: invokevirtual   net/minecraft/world/chunk/Chunk.addTileEntity:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/tileentity/TileEntity;)V
        //   852: aload_0        
        //   853: aload_3        
        //   854: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/BlockPos;
        //   857: invokevirtual   net/minecraft/world/World.markBlockForUpdate:(Lnet/minecraft/util/BlockPos;)V
        //   860: iinc            2, 1
        //   863: goto            772
        //   866: aload_0        
        //   867: getfield        net/minecraft/world/World.addedTileEntityList:Ljava/util/List;
        //   870: invokeinterface java/util/List.clear:()V
        //   875: aload_0        
        //   876: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   879: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   882: aload_0        
        //   883: getfield        net/minecraft/world/World.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   886: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   889: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0479 (coming from #0476).
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
    
    public boolean isAreaLoaded(final StructureBoundingBox structureBoundingBox) {
        return this.isAreaLoaded(structureBoundingBox, true);
    }
    
    public boolean isSpawnChunk(final int n, final int n2) {
        final BlockPos spawnPoint = this.getSpawnPoint();
        final int n3 = n * 16 + 8 - spawnPoint.getX();
        final int n4 = n2 * 16 + 8 - spawnPoint.getZ();
        return n3 >= -128 && n3 <= 128 && n4 >= -128 && n4 <= 128;
    }
    
    public void playAuxSFXAtEntity(final EntityPlayer entityPlayer, final int n, final BlockPos blockPos, final int n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).playAuxSFX(entityPlayer, n, blockPos, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.provider.getWorldChunkManager();
    }
    
    public void setTileEntity(final BlockPos pos, final TileEntity tileEntity) {
        if (tileEntity != null && !tileEntity.isInvalid()) {
            if (this.processingLoadedTiles) {
                tileEntity.setPos(pos);
                final Iterator<TileEntity> iterator = (Iterator<TileEntity>)this.addedTileEntityList.iterator();
                while (iterator.hasNext()) {
                    final TileEntity tileEntity2 = iterator.next();
                    if (tileEntity2.getPos().equals(pos)) {
                        tileEntity2.invalidate();
                        iterator.remove();
                    }
                }
                this.addedTileEntityList.add(tileEntity);
            }
            else {
                this.addTileEntity(tileEntity);
                this.getChunkFromBlockCoords(pos).addTileEntity(pos, tileEntity);
            }
        }
    }
    
    public WorldBorder getWorldBorder() {
        return this.worldBorder;
    }
    
    public Random setRandomSeed(final int n, final int n2, final int n3) {
        this.rand.setSeed(n * 341873128712L + n2 * 132897987541L + this.getWorldInfo().getSeed() + n3);
        return this.rand;
    }
    
    public float getLightBrightness(final BlockPos blockPos) {
        return this.provider.getLightBrightnessTable()[this.getLightFromNeighbors(blockPos)];
    }
    
    public void loadEntities(final Collection collection) {
        this.loadedEntityList.addAll(collection);
        final Iterator<Entity> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.onEntityAdded(iterator.next());
        }
    }
    
    public float getBlockDensity(final Vec3 vec3, final AxisAlignedBB axisAlignedBB) {
        final double n = 1.0 / ((axisAlignedBB.maxX - axisAlignedBB.minX) * 2.0 + 1.0);
        final double n2 = 1.0 / ((axisAlignedBB.maxY - axisAlignedBB.minY) * 2.0 + 1.0);
        final double n3 = 1.0 / ((axisAlignedBB.maxZ - axisAlignedBB.minZ) * 2.0 + 1.0);
        final double n4 = (1.0 - Math.floor(1.0 / n) * n) / 2.0;
        final double n5 = (1.0 - Math.floor(1.0 / n3) * n3) / 2.0;
        if (n >= 0.0 && n2 >= 0.0 && n3 >= 0.0) {
            for (float n6 = 0.0f; n6 <= 1.0f; n6 += (float)n) {
                for (float n7 = 0.0f; n7 <= 1.0f; n7 += (float)n2) {
                    for (float n8 = 0.0f; n8 <= 1.0f; n8 += (float)n3) {
                        if (this.rayTraceBlocks(new Vec3(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * n6 + n4, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * n7, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * n8 + n5), vec3) == null) {
                            int n9 = 0;
                            ++n9;
                        }
                        int n10 = 0;
                        ++n10;
                    }
                }
            }
            return 0 / (float)0;
        }
        return 0.0f;
    }
    
    public DifficultyInstance getDifficultyForLocation(final BlockPos blockPos) {
        long inhabitedTime = 0L;
        float currentMoonPhaseFactor = 0.0f;
        if (this.isBlockLoaded(blockPos)) {
            currentMoonPhaseFactor = this.getCurrentMoonPhaseFactor();
            inhabitedTime = this.getChunkFromBlockCoords(blockPos).getInhabitedTime();
        }
        return new DifficultyInstance(this.getDifficulty(), this.getWorldTime(), inhabitedTime, currentMoonPhaseFactor);
    }
    
    public boolean addTileEntity(final TileEntity tileEntity) {
        final boolean add = this.loadedTileEntityList.add(tileEntity);
        if (add && tileEntity instanceof ITickable) {
            this.tickableTileEntities.add(tileEntity);
        }
        return add;
    }
    
    public int getLight(BlockPos blockPos) {
        if (blockPos.getY() < 0) {
            return 0;
        }
        if (blockPos.getY() >= 256) {
            blockPos = new BlockPos(blockPos.getX(), 255, blockPos.getZ());
        }
        return this.getChunkFromBlockCoords(blockPos).getLightSubtracted(blockPos, 0);
    }
    
    public int func_181545_F() {
        return this.field_181546_a;
    }
    
    public void setSkylightSubtracted(final int skylightSubtracted) {
        this.skylightSubtracted = skylightSubtracted;
    }
    
    public int calculateSkylightSubtracted(final float n) {
        return (int)((1.0f - (float)((float)((1.0f - MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f), 0.0f, 1.0f)) * (1.0 - this.getRainStrength(n) * 5.0f / 16.0)) * (1.0 - this.getThunderStrength(n) * 5.0f / 16.0))) * 11.0f);
    }
    
    public int getRedstonePower(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        return block.isNormalCube() ? this.getStrongPower(blockPos) : block.getWeakPower(this, blockPos, blockState, enumFacing);
    }
    
    public boolean addWeatherEffect(final Entity entity) {
        this.weatherEffects.add(entity);
        return true;
    }
    
    public int getLightFromNeighbors(final BlockPos blockPos) {
        return this.getLight(blockPos, true);
    }
    
    public float getCelestialAngleRadians(final float n) {
        return this.getCelestialAngle(n) * 3.1415927f * 2.0f;
    }
    
    private int getRawLight(final BlockPos blockPos, final EnumSkyBlock enumSkyBlock) {
        if (enumSkyBlock == EnumSkyBlock.SKY && this.canSeeSky(blockPos)) {
            return 15;
        }
        final Block block = this.getBlockState(blockPos).getBlock();
        int n = (enumSkyBlock == EnumSkyBlock.SKY) ? 0 : block.getLightValue();
        block.getLightOpacity();
        if (n >= 14) {
            return n;
        }
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final int n2 = this.getLightFor(enumSkyBlock, blockPos.offset(values[0])) - 1;
            if (n2 > n) {
                n = n2;
            }
            if (n >= 14) {
                return n;
            }
            int n3 = 0;
            ++n3;
        }
        return n;
    }
    
    public void scheduleBlockUpdate(final BlockPos blockPos, final Block block, final int n, final int n2) {
    }
    
    public Entity getEntityByID(final int n) {
        return (Entity)this.entitiesById.lookup(n);
    }
    
    public String getDebugLoadedEntities() {
        return "All: " + this.loadedEntityList.size();
    }
    
    public MapStorage getMapStorage() {
        return this.mapStorage;
    }
    
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport crashReport) {
        final CrashReportCategory categoryDepth = crashReport.makeCategoryDepth("Affected level", 1);
        categoryDepth.addCrashSection("Level name", (this.worldInfo == null) ? "????" : this.worldInfo.getWorldName());
        categoryDepth.addCrashSectionCallable("All players", new Callable(this) {
            final World this$0;
            
            @Override
            public String call() {
                return this.this$0.playerEntities.size() + " total; " + this.this$0.playerEntities.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        categoryDepth.addCrashSectionCallable("Chunk stats", new Callable(this) {
            final World this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() {
                return this.this$0.chunkProvider.makeString();
            }
        });
        this.worldInfo.addToCrashReport(categoryDepth);
        return categoryDepth;
    }
    
    public boolean setBlockToAir(final BlockPos blockPos) {
        return this.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
    }
    
    public Vec3 getCloudColour(final float n) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        float n2 = (this.cloudColour >> 16 & 0xFFL) / 255.0f;
        float n3 = (this.cloudColour >> 8 & 0xFFL) / 255.0f;
        float n4 = (this.cloudColour & 0xFFL) / 255.0f;
        final float rainStrength = this.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n5 = (n2 * 0.3f + n3 * 0.59f + n4 * 0.11f) * 0.6f;
            final float n6 = 1.0f - rainStrength * 0.95f;
            n2 = n2 * n6 + n5 * (1.0f - n6);
            n3 = n3 * n6 + n5 * (1.0f - n6);
            n4 = n4 * n6 + n5 * (1.0f - n6);
        }
        float n7 = n2 * (clamp_float * 0.9f + 0.1f);
        float n8 = n3 * (clamp_float * 0.9f + 0.1f);
        float n9 = n4 * (clamp_float * 0.85f + 0.15f);
        final float thunderStrength = this.getThunderStrength(n);
        if (thunderStrength > 0.0f) {
            final float n10 = (n7 * 0.3f + n8 * 0.59f + n9 * 0.11f) * 0.2f;
            final float n11 = 1.0f - thunderStrength * 0.95f;
            n7 = n7 * n11 + n10 * (1.0f - n11);
            n8 = n8 * n11 + n10 * (1.0f - n11);
            n9 = n9 * n11 + n10 * (1.0f - n11);
        }
        return new Vec3(n7, n8, n9);
    }
    
    public boolean isBlockPowered(final BlockPos blockPos) {
        return this.getRedstonePower(blockPos.down(), EnumFacing.DOWN) > 0 || this.getRedstonePower(blockPos.up(), EnumFacing.UP) > 0 || this.getRedstonePower(blockPos.north(), EnumFacing.NORTH) > 0 || this.getRedstonePower(blockPos.south(), EnumFacing.SOUTH) > 0 || this.getRedstonePower(blockPos.west(), EnumFacing.WEST) > 0 || this.getRedstonePower(blockPos.east(), EnumFacing.EAST) > 0;
    }
    
    public VillageCollection getVillageCollection() {
        return this.villageCollectionObj;
    }
    
    public int getChunksLowestHorizon(final int n, final int n2) {
        if (n >= -30000000 && n2 >= -30000000 && n < 30000000 && n2 < 30000000) {
            n >> 4;
            n2 >> 4;
            return 0;
        }
        return this.func_181545_F() + 1;
    }
    
    public boolean isBlockLoaded(final BlockPos blockPos, final boolean b) {
        return this < blockPos && this.isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4, b);
    }
    
    public void updateBlockTick(final BlockPos blockPos, final Block block, final int n, final int n2) {
    }
    
    public GameRules getGameRules() {
        return this.worldInfo.getGameRulesInstance();
    }
    
    public EnumDifficulty getDifficulty() {
        return this.getWorldInfo().getDifficulty();
    }
    
    public void playSoundToNearExcept(final EntityPlayer entityPlayer, final String s, final float n, final float n2) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).playSoundToNearExcept(entityPlayer, s, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, n, n2);
            int n3 = 0;
            ++n3;
        }
    }
    
    public void unloadEntities(final Collection collection) {
        this.unloadedEntityList.addAll(collection);
    }
    
    public List func_175712_a(final StructureBoundingBox structureBoundingBox, final boolean b) {
        return null;
    }
    
    public List getPendingBlockUpdates(final Chunk chunk, final boolean b) {
        return null;
    }
    
    public Scoreboard getScoreboard() {
        return this.worldScoreboard;
    }
    
    public List getCollidingBoundingBoxes(final Entity entity, final AxisAlignedBB axisAlignedBB) {
        final ArrayList arrayList = Lists.newArrayList();
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final WorldBorder worldBorder = this.getWorldBorder();
        final boolean outsideBorder = entity.isOutsideBorder();
        final boolean insideBorder = this.isInsideBorder(worldBorder, entity);
        final IBlockState defaultState = Blocks.stone.getDefaultState();
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double5; j < floor_double6; ++j) {
                if (this.isBlockLoaded(mutableBlockPos.func_181079_c(i, 64, j))) {
                    for (int k = floor_double3 - 1; k < floor_double4; ++k) {
                        mutableBlockPos.func_181079_c(i, k, j);
                        if (outsideBorder && insideBorder) {
                            entity.setOutsideBorder(false);
                        }
                        else if (!outsideBorder && !insideBorder) {
                            entity.setOutsideBorder(true);
                        }
                        IBlockState blockState = defaultState;
                        if (worldBorder.contains(mutableBlockPos) || !insideBorder) {
                            blockState = this.getBlockState(mutableBlockPos);
                        }
                        blockState.getBlock().addCollisionBoxesToList(this, mutableBlockPos, blockState, axisAlignedBB, arrayList, entity);
                    }
                }
            }
        }
        final double n = 0.25;
        final List entitiesWithinAABBExcludingEntity = this.getEntitiesWithinAABBExcludingEntity(entity, axisAlignedBB.expand(n, n, n));
        while (0 < entitiesWithinAABBExcludingEntity.size()) {
            if (entity.riddenByEntity != entitiesWithinAABBExcludingEntity && entity.ridingEntity != entitiesWithinAABBExcludingEntity) {
                final AxisAlignedBB collisionBoundingBox = entitiesWithinAABBExcludingEntity.get(0).getCollisionBoundingBox();
                if (collisionBoundingBox != null && collisionBoundingBox.intersectsWith(axisAlignedBB)) {
                    arrayList.add(collisionBoundingBox);
                }
                final AxisAlignedBB collisionBox = entity.getCollisionBox(entitiesWithinAABBExcludingEntity.get(0));
                if (collisionBox != null && collisionBox.intersectsWith(axisAlignedBB)) {
                    arrayList.add(collisionBox);
                }
            }
            int n2 = 0;
            ++n2;
        }
        return arrayList;
    }
    
    public int getLight(BlockPos blockPos, final boolean b) {
        if (blockPos.getX() < -30000000 || blockPos.getZ() < -30000000 || blockPos.getX() >= 30000000 || blockPos.getZ() >= 30000000) {
            return 15;
        }
        if (b && this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int light = this.getLight(blockPos.up(), false);
            final int light2 = this.getLight(blockPos.east(), false);
            final int light3 = this.getLight(blockPos.west(), false);
            final int light4 = this.getLight(blockPos.south(), false);
            final int light5 = this.getLight(blockPos.north(), false);
            if (light2 > light) {
                light = light2;
            }
            if (light3 > light) {
                light = light3;
            }
            if (light4 > light) {
                light = light4;
            }
            if (light5 > light) {
                light = light5;
            }
            return light;
        }
        if (blockPos.getY() < 0) {
            return 0;
        }
        if (blockPos.getY() >= 256) {
            blockPos = new BlockPos(blockPos.getX(), 255, blockPos.getZ());
        }
        return this.getChunkFromBlockCoords(blockPos).getLightSubtracted(blockPos, this.skylightSubtracted);
    }
    
    public boolean canBlockSeeSky(final BlockPos blockPos) {
        if (blockPos.getY() >= this.func_181545_F()) {
            return this.canSeeSky(blockPos);
        }
        final BlockPos blockPos2 = new BlockPos(blockPos.getX(), this.func_181545_F(), blockPos.getZ());
        if (!this.canSeeSky(blockPos2)) {
            return false;
        }
        for (BlockPos blockPos3 = blockPos2.down(); blockPos3.getY() > blockPos.getY(); blockPos3 = blockPos3.down()) {
            final Block block = this.getBlockState(blockPos3).getBlock();
            if (block.getLightOpacity() > 0 && !block.getMaterial().isLiquid()) {
                return false;
            }
        }
        return true;
    }
    
    public Explosion createExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b) {
        return this.newExplosion(entity, n, n2, n3, n4, false, b);
    }
    
    public boolean isBlockLoaded(final BlockPos blockPos) {
        return this.isBlockLoaded(blockPos, true);
    }
    
    public Entity findNearestEntityWithinAABB(final Class clazz, final AxisAlignedBB axisAlignedBB, final Entity entity) {
        final List entitiesWithinAABB = this.getEntitiesWithinAABB(clazz, axisAlignedBB);
        Entity entity2 = null;
        double n = Double.MAX_VALUE;
        while (0 < entitiesWithinAABB.size()) {
            final Entity entity3 = entitiesWithinAABB.get(0);
            if (entity3 != entity && EntitySelectors.NOT_SPECTATING.apply((Object)entity3)) {
                final double distanceSqToEntity = entity.getDistanceSqToEntity(entity3);
                if (distanceSqToEntity <= n) {
                    entity2 = entity3;
                    n = distanceSqToEntity;
                }
            }
            int n2 = 0;
            ++n2;
        }
        return entity2;
    }
    
    public boolean destroyBlock(final BlockPos blockPos, final boolean b) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block.getMaterial() == Material.air) {
            return false;
        }
        this.playAuxSFX(2001, blockPos, Block.getStateId(blockState));
        if (b) {
            block.dropBlockAsItem(this, blockPos, blockState, 0);
        }
        return this.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
    }
    
    public void playRecord(final BlockPos blockPos, final String s) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).playRecord(s, blockPos);
            int n = 0;
            ++n;
        }
    }
    
    public int getUniqueDataId(final String s) {
        return this.mapStorage.getUniqueDataId(s);
    }
    
    public boolean isBlockTickPending(final BlockPos blockPos, final Block block) {
        return false;
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 vec3, final Vec3 vec4) {
        return this.rayTraceBlocks(vec3, vec4, false, false, false);
    }
    
    public int getHeight() {
        return 256;
    }
    
    public void notifyLightSet(final BlockPos blockPos) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).notifyLightSet(blockPos);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos) {
        if (this >= blockPos) {
            return null;
        }
        TileEntity tileEntity = null;
        int n = 0;
        if (this.processingLoadedTiles) {
            while (0 < this.addedTileEntityList.size()) {
                final TileEntity tileEntity2 = this.addedTileEntityList.get(0);
                if (!tileEntity2.isInvalid() && tileEntity2.getPos().equals(blockPos)) {
                    tileEntity = tileEntity2;
                    break;
                }
                ++n;
            }
        }
        if (tileEntity == null) {
            tileEntity = this.getChunkFromBlockCoords(blockPos).getTileEntity(blockPos, Chunk.EnumCreateEntityType.IMMEDIATE);
        }
        if (tileEntity == null) {
            while (0 < this.addedTileEntityList.size()) {
                final TileEntity tileEntity3 = this.addedTileEntityList.get(0);
                if (!tileEntity3.isInvalid() && tileEntity3.getPos().equals(blockPos)) {
                    tileEntity = tileEntity3;
                    break;
                }
                ++n;
            }
        }
        return tileEntity;
    }
    
    public void notifyNeighborsRespectDebug(final BlockPos blockPos, final Block block) {
        if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            this.notifyNeighborsOfStateChange(blockPos, block);
        }
    }
    
    public void initialize(final WorldSettings worldSettings) {
        this.worldInfo.setServerInitialized(true);
    }
    
    public List getEntitiesWithinAABB(final Class clazz, final AxisAlignedBB axisAlignedBB) {
        return this.getEntitiesWithinAABB(clazz, axisAlignedBB, EntitySelectors.NOT_SPECTATING);
    }
    
    public Chunk getChunkFromChunkCoords(final int n, final int n2) {
        return this.chunkProvider.provideChunk(n, n2);
    }
    
    public boolean tickUpdates(final boolean b) {
        return false;
    }
    
    public List getLoadedEntityList() {
        return this.loadedEntityList;
    }
    
    public void sendQuittingDisconnectingPacket() {
    }
    
    public void addBlockEvent(final BlockPos blockPos, final Block block, final int n, final int n2) {
        block.onBlockEventReceived(this, blockPos, this.getBlockState(blockPos), n, n2);
    }
    
    public boolean isInsideBorder(final WorldBorder worldBorder, final Entity entity) {
        final double minX = worldBorder.minX();
        final double minZ = worldBorder.minZ();
        final double maxX = worldBorder.maxX();
        final double maxZ = worldBorder.maxZ();
        double n;
        double n2;
        double n3;
        double n4;
        if (entity.isOutsideBorder()) {
            n = minX + 1.0;
            n2 = minZ + 1.0;
            n3 = maxX - 1.0;
            n4 = maxZ - 1.0;
        }
        else {
            n = minX - 1.0;
            n2 = minZ - 1.0;
            n3 = maxX + 1.0;
            n4 = maxZ + 1.0;
        }
        return entity.posX > n && entity.posX < n3 && entity.posZ > n2 && entity.posZ < n4;
    }
    
    public EntityPlayer getClosestPlayerToEntity(final Entity entity, final double n) {
        return this.getClosestPlayer(entity.posX, entity.posY, entity.posZ, n);
    }
    
    public EntityPlayer getPlayerEntityByUUID(final UUID uuid) {
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(0);
            if (uuid.equals(entityPlayer.getUniqueID())) {
                return entityPlayer;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    protected void onEntityRemoved(final Entity entity) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).onEntityRemoved(entity);
            int n = 0;
            ++n;
        }
    }
    
    public boolean canLightningStrike(final BlockPos p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifle            6
        //     4: iconst_0       
        //     5: ireturn        
        //     6: aload_0        
        //     7: aload_1        
        //     8: invokevirtual   net/minecraft/world/World.canSeeSky:(Lnet/minecraft/util/BlockPos;)Z
        //    11: ifne            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_0        
        //    17: aload_1        
        //    18: invokevirtual   net/minecraft/world/World.getPrecipitationHeight:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/util/BlockPos;
        //    21: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    24: aload_1        
        //    25: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    28: if_icmple       33
        //    31: iconst_0       
        //    32: ireturn        
        //    33: aload_0        
        //    34: aload_1        
        //    35: invokevirtual   net/minecraft/world/World.getBiomeGenForCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/biome/BiomeGenBase;
        //    38: astore_2       
        //    39: aload_2        
        //    40: invokevirtual   net/minecraft/world/biome/BiomeGenBase.getEnableSnow:()Z
        //    43: ifeq            50
        //    46: iconst_0       
        //    47: goto            63
        //    50: aload_0        
        //    51: aload_1        
        //    52: goto            59
        //    55: iconst_0       
        //    56: goto            63
        //    59: aload_2        
        //    60: invokevirtual   net/minecraft/world/biome/BiomeGenBase.canSpawnLightningBolt:()Z
        //    63: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0063 (coming from #0047).
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
    
    @Override
    public WorldType getWorldType() {
        return this.worldInfo.getTerrainType();
    }
    
    public void notifyNeighborsOfStateChange(final BlockPos blockPos, final Block block) {
        this.notifyBlockOfStateChange(blockPos.west(), block);
        this.notifyBlockOfStateChange(blockPos.east(), block);
        this.notifyBlockOfStateChange(blockPos.down(), block);
        this.notifyBlockOfStateChange(blockPos.up(), block);
        this.notifyBlockOfStateChange(blockPos.north(), block);
        this.notifyBlockOfStateChange(blockPos.south(), block);
    }
    
    public void setLastLightningBolt(final int lastLightningBolt) {
        this.lastLightningBolt = lastLightningBolt;
    }
    
    public double getHorizon() {
        return (this.worldInfo.getTerrainType() == WorldType.FLAT) ? 0.0 : 63.0;
    }
    
    public IChunkProvider getChunkProvider() {
        return this.chunkProvider;
    }
    
    public boolean isAnyLiquid(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = floor_double; i <= floor_double2; ++i) {
            for (int j = floor_double3; j <= floor_double4; ++j) {
                for (int k = floor_double5; k <= floor_double6; ++k) {
                    if (this.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock().getMaterial().isLiquid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public float getCurrentMoonPhaseFactor() {
        return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
    }
    
    public float getStarBrightness(final float n) {
        final float clamp_float = MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.25f), 0.0f, 1.0f);
        return clamp_float * clamp_float * 0.5f;
    }
    
    public boolean isBlockinHighHumidity(final BlockPos blockPos) {
        return this.getBiomeGenForCoords(blockPos).isHighHumidity();
    }
    
    public String getProviderName() {
        return this.chunkProvider.makeString();
    }
    
    public boolean checkBlockCollision(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = floor_double; i <= floor_double2; ++i) {
            for (int j = floor_double3; j <= floor_double4; ++j) {
                for (int k = floor_double5; k <= floor_double6; ++k) {
                    if (this.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock().getMaterial() != Material.air) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isMaterialInBB(final AxisAlignedBB axisAlignedBB, final Material material) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = floor_double; i < floor_double2; ++i) {
            for (int j = floor_double3; j < floor_double4; ++j) {
                for (int k = floor_double5; k < floor_double6; ++k) {
                    if (this.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock().getMaterial() == material) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public Explosion newExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2) {
        final Explosion explosion = new Explosion(this, entity, n, n2, n3, n4, b, b2);
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }
    
    public void removePlayerEntityDangerously(final Entity p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/entity/Entity.setDead:()V
        //     4: aload_1        
        //     5: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //     8: ifeq            26
        //    11: aload_0        
        //    12: getfield        net/minecraft/world/World.playerEntities:Ljava/util/List;
        //    15: aload_1        
        //    16: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //    21: pop            
        //    22: aload_0        
        //    23: invokevirtual   net/minecraft/world/World.updateAllPlayersSleepingFlag:()V
        //    26: aload_1        
        //    27: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //    30: istore_2       
        //    31: aload_1        
        //    32: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //    35: istore_3       
        //    36: aload_1        
        //    37: getfield        net/minecraft/entity/Entity.addedToChunk:Z
        //    40: ifeq            56
        //    43: aload_0        
        //    44: iload_2        
        //    45: iload_3        
        //    46: aload_0        
        //    47: iload_2        
        //    48: iload_3        
        //    49: invokevirtual   net/minecraft/world/World.getChunkFromChunkCoords:(II)Lnet/minecraft/world/chunk/Chunk;
        //    52: aload_1        
        //    53: invokevirtual   net/minecraft/world/chunk/Chunk.removeEntity:(Lnet/minecraft/entity/Entity;)V
        //    56: aload_0        
        //    57: getfield        net/minecraft/world/World.loadedEntityList:Ljava/util/List;
        //    60: aload_1        
        //    61: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //    66: pop            
        //    67: aload_0        
        //    68: aload_1        
        //    69: invokevirtual   net/minecraft/world/World.onEntityRemoved:(Lnet/minecraft/entity/Entity;)V
        //    72: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0056 (coming from #0053).
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
    
    public void setWorldTime(final long worldTime) {
        this.worldInfo.setWorldTime(worldTime);
    }
    
    protected void setActivePlayerChunksAndCheckLight() {
        this.activeChunkSet.clear();
        this.theProfiler.startSection("buildList");
        while (0 < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(0);
            final int floor_double = MathHelper.floor_double(entityPlayer.posX / 16.0);
            final int floor_double2 = MathHelper.floor_double(entityPlayer.posZ / 16.0);
            for (int renderDistanceChunks = this.getRenderDistanceChunks(), i = -renderDistanceChunks; i <= renderDistanceChunks; ++i) {
                for (int j = -renderDistanceChunks; j <= renderDistanceChunks; ++j) {
                    this.activeChunkSet.add(new ChunkCoordIntPair(i + floor_double, j + floor_double2));
                }
            }
            int nextInt = 0;
            ++nextInt;
        }
        this.theProfiler.endSection();
        if (this.ambientTickCountdown > 0) {
            --this.ambientTickCountdown;
        }
        this.theProfiler.startSection("playerCheckLight");
        if (!this.playerEntities.isEmpty()) {
            final int nextInt = this.rand.nextInt(this.playerEntities.size());
            final EntityPlayer entityPlayer2 = this.playerEntities.get(0);
            this.checkLight(new BlockPos(MathHelper.floor_double(entityPlayer2.posX) + this.rand.nextInt(11) - 5, MathHelper.floor_double(entityPlayer2.posY) + this.rand.nextInt(11) - 5, MathHelper.floor_double(entityPlayer2.posZ) + this.rand.nextInt(11) - 5));
        }
        this.theProfiler.endSection();
    }
    
    public ISaveHandler getSaveHandler() {
        return this.saveHandler;
    }
    
    public static boolean doesBlockHaveSolidTopSurface(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        return (block.getMaterial().isOpaque() && block.isFullCube()) || ((block instanceof BlockStairs) ? (blockState.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP) : ((block instanceof BlockSlab) ? (blockState.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP) : (block instanceof BlockHopper || (block instanceof BlockSnow && (int)blockState.getValue(BlockSnow.LAYERS) == 7))));
    }
    
    public void joinEntityInSurroundings(final Entity entity) {
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        for (int i = floor_double - 2; i <= floor_double + 2; ++i) {
            for (int j = floor_double2 - 2; j <= floor_double2 + 2; ++j) {
                this.getChunkFromChunkCoords(i, j);
            }
        }
        if (!this.loadedEntityList.contains(entity)) {
            this.loadedEntityList.add(entity);
        }
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 vec3, final Vec3 vec4, final boolean b) {
        return this.rayTraceBlocks(vec3, vec4, b, false, false);
    }
    
    public void calculateInitialSkylight() {
        final int calculateSkylightSubtracted = this.calculateSkylightSubtracted(1.0f);
        if (calculateSkylightSubtracted != this.skylightSubtracted) {
            this.skylightSubtracted = calculateSkylightSubtracted;
        }
    }
    
    public long getSeed() {
        return this.worldInfo.getSeed();
    }
    
    public void notifyBlockOfStateChange(final BlockPos blockPos, final Block block) {
        if (!this.isRemote) {
            final IBlockState blockState = this.getBlockState(blockPos);
            blockState.getBlock().onNeighborBlockChange(this, blockPos, blockState, block);
        }
    }
    
    public void setEntityState(final Entity entity, final byte b) {
    }
    
    public void markChunkDirty(final BlockPos blockPos, final TileEntity tileEntity) {
        if (this.isBlockLoaded(blockPos)) {
            this.getChunkFromBlockCoords(blockPos).setChunkModified();
        }
    }
    
    public void setThunderStrength(final float n) {
        this.prevThunderingStrength = n;
        this.thunderingStrength = n;
    }
    
    public void markTileEntityForRemoval(final TileEntity tileEntity) {
        this.tileEntitiesToBeRemoved.add(tileEntity);
    }
    
    public Calendar getCurrentDate() {
        if (this.getTotalWorldTime() % 600L == 0L) {
            this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
        }
        return this.theCalendar;
    }
    
    public float getRainStrength(final float n) {
        return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * n;
    }
    
    public int getActualHeight() {
        return this.provider.getHasNoSky() ? 128 : 256;
    }
    
    protected void onEntityAdded(final Entity entity) {
        while (0 < this.worldAccesses.size()) {
            this.worldAccesses.get(0).onEntityAdded(entity);
            int n = 0;
            ++n;
        }
    }
    
    public Block getGroundAboveSeaLevel(final BlockPos blockPos) {
        BlockPos up;
        for (up = new BlockPos(blockPos.getX(), this.func_181545_F(), blockPos.getZ()); this == up.up(); up = up.up()) {}
        return this.getBlockState(up).getBlock();
    }
    
    public void markBlockRangeForRenderUpdate(final BlockPos blockPos, final BlockPos blockPos2) {
        this.markBlockRangeForRenderUpdate(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final BlockPos blockPos2) {
        return this.isAreaLoaded(blockPos, blockPos2, true);
    }
    
    public void updateComparatorOutputLevel(final BlockPos blockPos, final Block block) {
        for (final EnumFacing next : EnumFacing.Plane.HORIZONTAL) {
            final BlockPos offset = blockPos.offset(next);
            if (this.isBlockLoaded(offset)) {
                final IBlockState blockState = this.getBlockState(offset);
                if (Blocks.unpowered_comparator.isAssociated(blockState.getBlock())) {
                    blockState.getBlock().onNeighborBlockChange(this, offset, blockState, block);
                }
                else {
                    if (!blockState.getBlock().isNormalCube()) {
                        continue;
                    }
                    final BlockPos offset2 = offset.offset(next);
                    final IBlockState blockState2 = this.getBlockState(offset2);
                    if (!Blocks.unpowered_comparator.isAssociated(blockState2.getBlock())) {
                        continue;
                    }
                    blockState2.getBlock().onNeighborBlockChange(this, offset2, blockState2, block);
                }
            }
        }
    }
    
    public void removeEntity(final Entity entity) {
        if (entity.riddenByEntity != null) {
            entity.riddenByEntity.mountEntity(null);
        }
        if (entity.ridingEntity != null) {
            entity.mountEntity(null);
        }
        entity.setDead();
        if (entity instanceof EntityPlayer) {
            this.playerEntities.remove(entity);
            this.updateAllPlayersSleepingFlag();
            this.onEntityRemoved(entity);
        }
    }
}
