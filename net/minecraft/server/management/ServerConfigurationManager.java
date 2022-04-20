package net.minecraft.server.management;

import java.io.*;
import java.text.*;
import net.minecraft.server.*;
import net.minecraft.world.demo.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.world.border.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.stats.*;
import net.minecraft.potion.*;
import java.util.*;
import java.net.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.storage.*;

public abstract class ServerConfigurationManager
{
    private static final Logger logger;
    public static final File FILE_IPBANS;
    protected int maxPlayers;
    private final Map uuidToPlayerMap;
    public static final File FILE_OPS;
    private boolean whiteListEnforced;
    private int playerPingIndex;
    private WorldSettings.GameType gameType;
    private static final SimpleDateFormat dateFormat;
    public static final File FILE_PLAYERBANS;
    private boolean commandsAllowedForAll;
    private final List playerEntityList;
    private final UserListBans bannedPlayers;
    private final BanList bannedIPs;
    private int viewDistance;
    private final MinecraftServer mcServer;
    private IPlayerFileData playerNBTManagerObj;
    private final UserListOps ops;
    private final UserListWhitelist whiteListedPlayers;
    private final Map playerStatFiles;
    public static final File FILE_WHITELIST;
    
    public void serverUpdateMountedMovingPlayer(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(entityPlayerMP);
    }
    
    public void sendChatMsg(final IChatComponent chatComponent) {
        this.sendChatMsgImpl(chatComponent, true);
    }
    
    public EntityPlayerMP recreatePlayerEntity(final EntityPlayerMP entityPlayerMP, final int dimension, final boolean b) {
        entityPlayerMP.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(entityPlayerMP);
        entityPlayerMP.getServerForPlayer().getEntityTracker().untrackEntity(entityPlayerMP);
        entityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(entityPlayerMP);
        this.playerEntityList.remove(entityPlayerMP);
        this.mcServer.worldServerForDimension(entityPlayerMP.dimension).removePlayerEntityDangerously(entityPlayerMP);
        final BlockPos bedLocation = entityPlayerMP.getBedLocation();
        final boolean spawnForced = entityPlayerMP.isSpawnForced();
        entityPlayerMP.dimension = dimension;
        ItemInWorldManager itemInWorldManager;
        if (this.mcServer.isDemo()) {
            itemInWorldManager = new DemoWorldManager(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        }
        else {
            itemInWorldManager = new ItemInWorldManager(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        }
        final EntityPlayerMP entityPlayerMP2 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(entityPlayerMP.dimension), entityPlayerMP.getGameProfile(), itemInWorldManager);
        entityPlayerMP2.playerNetServerHandler = entityPlayerMP.playerNetServerHandler;
        entityPlayerMP2.clonePlayer(entityPlayerMP, b);
        entityPlayerMP2.setEntityId(entityPlayerMP.getEntityId());
        entityPlayerMP2.func_174817_o(entityPlayerMP);
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        this.setPlayerGameTypeBasedOnOther(entityPlayerMP2, entityPlayerMP, worldServerForDimension);
        if (bedLocation != null) {
            final BlockPos bedSpawnLocation = EntityPlayer.getBedSpawnLocation(this.mcServer.worldServerForDimension(entityPlayerMP.dimension), bedLocation, spawnForced);
            if (bedSpawnLocation != null) {
                entityPlayerMP2.setLocationAndAngles(bedSpawnLocation.getX() + 0.5f, bedSpawnLocation.getY() + 0.1f, bedSpawnLocation.getZ() + 0.5f, 0.0f, 0.0f);
                entityPlayerMP2.setSpawnPoint(bedLocation, spawnForced);
            }
            else {
                entityPlayerMP2.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0f));
            }
        }
        worldServerForDimension.theChunkProviderServer.loadChunk((int)entityPlayerMP2.posX >> 4, (int)entityPlayerMP2.posZ >> 4);
        while (!worldServerForDimension.getCollidingBoundingBoxes(entityPlayerMP2, entityPlayerMP2.getEntityBoundingBox()).isEmpty() && entityPlayerMP2.posY < 256.0) {
            entityPlayerMP2.setPosition(entityPlayerMP2.posX, entityPlayerMP2.posY + 1.0, entityPlayerMP2.posZ);
        }
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayerMP2.dimension, entityPlayerMP2.worldObj.getDifficulty(), entityPlayerMP2.worldObj.getWorldInfo().getTerrainType(), entityPlayerMP2.theItemInWorldManager.getGameType()));
        final BlockPos spawnPoint = worldServerForDimension.getSpawnPoint();
        entityPlayerMP2.playerNetServerHandler.setPlayerLocation(entityPlayerMP2.posX, entityPlayerMP2.posY, entityPlayerMP2.posZ, entityPlayerMP2.rotationYaw, entityPlayerMP2.rotationPitch);
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(spawnPoint));
        entityPlayerMP2.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(entityPlayerMP2.experience, entityPlayerMP2.experienceTotal, entityPlayerMP2.experienceLevel));
        this.updateTimeAndWeatherForPlayer(entityPlayerMP2, worldServerForDimension);
        worldServerForDimension.getPlayerManager().addPlayer(entityPlayerMP2);
        worldServerForDimension.spawnEntityInWorld(entityPlayerMP2);
        this.playerEntityList.add(entityPlayerMP2);
        this.uuidToPlayerMap.put(entityPlayerMP2.getUniqueID(), entityPlayerMP2);
        entityPlayerMP2.addSelfToInternalCraftingInventory();
        entityPlayerMP2.setHealth(entityPlayerMP2.getHealth());
        return entityPlayerMP2;
    }
    
    public UserListBans getBannedPlayers() {
        return this.bannedPlayers;
    }
    
    public void sendToAllNear(final double n, final double n2, final double n3, final double n4, final int n5, final Packet packet) {
        this.sendToAllNearExcept(null, n, n2, n3, n4, n5, packet);
    }
    
    public void setPlayerManager(final WorldServer[] array) {
        this.playerNBTManagerObj = array[0].getSaveHandler().getPlayerNBTManager();
        array[0].getWorldBorder().addListener(new IBorderListener(this) {
            final ServerConfigurationManager this$0;
            
            @Override
            public void onWarningDistanceChanged(final WorldBorder worldBorder, final int n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
            }
            
            @Override
            public void onWarningTimeChanged(final WorldBorder worldBorder, final int n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_WARNING_TIME));
            }
            
            @Override
            public void onSizeChanged(final WorldBorder worldBorder, final double n) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_SIZE));
            }
            
            @Override
            public void onTransitionStarted(final WorldBorder worldBorder, final double n, final double n2, final long n3) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.LERP_SIZE));
            }
            
            @Override
            public void onCenterChanged(final WorldBorder worldBorder, final double n, final double n2) {
                this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(worldBorder, S44PacketWorldBorder.Action.SET_CENTER));
            }
            
            @Override
            public void onDamageAmountChanged(final WorldBorder worldBorder, final double n) {
            }
            
            @Override
            public void onDamageBufferChanged(final WorldBorder worldBorder, final double n) {
            }
        });
    }
    
    public void addOp(final GameProfile gameProfile) {
        this.ops.addEntry(new UserListOpsEntry(gameProfile, this.mcServer.getOpPermissionLevel(), this.ops.func_183026_b(gameProfile)));
    }
    
    public void syncPlayerInventory(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.sendContainerToPlayer(entityPlayerMP.inventoryContainer);
        entityPlayerMP.setPlayerHealthUpdated();
        entityPlayerMP.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
    }
    
    public NBTTagCompound getHostPlayerData() {
        return null;
    }
    
    public void playerLoggedOut(final EntityPlayerMP entityPlayerMP) {
        entityPlayerMP.triggerAchievement(StatList.leaveGameStat);
        this.writePlayerData(entityPlayerMP);
        final WorldServer serverForPlayer = entityPlayerMP.getServerForPlayer();
        if (entityPlayerMP.ridingEntity != null) {
            serverForPlayer.removePlayerEntityDangerously(entityPlayerMP.ridingEntity);
            ServerConfigurationManager.logger.debug("removing player mount");
        }
        serverForPlayer.removeEntity(entityPlayerMP);
        serverForPlayer.getPlayerManager().removePlayer(entityPlayerMP);
        this.playerEntityList.remove(entityPlayerMP);
        final UUID uniqueID = entityPlayerMP.getUniqueID();
        if (this.uuidToPlayerMap.get(uniqueID) == entityPlayerMP) {
            this.uuidToPlayerMap.remove(uniqueID);
            this.playerStatFiles.remove(uniqueID);
        }
        this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[] { entityPlayerMP }));
    }
    
    public int getCurrentPlayerCount() {
        return this.playerEntityList.size();
    }
    
    public UserListWhitelist getWhitelistedPlayers() {
        return this.whiteListedPlayers;
    }
    
    public void onTick() {
        if (++this.playerPingIndex > 600) {
            this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
            this.playerPingIndex = 0;
        }
    }
    
    public void sendToAllNearExcept(final EntityPlayer entityPlayer, final double n, final double n2, final double n3, final double n4, final int n5, final Packet packet) {
        while (0 < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(0);
            if (entityPlayerMP != entityPlayer && entityPlayerMP.dimension == n5) {
                final double n6 = n - entityPlayerMP.posX;
                final double n7 = n2 - entityPlayerMP.posY;
                final double n8 = n3 - entityPlayerMP.posZ;
                if (n6 * n6 + n7 * n7 + n8 * n8 < n4 * n4) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                }
            }
            int n9 = 0;
            ++n9;
        }
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public String[] getOppedPlayerNames() {
        return this.ops.getKeys();
    }
    
    protected void writePlayerData(final EntityPlayerMP entityPlayerMP) {
        this.playerNBTManagerObj.writePlayerData(entityPlayerMP);
        final StatisticsFile statisticsFile = this.playerStatFiles.get(entityPlayerMP.getUniqueID());
        if (statisticsFile != null) {
            statisticsFile.saveStatFile();
        }
    }
    
    public NBTTagCompound readPlayerDataFromFile(final EntityPlayerMP entityPlayerMP) {
        final NBTTagCompound playerNBTTagCompound = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
        NBTTagCompound playerData;
        if (entityPlayerMP.getName().equals(this.mcServer.getServerOwner()) && playerNBTTagCompound != null) {
            entityPlayerMP.readFromNBT(playerNBTTagCompound);
            playerData = playerNBTTagCompound;
            ServerConfigurationManager.logger.debug("loading single player");
        }
        else {
            playerData = this.playerNBTManagerObj.readPlayerData(entityPlayerMP);
        }
        return playerData;
    }
    
    public String func_181058_b(final boolean b) {
        String s = "";
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.playerEntityList);
        while (0 < arrayList.size()) {
            s += ((EntityPlayerMP)arrayList.get(0)).getName();
            if (b) {
                s = s + " (" + ((EntityPlayerMP)arrayList.get(0)).getUniqueID().toString() + ")";
            }
            int n = 0;
            ++n;
        }
        return s;
    }
    
    public void preparePlayer(final EntityPlayerMP entityPlayerMP, final WorldServer worldServer) {
        final WorldServer serverForPlayer = entityPlayerMP.getServerForPlayer();
        if (worldServer != null) {
            worldServer.getPlayerManager().removePlayer(entityPlayerMP);
        }
        serverForPlayer.getPlayerManager().addPlayer(entityPlayerMP);
        serverForPlayer.theChunkProviderServer.loadChunk((int)entityPlayerMP.posX >> 4, (int)entityPlayerMP.posZ >> 4);
    }
    
    public void transferEntityToWorld(final Entity entity, final int n, final WorldServer worldServer, final WorldServer world) {
        final double posX = entity.posX;
        final double posZ = entity.posZ;
        final double n2 = 8.0;
        final float rotationYaw = entity.rotationYaw;
        worldServer.theProfiler.startSection("moving");
        double n3;
        double n4;
        if (entity.dimension == -1) {
            n3 = MathHelper.clamp_double(posX / n2, world.getWorldBorder().minX() + 16.0, world.getWorldBorder().maxX() - 16.0);
            n4 = MathHelper.clamp_double(posZ / n2, world.getWorldBorder().minZ() + 16.0, world.getWorldBorder().maxZ() - 16.0);
            entity.setLocationAndAngles(n3, entity.posY, n4, entity.rotationYaw, entity.rotationPitch);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, false);
            }
        }
        else if (entity.dimension == 0) {
            n3 = MathHelper.clamp_double(posX * n2, world.getWorldBorder().minX() + 16.0, world.getWorldBorder().maxX() - 16.0);
            n4 = MathHelper.clamp_double(posZ * n2, world.getWorldBorder().minZ() + 16.0, world.getWorldBorder().maxZ() - 16.0);
            entity.setLocationAndAngles(n3, entity.posY, n4, entity.rotationYaw, entity.rotationPitch);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, false);
            }
        }
        else {
            BlockPos blockPos;
            if (n == 1) {
                blockPos = world.getSpawnPoint();
            }
            else {
                blockPos = world.getSpawnCoordinate();
            }
            n3 = blockPos.getX();
            entity.posY = blockPos.getY();
            n4 = blockPos.getZ();
            entity.setLocationAndAngles(n3, entity.posY, n4, 90.0f, 0.0f);
            if (entity.isEntityAlive()) {
                worldServer.updateEntityWithOptionalForce(entity, false);
            }
        }
        worldServer.theProfiler.endSection();
        if (n != 1) {
            worldServer.theProfiler.startSection("placing");
            final double n5 = MathHelper.clamp_int((int)n3, -29999872, 29999872);
            final double n6 = MathHelper.clamp_int((int)n4, -29999872, 29999872);
            if (entity.isEntityAlive()) {
                entity.setLocationAndAngles(n5, entity.posY, n6, entity.rotationYaw, entity.rotationPitch);
                world.getDefaultTeleporter().placeInPortal(entity, rotationYaw);
                world.spawnEntityInWorld(entity);
                world.updateEntityWithOptionalForce(entity, false);
            }
            worldServer.theProfiler.endSection();
        }
        entity.setWorld(world);
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
        this.gameType = gameType;
    }
    
    public void setWhiteListEnabled(final boolean whiteListEnforced) {
        this.whiteListEnforced = whiteListEnforced;
    }
    
    public void sendPacketToAllPlayers(final Packet packet) {
        while (0 < this.playerEntityList.size()) {
            this.playerEntityList.get(0).playerNetServerHandler.sendPacket(packet);
            int n = 0;
            ++n;
        }
    }
    
    public String[] getAllUsernames() {
        final String[] array = new String[this.playerEntityList.size()];
        while (0 < this.playerEntityList.size()) {
            array[0] = this.playerEntityList.get(0).getName();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void sendChatMsgImpl(final IChatComponent chatComponent, final boolean b) {
        this.mcServer.addChatMessage(chatComponent);
        this.sendPacketToAllPlayers(new S02PacketChat(chatComponent, (byte)(b ? 1 : 0)));
    }
    
    public void transferPlayerToDimension(final EntityPlayerMP entityPlayerMP, final int dimension) {
        final int dimension2 = entityPlayerMP.dimension;
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        entityPlayerMP.dimension = dimension;
        final WorldServer worldServerForDimension2 = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        entityPlayerMP.playerNetServerHandler.sendPacket(new S07PacketRespawn(entityPlayerMP.dimension, entityPlayerMP.worldObj.getDifficulty(), entityPlayerMP.worldObj.getWorldInfo().getTerrainType(), entityPlayerMP.theItemInWorldManager.getGameType()));
        worldServerForDimension.removePlayerEntityDangerously(entityPlayerMP);
        entityPlayerMP.isDead = false;
        this.transferEntityToWorld(entityPlayerMP, dimension2, worldServerForDimension, worldServerForDimension2);
        this.preparePlayer(entityPlayerMP, worldServerForDimension);
        entityPlayerMP.playerNetServerHandler.setPlayerLocation(entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
        entityPlayerMP.theItemInWorldManager.setWorld(worldServerForDimension2);
        this.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerForDimension2);
        this.syncPlayerInventory(entityPlayerMP);
        final Iterator<PotionEffect> iterator = (Iterator<PotionEffect>)entityPlayerMP.getActivePotionEffects().iterator();
        while (iterator.hasNext()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), iterator.next()));
        }
    }
    
    public String[] getWhitelistedPlayerNames() {
        return this.whiteListedPlayers.getKeys();
    }
    
    public String allowUserToConnect(final SocketAddress socketAddress, final GameProfile gameProfile) {
        if (this.bannedPlayers.isBanned(gameProfile)) {
            final UserListBansEntry userListBansEntry = (UserListBansEntry)this.bannedPlayers.getEntry(gameProfile);
            String s = "You are banned from this server!\nReason: " + userListBansEntry.getBanReason();
            if (userListBansEntry.getBanEndDate() != null) {
                s = s + "\nYour ban will be removed on " + ServerConfigurationManager.dateFormat.format(userListBansEntry.getBanEndDate());
            }
            return s;
        }
        if (gameProfile != 0) {
            return "You are not white-listed on this server!";
        }
        if (this.bannedIPs.isBanned(socketAddress)) {
            final IPBanEntry banEntry = this.bannedIPs.getBanEntry(socketAddress);
            String s2 = "Your IP address is banned from this server!\nReason: " + banEntry.getBanReason();
            if (banEntry.getBanEndDate() != null) {
                s2 = s2 + "\nYour ban will be removed on " + ServerConfigurationManager.dateFormat.format(banEntry.getBanEndDate());
            }
            return s2;
        }
        return (this.playerEntityList.size() >= this.maxPlayers && !this.func_183023_f(gameProfile)) ? "The server is full!" : null;
    }
    
    public BanList getBannedIPs() {
        return this.bannedIPs;
    }
    
    public ServerConfigurationManager(final MinecraftServer mcServer) {
        this.playerEntityList = Lists.newArrayList();
        this.uuidToPlayerMap = Maps.newHashMap();
        this.bannedPlayers = new UserListBans(ServerConfigurationManager.FILE_PLAYERBANS);
        this.bannedIPs = new BanList(ServerConfigurationManager.FILE_IPBANS);
        this.ops = new UserListOps(ServerConfigurationManager.FILE_OPS);
        this.whiteListedPlayers = new UserListWhitelist(ServerConfigurationManager.FILE_WHITELIST);
        this.playerStatFiles = Maps.newHashMap();
        this.mcServer = mcServer;
        this.bannedPlayers.setLanServer(false);
        this.bannedIPs.setLanServer(false);
        this.maxPlayers = 8;
    }
    
    public void updateTimeAndWeatherForPlayer(final EntityPlayerMP entityPlayerMP, final WorldServer worldServer) {
        entityPlayerMP.playerNetServerHandler.sendPacket(new S44PacketWorldBorder(this.mcServer.worldServers[0].getWorldBorder(), S44PacketWorldBorder.Action.INITIALIZE));
        entityPlayerMP.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(worldServer.getTotalWorldTime(), worldServer.getWorldTime(), worldServer.getGameRules().getBoolean("doDaylightCycle")));
        if (worldServer.isRaining()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0f));
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, worldServer.getRainStrength(1.0f)));
            entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, worldServer.getThunderStrength(1.0f)));
        }
    }
    
    public EntityPlayerMP createPlayerForUser(final GameProfile gameProfile) {
        final UUID uuid = EntityPlayer.getUUID(gameProfile);
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(0);
            if (entityPlayerMP.getUniqueID().equals(uuid)) {
                arrayList.add(entityPlayerMP);
            }
            int n = 0;
            ++n;
        }
        final EntityPlayerMP entityPlayerMP2 = this.uuidToPlayerMap.get(gameProfile.getId());
        if (entityPlayerMP2 != null && !arrayList.contains(entityPlayerMP2)) {
            arrayList.add(entityPlayerMP2);
        }
        final Iterator<EntityPlayerMP> iterator = (Iterator<EntityPlayerMP>)arrayList.iterator();
        while (iterator.hasNext()) {
            iterator.next().playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
        }
        ItemInWorldManager itemInWorldManager;
        if (this.mcServer.isDemo()) {
            itemInWorldManager = new DemoWorldManager(this.mcServer.worldServerForDimension(0));
        }
        else {
            itemInWorldManager = new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
        }
        return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), gameProfile, itemInWorldManager);
    }
    
    public GameProfile[] getAllProfiles() {
        final GameProfile[] array = new GameProfile[this.playerEntityList.size()];
        while (0 < this.playerEntityList.size()) {
            array[0] = this.playerEntityList.get(0).getGameProfile();
            int n = 0;
            ++n;
        }
        return array;
    }
    
    public void sendMessageToAllTeamMembers(final EntityPlayer entityPlayer, final IChatComponent chatComponent) {
        final Team team = entityPlayer.getTeam();
        if (team != null) {
            final Iterator iterator = team.getMembershipCollection().iterator();
            while (iterator.hasNext()) {
                final EntityPlayerMP playerByUsername = this.getPlayerByUsername(iterator.next());
                if (playerByUsername != null && playerByUsername != entityPlayer) {
                    playerByUsername.addChatMessage(chatComponent);
                }
            }
        }
    }
    
    static {
        FILE_PLAYERBANS = new File("banned-players.json");
        FILE_IPBANS = new File("banned-ips.json");
        FILE_OPS = new File("ops.json");
        FILE_WHITELIST = new File("whitelist.json");
        logger = LogManager.getLogger();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }
    
    public void loadWhiteList() {
    }
    
    public EntityPlayerMP getPlayerByUsername(final String s) {
        for (final EntityPlayerMP entityPlayerMP : this.playerEntityList) {
            if (entityPlayerMP.getName().equalsIgnoreCase(s)) {
                return entityPlayerMP;
            }
        }
        return null;
    }
    
    public void removeAllPlayers() {
        while (0 < this.playerEntityList.size()) {
            this.playerEntityList.get(0).playerNetServerHandler.kickPlayerFromServer("Server closed");
            int n = 0;
            ++n;
        }
    }
    
    public void setCommandsAllowedForAll(final boolean commandsAllowedForAll) {
        this.commandsAllowedForAll = commandsAllowedForAll;
    }
    
    public void removePlayerFromWhitelist(final GameProfile gameProfile) {
        this.whiteListedPlayers.removeEntry(gameProfile);
    }
    
    public void initializeConnectionToPlayer(final NetworkManager networkManager, final EntityPlayerMP entityPlayerMP) {
        final GameProfile gameProfile = entityPlayerMP.getGameProfile();
        final PlayerProfileCache playerProfileCache = this.mcServer.getPlayerProfileCache();
        final GameProfile profileByUUID = playerProfileCache.getProfileByUUID(gameProfile.getId());
        final String s = (profileByUUID == null) ? gameProfile.getName() : profileByUUID.getName();
        playerProfileCache.addEntry(gameProfile);
        final NBTTagCompound playerDataFromFile = this.readPlayerDataFromFile(entityPlayerMP);
        entityPlayerMP.setWorld(this.mcServer.worldServerForDimension(entityPlayerMP.dimension));
        entityPlayerMP.theItemInWorldManager.setWorld((WorldServer)entityPlayerMP.worldObj);
        String string = "local";
        if (networkManager.getRemoteAddress() != null) {
            string = networkManager.getRemoteAddress().toString();
        }
        ServerConfigurationManager.logger.info(entityPlayerMP.getName() + "[" + string + "] logged in with entity id " + entityPlayerMP.getEntityId() + " at (" + entityPlayerMP.posX + ", " + entityPlayerMP.posY + ", " + entityPlayerMP.posZ + ")");
        final WorldServer worldServerForDimension = this.mcServer.worldServerForDimension(entityPlayerMP.dimension);
        final WorldInfo worldInfo = worldServerForDimension.getWorldInfo();
        final BlockPos spawnPoint = worldServerForDimension.getSpawnPoint();
        this.setPlayerGameTypeBasedOnOther(entityPlayerMP, null, worldServerForDimension);
        final NetHandlerPlayServer netHandlerPlayServer = new NetHandlerPlayServer(this.mcServer, networkManager, entityPlayerMP);
        netHandlerPlayServer.sendPacket(new S01PacketJoinGame(entityPlayerMP.getEntityId(), entityPlayerMP.theItemInWorldManager.getGameType(), worldInfo.isHardcoreModeEnabled(), worldServerForDimension.provider.getDimensionId(), worldServerForDimension.getDifficulty(), this.getMaxPlayers(), worldInfo.getTerrainType(), worldServerForDimension.getGameRules().getBoolean("reducedDebugInfo")));
        netHandlerPlayServer.sendPacket(new S3FPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).writeString(this.getServerInstance().getServerModName())));
        netHandlerPlayServer.sendPacket(new S41PacketServerDifficulty(worldInfo.getDifficulty(), worldInfo.isDifficultyLocked()));
        netHandlerPlayServer.sendPacket(new S05PacketSpawnPosition(spawnPoint));
        netHandlerPlayServer.sendPacket(new S39PacketPlayerAbilities(entityPlayerMP.capabilities));
        netHandlerPlayServer.sendPacket(new S09PacketHeldItemChange(entityPlayerMP.inventory.currentItem));
        entityPlayerMP.getStatFile().func_150877_d();
        entityPlayerMP.getStatFile().sendAchievements(entityPlayerMP);
        this.sendScoreboard((ServerScoreboard)worldServerForDimension.getScoreboard(), entityPlayerMP);
        this.mcServer.refreshStatusNextTick();
        ChatComponentTranslation chatComponentTranslation;
        if (!entityPlayerMP.getName().equalsIgnoreCase(s)) {
            chatComponentTranslation = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[] { entityPlayerMP.getDisplayName(), s });
        }
        else {
            chatComponentTranslation = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { entityPlayerMP.getDisplayName() });
        }
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.sendChatMsg(chatComponentTranslation);
        this.playerLoggedIn(entityPlayerMP);
        netHandlerPlayServer.setPlayerLocation(entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);
        this.updateTimeAndWeatherForPlayer(entityPlayerMP, worldServerForDimension);
        if (this.mcServer.getResourcePackUrl().length() > 0) {
            entityPlayerMP.loadResourcePack(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
        }
        final Iterator<PotionEffect> iterator = entityPlayerMP.getActivePotionEffects().iterator();
        while (iterator.hasNext()) {
            netHandlerPlayServer.sendPacket(new S1DPacketEntityEffect(entityPlayerMP.getEntityId(), iterator.next()));
        }
        entityPlayerMP.addSelfToInternalCraftingInventory();
        if (playerDataFromFile != null && playerDataFromFile.hasKey("Riding", 10)) {
            final Entity entityFromNBT = EntityList.createEntityFromNBT(playerDataFromFile.getCompoundTag("Riding"), worldServerForDimension);
            if (entityFromNBT != null) {
                entityFromNBT.forceSpawn = true;
                worldServerForDimension.spawnEntityInWorld(entityFromNBT);
                entityPlayerMP.mountEntity(entityFromNBT);
                entityFromNBT.forceSpawn = false;
            }
        }
    }
    
    public EntityPlayerMP getPlayerByUUID(final UUID uuid) {
        return this.uuidToPlayerMap.get(uuid);
    }
    
    public boolean canSendCommands(final GameProfile gameProfile) {
        return this.ops.hasEntry(gameProfile) || (this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(gameProfile.getName())) || this.commandsAllowedForAll;
    }
    
    public int getViewDistance() {
        return this.viewDistance;
    }
    
    public List getPlayersMatchingAddress(final String s) {
        final ArrayList arrayList = Lists.newArrayList();
        for (final EntityPlayerMP entityPlayerMP : this.playerEntityList) {
            if (entityPlayerMP.getPlayerIP().equals(s)) {
                arrayList.add(entityPlayerMP);
            }
        }
        return arrayList;
    }
    
    public void addWhitelistedPlayer(final GameProfile gameProfile) {
        this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(gameProfile));
    }
    
    public void setViewDistance(final int n) {
        this.viewDistance = n;
        if (this.mcServer.worldServers != null) {
            final WorldServer[] worldServers = this.mcServer.worldServers;
            while (0 < worldServers.length) {
                final WorldServer worldServer = worldServers[0];
                if (worldServer != null) {
                    worldServer.getPlayerManager().setPlayerViewRadius(n);
                }
                int n2 = 0;
                ++n2;
            }
        }
    }
    
    public StatisticsFile getPlayerStatsFile(final EntityPlayer entityPlayer) {
        final UUID uniqueID = entityPlayer.getUniqueID();
        StatisticsFile statisticsFile = (uniqueID == null) ? null : this.playerStatFiles.get(uniqueID);
        if (statisticsFile == null) {
            final File file = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
            final File file2 = new File(file, uniqueID.toString() + ".json");
            if (!file2.exists()) {
                final File file3 = new File(file, entityPlayer.getName() + ".json");
                if (file3.exists() && file3.isFile()) {
                    file3.renameTo(file2);
                }
            }
            statisticsFile = new StatisticsFile(this.mcServer, file2);
            statisticsFile.readStatFile();
            this.playerStatFiles.put(uniqueID, statisticsFile);
        }
        return statisticsFile;
    }
    
    protected void sendScoreboard(final ServerScoreboard p0, final EntityPlayerMP p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore_3       
        //     4: aload_1        
        //     5: invokevirtual   net/minecraft/scoreboard/ServerScoreboard.getTeams:()Ljava/util/Collection;
        //     8: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    13: astore          4
        //    15: aload           4
        //    17: invokeinterface java/util/Iterator.hasNext:()Z
        //    22: ifeq            57
        //    25: aload           4
        //    27: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    32: checkcast       Lnet/minecraft/scoreboard/ScorePlayerTeam;
        //    35: astore          5
        //    37: aload_2        
        //    38: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //    41: new             Lnet/minecraft/network/play/server/S3EPacketTeams;
        //    44: dup            
        //    45: aload           5
        //    47: iconst_0       
        //    48: invokespecial   net/minecraft/network/play/server/S3EPacketTeams.<init>:(Lnet/minecraft/scoreboard/ScorePlayerTeam;I)V
        //    51: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //    54: goto            15
        //    57: aload_1        
        //    58: iconst_0       
        //    59: invokevirtual   net/minecraft/scoreboard/ServerScoreboard.getObjectiveInDisplaySlot:(I)Lnet/minecraft/scoreboard/ScoreObjective;
        //    62: astore          5
        //    64: aload           5
        //    66: ifnull          136
        //    69: aload_3        
        //    70: aload           5
        //    72: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //    77: ifne            136
        //    80: aload_1        
        //    81: aload           5
        //    83: invokevirtual   net/minecraft/scoreboard/ServerScoreboard.func_96550_d:(Lnet/minecraft/scoreboard/ScoreObjective;)Ljava/util/List;
        //    86: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    91: astore          6
        //    93: aload           6
        //    95: invokeinterface java/util/Iterator.hasNext:()Z
        //   100: ifeq            127
        //   103: aload           6
        //   105: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   110: checkcast       Lnet/minecraft/network/Packet;
        //   113: astore          7
        //   115: aload_2        
        //   116: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   119: aload           7
        //   121: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   124: goto            93
        //   127: aload_3        
        //   128: aload           5
        //   130: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   135: pop            
        //   136: iinc            4, 1
        //   139: goto            57
        //   142: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void playerLoggedIn(final EntityPlayerMP entityPlayerMP) {
        this.playerEntityList.add(entityPlayerMP);
        this.uuidToPlayerMap.put(entityPlayerMP.getUniqueID(), entityPlayerMP);
        this.sendPacketToAllPlayers(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { entityPlayerMP }));
        this.mcServer.worldServerForDimension(entityPlayerMP.dimension).spawnEntityInWorld(entityPlayerMP);
        this.preparePlayer(entityPlayerMP, null);
        while (0 < this.playerEntityList.size()) {
            entityPlayerMP.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { this.playerEntityList.get(0) }));
            int n = 0;
            ++n;
        }
    }
    
    public MinecraftServer getServerInstance() {
        return this.mcServer;
    }
    
    public void sendPacketToAllPlayersInDimension(final Packet packet, final int n) {
        while (0 < this.playerEntityList.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(0);
            if (entityPlayerMP.dimension == n) {
                entityPlayerMP.playerNetServerHandler.sendPacket(packet);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public void removeOp(final GameProfile gameProfile) {
        this.ops.removeEntry(gameProfile);
    }
    
    private void setPlayerGameTypeBasedOnOther(final EntityPlayerMP entityPlayerMP, final EntityPlayerMP entityPlayerMP2, final World world) {
        if (entityPlayerMP2 != null) {
            entityPlayerMP.theItemInWorldManager.setGameType(entityPlayerMP2.theItemInWorldManager.getGameType());
        }
        else if (this.gameType != null) {
            entityPlayerMP.theItemInWorldManager.setGameType(this.gameType);
        }
        entityPlayerMP.theItemInWorldManager.initializeGameType(world.getWorldInfo().getGameType());
    }
    
    public UserListOps getOppedPlayers() {
        return this.ops;
    }
    
    public boolean func_183023_f(final GameProfile gameProfile) {
        return false;
    }
    
    public void saveAllPlayerData() {
        while (0 < this.playerEntityList.size()) {
            this.writePlayerData(this.playerEntityList.get(0));
            int n = 0;
            ++n;
        }
    }
    
    public List func_181057_v() {
        return this.playerEntityList;
    }
    
    public String[] getAvailablePlayerDat() {
        return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
    }
    
    public int getEntityViewDistance() {
        return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
    }
    
    public void sendMessageToTeamOrEvryPlayer(final EntityPlayer entityPlayer, final IChatComponent chatComponent) {
        final Team team = entityPlayer.getTeam();
        if (team == null) {
            this.sendChatMsg(chatComponent);
        }
        else {
            while (0 < this.playerEntityList.size()) {
                final EntityPlayerMP entityPlayerMP = this.playerEntityList.get(0);
                if (entityPlayerMP.getTeam() != team) {
                    entityPlayerMP.addChatMessage(chatComponent);
                }
                int n = 0;
                ++n;
            }
        }
    }
}
