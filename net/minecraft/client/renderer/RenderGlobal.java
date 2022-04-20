package net.minecraft.client.renderer;

import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import org.lwjgl.util.vector.*;
import optfine.*;
import net.minecraft.client.particle.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.world.*;
import net.minecraft.client.audio.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.border.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.chunk.*;
import net.minecraft.client.renderer.chunk.*;
import java.util.*;

public class RenderGlobal implements IResourceManagerReloadListener, IWorldAccess
{
    IRenderChunkFactory renderChunkFactory;
    private double frustumUpdatePosX;
    private ClippingHelper debugFixedClippingHelper;
    private VertexBuffer sky2VBO;
    public boolean displayListEntitiesDirty;
    private final Set field_181024_n;
    private int starGLCallList;
    private final TextureAtlasSprite[] destroyBlockIcons;
    private final Map mapSoundPositions;
    private int countEntitiesHidden;
    private ViewFrustum viewFrustum;
    private boolean vboEnabled;
    private int renderDistanceSq;
    private List renderInfosEntities;
    private ShaderGroup entityOutlineShader;
    private CloudRenderer cloudRenderer;
    private boolean debugFixTerrainFrustum;
    private static final ResourceLocation locationForcefieldPng;
    private double lastViewEntityY;
    private int glSkyList;
    private static final ResourceLocation locationCloudsPng;
    private int renderEntitiesStartupCounter;
    private double lastViewEntityYaw;
    private List renderInfosTileEntities;
    private VertexFormat vertexBufferFormat;
    public Entity renderedEntity;
    private final Vector4f[] debugTerrainMatrix;
    private double prevRenderSortX;
    private static final Set SET_ALL_FACINGS;
    public final Minecraft mc;
    private Set chunksToUpdate;
    private int frustumUpdatePosChunkY;
    public final Map damagedBlocks;
    private double lastViewEntityX;
    private int countEntitiesRendered;
    private Framebuffer entityOutlineFramebuffer;
    private double lastViewEntityPitch;
    private int countTileEntitiesRendered;
    private int glSkyList2;
    public Set chunksToUpdateForced;
    private double frustumUpdatePosZ;
    private ChunkRenderContainer renderContainer;
    private List renderInfos;
    private double prevRenderSortY;
    private final Vector3d debugTerrainFrustumPosition;
    private final RenderManager renderManager;
    private final TextureManager renderEngine;
    private Deque visibilityDeque;
    private VertexBuffer starVBO;
    private VertexBuffer skyVBO;
    private static final ResourceLocation locationSunPng;
    private static final ResourceLocation locationEndSkyPng;
    private int frustumUpdatePosChunkX;
    public Set chunksToResortTransparency;
    private double prevRenderSortZ;
    private final ChunkRenderDispatcher renderDispatcher;
    private int renderDistanceChunks;
    private int cloudTickCounter;
    private int frustumUpdatePosChunkZ;
    private double frustumUpdatePosY;
    private static final Logger logger;
    private static final String __OBFID = "CL_00000954";
    private WorldClient theWorld;
    private static final ResourceLocation locationMoonPhasesPng;
    private int renderDistance;
    private int countEntitiesTotal;
    private double lastViewEntityZ;
    
    public int renderBlockLayer(final EnumWorldBlockLayer p0, final double p1, final int p2, final Entity p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getstatic       net/minecraft/util/EnumWorldBlockLayer.TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //     4: if_acmpne       198
        //     7: aload_0        
        //     8: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    11: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //    14: ldc             "translucent_sort"
        //    16: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    19: aload           5
        //    21: getfield        net/minecraft/entity/Entity.posX:D
        //    24: aload_0        
        //    25: getfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortX:D
        //    28: dsub           
        //    29: dstore          6
        //    31: aload           5
        //    33: getfield        net/minecraft/entity/Entity.posY:D
        //    36: aload_0        
        //    37: getfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortY:D
        //    40: dsub           
        //    41: dstore          8
        //    43: aload           5
        //    45: getfield        net/minecraft/entity/Entity.posZ:D
        //    48: aload_0        
        //    49: getfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortZ:D
        //    52: dsub           
        //    53: dstore          10
        //    55: dload           6
        //    57: dload           6
        //    59: dmul           
        //    60: dload           8
        //    62: dload           8
        //    64: dmul           
        //    65: dadd           
        //    66: dload           10
        //    68: dload           10
        //    70: dmul           
        //    71: dadd           
        //    72: dconst_1       
        //    73: dcmpl          
        //    74: ifle            188
        //    77: aload_0        
        //    78: aload           5
        //    80: getfield        net/minecraft/entity/Entity.posX:D
        //    83: putfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortX:D
        //    86: aload_0        
        //    87: aload           5
        //    89: getfield        net/minecraft/entity/Entity.posY:D
        //    92: putfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortY:D
        //    95: aload_0        
        //    96: aload           5
        //    98: getfield        net/minecraft/entity/Entity.posZ:D
        //   101: putfield        net/minecraft/client/renderer/RenderGlobal.prevRenderSortZ:D
        //   104: aload_0        
        //   105: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfos:Ljava/util/List;
        //   108: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   113: astore          13
        //   115: aload_0        
        //   116: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToResortTransparency:Ljava/util/Set;
        //   119: invokeinterface java/util/Set.clear:()V
        //   124: aload           13
        //   126: invokeinterface java/util/Iterator.hasNext:()Z
        //   131: ifeq            188
        //   134: aload           13
        //   136: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   141: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   144: astore          14
        //   146: aload           14
        //   148: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   151: getfield        net/minecraft/client/renderer/chunk/RenderChunk.compiledChunk:Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //   154: aload_1        
        //   155: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted:(Lnet/minecraft/util/EnumWorldBlockLayer;)Z
        //   158: ifeq            185
        //   161: iconst_0       
        //   162: iinc            12, 1
        //   165: bipush          15
        //   167: if_icmpge       185
        //   170: aload_0        
        //   171: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToResortTransparency:Ljava/util/Set;
        //   174: aload           14
        //   176: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   179: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   184: pop            
        //   185: goto            124
        //   188: aload_0        
        //   189: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   192: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   195: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   198: aload_0        
        //   199: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   202: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   205: ldc             "filterempty"
        //   207: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   210: aload_1        
        //   211: getstatic       net/minecraft/util/EnumWorldBlockLayer.TRANSLUCENT:Lnet/minecraft/util/EnumWorldBlockLayer;
        //   214: if_acmpne       221
        //   217: iconst_1       
        //   218: goto            222
        //   221: iconst_0       
        //   222: istore          7
        //   224: iload           7
        //   226: ifeq            243
        //   229: aload_0        
        //   230: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfos:Ljava/util/List;
        //   233: invokeinterface java/util/List.size:()I
        //   238: iconst_1       
        //   239: isub           
        //   240: goto            244
        //   243: iconst_0       
        //   244: istore          8
        //   246: iload           7
        //   248: ifeq            255
        //   251: iconst_m1      
        //   252: goto            264
        //   255: aload_0        
        //   256: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfos:Ljava/util/List;
        //   259: invokeinterface java/util/List.size:()I
        //   264: istore          9
        //   266: iload           7
        //   268: ifeq            275
        //   271: iconst_m1      
        //   272: goto            276
        //   275: iconst_1       
        //   276: istore          10
        //   278: iload           8
        //   280: istore          11
        //   282: iload           11
        //   284: iload           9
        //   286: if_icmpeq       343
        //   289: aload_0        
        //   290: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfos:Ljava/util/List;
        //   293: iload           11
        //   295: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   300: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   303: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   306: astore          12
        //   308: aload           12
        //   310: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.getCompiledChunk:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //   313: aload_1        
        //   314: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.isLayerEmpty:(Lnet/minecraft/util/EnumWorldBlockLayer;)Z
        //   317: ifne            333
        //   320: iinc            6, 1
        //   323: aload_0        
        //   324: getfield        net/minecraft/client/renderer/RenderGlobal.renderContainer:Lnet/minecraft/client/renderer/ChunkRenderContainer;
        //   327: aload           12
        //   329: aload_1        
        //   330: invokevirtual   net/minecraft/client/renderer/ChunkRenderContainer.addRenderChunk:(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   333: iload           11
        //   335: iload           10
        //   337: iadd           
        //   338: istore          11
        //   340: goto            282
        //   343: iconst_0       
        //   344: ireturn        
        //   345: invokestatic    optfine/Config.isFogOff:()Z
        //   348: ifeq            361
        //   351: aload_0        
        //   352: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   355: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //   358: getfield        net/minecraft/client/renderer/EntityRenderer.fogStandard:Z
        //   361: aload_0        
        //   362: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   365: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   368: new             Ljava/lang/StringBuilder;
        //   371: dup            
        //   372: invokespecial   java/lang/StringBuilder.<init>:()V
        //   375: ldc_w           "render_"
        //   378: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   381: aload_1        
        //   382: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   385: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   388: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   391: aload_0        
        //   392: aload_1        
        //   393: invokespecial   net/minecraft/client/renderer/RenderGlobal.renderBlockLayer:(Lnet/minecraft/util/EnumWorldBlockLayer;)V
        //   396: aload_0        
        //   397: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   400: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   403: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   406: iconst_0       
        //   407: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void renderCloudsFancy(float n, final int n2) {
        this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, n);
        n = 0.0f;
        final float n3 = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * n);
        Tessellator.getInstance().getWorldRenderer();
        final double n4 = (this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * n + (this.cloudTickCounter + n) * 0.029999999329447746) / 12.0;
        final double n5 = (this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * n) / 12.0 + 0.33000001311302185;
        final float n6 = this.theWorld.provider.getCloudHeight() - n3 + 0.33f + this.mc.gameSettings.ofCloudsHeight * 128.0f;
        final int floor_double = MathHelper.floor_double(n4 / 2048.0);
        final int floor_double2 = MathHelper.floor_double(n5 / 2048.0);
        final double n7 = n4 - floor_double * 2048;
        final double n8 = n5 - floor_double2 * 2048;
        this.renderEngine.bindTexture(RenderGlobal.locationCloudsPng);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final Vec3 cloudColour = this.theWorld.getCloudColour(n);
        float n9 = (float)cloudColour.xCoord;
        float n10 = (float)cloudColour.yCoord;
        float n11 = (float)cloudColour.zCoord;
        if (n2 != 2) {
            final float n12 = (n9 * 30.0f + n10 * 59.0f + n11 * 11.0f) / 100.0f;
            final float n13 = (n9 * 30.0f + n10 * 70.0f) / 100.0f;
            final float n14 = (n9 * 30.0f + n11 * 70.0f) / 100.0f;
            n9 = n12;
            n10 = n13;
            n11 = n14;
        }
        final float n15 = MathHelper.floor_double(n7) * 0.00390625f;
        final float n16 = MathHelper.floor_double(n8) * 0.00390625f;
        final float n17 = (float)(n7 - MathHelper.floor_double(n7));
        final float n18 = (float)(n8 - MathHelper.floor_double(n8));
        GlStateManager.scale(12.0f, 1.0f, 12.0f);
        while (true) {
            switch (n2) {
                case 0: {
                    GlStateManager.colorMask(false, true, true, true);
                    break;
                }
                case 1: {
                    GlStateManager.colorMask(true, false, false, true);
                    break;
                }
                case 2: {
                    GlStateManager.colorMask(true, true, true, true);
                    break;
                }
            }
            this.cloudRenderer.renderGlList();
            int n19 = 0;
            ++n19;
        }
    }
    
    @Override
    public void onEntityAdded(final Entity entity) {
        RandomMobs.entityLoaded(entity);
    }
    
    @Override
    public void sendBlockBreakProgress(final int n, final BlockPos blockPos, final int partialBlockDamage) {
        if (partialBlockDamage >= 0 && partialBlockDamage < 10) {
            DestroyBlockProgress destroyBlockProgress = this.damagedBlocks.get(n);
            if (destroyBlockProgress == null || destroyBlockProgress.getPosition().getX() != blockPos.getX() || destroyBlockProgress.getPosition().getY() != blockPos.getY() || destroyBlockProgress.getPosition().getZ() != blockPos.getZ()) {
                destroyBlockProgress = new DestroyBlockProgress(n, blockPos);
                this.damagedBlocks.put(n, destroyBlockProgress);
            }
            destroyBlockProgress.setPartialBlockDamage(partialBlockDamage);
            destroyBlockProgress.setCloudUpdateTick(this.cloudTickCounter);
        }
        else {
            this.damagedBlocks.remove(n);
        }
    }
    
    public void drawBlockDamageTexture(final Tessellator tessellator, final WorldRenderer worldRenderer, final Entity entity, final float n) {
        final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        if (!this.damagedBlocks.isEmpty()) {
            this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            this.preRenderDamagedBlocks();
            worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
            worldRenderer.setTranslation(-n2, -n3, -n4);
            worldRenderer.markDirty();
            final Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();
            while (iterator.hasNext()) {
                final DestroyBlockProgress destroyBlockProgress = iterator.next();
                final BlockPos position = destroyBlockProgress.getPosition();
                final double n5 = position.getX() - n2;
                final double n6 = position.getY() - n3;
                final double n7 = position.getZ() - n4;
                final Block block = this.theWorld.getBlockState(position).getBlock();
                boolean b;
                if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
                    boolean callBoolean = block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull;
                    if (!callBoolean) {
                        final TileEntity tileEntity = this.theWorld.getTileEntity(position);
                        if (tileEntity != null) {
                            callBoolean = Reflector.callBoolean(tileEntity, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]);
                        }
                    }
                    b = !callBoolean;
                }
                else {
                    b = (!(block instanceof BlockChest) && !(block instanceof BlockEnderChest) && !(block instanceof BlockSign) && !(block instanceof BlockSkull));
                }
                if (b) {
                    if (n5 * n5 + n6 * n6 + n7 * n7 > 1024.0) {
                        iterator.remove();
                    }
                    else {
                        final IBlockState blockState = this.theWorld.getBlockState(position);
                        if (blockState.getBlock().getMaterial() == Material.air) {
                            continue;
                        }
                        this.mc.getBlockRendererDispatcher().renderBlockDamage(blockState, position, this.destroyBlockIcons[destroyBlockProgress.getPartialBlockDamage()], this.theWorld);
                    }
                }
            }
            tessellator.draw();
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            this.postRenderDamagedBlocks();
        }
    }
    
    private void cleanupDamagedBlocks(final Iterator iterator) {
        while (iterator.hasNext()) {
            if (this.cloudTickCounter - iterator.next().getCreationCloudUpdateTick() > 400) {
                iterator.remove();
            }
        }
    }
    
    public void updateClouds() {
        ++this.cloudTickCounter;
        if (this.cloudTickCounter % 20 == 0) {
            this.cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
        }
    }
    
    protected Vector3f getViewVector(final Entity entity, final double n) {
        float n2 = (float)(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * n);
        final float n3 = (float)(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * n);
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
            n2 += 180.0f;
        }
        final float cos = MathHelper.cos(-n3 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n3 * 0.017453292f - 3.1415927f);
        final float n4 = -MathHelper.cos(-n2 * 0.017453292f);
        return new Vector3f(sin * n4, MathHelper.sin(-n2 * 0.017453292f), cos * n4);
    }
    
    public static void func_181561_a(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
    }
    
    public void createBindEntityOutlineFbs(final int n, final int n2) {
        if (OpenGlHelper.shadersSupported && this.entityOutlineShader != null) {
            this.entityOutlineShader.createBindFramebuffers(n, n2);
        }
    }
    
    private void postRenderDamagedBlocks() {
        GlStateManager.doPolygonOffset(0.0f, 0.0f);
        GlStateManager.depthMask(true);
    }
    
    private void updateDestroyBlockIcons() {
        final TextureMap textureMapBlocks = this.mc.getTextureMapBlocks();
        while (0 < this.destroyBlockIcons.length) {
            this.destroyBlockIcons[0] = textureMapBlocks.getAtlasSprite("minecraft:blocks/destroy_stage_" + 0);
            int n = 0;
            ++n;
        }
    }
    
    public void setDisplayListEntitiesDirty() {
        this.displayListEntitiesDirty = true;
    }
    
    @Override
    public void spawnParticle(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        this.spawnEntityFX(n, b, n2, n3, n4, n5, n6, n7, array);
    }
    
    @Override
    public void notifyLightSet(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.markBlocksForUpdate(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
    }
    
    public void renderClouds(float n, final int n2) {
        if (!Config.isCloudsOff()) {
            if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
                final Object call = Reflector.call(this.mc.theWorld.provider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);
                if (call != null) {
                    Reflector.callVoid(call, Reflector.IRenderHandler_render, n, this.theWorld, this.mc);
                    return;
                }
            }
            if (this.mc.theWorld.provider.isSurfaceWorld()) {
                if (Config.isCloudsFancy()) {
                    this.renderCloudsFancy(n, n2);
                }
                else {
                    this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, n);
                    n = 0.0f;
                    final float n3 = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * n);
                    final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
                    this.renderEngine.bindTexture(RenderGlobal.locationCloudsPng);
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    if (this.cloudRenderer.shouldUpdateGlList()) {
                        this.cloudRenderer.startUpdateGlList();
                        final Vec3 cloudColour = this.theWorld.getCloudColour(n);
                        float n4 = (float)cloudColour.xCoord;
                        float n5 = (float)cloudColour.yCoord;
                        float n6 = (float)cloudColour.zCoord;
                        if (n2 != 2) {
                            final float n7 = (n4 * 30.0f + n5 * 59.0f + n6 * 11.0f) / 100.0f;
                            final float n8 = (n4 * 30.0f + n5 * 70.0f) / 100.0f;
                            final float n9 = (n4 * 30.0f + n6 * 70.0f) / 100.0f;
                            n4 = n7;
                            n5 = n8;
                            n6 = n9;
                        }
                        final double n10 = this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * n + (this.cloudTickCounter + n) * 0.029999999329447746;
                        final double n11 = this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * n;
                        final int floor_double = MathHelper.floor_double(n10 / 2048.0);
                        final int floor_double2 = MathHelper.floor_double(n11 / 2048.0);
                        final double n12 = n10 - floor_double * 2048;
                        final double n13 = n11 - floor_double2 * 2048;
                        final float n14 = this.theWorld.provider.getCloudHeight() - n3 + 0.33f + this.mc.gameSettings.ofCloudsHeight * 128.0f;
                        final float n15 = (float)(n12 * 4.8828125E-4);
                        final float n16 = (float)(n13 * 4.8828125E-4);
                        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                        while (true) {
                            worldRenderer.pos(-256, n14, -224).tex(-256 * 4.8828125E-4f + n15, -224 * 4.8828125E-4f + n16).color(n4, n5, n6, 0.8f).endVertex();
                            worldRenderer.pos(-224, n14, -224).tex(-224 * 4.8828125E-4f + n15, -224 * 4.8828125E-4f + n16).color(n4, n5, n6, 0.8f).endVertex();
                            worldRenderer.pos(-224, n14, -256).tex(-224 * 4.8828125E-4f + n15, -256 * 4.8828125E-4f + n16).color(n4, n5, n6, 0.8f).endVertex();
                            worldRenderer.pos(-256, n14, -256).tex(-256 * 4.8828125E-4f + n15, -256 * 4.8828125E-4f + n16).color(n4, n5, n6, 0.8f).endVertex();
                            final int n17;
                            n17 += 32;
                        }
                    }
                    else {
                        this.cloudRenderer.renderGlList();
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                }
            }
        }
    }
    
    public int getCountEntitiesRendered() {
        return this.countEntitiesRendered;
    }
    
    private EntityFX spawnEntityFX(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        if (this.mc == null || this.mc.getRenderViewEntity() == null || this.mc.effectRenderer == null) {
            return null;
        }
        final int particleSetting = this.mc.gameSettings.particleSetting;
        final double n8 = this.mc.getRenderViewEntity().posX - n2;
        final double n9 = this.mc.getRenderViewEntity().posY - n3;
        final double n10 = this.mc.getRenderViewEntity().posZ - n4;
        if (n == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
        }
        if (n == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke()) {
            return null;
        }
        if (n == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles()) {
            return null;
        }
        if (n == EnumParticleTypes.PORTAL.getParticleID() && !Config.isAnimatedPortal()) {
            return null;
        }
        if (n == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame()) {
            return null;
        }
        if (n == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone()) {
            return null;
        }
        if (n == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava()) {
            return null;
        }
        if (n == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava()) {
            return null;
        }
        if (n == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles()) {
            return null;
        }
        if (b) {
            return this.mc.effectRenderer.spawnEffectParticle(n, n2, n3, n4, n5, n6, n7, array);
        }
        double n11 = 256.0;
        if (n == EnumParticleTypes.CRIT.getParticleID()) {
            n11 = 38416.0;
        }
        if (n8 * n8 + n9 * n9 + n10 * n10 > n11) {
            return null;
        }
        return null;
    }
    
    public void renderEntities(final Entity p0, final ICamera p1, final float p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   optfine/ReflectorMethod.exists:()Z
        //     6: ifeq            21
        //     9: getstatic       optfine/Reflector.MinecraftForgeClient_getRenderPass:Loptfine/ReflectorMethod;
        //    12: iconst_0       
        //    13: anewarray       Ljava/lang/Object;
        //    16: invokestatic    optfine/Reflector.callInt:(Loptfine/ReflectorMethod;[Ljava/lang/Object;)I
        //    19: istore          4
        //    21: aload_0        
        //    22: getfield        net/minecraft/client/renderer/RenderGlobal.renderEntitiesStartupCounter:I
        //    25: ifle            44
        //    28: goto            31
        //    31: aload_0        
        //    32: dup            
        //    33: getfield        net/minecraft/client/renderer/RenderGlobal.renderEntitiesStartupCounter:I
        //    36: iconst_1       
        //    37: isub           
        //    38: putfield        net/minecraft/client/renderer/RenderGlobal.renderEntitiesStartupCounter:I
        //    41: goto            2032
        //    44: aload_1        
        //    45: getfield        net/minecraft/entity/Entity.prevPosX:D
        //    48: aload_1        
        //    49: getfield        net/minecraft/entity/Entity.posX:D
        //    52: aload_1        
        //    53: getfield        net/minecraft/entity/Entity.prevPosX:D
        //    56: dsub           
        //    57: fload_3        
        //    58: f2d            
        //    59: dmul           
        //    60: dadd           
        //    61: dstore          5
        //    63: aload_1        
        //    64: getfield        net/minecraft/entity/Entity.prevPosY:D
        //    67: aload_1        
        //    68: getfield        net/minecraft/entity/Entity.posY:D
        //    71: aload_1        
        //    72: getfield        net/minecraft/entity/Entity.prevPosY:D
        //    75: dsub           
        //    76: fload_3        
        //    77: f2d            
        //    78: dmul           
        //    79: dadd           
        //    80: dstore          7
        //    82: aload_1        
        //    83: getfield        net/minecraft/entity/Entity.prevPosZ:D
        //    86: aload_1        
        //    87: getfield        net/minecraft/entity/Entity.posZ:D
        //    90: aload_1        
        //    91: getfield        net/minecraft/entity/Entity.prevPosZ:D
        //    94: dsub           
        //    95: fload_3        
        //    96: f2d            
        //    97: dmul           
        //    98: dadd           
        //    99: dstore          9
        //   101: aload_0        
        //   102: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   105: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   108: ldc_w           "prepare"
        //   111: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   114: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //   117: aload_0        
        //   118: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   121: aload_0        
        //   122: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   125: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //   128: aload_0        
        //   129: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   132: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   135: aload_0        
        //   136: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   139: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   142: fload_3        
        //   143: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.cacheActiveRenderInfo:(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;F)V
        //   146: aload_0        
        //   147: getfield        net/minecraft/client/renderer/RenderGlobal.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   150: aload_0        
        //   151: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   154: aload_0        
        //   155: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   158: getfield        net/minecraft/client/Minecraft.fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;
        //   161: aload_0        
        //   162: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   165: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   168: aload_0        
        //   169: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   172: getfield        net/minecraft/client/Minecraft.pointedEntity:Lnet/minecraft/entity/Entity;
        //   175: aload_0        
        //   176: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   179: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   182: fload_3        
        //   183: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.cacheActiveRenderInfo:(Lnet/minecraft/world/World;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/settings/GameSettings;F)V
        //   186: aload_0        
        //   187: iconst_0       
        //   188: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesTotal:I
        //   191: aload_0        
        //   192: iconst_0       
        //   193: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //   196: aload_0        
        //   197: iconst_0       
        //   198: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesHidden:I
        //   201: aload_0        
        //   202: iconst_0       
        //   203: putfield        net/minecraft/client/renderer/RenderGlobal.countTileEntitiesRendered:I
        //   206: aload_0        
        //   207: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   210: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   213: astore          11
        //   215: aload           11
        //   217: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   220: aload           11
        //   222: getfield        net/minecraft/entity/Entity.posX:D
        //   225: aload           11
        //   227: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   230: dsub           
        //   231: fload_3        
        //   232: f2d            
        //   233: dmul           
        //   234: dadd           
        //   235: dstore          12
        //   237: aload           11
        //   239: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   242: aload           11
        //   244: getfield        net/minecraft/entity/Entity.posY:D
        //   247: aload           11
        //   249: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   252: dsub           
        //   253: fload_3        
        //   254: f2d            
        //   255: dmul           
        //   256: dadd           
        //   257: dstore          14
        //   259: aload           11
        //   261: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   264: aload           11
        //   266: getfield        net/minecraft/entity/Entity.posZ:D
        //   269: aload           11
        //   271: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   274: dsub           
        //   275: fload_3        
        //   276: f2d            
        //   277: dmul           
        //   278: dadd           
        //   279: dstore          16
        //   281: dload           12
        //   283: putstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerX:D
        //   286: dload           14
        //   288: putstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerY:D
        //   291: dload           16
        //   293: putstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerZ:D
        //   296: aload_0        
        //   297: getfield        net/minecraft/client/renderer/RenderGlobal.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   300: dload           12
        //   302: dload           14
        //   304: dload           16
        //   306: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.setRenderPosition:(DDD)V
        //   309: aload_0        
        //   310: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   313: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //   316: invokevirtual   net/minecraft/client/renderer/EntityRenderer.enableLightmap:()V
        //   319: aload_0        
        //   320: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   323: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   326: ldc_w           "global"
        //   329: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   332: aload_0        
        //   333: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   336: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getLoadedEntityList:()Ljava/util/List;
        //   339: astore          18
        //   341: aload_0        
        //   342: aload           18
        //   344: invokeinterface java/util/List.size:()I
        //   349: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesTotal:I
        //   352: invokestatic    optfine/Config.isFogOff:()Z
        //   355: ifeq            368
        //   358: aload_0        
        //   359: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   362: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //   365: getfield        net/minecraft/client/renderer/EntityRenderer.fogStandard:Z
        //   368: getstatic       optfine/Reflector.ForgeEntity_shouldRenderInPass:Loptfine/ReflectorMethod;
        //   371: invokevirtual   optfine/ReflectorMethod.exists:()Z
        //   374: istore          19
        //   376: getstatic       optfine/Reflector.ForgeTileEntity_shouldRenderInPass:Loptfine/ReflectorMethod;
        //   379: invokevirtual   optfine/ReflectorMethod.exists:()Z
        //   382: istore          20
        //   384: iconst_0       
        //   385: aload_0        
        //   386: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   389: getfield        net/minecraft/client/multiplayer/WorldClient.weatherEffects:Ljava/util/List;
        //   392: invokeinterface java/util/List.size:()I
        //   397: if_icmpge       486
        //   400: aload_0        
        //   401: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   404: getfield        net/minecraft/client/multiplayer/WorldClient.weatherEffects:Ljava/util/List;
        //   407: iconst_0       
        //   408: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   413: checkcast       Lnet/minecraft/entity/Entity;
        //   416: astore          22
        //   418: iload           19
        //   420: ifeq            445
        //   423: aload           22
        //   425: getstatic       optfine/Reflector.ForgeEntity_shouldRenderInPass:Loptfine/ReflectorMethod;
        //   428: iconst_1       
        //   429: anewarray       Ljava/lang/Object;
        //   432: dup            
        //   433: iconst_0       
        //   434: iconst_0       
        //   435: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   438: aastore        
        //   439: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   442: ifeq            480
        //   445: aload_0        
        //   446: dup            
        //   447: getfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //   450: iconst_1       
        //   451: iadd           
        //   452: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //   455: aload           22
        //   457: dload           5
        //   459: dload           7
        //   461: dload           9
        //   463: invokevirtual   net/minecraft/entity/Entity.isInRangeToRender3d:(DDD)Z
        //   466: ifeq            480
        //   469: aload_0        
        //   470: getfield        net/minecraft/client/renderer/RenderGlobal.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   473: aload           22
        //   475: fload_3        
        //   476: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.renderEntitySimple:(Lnet/minecraft/entity/Entity;F)Z
        //   479: pop            
        //   480: iinc            21, 1
        //   483: goto            384
        //   486: aload_0        
        //   487: ifnull          778
        //   490: sipush          519
        //   493: invokestatic    net/minecraft/client/renderer/GlStateManager.depthFunc:(I)V
        //   496: aload_0        
        //   497: getfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineFramebuffer:Lnet/minecraft/client/shader/Framebuffer;
        //   500: invokevirtual   net/minecraft/client/shader/Framebuffer.framebufferClear:()V
        //   503: aload_0        
        //   504: getfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineFramebuffer:Lnet/minecraft/client/shader/Framebuffer;
        //   507: iconst_0       
        //   508: invokevirtual   net/minecraft/client/shader/Framebuffer.bindFramebuffer:(Z)V
        //   511: aload_0        
        //   512: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   515: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   518: ldc_w           "entityOutlines"
        //   521: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   524: aload_0        
        //   525: getfield        net/minecraft/client/renderer/RenderGlobal.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   528: iconst_1       
        //   529: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.setRenderOutlines:(Z)V
        //   532: iconst_0       
        //   533: aload           18
        //   535: invokeinterface java/util/List.size:()I
        //   540: if_icmpge       737
        //   543: aload           18
        //   545: iconst_0       
        //   546: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   551: checkcast       Lnet/minecraft/entity/Entity;
        //   554: astore          22
        //   556: iload           19
        //   558: ifeq            583
        //   561: aload           22
        //   563: getstatic       optfine/Reflector.ForgeEntity_shouldRenderInPass:Loptfine/ReflectorMethod;
        //   566: iconst_1       
        //   567: anewarray       Ljava/lang/Object;
        //   570: dup            
        //   571: iconst_0       
        //   572: iconst_0       
        //   573: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   576: aastore        
        //   577: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   580: ifeq            731
        //   583: aload_0        
        //   584: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   587: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   590: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   593: ifeq            616
        //   596: aload_0        
        //   597: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   600: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   603: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   606: invokevirtual   net/minecraft/entity/EntityLivingBase.isPlayerSleeping:()Z
        //   609: ifeq            616
        //   612: iconst_1       
        //   613: goto            617
        //   616: iconst_0       
        //   617: istore          23
        //   619: aload           22
        //   621: dload           5
        //   623: dload           7
        //   625: dload           9
        //   627: invokevirtual   net/minecraft/entity/Entity.isInRangeToRender3d:(DDD)Z
        //   630: ifeq            682
        //   633: aload           22
        //   635: getfield        net/minecraft/entity/Entity.ignoreFrustumCheck:Z
        //   638: ifne            670
        //   641: aload_2        
        //   642: aload           22
        //   644: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   647: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //   652: ifne            670
        //   655: aload           22
        //   657: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   660: aload_0        
        //   661: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   664: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   667: if_acmpne       682
        //   670: aload           22
        //   672: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   675: ifeq            682
        //   678: iconst_1       
        //   679: goto            683
        //   682: iconst_0       
        //   683: istore          24
        //   685: aload           22
        //   687: aload_0        
        //   688: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   691: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   694: if_acmpne       715
        //   697: aload_0        
        //   698: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   701: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   704: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //   707: ifne            715
        //   710: iload           23
        //   712: ifeq            731
        //   715: iload           24
        //   717: ifeq            731
        //   720: aload_0        
        //   721: getfield        net/minecraft/client/renderer/RenderGlobal.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   724: aload           22
        //   726: fload_3        
        //   727: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.renderEntitySimple:(Lnet/minecraft/entity/Entity;F)Z
        //   730: pop            
        //   731: iinc            21, 1
        //   734: goto            532
        //   737: aload_0        
        //   738: getfield        net/minecraft/client/renderer/RenderGlobal.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   741: iconst_0       
        //   742: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.setRenderOutlines:(Z)V
        //   745: iconst_0       
        //   746: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   749: aload_0        
        //   750: getfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineShader:Lnet/minecraft/client/shader/ShaderGroup;
        //   753: fload_3        
        //   754: invokevirtual   net/minecraft/client/shader/ShaderGroup.loadShaderGroup:(F)V
        //   757: iconst_1       
        //   758: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   761: aload_0        
        //   762: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   765: invokevirtual   net/minecraft/client/Minecraft.getFramebuffer:()Lnet/minecraft/client/shader/Framebuffer;
        //   768: iconst_0       
        //   769: invokevirtual   net/minecraft/client/shader/Framebuffer.bindFramebuffer:(Z)V
        //   772: sipush          515
        //   775: invokestatic    net/minecraft/client/renderer/GlStateManager.depthFunc:(I)V
        //   778: aload_0        
        //   779: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   782: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   785: ldc_w           "entities"
        //   788: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   791: aload_0        
        //   792: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //   795: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   800: astore          21
        //   802: aload_0        
        //   803: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   806: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   809: getfield        net/minecraft/client/settings/GameSettings.fancyGraphics:Z
        //   812: istore          22
        //   814: aload_0        
        //   815: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   818: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   821: invokestatic    optfine/Config.isDroppedItemsFancy:()Z
        //   824: putfield        net/minecraft/client/settings/GameSettings.fancyGraphics:Z
        //   827: aload           21
        //   829: invokeinterface java/util/Iterator.hasNext:()Z
        //   834: ifeq            1196
        //   837: aload           21
        //   839: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   844: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   847: astore          23
        //   849: aload_0        
        //   850: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   853: aload           23
        //   855: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   858: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.getPosition:()Lnet/minecraft/util/BlockPos;
        //   861: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //   864: astore          24
        //   866: aload           24
        //   868: invokevirtual   net/minecraft/world/chunk/Chunk.getEntityLists:()[Lnet/minecraft/util/ClassInheritanceMultiMap;
        //   871: aload           23
        //   873: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   876: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.getPosition:()Lnet/minecraft/util/BlockPos;
        //   879: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   882: bipush          16
        //   884: idiv           
        //   885: aaload         
        //   886: astore          25
        //   888: aload           25
        //   890: invokevirtual   net/minecraft/util/ClassInheritanceMultiMap.isEmpty:()Z
        //   893: ifne            1193
        //   896: aload           25
        //   898: invokevirtual   net/minecraft/util/ClassInheritanceMultiMap.iterator:()Ljava/util/Iterator;
        //   901: astore          26
        //   903: aload           26
        //   905: invokeinterface java/util/Iterator.hasNext:()Z
        //   910: ifne            916
        //   913: goto            827
        //   916: aload           26
        //   918: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   923: checkcast       Lnet/minecraft/entity/Entity;
        //   926: astore          27
        //   928: iload           19
        //   930: ifeq            955
        //   933: aload           27
        //   935: getstatic       optfine/Reflector.ForgeEntity_shouldRenderInPass:Loptfine/ReflectorMethod;
        //   938: iconst_1       
        //   939: anewarray       Ljava/lang/Object;
        //   942: dup            
        //   943: iconst_0       
        //   944: iconst_0       
        //   945: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   948: aastore        
        //   949: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //   952: ifeq            903
        //   955: aload_0        
        //   956: getfield        net/minecraft/client/renderer/RenderGlobal.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //   959: aload           27
        //   961: aload_2        
        //   962: dload           5
        //   964: dload           7
        //   966: dload           9
        //   968: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.shouldRender:(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z
        //   971: ifne            989
        //   974: aload           27
        //   976: getfield        net/minecraft/entity/Entity.riddenByEntity:Lnet/minecraft/entity/Entity;
        //   979: aload_0        
        //   980: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   983: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   986: if_acmpne       993
        //   989: iconst_1       
        //   990: goto            994
        //   993: iconst_0       
        //   994: istore          28
        //   996: iload           28
        //   998: ifne            1004
        //  1001: goto            1164
        //  1004: aload_0        
        //  1005: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1008: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //  1011: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //  1014: ifeq            1033
        //  1017: aload_0        
        //  1018: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1021: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //  1024: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //  1027: invokevirtual   net/minecraft/entity/EntityLivingBase.isPlayerSleeping:()Z
        //  1030: goto            1034
        //  1033: iconst_0       
        //  1034: istore          29
        //  1036: aload           27
        //  1038: aload_0        
        //  1039: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1042: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //  1045: if_acmpne       1066
        //  1048: aload_0        
        //  1049: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1052: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1055: getfield        net/minecraft/client/settings/GameSettings.thirdPersonView:I
        //  1058: ifne            1066
        //  1061: iload           29
        //  1063: ifeq            1161
        //  1066: aload           27
        //  1068: getfield        net/minecraft/entity/Entity.posY:D
        //  1071: dconst_0       
        //  1072: dcmpg          
        //  1073: iflt            1107
        //  1076: aload           27
        //  1078: getfield        net/minecraft/entity/Entity.posY:D
        //  1081: ldc2_w          256.0
        //  1084: dcmpl          
        //  1085: ifge            1107
        //  1088: aload_0        
        //  1089: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1092: new             Lnet/minecraft/util/BlockPos;
        //  1095: dup            
        //  1096: aload           27
        //  1098: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  1101: invokevirtual   net/minecraft/client/multiplayer/WorldClient.isBlockLoaded:(Lnet/minecraft/util/BlockPos;)Z
        //  1104: ifeq            1161
        //  1107: aload_0        
        //  1108: dup            
        //  1109: getfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //  1112: iconst_1       
        //  1113: iadd           
        //  1114: putfield        net/minecraft/client/renderer/RenderGlobal.countEntitiesRendered:I
        //  1117: aload           27
        //  1119: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //  1122: ldc_w           Lnet/minecraft/entity/item/EntityItemFrame;.class
        //  1125: if_acmpne       1136
        //  1128: aload           27
        //  1130: ldc2_w          0.06
        //  1133: putfield        net/minecraft/entity/Entity.renderDistanceWeight:D
        //  1136: aload_0        
        //  1137: aload           27
        //  1139: putfield        net/minecraft/client/renderer/RenderGlobal.renderedEntity:Lnet/minecraft/entity/Entity;
        //  1142: aload_0        
        //  1143: getfield        net/minecraft/client/renderer/RenderGlobal.renderManager:Lnet/minecraft/client/renderer/entity/RenderManager;
        //  1146: aload           27
        //  1148: fload_3        
        //  1149: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.renderEntitySimple:(Lnet/minecraft/entity/Entity;F)Z
        //  1152: pop            
        //  1153: aload_0        
        //  1154: aconst_null    
        //  1155: putfield        net/minecraft/client/renderer/RenderGlobal.renderedEntity:Lnet/minecraft/entity/Entity;
        //  1158: goto            1164
        //  1161: goto            903
        //  1164: iload           28
        //  1166: ifne            1190
        //  1169: aload           27
        //  1171: instanceof      Lnet/minecraft/entity/projectile/EntityWitherSkull;
        //  1174: ifeq            1190
        //  1177: aload_0        
        //  1178: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1181: invokevirtual   net/minecraft/client/Minecraft.getRenderManager:()Lnet/minecraft/client/renderer/entity/RenderManager;
        //  1184: aload           27
        //  1186: fload_3        
        //  1187: invokevirtual   net/minecraft/client/renderer/entity/RenderManager.renderWitherSkull:(Lnet/minecraft/entity/Entity;F)V
        //  1190: goto            903
        //  1193: goto            827
        //  1196: aload_0        
        //  1197: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1200: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1203: iload           22
        //  1205: putfield        net/minecraft/client/settings/GameSettings.fancyGraphics:Z
        //  1208: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1211: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.getFontRenderer:()Lnet/minecraft/client/gui/FontRenderer;
        //  1214: astore          23
        //  1216: aload_0        
        //  1217: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1220: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //  1223: ldc_w           "blockentities"
        //  1226: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //  1229: aload_0        
        //  1230: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //  1233: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1238: astore          24
        //  1240: aload           24
        //  1242: invokeinterface java/util/Iterator.hasNext:()Z
        //  1247: ifeq            1491
        //  1250: aload           24
        //  1252: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1257: astore          25
        //  1259: aload           25
        //  1261: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1264: astore          26
        //  1266: aload           26
        //  1268: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1271: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.getCompiledChunk:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //  1274: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.getTileEntities:()Ljava/util/List;
        //  1277: astore          27
        //  1279: aload           27
        //  1281: invokeinterface java/util/List.isEmpty:()Z
        //  1286: ifne            1488
        //  1289: aload           27
        //  1291: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1296: astore          28
        //  1298: aload           28
        //  1300: invokeinterface java/util/Iterator.hasNext:()Z
        //  1305: ifne            1311
        //  1308: goto            1240
        //  1311: aload           28
        //  1313: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1318: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //  1321: astore          29
        //  1323: iload           20
        //  1325: ifne            1331
        //  1328: goto            1392
        //  1331: aload           29
        //  1333: getstatic       optfine/Reflector.ForgeTileEntity_shouldRenderInPass:Loptfine/ReflectorMethod;
        //  1336: iconst_1       
        //  1337: anewarray       Ljava/lang/Object;
        //  1340: dup            
        //  1341: iconst_0       
        //  1342: iconst_0       
        //  1343: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1346: aastore        
        //  1347: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1350: ifeq            1298
        //  1353: aload           29
        //  1355: getstatic       optfine/Reflector.ForgeTileEntity_getRenderBoundingBox:Loptfine/ReflectorMethod;
        //  1358: iconst_0       
        //  1359: anewarray       Ljava/lang/Object;
        //  1362: invokestatic    optfine/Reflector.call:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //  1365: checkcast       Lnet/minecraft/util/AxisAlignedBB;
        //  1368: astore          30
        //  1370: aload           30
        //  1372: ifnull          1392
        //  1375: aload_2        
        //  1376: aload           30
        //  1378: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  1383: ifeq            1389
        //  1386: goto            1392
        //  1389: goto            1298
        //  1392: aload           29
        //  1394: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //  1397: astore          30
        //  1399: aload           30
        //  1401: ldc_w           Lnet/minecraft/tileentity/TileEntitySign;.class
        //  1404: if_acmpne       1459
        //  1407: getstatic       optfine/Config.zoomMode:Z
        //  1410: ifne            1459
        //  1413: aload_0        
        //  1414: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1417: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1420: astore          31
        //  1422: aload           29
        //  1424: aload           31
        //  1426: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //  1429: aload           31
        //  1431: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //  1434: aload           31
        //  1436: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //  1439: invokevirtual   net/minecraft/tileentity/TileEntity.getDistanceSq:(DDD)D
        //  1442: dstore          32
        //  1444: dload           32
        //  1446: ldc2_w          256.0
        //  1449: dcmpl          
        //  1450: ifle            1459
        //  1453: aload           23
        //  1455: iconst_0       
        //  1456: putfield        net/minecraft/client/gui/FontRenderer.enabled:Z
        //  1459: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1462: aload           29
        //  1464: fload_3        
        //  1465: iconst_m1      
        //  1466: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.renderTileEntity:(Lnet/minecraft/tileentity/TileEntity;FI)V
        //  1469: aload_0        
        //  1470: dup            
        //  1471: getfield        net/minecraft/client/renderer/RenderGlobal.countTileEntitiesRendered:I
        //  1474: iconst_1       
        //  1475: iadd           
        //  1476: putfield        net/minecraft/client/renderer/RenderGlobal.countTileEntitiesRendered:I
        //  1479: aload           23
        //  1481: iconst_1       
        //  1482: putfield        net/minecraft/client/gui/FontRenderer.enabled:Z
        //  1485: goto            1298
        //  1488: goto            1240
        //  1491: aload_0        
        //  1492: getfield        net/minecraft/client/renderer/RenderGlobal.field_181024_n:Ljava/util/Set;
        //  1495: astore          24
        //  1497: aload_0        
        //  1498: getfield        net/minecraft/client/renderer/RenderGlobal.field_181024_n:Ljava/util/Set;
        //  1501: dup            
        //  1502: astore          25
        //  1504: monitorenter   
        //  1505: aload_0        
        //  1506: getfield        net/minecraft/client/renderer/RenderGlobal.field_181024_n:Ljava/util/Set;
        //  1509: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //  1514: astore          26
        //  1516: aload           26
        //  1518: invokeinterface java/util/Iterator.hasNext:()Z
        //  1523: ifeq            1693
        //  1526: aload           26
        //  1528: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1533: astore          27
        //  1535: iload           20
        //  1537: ifeq            1601
        //  1540: aload           27
        //  1542: getstatic       optfine/Reflector.ForgeTileEntity_shouldRenderInPass:Loptfine/ReflectorMethod;
        //  1545: iconst_1       
        //  1546: anewarray       Ljava/lang/Object;
        //  1549: dup            
        //  1550: iconst_0       
        //  1551: iconst_0       
        //  1552: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1555: aastore        
        //  1556: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1559: ifne            1565
        //  1562: goto            1516
        //  1565: aload           27
        //  1567: getstatic       optfine/Reflector.ForgeTileEntity_getRenderBoundingBox:Loptfine/ReflectorMethod;
        //  1570: iconst_0       
        //  1571: anewarray       Ljava/lang/Object;
        //  1574: invokestatic    optfine/Reflector.call:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //  1577: checkcast       Lnet/minecraft/util/AxisAlignedBB;
        //  1580: astore          28
        //  1582: aload           28
        //  1584: ifnull          1601
        //  1587: aload_2        
        //  1588: aload           28
        //  1590: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  1595: ifne            1601
        //  1598: goto            1516
        //  1601: aload           27
        //  1603: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //  1606: astore          28
        //  1608: aload           28
        //  1610: ldc_w           Lnet/minecraft/tileentity/TileEntitySign;.class
        //  1613: if_acmpne       1671
        //  1616: getstatic       optfine/Config.zoomMode:Z
        //  1619: ifne            1671
        //  1622: aload_0        
        //  1623: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1626: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1629: astore          29
        //  1631: aload           27
        //  1633: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //  1636: aload           29
        //  1638: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //  1641: aload           29
        //  1643: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //  1646: aload           29
        //  1648: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //  1651: invokevirtual   net/minecraft/tileentity/TileEntity.getDistanceSq:(DDD)D
        //  1654: dstore          30
        //  1656: dload           30
        //  1658: ldc2_w          256.0
        //  1661: dcmpl          
        //  1662: ifle            1671
        //  1665: aload           23
        //  1667: iconst_0       
        //  1668: putfield        net/minecraft/client/gui/FontRenderer.enabled:Z
        //  1671: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1674: aload           27
        //  1676: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //  1679: fload_3        
        //  1680: iconst_m1      
        //  1681: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.renderTileEntity:(Lnet/minecraft/tileentity/TileEntity;FI)V
        //  1684: aload           23
        //  1686: iconst_1       
        //  1687: putfield        net/minecraft/client/gui/FontRenderer.enabled:Z
        //  1690: goto            1516
        //  1693: aload           25
        //  1695: monitorexit    
        //  1696: goto            1707
        //  1699: astore          34
        //  1701: aload           25
        //  1703: monitorexit    
        //  1704: aload           34
        //  1706: athrow         
        //  1707: aload_0        
        //  1708: invokespecial   net/minecraft/client/renderer/RenderGlobal.preRenderDamagedBlocks:()V
        //  1711: aload_0        
        //  1712: getfield        net/minecraft/client/renderer/RenderGlobal.damagedBlocks:Ljava/util/Map;
        //  1715: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //  1720: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //  1725: astore          25
        //  1727: aload           25
        //  1729: invokeinterface java/util/Iterator.hasNext:()Z
        //  1734: ifeq            2008
        //  1737: aload           25
        //  1739: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1744: astore          26
        //  1746: aload           26
        //  1748: checkcast       Lnet/minecraft/client/renderer/DestroyBlockProgress;
        //  1751: invokevirtual   net/minecraft/client/renderer/DestroyBlockProgress.getPosition:()Lnet/minecraft/util/BlockPos;
        //  1754: astore          27
        //  1756: aload_0        
        //  1757: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1760: aload           27
        //  1762: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //  1765: astore          28
        //  1767: aload           28
        //  1769: instanceof      Lnet/minecraft/tileentity/TileEntityChest;
        //  1772: ifeq            1843
        //  1775: aload           28
        //  1777: checkcast       Lnet/minecraft/tileentity/TileEntityChest;
        //  1780: astore          29
        //  1782: aload           29
        //  1784: getfield        net/minecraft/tileentity/TileEntityChest.adjacentChestXNeg:Lnet/minecraft/tileentity/TileEntityChest;
        //  1787: ifnull          1814
        //  1790: aload           27
        //  1792: getstatic       net/minecraft/util/EnumFacing.WEST:Lnet/minecraft/util/EnumFacing;
        //  1795: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //  1798: astore          27
        //  1800: aload_0        
        //  1801: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1804: aload           27
        //  1806: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //  1809: astore          28
        //  1811: goto            1843
        //  1814: aload           29
        //  1816: getfield        net/minecraft/tileentity/TileEntityChest.adjacentChestZNeg:Lnet/minecraft/tileentity/TileEntityChest;
        //  1819: ifnull          1843
        //  1822: aload           27
        //  1824: getstatic       net/minecraft/util/EnumFacing.NORTH:Lnet/minecraft/util/EnumFacing;
        //  1827: invokevirtual   net/minecraft/util/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
        //  1830: astore          27
        //  1832: aload_0        
        //  1833: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1836: aload           27
        //  1838: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //  1841: astore          28
        //  1843: aload_0        
        //  1844: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1847: aload           27
        //  1849: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  1852: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  1857: astore          29
        //  1859: iload           20
        //  1861: ifeq            1941
        //  1864: aload           28
        //  1866: ifnull          1985
        //  1869: aload           28
        //  1871: getstatic       optfine/Reflector.ForgeTileEntity_shouldRenderInPass:Loptfine/ReflectorMethod;
        //  1874: iconst_1       
        //  1875: anewarray       Ljava/lang/Object;
        //  1878: dup            
        //  1879: iconst_0       
        //  1880: iconst_0       
        //  1881: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //  1884: aastore        
        //  1885: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1888: ifeq            1985
        //  1891: aload           28
        //  1893: getstatic       optfine/Reflector.ForgeTileEntity_canRenderBreaking:Loptfine/ReflectorMethod;
        //  1896: iconst_0       
        //  1897: anewarray       Ljava/lang/Object;
        //  1900: invokestatic    optfine/Reflector.callBoolean:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
        //  1903: ifeq            1985
        //  1906: aload           28
        //  1908: getstatic       optfine/Reflector.ForgeTileEntity_getRenderBoundingBox:Loptfine/ReflectorMethod;
        //  1911: iconst_0       
        //  1912: anewarray       Ljava/lang/Object;
        //  1915: invokestatic    optfine/Reflector.call:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //  1918: checkcast       Lnet/minecraft/util/AxisAlignedBB;
        //  1921: astore          31
        //  1923: aload           31
        //  1925: ifnull          1938
        //  1928: aload_2        
        //  1929: aload           31
        //  1931: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  1936: istore          30
        //  1938: goto            2005
        //  1941: aload           28
        //  1943: ifnull          1982
        //  1946: aload           29
        //  1948: instanceof      Lnet/minecraft/block/BlockChest;
        //  1951: ifne            1978
        //  1954: aload           29
        //  1956: instanceof      Lnet/minecraft/block/BlockEnderChest;
        //  1959: ifne            1978
        //  1962: aload           29
        //  1964: instanceof      Lnet/minecraft/block/BlockSign;
        //  1967: ifne            1978
        //  1970: aload           29
        //  1972: instanceof      Lnet/minecraft/block/BlockSkull;
        //  1975: ifeq            1982
        //  1978: iconst_1       
        //  1979: goto            1983
        //  1982: iconst_0       
        //  1983: istore          30
        //  1985: goto            1727
        //  1988: getstatic       net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance:Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
        //  1991: aload           28
        //  1993: fload_3        
        //  1994: aload           26
        //  1996: checkcast       Lnet/minecraft/client/renderer/DestroyBlockProgress;
        //  1999: invokevirtual   net/minecraft/client/renderer/DestroyBlockProgress.getPartialBlockDamage:()I
        //  2002: invokevirtual   net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.renderTileEntity:(Lnet/minecraft/tileentity/TileEntity;FI)V
        //  2005: goto            1727
        //  2008: aload_0        
        //  2009: invokespecial   net/minecraft/client/renderer/RenderGlobal.postRenderDamagedBlocks:()V
        //  2012: aload_0        
        //  2013: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  2016: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  2019: invokevirtual   net/minecraft/client/renderer/EntityRenderer.disableLightmap:()V
        //  2022: aload_0        
        //  2023: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  2026: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  2029: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //  2032: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0368 (coming from #0365).
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
    
    public void drawSelectionBox(final EntityPlayer entityPlayer, final MovingObjectPosition movingObjectPosition, final int n, final float n2) {
        if (n == 0 && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(0.0f, 0.0f, 0.0f, 0.4f);
            GL11.glLineWidth(2.0f);
            GlStateManager.depthMask(false);
            final BlockPos blockPos = movingObjectPosition.getBlockPos();
            final Block block = this.theWorld.getBlockState(blockPos).getBlock();
            if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockPos)) {
                block.setBlockBoundsBasedOnState(this.theWorld, blockPos);
                func_181561_a(block.getSelectedBoundingBox(this.theWorld, blockPos).expand(0.0020000000949949026, 0.0020000000949949026, 0.0020000000949949026).offset(-(entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * n2), -(entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * n2), -(entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * n2)));
            }
            GlStateManager.depthMask(true);
        }
    }
    
    protected void stopChunkUpdates() {
        this.chunksToUpdate.clear();
        this.renderDispatcher.stopChunkUpdates();
    }
    
    @Override
    public void playAuxSFX(final EntityPlayer entityPlayer, final int n, final BlockPos blockPos, final int n2) {
        final Random rand = this.theWorld.rand;
        switch (n) {
            case 1000: {
                this.theWorld.playSoundAtPos(blockPos, "random.click", 1.0f, 1.0f, false);
                break;
            }
            case 1001: {
                this.theWorld.playSoundAtPos(blockPos, "random.click", 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.theWorld.playSoundAtPos(blockPos, "random.bow", 1.0f, 1.2f, false);
                break;
            }
            case 1003: {
                this.theWorld.playSoundAtPos(blockPos, "random.door_open", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1004: {
                this.theWorld.playSoundAtPos(blockPos, "random.fizz", 0.5f, 2.6f + (rand.nextFloat() - rand.nextFloat()) * 0.8f, false);
                break;
            }
            case 1005: {
                if (Item.getItemById(n2) instanceof ItemRecord) {
                    this.theWorld.playRecord(blockPos, "records." + ((ItemRecord)Item.getItemById(n2)).recordName);
                    break;
                }
                this.theWorld.playRecord(blockPos, null);
                break;
            }
            case 1006: {
                this.theWorld.playSoundAtPos(blockPos, "random.door_close", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1007: {
                this.theWorld.playSoundAtPos(blockPos, "mob.ghast.charge", 10.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1008: {
                this.theWorld.playSoundAtPos(blockPos, "mob.ghast.fireball", 10.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1009: {
                this.theWorld.playSoundAtPos(blockPos, "mob.ghast.fireball", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1010: {
                this.theWorld.playSoundAtPos(blockPos, "mob.zombie.wood", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1011: {
                this.theWorld.playSoundAtPos(blockPos, "mob.zombie.metal", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1012: {
                this.theWorld.playSoundAtPos(blockPos, "mob.zombie.woodbreak", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1014: {
                this.theWorld.playSoundAtPos(blockPos, "mob.wither.shoot", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1015: {
                this.theWorld.playSoundAtPos(blockPos, "mob.bat.takeoff", 0.05f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.theWorld.playSoundAtPos(blockPos, "mob.zombie.infect", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.theWorld.playSoundAtPos(blockPos, "mob.zombie.unfect", 2.0f, (rand.nextFloat() - rand.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.theWorld.playSoundAtPos(blockPos, "random.anvil_break", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1021: {
                this.theWorld.playSoundAtPos(blockPos, "random.anvil_use", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1022: {
                this.theWorld.playSoundAtPos(blockPos, "random.anvil_land", 0.3f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2000: {
                final int n3 = n2 % 3 - 1;
                final int n4 = n2 / 3 % 3 - 1;
                final double n5 = blockPos.getX() + n3 * 0.6 + 0.5;
                final double n6 = blockPos.getY() + 0.5;
                final double n7 = blockPos.getZ() + n4 * 0.6 + 0.5;
                while (true) {
                    final double n8 = rand.nextDouble() * 0.2 + 0.01;
                    this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n5 + n3 * 0.01 + (rand.nextDouble() - 0.5) * n4 * 0.5, n6 + (rand.nextDouble() - 0.5) * 0.5, n7 + n4 * 0.01 + (rand.nextDouble() - 0.5) * n3 * 0.5, n3 * n8 + rand.nextGaussian() * 0.01, -0.03 + rand.nextGaussian() * 0.01, n4 * n8 + rand.nextGaussian() * 0.01, new int[0]);
                    int n9 = 0;
                    ++n9;
                }
                break;
            }
            case 2001: {
                final Block blockById = Block.getBlockById(n2 & 0xFFF);
                if (blockById.getMaterial() != Material.air) {
                    this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(blockById.stepSound.getBreakSound()), (blockById.stepSound.getVolume() + 1.0f) / 2.0f, blockById.stepSound.getFrequency() * 0.8f, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f));
                }
                this.mc.effectRenderer.addBlockDestroyEffects(blockPos, blockById.getStateFromMeta(n2 >> 12 & 0xFF));
                break;
            }
            case 2002: {
                final double n10 = blockPos.getX();
                final double n11 = blockPos.getY();
                final double n12 = blockPos.getZ();
                while (true) {
                    this.spawnParticle(EnumParticleTypes.ITEM_CRACK, n10, n11, n12, rand.nextGaussian() * 0.15, rand.nextDouble() * 0.2, rand.nextGaussian() * 0.15, Item.getIdFromItem(Items.potionitem), n2);
                    int n13 = 0;
                    ++n13;
                }
                break;
            }
            case 2003: {
                final double n14 = blockPos.getX() + 0.5;
                final double n15 = blockPos.getY();
                final double n16 = blockPos.getZ() + 0.5;
                while (true) {
                    this.spawnParticle(EnumParticleTypes.ITEM_CRACK, n14, n15, n16, rand.nextGaussian() * 0.15, rand.nextDouble() * 0.2, rand.nextGaussian() * 0.15, Item.getIdFromItem(Items.ender_eye));
                    int n17 = 0;
                    ++n17;
                }
                break;
            }
            case 2004: {
                while (true) {
                    final double n18 = blockPos.getX() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double n19 = blockPos.getY() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double n20 = blockPos.getZ() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n18, n19, n20, 0.0, 0.0, 0.0, new int[0]);
                    this.theWorld.spawnParticle(EnumParticleTypes.FLAME, n18, n19, n20, 0.0, 0.0, 0.0, new int[0]);
                    int n17 = 0;
                    ++n17;
                }
                break;
            }
            case 2005: {
                ItemDye.spawnBonemealParticles(this.theWorld, blockPos, n2);
                break;
            }
        }
    }
    
    private void renderSkyEnd() {
        if (!Config.isSkyEnabled()) {
            return;
        }
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.depthMask(false);
        this.renderEngine.bindTexture(RenderGlobal.locationEndSkyPng);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        while (true) {
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(-100.0, -100.0, -100.0).tex(0.0, 0.0).color(40, 40, 40, 255).endVertex();
            worldRenderer.pos(-100.0, -100.0, 100.0).tex(0.0, 16.0).color(40, 40, 40, 255).endVertex();
            worldRenderer.pos(100.0, -100.0, 100.0).tex(16.0, 16.0).color(40, 40, 40, 255).endVertex();
            worldRenderer.pos(100.0, -100.0, -100.0).tex(16.0, 0.0).color(40, 40, 40, 255).endVertex();
            instance.draw();
            int n = 0;
            ++n;
        }
    }
    
    private void generateSky() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.skyVBO != null) {
            this.skyVBO.deleteGlBuffers();
        }
        if (this.glSkyList >= 0) {
            GLAllocation.deleteDisplayLists(this.glSkyList);
            this.glSkyList = -1;
        }
        if (this.vboEnabled) {
            this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderSky(worldRenderer, 16.0f, false);
            worldRenderer.finishDrawing();
            worldRenderer.reset();
            this.skyVBO.func_181722_a(worldRenderer.getByteBuffer());
        }
        else {
            GL11.glNewList(this.glSkyList = GLAllocation.generateDisplayLists(1), 4864);
            this.renderSky(worldRenderer, 16.0f, false);
            instance.draw();
            GL11.glEndList();
        }
    }
    
    public RenderGlobal(final Minecraft mc) {
        this.chunksToUpdate = Sets.newLinkedHashSet();
        this.renderInfos = Lists.newArrayListWithCapacity(69696);
        this.field_181024_n = Sets.newHashSet();
        this.starGLCallList = -1;
        this.glSkyList = -1;
        this.glSkyList2 = -1;
        this.damagedBlocks = Maps.newHashMap();
        this.mapSoundPositions = Maps.newHashMap();
        this.destroyBlockIcons = new TextureAtlasSprite[10];
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.lastViewEntityX = Double.MIN_VALUE;
        this.lastViewEntityY = Double.MIN_VALUE;
        this.lastViewEntityZ = Double.MIN_VALUE;
        this.lastViewEntityPitch = Double.MIN_VALUE;
        this.lastViewEntityYaw = Double.MIN_VALUE;
        this.renderDispatcher = new ChunkRenderDispatcher();
        this.renderDistanceChunks = -1;
        this.renderEntitiesStartupCounter = 2;
        this.debugFixTerrainFrustum = false;
        this.debugTerrainMatrix = new Vector4f[8];
        this.debugTerrainFrustumPosition = new Vector3d();
        this.vboEnabled = false;
        this.displayListEntitiesDirty = true;
        this.chunksToResortTransparency = new LinkedHashSet();
        this.chunksToUpdateForced = new LinkedHashSet();
        this.visibilityDeque = new ArrayDeque();
        this.renderInfosEntities = new ArrayList(1024);
        this.renderInfosTileEntities = new ArrayList(1024);
        this.renderDistance = 0;
        this.renderDistanceSq = 0;
        this.cloudRenderer = new CloudRenderer(mc);
        this.mc = mc;
        this.renderManager = mc.getRenderManager();
        (this.renderEngine = mc.getTextureManager()).bindTexture(RenderGlobal.locationForcefieldPng);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GlStateManager.bindTexture(0);
        this.updateDestroyBlockIcons();
        this.vboEnabled = OpenGlHelper.useVbo();
        if (this.vboEnabled) {
            this.renderContainer = new VboRenderList();
            this.renderChunkFactory = new VboChunkFactory();
        }
        else {
            this.renderContainer = new RenderList();
            this.renderChunkFactory = new ListChunkFactory();
        }
        (this.vertexBufferFormat = new VertexFormat()).func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
        this.generateStars();
        this.generateSky();
        this.generateSky2();
    }
    
    @Override
    public void broadcastSound(final int n, final BlockPos blockPos, final int n2) {
        switch (n) {
            case 1013:
            case 1018: {
                if (this.mc.getRenderViewEntity() == null) {
                    break;
                }
                final double n3 = blockPos.getX() - this.mc.getRenderViewEntity().posX;
                final double n4 = blockPos.getY() - this.mc.getRenderViewEntity().posY;
                final double n5 = blockPos.getZ() - this.mc.getRenderViewEntity().posZ;
                final double sqrt = Math.sqrt(n3 * n3 + n4 * n4 + n5 * n5);
                double posX = this.mc.getRenderViewEntity().posX;
                double posY = this.mc.getRenderViewEntity().posY;
                double posZ = this.mc.getRenderViewEntity().posZ;
                if (sqrt > 0.0) {
                    posX += n3 / sqrt * 2.0;
                    posY += n4 / sqrt * 2.0;
                    posZ += n5 / sqrt * 2.0;
                }
                if (n == 1013) {
                    this.theWorld.playSound(posX, posY, posZ, "mob.wither.spawn", 1.0f, 1.0f, false);
                    break;
                }
                this.theWorld.playSound(posX, posY, posZ, "mob.enderdragon.end", 5.0f, 1.0f, false);
                break;
            }
        }
    }
    
    public void renderEntityOutlineFramebuffer() {
        if (this != null) {
            GL11.glPushMatrix();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
            GL11.glEnable(2929);
            GL11.glEnable(2896);
            GL11.glPopMatrix();
        }
    }
    
    public void resetClouds() {
        this.cloudRenderer.reset();
    }
    
    @Override
    public void onEntityRemoved(final Entity entity) {
    }
    
    public void setupTerrain(final Entity p0, final double p1, final ICamera p2, final int p3, final boolean p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //     7: getfield        net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //    10: aload_0        
        //    11: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //    14: if_icmpeq       21
        //    17: aload_0        
        //    18: invokevirtual   net/minecraft/client/renderer/RenderGlobal.loadRenderers:()V
        //    21: aload_0        
        //    22: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    25: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //    28: ldc_w           "camera"
        //    31: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //    34: aload_1        
        //    35: getfield        net/minecraft/entity/Entity.posX:D
        //    38: aload_0        
        //    39: getfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosX:D
        //    42: dsub           
        //    43: dstore          7
        //    45: aload_1        
        //    46: getfield        net/minecraft/entity/Entity.posY:D
        //    49: aload_0        
        //    50: getfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosY:D
        //    53: dsub           
        //    54: dstore          9
        //    56: aload_1        
        //    57: getfield        net/minecraft/entity/Entity.posZ:D
        //    60: aload_0        
        //    61: getfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosZ:D
        //    64: dsub           
        //    65: dstore          11
        //    67: aload_0        
        //    68: getfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosChunkX:I
        //    71: aload_1        
        //    72: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //    75: if_icmpne       124
        //    78: aload_0        
        //    79: getfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosChunkY:I
        //    82: aload_1        
        //    83: getfield        net/minecraft/entity/Entity.chunkCoordY:I
        //    86: if_icmpne       124
        //    89: aload_0        
        //    90: getfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosChunkZ:I
        //    93: aload_1        
        //    94: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //    97: if_icmpne       124
        //   100: dload           7
        //   102: dload           7
        //   104: dmul           
        //   105: dload           9
        //   107: dload           9
        //   109: dmul           
        //   110: dadd           
        //   111: dload           11
        //   113: dload           11
        //   115: dmul           
        //   116: dadd           
        //   117: ldc2_w          16.0
        //   120: dcmpl          
        //   121: ifle            187
        //   124: aload_0        
        //   125: aload_1        
        //   126: getfield        net/minecraft/entity/Entity.posX:D
        //   129: putfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosX:D
        //   132: aload_0        
        //   133: aload_1        
        //   134: getfield        net/minecraft/entity/Entity.posY:D
        //   137: putfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosY:D
        //   140: aload_0        
        //   141: aload_1        
        //   142: getfield        net/minecraft/entity/Entity.posZ:D
        //   145: putfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosZ:D
        //   148: aload_0        
        //   149: aload_1        
        //   150: getfield        net/minecraft/entity/Entity.chunkCoordX:I
        //   153: putfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosChunkX:I
        //   156: aload_0        
        //   157: aload_1        
        //   158: getfield        net/minecraft/entity/Entity.chunkCoordY:I
        //   161: putfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosChunkY:I
        //   164: aload_0        
        //   165: aload_1        
        //   166: getfield        net/minecraft/entity/Entity.chunkCoordZ:I
        //   169: putfield        net/minecraft/client/renderer/RenderGlobal.frustumUpdatePosChunkZ:I
        //   172: aload_0        
        //   173: getfield        net/minecraft/client/renderer/RenderGlobal.viewFrustum:Lnet/minecraft/client/renderer/ViewFrustum;
        //   176: aload_1        
        //   177: getfield        net/minecraft/entity/Entity.posX:D
        //   180: aload_1        
        //   181: getfield        net/minecraft/entity/Entity.posZ:D
        //   184: invokevirtual   net/minecraft/client/renderer/ViewFrustum.updateChunkPositions:(DD)V
        //   187: aload_0        
        //   188: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   191: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   194: ldc_w           "renderlistcamera"
        //   197: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   200: aload_1        
        //   201: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   204: aload_1        
        //   205: getfield        net/minecraft/entity/Entity.posX:D
        //   208: aload_1        
        //   209: getfield        net/minecraft/entity/Entity.lastTickPosX:D
        //   212: dsub           
        //   213: dload_2        
        //   214: dmul           
        //   215: dadd           
        //   216: dstore          13
        //   218: aload_1        
        //   219: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   222: aload_1        
        //   223: getfield        net/minecraft/entity/Entity.posY:D
        //   226: aload_1        
        //   227: getfield        net/minecraft/entity/Entity.lastTickPosY:D
        //   230: dsub           
        //   231: dload_2        
        //   232: dmul           
        //   233: dadd           
        //   234: dstore          15
        //   236: aload_1        
        //   237: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   240: aload_1        
        //   241: getfield        net/minecraft/entity/Entity.posZ:D
        //   244: aload_1        
        //   245: getfield        net/minecraft/entity/Entity.lastTickPosZ:D
        //   248: dsub           
        //   249: dload_2        
        //   250: dmul           
        //   251: dadd           
        //   252: dstore          17
        //   254: aload_0        
        //   255: getfield        net/minecraft/client/renderer/RenderGlobal.renderContainer:Lnet/minecraft/client/renderer/ChunkRenderContainer;
        //   258: dload           13
        //   260: dload           15
        //   262: dload           17
        //   264: invokevirtual   net/minecraft/client/renderer/ChunkRenderContainer.initialize:(DDD)V
        //   267: aload_0        
        //   268: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   271: getfield        net/minecraft/client/multiplayer/WorldClient.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   274: ldc_w           "cull"
        //   277: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   280: aload_0        
        //   281: getfield        net/minecraft/client/renderer/RenderGlobal.debugFixedClippingHelper:Lnet/minecraft/client/renderer/culling/ClippingHelper;
        //   284: ifnull          330
        //   287: new             Lnet/minecraft/client/renderer/culling/Frustum;
        //   290: dup            
        //   291: aload_0        
        //   292: getfield        net/minecraft/client/renderer/RenderGlobal.debugFixedClippingHelper:Lnet/minecraft/client/renderer/culling/ClippingHelper;
        //   295: invokespecial   net/minecraft/client/renderer/culling/Frustum.<init>:(Lnet/minecraft/client/renderer/culling/ClippingHelper;)V
        //   298: astore          19
        //   300: aload           19
        //   302: aload_0        
        //   303: getfield        net/minecraft/client/renderer/RenderGlobal.debugTerrainFrustumPosition:Lnet/minecraft/util/Vector3d;
        //   306: getfield        net/minecraft/util/Vector3d.field_181059_a:D
        //   309: aload_0        
        //   310: getfield        net/minecraft/client/renderer/RenderGlobal.debugTerrainFrustumPosition:Lnet/minecraft/util/Vector3d;
        //   313: getfield        net/minecraft/util/Vector3d.field_181060_b:D
        //   316: aload_0        
        //   317: getfield        net/minecraft/client/renderer/RenderGlobal.debugTerrainFrustumPosition:Lnet/minecraft/util/Vector3d;
        //   320: getfield        net/minecraft/util/Vector3d.field_181061_c:D
        //   323: invokevirtual   net/minecraft/client/renderer/culling/Frustum.setPosition:(DDD)V
        //   326: aload           19
        //   328: astore          4
        //   330: aload_0        
        //   331: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   334: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //   337: ldc_w           "culling"
        //   340: invokevirtual   net/minecraft/profiler/Profiler.endStartSection:(Ljava/lang/String;)V
        //   343: new             Lnet/minecraft/util/BlockPos;
        //   346: dup            
        //   347: dload           13
        //   349: dload           15
        //   351: aload_1        
        //   352: invokevirtual   net/minecraft/entity/Entity.getEyeHeight:()F
        //   355: f2d            
        //   356: dadd           
        //   357: dload           17
        //   359: invokespecial   net/minecraft/util/BlockPos.<init>:(DDD)V
        //   362: astore          19
        //   364: aload_0        
        //   365: getfield        net/minecraft/client/renderer/RenderGlobal.viewFrustum:Lnet/minecraft/client/renderer/ViewFrustum;
        //   368: aload           19
        //   370: invokevirtual   net/minecraft/client/renderer/ViewFrustum.getRenderChunk:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   373: astore          20
        //   375: new             Lnet/minecraft/util/BlockPos;
        //   378: dup            
        //   379: dload           13
        //   381: ldc2_w          16.0
        //   384: ddiv           
        //   385: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   388: bipush          16
        //   390: imul           
        //   391: dload           15
        //   393: ldc2_w          16.0
        //   396: ddiv           
        //   397: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   400: bipush          16
        //   402: imul           
        //   403: dload           17
        //   405: ldc2_w          16.0
        //   408: ddiv           
        //   409: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   412: bipush          16
        //   414: imul           
        //   415: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   418: astore          21
        //   420: aload_0        
        //   421: aload_0        
        //   422: getfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   425: ifne            502
        //   428: aload_0        
        //   429: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToUpdate:Ljava/util/Set;
        //   432: invokeinterface java/util/Set.isEmpty:()Z
        //   437: ifeq            502
        //   440: aload_1        
        //   441: getfield        net/minecraft/entity/Entity.posX:D
        //   444: aload_0        
        //   445: getfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityX:D
        //   448: dcmpl          
        //   449: ifne            502
        //   452: aload_1        
        //   453: getfield        net/minecraft/entity/Entity.posY:D
        //   456: aload_0        
        //   457: getfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityY:D
        //   460: dcmpl          
        //   461: ifne            502
        //   464: aload_1        
        //   465: getfield        net/minecraft/entity/Entity.posZ:D
        //   468: aload_0        
        //   469: getfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityZ:D
        //   472: dcmpl          
        //   473: ifne            502
        //   476: aload_1        
        //   477: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //   480: f2d            
        //   481: aload_0        
        //   482: getfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityPitch:D
        //   485: dcmpl          
        //   486: ifne            502
        //   489: aload_1        
        //   490: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   493: f2d            
        //   494: aload_0        
        //   495: getfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityYaw:D
        //   498: dcmpl          
        //   499: ifeq            506
        //   502: iconst_1       
        //   503: goto            507
        //   506: iconst_0       
        //   507: putfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   510: aload_0        
        //   511: aload_1        
        //   512: getfield        net/minecraft/entity/Entity.posX:D
        //   515: putfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityX:D
        //   518: aload_0        
        //   519: aload_1        
        //   520: getfield        net/minecraft/entity/Entity.posY:D
        //   523: putfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityY:D
        //   526: aload_0        
        //   527: aload_1        
        //   528: getfield        net/minecraft/entity/Entity.posZ:D
        //   531: putfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityZ:D
        //   534: aload_0        
        //   535: aload_1        
        //   536: getfield        net/minecraft/entity/Entity.rotationPitch:F
        //   539: f2d            
        //   540: putfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityPitch:D
        //   543: aload_0        
        //   544: aload_1        
        //   545: getfield        net/minecraft/entity/Entity.rotationYaw:F
        //   548: f2d            
        //   549: putfield        net/minecraft/client/renderer/RenderGlobal.lastViewEntityYaw:D
        //   552: aload_0        
        //   553: getfield        net/minecraft/client/renderer/RenderGlobal.debugFixedClippingHelper:Lnet/minecraft/client/renderer/culling/ClippingHelper;
        //   556: ifnull          563
        //   559: iconst_1       
        //   560: goto            564
        //   563: iconst_0       
        //   564: istore          22
        //   566: getstatic       optfine/Lagometer.timerVisibility:Loptfine/Lagometer$TimerNano;
        //   569: invokevirtual   optfine/Lagometer$TimerNano.start:()V
        //   572: iload           22
        //   574: ifne            1251
        //   577: aload_0        
        //   578: getfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   581: ifeq            1251
        //   584: aload_0        
        //   585: iconst_0       
        //   586: putfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //   589: aload_0        
        //   590: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfos:Ljava/util/List;
        //   593: invokeinterface java/util/List.clear:()V
        //   598: aload_0        
        //   599: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //   602: invokeinterface java/util/List.clear:()V
        //   607: aload_0        
        //   608: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //   611: invokeinterface java/util/List.clear:()V
        //   616: aload_0        
        //   617: getfield        net/minecraft/client/renderer/RenderGlobal.visibilityDeque:Ljava/util/Deque;
        //   620: invokeinterface java/util/Deque.clear:()V
        //   625: aload_0        
        //   626: getfield        net/minecraft/client/renderer/RenderGlobal.visibilityDeque:Ljava/util/Deque;
        //   629: astore          23
        //   631: aload_0        
        //   632: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   635: getfield        net/minecraft/client/Minecraft.renderChunksMany:Z
        //   638: istore          24
        //   640: aload           20
        //   642: ifnull          796
        //   645: new             Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   648: dup            
        //   649: aload_0        
        //   650: aload           20
        //   652: aconst_null    
        //   653: checkcast       Lnet/minecraft/util/EnumFacing;
        //   656: iconst_0       
        //   657: aconst_null    
        //   658: invokespecial   net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.<init>:(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;ILjava/lang/Object;)V
        //   661: astore          26
        //   663: getstatic       net/minecraft/client/renderer/RenderGlobal.SET_ALL_FACINGS:Ljava/util/Set;
        //   666: astore          27
        //   668: aload           27
        //   670: invokeinterface java/util/Set.size:()I
        //   675: iconst_1       
        //   676: if_icmpne       720
        //   679: aload_0        
        //   680: aload_1        
        //   681: dload_2        
        //   682: invokevirtual   net/minecraft/client/renderer/RenderGlobal.getViewVector:(Lnet/minecraft/entity/Entity;D)Lorg/lwjgl/util/vector/Vector3f;
        //   685: astore          28
        //   687: aload           28
        //   689: getfield        org/lwjgl/util/vector/Vector3f.x:F
        //   692: aload           28
        //   694: getfield        org/lwjgl/util/vector/Vector3f.y:F
        //   697: aload           28
        //   699: getfield        org/lwjgl/util/vector/Vector3f.z:F
        //   702: invokestatic    net/minecraft/util/EnumFacing.getFacingFromVector:(FFF)Lnet/minecraft/util/EnumFacing;
        //   705: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //   708: astore          29
        //   710: aload           27
        //   712: aload           29
        //   714: invokeinterface java/util/Set.remove:(Ljava/lang/Object;)Z
        //   719: pop            
        //   720: aload           27
        //   722: invokeinterface java/util/Set.isEmpty:()Z
        //   727: ifeq            730
        //   730: iload           6
        //   732: ifne            750
        //   735: aload_0        
        //   736: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfos:Ljava/util/List;
        //   739: aload           26
        //   741: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   746: pop            
        //   747: goto            941
        //   750: iload           6
        //   752: ifeq            775
        //   755: aload_0        
        //   756: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   759: aload           19
        //   761: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   764: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   769: invokevirtual   net/minecraft/block/Block.isOpaqueCube:()Z
        //   772: ifeq            775
        //   775: aload           20
        //   777: iload           5
        //   779: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.setFrameIndex:(I)Z
        //   782: pop            
        //   783: aload           23
        //   785: aload           26
        //   787: invokeinterface java/util/Deque.add:(Ljava/lang/Object;)Z
        //   792: pop            
        //   793: goto            941
        //   796: aload           19
        //   798: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //   801: ifle            810
        //   804: sipush          248
        //   807: goto            812
        //   810: bipush          8
        //   812: istore          25
        //   814: aload_0        
        //   815: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //   818: ineg           
        //   819: istore          26
        //   821: iload           26
        //   823: aload_0        
        //   824: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //   827: if_icmpgt       941
        //   830: aload_0        
        //   831: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //   834: ineg           
        //   835: istore          27
        //   837: iload           27
        //   839: aload_0        
        //   840: getfield        net/minecraft/client/renderer/RenderGlobal.renderDistanceChunks:I
        //   843: if_icmpgt       935
        //   846: aload_0        
        //   847: getfield        net/minecraft/client/renderer/RenderGlobal.viewFrustum:Lnet/minecraft/client/renderer/ViewFrustum;
        //   850: new             Lnet/minecraft/util/BlockPos;
        //   853: dup            
        //   854: iload           26
        //   856: iconst_4       
        //   857: ishl           
        //   858: bipush          8
        //   860: iadd           
        //   861: iconst_1       
        //   862: iload           27
        //   864: iconst_4       
        //   865: ishl           
        //   866: bipush          8
        //   868: iadd           
        //   869: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   872: invokevirtual   net/minecraft/client/renderer/ViewFrustum.getRenderChunk:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   875: astore          28
        //   877: aload           28
        //   879: ifnull          929
        //   882: aload           4
        //   884: aload           28
        //   886: getfield        net/minecraft/client/renderer/chunk/RenderChunk.boundingBox:Lnet/minecraft/util/AxisAlignedBB;
        //   889: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //   894: ifeq            929
        //   897: aload           28
        //   899: iload           5
        //   901: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.setFrameIndex:(I)Z
        //   904: pop            
        //   905: aload           23
        //   907: new             Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   910: dup            
        //   911: aload_0        
        //   912: aload           28
        //   914: aconst_null    
        //   915: checkcast       Lnet/minecraft/util/EnumFacing;
        //   918: iconst_0       
        //   919: aconst_null    
        //   920: invokespecial   net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.<init>:(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;ILjava/lang/Object;)V
        //   923: invokeinterface java/util/Deque.add:(Ljava/lang/Object;)Z
        //   928: pop            
        //   929: iinc            27, 1
        //   932: goto            837
        //   935: iinc            26, 1
        //   938: goto            821
        //   941: getstatic       net/minecraft/util/EnumFacing.VALUES:[Lnet/minecraft/util/EnumFacing;
        //   944: astore          25
        //   946: aload           25
        //   948: arraylength    
        //   949: istore          26
        //   951: aload           23
        //   953: invokeinterface java/util/Deque.isEmpty:()Z
        //   958: ifne            1251
        //   961: aload           23
        //   963: invokeinterface java/util/Deque.poll:()Ljava/lang/Object;
        //   968: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //   971: astore          27
        //   973: aload           27
        //   975: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //   978: astore          28
        //   980: aload           27
        //   982: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.facing:Lnet/minecraft/util/EnumFacing;
        //   985: astore          29
        //   987: aload           28
        //   989: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.getPosition:()Lnet/minecraft/util/BlockPos;
        //   992: astore          30
        //   994: aload           28
        //   996: getfield        net/minecraft/client/renderer/chunk/RenderChunk.compiledChunk:Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //   999: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.isEmpty:()Z
        //  1002: ifeq            1013
        //  1005: aload           28
        //  1007: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.isNeedsUpdate:()Z
        //  1010: ifeq            1025
        //  1013: aload_0        
        //  1014: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfos:Ljava/util/List;
        //  1017: aload           27
        //  1019: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1024: pop            
        //  1025: aload_0        
        //  1026: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1029: aload           30
        //  1031: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getChunkFromBlockCoords:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
        //  1034: invokestatic    optfine/ChunkUtils.hasEntities:(Lnet/minecraft/world/chunk/Chunk;)Z
        //  1037: ifeq            1052
        //  1040: aload_0        
        //  1041: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosEntities:Ljava/util/List;
        //  1044: aload           27
        //  1046: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1051: pop            
        //  1052: aload           28
        //  1054: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.getCompiledChunk:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //  1057: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.getTileEntities:()Ljava/util/List;
        //  1060: invokeinterface java/util/List.size:()I
        //  1065: ifle            1080
        //  1068: aload_0        
        //  1069: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfosTileEntities:Ljava/util/List;
        //  1072: aload           27
        //  1074: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //  1079: pop            
        //  1080: iconst_0       
        //  1081: iload           26
        //  1083: if_icmpge       1248
        //  1086: aload           25
        //  1088: iconst_0       
        //  1089: aaload         
        //  1090: astore          32
        //  1092: goto            1139
        //  1095: aload           27
        //  1097: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.setFacing:Ljava/util/Set;
        //  1100: aload           32
        //  1102: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  1105: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1110: ifne            1242
        //  1113: goto            1139
        //  1116: aload           29
        //  1118: ifnull          1139
        //  1121: aload           28
        //  1123: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.getCompiledChunk:()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
        //  1126: aload           29
        //  1128: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  1131: aload           32
        //  1133: invokevirtual   net/minecraft/client/renderer/chunk/CompiledChunk.isVisible:(Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;)Z
        //  1136: ifeq            1242
        //  1139: aload_0        
        //  1140: aload           19
        //  1142: aload           28
        //  1144: aload           32
        //  1146: invokespecial   net/minecraft/client/renderer/RenderGlobal.func_181562_a:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1149: astore          33
        //  1151: aload           33
        //  1153: ifnull          1242
        //  1156: aload           33
        //  1158: iload           5
        //  1160: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.setFrameIndex:(I)Z
        //  1163: ifeq            1242
        //  1166: aload           4
        //  1168: aload           33
        //  1170: getfield        net/minecraft/client/renderer/chunk/RenderChunk.boundingBox:Lnet/minecraft/util/AxisAlignedBB;
        //  1173: invokeinterface net/minecraft/client/renderer/culling/ICamera.isBoundingBoxInFrustum:(Lnet/minecraft/util/AxisAlignedBB;)Z
        //  1178: ifeq            1242
        //  1181: new             Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1184: dup            
        //  1185: aload_0        
        //  1186: aload           33
        //  1188: aload           32
        //  1190: aload           27
        //  1192: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.counter:I
        //  1195: iconst_1       
        //  1196: iadd           
        //  1197: aconst_null    
        //  1198: invokespecial   net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.<init>:(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/util/EnumFacing;ILjava/lang/Object;)V
        //  1201: astore          34
        //  1203: aload           34
        //  1205: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.setFacing:Ljava/util/Set;
        //  1208: aload           27
        //  1210: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.setFacing:Ljava/util/Set;
        //  1213: invokeinterface java/util/Set.addAll:(Ljava/util/Collection;)Z
        //  1218: pop            
        //  1219: aload           34
        //  1221: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.setFacing:Ljava/util/Set;
        //  1224: aload           32
        //  1226: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //  1231: pop            
        //  1232: aload           23
        //  1234: aload           34
        //  1236: invokeinterface java/util/Deque.add:(Ljava/lang/Object;)Z
        //  1241: pop            
        //  1242: iinc            31, 1
        //  1245: goto            1080
        //  1248: goto            951
        //  1251: aload_0        
        //  1252: getfield        net/minecraft/client/renderer/RenderGlobal.debugFixTerrainFrustum:Z
        //  1255: ifeq            1273
        //  1258: aload_0        
        //  1259: dload           13
        //  1261: dload           15
        //  1263: dload           17
        //  1265: invokespecial   net/minecraft/client/renderer/RenderGlobal.fixTerrainFrustum:(DDD)V
        //  1268: aload_0        
        //  1269: iconst_0       
        //  1270: putfield        net/minecraft/client/renderer/RenderGlobal.debugFixTerrainFrustum:Z
        //  1273: getstatic       optfine/Lagometer.timerVisibility:Loptfine/Lagometer$TimerNano;
        //  1276: invokevirtual   optfine/Lagometer$TimerNano.end:()V
        //  1279: aload_0        
        //  1280: getfield        net/minecraft/client/renderer/RenderGlobal.renderDispatcher:Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher;
        //  1283: invokevirtual   net/minecraft/client/renderer/chunk/ChunkRenderDispatcher.clearChunkUpdates:()V
        //  1286: aload_0        
        //  1287: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToUpdate:Ljava/util/Set;
        //  1290: astore          23
        //  1292: aload_0        
        //  1293: invokestatic    com/google/common/collect/Sets.newLinkedHashSet:()Ljava/util/LinkedHashSet;
        //  1296: putfield        net/minecraft/client/renderer/RenderGlobal.chunksToUpdate:Ljava/util/Set;
        //  1299: aload_0        
        //  1300: getfield        net/minecraft/client/renderer/RenderGlobal.renderInfos:Ljava/util/List;
        //  1303: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  1308: astore          24
        //  1310: getstatic       optfine/Lagometer.timerChunkUpdate:Loptfine/Lagometer$TimerNano;
        //  1313: invokevirtual   optfine/Lagometer$TimerNano.start:()V
        //  1316: aload           24
        //  1318: invokeinterface java/util/Iterator.hasNext:()Z
        //  1323: ifeq            1459
        //  1326: aload           24
        //  1328: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  1333: checkcast       Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
        //  1336: astore          25
        //  1338: aload           25
        //  1340: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1343: astore          26
        //  1345: aload           26
        //  1347: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.isNeedsUpdate:()Z
        //  1350: ifne            1365
        //  1353: aload           23
        //  1355: aload           26
        //  1357: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1362: ifeq            1456
        //  1365: aload_0        
        //  1366: iconst_1       
        //  1367: putfield        net/minecraft/client/renderer/RenderGlobal.displayListEntitiesDirty:Z
        //  1370: aload_0        
        //  1371: aload           21
        //  1373: aload           25
        //  1375: getfield        net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation.renderChunk:Lnet/minecraft/client/renderer/chunk/RenderChunk;
        //  1378: if_icmple       1444
        //  1381: invokestatic    optfine/Config.isActing:()Z
        //  1384: ifne            1402
        //  1387: aload_0        
        //  1388: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToUpdateForced:Ljava/util/Set;
        //  1391: aload           26
        //  1393: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //  1398: pop            
        //  1399: goto            1316
        //  1402: aload_0        
        //  1403: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1406: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1409: ldc_w           "build near"
        //  1412: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //  1415: aload_0        
        //  1416: getfield        net/minecraft/client/renderer/RenderGlobal.renderDispatcher:Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher;
        //  1419: aload           26
        //  1421: invokevirtual   net/minecraft/client/renderer/chunk/ChunkRenderDispatcher.updateChunkNow:(Lnet/minecraft/client/renderer/chunk/RenderChunk;)Z
        //  1424: pop            
        //  1425: aload           26
        //  1427: iconst_0       
        //  1428: invokevirtual   net/minecraft/client/renderer/chunk/RenderChunk.setNeedsUpdate:(Z)V
        //  1431: aload_0        
        //  1432: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1435: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1438: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //  1441: goto            1316
        //  1444: aload_0        
        //  1445: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToUpdate:Ljava/util/Set;
        //  1448: aload           26
        //  1450: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //  1455: pop            
        //  1456: goto            1316
        //  1459: getstatic       optfine/Lagometer.timerChunkUpdate:Loptfine/Lagometer$TimerNano;
        //  1462: invokevirtual   optfine/Lagometer$TimerNano.end:()V
        //  1465: aload_0        
        //  1466: getfield        net/minecraft/client/renderer/RenderGlobal.chunksToUpdate:Ljava/util/Set;
        //  1469: aload           23
        //  1471: invokeinterface java/util/Set.addAll:(Ljava/util/Collection;)Z
        //  1476: pop            
        //  1477: aload_0        
        //  1478: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1481: getfield        net/minecraft/client/Minecraft.mcProfiler:Lnet/minecraft/profiler/Profiler;
        //  1484: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //  1487: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #1316 (coming from #1456).
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
    
    public void deleteAllDisplayLists() {
    }
    
    public void renderSky(final float p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: invokevirtual   optfine/ReflectorMethod.exists:()Z
        //     6: ifeq            72
        //     9: aload_0        
        //    10: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    13: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    16: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //    19: astore_3       
        //    20: aload_3        
        //    21: getstatic       optfine/Reflector.ForgeWorldProvider_getSkyRenderer:Loptfine/ReflectorMethod;
        //    24: iconst_0       
        //    25: anewarray       Ljava/lang/Object;
        //    28: invokestatic    optfine/Reflector.call:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
        //    31: astore          4
        //    33: aload           4
        //    35: ifnull          72
        //    38: aload           4
        //    40: getstatic       optfine/Reflector.IRenderHandler_render:Loptfine/ReflectorMethod;
        //    43: iconst_3       
        //    44: anewarray       Ljava/lang/Object;
        //    47: dup            
        //    48: iconst_0       
        //    49: fload_1        
        //    50: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //    53: aastore        
        //    54: dup            
        //    55: iconst_1       
        //    56: aload_0        
        //    57: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    60: aastore        
        //    61: dup            
        //    62: iconst_2       
        //    63: aload_0        
        //    64: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    67: aastore        
        //    68: invokestatic    optfine/Reflector.callVoid:(Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)V
        //    71: return         
        //    72: aload_0        
        //    73: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    76: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    79: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //    82: invokevirtual   net/minecraft/world/WorldProvider.getDimensionId:()I
        //    85: iconst_1       
        //    86: if_icmpne       96
        //    89: aload_0        
        //    90: invokespecial   net/minecraft/client/renderer/RenderGlobal.renderSkyEnd:()V
        //    93: goto            1968
        //    96: aload_0        
        //    97: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   100: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   103: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //   106: invokevirtual   net/minecraft/world/WorldProvider.isSurfaceWorld:()Z
        //   109: ifeq            1968
        //   112: aload_0        
        //   113: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   116: aload_0        
        //   117: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   120: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   123: fload_1        
        //   124: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getSkyColor:(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/util/Vec3;
        //   127: astore_3       
        //   128: aload_3        
        //   129: aload_0        
        //   130: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   133: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   136: aload_0        
        //   137: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   140: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   143: getfield        net/minecraft/entity/Entity.posX:D
        //   146: aload_0        
        //   147: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   150: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   153: getfield        net/minecraft/entity/Entity.posY:D
        //   156: dconst_1       
        //   157: dadd           
        //   158: aload_0        
        //   159: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //   162: invokevirtual   net/minecraft/client/Minecraft.getRenderViewEntity:()Lnet/minecraft/entity/Entity;
        //   165: getfield        net/minecraft/entity/Entity.posZ:D
        //   168: invokestatic    optfine/CustomColorizer.getSkyColor:(Lnet/minecraft/util/Vec3;Lnet/minecraft/world/IBlockAccess;DDD)Lnet/minecraft/util/Vec3;
        //   171: astore_3       
        //   172: aload_3        
        //   173: getfield        net/minecraft/util/Vec3.xCoord:D
        //   176: d2f            
        //   177: fstore          4
        //   179: aload_3        
        //   180: getfield        net/minecraft/util/Vec3.yCoord:D
        //   183: d2f            
        //   184: fstore          5
        //   186: aload_3        
        //   187: getfield        net/minecraft/util/Vec3.zCoord:D
        //   190: d2f            
        //   191: fstore          6
        //   193: iload_2        
        //   194: iconst_2       
        //   195: if_icmpeq       274
        //   198: fload           4
        //   200: ldc_w           30.0
        //   203: fmul           
        //   204: fload           5
        //   206: ldc_w           59.0
        //   209: fmul           
        //   210: fadd           
        //   211: fload           6
        //   213: ldc_w           11.0
        //   216: fmul           
        //   217: fadd           
        //   218: ldc_w           100.0
        //   221: fdiv           
        //   222: fstore          7
        //   224: fload           4
        //   226: ldc_w           30.0
        //   229: fmul           
        //   230: fload           5
        //   232: ldc_w           70.0
        //   235: fmul           
        //   236: fadd           
        //   237: ldc_w           100.0
        //   240: fdiv           
        //   241: fstore          8
        //   243: fload           4
        //   245: ldc_w           30.0
        //   248: fmul           
        //   249: fload           6
        //   251: ldc_w           70.0
        //   254: fmul           
        //   255: fadd           
        //   256: ldc_w           100.0
        //   259: fdiv           
        //   260: fstore          9
        //   262: fload           7
        //   264: fstore          4
        //   266: fload           8
        //   268: fstore          5
        //   270: fload           9
        //   272: fstore          6
        //   274: fload           4
        //   276: fload           5
        //   278: fload           6
        //   280: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //   283: invokestatic    net/minecraft/client/renderer/Tessellator.getInstance:()Lnet/minecraft/client/renderer/Tessellator;
        //   286: astore          7
        //   288: aload           7
        //   290: invokevirtual   net/minecraft/client/renderer/Tessellator.getWorldRenderer:()Lnet/minecraft/client/renderer/WorldRenderer;
        //   293: astore          8
        //   295: iconst_0       
        //   296: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //   299: fload           4
        //   301: fload           5
        //   303: fload           6
        //   305: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //   308: invokestatic    optfine/Config.isSkyEnabled:()Z
        //   311: ifeq            376
        //   314: aload_0        
        //   315: getfield        net/minecraft/client/renderer/RenderGlobal.vboEnabled:Z
        //   318: ifeq            369
        //   321: aload_0        
        //   322: getfield        net/minecraft/client/renderer/RenderGlobal.skyVBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //   325: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.bindBuffer:()V
        //   328: ldc_w           32884
        //   331: invokestatic    org/lwjgl/opengl/GL11.glEnableClientState:(I)V
        //   334: iconst_3       
        //   335: sipush          5126
        //   338: bipush          12
        //   340: lconst_0       
        //   341: invokestatic    org/lwjgl/opengl/GL11.glVertexPointer:(IIIJ)V
        //   344: aload_0        
        //   345: getfield        net/minecraft/client/renderer/RenderGlobal.skyVBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //   348: bipush          7
        //   350: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.drawArrays:(I)V
        //   353: aload_0        
        //   354: getfield        net/minecraft/client/renderer/RenderGlobal.skyVBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //   357: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.unbindBuffer:()V
        //   360: ldc_w           32884
        //   363: invokestatic    org/lwjgl/opengl/GL11.glDisableClientState:(I)V
        //   366: goto            376
        //   369: aload_0        
        //   370: getfield        net/minecraft/client/renderer/RenderGlobal.glSkyList:I
        //   373: invokestatic    net/minecraft/client/renderer/GlStateManager.callList:(I)V
        //   376: sipush          770
        //   379: sipush          771
        //   382: iconst_1       
        //   383: iconst_0       
        //   384: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //   387: aload_0        
        //   388: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   391: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //   394: aload_0        
        //   395: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   398: fload_1        
        //   399: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCelestialAngle:(F)F
        //   402: fload_1        
        //   403: invokevirtual   net/minecraft/world/WorldProvider.calcSunriseSunsetColors:(FF)[F
        //   406: astore          9
        //   408: aload           9
        //   410: ifnull          703
        //   413: invokestatic    optfine/Config.isSunMoonEnabled:()Z
        //   416: ifeq            703
        //   419: sipush          7425
        //   422: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //   425: ldc_w           90.0
        //   428: fconst_1       
        //   429: fconst_0       
        //   430: fconst_0       
        //   431: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   434: aload_0        
        //   435: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   438: fload_1        
        //   439: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCelestialAngleRadians:(F)F
        //   442: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   445: fconst_0       
        //   446: fcmpg          
        //   447: ifge            456
        //   450: ldc_w           180.0
        //   453: goto            457
        //   456: fconst_0       
        //   457: fconst_0       
        //   458: fconst_0       
        //   459: fconst_1       
        //   460: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   463: ldc_w           90.0
        //   466: fconst_0       
        //   467: fconst_0       
        //   468: fconst_1       
        //   469: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   472: aload           9
        //   474: iconst_0       
        //   475: faload         
        //   476: fstore          10
        //   478: aload           9
        //   480: iconst_1       
        //   481: faload         
        //   482: fstore          11
        //   484: aload           9
        //   486: iconst_2       
        //   487: faload         
        //   488: fstore          12
        //   490: iload_2        
        //   491: iconst_2       
        //   492: if_icmpeq       571
        //   495: fload           10
        //   497: ldc_w           30.0
        //   500: fmul           
        //   501: fload           11
        //   503: ldc_w           59.0
        //   506: fmul           
        //   507: fadd           
        //   508: fload           12
        //   510: ldc_w           11.0
        //   513: fmul           
        //   514: fadd           
        //   515: ldc_w           100.0
        //   518: fdiv           
        //   519: fstore          13
        //   521: fload           10
        //   523: ldc_w           30.0
        //   526: fmul           
        //   527: fload           11
        //   529: ldc_w           70.0
        //   532: fmul           
        //   533: fadd           
        //   534: ldc_w           100.0
        //   537: fdiv           
        //   538: fstore          14
        //   540: fload           10
        //   542: ldc_w           30.0
        //   545: fmul           
        //   546: fload           12
        //   548: ldc_w           70.0
        //   551: fmul           
        //   552: fadd           
        //   553: ldc_w           100.0
        //   556: fdiv           
        //   557: fstore          15
        //   559: fload           13
        //   561: fstore          10
        //   563: fload           14
        //   565: fstore          11
        //   567: fload           15
        //   569: fstore          12
        //   571: aload           8
        //   573: bipush          6
        //   575: getstatic       net/minecraft/client/renderer/vertex/DefaultVertexFormats.POSITION_COLOR:Lnet/minecraft/client/renderer/vertex/VertexFormat;
        //   578: invokevirtual   net/minecraft/client/renderer/WorldRenderer.begin:(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
        //   581: aload           8
        //   583: dconst_0       
        //   584: ldc2_w          100.0
        //   587: dconst_0       
        //   588: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   591: fload           10
        //   593: fload           11
        //   595: fload           12
        //   597: aload           9
        //   599: iconst_3       
        //   600: faload         
        //   601: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;
        //   604: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   607: iconst_0       
        //   608: i2f            
        //   609: ldc_w           3.1415927
        //   612: fmul           
        //   613: fconst_2       
        //   614: fmul           
        //   615: ldc_w           16.0
        //   618: fdiv           
        //   619: fstore          15
        //   621: fload           15
        //   623: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //   626: fstore          16
        //   628: fload           15
        //   630: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //   633: fstore          17
        //   635: aload           8
        //   637: fload           16
        //   639: ldc_w           120.0
        //   642: fmul           
        //   643: f2d            
        //   644: fload           17
        //   646: ldc_w           120.0
        //   649: fmul           
        //   650: f2d            
        //   651: fload           17
        //   653: fneg           
        //   654: ldc_w           40.0
        //   657: fmul           
        //   658: aload           9
        //   660: iconst_3       
        //   661: faload         
        //   662: fmul           
        //   663: f2d            
        //   664: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   667: aload           9
        //   669: iconst_0       
        //   670: faload         
        //   671: aload           9
        //   673: iconst_1       
        //   674: faload         
        //   675: aload           9
        //   677: iconst_2       
        //   678: faload         
        //   679: fconst_0       
        //   680: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;
        //   683: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   686: iinc            14, 1
        //   689: goto            607
        //   692: aload           7
        //   694: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()V
        //   697: sipush          7424
        //   700: invokestatic    net/minecraft/client/renderer/GlStateManager.shadeModel:(I)V
        //   703: sipush          770
        //   706: iconst_1       
        //   707: iconst_1       
        //   708: iconst_0       
        //   709: invokestatic    net/minecraft/client/renderer/GlStateManager.tryBlendFuncSeparate:(IIII)V
        //   712: fconst_1       
        //   713: aload_0        
        //   714: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   717: fload_1        
        //   718: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getRainStrength:(F)F
        //   721: fsub           
        //   722: fstore          10
        //   724: fconst_1       
        //   725: fconst_1       
        //   726: fconst_1       
        //   727: fload           10
        //   729: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //   732: ldc_w           -90.0
        //   735: fconst_0       
        //   736: fconst_1       
        //   737: fconst_0       
        //   738: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   741: aload_0        
        //   742: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   745: aload_0        
        //   746: getfield        net/minecraft/client/renderer/RenderGlobal.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   749: aload_0        
        //   750: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   753: fload_1        
        //   754: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCelestialAngle:(F)F
        //   757: fload           10
        //   759: invokestatic    optfine/CustomSky.renderSky:(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;FF)V
        //   762: aload_0        
        //   763: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   766: fload_1        
        //   767: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getCelestialAngle:(F)F
        //   770: ldc_w           360.0
        //   773: fmul           
        //   774: fconst_1       
        //   775: fconst_0       
        //   776: fconst_0       
        //   777: invokestatic    net/minecraft/client/renderer/GlStateManager.rotate:(FFFF)V
        //   780: invokestatic    optfine/Config.isSunMoonEnabled:()Z
        //   783: ifeq            1097
        //   786: ldc_w           30.0
        //   789: fstore          11
        //   791: aload_0        
        //   792: getfield        net/minecraft/client/renderer/RenderGlobal.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   795: getstatic       net/minecraft/client/renderer/RenderGlobal.locationSunPng:Lnet/minecraft/util/ResourceLocation;
        //   798: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   801: aload           8
        //   803: bipush          7
        //   805: getstatic       net/minecraft/client/renderer/vertex/DefaultVertexFormats.POSITION_TEX:Lnet/minecraft/client/renderer/vertex/VertexFormat;
        //   808: invokevirtual   net/minecraft/client/renderer/WorldRenderer.begin:(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
        //   811: aload           8
        //   813: fload           11
        //   815: fneg           
        //   816: f2d            
        //   817: ldc2_w          100.0
        //   820: fload           11
        //   822: fneg           
        //   823: f2d            
        //   824: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   827: dconst_0       
        //   828: dconst_0       
        //   829: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   832: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   835: aload           8
        //   837: fload           11
        //   839: f2d            
        //   840: ldc2_w          100.0
        //   843: fload           11
        //   845: fneg           
        //   846: f2d            
        //   847: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   850: dconst_1       
        //   851: dconst_0       
        //   852: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   855: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   858: aload           8
        //   860: fload           11
        //   862: f2d            
        //   863: ldc2_w          100.0
        //   866: fload           11
        //   868: f2d            
        //   869: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   872: dconst_1       
        //   873: dconst_1       
        //   874: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   877: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   880: aload           8
        //   882: fload           11
        //   884: fneg           
        //   885: f2d            
        //   886: ldc2_w          100.0
        //   889: fload           11
        //   891: f2d            
        //   892: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   895: dconst_0       
        //   896: dconst_1       
        //   897: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   900: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //   903: aload           7
        //   905: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()V
        //   908: ldc_w           20.0
        //   911: fstore          11
        //   913: aload_0        
        //   914: getfield        net/minecraft/client/renderer/RenderGlobal.renderEngine:Lnet/minecraft/client/renderer/texture/TextureManager;
        //   917: getstatic       net/minecraft/client/renderer/RenderGlobal.locationMoonPhasesPng:Lnet/minecraft/util/ResourceLocation;
        //   920: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   923: aload_0        
        //   924: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //   927: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getMoonPhase:()I
        //   930: istore          12
        //   932: iload           12
        //   934: iconst_4       
        //   935: irem           
        //   936: istore          13
        //   938: iload           12
        //   940: iconst_4       
        //   941: idiv           
        //   942: iconst_2       
        //   943: irem           
        //   944: istore          14
        //   946: iconst_1       
        //   947: i2f            
        //   948: ldc_w           4.0
        //   951: fdiv           
        //   952: fstore          15
        //   954: iconst_0       
        //   955: i2f            
        //   956: fconst_2       
        //   957: fdiv           
        //   958: fstore          16
        //   960: iconst_2       
        //   961: i2f            
        //   962: ldc_w           4.0
        //   965: fdiv           
        //   966: fstore          17
        //   968: iconst_1       
        //   969: i2f            
        //   970: fconst_2       
        //   971: fdiv           
        //   972: fstore          18
        //   974: aload           8
        //   976: bipush          7
        //   978: getstatic       net/minecraft/client/renderer/vertex/DefaultVertexFormats.POSITION_TEX:Lnet/minecraft/client/renderer/vertex/VertexFormat;
        //   981: invokevirtual   net/minecraft/client/renderer/WorldRenderer.begin:(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
        //   984: aload           8
        //   986: fload           11
        //   988: fneg           
        //   989: f2d            
        //   990: ldc2_w          -100.0
        //   993: fload           11
        //   995: f2d            
        //   996: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //   999: fload           17
        //  1001: f2d            
        //  1002: fload           18
        //  1004: f2d            
        //  1005: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1008: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1011: aload           8
        //  1013: fload           11
        //  1015: f2d            
        //  1016: ldc2_w          -100.0
        //  1019: fload           11
        //  1021: f2d            
        //  1022: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1025: fload           15
        //  1027: f2d            
        //  1028: fload           18
        //  1030: f2d            
        //  1031: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1034: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1037: aload           8
        //  1039: fload           11
        //  1041: f2d            
        //  1042: ldc2_w          -100.0
        //  1045: fload           11
        //  1047: fneg           
        //  1048: f2d            
        //  1049: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1052: fload           15
        //  1054: f2d            
        //  1055: fload           16
        //  1057: f2d            
        //  1058: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1061: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1064: aload           8
        //  1066: fload           11
        //  1068: fneg           
        //  1069: f2d            
        //  1070: ldc2_w          -100.0
        //  1073: fload           11
        //  1075: fneg           
        //  1076: f2d            
        //  1077: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1080: fload           17
        //  1082: f2d            
        //  1083: fload           16
        //  1085: f2d            
        //  1086: invokevirtual   net/minecraft/client/renderer/WorldRenderer.tex:(DD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1089: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1092: aload           7
        //  1094: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()V
        //  1097: aload_0        
        //  1098: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1101: fload_1        
        //  1102: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getStarBrightness:(F)F
        //  1105: fload           10
        //  1107: fmul           
        //  1108: fstore          11
        //  1110: fload           11
        //  1112: fconst_0       
        //  1113: fcmpl          
        //  1114: ifle            1206
        //  1117: invokestatic    optfine/Config.isStarsEnabled:()Z
        //  1120: ifeq            1206
        //  1123: aload_0        
        //  1124: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1127: invokestatic    optfine/CustomSky.hasSkyLayers:(Lnet/minecraft/world/World;)Z
        //  1130: ifne            1206
        //  1133: fload           11
        //  1135: fload           11
        //  1137: fload           11
        //  1139: fload           11
        //  1141: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //  1144: aload_0        
        //  1145: getfield        net/minecraft/client/renderer/RenderGlobal.vboEnabled:Z
        //  1148: ifeq            1199
        //  1151: aload_0        
        //  1152: getfield        net/minecraft/client/renderer/RenderGlobal.starVBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1155: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.bindBuffer:()V
        //  1158: ldc_w           32884
        //  1161: invokestatic    org/lwjgl/opengl/GL11.glEnableClientState:(I)V
        //  1164: iconst_3       
        //  1165: sipush          5126
        //  1168: bipush          12
        //  1170: lconst_0       
        //  1171: invokestatic    org/lwjgl/opengl/GL11.glVertexPointer:(IIIJ)V
        //  1174: aload_0        
        //  1175: getfield        net/minecraft/client/renderer/RenderGlobal.starVBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1178: bipush          7
        //  1180: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.drawArrays:(I)V
        //  1183: aload_0        
        //  1184: getfield        net/minecraft/client/renderer/RenderGlobal.starVBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1187: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.unbindBuffer:()V
        //  1190: ldc_w           32884
        //  1193: invokestatic    org/lwjgl/opengl/GL11.glDisableClientState:(I)V
        //  1196: goto            1206
        //  1199: aload_0        
        //  1200: getfield        net/minecraft/client/renderer/RenderGlobal.starGLCallList:I
        //  1203: invokestatic    net/minecraft/client/renderer/GlStateManager.callList:(I)V
        //  1206: fconst_1       
        //  1207: fconst_1       
        //  1208: fconst_1       
        //  1209: fconst_1       
        //  1210: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //  1213: fconst_0       
        //  1214: fconst_0       
        //  1215: fconst_0       
        //  1216: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //  1219: aload_0        
        //  1220: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1223: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1226: fload_1        
        //  1227: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/Vec3;
        //  1230: getfield        net/minecraft/util/Vec3.yCoord:D
        //  1233: aload_0        
        //  1234: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1237: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getHorizon:()D
        //  1240: dsub           
        //  1241: dstore          12
        //  1243: dload           12
        //  1245: dconst_0       
        //  1246: dcmpg          
        //  1247: ifge            1833
        //  1250: fconst_0       
        //  1251: ldc_w           12.0
        //  1254: fconst_0       
        //  1255: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //  1258: aload_0        
        //  1259: getfield        net/minecraft/client/renderer/RenderGlobal.vboEnabled:Z
        //  1262: ifeq            1313
        //  1265: aload_0        
        //  1266: getfield        net/minecraft/client/renderer/RenderGlobal.sky2VBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1269: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.bindBuffer:()V
        //  1272: ldc_w           32884
        //  1275: invokestatic    org/lwjgl/opengl/GL11.glEnableClientState:(I)V
        //  1278: iconst_3       
        //  1279: sipush          5126
        //  1282: bipush          12
        //  1284: lconst_0       
        //  1285: invokestatic    org/lwjgl/opengl/GL11.glVertexPointer:(IIIJ)V
        //  1288: aload_0        
        //  1289: getfield        net/minecraft/client/renderer/RenderGlobal.sky2VBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1292: bipush          7
        //  1294: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.drawArrays:(I)V
        //  1297: aload_0        
        //  1298: getfield        net/minecraft/client/renderer/RenderGlobal.sky2VBO:Lnet/minecraft/client/renderer/vertex/VertexBuffer;
        //  1301: invokevirtual   net/minecraft/client/renderer/vertex/VertexBuffer.unbindBuffer:()V
        //  1304: ldc_w           32884
        //  1307: invokestatic    org/lwjgl/opengl/GL11.glDisableClientState:(I)V
        //  1310: goto            1320
        //  1313: aload_0        
        //  1314: getfield        net/minecraft/client/renderer/RenderGlobal.glSkyList2:I
        //  1317: invokestatic    net/minecraft/client/renderer/GlStateManager.callList:(I)V
        //  1320: fconst_1       
        //  1321: fstore          14
        //  1323: dload           12
        //  1325: ldc2_w          65.0
        //  1328: dadd           
        //  1329: d2f            
        //  1330: fneg           
        //  1331: fstore          15
        //  1333: ldc_w           -1.0
        //  1336: fstore          16
        //  1338: aload           8
        //  1340: bipush          7
        //  1342: getstatic       net/minecraft/client/renderer/vertex/DefaultVertexFormats.POSITION_COLOR:Lnet/minecraft/client/renderer/vertex/VertexFormat;
        //  1345: invokevirtual   net/minecraft/client/renderer/WorldRenderer.begin:(ILnet/minecraft/client/renderer/vertex/VertexFormat;)V
        //  1348: aload           8
        //  1350: ldc2_w          -1.0
        //  1353: fload           15
        //  1355: f2d            
        //  1356: dconst_1       
        //  1357: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1360: iconst_0       
        //  1361: iconst_0       
        //  1362: iconst_0       
        //  1363: sipush          255
        //  1366: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1369: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1372: aload           8
        //  1374: dconst_1       
        //  1375: fload           15
        //  1377: f2d            
        //  1378: dconst_1       
        //  1379: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1382: iconst_0       
        //  1383: iconst_0       
        //  1384: iconst_0       
        //  1385: sipush          255
        //  1388: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1391: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1394: aload           8
        //  1396: dconst_1       
        //  1397: ldc2_w          -1.0
        //  1400: dconst_1       
        //  1401: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1404: iconst_0       
        //  1405: iconst_0       
        //  1406: iconst_0       
        //  1407: sipush          255
        //  1410: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1413: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1416: aload           8
        //  1418: ldc2_w          -1.0
        //  1421: ldc2_w          -1.0
        //  1424: dconst_1       
        //  1425: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1428: iconst_0       
        //  1429: iconst_0       
        //  1430: iconst_0       
        //  1431: sipush          255
        //  1434: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1437: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1440: aload           8
        //  1442: ldc2_w          -1.0
        //  1445: ldc2_w          -1.0
        //  1448: ldc2_w          -1.0
        //  1451: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1454: iconst_0       
        //  1455: iconst_0       
        //  1456: iconst_0       
        //  1457: sipush          255
        //  1460: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1463: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1466: aload           8
        //  1468: dconst_1       
        //  1469: ldc2_w          -1.0
        //  1472: ldc2_w          -1.0
        //  1475: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1478: iconst_0       
        //  1479: iconst_0       
        //  1480: iconst_0       
        //  1481: sipush          255
        //  1484: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1487: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1490: aload           8
        //  1492: dconst_1       
        //  1493: fload           15
        //  1495: f2d            
        //  1496: ldc2_w          -1.0
        //  1499: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1502: iconst_0       
        //  1503: iconst_0       
        //  1504: iconst_0       
        //  1505: sipush          255
        //  1508: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1511: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1514: aload           8
        //  1516: ldc2_w          -1.0
        //  1519: fload           15
        //  1521: f2d            
        //  1522: ldc2_w          -1.0
        //  1525: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1528: iconst_0       
        //  1529: iconst_0       
        //  1530: iconst_0       
        //  1531: sipush          255
        //  1534: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1537: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1540: aload           8
        //  1542: dconst_1       
        //  1543: ldc2_w          -1.0
        //  1546: ldc2_w          -1.0
        //  1549: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1552: iconst_0       
        //  1553: iconst_0       
        //  1554: iconst_0       
        //  1555: sipush          255
        //  1558: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1561: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1564: aload           8
        //  1566: dconst_1       
        //  1567: ldc2_w          -1.0
        //  1570: dconst_1       
        //  1571: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1574: iconst_0       
        //  1575: iconst_0       
        //  1576: iconst_0       
        //  1577: sipush          255
        //  1580: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1583: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1586: aload           8
        //  1588: dconst_1       
        //  1589: fload           15
        //  1591: f2d            
        //  1592: dconst_1       
        //  1593: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1596: iconst_0       
        //  1597: iconst_0       
        //  1598: iconst_0       
        //  1599: sipush          255
        //  1602: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1605: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1608: aload           8
        //  1610: dconst_1       
        //  1611: fload           15
        //  1613: f2d            
        //  1614: ldc2_w          -1.0
        //  1617: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1620: iconst_0       
        //  1621: iconst_0       
        //  1622: iconst_0       
        //  1623: sipush          255
        //  1626: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1629: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1632: aload           8
        //  1634: ldc2_w          -1.0
        //  1637: fload           15
        //  1639: f2d            
        //  1640: ldc2_w          -1.0
        //  1643: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1646: iconst_0       
        //  1647: iconst_0       
        //  1648: iconst_0       
        //  1649: sipush          255
        //  1652: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1655: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1658: aload           8
        //  1660: ldc2_w          -1.0
        //  1663: fload           15
        //  1665: f2d            
        //  1666: dconst_1       
        //  1667: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1670: iconst_0       
        //  1671: iconst_0       
        //  1672: iconst_0       
        //  1673: sipush          255
        //  1676: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1679: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1682: aload           8
        //  1684: ldc2_w          -1.0
        //  1687: ldc2_w          -1.0
        //  1690: dconst_1       
        //  1691: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1694: iconst_0       
        //  1695: iconst_0       
        //  1696: iconst_0       
        //  1697: sipush          255
        //  1700: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1703: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1706: aload           8
        //  1708: ldc2_w          -1.0
        //  1711: ldc2_w          -1.0
        //  1714: ldc2_w          -1.0
        //  1717: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1720: iconst_0       
        //  1721: iconst_0       
        //  1722: iconst_0       
        //  1723: sipush          255
        //  1726: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1729: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1732: aload           8
        //  1734: ldc2_w          -1.0
        //  1737: ldc2_w          -1.0
        //  1740: ldc2_w          -1.0
        //  1743: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1746: iconst_0       
        //  1747: iconst_0       
        //  1748: iconst_0       
        //  1749: sipush          255
        //  1752: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1755: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1758: aload           8
        //  1760: ldc2_w          -1.0
        //  1763: ldc2_w          -1.0
        //  1766: dconst_1       
        //  1767: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1770: iconst_0       
        //  1771: iconst_0       
        //  1772: iconst_0       
        //  1773: sipush          255
        //  1776: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1779: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1782: aload           8
        //  1784: dconst_1       
        //  1785: ldc2_w          -1.0
        //  1788: dconst_1       
        //  1789: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1792: iconst_0       
        //  1793: iconst_0       
        //  1794: iconst_0       
        //  1795: sipush          255
        //  1798: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1801: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1804: aload           8
        //  1806: dconst_1       
        //  1807: ldc2_w          -1.0
        //  1810: ldc2_w          -1.0
        //  1813: invokevirtual   net/minecraft/client/renderer/WorldRenderer.pos:(DDD)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1816: iconst_0       
        //  1817: iconst_0       
        //  1818: iconst_0       
        //  1819: sipush          255
        //  1822: invokevirtual   net/minecraft/client/renderer/WorldRenderer.color:(IIII)Lnet/minecraft/client/renderer/WorldRenderer;
        //  1825: invokevirtual   net/minecraft/client/renderer/WorldRenderer.endVertex:()V
        //  1828: aload           7
        //  1830: invokevirtual   net/minecraft/client/renderer/Tessellator.draw:()V
        //  1833: aload_0        
        //  1834: getfield        net/minecraft/client/renderer/RenderGlobal.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1837: getfield        net/minecraft/client/multiplayer/WorldClient.provider:Lnet/minecraft/world/WorldProvider;
        //  1840: invokevirtual   net/minecraft/world/WorldProvider.isSkyColored:()Z
        //  1843: ifeq            1882
        //  1846: fload           4
        //  1848: ldc_w           0.2
        //  1851: fmul           
        //  1852: ldc_w           0.04
        //  1855: fadd           
        //  1856: fload           5
        //  1858: ldc_w           0.2
        //  1861: fmul           
        //  1862: ldc_w           0.04
        //  1865: fadd           
        //  1866: fload           6
        //  1868: ldc_w           0.6
        //  1871: fmul           
        //  1872: ldc_w           0.1
        //  1875: fadd           
        //  1876: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //  1879: goto            1891
        //  1882: fload           4
        //  1884: fload           5
        //  1886: fload           6
        //  1888: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //  1891: aload_0        
        //  1892: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1895: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //  1898: getfield        net/minecraft/client/settings/GameSettings.renderDistanceChunks:I
        //  1901: iconst_4       
        //  1902: if_icmpgt       1938
        //  1905: aload_0        
        //  1906: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1909: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1912: getfield        net/minecraft/client/renderer/EntityRenderer.fogColorRed:F
        //  1915: aload_0        
        //  1916: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1919: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1922: getfield        net/minecraft/client/renderer/EntityRenderer.fogColorGreen:F
        //  1925: aload_0        
        //  1926: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //  1929: getfield        net/minecraft/client/Minecraft.entityRenderer:Lnet/minecraft/client/renderer/EntityRenderer;
        //  1932: getfield        net/minecraft/client/renderer/EntityRenderer.fogColorBlue:F
        //  1935: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFF)V
        //  1938: fconst_0       
        //  1939: dload           12
        //  1941: ldc2_w          16.0
        //  1944: dsub           
        //  1945: d2f            
        //  1946: fneg           
        //  1947: fconst_0       
        //  1948: invokestatic    net/minecraft/client/renderer/GlStateManager.translate:(FFF)V
        //  1951: invokestatic    optfine/Config.isSkyEnabled:()Z
        //  1954: ifeq            1964
        //  1957: aload_0        
        //  1958: getfield        net/minecraft/client/renderer/RenderGlobal.glSkyList2:I
        //  1961: invokestatic    net/minecraft/client/renderer/GlStateManager.callList:(I)V
        //  1964: iconst_1       
        //  1965: invokestatic    net/minecraft/client/renderer/GlStateManager.depthMask:(Z)V
        //  1968: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void generateStars() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.starVBO != null) {
            this.starVBO.deleteGlBuffers();
        }
        if (this.starGLCallList >= 0) {
            GLAllocation.deleteDisplayLists(this.starGLCallList);
            this.starGLCallList = -1;
        }
        if (this.vboEnabled) {
            this.starVBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderStars(worldRenderer);
            worldRenderer.finishDrawing();
            worldRenderer.reset();
            this.starVBO.func_181722_a(worldRenderer.getByteBuffer());
        }
        else {
            GL11.glNewList(this.starGLCallList = GLAllocation.generateDisplayLists(1), 4864);
            this.renderStars(worldRenderer);
            instance.draw();
            GL11.glEndList();
        }
    }
    
    public void renderWorldBorder(final Entity entity, final float n) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final WorldBorder worldBorder = this.theWorld.getWorldBorder();
        final double n2 = this.mc.gameSettings.renderDistanceChunks * 16;
        if (entity.posX >= worldBorder.maxX() - n2 || entity.posX <= worldBorder.minX() + n2 || entity.posZ >= worldBorder.maxZ() - n2 || entity.posZ <= worldBorder.minZ() + n2) {
            final double pow = Math.pow(1.0 - worldBorder.getClosestDistance(entity) / n2, 4.0);
            final double n3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
            final double n4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
            final double n5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            this.renderEngine.bindTexture(RenderGlobal.locationForcefieldPng);
            GlStateManager.depthMask(false);
            final int id = worldBorder.getStatus().getID();
            GlStateManager.color((id >> 16 & 0xFF) / 255.0f, (id >> 8 & 0xFF) / 255.0f, (id & 0xFF) / 255.0f, (float)pow);
            GlStateManager.doPolygonOffset(-3.0f, -3.0f);
            GlStateManager.alphaFunc(516, 0.1f);
            final float n6 = Minecraft.getSystemTime() % 3000L / 3000.0f;
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.setTranslation(-n3, -n4, -n5);
            final double max = Math.max(MathHelper.floor_double(n5 - n2), worldBorder.minZ());
            final double min = Math.min(MathHelper.ceiling_double_int(n5 + n2), worldBorder.maxZ());
            if (n3 > worldBorder.maxX() - n2) {
                float n7 = 0.0f;
                for (double n8 = max; n8 < min; ++n8, n7 += 0.5f) {
                    final double min2 = Math.min(1.0, min - n8);
                    final float n9 = (float)min2 * 0.5f;
                    worldRenderer.pos(worldBorder.maxX(), 256.0, n8).tex(n6 + n7, n6 + 0.0f).endVertex();
                    worldRenderer.pos(worldBorder.maxX(), 256.0, n8 + min2).tex(n6 + n9 + n7, n6 + 0.0f).endVertex();
                    worldRenderer.pos(worldBorder.maxX(), 0.0, n8 + min2).tex(n6 + n9 + n7, n6 + 128.0f).endVertex();
                    worldRenderer.pos(worldBorder.maxX(), 0.0, n8).tex(n6 + n7, n6 + 128.0f).endVertex();
                }
            }
            if (n3 < worldBorder.minX() + n2) {
                float n10 = 0.0f;
                for (double n11 = max; n11 < min; ++n11, n10 += 0.5f) {
                    final double min3 = Math.min(1.0, min - n11);
                    final float n12 = (float)min3 * 0.5f;
                    worldRenderer.pos(worldBorder.minX(), 256.0, n11).tex(n6 + n10, n6 + 0.0f).endVertex();
                    worldRenderer.pos(worldBorder.minX(), 256.0, n11 + min3).tex(n6 + n12 + n10, n6 + 0.0f).endVertex();
                    worldRenderer.pos(worldBorder.minX(), 0.0, n11 + min3).tex(n6 + n12 + n10, n6 + 128.0f).endVertex();
                    worldRenderer.pos(worldBorder.minX(), 0.0, n11).tex(n6 + n10, n6 + 128.0f).endVertex();
                }
            }
            final double max2 = Math.max(MathHelper.floor_double(n3 - n2), worldBorder.minX());
            final double min4 = Math.min(MathHelper.ceiling_double_int(n3 + n2), worldBorder.maxX());
            if (n5 > worldBorder.maxZ() - n2) {
                float n13 = 0.0f;
                for (double n14 = max2; n14 < min4; ++n14, n13 += 0.5f) {
                    final double min5 = Math.min(1.0, min4 - n14);
                    final float n15 = (float)min5 * 0.5f;
                    worldRenderer.pos(n14, 256.0, worldBorder.maxZ()).tex(n6 + n13, n6 + 0.0f).endVertex();
                    worldRenderer.pos(n14 + min5, 256.0, worldBorder.maxZ()).tex(n6 + n15 + n13, n6 + 0.0f).endVertex();
                    worldRenderer.pos(n14 + min5, 0.0, worldBorder.maxZ()).tex(n6 + n15 + n13, n6 + 128.0f).endVertex();
                    worldRenderer.pos(n14, 0.0, worldBorder.maxZ()).tex(n6 + n13, n6 + 128.0f).endVertex();
                }
            }
            if (n5 < worldBorder.minZ() + n2) {
                float n16 = 0.0f;
                for (double n17 = max2; n17 < min4; ++n17, n16 += 0.5f) {
                    final double min6 = Math.min(1.0, min4 - n17);
                    final float n18 = (float)min6 * 0.5f;
                    worldRenderer.pos(n17, 256.0, worldBorder.minZ()).tex(n6 + n16, n6 + 0.0f).endVertex();
                    worldRenderer.pos(n17 + min6, 256.0, worldBorder.minZ()).tex(n6 + n18 + n16, n6 + 0.0f).endVertex();
                    worldRenderer.pos(n17 + min6, 0.0, worldBorder.minZ()).tex(n6 + n18 + n16, n6 + 128.0f).endVertex();
                    worldRenderer.pos(n17, 0.0, worldBorder.minZ()).tex(n6 + n16, n6 + 128.0f).endVertex();
                }
            }
            instance.draw();
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            GlStateManager.doPolygonOffset(0.0f, 0.0f);
            GlStateManager.depthMask(true);
        }
    }
    
    private void spawnParticle(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.spawnParticle(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange(), n, n2, n3, n4, n5, n6, array);
    }
    
    private void preRenderDamagedBlocks() {
        GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.5f);
        GlStateManager.doPolygonOffset(-3.0f, -3.0f);
        GlStateManager.alphaFunc(516, 0.1f);
    }
    
    public void loadRenderers() {
        if (this.theWorld != null) {
            this.displayListEntitiesDirty = true;
            Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
            Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
            this.renderDistance = this.renderDistanceChunks * 16;
            this.renderDistanceSq = this.renderDistance * this.renderDistance;
            final boolean vboEnabled = this.vboEnabled;
            this.vboEnabled = OpenGlHelper.useVbo();
            if (vboEnabled && !this.vboEnabled) {
                this.renderContainer = new RenderList();
                this.renderChunkFactory = new ListChunkFactory();
            }
            else if (!vboEnabled && this.vboEnabled) {
                this.renderContainer = new VboRenderList();
                this.renderChunkFactory = new VboChunkFactory();
            }
            if (vboEnabled != this.vboEnabled) {
                this.generateStars();
                this.generateSky();
                this.generateSky2();
            }
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }
            this.stopChunkUpdates();
            final Set field_181024_n = this.field_181024_n;
            // monitorenter(field_181024_n2 = this.field_181024_n)
            this.field_181024_n.clear();
            // monitorexit(field_181024_n2)
            this.viewFrustum = new ViewFrustum(this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
            if (this.theWorld != null) {
                final Entity renderViewEntity = this.mc.getRenderViewEntity();
                if (renderViewEntity != null) {
                    this.viewFrustum.updateChunkPositions(renderViewEntity.posX, renderViewEntity.posZ);
                }
            }
            this.renderEntitiesStartupCounter = 2;
        }
    }
    
    private void renderSky(final WorldRenderer worldRenderer, final float n, final boolean b) {
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        while (true) {
            float n2 = -384;
            float n3 = -320;
            if (b) {
                n3 = -384;
                n2 = -320;
            }
            worldRenderer.pos(n2, n, -384).endVertex();
            worldRenderer.pos(n3, n, -384).endVertex();
            worldRenderer.pos(n3, n, -320).endVertex();
            worldRenderer.pos(n2, n, -320).endVertex();
            final int n4;
            n4 += 64;
        }
    }
    
    private void renderBlockLayer(final EnumWorldBlockLayer enumWorldBlockLayer) {
        this.mc.entityRenderer.enableLightmap();
        if (OpenGlHelper.useVbo()) {
            GL11.glEnableClientState(32884);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState(32886);
        }
        this.renderContainer.renderChunkLayer(enumWorldBlockLayer);
        if (OpenGlHelper.useVbo()) {
            for (final VertexFormatElement vertexFormatElement : DefaultVertexFormats.BLOCK.getElements()) {
                final VertexFormatElement.EnumUsage usage = vertexFormatElement.getUsage();
                final int index = vertexFormatElement.getIndex();
                switch (RenderGlobal$2.field_178037_a[usage.ordinal()]) {
                    case 1: {
                        GL11.glDisableClientState(32884);
                        continue;
                    }
                    case 2: {
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + index);
                        GL11.glDisableClientState(32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        continue;
                    }
                    case 3: {
                        GL11.glDisableClientState(32886);
                        continue;
                    }
                }
            }
        }
        this.mc.entityRenderer.disableLightmap();
    }
    
    public int getCountActiveRenderers() {
        return this.renderInfos.size();
    }
    
    @Override
    public void markBlockRangeForRenderUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.markBlocksForUpdate(n - 1, n2 - 1, n3 - 1, n4 + 1, n5 + 1, n6 + 1);
    }
    
    @Override
    public void markBlockForUpdate(final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.markBlocksForUpdate(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
    }
    
    private void renderStars(final WorldRenderer worldRenderer) {
        final Random random = new Random(10842L);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        double n;
        double n2;
        double n3;
        double n4;
        double n5;
        while (true) {
            n = random.nextFloat() * 2.0f - 1.0f;
            n2 = random.nextFloat() * 2.0f - 1.0f;
            n3 = random.nextFloat() * 2.0f - 1.0f;
            n4 = 0.15f + random.nextFloat() * 0.1f;
            n5 = n * n + n2 * n2 + n3 * n3;
            if (n5 < 1.0 && n5 > 0.01) {
                break;
            }
            int n6 = 0;
            ++n6;
        }
        final double n7 = 1.0 / Math.sqrt(n5);
        final double n8 = n * n7;
        final double n9 = n2 * n7;
        final double n10 = n3 * n7;
        final double n11 = n8 * 100.0;
        final double n12 = n9 * 100.0;
        final double n13 = n10 * 100.0;
        final double atan2 = Math.atan2(n8, n10);
        final double sin = Math.sin(atan2);
        final double cos = Math.cos(atan2);
        final double atan3 = Math.atan2(Math.sqrt(n8 * n8 + n10 * n10), n9);
        final double sin2 = Math.sin(atan3);
        final double cos2 = Math.cos(atan3);
        final double n14 = random.nextDouble() * 3.141592653589793 * 2.0;
        final double sin3 = Math.sin(n14);
        final double cos3 = Math.cos(n14);
        while (true) {
            final double n15 = -1 * n4;
            final double n16 = -1 * n4;
            final double n17 = n15 * cos3 - n16 * sin3;
            final double n18 = n16 * cos3 + n15 * sin3;
            final double n19 = n17 * sin2 + 0.0 * cos2;
            final double n20 = 0.0 * sin2 - n17 * cos2;
            worldRenderer.pos(n11 + (n20 * sin - n18 * cos), n12 + n19, n13 + (n18 * sin + n20 * cos)).endVertex();
            int n21 = 0;
            ++n21;
        }
    }
    
    public void makeEntityOutlineShader() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifeq            177
        //     6: invokestatic    net/minecraft/client/shader/ShaderLinkHelper.getStaticShaderLinkHelper:()Lnet/minecraft/client/shader/ShaderLinkHelper;
        //     9: new             Lnet/minecraft/util/ResourceLocation;
        //    12: dup            
        //    13: ldc_w           "shaders/post/entity_outline.json"
        //    16: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    19: astore_1       
        //    20: aload_0        
        //    21: new             Lnet/minecraft/client/shader/ShaderGroup;
        //    24: dup            
        //    25: aload_0        
        //    26: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    29: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //    32: aload_0        
        //    33: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    36: invokevirtual   net/minecraft/client/Minecraft.getResourceManager:()Lnet/minecraft/client/resources/IResourceManager;
        //    39: aload_0        
        //    40: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    43: invokevirtual   net/minecraft/client/Minecraft.getFramebuffer:()Lnet/minecraft/client/shader/Framebuffer;
        //    46: aload_1        
        //    47: invokespecial   net/minecraft/client/shader/ShaderGroup.<init>:(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/resources/IResourceManager;Lnet/minecraft/client/shader/Framebuffer;Lnet/minecraft/util/ResourceLocation;)V
        //    50: putfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineShader:Lnet/minecraft/client/shader/ShaderGroup;
        //    53: aload_0        
        //    54: getfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineShader:Lnet/minecraft/client/shader/ShaderGroup;
        //    57: aload_0        
        //    58: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    61: getfield        net/minecraft/client/Minecraft.displayWidth:I
        //    64: aload_0        
        //    65: getfield        net/minecraft/client/renderer/RenderGlobal.mc:Lnet/minecraft/client/Minecraft;
        //    68: getfield        net/minecraft/client/Minecraft.displayHeight:I
        //    71: invokevirtual   net/minecraft/client/shader/ShaderGroup.createBindFramebuffers:(II)V
        //    74: aload_0        
        //    75: aload_0        
        //    76: getfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineShader:Lnet/minecraft/client/shader/ShaderGroup;
        //    79: ldc_w           "final"
        //    82: invokevirtual   net/minecraft/client/shader/ShaderGroup.getFramebufferRaw:(Ljava/lang/String;)Lnet/minecraft/client/shader/Framebuffer;
        //    85: putfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineFramebuffer:Lnet/minecraft/client/shader/Framebuffer;
        //    88: goto            187
        //    91: astore_2       
        //    92: getstatic       net/minecraft/client/renderer/RenderGlobal.logger:Lorg/apache/logging/log4j/Logger;
        //    95: new             Ljava/lang/StringBuilder;
        //    98: dup            
        //    99: invokespecial   java/lang/StringBuilder.<init>:()V
        //   102: ldc_w           "Failed to load shader: "
        //   105: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   108: aload_1        
        //   109: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   112: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   115: aload_2        
        //   116: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   121: aload_0        
        //   122: aconst_null    
        //   123: putfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineShader:Lnet/minecraft/client/shader/ShaderGroup;
        //   126: aload_0        
        //   127: aconst_null    
        //   128: putfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineFramebuffer:Lnet/minecraft/client/shader/Framebuffer;
        //   131: goto            187
        //   134: astore_2       
        //   135: getstatic       net/minecraft/client/renderer/RenderGlobal.logger:Lorg/apache/logging/log4j/Logger;
        //   138: new             Ljava/lang/StringBuilder;
        //   141: dup            
        //   142: invokespecial   java/lang/StringBuilder.<init>:()V
        //   145: ldc_w           "Failed to load shader: "
        //   148: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   151: aload_1        
        //   152: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   155: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   158: aload_2        
        //   159: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   164: aload_0        
        //   165: aconst_null    
        //   166: putfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineShader:Lnet/minecraft/client/shader/ShaderGroup;
        //   169: aload_0        
        //   170: aconst_null    
        //   171: putfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineFramebuffer:Lnet/minecraft/client/shader/Framebuffer;
        //   174: goto            187
        //   177: aload_0        
        //   178: aconst_null    
        //   179: putfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineShader:Lnet/minecraft/client/shader/ShaderGroup;
        //   182: aload_0        
        //   183: aconst_null    
        //   184: putfield        net/minecraft/client/renderer/RenderGlobal.entityOutlineFramebuffer:Lnet/minecraft/client/shader/Framebuffer;
        //   187: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0187 (coming from #0088).
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
    
    private RenderChunk func_181562_a(final BlockPos blockPos, final RenderChunk renderChunk, final EnumFacing enumFacing) {
        final BlockPos positionOffset16 = renderChunk.getPositionOffset16(enumFacing);
        if (positionOffset16.getY() >= 0 && positionOffset16.getY() < 256) {
            final int abs_int = MathHelper.abs_int(blockPos.getX() - positionOffset16.getX());
            final int abs_int2 = MathHelper.abs_int(blockPos.getZ() - positionOffset16.getZ());
            if (Config.isFogOff()) {
                if (abs_int > this.renderDistance || abs_int2 > this.renderDistance) {
                    return null;
                }
            }
            else if (abs_int * abs_int + abs_int2 * abs_int2 > this.renderDistanceSq) {
                return null;
            }
            return this.viewFrustum.getRenderChunk(positionOffset16);
        }
        return null;
    }
    
    public void func_181023_a(final Collection collection, final Collection collection2) {
        final Set field_181024_n = this.field_181024_n;
        // monitorenter(field_181024_n2 = this.field_181024_n)
        this.field_181024_n.removeAll(collection);
        this.field_181024_n.addAll(collection2);
    }
    // monitorexit(field_181024_n2)
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.updateDestroyBlockIcons();
    }
    
    @Override
    public void playSoundToNearExcept(final EntityPlayer entityPlayer, final String s, final double n, final double n2, final double n3, final float n4, final float n5) {
    }
    
    public void setWorldAndLoadRenderers(final WorldClient theWorld) {
        if (this.theWorld != null) {
            this.theWorld.removeWorldAccess(this);
        }
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.renderManager.set(theWorld);
        if ((this.theWorld = theWorld) != null) {
            theWorld.addWorldAccess(this);
            this.loadRenderers();
        }
    }
    
    public int getCountRenderers() {
        return this.viewFrustum.renderChunks.length;
    }
    
    private void fixTerrainFrustum(final double field_181059_a, final double field_181060_b, final double field_181061_c) {
        this.debugFixedClippingHelper = new ClippingHelperImpl();
        ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
        final Matrix4f matrix4f = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
        matrix4f.transpose();
        final Matrix4f matrix4f2 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
        matrix4f2.transpose();
        final Matrix4f matrix4f3 = new Matrix4f();
        Matrix4f.mul((org.lwjgl.util.vector.Matrix4f)matrix4f2, (org.lwjgl.util.vector.Matrix4f)matrix4f, (org.lwjgl.util.vector.Matrix4f)matrix4f3);
        matrix4f3.invert();
        this.debugTerrainFrustumPosition.field_181059_a = field_181059_a;
        this.debugTerrainFrustumPosition.field_181060_b = field_181060_b;
        this.debugTerrainFrustumPosition.field_181061_c = field_181061_c;
        this.debugTerrainMatrix[0] = new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[1] = new Vector4f(1.0f, -1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[2] = new Vector4f(1.0f, 1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[3] = new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f);
        this.debugTerrainMatrix[4] = new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[5] = new Vector4f(1.0f, -1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[6] = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.debugTerrainMatrix[7] = new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f);
        while (true) {
            Matrix4f.transform((org.lwjgl.util.vector.Matrix4f)matrix4f3, this.debugTerrainMatrix[0], this.debugTerrainMatrix[0]);
            final Vector4f vector4f = this.debugTerrainMatrix[0];
            vector4f.x /= this.debugTerrainMatrix[0].w;
            final Vector4f vector4f2 = this.debugTerrainMatrix[0];
            vector4f2.y /= this.debugTerrainMatrix[0].w;
            final Vector4f vector4f3 = this.debugTerrainMatrix[0];
            vector4f3.z /= this.debugTerrainMatrix[0].w;
            this.debugTerrainMatrix[0].w = 1.0f;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void playRecord(final String s, final BlockPos blockPos) {
        final ISound sound = this.mapSoundPositions.get(blockPos);
        if (sound != null) {
            this.mc.getSoundHandler().stopSound(sound);
            this.mapSoundPositions.remove(blockPos);
        }
        if (s != null) {
            final ItemRecord record = ItemRecord.getRecord(s);
            if (record != null) {
                this.mc.ingameGUI.setRecordPlayingMessage(record.getRecordNameLocal());
            }
            ResourceLocation resourceLocation = null;
            if (Reflector.ForgeItemRecord_getRecordResource.exists() && record != null) {
                resourceLocation = (ResourceLocation)Reflector.call(record, Reflector.ForgeItemRecord_getRecordResource, s);
            }
            if (resourceLocation == null) {
                resourceLocation = new ResourceLocation(s);
            }
            final PositionedSoundRecord create = PositionedSoundRecord.create(resourceLocation, (float)blockPos.getX(), (float)blockPos.getY(), (float)blockPos.getZ());
            this.mapSoundPositions.put(blockPos, create);
            this.mc.getSoundHandler().playSound(create);
        }
    }
    
    static {
        logger = LogManager.getLogger();
        locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
        locationSunPng = new ResourceLocation("textures/environment/sun.png");
        locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
        locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
        locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
        SET_ALL_FACINGS = Collections.unmodifiableSet((Set<?>)new HashSet<Object>(Arrays.asList(EnumFacing.VALUES)));
    }
    
    @Override
    public void playSound(final String s, final double n, final double n2, final double n3, final float n4, final float n5) {
    }
    
    public boolean hasCloudFog(final double n, final double n2, final double n3, final float n4) {
        return false;
    }
    
    private void generateSky2() {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        if (this.sky2VBO != null) {
            this.sky2VBO.deleteGlBuffers();
        }
        if (this.glSkyList2 >= 0) {
            GLAllocation.deleteDisplayLists(this.glSkyList2);
            this.glSkyList2 = -1;
        }
        if (this.vboEnabled) {
            this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderSky(worldRenderer, -16.0f, true);
            worldRenderer.finishDrawing();
            worldRenderer.reset();
            this.sky2VBO.func_181722_a(worldRenderer.getByteBuffer());
        }
        else {
            GL11.glNewList(this.glSkyList2 = GLAllocation.generateDisplayLists(1), 4864);
            this.renderSky(worldRenderer, -16.0f, true);
            instance.draw();
            GL11.glEndList();
        }
    }
    
    private Set getVisibleFacings(final BlockPos blockPos) {
        final VisGraph visGraph = new VisGraph();
        final BlockPos blockPos2 = new BlockPos(blockPos.getX() >> 4 << 4, blockPos.getY() >> 4 << 4, blockPos.getZ() >> 4 << 4);
        final Chunk chunkFromBlockCoords = this.theWorld.getChunkFromBlockCoords(blockPos2);
        for (final BlockPos.MutableBlockPos mutableBlockPos : BlockPos.getAllInBoxMutable(blockPos2, blockPos2.add(15, 15, 15))) {
            if (chunkFromBlockCoords.getBlock(mutableBlockPos).isOpaqueCube()) {
                visGraph.func_178606_a(mutableBlockPos);
            }
        }
        return visGraph.func_178609_b(blockPos);
    }
    
    public int getCountTileEntitiesRendered() {
        return this.countTileEntitiesRendered;
    }
    
    public String getDebugInfoEntities() {
        return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersion();
    }
    
    public String getDebugInfoRenders() {
        final int length = this.viewFrustum.renderChunks.length;
        final Iterator<ContainerLocalRenderInformation> iterator = this.renderInfos.iterator();
        while (iterator.hasNext()) {
            final CompiledChunk compiledChunk = iterator.next().renderChunk.compiledChunk;
            if (compiledChunk != CompiledChunk.DUMMY && !compiledChunk.isEmpty()) {
                int n = 0;
                ++n;
            }
        }
        return String.format("C: %d/%d %sD: %d, %s", 0, length, this.mc.renderChunksMany ? "(s) " : "", this.renderDistanceChunks, this.renderDispatcher.getDebugInfo());
    }
    
    private void markBlocksForUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.viewFrustum.markBlocksForUpdate(n, n2, n3, n4, n5, n6);
    }
    
    public static void func_181563_a(final AxisAlignedBB axisAlignedBB, final int n, final int n2, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
    }
    
    public void updateChunks(final long n) {
        this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(n);
        if (this.chunksToUpdateForced.size() > 0) {
            final Iterator<RenderChunk> iterator = (Iterator<RenderChunk>)this.chunksToUpdateForced.iterator();
            while (iterator.hasNext()) {
                final RenderChunk renderChunk = iterator.next();
                if (!this.renderDispatcher.updateChunkLater(renderChunk)) {
                    break;
                }
                renderChunk.setNeedsUpdate(false);
                iterator.remove();
                this.chunksToUpdate.remove(renderChunk);
                this.chunksToResortTransparency.remove(renderChunk);
            }
        }
        if (this.chunksToResortTransparency.size() > 0) {
            final Iterator<RenderChunk> iterator2 = (Iterator<RenderChunk>)this.chunksToResortTransparency.iterator();
            if (iterator2.hasNext() && this.renderDispatcher.updateTransparencyLater(iterator2.next())) {
                iterator2.remove();
            }
        }
        int updatesPerFrame = Config.getUpdatesPerFrame();
        final int n2 = updatesPerFrame * 2;
        final Iterator<RenderChunk> iterator3 = (Iterator<RenderChunk>)this.chunksToUpdate.iterator();
        while (iterator3.hasNext()) {
            final RenderChunk renderChunk2 = iterator3.next();
            if (!this.renderDispatcher.updateChunkLater(renderChunk2)) {
                break;
            }
            renderChunk2.setNeedsUpdate(false);
            iterator3.remove();
            if (renderChunk2.getCompiledChunk().isEmpty() && updatesPerFrame < n2) {
                ++updatesPerFrame;
            }
            int n3 = 0;
            ++n3;
            if (0 >= updatesPerFrame) {
                break;
            }
        }
    }
    
    static final class RenderGlobal$2
    {
        private static final String __OBFID = "CL_00002535";
        
        static {
            (RenderGlobal$2.field_178037_a = new int[VertexFormatElement.EnumUsage.values().length])[VertexFormatElement.EnumUsage.POSITION.ordinal()] = 1;
            RenderGlobal$2.field_178037_a[VertexFormatElement.EnumUsage.UV.ordinal()] = 2;
            RenderGlobal$2.field_178037_a[VertexFormatElement.EnumUsage.COLOR.ordinal()] = 3;
        }
    }
    
    class ContainerLocalRenderInformation
    {
        final RenderChunk renderChunk;
        private static final String __OBFID = "CL_00002534";
        final EnumFacing facing;
        final int counter;
        final RenderGlobal this$0;
        final Set setFacing;
        
        ContainerLocalRenderInformation(final RenderGlobal renderGlobal, final RenderChunk renderChunk, final EnumFacing enumFacing, final int n, final Object o) {
            this(renderGlobal, renderChunk, enumFacing, n);
        }
        
        private ContainerLocalRenderInformation(final RenderGlobal this$0, final RenderChunk renderChunk, final EnumFacing facing, final int counter) {
            this.this$0 = this$0;
            this.setFacing = EnumSet.noneOf(EnumFacing.class);
            this.renderChunk = renderChunk;
            this.facing = facing;
            this.counter = counter;
        }
    }
}
