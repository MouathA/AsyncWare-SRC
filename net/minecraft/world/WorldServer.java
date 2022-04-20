package net.minecraft.world;

import net.minecraft.world.gen.*;
import net.minecraft.server.management.*;
import net.minecraft.server.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.chunk.*;
import org.apache.logging.log4j.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.entity.player.*;
import net.minecraft.profiler.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.effect.*;
import net.minecraft.world.chunk.storage.*;
import com.google.common.util.concurrent.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.world.storage.*;
import net.minecraft.village.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.play.server.*;

public class WorldServer extends World implements IThreadListener
{
    private List pendingTickListEntriesThisTick;
    private final TreeSet pendingTickListEntriesTreeSet;
    public ChunkProviderServer theChunkProviderServer;
    private final EntityTracker theEntityTracker;
    private static final List bonusChestContent;
    protected final VillageSiege villageSiege;
    private ServerBlockEventList[] field_147490_S;
    private boolean allPlayersSleeping;
    private final SpawnerAnimals mobSpawner;
    private static final Logger logger;
    private int blockEventCacheIndex;
    private final PlayerManager thePlayerManager;
    private final Set pendingTickListEntriesHashSet;
    private final Map entitiesByUuid;
    public boolean disableLevelSaving;
    private int updateEntityTick;
    private final Teleporter worldTeleporter;
    private final MinecraftServer mcServer;
    
    public void saveAllChunks(final boolean b, final IProgressUpdate progressUpdate) throws MinecraftException {
        if (this.chunkProvider.canSave()) {
            if (progressUpdate != null) {
                progressUpdate.displaySavingString("Saving level");
            }
            this.saveLevel();
            if (progressUpdate != null) {
                progressUpdate.displayLoadingString("Saving chunks");
            }
            this.chunkProvider.saveChunks(b, progressUpdate);
            for (final Chunk chunk : Lists.newArrayList((Iterable)this.theChunkProviderServer.func_152380_a())) {
                if (chunk != null && !this.thePlayerManager.hasPlayerInstance(chunk.xPosition, chunk.zPosition)) {
                    this.theChunkProviderServer.dropChunk(chunk.xPosition, chunk.zPosition);
                }
            }
        }
    }
    
    public Teleporter getDefaultTeleporter() {
        return this.worldTeleporter;
    }
    
    public void saveChunkData() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.saveExtraData();
        }
    }
    
    private void setDebugWorldSettings() {
        this.worldInfo.setMapFeaturesEnabled(false);
        this.worldInfo.setAllowCommands(true);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThundering(false);
        this.worldInfo.setCleanWeatherTime(1000000000);
        this.worldInfo.setWorldTime(6000L);
        this.worldInfo.setGameType(WorldSettings.GameType.SPECTATOR);
        this.worldInfo.setHardcore(false);
        this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
        this.worldInfo.setDifficultyLocked(true);
        this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
    }
    
    @Override
    public List func_175712_a(final StructureBoundingBox structureBoundingBox, final boolean b) {
        List<NextTickListEntry> arrayList = null;
        while (true) {
            final Iterator<NextTickListEntry> iterator = (Iterator<NextTickListEntry>)this.pendingTickListEntriesTreeSet.iterator();
            while (iterator.hasNext()) {
                final NextTickListEntry nextTickListEntry = iterator.next();
                final BlockPos position = nextTickListEntry.position;
                if (position.getX() >= structureBoundingBox.minX && position.getX() < structureBoundingBox.maxX && position.getZ() >= structureBoundingBox.minZ && position.getZ() < structureBoundingBox.maxZ) {
                    if (b) {
                        this.pendingTickListEntriesHashSet.remove(nextTickListEntry);
                        iterator.remove();
                    }
                    if (arrayList == null) {
                        arrayList = (List<NextTickListEntry>)Lists.newArrayList();
                    }
                    arrayList.add(nextTickListEntry);
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    private void resetRainAndThunder() {
        this.worldInfo.setRainTime(0);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThunderTime(0);
        this.worldInfo.setThundering(false);
    }
    
    public Entity getEntityFromUuid(final UUID uuid) {
        return this.entitiesByUuid.get(uuid);
    }
    
    public void resetUpdateEntityTick() {
        this.updateEntityTick = 0;
    }
    
    @Override
    public void updateEntityWithOptionalForce(final Entity entity, final boolean b) {
        if (!this.canSpawnAnimals() && (entity instanceof EntityAnimal || entity instanceof EntityWaterMob)) {
            entity.setDead();
        }
        if (!this.canSpawnNPCs() && entity instanceof INpc) {
            entity.setDead();
        }
        super.updateEntityWithOptionalForce(entity, b);
    }
    
    public MinecraftServer getMinecraftServer() {
        return this.mcServer;
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        return this.theChunkProviderServer = new ChunkProviderServer(this, this.saveHandler.getChunkLoader(this.provider), this.provider.createChunkGenerator());
    }
    
    static {
        logger = LogManager.getLogger();
        bonusChestContent = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10) });
    }
    
    public BiomeGenBase.SpawnListEntry getSpawnListEntryForTypeAt(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        final List possibleCreatures = this.getChunkProvider().getPossibleCreatures(enumCreatureType, blockPos);
        return (possibleCreatures != null && !possibleCreatures.isEmpty()) ? ((BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, possibleCreatures)) : null;
    }
    
    @Override
    public void setInitialSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(this.func_181545_F() + 1);
        }
        int spawnX = this.worldInfo.getSpawnX();
        int spawnZ = this.worldInfo.getSpawnZ();
        if (this.getGroundAboveSeaLevel(new BlockPos(spawnX, 0, spawnZ)).getMaterial() == Material.air) {
            spawnX += this.rand.nextInt(8) - this.rand.nextInt(8);
            spawnZ += this.rand.nextInt(8) - this.rand.nextInt(8);
            int n = 0;
            ++n;
        }
        this.worldInfo.setSpawnX(spawnX);
        this.worldInfo.setSpawnZ(spawnZ);
    }
    
    @Override
    protected void updateWeather() {
        final boolean raining = this.isRaining();
        super.updateWeather();
        if (this.prevRainingStrength != this.rainingStrength) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionId());
        }
        if (this.prevThunderingStrength != this.thunderingStrength) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionId());
        }
        if (raining != this.isRaining()) {
            if (raining) {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0f));
            }
            else {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(1, 0.0f));
            }
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, this.rainingStrength));
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, this.thunderingStrength));
        }
    }
    
    public BlockPos getSpawnCoordinate() {
        return this.provider.getSpawnCoordinate();
    }
    
    @Override
    public void updateBlockTick(final BlockPos blockPos, final Block block, final int n, final int priority) {
        final NextTickListEntry nextTickListEntry = new NextTickListEntry(blockPos, block);
        if (this.scheduledUpdatesAreImmediate && block.getMaterial() != Material.air && block.requiresUpdates()) {
            if (this.isAreaLoaded(nextTickListEntry.position.add(-8, -8, -8), nextTickListEntry.position.add(8, 8, 8))) {
                final IBlockState blockState = this.getBlockState(nextTickListEntry.position);
                if (blockState.getBlock().getMaterial() != Material.air && blockState.getBlock() == nextTickListEntry.getBlock()) {
                    blockState.getBlock().updateTick(this, nextTickListEntry.position, blockState, this.rand);
                }
            }
            return;
        }
        if (this.isAreaLoaded(blockPos.add(-8, -8, -8), blockPos.add(8, 8, 8))) {
            if (block.getMaterial() != Material.air) {
                nextTickListEntry.setScheduledTime(1 + this.worldInfo.getWorldTotalTime());
                nextTickListEntry.setPriority(priority);
            }
            if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
                this.pendingTickListEntriesHashSet.add(nextTickListEntry);
                this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
            }
        }
    }
    
    public boolean canCreatureTypeSpawnHere(final EnumCreatureType enumCreatureType, final BiomeGenBase.SpawnListEntry spawnListEntry, final BlockPos blockPos) {
        final List possibleCreatures = this.getChunkProvider().getPossibleCreatures(enumCreatureType, blockPos);
        return possibleCreatures != null && !possibleCreatures.isEmpty() && possibleCreatures.contains(spawnListEntry);
    }
    
    private boolean canSpawnAnimals() {
        return this.mcServer.getCanSpawnAnimals();
    }
    
    @Override
    public boolean tickUpdates(final boolean b) {
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return false;
        }
        this.pendingTickListEntriesTreeSet.size();
        if (1000 != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        this.theProfiler.startSection("cleaning");
        while (true) {
            final NextTickListEntry nextTickListEntry = this.pendingTickListEntriesTreeSet.first();
            if (!b && nextTickListEntry.scheduledTime > this.worldInfo.getWorldTotalTime()) {
                break;
            }
            this.pendingTickListEntriesTreeSet.remove(nextTickListEntry);
            this.pendingTickListEntriesHashSet.remove(nextTickListEntry);
            this.pendingTickListEntriesThisTick.add(nextTickListEntry);
            int n = 0;
            ++n;
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("ticking");
        final Iterator<NextTickListEntry> iterator = (Iterator<NextTickListEntry>)this.pendingTickListEntriesThisTick.iterator();
        while (iterator.hasNext()) {
            final NextTickListEntry nextTickListEntry2 = iterator.next();
            iterator.remove();
            if (this.isAreaLoaded(nextTickListEntry2.position.add(0, 0, 0), nextTickListEntry2.position.add(0, 0, 0))) {
                final IBlockState blockState = this.getBlockState(nextTickListEntry2.position);
                if (blockState.getBlock().getMaterial() == Material.air || !Block.isEqualTo(blockState.getBlock(), nextTickListEntry2.getBlock())) {
                    continue;
                }
                blockState.getBlock().updateTick(this, nextTickListEntry2.position, blockState, this.rand);
            }
            else {
                this.scheduleUpdate(nextTickListEntry2.position, nextTickListEntry2.getBlock(), 0);
            }
        }
        this.theProfiler.endSection();
        this.pendingTickListEntriesThisTick.clear();
        return !this.pendingTickListEntriesTreeSet.isEmpty();
    }
    
    @Override
    public List getPendingBlockUpdates(final Chunk chunk, final boolean b) {
        final ChunkCoordIntPair chunkCoordIntPair = chunk.getChunkCoordIntPair();
        final int n = (chunkCoordIntPair.chunkXPos << 4) - 2;
        final int n2 = n + 16 + 2;
        final int n3 = (chunkCoordIntPair.chunkZPos << 4) - 2;
        return this.func_175712_a(new StructureBoundingBox(n, 0, n3, n2, 256, n3 + 16 + 2), b);
    }
    
    protected void createBonusChest() {
        while (!new WorldGeneratorBonusChest(WorldServer.bonusChestContent, 10).generate(this, this.rand, this.getTopSolidOrLiquidBlock(new BlockPos(this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6), 0, this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6))).up())) {
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public Explosion newExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2) {
        final Explosion explosion = new Explosion(this, entity, n, n2, n3, n4, b, b2);
        explosion.doExplosionA();
        explosion.doExplosionB(false);
        if (!b2) {
            explosion.func_180342_d();
        }
        for (final EntityPlayer entityPlayer : this.playerEntities) {
            if (entityPlayer.getDistanceSq(n, n2, n3) < 4096.0) {
                ((EntityPlayerMP)entityPlayer).playerNetServerHandler.sendPacket(new S27PacketExplosion(n, n2, n3, n4, explosion.getAffectedBlockPositions(), (Vec3)explosion.getPlayerKnockbackMap().get(entityPlayer)));
            }
        }
        return explosion;
    }
    
    @Override
    public void setEntityState(final Entity entity, final byte b) {
        this.getEntityTracker().func_151248_b(entity, new S19PacketEntityStatus(entity, b));
    }
    
    public WorldServer(final MinecraftServer mcServer, final ISaveHandler saveHandler, final WorldInfo worldInfo, final int n, final Profiler profiler) {
        super(saveHandler, worldInfo, WorldProvider.getProviderForDimension(n), profiler, false);
        this.pendingTickListEntriesHashSet = Sets.newHashSet();
        this.pendingTickListEntriesTreeSet = new TreeSet();
        this.entitiesByUuid = Maps.newHashMap();
        this.mobSpawner = new SpawnerAnimals();
        this.villageSiege = new VillageSiege(this);
        this.field_147490_S = new ServerBlockEventList[] { new ServerBlockEventList((WorldServer$1)null), new ServerBlockEventList((WorldServer$1)null) };
        this.pendingTickListEntriesThisTick = Lists.newArrayList();
        this.mcServer = mcServer;
        this.theEntityTracker = new EntityTracker(this);
        this.thePlayerManager = new PlayerManager(this);
        this.provider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        this.worldTeleporter = new Teleporter(this);
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.getWorldBorder().setSize(mcServer.getMaxWorldSize());
    }
    
    @Override
    protected int getRenderDistanceChunks() {
        return this.mcServer.getConfigurationManager().getViewDistance();
    }
    
    @Override
    public void addBlockEvent(final BlockPos blockPos, final Block block, final int n, final int n2) {
        final BlockEventData blockEventData = new BlockEventData(blockPos, block, n, n2);
        final Iterator<BlockEventData> iterator = this.field_147490_S[this.blockEventCacheIndex].iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(blockEventData)) {
                return;
            }
        }
        this.field_147490_S[this.blockEventCacheIndex].add(blockEventData);
    }
    
    @Override
    public void scheduleUpdate(final BlockPos blockPos, final Block block, final int n) {
        this.updateBlockTick(blockPos, block, n, 0);
    }
    
    private void createSpawnPosition(final WorldSettings worldSettings) {
        if (!this.provider.canRespawnHere()) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.up(this.provider.getAverageGroundLevel()));
        }
        else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.up());
        }
        else {
            this.findingSpawnPoint = true;
            final WorldChunkManager worldChunkManager = this.provider.getWorldChunkManager();
            final List biomesToSpawnIn = worldChunkManager.getBiomesToSpawnIn();
            final Random random = new Random(this.getSeed());
            final BlockPos biomePosition = worldChunkManager.findBiomePosition(0, 0, 256, biomesToSpawnIn, random);
            final int averageGroundLevel = this.provider.getAverageGroundLevel();
            if (biomePosition != null) {
                biomePosition.getX();
                biomePosition.getZ();
            }
            else {
                WorldServer.logger.warn("Unable to find spawn biome");
            }
            if (!this.provider.canCoordinateBeSpawn(0, 0)) {
                final int n = 0 + (random.nextInt(64) - random.nextInt(64));
                final int n2 = 0 + (random.nextInt(64) - random.nextInt(64));
                int n3 = 0;
                ++n3;
            }
            this.worldInfo.setSpawn(new BlockPos(0, averageGroundLevel, 0));
            this.findingSpawnPoint = false;
            if (worldSettings.isBonusChestEnabled()) {
                this.createBonusChest();
            }
        }
    }
    
    @Override
    public void updateEntities() {
        if (this.playerEntities.isEmpty()) {
            if (this.updateEntityTick++ >= 1200) {
                return;
            }
        }
        else {
            this.resetUpdateEntityTick();
        }
        super.updateEntities();
    }
    
    @Override
    protected void updateBlocks() {
        super.updateBlocks();
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            for (final ChunkCoordIntPair chunkCoordIntPair : this.activeChunkSet) {
                this.getChunkFromChunkCoords(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos).func_150804_b(false);
            }
        }
        else {
            for (final ChunkCoordIntPair chunkCoordIntPair2 : this.activeChunkSet) {
                final int n = chunkCoordIntPair2.chunkXPos * 16;
                final int n2 = chunkCoordIntPair2.chunkZPos * 16;
                this.theProfiler.startSection("getChunk");
                final Chunk chunkFromChunkCoords = this.getChunkFromChunkCoords(chunkCoordIntPair2.chunkXPos, chunkCoordIntPair2.chunkZPos);
                this.playMoodSoundAndCheckLight(n, n2, chunkFromChunkCoords);
                this.theProfiler.endStartSection("tickChunk");
                chunkFromChunkCoords.func_150804_b(false);
                this.theProfiler.endStartSection("thunder");
                if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
                    this.updateLCG = this.updateLCG * 3 + 1013904223;
                    final int n3 = this.updateLCG >> 2;
                    final BlockPos adjustPosToNearbyEntity = this.adjustPosToNearbyEntity(new BlockPos(n + (n3 & 0xF), 0, n2 + (n3 >> 8 & 0xF)));
                    if (this.canLightningStrike(adjustPosToNearbyEntity)) {
                        this.addWeatherEffect(new EntityLightningBolt(this, adjustPosToNearbyEntity.getX(), adjustPosToNearbyEntity.getY(), adjustPosToNearbyEntity.getZ()));
                    }
                }
                this.theProfiler.endStartSection("iceandsnow");
                if (this.rand.nextInt(16) == 0) {
                    this.updateLCG = this.updateLCG * 3 + 1013904223;
                    final int n4 = this.updateLCG >> 2;
                    final BlockPos precipitationHeight = this.getPrecipitationHeight(new BlockPos(n + (n4 & 0xF), 0, n2 + (n4 >> 8 & 0xF)));
                    final BlockPos down = precipitationHeight.down();
                    if (this.canBlockFreezeNoWater(down)) {
                        this.setBlockState(down, Blocks.ice.getDefaultState());
                    }
                    if (this.isRaining() && this.canSnowAt(precipitationHeight, true)) {
                        this.setBlockState(precipitationHeight, Blocks.snow_layer.getDefaultState());
                    }
                    if (this.isRaining() && this.getBiomeGenForCoords(down).canSpawnLightningBolt()) {
                        this.getBlockState(down).getBlock().fillWithRain(this, down);
                    }
                }
                this.theProfiler.endStartSection("tickBlocks");
                final int int1 = this.getGameRules().getInt("randomTickSpeed");
                if (int1 > 0) {
                    final ExtendedBlockStorage[] blockStorageArray = chunkFromChunkCoords.getBlockStorageArray();
                    while (0 < blockStorageArray.length) {
                        final ExtendedBlockStorage extendedBlockStorage = blockStorageArray[0];
                        if (extendedBlockStorage != null && extendedBlockStorage.getNeedsRandomTick()) {
                            while (0 < int1) {
                                this.updateLCG = this.updateLCG * 3 + 1013904223;
                                final int n5 = this.updateLCG >> 2;
                                final int n6 = n5 & 0xF;
                                final int n7 = n5 >> 8 & 0xF;
                                final int n8 = n5 >> 16 & 0xF;
                                int n9 = 0;
                                ++n9;
                                final IBlockState value = extendedBlockStorage.get(n6, n8, n7);
                                final Block block = value.getBlock();
                                if (block.getTickRandomly()) {
                                    int n10 = 0;
                                    ++n10;
                                    block.randomTick(this, new BlockPos(n6 + n, n8 + extendedBlockStorage.getYLocation(), n7 + n2), value, this.rand);
                                }
                                int n11 = 0;
                                ++n11;
                            }
                        }
                        int n12 = 0;
                        ++n12;
                    }
                }
                this.theProfiler.endSection();
            }
        }
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final int n4, final double n5, final double n6, final double n7, final double n8, final int... array) {
        this.spawnParticle(enumParticleTypes, false, n, n2, n3, n4, n5, n6, n7, n8, array);
    }
    
    @Override
    public ListenableFuture addScheduledTask(final Runnable runnable) {
        return this.mcServer.addScheduledTask(runnable);
    }
    
    @Override
    public boolean isCallingFromMinecraftThread() {
        return this.mcServer.isCallingFromMinecraftThread();
    }
    
    protected BlockPos adjustPosToNearbyEntity(final BlockPos blockPos) {
        final BlockPos precipitationHeight = this.getPrecipitationHeight(blockPos);
        final List entitiesWithinAABB = this.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(precipitationHeight, new BlockPos(precipitationHeight.getX(), this.getHeight(), precipitationHeight.getZ())).expand(3.0, 3.0, 3.0), (Predicate)new Predicate(this) {
            final WorldServer this$0;
            
            public boolean apply(final Object o) {
                return this.apply((EntityLivingBase)o);
            }
            
            public boolean apply(final EntityLivingBase entityLivingBase) {
                return entityLivingBase != null && entityLivingBase.isEntityAlive() && this.this$0.canSeeSky(entityLivingBase.getPosition());
            }
        });
        return entitiesWithinAABB.isEmpty() ? precipitationHeight : entitiesWithinAABB.get(this.rand.nextInt(entitiesWithinAABB.size())).getPosition();
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final boolean b, final double n, final double n2, final double n3, final int n4, final double n5, final double n6, final double n7, final double n8, final int... array) {
        final S2APacketParticles s2APacketParticles = new S2APacketParticles(enumParticleTypes, b, (float)n, (float)n2, (float)n3, (float)n5, (float)n6, (float)n7, (float)n8, n4, array);
        while (0 < this.playerEntities.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntities.get(0);
            final double distanceSq = entityPlayerMP.getPosition().distanceSq(n, n2, n3);
            if (distanceSq <= 256.0 || (b && distanceSq <= 65536.0)) {
                entityPlayerMP.playerNetServerHandler.sendPacket(s2APacketParticles);
            }
            int n9 = 0;
            ++n9;
        }
    }
    
    protected void saveLevel() throws MinecraftException {
        this.checkSessionLock();
        this.worldInfo.setBorderSize(this.getWorldBorder().getDiameter());
        this.worldInfo.getBorderCenterX(this.getWorldBorder().getCenterX());
        this.worldInfo.getBorderCenterZ(this.getWorldBorder().getCenterZ());
        this.worldInfo.setBorderSafeZone(this.getWorldBorder().getDamageBuffer());
        this.worldInfo.setBorderDamagePerBlock(this.getWorldBorder().getDamageAmount());
        this.worldInfo.setBorderWarningDistance(this.getWorldBorder().getWarningDistance());
        this.worldInfo.setBorderWarningTime(this.getWorldBorder().getWarningTime());
        this.worldInfo.setBorderLerpTarget(this.getWorldBorder().getTargetSize());
        this.worldInfo.setBorderLerpTime(this.getWorldBorder().getTimeUntilTarget());
        this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }
    
    @Override
    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = false;
        if (!this.playerEntities.isEmpty()) {
            for (final EntityPlayer entityPlayer : this.playerEntities) {
                if (entityPlayer.isSpectator()) {
                    int n = 0;
                    ++n;
                }
                else {
                    if (!entityPlayer.isPlayerSleeping()) {
                        continue;
                    }
                    int n2 = 0;
                    ++n2;
                }
            }
            this.allPlayersSleeping = false;
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.getWorldInfo().isHardcoreModeEnabled() && this.getDifficulty() != EnumDifficulty.HARD) {
            this.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
        }
        this.provider.getWorldChunkManager().cleanupCache();
        if (this != 0) {
            if (this.getGameRules().getBoolean("doDaylightCycle")) {
                final long n = this.worldInfo.getWorldTime() + 24000L;
                this.worldInfo.setWorldTime(n - n % 24000L);
            }
            this.wakeAllPlayers();
        }
        this.theProfiler.startSection("mobSpawner");
        if (this.getGameRules().getBoolean("doMobSpawning") && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            this.mobSpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
        }
        this.theProfiler.endStartSection("chunkSource");
        this.chunkProvider.unloadQueuedChunks();
        final int calculateSkylightSubtracted = this.calculateSkylightSubtracted(1.0f);
        if (calculateSkylightSubtracted != this.getSkylightSubtracted()) {
            this.setSkylightSubtracted(calculateSkylightSubtracted);
        }
        this.worldInfo.setWorldTotalTime(this.worldInfo.getWorldTotalTime() + 1L);
        if (this.getGameRules().getBoolean("doDaylightCycle")) {
            this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
        }
        this.theProfiler.endStartSection("tickPending");
        this.tickUpdates(false);
        this.theProfiler.endStartSection("tickBlocks");
        this.updateBlocks();
        this.theProfiler.endStartSection("chunkMap");
        this.thePlayerManager.updatePlayerInstances();
        this.theProfiler.endStartSection("village");
        this.villageCollectionObj.tick();
        this.villageSiege.tick();
        this.theProfiler.endStartSection("portalForcer");
        this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
        this.theProfiler.endSection();
        this.sendQueuedBlockEvents();
    }
    
    public EntityTracker getEntityTracker() {
        return this.theEntityTracker;
    }
    
    @Override
    public void scheduleBlockUpdate(final BlockPos blockPos, final Block block, final int n, final int priority) {
        final NextTickListEntry nextTickListEntry = new NextTickListEntry(blockPos, block);
        nextTickListEntry.setPriority(priority);
        if (block.getMaterial() != Material.air) {
            nextTickListEntry.setScheduledTime(n + this.worldInfo.getWorldTotalTime());
        }
        if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
            this.pendingTickListEntriesHashSet.add(nextTickListEntry);
            this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
        }
    }
    
    protected void wakeAllPlayers() {
        this.allPlayersSleeping = false;
        for (final EntityPlayer entityPlayer : this.playerEntities) {
            if (entityPlayer.isPlayerSleeping()) {
                entityPlayer.wakeUpPlayer(false, false, true);
            }
        }
        this.resetRainAndThunder();
    }
    
    private void sendQueuedBlockEvents() {
        while (!this.field_147490_S[this.blockEventCacheIndex].isEmpty()) {
            final int blockEventCacheIndex = this.blockEventCacheIndex;
            this.blockEventCacheIndex ^= 0x1;
            for (final BlockEventData blockEventData : this.field_147490_S[blockEventCacheIndex]) {
                if (this == blockEventData) {
                    this.mcServer.getConfigurationManager().sendToAllNear(blockEventData.getPosition().getX(), blockEventData.getPosition().getY(), blockEventData.getPosition().getZ(), 64.0, this.provider.getDimensionId(), new S24PacketBlockAction(blockEventData.getPosition(), blockEventData.getBlock(), blockEventData.getEventID(), blockEventData.getEventParameter()));
                }
            }
            this.field_147490_S[blockEventCacheIndex].clear();
        }
    }
    
    @Override
    protected void onEntityAdded(final Entity entity) {
        super.onEntityAdded(entity);
        this.entitiesById.addKey(entity.getEntityId(), entity);
        this.entitiesByUuid.put(entity.getUniqueID(), entity);
        final Entity[] parts = entity.getParts();
        if (parts != null) {
            while (0 < parts.length) {
                this.entitiesById.addKey(parts[0].getEntityId(), parts[0]);
                int n = 0;
                ++n;
            }
        }
    }
    
    public PlayerManager getPlayerManager() {
        return this.thePlayerManager;
    }
    
    private boolean canSpawnNPCs() {
        return this.mcServer.getCanSpawnNPCs();
    }
    
    @Override
    public void initialize(final WorldSettings worldSettings) {
        if (!this.worldInfo.isInitialized()) {
            this.createSpawnPosition(worldSettings);
            if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
                this.setDebugWorldSettings();
            }
            super.initialize(worldSettings);
            this.worldInfo.setServerInitialized(true);
        }
    }
    
    public void flush() {
        this.saveHandler.flush();
    }
    
    public List getTileEntitiesIn(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.loadedTileEntityList.size()) {
            final TileEntity tileEntity = this.loadedTileEntityList.get(0);
            final BlockPos pos = tileEntity.getPos();
            if (pos.getX() >= n && pos.getY() >= n2 && pos.getZ() >= n3 && pos.getX() < n4 && pos.getY() < n5 && pos.getZ() < n6) {
                arrayList.add(tileEntity);
            }
            int n7 = 0;
            ++n7;
        }
        return arrayList;
    }
    
    @Override
    protected void onEntityRemoved(final Entity entity) {
        super.onEntityRemoved(entity);
        this.entitiesById.removeObject(entity.getEntityId());
        this.entitiesByUuid.remove(entity.getUniqueID());
        final Entity[] parts = entity.getParts();
        if (parts != null) {
            while (0 < parts.length) {
                this.entitiesById.removeObject(parts[0].getEntityId());
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    public boolean isBlockModifiable(final EntityPlayer entityPlayer, final BlockPos blockPos) {
        return !this.mcServer.isBlockProtected(this, blockPos, entityPlayer) && this.getWorldBorder().contains(blockPos);
    }
    
    @Override
    public boolean isBlockTickPending(final BlockPos blockPos, final Block block) {
        return this.pendingTickListEntriesThisTick.contains(new NextTickListEntry(blockPos, block));
    }
    
    @Override
    public World init() {
        this.mapStorage = new MapStorage(this.saveHandler);
        final String fileNameForProvider = VillageCollection.fileNameForProvider(this.provider);
        final VillageCollection villageCollectionObj = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, fileNameForProvider);
        if (villageCollectionObj == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(fileNameForProvider, this.villageCollectionObj);
        }
        else {
            (this.villageCollectionObj = villageCollectionObj).setWorldsForAll(this);
        }
        this.worldScoreboard = new ServerScoreboard(this.mcServer);
        ScoreboardSaveData scoreboardSaveData = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
        if (scoreboardSaveData == null) {
            scoreboardSaveData = new ScoreboardSaveData();
            this.mapStorage.setData("scoreboard", scoreboardSaveData);
        }
        scoreboardSaveData.setScoreboard(this.worldScoreboard);
        ((ServerScoreboard)this.worldScoreboard).func_96547_a(scoreboardSaveData);
        this.getWorldBorder().setCenter(this.worldInfo.getBorderCenterX(), this.worldInfo.getBorderCenterZ());
        this.getWorldBorder().setDamageAmount(this.worldInfo.getBorderDamagePerBlock());
        this.getWorldBorder().setDamageBuffer(this.worldInfo.getBorderSafeZone());
        this.getWorldBorder().setWarningDistance(this.worldInfo.getBorderWarningDistance());
        this.getWorldBorder().setWarningTime(this.worldInfo.getBorderWarningTime());
        if (this.worldInfo.getBorderLerpTime() > 0L) {
            this.getWorldBorder().setTransition(this.worldInfo.getBorderSize(), this.worldInfo.getBorderLerpTarget(), this.worldInfo.getBorderLerpTime());
        }
        else {
            this.getWorldBorder().setTransition(this.worldInfo.getBorderSize());
        }
        return this;
    }
    
    @Override
    public boolean addWeatherEffect(final Entity entity) {
        if (super.addWeatherEffect(entity)) {
            this.mcServer.getConfigurationManager().sendToAllNear(entity.posX, entity.posY, entity.posZ, 512.0, this.provider.getDimensionId(), new S2CPacketSpawnGlobalEntity(entity));
            return true;
        }
        return false;
    }
    
    static class ServerBlockEventList extends ArrayList
    {
        private ServerBlockEventList() {
        }
        
        ServerBlockEventList(final WorldServer$1 predicate) {
            this();
        }
    }
}
