package net.minecraft.entity.player;

import net.minecraft.server.*;
import net.minecraft.scoreboard.*;
import net.minecraft.server.management.*;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.*;
import net.minecraft.stats.*;
import net.minecraft.entity.passive.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.village.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import org.apache.logging.log4j.*;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    public int ping;
    private EnumChatVisibility chatVisibility;
    public final MinecraftServer mcServer;
    private static final Logger logger;
    public final List loadedChunks;
    public double managedPosZ;
    public final ItemInWorldManager theItemInWorldManager;
    private boolean wasHungry;
    private final StatisticsFile statsFile;
    private boolean chatColours;
    private float lastHealth;
    private int currentWindowId;
    public boolean playerConqueredTheEnd;
    public boolean isChangingQuantityOnly;
    private long playerLastActiveTime;
    private int lastExperience;
    private float combinedHealth;
    private final List destroyedItemsNetCache;
    private int lastFoodLevel;
    public NetHandlerPlayServer playerNetServerHandler;
    private Entity spectatingEntity;
    private int respawnInvulnerabilityTicks;
    private String translator;
    public double managedPosX;
    
    @Override
    public void displayGui(final IInteractionObject interactionObject) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, interactionObject.getGuiID(), interactionObject.getDisplayName()));
        this.openContainer = interactionObject.createContainer(this.inventory, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }
    
    public void closeScreen() {
        this.playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(this.openContainer.windowId));
        this.closeContainer();
    }
    
    @Override
    public void addExperienceLevel(final int n) {
        super.addExperienceLevel(n);
        this.lastExperience = -1;
    }
    
    public void closeContainer() {
        this.openContainer.onContainerClosed(this);
        this.openContainer = this.inventoryContainer;
    }
    
    public void markPlayerActive() {
        this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
    }
    
    @Override
    public void clonePlayer(final EntityPlayer entityPlayer, final boolean b) {
        super.clonePlayer(entityPlayer, b);
        this.lastExperience = -1;
        this.lastHealth = -1.0f;
        this.lastFoodLevel = -1;
        this.destroyedItemsNetCache.addAll(((EntityPlayerMP)entityPlayer).destroyedItemsNetCache);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p0, final float p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.isEntityInvulnerable:(Lnet/minecraft/util/DamageSource;)Z
        //     5: ifeq            10
        //     8: iconst_0       
        //     9: ireturn        
        //    10: aload_0        
        //    11: getfield        net/minecraft/entity/player/EntityPlayerMP.mcServer:Lnet/minecraft/server/MinecraftServer;
        //    14: invokevirtual   net/minecraft/server/MinecraftServer.isDedicatedServer:()Z
        //    17: ifeq            43
        //    20: aload_0        
        //    21: invokespecial   net/minecraft/entity/player/EntityPlayerMP.canPlayersAttack:()Z
        //    24: ifeq            43
        //    27: ldc             "fall"
        //    29: aload_1        
        //    30: getfield        net/minecraft/util/DamageSource.damageType:Ljava/lang/String;
        //    33: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    36: ifeq            43
        //    39: iconst_1       
        //    40: goto            44
        //    43: iconst_0       
        //    44: istore_3       
        //    45: iload_3        
        //    46: ifne            65
        //    49: aload_0        
        //    50: getfield        net/minecraft/entity/player/EntityPlayerMP.respawnInvulnerabilityTicks:I
        //    53: ifle            65
        //    56: aload_1        
        //    57: getstatic       net/minecraft/util/DamageSource.outOfWorld:Lnet/minecraft/util/DamageSource;
        //    60: if_acmpeq       65
        //    63: iconst_0       
        //    64: ireturn        
        //    65: aload_1        
        //    66: instanceof      Lnet/minecraft/util/EntityDamageSource;
        //    69: ifeq            137
        //    72: aload_1        
        //    73: invokevirtual   net/minecraft/util/DamageSource.getEntity:()Lnet/minecraft/entity/Entity;
        //    76: astore          4
        //    78: aload           4
        //    80: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //    83: ifeq            97
        //    86: aload_0        
        //    87: aload           4
        //    89: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //    92: ifne            97
        //    95: iconst_0       
        //    96: ireturn        
        //    97: aload           4
        //    99: instanceof      Lnet/minecraft/entity/projectile/EntityArrow;
        //   102: ifeq            137
        //   105: aload           4
        //   107: checkcast       Lnet/minecraft/entity/projectile/EntityArrow;
        //   110: astore          5
        //   112: aload           5
        //   114: getfield        net/minecraft/entity/projectile/EntityArrow.shootingEntity:Lnet/minecraft/entity/Entity;
        //   117: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   120: ifeq            137
        //   123: aload_0        
        //   124: aload           5
        //   126: getfield        net/minecraft/entity/projectile/EntityArrow.shootingEntity:Lnet/minecraft/entity/Entity;
        //   129: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   132: ifne            137
        //   135: iconst_0       
        //   136: ireturn        
        //   137: aload_0        
        //   138: aload_1        
        //   139: fload_2        
        //   140: invokespecial   net/minecraft/entity/player/EntityPlayer.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //   143: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0137 (coming from #0132).
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
    public void onDeath(final DamageSource damageSource) {
        if (this.worldObj.getGameRules().getBoolean("showDeathMessages")) {
            final Team team = this.getTeam();
            if (team != null && team.getDeathMessageVisibility() != Team.EnumVisible.ALWAYS) {
                if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
                    this.mcServer.getConfigurationManager().sendMessageToAllTeamMembers(this, this.getCombatTracker().getDeathMessage());
                }
                else if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
                    this.mcServer.getConfigurationManager().sendMessageToTeamOrEvryPlayer(this, this.getCombatTracker().getDeathMessage());
                }
            }
            else {
                this.mcServer.getConfigurationManager().sendChatMsg(this.getCombatTracker().getDeathMessage());
            }
        }
        if (!this.worldObj.getGameRules().getBoolean("keepInventory")) {
            this.inventory.dropAllItems();
        }
        final Iterator<ScoreObjective> iterator = this.worldObj.getScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.deathCount).iterator();
        while (iterator.hasNext()) {
            this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).func_96648_a();
        }
        final EntityLivingBase func_94060_bK = this.func_94060_bK();
        if (func_94060_bK != null) {
            final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(EntityList.getEntityID(func_94060_bK));
            if (entityEggInfo != null) {
                this.triggerAchievement(entityEggInfo.field_151513_e);
            }
            func_94060_bK.addToPlayerScore(this, this.scoreValue);
        }
        this.triggerAchievement(StatList.deathsStat);
        this.func_175145_a(StatList.timeSinceDeathStat);
        this.getCombatTracker().reset();
    }
    
    @Override
    public EnumStatus trySleep(final BlockPos blockPos) {
        final EnumStatus trySleep = super.trySleep(blockPos);
        if (trySleep == EnumStatus.OK) {
            final S0APacketUseBed s0APacketUseBed = new S0APacketUseBed(this, blockPos);
            this.getServerForPlayer().getEntityTracker().sendToAllTrackingEntity(this, s0APacketUseBed);
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playerNetServerHandler.sendPacket(s0APacketUseBed);
        }
        return trySleep;
    }
    
    @Override
    public void onCriticalHit(final Entity entity) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(entity, 4));
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        if ("seed".equals(s) && !this.mcServer.isDedicatedServer()) {
            return true;
        }
        if ("tell".equals(s) || "help".equals(s) || "me".equals(s) || "trigger".equals(s)) {
            return true;
        }
        if (this.mcServer.getConfigurationManager().canSendCommands(this.getGameProfile())) {
            final UserListOpsEntry userListOpsEntry = (UserListOpsEntry)this.mcServer.getConfigurationManager().getOppedPlayers().getEntry(this.getGameProfile());
            return (userListOpsEntry != null) ? (userListOpsEntry.getPermissionLevel() >= n) : (this.mcServer.getOpPermissionLevel() >= n);
        }
        return false;
    }
    
    public void handleClientSettings(final C15PacketClientSettings c15PacketClientSettings) {
        this.translator = c15PacketClientSettings.getLang();
        this.chatVisibility = c15PacketClientSettings.getChatVisibility();
        this.chatColours = c15PacketClientSettings.isColorsEnabled();
        this.getDataWatcher().updateObject(10, (byte)c15PacketClientSettings.getModelPartFlags());
    }
    
    @Override
    public void sendPlayerAbilities() {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
            this.updatePotionMetadata();
        }
    }
    
    @Override
    public void onItemPickup(final Entity entity, final int n) {
        super.onItemPickup(entity, n);
        this.openContainer.detectAndSendChanges();
    }
    
    public void setPlayerHealthUpdated() {
        this.lastHealth = -1.0E8f;
    }
    
    public void sendContainerToPlayer(final Container container) {
        this.updateCraftingInventory(container, container.getInventory());
    }
    
    public WorldServer getServerForPlayer() {
        return (WorldServer)this.worldObj;
    }
    
    @Override
    protected void onFinishedPotionEffect(final PotionEffect potionEffect) {
        super.onFinishedPotionEffect(potionEffect);
        this.playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(this.getEntityId(), potionEffect));
    }
    
    @Override
    public void attackTargetEntityWithCurrentItem(final Entity spectatingEntity) {
        if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
            this.setSpectatingEntity(spectatingEntity);
        }
        else {
            super.attackTargetEntityWithCurrentItem(spectatingEntity);
        }
    }
    
    public IChatComponent getTabListDisplayName() {
        return null;
    }
    
    @Override
    public void travelToDimension(final int n) {
        if (this.dimension == 1) {
            this.triggerAchievement(AchievementList.theEnd2);
            this.worldObj.removeEntity(this);
            this.playerConqueredTheEnd = true;
            this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0f));
        }
        else {
            if (this.dimension == 0) {
                this.triggerAchievement(AchievementList.theEnd);
                final BlockPos spawnCoordinate = this.mcServer.worldServerForDimension(1).getSpawnCoordinate();
                if (spawnCoordinate != null) {
                    this.playerNetServerHandler.setPlayerLocation(spawnCoordinate.getX(), spawnCoordinate.getY(), spawnCoordinate.getZ(), 0.0f, 0.0f);
                }
            }
            else {
                this.triggerAchievement(AchievementList.portal);
            }
            this.mcServer.getConfigurationManager().transferPlayerToDimension(this, 1);
            this.lastExperience = -1;
            this.lastHealth = -1.0f;
            this.lastFoodLevel = -1;
        }
    }
    
    public void setSpectatingEntity(final Entity entity) {
        final Entity spectatingEntity = this.getSpectatingEntity();
        this.spectatingEntity = ((entity == null) ? this : entity);
        if (spectatingEntity != this.spectatingEntity) {
            this.playerNetServerHandler.sendPacket(new S43PacketCamera(this.spectatingEntity));
            this.setPositionAndUpdate(this.spectatingEntity.posX, this.spectatingEntity.posY, this.spectatingEntity.posZ);
        }
    }
    
    public void loadResourcePack(final String s, final String s2) {
        this.playerNetServerHandler.sendPacket(new S48PacketResourcePackSend(s, s2));
    }
    
    public String getPlayerIP() {
        final String string = this.playerNetServerHandler.netManager.getRemoteAddress().toString();
        final String substring = string.substring(string.indexOf("/") + 1);
        return substring.substring(0, substring.indexOf(":"));
    }
    
    @Override
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "EntityHorse", inventory.getDisplayName(), inventory.getSizeInventory(), entityHorse.getEntityId()));
        this.openContainer = new ContainerHorseInventory(this.inventory, inventory, entityHorse, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    protected void onItemUseFinish() {
        this.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, (byte)9));
        super.onItemUseFinish();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
    }
    
    @Override
    public void setPositionAndUpdate(final double n, final double n2, final double n3) {
        this.playerNetServerHandler.setPlayerLocation(n, n2, n3, this.rotationYaw, this.rotationPitch);
    }
    
    private void getNextWindowId() {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }
    
    @Override
    public void openEditSign(final TileEntitySign tileEntitySign) {
        tileEntitySign.setPlayer(this);
        this.playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(tileEntitySign.getPos()));
    }
    
    @Override
    public boolean isSpectatedByPlayer(final EntityPlayerMP p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpne       20
        //     4: aload_0        
        //     5: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.getSpectatingEntity:()Lnet/minecraft/entity/Entity;
        //     8: aload_0        
        //     9: if_acmpne       16
        //    12: iconst_1       
        //    13: goto            33
        //    16: iconst_0       
        //    17: goto            33
        //    20: aload_0        
        //    21: if_acmpne       28
        //    24: iconst_0       
        //    25: goto            33
        //    28: aload_0        
        //    29: aload_1        
        //    30: invokespecial   net/minecraft/entity/player/EntityPlayer.isSpectatedByPlayer:(Lnet/minecraft/entity/player/EntityPlayerMP;)Z
        //    33: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void updateHeldItem() {
        if (!this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
        }
    }
    
    @Override
    public void sendProgressBarUpdate(final Container container, final int n, final int n2) {
        this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(container.windowId, n, n2));
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponent));
    }
    
    @Override
    protected void onNewPotionEffect(final PotionEffect potionEffect) {
        super.onNewPotionEffect(potionEffect);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
    }
    
    @Override
    public void onEnchantmentCritical(final Entity entity) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(entity, 5));
    }
    
    private void sendTileEntityUpdate(final TileEntity tileEntity) {
        if (tileEntity != null) {
            final Packet descriptionPacket = tileEntity.getDescriptionPacket();
            if (descriptionPacket != null) {
                this.playerNetServerHandler.sendPacket(descriptionPacket);
            }
        }
    }
    
    public StatisticsFile getStatFile() {
        return this.statsFile;
    }
    
    private boolean canPlayersAttack() {
        return this.mcServer.isPVPEnabled();
    }
    
    public void mountEntityAndWakeUp() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(this);
        }
        if (this.sleeping) {
            this.wakeUpPlayer(true, false, false);
        }
    }
    
    public EnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }
    
    public void onUpdateEntity() {
        super.onUpdate();
        while (0 < this.inventory.getSizeInventory()) {
            final ItemStack stackInSlot = this.inventory.getStackInSlot(0);
            if (stackInSlot != null && stackInSlot.getItem().isMap()) {
                final Packet mapDataPacket = ((ItemMapBase)stackInSlot.getItem()).createMapDataPacket(stackInSlot, this.worldObj, this);
                if (mapDataPacket != null) {
                    this.playerNetServerHandler.sendPacket(mapDataPacket);
                }
            }
            int n = 0;
            ++n;
        }
        if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0f != this.wasHungry) {
            this.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
            this.lastHealth = this.getHealth();
            this.lastFoodLevel = this.foodStats.getFoodLevel();
            this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0f);
        }
        if (this.getHealth() + this.getAbsorptionAmount() != this.combinedHealth) {
            this.combinedHealth = this.getHealth() + this.getAbsorptionAmount();
            final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.health).iterator();
            while (iterator.hasNext()) {
                this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).func_96651_a(Arrays.asList(this));
            }
        }
        if (this.experienceTotal != this.lastExperience) {
            this.lastExperience = this.experienceTotal;
            this.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
        }
        if (this.ticksExisted % 20 * 5 == 0 && !this.getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes)) {
            this.updateBiomesExplored();
        }
    }
    
    @Override
    public void sendEnterCombat() {
        super.sendEnterCombat();
        this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
    }
    
    @Override
    public void displayGUIBook(final ItemStack itemStack) {
        if (itemStack.getItem() == Items.written_book) {
            this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
        }
    }
    
    @Override
    protected void onChangedPotionEffect(final PotionEffect potionEffect, final boolean b) {
        super.onChangedPotionEffect(potionEffect, b);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
    }
    
    @Override
    public void onUpdate() {
        this.theItemInWorldManager.updateBlockRemoving();
        --this.respawnInvulnerabilityTicks;
        if (this.hurtResistantTime > 0) {
            --this.hurtResistantTime;
        }
        this.openContainer.detectAndSendChanges();
        if (!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        while (!this.destroyedItemsNetCache.isEmpty()) {
            final int min = Math.min(this.destroyedItemsNetCache.size(), Integer.MAX_VALUE);
            final int[] array = new int[min];
            final Iterator iterator = this.destroyedItemsNetCache.iterator();
            while (iterator.hasNext() && 0 < min) {
                final int[] array2 = array;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array2[n] = iterator.next();
                iterator.remove();
            }
            this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(array));
        }
        if (!this.loadedChunks.isEmpty()) {
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator iterator2 = this.loadedChunks.iterator();
            final ArrayList arrayList2 = Lists.newArrayList();
            while (iterator2.hasNext() && arrayList.size() < 10) {
                final ChunkCoordIntPair chunkCoordIntPair = iterator2.next();
                if (chunkCoordIntPair != null) {
                    if (!this.worldObj.isBlockLoaded(new BlockPos(chunkCoordIntPair.chunkXPos << 4, 0, chunkCoordIntPair.chunkZPos << 4))) {
                        continue;
                    }
                    final Chunk chunkFromChunkCoords = this.worldObj.getChunkFromChunkCoords(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);
                    if (!chunkFromChunkCoords.isPopulated()) {
                        continue;
                    }
                    arrayList.add(chunkFromChunkCoords);
                    arrayList2.addAll(((WorldServer)this.worldObj).getTileEntitiesIn(chunkCoordIntPair.chunkXPos * 16, 0, chunkCoordIntPair.chunkZPos * 16, chunkCoordIntPair.chunkXPos * 16 + 16, 256, chunkCoordIntPair.chunkZPos * 16 + 16));
                    iterator2.remove();
                }
                else {
                    iterator2.remove();
                }
            }
            if (!arrayList.isEmpty()) {
                if (arrayList.size() == 1) {
                    this.playerNetServerHandler.sendPacket(new S21PacketChunkData((Chunk)arrayList.get(0), true, 65535));
                }
                else {
                    this.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(arrayList));
                }
                final Iterator<TileEntity> iterator3 = arrayList2.iterator();
                while (iterator3.hasNext()) {
                    this.sendTileEntityUpdate(iterator3.next());
                }
                final Iterator<Chunk> iterator4 = arrayList.iterator();
                while (iterator4.hasNext()) {
                    this.getServerForPlayer().getEntityTracker().func_85172_a(this, iterator4.next());
                }
            }
        }
        final Entity spectatingEntity = this.getSpectatingEntity();
        if (spectatingEntity != this) {
            if (!spectatingEntity.isEntityAlive()) {
                this.setSpectatingEntity(this);
            }
            else {
                this.setPositionAndRotation(spectatingEntity.posX, spectatingEntity.posY, spectatingEntity.posZ, spectatingEntity.rotationYaw, spectatingEntity.rotationPitch);
                this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
                if (this.isSneaking()) {
                    this.setSpectatingEntity(this);
                }
            }
        }
    }
    
    public EntityPlayerMP(final MinecraftServer mcServer, final WorldServer worldServer, final GameProfile gameProfile, final ItemInWorldManager theItemInWorldManager) {
        super(worldServer, gameProfile);
        this.translator = "en_US";
        this.loadedChunks = Lists.newLinkedList();
        this.destroyedItemsNetCache = Lists.newLinkedList();
        this.combinedHealth = Float.MIN_VALUE;
        this.lastHealth = -1.0E8f;
        this.lastFoodLevel = -99999999;
        this.wasHungry = true;
        this.lastExperience = -99999999;
        this.respawnInvulnerabilityTicks = 60;
        this.chatColours = true;
        this.playerLastActiveTime = System.currentTimeMillis();
        this.spectatingEntity = null;
        theItemInWorldManager.thisPlayerMP = this;
        this.theItemInWorldManager = theItemInWorldManager;
        BlockPos blockPos = worldServer.getSpawnPoint();
        if (!worldServer.provider.getHasNoSky() && worldServer.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
            Math.max(5, mcServer.getSpawnProtectionSize() - 6);
            final int floor_double = MathHelper.floor_double(worldServer.getWorldBorder().getClosestDistance(blockPos.getX(), blockPos.getZ()));
            if (floor_double < 1) {}
            if (floor_double <= 1) {}
            blockPos = worldServer.getTopSolidOrLiquidBlock(blockPos.add(this.rand.nextInt(2) - 1, 0, this.rand.nextInt(2) - 1));
        }
        this.mcServer = mcServer;
        this.statsFile = mcServer.getConfigurationManager().getPlayerStatsFile(this);
        this.moveToBlockPosAndAngles(blockPos, this.stepHeight = 0.0f, 0.0f);
        while (!worldServer.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && this.posY < 255.0) {
            this.setPosition(this.posX, this.posY + 1.0, this.posZ);
        }
    }
    
    @Override
    public void displayVillagerTradeGui(final IMerchant merchant) {
        this.getNextWindowId();
        this.openContainer = new ContainerMerchant(this.inventory, merchant, this.worldObj);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "minecraft:villager", merchant.getDisplayName(), ((ContainerMerchant)this.openContainer).getMerchantInventory().getSizeInventory()));
        final MerchantRecipeList recipes = merchant.getRecipes(this);
        if (recipes != null) {
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.currentWindowId);
            recipes.writeToBuf(packetBuffer);
            this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", packetBuffer));
        }
    }
    
    public long getLastActiveTime() {
        return this.playerLastActiveTime;
    }
    
    public void addSelfToInternalCraftingInventory() {
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    public void updateCraftingInventory(final Container container, final List list) {
        this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(container.windowId, list));
        this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
    }
    
    @Override
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
    }
    
    @Override
    public void addChatComponentMessage(final IChatComponent chatComponent) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponent));
    }
    
    @Override
    public void sendSlotContents(final Container container, final int n, final ItemStack itemStack) {
        if (!(container.getSlot(n) instanceof SlotCrafting) && !this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(container.windowId, n, itemStack));
        }
    }
    
    public void setEntityActionState(final float moveStrafing, final float moveForward, final boolean isJumping, final boolean sneaking) {
        if (this.ridingEntity != null) {
            if (moveStrafing >= -1.0f && moveStrafing <= 1.0f) {
                this.moveStrafing = moveStrafing;
            }
            if (moveForward >= -1.0f && moveForward <= 1.0f) {
                this.moveForward = moveForward;
            }
            this.isJumping = isJumping;
            this.setSneaking(sneaking);
        }
    }
    
    @Override
    public void removeExperienceLevel(final int n) {
        super.removeExperienceLevel(n);
        this.lastExperience = -1;
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType gameType) {
        this.theItemInWorldManager.setGameType(gameType);
        this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, (float)gameType.getID()));
        if (gameType == WorldSettings.GameType.SPECTATOR) {
            this.mountEntity(null);
        }
        else {
            this.setSpectatingEntity(this);
        }
        this.sendPlayerAbilities();
        this.markPotionsDirty();
    }
    
    @Override
    public void sendEndCombat() {
        super.sendEndCombat();
        this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
    }
    
    @Override
    public void func_175173_a(final Container container, final IInventory inventory) {
        while (0 < inventory.getFieldCount()) {
            this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(container.windowId, 0, inventory.getField(0)));
            int n = 0;
            ++n;
        }
    }
    
    public void removeEntity(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(new int[] { entity.getEntityId() }));
        }
        else {
            this.destroyedItemsNetCache.add(entity.getEntityId());
        }
    }
    
    @Override
    public void addStat(final StatBase statBase, final int n) {
        if (statBase != null) {
            this.statsFile.increaseStat(this, statBase, n);
            final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().getObjectivesFromCriteria(statBase.func_150952_k()).iterator();
            while (iterator.hasNext()) {
                this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).increseScore(n);
            }
            if (this.statsFile.func_150879_e()) {
                this.statsFile.func_150876_a(this);
            }
        }
    }
    
    @Override
    public void displayGUIChest(final IInventory p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/player/EntityPlayerMP.openContainer:Lnet/minecraft/inventory/Container;
        //     4: aload_0        
        //     5: getfield        net/minecraft/entity/player/EntityPlayerMP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //     8: if_acmpeq       15
        //    11: aload_0        
        //    12: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.closeScreen:()V
        //    15: aload_1        
        //    16: instanceof      Lnet/minecraft/world/ILockableContainer;
        //    19: ifeq            123
        //    22: aload_1        
        //    23: checkcast       Lnet/minecraft/world/ILockableContainer;
        //    26: astore_2       
        //    27: aload_2        
        //    28: invokeinterface net/minecraft/world/ILockableContainer.isLocked:()Z
        //    33: ifeq            123
        //    36: aload_0        
        //    37: aload_2        
        //    38: invokeinterface net/minecraft/world/ILockableContainer.getLockCode:()Lnet/minecraft/world/LockCode;
        //    43: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.canOpen:(Lnet/minecraft/world/LockCode;)Z
        //    46: ifne            123
        //    49: aload_0        
        //    50: if_acmpne       123
        //    53: aload_0        
        //    54: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //    57: new             Lnet/minecraft/network/play/server/S02PacketChat;
        //    60: dup            
        //    61: new             Lnet/minecraft/util/ChatComponentTranslation;
        //    64: dup            
        //    65: ldc_w           "container.isLocked"
        //    68: iconst_1       
        //    69: anewarray       Ljava/lang/Object;
        //    72: dup            
        //    73: iconst_0       
        //    74: aload_1        
        //    75: invokeinterface net/minecraft/inventory/IInventory.getDisplayName:()Lnet/minecraft/util/IChatComponent;
        //    80: aastore        
        //    81: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
        //    84: iconst_2       
        //    85: invokespecial   net/minecraft/network/play/server/S02PacketChat.<init>:(Lnet/minecraft/util/IChatComponent;B)V
        //    88: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //    91: aload_0        
        //    92: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //    95: new             Lnet/minecraft/network/play/server/S29PacketSoundEffect;
        //    98: dup            
        //    99: ldc_w           "random.door_close"
        //   102: aload_0        
        //   103: getfield        net/minecraft/entity/player/EntityPlayerMP.posX:D
        //   106: aload_0        
        //   107: getfield        net/minecraft/entity/player/EntityPlayerMP.posY:D
        //   110: aload_0        
        //   111: getfield        net/minecraft/entity/player/EntityPlayerMP.posZ:D
        //   114: fconst_1       
        //   115: fconst_1       
        //   116: invokespecial   net/minecraft/network/play/server/S29PacketSoundEffect.<init>:(Ljava/lang/String;DDDFF)V
        //   119: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   122: return         
        //   123: aload_0        
        //   124: invokespecial   net/minecraft/entity/player/EntityPlayerMP.getNextWindowId:()V
        //   127: aload_1        
        //   128: instanceof      Lnet/minecraft/world/IInteractionObject;
        //   131: ifeq            194
        //   134: aload_0        
        //   135: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   138: new             Lnet/minecraft/network/play/server/S2DPacketOpenWindow;
        //   141: dup            
        //   142: aload_0        
        //   143: getfield        net/minecraft/entity/player/EntityPlayerMP.currentWindowId:I
        //   146: aload_1        
        //   147: checkcast       Lnet/minecraft/world/IInteractionObject;
        //   150: invokeinterface net/minecraft/world/IInteractionObject.getGuiID:()Ljava/lang/String;
        //   155: aload_1        
        //   156: invokeinterface net/minecraft/inventory/IInventory.getDisplayName:()Lnet/minecraft/util/IChatComponent;
        //   161: aload_1        
        //   162: invokeinterface net/minecraft/inventory/IInventory.getSizeInventory:()I
        //   167: invokespecial   net/minecraft/network/play/server/S2DPacketOpenWindow.<init>:(ILjava/lang/String;Lnet/minecraft/util/IChatComponent;I)V
        //   170: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   173: aload_0        
        //   174: aload_1        
        //   175: checkcast       Lnet/minecraft/world/IInteractionObject;
        //   178: aload_0        
        //   179: getfield        net/minecraft/entity/player/EntityPlayerMP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   182: aload_0        
        //   183: invokeinterface net/minecraft/world/IInteractionObject.createContainer:(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/inventory/Container;
        //   188: putfield        net/minecraft/entity/player/EntityPlayerMP.openContainer:Lnet/minecraft/inventory/Container;
        //   191: goto            244
        //   194: aload_0        
        //   195: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   198: new             Lnet/minecraft/network/play/server/S2DPacketOpenWindow;
        //   201: dup            
        //   202: aload_0        
        //   203: getfield        net/minecraft/entity/player/EntityPlayerMP.currentWindowId:I
        //   206: ldc_w           "minecraft:container"
        //   209: aload_1        
        //   210: invokeinterface net/minecraft/inventory/IInventory.getDisplayName:()Lnet/minecraft/util/IChatComponent;
        //   215: aload_1        
        //   216: invokeinterface net/minecraft/inventory/IInventory.getSizeInventory:()I
        //   221: invokespecial   net/minecraft/network/play/server/S2DPacketOpenWindow.<init>:(ILjava/lang/String;Lnet/minecraft/util/IChatComponent;I)V
        //   224: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   227: aload_0        
        //   228: new             Lnet/minecraft/inventory/ContainerChest;
        //   231: dup            
        //   232: aload_0        
        //   233: getfield        net/minecraft/entity/player/EntityPlayerMP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   236: aload_1        
        //   237: aload_0        
        //   238: invokespecial   net/minecraft/inventory/ContainerChest.<init>:(Lnet/minecraft/inventory/IInventory;Lnet/minecraft/inventory/IInventory;Lnet/minecraft/entity/player/EntityPlayer;)V
        //   241: putfield        net/minecraft/entity/player/EntityPlayerMP.openContainer:Lnet/minecraft/inventory/Container;
        //   244: aload_0        
        //   245: getfield        net/minecraft/entity/player/EntityPlayerMP.openContainer:Lnet/minecraft/inventory/Container;
        //   248: aload_0        
        //   249: getfield        net/minecraft/entity/player/EntityPlayerMP.currentWindowId:I
        //   252: putfield        net/minecraft/inventory/Container.windowId:I
        //   255: aload_0        
        //   256: getfield        net/minecraft/entity/player/EntityPlayerMP.openContainer:Lnet/minecraft/inventory/Container;
        //   259: aload_0        
        //   260: invokevirtual   net/minecraft/inventory/Container.onCraftGuiOpened:(Lnet/minecraft/inventory/ICrafting;)V
        //   263: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("playerGameType", 99)) {
            if (MinecraftServer.getServer().getForceGamemode()) {
                this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
            }
            else {
                this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(nbtTagCompound.getInteger("playerGameType")));
            }
        }
    }
    
    protected void updateBiomesExplored() {
        final String biomeName = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))).biomeName;
        JsonSerializableSet set = (JsonSerializableSet)this.getStatFile().func_150870_b(AchievementList.exploreAllBiomes);
        if (set == null) {
            set = (JsonSerializableSet)this.getStatFile().func_150872_a(AchievementList.exploreAllBiomes, new JsonSerializableSet());
        }
        set.add((Object)biomeName);
        if (this.getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes) && set.size() >= BiomeGenBase.explorationBiomesList.size()) {
            final HashSet hashSet = Sets.newHashSet((Iterable)BiomeGenBase.explorationBiomesList);
            for (final String s : set) {
                final Iterator<BiomeGenBase> iterator2 = hashSet.iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().biomeName.equals(s)) {
                        iterator2.remove();
                    }
                }
                if (hashSet.isEmpty()) {
                    break;
                }
            }
            if (hashSet.isEmpty()) {
                this.triggerAchievement(AchievementList.exploreAllBiomes);
            }
        }
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        final Entity ridingEntity = this.ridingEntity;
        super.mountEntity(entity);
        if (entity != ridingEntity) {
            this.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, this.ridingEntity));
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    @Override
    protected void updatePotionMetadata() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: if_acmpne       16
        //     4: aload_0        
        //     5: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.resetPotionEffectMetadata:()V
        //     8: aload_0        
        //     9: iconst_1       
        //    10: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.setInvisible:(Z)V
        //    13: goto            20
        //    16: aload_0        
        //    17: invokespecial   net/minecraft/entity/player/EntityPlayer.updatePotionMetadata:()V
        //    20: aload_0        
        //    21: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.getServerForPlayer:()Lnet/minecraft/world/WorldServer;
        //    24: invokevirtual   net/minecraft/world/WorldServer.getEntityTracker:()Lnet/minecraft/entity/EntityTracker;
        //    27: aload_0        
        //    28: invokevirtual   net/minecraft/entity/EntityTracker.func_180245_a:(Lnet/minecraft/entity/player/EntityPlayerMP;)V
        //    31: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void wakeUpPlayer(final boolean b, final boolean b2, final boolean b3) {
        if (this.isPlayerSleeping()) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
        }
        super.wakeUpPlayer(b, b2, b3);
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    public Entity getSpectatingEntity() {
        return (this.spectatingEntity == null) ? this : this.spectatingEntity;
    }
    
    @Override
    public void func_175145_a(final StatBase statBase) {
        if (statBase != null) {
            this.statsFile.unlockAchievement(this, statBase, 0);
            final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().getObjectivesFromCriteria(statBase.func_150952_k()).iterator();
            while (iterator.hasNext()) {
                this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).setScorePoints(0);
            }
            if (this.statsFile.func_150879_e()) {
                this.statsFile.func_150876_a(this);
            }
        }
    }
    
    @Override
    public void setItemInUse(final ItemStack itemStack, final int n) {
        super.setItemInUse(itemStack, n);
        if (itemStack != null && itemStack.getItem() != null && itemStack.getItem().getItemUseAction(itemStack) == EnumAction.EAT) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
        }
    }
    
    public void handleFalling(final double n, final boolean b) {
        BlockPos down = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ));
        Block block = this.worldObj.getBlockState(down).getBlock();
        if (block.getMaterial() == Material.air) {
            final Block block2 = this.worldObj.getBlockState(down.down()).getBlock();
            if (block2 instanceof BlockFence || block2 instanceof BlockWall || block2 instanceof BlockFenceGate) {
                down = down.down();
                block = this.worldObj.getBlockState(down).getBlock();
            }
        }
        super.updateFallState(n, b, block, down);
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
