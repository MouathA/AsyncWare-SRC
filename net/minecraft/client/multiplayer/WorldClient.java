package net.minecraft.client.multiplayer;

import optfine.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.client.audio.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.profiler.*;
import com.google.common.collect.*;
import net.minecraft.world.storage.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.client.particle.*;

public class WorldClient extends World
{
    private final Set previousActiveChunkSet;
    private BlockPosM randomTickPosM;
    private final Set entityList;
    private NetHandlerPlayClient sendQueue;
    private final Set entitySpawnQueue;
    private static final String __OBFID = "CL_00000882";
    private ChunkProviderClient clientChunkProvider;
    private final Minecraft mc;
    
    @Override
    public void playSound(final double n, final double n2, final double n3, final String s, final float n4, final float n5, final boolean b) {
        final double distanceSq = this.mc.getRenderViewEntity().getDistanceSq(n, n2, n3);
        final PositionedSoundRecord positionedSoundRecord = new PositionedSoundRecord(new ResourceLocation(s), n4, n5, (float)n, (float)n2, (float)n3);
        if (b && distanceSq > 100.0) {
            this.mc.getSoundHandler().playDelayedSound(positionedSoundRecord, (int)(Math.sqrt(distanceSq) / 40.0 * 20.0));
        }
        else {
            this.mc.getSoundHandler().playSound(positionedSoundRecord);
        }
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        return this.clientChunkProvider = new ChunkProviderClient(this);
    }
    
    public void removeAllEntities() {
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        int n = 0;
        while (0 < this.unloadedEntityList.size()) {
            final Entity entity = this.unloadedEntityList.get(0);
            final int chunkCoordX = entity.chunkCoordX;
            final int chunkCoordZ = entity.chunkCoordZ;
            if (entity.addedToChunk && this.isChunkLoaded(chunkCoordX, chunkCoordZ, true)) {
                this.getChunkFromChunkCoords(chunkCoordX, chunkCoordZ).removeEntity(entity);
            }
            ++n;
        }
        while (0 < this.unloadedEntityList.size()) {
            this.onEntityRemoved(this.unloadedEntityList.get(0));
            ++n;
        }
        this.unloadedEntityList.clear();
        while (0 < this.loadedEntityList.size()) {
            final Entity entity2 = this.loadedEntityList.get(0);
            Label_0269: {
                if (entity2.ridingEntity != null) {
                    if (!entity2.ridingEntity.isDead && entity2.ridingEntity.riddenByEntity == entity2) {
                        break Label_0269;
                    }
                    entity2.ridingEntity.riddenByEntity = null;
                    entity2.ridingEntity = null;
                }
                if (entity2.isDead) {
                    final int chunkCoordX2 = entity2.chunkCoordX;
                    final int chunkCoordZ2 = entity2.chunkCoordZ;
                    if (entity2.addedToChunk && this.isChunkLoaded(chunkCoordX2, chunkCoordZ2, true)) {
                        this.getChunkFromChunkCoords(chunkCoordX2, chunkCoordZ2).removeEntity(entity2);
                    }
                    final List loadedEntityList = this.loadedEntityList;
                    final int n2 = 0;
                    --n;
                    loadedEntityList.remove(n2);
                    this.onEntityRemoved(entity2);
                }
            }
            ++n;
        }
    }
    
    public void playSoundAtPos(final BlockPos blockPos, final String s, final float n, final float n2, final boolean b) {
        this.playSound(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, s, n, n2, b);
    }
    
    public boolean invalidateRegionAndSetBlock(final BlockPos blockPos, final IBlockState blockState) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.invalidateBlockReceiveRegion(x, y, z, x, y, z);
        return super.setBlockState(blockPos, blockState, 3);
    }
    
    public void doPreChunk(final int n, final int n2, final boolean b) {
        if (b) {
            this.clientChunkProvider.loadChunk(n, n2);
        }
        else {
            this.clientChunkProvider.unloadChunk(n, n2);
        }
        if (!b) {
            this.markBlockRangeForRenderUpdate(n * 16, 0, n2 * 16, n * 16 + 15, 256, n2 * 16 + 15);
        }
    }
    
    @Override
    public void setWorldTime(long worldTime) {
        if (worldTime < 0L) {
            worldTime = -worldTime;
            this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
        }
        else {
            this.getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
        }
        super.setWorldTime(worldTime);
    }
    
    @Override
    protected void onEntityRemoved(final Entity entity) {
        super.onEntityRemoved(entity);
        if (this.entityList.contains(entity)) {
            if (entity.isEntityAlive()) {
                this.entitySpawnQueue.add(entity);
            }
            else {
                this.entityList.remove(entity);
            }
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        this.setTotalWorldTime(this.getTotalWorldTime() + 1L);
        if (this.getGameRules().getBoolean("doDaylightCycle")) {
            this.setWorldTime(this.getWorldTime() + 1L);
        }
        this.theProfiler.startSection("reEntryProcessing");
        while (!this.entitySpawnQueue.isEmpty()) {
            final Entity entity = this.entitySpawnQueue.iterator().next();
            this.entitySpawnQueue.remove(entity);
            if (!this.loadedEntityList.contains(entity)) {
                this.spawnEntityInWorld(entity);
            }
            int n = 0;
            ++n;
        }
        this.theProfiler.endStartSection("chunkCache");
        this.clientChunkProvider.unloadQueuedChunks();
        this.theProfiler.endStartSection("blocks");
        this.updateBlocks();
        this.theProfiler.endSection();
    }
    
    static Set access$000(final WorldClient worldClient) {
        return worldClient.entityList;
    }
    
    public void doVoidFogParticles(final int n, final int n2, final int n3) {
        final Random random = new Random();
        final ItemStack heldItem = this.mc.thePlayer.getHeldItem();
        final boolean b = this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE && heldItem != null && Block.getBlockFromItem(heldItem.getItem()) == Blocks.barrier;
        final BlockPosM randomTickPosM = this.randomTickPosM;
        while (true) {
            final int n4 = n + this.rand.nextInt(16) - this.rand.nextInt(16);
            final int n5 = n2 + this.rand.nextInt(16) - this.rand.nextInt(16);
            final int n6 = n3 + this.rand.nextInt(16) - this.rand.nextInt(16);
            randomTickPosM.setXyz(n4, n5, n6);
            final IBlockState blockState = this.getBlockState(randomTickPosM);
            blockState.getBlock().randomDisplayTick(this, randomTickPosM, blockState, random);
            if (b && blockState.getBlock() == Blocks.barrier) {
                this.spawnParticle(EnumParticleTypes.BARRIER, n4 + 0.5f, n5 + 0.5f, n6 + 0.5f, 0.0, 0.0, 0.0, new int[0]);
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    @Override
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport crashReport) {
        final CrashReportCategory addWorldInfoToCrashReport = super.addWorldInfoToCrashReport(crashReport);
        addWorldInfoToCrashReport.addCrashSectionCallable("Forced entities", new Callable(this) {
            private static final String __OBFID = "CL_00000883";
            final WorldClient this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() {
                return WorldClient.access$000(this.this$0).size() + " total; " + WorldClient.access$000(this.this$0).toString();
            }
        });
        addWorldInfoToCrashReport.addCrashSectionCallable("Retry entities", new Callable(this) {
            final WorldClient this$0;
            private static final String __OBFID = "CL_00000884";
            
            @Override
            public String call() {
                return WorldClient.access$100(this.this$0).size() + " total; " + WorldClient.access$100(this.this$0).toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        addWorldInfoToCrashReport.addCrashSectionCallable("Server brand", new Callable(this) {
            private static final String __OBFID = "CL_00000885";
            final WorldClient this$0;
            
            @Override
            public String call() throws Exception {
                return WorldClient.access$200(this.this$0).thePlayer.getClientBrand();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        addWorldInfoToCrashReport.addCrashSectionCallable("Server type", new Callable(this) {
            final WorldClient this$0;
            private static final String __OBFID = "CL_00000886";
            
            @Override
            public String call() throws Exception {
                return (WorldClient.access$200(this.this$0).getIntegratedServer() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        return addWorldInfoToCrashReport;
    }
    
    @Override
    public Entity getEntityByID(final int n) {
        return (n == this.mc.thePlayer.getEntityId()) ? this.mc.thePlayer : super.getEntityByID(n);
    }
    
    static Minecraft access$200(final WorldClient worldClient) {
        return worldClient.mc;
    }
    
    @Override
    public void removeEntity(final Entity entity) {
        super.removeEntity(entity);
        this.entityList.remove(entity);
    }
    
    @Override
    protected int getRenderDistanceChunks() {
        return this.mc.gameSettings.renderDistanceChunks;
    }
    
    public WorldClient(final NetHandlerPlayClient sendQueue, final WorldSettings worldSettings, final int n, final EnumDifficulty difficulty, final Profiler profiler) {
        super(new SaveHandlerMP(), new WorldInfo(worldSettings, "MpServer"), WorldProvider.getProviderForDimension(n), profiler, true);
        this.entityList = Sets.newHashSet();
        this.entitySpawnQueue = Sets.newHashSet();
        this.mc = Minecraft.getMinecraft();
        this.previousActiveChunkSet = Sets.newHashSet();
        this.randomTickPosM = new BlockPosM(0, 0, 0, 3);
        this.sendQueue = sendQueue;
        this.getWorldInfo().setDifficulty(difficulty);
        this.provider.registerWorld(this);
        this.setSpawnPoint(new BlockPos(8, 64, 8));
        this.chunkProvider = this.createChunkProvider();
        this.mapStorage = new SaveDataMemoryStorage();
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
    }
    
    public Entity removeEntityFromWorld(final int n) {
        final Entity entity = (Entity)this.entitiesById.removeObject(n);
        if (entity != null) {
            this.entityList.remove(entity);
            this.removeEntity(entity);
        }
        return entity;
    }
    
    @Override
    public void sendQuittingDisconnectingPacket() {
        this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
    }
    
    @Override
    protected void onEntityAdded(final Entity entity) {
        super.onEntityAdded(entity);
        if (this.entitySpawnQueue.contains(entity)) {
            this.entitySpawnQueue.remove(entity);
        }
    }
    
    @Override
    protected void updateWeather() {
    }
    
    public void setWorldScoreboard(final Scoreboard worldScoreboard) {
        this.worldScoreboard = worldScoreboard;
    }
    
    public void invalidateBlockReceiveRegion(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    @Override
    protected void updateBlocks() {
        super.updateBlocks();
        this.previousActiveChunkSet.retainAll(this.activeChunkSet);
        if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
            this.previousActiveChunkSet.clear();
        }
        for (final ChunkCoordIntPair chunkCoordIntPair : this.activeChunkSet) {
            if (!this.previousActiveChunkSet.contains(chunkCoordIntPair)) {
                final int n = chunkCoordIntPair.chunkXPos * 16;
                final int n2 = chunkCoordIntPair.chunkZPos * 16;
                this.theProfiler.startSection("getChunk");
                this.playMoodSoundAndCheckLight(n, n2, this.getChunkFromChunkCoords(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos));
                this.theProfiler.endSection();
                this.previousActiveChunkSet.add(chunkCoordIntPair);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    @Override
    public void makeFireworks(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final NBTTagCompound nbtTagCompound) {
        this.mc.effectRenderer.addEffect(new EntityFirework.StarterFX(this, n, n2, n3, n4, n5, n6, this.mc.effectRenderer, nbtTagCompound));
    }
    
    public void addEntityToWorld(final int entityId, final Entity entity) {
        final Entity entityByID = this.getEntityByID(entityId);
        if (entityByID != null) {
            this.removeEntity(entityByID);
        }
        this.entityList.add(entity);
        entity.setEntityId(entityId);
        if (entity == 0) {
            this.entitySpawnQueue.add(entity);
        }
        this.entitiesById.addKey(entityId, entity);
    }
    
    static Set access$100(final WorldClient worldClient) {
        return worldClient.entitySpawnQueue;
    }
}
