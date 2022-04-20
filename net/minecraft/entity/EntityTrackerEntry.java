package net.minecraft.entity;

import net.minecraft.network.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class EntityTrackerEntry
{
    public double lastTrackedEntityMotionY;
    private double lastTrackedEntityPosX;
    public int encodedPosZ;
    private double lastTrackedEntityPosZ;
    private boolean ridingEntity;
    public int encodedRotationYaw;
    public double motionZ;
    private boolean onGround;
    public double lastTrackedEntityMotionX;
    private boolean firstUpdateDone;
    public int lastHeadMotion;
    public Entity trackedEntity;
    public int updateFrequency;
    public int updateCounter;
    private Entity field_85178_v;
    public boolean playerEntitiesUpdated;
    public int trackingDistanceThreshold;
    private static final Logger logger;
    private double lastTrackedEntityPosY;
    public int encodedRotationPitch;
    public Set trackingPlayers;
    private boolean sendVelocityUpdates;
    public int encodedPosX;
    private int ticksSinceLastForcedTeleport;
    public int encodedPosY;
    
    public void removeTrackedPlayerSymmetric(final EntityPlayerMP entityPlayerMP) {
        if (this.trackingPlayers.contains(entityPlayerMP)) {
            this.trackingPlayers.remove(entityPlayerMP);
            entityPlayerMP.removeEntity(this.trackedEntity);
        }
    }
    
    @Override
    public int hashCode() {
        return this.trackedEntity.getEntityId();
    }
    
    private void sendMetadataToAllAssociatedPlayers() {
        final DataWatcher dataWatcher = this.trackedEntity.getDataWatcher();
        if (dataWatcher.hasObjectChanged()) {
            this.func_151261_b(new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), dataWatcher, false));
        }
        if (this.trackedEntity instanceof EntityLivingBase) {
            final Set attributeInstanceSet = ((ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap()).getAttributeInstanceSet();
            if (!attributeInstanceSet.isEmpty()) {
                this.func_151261_b(new S20PacketEntityProperties(this.trackedEntity.getEntityId(), attributeInstanceSet));
            }
            attributeInstanceSet.clear();
        }
    }
    
    private boolean isPlayerWatchingThisChunk(final EntityPlayerMP entityPlayerMP) {
        return entityPlayerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(entityPlayerMP, this.trackedEntity.chunkCoordX, this.trackedEntity.chunkCoordZ);
    }
    
    public void updatePlayerEntities(final List list) {
        while (0 < list.size()) {
            this.updatePlayerEntity(list.get(0));
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof EntityTrackerEntry && ((EntityTrackerEntry)o).trackedEntity.getEntityId() == this.trackedEntity.getEntityId();
    }
    
    public void updatePlayerEntity(final EntityPlayerMP p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //     5: if_acmpeq       612
        //     8: aload_0        
        //     9: aload_1        
        //    10: iflt            580
        //    13: aload_0        
        //    14: getfield        net/minecraft/entity/EntityTrackerEntry.trackingPlayers:Ljava/util/Set;
        //    17: aload_1        
        //    18: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //    23: ifne            612
        //    26: aload_0        
        //    27: aload_1        
        //    28: invokespecial   net/minecraft/entity/EntityTrackerEntry.isPlayerWatchingThisChunk:(Lnet/minecraft/entity/player/EntityPlayerMP;)Z
        //    31: ifne            44
        //    34: aload_0        
        //    35: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    38: getfield        net/minecraft/entity/Entity.forceSpawn:Z
        //    41: ifeq            612
        //    44: aload_0        
        //    45: getfield        net/minecraft/entity/EntityTrackerEntry.trackingPlayers:Ljava/util/Set;
        //    48: aload_1        
        //    49: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //    54: pop            
        //    55: aload_0        
        //    56: invokespecial   net/minecraft/entity/EntityTrackerEntry.func_151260_c:()Lnet/minecraft/network/Packet;
        //    59: astore_2       
        //    60: aload_1        
        //    61: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //    64: aload_2        
        //    65: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //    68: aload_0        
        //    69: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    72: invokevirtual   net/minecraft/entity/Entity.getDataWatcher:()Lnet/minecraft/entity/DataWatcher;
        //    75: invokevirtual   net/minecraft/entity/DataWatcher.getIsBlank:()Z
        //    78: ifne            110
        //    81: aload_1        
        //    82: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //    85: new             Lnet/minecraft/network/play/server/S1CPacketEntityMetadata;
        //    88: dup            
        //    89: aload_0        
        //    90: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    93: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //    96: aload_0        
        //    97: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   100: invokevirtual   net/minecraft/entity/Entity.getDataWatcher:()Lnet/minecraft/entity/DataWatcher;
        //   103: iconst_1       
        //   104: invokespecial   net/minecraft/network/play/server/S1CPacketEntityMetadata.<init>:(ILnet/minecraft/entity/DataWatcher;Z)V
        //   107: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   110: aload_0        
        //   111: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   114: invokevirtual   net/minecraft/entity/Entity.getNBTTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   117: astore_3       
        //   118: aload_3        
        //   119: ifnull          144
        //   122: aload_1        
        //   123: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   126: new             Lnet/minecraft/network/play/server/S49PacketUpdateEntityNBT;
        //   129: dup            
        //   130: aload_0        
        //   131: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   134: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   137: aload_3        
        //   138: invokespecial   net/minecraft/network/play/server/S49PacketUpdateEntityNBT.<init>:(ILnet/minecraft/nbt/NBTTagCompound;)V
        //   141: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   144: aload_0        
        //   145: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   148: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   151: ifeq            209
        //   154: aload_0        
        //   155: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   158: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   161: invokevirtual   net/minecraft/entity/EntityLivingBase.getAttributeMap:()Lnet/minecraft/entity/ai/attributes/BaseAttributeMap;
        //   164: checkcast       Lnet/minecraft/entity/ai/attributes/ServersideAttributeMap;
        //   167: astore          4
        //   169: aload           4
        //   171: invokevirtual   net/minecraft/entity/ai/attributes/ServersideAttributeMap.getWatchedAttributes:()Ljava/util/Collection;
        //   174: astore          5
        //   176: aload           5
        //   178: invokeinterface java/util/Collection.isEmpty:()Z
        //   183: ifne            209
        //   186: aload_1        
        //   187: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   190: new             Lnet/minecraft/network/play/server/S20PacketEntityProperties;
        //   193: dup            
        //   194: aload_0        
        //   195: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   198: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   201: aload           5
        //   203: invokespecial   net/minecraft/network/play/server/S20PacketEntityProperties.<init>:(ILjava/util/Collection;)V
        //   206: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   209: aload_0        
        //   210: aload_0        
        //   211: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   214: getfield        net/minecraft/entity/Entity.motionX:D
        //   217: putfield        net/minecraft/entity/EntityTrackerEntry.lastTrackedEntityMotionX:D
        //   220: aload_0        
        //   221: aload_0        
        //   222: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   225: getfield        net/minecraft/entity/Entity.motionY:D
        //   228: putfield        net/minecraft/entity/EntityTrackerEntry.lastTrackedEntityMotionY:D
        //   231: aload_0        
        //   232: aload_0        
        //   233: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   236: getfield        net/minecraft/entity/Entity.motionZ:D
        //   239: putfield        net/minecraft/entity/EntityTrackerEntry.motionZ:D
        //   242: aload_0        
        //   243: getfield        net/minecraft/entity/EntityTrackerEntry.sendVelocityUpdates:Z
        //   246: ifeq            298
        //   249: aload_2        
        //   250: instanceof      Lnet/minecraft/network/play/server/S0FPacketSpawnMob;
        //   253: ifne            298
        //   256: aload_1        
        //   257: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   260: new             Lnet/minecraft/network/play/server/S12PacketEntityVelocity;
        //   263: dup            
        //   264: aload_0        
        //   265: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   268: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   271: aload_0        
        //   272: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   275: getfield        net/minecraft/entity/Entity.motionX:D
        //   278: aload_0        
        //   279: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   282: getfield        net/minecraft/entity/Entity.motionY:D
        //   285: aload_0        
        //   286: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   289: getfield        net/minecraft/entity/Entity.motionZ:D
        //   292: invokespecial   net/minecraft/network/play/server/S12PacketEntityVelocity.<init>:(IDDD)V
        //   295: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   298: aload_0        
        //   299: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   302: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   305: ifnull          334
        //   308: aload_1        
        //   309: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   312: new             Lnet/minecraft/network/play/server/S1BPacketEntityAttach;
        //   315: dup            
        //   316: iconst_0       
        //   317: aload_0        
        //   318: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   321: aload_0        
        //   322: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   325: getfield        net/minecraft/entity/Entity.ridingEntity:Lnet/minecraft/entity/Entity;
        //   328: invokespecial   net/minecraft/network/play/server/S1BPacketEntityAttach.<init>:(ILnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)V
        //   331: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   334: aload_0        
        //   335: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   338: instanceof      Lnet/minecraft/entity/EntityLiving;
        //   341: ifeq            386
        //   344: aload_0        
        //   345: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   348: checkcast       Lnet/minecraft/entity/EntityLiving;
        //   351: invokevirtual   net/minecraft/entity/EntityLiving.getLeashedToEntity:()Lnet/minecraft/entity/Entity;
        //   354: ifnull          386
        //   357: aload_1        
        //   358: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   361: new             Lnet/minecraft/network/play/server/S1BPacketEntityAttach;
        //   364: dup            
        //   365: iconst_1       
        //   366: aload_0        
        //   367: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   370: aload_0        
        //   371: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   374: checkcast       Lnet/minecraft/entity/EntityLiving;
        //   377: invokevirtual   net/minecraft/entity/EntityLiving.getLeashedToEntity:()Lnet/minecraft/entity/Entity;
        //   380: invokespecial   net/minecraft/network/play/server/S1BPacketEntityAttach.<init>:(ILnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)V
        //   383: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   386: aload_0        
        //   387: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   390: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   393: ifeq            444
        //   396: aload_0        
        //   397: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   400: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   403: iconst_0       
        //   404: invokevirtual   net/minecraft/entity/EntityLivingBase.getEquipmentInSlot:(I)Lnet/minecraft/item/ItemStack;
        //   407: astore          5
        //   409: aload           5
        //   411: ifnull          438
        //   414: aload_1        
        //   415: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   418: new             Lnet/minecraft/network/play/server/S04PacketEntityEquipment;
        //   421: dup            
        //   422: aload_0        
        //   423: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   426: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   429: iconst_0       
        //   430: aload           5
        //   432: invokespecial   net/minecraft/network/play/server/S04PacketEntityEquipment.<init>:(IILnet/minecraft/item/ItemStack;)V
        //   435: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   438: iinc            4, 1
        //   441: goto            396
        //   444: aload_0        
        //   445: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   448: instanceof      Lnet/minecraft/entity/player/EntityPlayer;
        //   451: ifeq            498
        //   454: aload_0        
        //   455: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   458: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   461: astore          4
        //   463: aload           4
        //   465: invokevirtual   net/minecraft/entity/player/EntityPlayer.isPlayerSleeping:()Z
        //   468: ifeq            498
        //   471: aload_1        
        //   472: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   475: new             Lnet/minecraft/network/play/server/S0APacketUseBed;
        //   478: dup            
        //   479: aload           4
        //   481: new             Lnet/minecraft/util/BlockPos;
        //   484: dup            
        //   485: aload_0        
        //   486: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   489: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //   492: invokespecial   net/minecraft/network/play/server/S0APacketUseBed.<init>:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/BlockPos;)V
        //   495: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   498: aload_0        
        //   499: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   502: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //   505: ifeq            577
        //   508: aload_0        
        //   509: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   512: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //   515: astore          4
        //   517: aload           4
        //   519: invokevirtual   net/minecraft/entity/EntityLivingBase.getActivePotionEffects:()Ljava/util/Collection;
        //   522: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   527: astore          5
        //   529: aload           5
        //   531: invokeinterface java/util/Iterator.hasNext:()Z
        //   536: ifeq            577
        //   539: aload           5
        //   541: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   546: checkcast       Lnet/minecraft/potion/PotionEffect;
        //   549: astore          6
        //   551: aload_1        
        //   552: getfield        net/minecraft/entity/player/EntityPlayerMP.playerNetServerHandler:Lnet/minecraft/network/NetHandlerPlayServer;
        //   555: new             Lnet/minecraft/network/play/server/S1DPacketEntityEffect;
        //   558: dup            
        //   559: aload_0        
        //   560: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   563: invokevirtual   net/minecraft/entity/Entity.getEntityId:()I
        //   566: aload           6
        //   568: invokespecial   net/minecraft/network/play/server/S1DPacketEntityEffect.<init>:(ILnet/minecraft/potion/PotionEffect;)V
        //   571: invokevirtual   net/minecraft/network/NetHandlerPlayServer.sendPacket:(Lnet/minecraft/network/Packet;)V
        //   574: goto            529
        //   577: goto            612
        //   580: aload_0        
        //   581: getfield        net/minecraft/entity/EntityTrackerEntry.trackingPlayers:Ljava/util/Set;
        //   584: aload_1        
        //   585: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //   590: ifeq            612
        //   593: aload_0        
        //   594: getfield        net/minecraft/entity/EntityTrackerEntry.trackingPlayers:Ljava/util/Set;
        //   597: aload_1        
        //   598: invokeinterface java/util/Set.remove:(Ljava/lang/Object;)Z
        //   603: pop            
        //   604: aload_1        
        //   605: aload_0        
        //   606: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   609: invokevirtual   net/minecraft/entity/player/EntityPlayerMP.removeEntity:(Lnet/minecraft/entity/Entity;)V
        //   612: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0612 (coming from #0590).
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
    
    public void sendPacketToTrackedPlayers(final Packet packet) {
        final Iterator<EntityPlayerMP> iterator = this.trackingPlayers.iterator();
        while (iterator.hasNext()) {
            iterator.next().playerNetServerHandler.sendPacket(packet);
        }
    }
    
    public EntityTrackerEntry(final Entity trackedEntity, final int trackingDistanceThreshold, final int updateFrequency, final boolean sendVelocityUpdates) {
        this.trackingPlayers = Sets.newHashSet();
        this.trackedEntity = trackedEntity;
        this.trackingDistanceThreshold = trackingDistanceThreshold;
        this.updateFrequency = updateFrequency;
        this.sendVelocityUpdates = sendVelocityUpdates;
        this.encodedPosX = MathHelper.floor_double(trackedEntity.posX * 32.0);
        this.encodedPosY = MathHelper.floor_double(trackedEntity.posY * 32.0);
        this.encodedPosZ = MathHelper.floor_double(trackedEntity.posZ * 32.0);
        this.encodedRotationYaw = MathHelper.floor_float(trackedEntity.rotationYaw * 256.0f / 360.0f);
        this.encodedRotationPitch = MathHelper.floor_float(trackedEntity.rotationPitch * 256.0f / 360.0f);
        this.lastHeadMotion = MathHelper.floor_float(trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
        this.onGround = trackedEntity.onGround;
    }
    
    public void func_151261_b(final Packet packet) {
        this.sendPacketToTrackedPlayers(packet);
        if (this.trackedEntity instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.trackedEntity).playerNetServerHandler.sendPacket(packet);
        }
    }
    
    public void updatePlayerList(final List list) {
        this.playerEntitiesUpdated = false;
        if (!this.firstUpdateDone || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0) {
            this.lastTrackedEntityPosX = this.trackedEntity.posX;
            this.lastTrackedEntityPosY = this.trackedEntity.posY;
            this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
            this.firstUpdateDone = true;
            this.playerEntitiesUpdated = true;
            this.updatePlayerEntities(list);
        }
        if (this.field_85178_v != this.trackedEntity.ridingEntity || (this.trackedEntity.ridingEntity != null && this.updateCounter % 60 == 0)) {
            this.field_85178_v = this.trackedEntity.ridingEntity;
            this.sendPacketToTrackedPlayers(new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
        }
        if (this.trackedEntity instanceof EntityItemFrame && this.updateCounter % 10 == 0) {
            final ItemStack displayedItem = ((EntityItemFrame)this.trackedEntity).getDisplayedItem();
            if (displayedItem != null && displayedItem.getItem() instanceof ItemMap) {
                final MapData mapData = Items.filled_map.getMapData(displayedItem, this.trackedEntity.worldObj);
                for (final EntityPlayerMP entityPlayerMP : list) {
                    mapData.updateVisiblePlayers(entityPlayerMP, displayedItem);
                    final Packet mapDataPacket = Items.filled_map.createMapDataPacket(displayedItem, this.trackedEntity.worldObj, entityPlayerMP);
                    if (mapDataPacket != null) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(mapDataPacket);
                    }
                }
            }
            this.sendMetadataToAllAssociatedPlayers();
        }
        if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataWatcher().hasObjectChanged()) {
            if (this.trackedEntity.ridingEntity == null) {
                ++this.ticksSinceLastForcedTeleport;
                final int floor_double = MathHelper.floor_double(this.trackedEntity.posX * 32.0);
                final int floor_double2 = MathHelper.floor_double(this.trackedEntity.posY * 32.0);
                final int floor_double3 = MathHelper.floor_double(this.trackedEntity.posZ * 32.0);
                final int floor_float = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0f / 360.0f);
                final int floor_float2 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0f / 360.0f);
                final int n = floor_double - this.encodedPosX;
                final int n2 = floor_double2 - this.encodedPosY;
                final int n3 = floor_double3 - this.encodedPosZ;
                Packet packet = null;
                final boolean b = Math.abs(n) >= 4 || Math.abs(n2) >= 4 || Math.abs(n3) >= 4 || this.updateCounter % 60 == 0;
                final boolean b2 = Math.abs(floor_float - this.encodedRotationYaw) >= 4 || Math.abs(floor_float2 - this.encodedRotationPitch) >= 4;
                if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow) {
                    if (n >= -128 && n < 128 && n2 >= -128 && n2 < 128 && n3 >= -128 && n3 < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity && this.onGround == this.trackedEntity.onGround) {
                        if ((!b || !b2) && !(this.trackedEntity instanceof EntityArrow)) {
                            if (b) {
                                packet = new S14PacketEntity.S15PacketEntityRelMove(this.trackedEntity.getEntityId(), (byte)n, (byte)n2, (byte)n3, this.trackedEntity.onGround);
                            }
                            else if (b2) {
                                packet = new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                            }
                        }
                        else {
                            packet = new S14PacketEntity.S17PacketEntityLookMove(this.trackedEntity.getEntityId(), (byte)n, (byte)n2, (byte)n3, (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                        }
                    }
                    else {
                        this.onGround = this.trackedEntity.onGround;
                        this.ticksSinceLastForcedTeleport = 0;
                        packet = new S18PacketEntityTeleport(this.trackedEntity.getEntityId(), floor_double, floor_double2, floor_double3, (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                    }
                }
                if (this.sendVelocityUpdates) {
                    final double n4 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
                    final double n5 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
                    final double n6 = this.trackedEntity.motionZ - this.motionZ;
                    final double n7 = 0.02;
                    final double n8 = n4 * n4 + n5 * n5 + n6 * n6;
                    if (n8 > n7 * n7 || (n8 > 0.0 && this.trackedEntity.motionX == 0.0 && this.trackedEntity.motionY == 0.0 && this.trackedEntity.motionZ == 0.0)) {
                        this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
                        this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
                        this.motionZ = this.trackedEntity.motionZ;
                        this.sendPacketToTrackedPlayers(new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
                    }
                }
                if (packet != null) {
                    this.sendPacketToTrackedPlayers(packet);
                }
                this.sendMetadataToAllAssociatedPlayers();
                if (b) {
                    this.encodedPosX = floor_double;
                    this.encodedPosY = floor_double2;
                    this.encodedPosZ = floor_double3;
                }
                if (b2) {
                    this.encodedRotationYaw = floor_float;
                    this.encodedRotationPitch = floor_float2;
                }
                this.ridingEntity = false;
            }
            else {
                final int floor_float3 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0f / 360.0f);
                final int floor_float4 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0f / 360.0f);
                if (Math.abs(floor_float3 - this.encodedRotationYaw) >= 4 || Math.abs(floor_float4 - this.encodedRotationPitch) >= 4) {
                    this.sendPacketToTrackedPlayers(new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)floor_float3, (byte)floor_float4, this.trackedEntity.onGround));
                    this.encodedRotationYaw = floor_float3;
                    this.encodedRotationPitch = floor_float4;
                }
                this.encodedPosX = MathHelper.floor_double(this.trackedEntity.posX * 32.0);
                this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0);
                this.encodedPosZ = MathHelper.floor_double(this.trackedEntity.posZ * 32.0);
                this.sendMetadataToAllAssociatedPlayers();
                this.ridingEntity = true;
            }
            final int floor_float5 = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
            if (Math.abs(floor_float5 - this.lastHeadMotion) >= 4) {
                this.sendPacketToTrackedPlayers(new S19PacketEntityHeadLook(this.trackedEntity, (byte)floor_float5));
                this.lastHeadMotion = floor_float5;
            }
            this.trackedEntity.isAirBorne = false;
        }
        ++this.updateCounter;
        if (this.trackedEntity.velocityChanged) {
            this.func_151261_b(new S12PacketEntityVelocity(this.trackedEntity));
            this.trackedEntity.velocityChanged = false;
        }
    }
    
    public void sendDestroyEntityPacketToTrackedPlayers() {
        final Iterator<EntityPlayerMP> iterator = this.trackingPlayers.iterator();
        while (iterator.hasNext()) {
            iterator.next().removeEntity(this.trackedEntity);
        }
    }
    
    private Packet func_151260_c() {
        if (this.trackedEntity.isDead) {
            EntityTrackerEntry.logger.warn("Fetching addPacket for removed entity");
        }
        if (this.trackedEntity instanceof EntityItem) {
            return new S0EPacketSpawnObject(this.trackedEntity, 2, 1);
        }
        if (this.trackedEntity instanceof EntityPlayerMP) {
            return new S0CPacketSpawnPlayer((EntityPlayer)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityMinecart) {
            return new S0EPacketSpawnObject(this.trackedEntity, 10, ((EntityMinecart)this.trackedEntity).getMinecartType().getNetworkID());
        }
        if (this.trackedEntity instanceof EntityBoat) {
            return new S0EPacketSpawnObject(this.trackedEntity, 1);
        }
        if (this.trackedEntity instanceof IAnimals) {
            this.lastHeadMotion = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
            return new S0FPacketSpawnMob((EntityLivingBase)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityFishHook) {
            final EntityPlayer angler = ((EntityFishHook)this.trackedEntity).angler;
            return new S0EPacketSpawnObject(this.trackedEntity, 90, (angler != null) ? angler.getEntityId() : this.trackedEntity.getEntityId());
        }
        if (this.trackedEntity instanceof EntityArrow) {
            final Entity shootingEntity = ((EntityArrow)this.trackedEntity).shootingEntity;
            return new S0EPacketSpawnObject(this.trackedEntity, 60, (shootingEntity != null) ? shootingEntity.getEntityId() : this.trackedEntity.getEntityId());
        }
        if (this.trackedEntity instanceof EntitySnowball) {
            return new S0EPacketSpawnObject(this.trackedEntity, 61);
        }
        if (this.trackedEntity instanceof EntityPotion) {
            return new S0EPacketSpawnObject(this.trackedEntity, 73, ((EntityPotion)this.trackedEntity).getPotionDamage());
        }
        if (this.trackedEntity instanceof EntityExpBottle) {
            return new S0EPacketSpawnObject(this.trackedEntity, 75);
        }
        if (this.trackedEntity instanceof EntityEnderPearl) {
            return new S0EPacketSpawnObject(this.trackedEntity, 65);
        }
        if (this.trackedEntity instanceof EntityEnderEye) {
            return new S0EPacketSpawnObject(this.trackedEntity, 72);
        }
        if (this.trackedEntity instanceof EntityFireworkRocket) {
            return new S0EPacketSpawnObject(this.trackedEntity, 76);
        }
        if (this.trackedEntity instanceof EntityFireball) {
            final EntityFireball entityFireball = (EntityFireball)this.trackedEntity;
            if (!(this.trackedEntity instanceof EntitySmallFireball)) {
                if (this.trackedEntity instanceof EntityWitherSkull) {}
            }
            S0EPacketSpawnObject s0EPacketSpawnObject;
            if (entityFireball.shootingEntity != null) {
                s0EPacketSpawnObject = new S0EPacketSpawnObject(this.trackedEntity, 66, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityId());
            }
            else {
                s0EPacketSpawnObject = new S0EPacketSpawnObject(this.trackedEntity, 66, 0);
            }
            s0EPacketSpawnObject.setSpeedX((int)(entityFireball.accelerationX * 8000.0));
            s0EPacketSpawnObject.setSpeedY((int)(entityFireball.accelerationY * 8000.0));
            s0EPacketSpawnObject.setSpeedZ((int)(entityFireball.accelerationZ * 8000.0));
            return s0EPacketSpawnObject;
        }
        if (this.trackedEntity instanceof EntityEgg) {
            return new S0EPacketSpawnObject(this.trackedEntity, 62);
        }
        if (this.trackedEntity instanceof EntityTNTPrimed) {
            return new S0EPacketSpawnObject(this.trackedEntity, 50);
        }
        if (this.trackedEntity instanceof EntityEnderCrystal) {
            return new S0EPacketSpawnObject(this.trackedEntity, 51);
        }
        if (this.trackedEntity instanceof EntityFallingBlock) {
            return new S0EPacketSpawnObject(this.trackedEntity, 70, Block.getStateId(((EntityFallingBlock)this.trackedEntity).getBlock()));
        }
        if (this.trackedEntity instanceof EntityArmorStand) {
            return new S0EPacketSpawnObject(this.trackedEntity, 78);
        }
        if (this.trackedEntity instanceof EntityPainting) {
            return new S10PacketSpawnPainting((EntityPainting)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityItemFrame) {
            final EntityItemFrame entityItemFrame = (EntityItemFrame)this.trackedEntity;
            final S0EPacketSpawnObject s0EPacketSpawnObject2 = new S0EPacketSpawnObject(this.trackedEntity, 71, entityItemFrame.facingDirection.getHorizontalIndex());
            final BlockPos hangingPosition = entityItemFrame.getHangingPosition();
            s0EPacketSpawnObject2.setX(MathHelper.floor_float((float)(hangingPosition.getX() * 32)));
            s0EPacketSpawnObject2.setY(MathHelper.floor_float((float)(hangingPosition.getY() * 32)));
            s0EPacketSpawnObject2.setZ(MathHelper.floor_float((float)(hangingPosition.getZ() * 32)));
            return s0EPacketSpawnObject2;
        }
        if (this.trackedEntity instanceof EntityLeashKnot) {
            final EntityLeashKnot entityLeashKnot = (EntityLeashKnot)this.trackedEntity;
            final S0EPacketSpawnObject s0EPacketSpawnObject3 = new S0EPacketSpawnObject(this.trackedEntity, 77);
            final BlockPos hangingPosition2 = entityLeashKnot.getHangingPosition();
            s0EPacketSpawnObject3.setX(MathHelper.floor_float((float)(hangingPosition2.getX() * 32)));
            s0EPacketSpawnObject3.setY(MathHelper.floor_float((float)(hangingPosition2.getY() * 32)));
            s0EPacketSpawnObject3.setZ(MathHelper.floor_float((float)(hangingPosition2.getZ() * 32)));
            return s0EPacketSpawnObject3;
        }
        if (this.trackedEntity instanceof EntityXPOrb) {
            return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
        }
        throw new IllegalArgumentException("Don't know how to add " + this.trackedEntity.getClass() + "!");
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public void removeFromTrackedPlayers(final EntityPlayerMP entityPlayerMP) {
        if (this.trackingPlayers.contains(entityPlayerMP)) {
            entityPlayerMP.removeEntity(this.trackedEntity);
            this.trackingPlayers.remove(entityPlayerMP);
        }
    }
}
