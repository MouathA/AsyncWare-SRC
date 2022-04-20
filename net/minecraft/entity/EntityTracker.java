package net.minecraft.entity;

import net.minecraft.util.*;
import net.minecraft.world.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;

public class EntityTracker
{
    private IntHashMap trackedEntityHashTable;
    private static final Logger logger;
    private final WorldServer theWorld;
    private Set trackedEntities;
    private int maxTrackingDistanceThreshold;
    
    static {
        logger = LogManager.getLogger();
    }
    
    public void removePlayerFromTrackers(final EntityPlayerMP entityPlayerMP) {
        final Iterator<EntityTrackerEntry> iterator = this.trackedEntities.iterator();
        while (iterator.hasNext()) {
            iterator.next().removeTrackedPlayerSymmetric(entityPlayerMP);
        }
    }
    
    public void updateTrackedEntities() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore_1       
        //     4: aload_0        
        //     5: getfield        net/minecraft/entity/EntityTracker.trackedEntities:Ljava/util/Set;
        //     8: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    13: astore_2       
        //    14: aload_2        
        //    15: invokeinterface java/util/Iterator.hasNext:()Z
        //    20: ifeq            78
        //    23: aload_2        
        //    24: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    29: checkcast       Lnet/minecraft/entity/EntityTrackerEntry;
        //    32: astore_3       
        //    33: aload_3        
        //    34: aload_0        
        //    35: getfield        net/minecraft/entity/EntityTracker.theWorld:Lnet/minecraft/world/WorldServer;
        //    38: getfield        net/minecraft/world/WorldServer.playerEntities:Ljava/util/List;
        //    41: invokevirtual   net/minecraft/entity/EntityTrackerEntry.updatePlayerList:(Ljava/util/List;)V
        //    44: aload_3        
        //    45: getfield        net/minecraft/entity/EntityTrackerEntry.playerEntitiesUpdated:Z
        //    48: ifeq            75
        //    51: aload_3        
        //    52: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    55: instanceof      Lnet/minecraft/entity/player/EntityPlayerMP;
        //    58: ifeq            75
        //    61: aload_1        
        //    62: aload_3        
        //    63: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //    66: checkcast       Lnet/minecraft/entity/player/EntityPlayerMP;
        //    69: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    74: pop            
        //    75: goto            14
        //    78: iconst_0       
        //    79: aload_1        
        //    80: invokeinterface java/util/List.size:()I
        //    85: if_icmpge       156
        //    88: aload_1        
        //    89: iconst_0       
        //    90: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    95: checkcast       Lnet/minecraft/entity/player/EntityPlayerMP;
        //    98: astore_3       
        //    99: aload_0        
        //   100: getfield        net/minecraft/entity/EntityTracker.trackedEntities:Ljava/util/Set;
        //   103: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   108: astore          4
        //   110: aload           4
        //   112: invokeinterface java/util/Iterator.hasNext:()Z
        //   117: ifeq            150
        //   120: aload           4
        //   122: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   127: checkcast       Lnet/minecraft/entity/EntityTrackerEntry;
        //   130: astore          5
        //   132: aload           5
        //   134: getfield        net/minecraft/entity/EntityTrackerEntry.trackedEntity:Lnet/minecraft/entity/Entity;
        //   137: aload_3        
        //   138: if_acmpeq       147
        //   141: aload           5
        //   143: aload_3        
        //   144: invokevirtual   net/minecraft/entity/EntityTrackerEntry.updatePlayerEntity:(Lnet/minecraft/entity/player/EntityPlayerMP;)V
        //   147: goto            110
        //   150: iinc            2, 1
        //   153: goto            78
        //   156: return         
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
    
    public void addEntityToTracker(final Entity entity, int maxTrackingDistanceThreshold, final int n, final boolean b) {
        if (maxTrackingDistanceThreshold > this.maxTrackingDistanceThreshold) {
            maxTrackingDistanceThreshold = this.maxTrackingDistanceThreshold;
        }
        if (this.trackedEntityHashTable.containsItem(entity.getEntityId())) {
            throw new IllegalStateException("Entity is already tracked!");
        }
        final EntityTrackerEntry entityTrackerEntry = new EntityTrackerEntry(entity, maxTrackingDistanceThreshold, n, b);
        this.trackedEntities.add(entityTrackerEntry);
        this.trackedEntityHashTable.addKey(entity.getEntityId(), entityTrackerEntry);
        entityTrackerEntry.updatePlayerEntities(this.theWorld.playerEntities);
    }
    
    public void func_180245_a(final EntityPlayerMP entityPlayerMP) {
        for (final EntityTrackerEntry entityTrackerEntry : this.trackedEntities) {
            if (entityTrackerEntry.trackedEntity == entityPlayerMP) {
                entityTrackerEntry.updatePlayerEntities(this.theWorld.playerEntities);
            }
            else {
                entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
            }
        }
    }
    
    public void sendToAllTrackingEntity(final Entity entity, final Packet packet) {
        final EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entity.getEntityId());
        if (entityTrackerEntry != null) {
            entityTrackerEntry.sendPacketToTrackedPlayers(packet);
        }
    }
    
    public void trackEntity(final Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            this.trackEntity(entity, 512, 2);
            final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
            for (final EntityTrackerEntry entityTrackerEntry : this.trackedEntities) {
                if (entityTrackerEntry.trackedEntity != entityPlayerMP) {
                    entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
                }
            }
        }
        else if (entity instanceof EntityFishHook) {
            this.addEntityToTracker(entity, 64, 5, true);
        }
        else if (entity instanceof EntityArrow) {
            this.addEntityToTracker(entity, 64, 20, false);
        }
        else if (entity instanceof EntitySmallFireball) {
            this.addEntityToTracker(entity, 64, 10, false);
        }
        else if (entity instanceof EntityFireball) {
            this.addEntityToTracker(entity, 64, 10, false);
        }
        else if (entity instanceof EntitySnowball) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityEnderPearl) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityEnderEye) {
            this.addEntityToTracker(entity, 64, 4, true);
        }
        else if (entity instanceof EntityEgg) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityPotion) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityExpBottle) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityFireworkRocket) {
            this.addEntityToTracker(entity, 64, 10, true);
        }
        else if (entity instanceof EntityItem) {
            this.addEntityToTracker(entity, 64, 20, true);
        }
        else if (entity instanceof EntityMinecart) {
            this.addEntityToTracker(entity, 80, 3, true);
        }
        else if (entity instanceof EntityBoat) {
            this.addEntityToTracker(entity, 80, 3, true);
        }
        else if (entity instanceof EntitySquid) {
            this.addEntityToTracker(entity, 64, 3, true);
        }
        else if (entity instanceof EntityWither) {
            this.addEntityToTracker(entity, 80, 3, false);
        }
        else if (entity instanceof EntityBat) {
            this.addEntityToTracker(entity, 80, 3, false);
        }
        else if (entity instanceof EntityDragon) {
            this.addEntityToTracker(entity, 160, 3, true);
        }
        else if (entity instanceof IAnimals) {
            this.addEntityToTracker(entity, 80, 3, true);
        }
        else if (entity instanceof EntityTNTPrimed) {
            this.addEntityToTracker(entity, 160, 10, true);
        }
        else if (entity instanceof EntityFallingBlock) {
            this.addEntityToTracker(entity, 160, 20, true);
        }
        else if (entity instanceof EntityHanging) {
            this.addEntityToTracker(entity, 160, Integer.MAX_VALUE, false);
        }
        else if (entity instanceof EntityArmorStand) {
            this.addEntityToTracker(entity, 160, 3, true);
        }
        else if (entity instanceof EntityXPOrb) {
            this.addEntityToTracker(entity, 160, 20, true);
        }
        else if (entity instanceof EntityEnderCrystal) {
            this.addEntityToTracker(entity, 256, Integer.MAX_VALUE, false);
        }
    }
    
    public void untrackEntity(final Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
            final Iterator<EntityTrackerEntry> iterator = (Iterator<EntityTrackerEntry>)this.trackedEntities.iterator();
            while (iterator.hasNext()) {
                iterator.next().removeFromTrackedPlayers(entityPlayerMP);
            }
        }
        final EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry)this.trackedEntityHashTable.removeObject(entity.getEntityId());
        if (entityTrackerEntry != null) {
            this.trackedEntities.remove(entityTrackerEntry);
            entityTrackerEntry.sendDestroyEntityPacketToTrackedPlayers();
        }
    }
    
    public void trackEntity(final Entity entity, final int n, final int n2) {
        this.addEntityToTracker(entity, n, n2, false);
    }
    
    public EntityTracker(final WorldServer theWorld) {
        this.trackedEntities = Sets.newHashSet();
        this.trackedEntityHashTable = new IntHashMap();
        this.theWorld = theWorld;
        this.maxTrackingDistanceThreshold = theWorld.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
    }
    
    public void func_85172_a(final EntityPlayerMP entityPlayerMP, final Chunk chunk) {
        for (final EntityTrackerEntry entityTrackerEntry : this.trackedEntities) {
            if (entityTrackerEntry.trackedEntity != entityPlayerMP && entityTrackerEntry.trackedEntity.chunkCoordX == chunk.xPosition && entityTrackerEntry.trackedEntity.chunkCoordZ == chunk.zPosition) {
                entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
            }
        }
    }
    
    public void func_151248_b(final Entity entity, final Packet packet) {
        final EntityTrackerEntry entityTrackerEntry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entity.getEntityId());
        if (entityTrackerEntry != null) {
            entityTrackerEntry.func_151261_b(packet);
        }
    }
}
