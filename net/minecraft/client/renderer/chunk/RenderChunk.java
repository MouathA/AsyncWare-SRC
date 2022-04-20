package net.minecraft.client.renderer.chunk;

import java.util.concurrent.locks.*;
import java.nio.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.*;

public class RenderChunk
{
    private BlockPos[] positionOffsets16;
    private static final String __OBFID = "CL_00002452";
    private EnumWorldBlockLayer[] blockLayersSingle;
    private static EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS;
    private BlockPos position;
    private final ReentrantLock lockCompiledChunk;
    public CompiledChunk compiledChunk;
    private boolean needsUpdate;
    private final FloatBuffer modelviewMatrix;
    private World world;
    private final int index;
    private EnumMap field_181702_p;
    private final RenderGlobal renderGlobal;
    public static int renderChunksUpdated;
    private final Set field_181056_j;
    private final ReentrantLock lockCompileTask;
    private int frameIndex;
    private ChunkCompileTaskGenerator compileTask;
    private final VertexBuffer[] vertexBuffers;
    public AxisAlignedBB boundingBox;
    
    private void preRenderBlocks(final WorldRenderer worldRenderer, final BlockPos blockPos) {
        worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
        worldRenderer.setTranslation(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
    }
    
    public void setCompiledChunk(final CompiledChunk compiledChunk) {
        this.lockCompiledChunk.lock();
        this.compiledChunk = compiledChunk;
        this.lockCompiledChunk.unlock();
    }
    
    public VertexBuffer getVertexBufferByLayer(final int n) {
        return this.vertexBuffers[n];
    }
    
    public void multModelviewMatrix() {
        GlStateManager.multMatrix(this.modelviewMatrix);
    }
    
    protected void finishCompileTask() {
        this.lockCompileTask.lock();
        if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
            this.compileTask.finish();
            this.compileTask = null;
        }
        this.lockCompileTask.unlock();
    }
    
    public boolean setFrameIndex(final int frameIndex) {
        if (this.frameIndex == frameIndex) {
            return false;
        }
        this.frameIndex = frameIndex;
        return true;
    }
    
    public ChunkCompileTaskGenerator makeCompileTaskChunk() {
        this.lockCompileTask.lock();
        this.finishCompileTask();
        this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
        final ChunkCompileTaskGenerator compileTask = this.compileTask;
        this.lockCompileTask.unlock();
        return compileTask;
    }
    
    public void stopCompileTask() {
        this.finishCompileTask();
        this.compiledChunk = CompiledChunk.DUMMY;
    }
    
    private void initModelviewMatrix() {
        final float n = 1.000001f;
        GlStateManager.translate(-8.0f, -8.0f, -8.0f);
        GlStateManager.scale(n, n, n);
        GlStateManager.translate(8.0f, 8.0f, 8.0f);
        GlStateManager.getFloat(2982, this.modelviewMatrix);
    }
    
    private void postRenderBlocks(final EnumWorldBlockLayer enumWorldBlockLayer, final float n, final float n2, final float n3, final WorldRenderer worldRenderer, final CompiledChunk compiledChunk) {
        if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunk.isLayerEmpty(enumWorldBlockLayer)) {
            worldRenderer.func_181674_a(n, n2, n3);
            compiledChunk.setState(worldRenderer.func_181672_a());
        }
        worldRenderer.finishDrawing();
    }
    
    public ReentrantLock getLockCompileTask() {
        return this.lockCompileTask;
    }
    
    public CompiledChunk getCompiledChunk() {
        return this.compiledChunk;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
        this.lockCompileTask.lock();
        if (this.compileTask != null && this.compileTask.getStatus() == ChunkCompileTaskGenerator.Status.PENDING) {
            final ChunkCompileTaskGenerator chunkCompileTaskGenerator = null;
            this.lockCompileTask.unlock();
            return chunkCompileTaskGenerator;
        }
        if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
            this.compileTask.finish();
            this.compileTask = null;
        }
        (this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY)).setCompiledChunk(this.compiledChunk);
        final ChunkCompileTaskGenerator compileTask = this.compileTask;
        this.lockCompileTask.unlock();
        return compileTask;
    }
    
    public boolean isNeedsUpdate() {
        return this.needsUpdate;
    }
    
    public void resortTransparency(final float n, final float n2, final float n3, final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        final CompiledChunk compiledChunk = chunkCompileTaskGenerator.getCompiledChunk();
        if (compiledChunk.getState() != null && !compiledChunk.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
            final WorldRenderer worldRendererByLayer = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
            this.preRenderBlocks(worldRendererByLayer, this.position);
            worldRendererByLayer.setVertexState(compiledChunk.getState());
            this.postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, n, n2, n3, worldRendererByLayer, compiledChunk);
        }
    }
    
    public void deleteGlResources() {
        this.stopCompileTask();
        this.world = null;
        while (0 < EnumWorldBlockLayer.values().length) {
            if (this.vertexBuffers[0] != null) {
                this.vertexBuffers[0].deleteGlBuffers();
            }
            int n = 0;
            ++n;
        }
    }
    
    public void setNeedsUpdate(final boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }
    
    public BlockPos getPositionOffset16(final EnumFacing enumFacing) {
        final int index = enumFacing.getIndex();
        BlockPos offset = this.positionOffsets16[index];
        if (offset == null) {
            offset = this.getPosition().offset(enumFacing, 16);
            this.positionOffsets16[index] = offset;
        }
        return offset;
    }
    
    public void rebuildChunk(final float p0, final float p1, final float p2, final ChunkCompileTaskGenerator p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   net/minecraft/client/renderer/chunk/CompiledChunk.<init>:()V
        //     7: astore          5
        //     9: aload_0        
        //    10: getfield        net/minecraft/client/renderer/chunk/RenderChunk.position:Lnet/minecraft/util/BlockPos;
        //    13: astore          7
        //    15: aload           7
        //    17: bipush          15
        //    19: bipush          15
        //    21: bipush          15
        //    23: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    26: astore          8
        //    28: aload           4
        //    30: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //    33: invokevirtual   java/util/concurrent/locks/ReentrantLock.lock:()V
        //    36: aload           4
        //    38: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getStatus:()Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
        //    41: getstatic       net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status.COMPILING:Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
        //    44: if_acmpeq       56
        //    47: aload           4
        //    49: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //    52: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //    55: return         
        //    56: aload_0        
        //    57: getfield        net/minecraft/client/renderer/chunk/RenderChunk.world:Lnet/minecraft/world/World;
        //    60: ifnonnull       72
        //    63: aload           4
        //    65: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //    68: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //    71: return         
        //    72: new             Lnet/minecraft/client/renderer/RegionRenderCache;
        //    75: dup            
        //    76: aload_0        
        //    77: getfield        net/minecraft/client/renderer/chunk/RenderChunk.world:Lnet/minecraft/world/World;
        //    80: aload           7
        //    82: iconst_m1      
        //    83: iconst_m1      
        //    84: iconst_m1      
        //    85: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    88: aload           8
        //    90: iconst_1       
        //    91: iconst_1       
        //    92: iconst_1       
        //    93: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    96: iconst_1       
        //    97: invokespecial   net/minecraft/client/renderer/RegionRenderCache.<init>:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;I)V
        //   100: astore          9
        //   102: aload           4
        //   104: aload           5
        //   106: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.setCompiledChunk:(Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
        //   109: aload           4
        //   111: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //   114: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   117: goto            133
        //   120: astore          10
        //   122: aload           4
        //   124: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock:()Ljava/util/concurrent/locks/ReentrantLock;
        //   127: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   130: aload           10
        //   132: athrow         
        //   133: new             Lnet/minecraft/client/renderer/chunk/VisGraph;
        //   136: dup            
        //   137: invokespecial   net/minecraft/client/renderer/chunk/VisGraph.<init>:()V
        //   140: astore          10
        //   142: invokestatic    com/google/common/collect/Sets.newHashSet:()Ljava/util/HashSet;
        //   145: astore          11
        //   147: aload           9
        //   149: invokevirtual   net/minecraft/client/renderer/RegionRenderCache.extendedLevelsInChunkCache:()Z
        //   152: ifne            640
        //   155: getstatic       net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated:I
        //   158: iconst_1       
        //   159: iadd           
        //   160: putstatic       net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated:I
        //   163: invokestatic    net/minecraft/util/EnumWorldBlockLayer.values:()[Lnet/minecraft/util/EnumWorldBlockLayer;
        //   166: arraylength    
        //   167: newarray        Z
        //   169: astore          12
        //   171: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //   174: invokevirtual   net/minecraft/client/Minecraft.getBlockRendererDispatcher:()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
        //   177: astore          13
        //   179: aload           7
        //   181: aload           8
        //   183: invokestatic    optfine/BlockPosM.getAllInBoxMutable:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;)Ljava/lang/Iterable;
        //   186: invokeinterface java/lang/Iterable.iterator:()Ljava/util/Iterator;
        //   191: astore          14
        //   193: getstatic       optfine/Reflector.ForgeBlock_hasTileEntity:Loptfine/ReflectorMethod;
        //   196: invokevirtual   optfine/ReflectorMethod.exists:()Z
        //   199: istore          15
        //   201: getstatic       optfine/Reflector.ForgeBlock_canRenderInLayer:Loptfine/ReflectorMethod;
        //   204: invokevirtual   optfine/ReflectorMethod.exists:()Z
        //   207: istore          16
        //   209: getstatic       optfine/Reflector.ForgeHooksClient_setRenderLayer:Loptfine/ReflectorMethod;
        //   212: invokevirtual   optfine/ReflectorMethod.exists:()Z
        //   215: istore          17
        //   217: aload           14
        //   219: invokeinterface java/util/Iterator.hasNext:()Z
        //   224: ifeq            563
        //   227: aload           14
        //   229: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   234: checkcast       Loptfine/BlockPosM;
        //   237: astore          18
        //   239: aload           9
        //   241: aload           18
        //   243: invokevirtual   net/minecraft/client/renderer/RegionRenderCache.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   246: astore          19
        //   248: aload           19
        //   250: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   255: astore          20
        //   257: aload           20
        //   259: invokevirtual   net/minecraft/block/Block.isOpaqueCube:()Z
        //   262: ifeq            272
        //   265: aload           10
        //   267: aload           18
        //   269: invokevirtual   net/minecraft/client/renderer/chunk/VisGraph.func_178606_a:(Lnet/minecraft/util/BlockPos;)V
        //   272: iload           15
        //   274: ifeq            299
        //   277: aload           14
        //   279: getstatic       optfine/Reflector.ForgeBlock_hasTileEntity:Loptfine/ReflectorMethod;
        //   282: iconst_1       
        //   283: anewarray       Ljava/lang/Object;
        //   286: dup            
        //   287: iconst_0       
        //   288: aload           13
        //   290: aastore        
        //   291: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   294: istore          21
        //   296: goto            306
        //   299: aload           20
        //   301: invokevirtual   net/minecraft/block/Block.hasTileEntity:()Z
        //   304: istore          21
        //   306: iload           21
        //   308: ifeq            370
        //   311: aload           9
        //   313: new             Lnet/minecraft/util/BlockPos;
        //   316: dup            
        //   317: aload           18
        //   319: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/util/Vec3i;)V
        //   322: invokevirtual   net/minecraft/client/renderer/RegionRenderCache.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //   325: astore          22
        //   327: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //   330: aload           22
        //   332: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.getSpecialRenderer:(Lnet/minecraft/tileentity/TileEntity;)Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;
        //   335: astore          23
        //   337: aload           22
        //   339: ifnull          370
        //   342: aload           23
        //   344: ifnull          370
        //   347: aload           5
        //   349: aload           22
        //   351: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.addTileEntity:(Lnet/minecraft/tileentity/TileEntity;)V
        //   354: aload           23
        //   356: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer.func_181055_a:()Z
        //   359: ifeq            370
        //   362: aload           11
        //   364: aload           22
        //   366: invokevirtual   java/util/HashSet.add:(Ljava/lang/Object;)Z
        //   369: pop            
        //   370: iload           16
        //   372: ifeq            383
        //   375: getstatic       net/minecraft/client/renderer/chunk/RenderChunk.ENUM_WORLD_BLOCK_LAYERS:[Lnet/minecraft/util/EnumWorldBlockLayer;
        //   378: astore          22
        //   380: goto            398
        //   383: aload_0        
        //   384: getfield        net/minecraft/client/renderer/chunk/RenderChunk.blockLayersSingle:[Lnet/minecraft/util/EnumWorldBlockLayer;
        //   387: astore          22
        //   389: aload           22
        //   391: iconst_0       
        //   392: aload           20
        //   394: invokevirtual   net/minecraft/block/Block.getBlockLayer:()Lnet/minecraft/util/EnumWorldBlockLayer;
        //   397: aastore        
        //   398: iconst_0       
        //   399: aload           22
        //   401: arraylength    
        //   402: if_icmpge       560
        //   405: aload           22
        //   407: iconst_0       
        //   408: aaload         
        //   409: astore          24
        //   411: iload           16
        //   413: ifeq            443
        //   416: aload           20
        //   418: getstatic       optfine/Reflector.ForgeBlock_canRenderInLayer:Loptfine/ReflectorMethod;
        //   421: iconst_1       
        //   422: anewarray       Ljava/lang/Object;
        //   425: dup            
        //   426: iconst_0       
        //   427: aload           24
        //   429: aastore        
        //   430: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   433: istore          25
        //   435: iload           25
        //   437: ifne            443
        //   440: goto            554
        //   443: aload_0        
        //   444: aload           20
        //   446: aload           24
        //   448: invokespecial   net/minecraft/client/renderer/chunk/RenderChunk.fixBlockLayer:(Lnet/minecraft/block/Block;Lnet/minecraft/util/EnumWorldBlockLayer;)Lnet/minecraft/util/EnumWorldBlockLayer;
        //   451: astore          24
        //   453: iload           17
        //   455: ifeq            473
        //   458: getstatic       optfine/Reflector.ForgeHooksClient_setRenderLayer:Loptfine/ReflectorMethod;
        //   461: iconst_1       
        //   462: anewarray       Ljava/lang/Object;
        //   465: dup            
        //   466: iconst_0       
        //   467: aload           24
        //   469: aastore        
        //   470: invokestatic    optfine/Reflector.callVoid:(Loptfine/ReflectorMethod;[Ljava/lang/Object;)V
        //   473: aload           24
        //   475: invokevirtual   net/minecraft/util/EnumWorldBlockLayer.ordinal:()I
        //   478: istore          25
        //   480: aload           20
        //   482: invokevirtual   net/minecraft/block/Block.getRenderType:()I
        //   485: iconst_m1      
        //   486: if_icmpeq       554
        //   489: aload           4
        //   491: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getRegionRenderCacheBuilder:()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
        //   494: iload           25
        //   496: invokevirtual   net/minecraft/client/renderer/RegionRenderCacheBuilder.getWorldRendererByLayerId:(I)Lnet/minecraft/client/renderer/WorldRenderer;
        //   499: astore          26
        //   501: aload           26
        //   503: aload           24
        //   505: invokevirtual   net/minecraft/client/renderer/WorldRenderer.setBlockLayer:(Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   508: aload           5
        //   510: aload           24
        //   512: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted:(Lnet/minecraft/util/EnumWorldBlockLayer;)Z
        //   515: ifne            533
        //   518: aload           5
        //   520: aload           24
        //   522: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.setLayerStarted:(Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   525: aload_0        
        //   526: aload           26
        //   528: aload           7
        //   530: invokespecial   net/minecraft/client/renderer/chunk/RenderChunk.preRenderBlocks:(Lnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/util/BlockPos;)V
        //   533: aload           12
        //   535: iload           25
        //   537: dup2           
        //   538: baload         
        //   539: aload           13
        //   541: aload           19
        //   543: aload           18
        //   545: aload           9
        //   547: aload           26
        //   549: invokevirtual   net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock:(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/WorldRenderer;)Z
        //   552: ior            
        //   553: bastore        
        //   554: iinc            23, 1
        //   557: goto            398
        //   560: goto            217
        //   563: invokestatic    net/minecraft/util/EnumWorldBlockLayer.values:()[Lnet/minecraft/util/EnumWorldBlockLayer;
        //   566: astore          18
        //   568: aload           18
        //   570: arraylength    
        //   571: istore          19
        //   573: iconst_0       
        //   574: iload           19
        //   576: if_icmpge       640
        //   579: aload           18
        //   581: iconst_0       
        //   582: aaload         
        //   583: astore          21
        //   585: aload           12
        //   587: aload           21
        //   589: invokevirtual   net/minecraft/util/EnumWorldBlockLayer.ordinal:()I
        //   592: baload         
        //   593: ifeq            603
        //   596: aload           5
        //   598: aload           21
        //   600: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.setLayerUsed:(Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   603: aload           5
        //   605: aload           21
        //   607: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted:(Lnet/minecraft/util/EnumWorldBlockLayer;)Z
        //   610: ifeq            634
        //   613: aload_0        
        //   614: aload           21
        //   616: fload_1        
        //   617: fload_2        
        //   618: fload_3        
        //   619: aload           4
        //   621: invokevirtual   net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getRegionRenderCacheBuilder:()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
        //   624: aload           21
        //   626: invokevirtual   net/minecraft/client/renderer/RegionRenderCacheBuilder.getWorldRendererByLayer:(Lnet/minecraft/util/EnumWorldBlockLayer;)Lnet/minecraft/client/renderer/WorldRenderer;
        //   629: aload           5
        //   631: invokespecial   net/minecraft/client/renderer/chunk/RenderChunk.postRenderBlocks:(Lnet/minecraft/util/EnumWorldBlockLayer;FFFLnet/minecraft/client/renderer/WorldRenderer;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
        //   634: iinc            20, 1
        //   637: goto            573
        //   640: aload           5
        //   642: aload           10
        //   644: invokevirtual   net/minecraft/client/renderer/chunk/VisGraph.computeVisibility:()Lnet/minecraft/client/renderer/chunk/SetVisibility;
        //   647: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.setVisibility:(Lnet/minecraft/client/renderer/chunk/SetVisibility;)V
        //   650: aload_0        
        //   651: getfield        net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask:Ljava/util/concurrent/locks/ReentrantLock;
        //   654: invokevirtual   java/util/concurrent/locks/ReentrantLock.lock:()V
        //   657: aload           11
        //   659: invokestatic    com/google/common/collect/Sets.newHashSet:(Ljava/lang/Iterable;)Ljava/util/HashSet;
        //   662: astore          12
        //   664: aload_0        
        //   665: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_181056_j:Ljava/util/Set;
        //   668: invokestatic    com/google/common/collect/Sets.newHashSet:(Ljava/lang/Iterable;)Ljava/util/HashSet;
        //   671: astore          13
        //   673: aload           12
        //   675: aload_0        
        //   676: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_181056_j:Ljava/util/Set;
        //   679: invokevirtual   java/util/HashSet.removeAll:(Ljava/util/Collection;)Z
        //   682: pop            
        //   683: aload           13
        //   685: aload           11
        //   687: invokevirtual   java/util/HashSet.removeAll:(Ljava/util/Collection;)Z
        //   690: pop            
        //   691: aload_0        
        //   692: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_181056_j:Ljava/util/Set;
        //   695: invokeinterface java/util/Set.clear:()V
        //   700: aload_0        
        //   701: getfield        net/minecraft/client/renderer/chunk/RenderChunk.field_181056_j:Ljava/util/Set;
        //   704: aload           11
        //   706: invokeinterface java/util/Set.addAll:(Ljava/util/Collection;)Z
        //   711: pop            
        //   712: aload_0        
        //   713: getfield        net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;
        //   716: aload           13
        //   718: aload           12
        //   720: invokevirtual   net/minecraft/client/renderer/RenderGlobal.func_181023_a:(Ljava/util/Collection;Ljava/util/Collection;)V
        //   723: aload_0        
        //   724: getfield        net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask:Ljava/util/concurrent/locks/ReentrantLock;
        //   727: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   730: goto            745
        //   733: astore          27
        //   735: aload_0        
        //   736: getfield        net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask:Ljava/util/concurrent/locks/ReentrantLock;
        //   739: invokevirtual   java/util/concurrent/locks/ReentrantLock.unlock:()V
        //   742: aload           27
        //   744: athrow         
        //   745: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public RenderChunk(final World world, final RenderGlobal renderGlobal, final BlockPos position, final int index) {
        this.compiledChunk = CompiledChunk.DUMMY;
        this.lockCompileTask = new ReentrantLock();
        this.lockCompiledChunk = new ReentrantLock();
        this.compileTask = null;
        this.field_181056_j = Sets.newHashSet();
        this.modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
        this.vertexBuffers = new VertexBuffer[EnumWorldBlockLayer.values().length];
        this.frameIndex = -1;
        this.needsUpdate = true;
        this.positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
        this.blockLayersSingle = new EnumWorldBlockLayer[1];
        this.world = world;
        this.renderGlobal = renderGlobal;
        this.index = index;
        if (!position.equals(this.getPosition())) {
            this.setPosition(position);
        }
        if (OpenGlHelper.useVbo()) {
            while (0 < EnumWorldBlockLayer.values().length) {
                this.vertexBuffers[0] = new VertexBuffer(DefaultVertexFormats.BLOCK);
                int n = 0;
                ++n;
            }
        }
    }
    
    static {
        RenderChunk.ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
    }
    
    public void setPosition(final BlockPos position) {
        this.stopCompileTask();
        this.position = position;
        this.boundingBox = new AxisAlignedBB(position, position.add(16, 16, 16));
        final int length = EnumFacing.values().length;
        this.initModelviewMatrix();
        while (0 < this.positionOffsets16.length) {
            this.positionOffsets16[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    private EnumWorldBlockLayer fixBlockLayer(final Block block, final EnumWorldBlockLayer enumWorldBlockLayer) {
        return (enumWorldBlockLayer == EnumWorldBlockLayer.CUTOUT) ? ((block instanceof BlockRedstoneWire) ? enumWorldBlockLayer : ((block instanceof BlockCactus) ? enumWorldBlockLayer : EnumWorldBlockLayer.CUTOUT_MIPPED)) : enumWorldBlockLayer;
    }
    
    public BlockPos func_181701_a(final EnumFacing enumFacing) {
        return this.getPositionOffset16(enumFacing);
    }
}
