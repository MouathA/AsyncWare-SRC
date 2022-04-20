package net.minecraft.server.management;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.play.server.*;

public class PlayerManager
{
    private final List playerInstanceList;
    private final int[][] xzDirectionsConst;
    private final LongHashMap playerInstances;
    private long previousTotalWorldTime;
    private final List playerInstancesToUpdate;
    private final WorldServer theWorldServer;
    private final List players;
    private static final Logger pmLogger;
    private int playerViewRadius;
    
    public static int getFurthestViewableBlock(final int n) {
        return n * 16 - 16;
    }
    
    public WorldServer getWorldServer() {
        return this.theWorldServer;
    }
    
    static {
        pmLogger = LogManager.getLogger();
    }
    
    public PlayerManager(final WorldServer theWorldServer) {
        this.players = Lists.newArrayList();
        this.playerInstances = new LongHashMap();
        this.playerInstancesToUpdate = Lists.newArrayList();
        this.playerInstanceList = Lists.newArrayList();
        this.xzDirectionsConst = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        this.theWorldServer = theWorldServer;
        this.setPlayerViewRadius(theWorldServer.getMinecraftServer().getConfigurationManager().getViewDistance());
    }
    
    public void updateMountedMovingPlayer(final EntityPlayerMP p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/player/EntityPlayerMP.posX:D
        //     4: d2i            
        //     5: iconst_4       
        //     6: ishr           
        //     7: istore_2       
        //     8: aload_1        
        //     9: getfield        net/minecraft/entity/player/EntityPlayerMP.posZ:D
        //    12: d2i            
        //    13: iconst_4       
        //    14: ishr           
        //    15: istore_3       
        //    16: aload_1        
        //    17: getfield        net/minecraft/entity/player/EntityPlayerMP.managedPosX:D
        //    20: aload_1        
        //    21: getfield        net/minecraft/entity/player/EntityPlayerMP.posX:D
        //    24: dsub           
        //    25: dstore          4
        //    27: aload_1        
        //    28: getfield        net/minecraft/entity/player/EntityPlayerMP.managedPosZ:D
        //    31: aload_1        
        //    32: getfield        net/minecraft/entity/player/EntityPlayerMP.posZ:D
        //    35: dsub           
        //    36: dstore          6
        //    38: dload           4
        //    40: dload           4
        //    42: dmul           
        //    43: dload           6
        //    45: dload           6
        //    47: dmul           
        //    48: dadd           
        //    49: dstore          8
        //    51: dload           8
        //    53: ldc2_w          64.0
        //    56: dcmpl          
        //    57: iflt            242
        //    60: aload_1        
        //    61: getfield        net/minecraft/entity/player/EntityPlayerMP.managedPosX:D
        //    64: d2i            
        //    65: iconst_4       
        //    66: ishr           
        //    67: istore          10
        //    69: aload_1        
        //    70: getfield        net/minecraft/entity/player/EntityPlayerMP.managedPosZ:D
        //    73: d2i            
        //    74: iconst_4       
        //    75: ishr           
        //    76: istore          11
        //    78: aload_0        
        //    79: getfield        net/minecraft/server/management/PlayerManager.playerViewRadius:I
        //    82: istore          12
        //    84: iload_2        
        //    85: iload           10
        //    87: isub           
        //    88: istore          13
        //    90: iload_3        
        //    91: iload           11
        //    93: isub           
        //    94: istore          14
        //    96: iload           13
        //    98: ifne            106
        //   101: iload           14
        //   103: ifeq            242
        //   106: iload_2        
        //   107: iload           12
        //   109: isub           
        //   110: istore          15
        //   112: iload           15
        //   114: iload_2        
        //   115: iload           12
        //   117: iadd           
        //   118: if_icmpgt       221
        //   121: iload_3        
        //   122: iload           12
        //   124: isub           
        //   125: istore          16
        //   127: iload           16
        //   129: iload_3        
        //   130: iload           12
        //   132: iadd           
        //   133: if_icmpgt       215
        //   136: aload_0        
        //   137: iload           15
        //   139: iload           16
        //   141: iload           10
        //   143: iload           11
        //   145: iload           12
        //   147: if_icmplt       163
        //   150: aload_0        
        //   151: iload           15
        //   153: iload           16
        //   155: iconst_1       
        //   156: invokespecial   net/minecraft/server/management/PlayerManager.getPlayerInstance:(IIZ)Lnet/minecraft/server/management/PlayerManager$PlayerInstance;
        //   159: aload_1        
        //   160: invokevirtual   net/minecraft/server/management/PlayerManager$PlayerInstance.addPlayer:(Lnet/minecraft/entity/player/EntityPlayerMP;)V
        //   163: aload_0        
        //   164: iload           15
        //   166: iload           13
        //   168: isub           
        //   169: iload           16
        //   171: iload           14
        //   173: isub           
        //   174: iload_2        
        //   175: iload_3        
        //   176: iload           12
        //   178: if_icmplt       209
        //   181: aload_0        
        //   182: iload           15
        //   184: iload           13
        //   186: isub           
        //   187: iload           16
        //   189: iload           14
        //   191: isub           
        //   192: iconst_0       
        //   193: invokespecial   net/minecraft/server/management/PlayerManager.getPlayerInstance:(IIZ)Lnet/minecraft/server/management/PlayerManager$PlayerInstance;
        //   196: astore          17
        //   198: aload           17
        //   200: ifnull          209
        //   203: aload           17
        //   205: aload_1        
        //   206: invokevirtual   net/minecraft/server/management/PlayerManager$PlayerInstance.removePlayer:(Lnet/minecraft/entity/player/EntityPlayerMP;)V
        //   209: iinc            16, 1
        //   212: goto            127
        //   215: iinc            15, 1
        //   218: goto            112
        //   221: aload_0        
        //   222: aload_1        
        //   223: invokevirtual   net/minecraft/server/management/PlayerManager.filterChunkLoadQueue:(Lnet/minecraft/entity/player/EntityPlayerMP;)V
        //   226: aload_1        
        //   227: aload_1        
        //   228: getfield        net/minecraft/entity/player/EntityPlayerMP.posX:D
        //   231: putfield        net/minecraft/entity/player/EntityPlayerMP.managedPosX:D
        //   234: aload_1        
        //   235: aload_1        
        //   236: getfield        net/minecraft/entity/player/EntityPlayerMP.posZ:D
        //   239: putfield        net/minecraft/entity/player/EntityPlayerMP.managedPosZ:D
        //   242: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0127 (coming from #0212).
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
    
    static LongHashMap access$400(final PlayerManager playerManager) {
        return playerManager.playerInstances;
    }
    
    public void addPlayer(final EntityPlayerMP entityPlayerMP) {
        final int n = (int)entityPlayerMP.posX >> 4;
        final int n2 = (int)entityPlayerMP.posZ >> 4;
        entityPlayerMP.managedPosX = entityPlayerMP.posX;
        entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
        for (int i = n - this.playerViewRadius; i <= n + this.playerViewRadius; ++i) {
            for (int j = n2 - this.playerViewRadius; j <= n2 + this.playerViewRadius; ++j) {
                this.getPlayerInstance(i, j, true).addPlayer(entityPlayerMP);
            }
        }
        this.players.add(entityPlayerMP);
        this.filterChunkLoadQueue(entityPlayerMP);
    }
    
    public boolean isPlayerWatchingChunk(final EntityPlayerMP entityPlayerMP, final int n, final int n2) {
        final PlayerInstance playerInstance = this.getPlayerInstance(n, n2, false);
        return playerInstance != null && PlayerInstance.access$100(playerInstance).contains(entityPlayerMP) && !entityPlayerMP.loadedChunks.contains(PlayerInstance.access$000(playerInstance));
    }
    
    public void filterChunkLoadQueue(final EntityPlayerMP entityPlayerMP) {
        final ArrayList arrayList = Lists.newArrayList((Iterable)entityPlayerMP.loadedChunks);
        final int playerViewRadius = this.playerViewRadius;
        final int n = (int)entityPlayerMP.posX >> 4;
        final int n2 = (int)entityPlayerMP.posZ >> 4;
        final ChunkCoordIntPair access$000 = PlayerInstance.access$000(this.getPlayerInstance(n, n2, true));
        entityPlayerMP.loadedChunks.clear();
        if (arrayList.contains(access$000)) {
            entityPlayerMP.loadedChunks.add(access$000);
        }
        if (0 > playerViewRadius * 2) {
            while (0 < playerViewRadius * 2) {
                final int n3 = 0 + this.xzDirectionsConst[0][0];
                final int n4 = 0 + this.xzDirectionsConst[0][1];
                final ChunkCoordIntPair access$2 = PlayerInstance.access$000(this.getPlayerInstance(n + 0, n2 + 0, true));
                if (arrayList.contains(access$2)) {
                    entityPlayerMP.loadedChunks.add(access$2);
                }
                int n5 = 0;
                ++n5;
            }
            return;
        }
        while (true) {
            final int[][] xzDirectionsConst = this.xzDirectionsConst;
            final int n6 = 0;
            int n7 = 0;
            ++n7;
            final int[] array = xzDirectionsConst[n6 % 4];
            int n8 = 0;
            ++n8;
        }
    }
    
    public void markBlockForUpdate(final BlockPos blockPos) {
        final PlayerInstance playerInstance = this.getPlayerInstance(blockPos.getX() >> 4, blockPos.getZ() >> 4, false);
        if (playerInstance != null) {
            playerInstance.flagChunkForUpdate(blockPos.getX() & 0xF, blockPos.getY(), blockPos.getZ() & 0xF);
        }
    }
    
    private PlayerInstance getPlayerInstance(final int n, final int n2, final boolean b) {
        final long n3 = n + 2147483647L | n2 + 2147483647L << 32;
        PlayerInstance playerInstance = (PlayerInstance)this.playerInstances.getValueByKey(n3);
        if (playerInstance == null && b) {
            playerInstance = new PlayerInstance(n, n2);
            this.playerInstances.add(n3, playerInstance);
            this.playerInstanceList.add(playerInstance);
        }
        return playerInstance;
    }
    
    static List access$600(final PlayerManager playerManager) {
        return playerManager.playerInstancesToUpdate;
    }
    
    static Logger access$200() {
        return PlayerManager.pmLogger;
    }
    
    public void setPlayerViewRadius(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_3       
        //     2: bipush          32
        //     4: invokestatic    net/minecraft/util/MathHelper.clamp_int:(III)I
        //     7: istore_1       
        //     8: iload_1        
        //     9: aload_0        
        //    10: getfield        net/minecraft/server/management/PlayerManager.playerViewRadius:I
        //    13: if_icmpeq       245
        //    16: iload_1        
        //    17: aload_0        
        //    18: getfield        net/minecraft/server/management/PlayerManager.playerViewRadius:I
        //    21: isub           
        //    22: istore_2       
        //    23: aload_0        
        //    24: getfield        net/minecraft/server/management/PlayerManager.players:Ljava/util/List;
        //    27: invokestatic    com/google/common/collect/Lists.newArrayList:(Ljava/lang/Iterable;)Ljava/util/ArrayList;
        //    30: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //    33: astore_3       
        //    34: aload_3        
        //    35: invokeinterface java/util/Iterator.hasNext:()Z
        //    40: ifeq            240
        //    43: aload_3        
        //    44: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    49: checkcast       Lnet/minecraft/entity/player/EntityPlayerMP;
        //    52: astore          4
        //    54: aload           4
        //    56: getfield        net/minecraft/entity/player/EntityPlayerMP.posX:D
        //    59: d2i            
        //    60: iconst_4       
        //    61: ishr           
        //    62: istore          5
        //    64: aload           4
        //    66: getfield        net/minecraft/entity/player/EntityPlayerMP.posZ:D
        //    69: d2i            
        //    70: iconst_4       
        //    71: ishr           
        //    72: istore          6
        //    74: iload_2        
        //    75: ifle            156
        //    78: iload           5
        //    80: iload_1        
        //    81: isub           
        //    82: istore          7
        //    84: iload           7
        //    86: iload           5
        //    88: iload_1        
        //    89: iadd           
        //    90: if_icmpgt       153
        //    93: iload           6
        //    95: iload_1        
        //    96: isub           
        //    97: istore          8
        //    99: iload           8
        //   101: iload           6
        //   103: iload_1        
        //   104: iadd           
        //   105: if_icmpgt       147
        //   108: aload_0        
        //   109: iload           7
        //   111: iload           8
        //   113: iconst_1       
        //   114: invokespecial   net/minecraft/server/management/PlayerManager.getPlayerInstance:(IIZ)Lnet/minecraft/server/management/PlayerManager$PlayerInstance;
        //   117: astore          9
        //   119: aload           9
        //   121: invokestatic    net/minecraft/server/management/PlayerManager$PlayerInstance.access$100:(Lnet/minecraft/server/management/PlayerManager$PlayerInstance;)Ljava/util/List;
        //   124: aload           4
        //   126: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   131: ifne            141
        //   134: aload           9
        //   136: aload           4
        //   138: invokevirtual   net/minecraft/server/management/PlayerManager$PlayerInstance.addPlayer:(Lnet/minecraft/entity/player/EntityPlayerMP;)V
        //   141: iinc            8, 1
        //   144: goto            99
        //   147: iinc            7, 1
        //   150: goto            84
        //   153: goto            34
        //   156: iload           5
        //   158: aload_0        
        //   159: getfield        net/minecraft/server/management/PlayerManager.playerViewRadius:I
        //   162: isub           
        //   163: istore          7
        //   165: iload           7
        //   167: iload           5
        //   169: aload_0        
        //   170: getfield        net/minecraft/server/management/PlayerManager.playerViewRadius:I
        //   173: iadd           
        //   174: if_icmpgt       237
        //   177: iload           6
        //   179: aload_0        
        //   180: getfield        net/minecraft/server/management/PlayerManager.playerViewRadius:I
        //   183: isub           
        //   184: istore          8
        //   186: iload           8
        //   188: iload           6
        //   190: aload_0        
        //   191: getfield        net/minecraft/server/management/PlayerManager.playerViewRadius:I
        //   194: iadd           
        //   195: if_icmpgt       231
        //   198: aload_0        
        //   199: iload           7
        //   201: iload           8
        //   203: iload           5
        //   205: iload           6
        //   207: iload_1        
        //   208: if_icmplt       225
        //   211: aload_0        
        //   212: iload           7
        //   214: iload           8
        //   216: iconst_1       
        //   217: invokespecial   net/minecraft/server/management/PlayerManager.getPlayerInstance:(IIZ)Lnet/minecraft/server/management/PlayerManager$PlayerInstance;
        //   220: aload           4
        //   222: invokevirtual   net/minecraft/server/management/PlayerManager$PlayerInstance.removePlayer:(Lnet/minecraft/entity/player/EntityPlayerMP;)V
        //   225: iinc            8, 1
        //   228: goto            186
        //   231: iinc            7, 1
        //   234: goto            165
        //   237: goto            34
        //   240: aload_0        
        //   241: iload_1        
        //   242: putfield        net/minecraft/server/management/PlayerManager.playerViewRadius:I
        //   245: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0186 (coming from #0228).
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
    
    static WorldServer access$300(final PlayerManager playerManager) {
        return playerManager.theWorldServer;
    }
    
    static List access$500(final PlayerManager playerManager) {
        return playerManager.playerInstanceList;
    }
    
    public void removePlayer(final EntityPlayerMP entityPlayerMP) {
        final int n = (int)entityPlayerMP.managedPosX >> 4;
        final int n2 = (int)entityPlayerMP.managedPosZ >> 4;
        for (int i = n - this.playerViewRadius; i <= n + this.playerViewRadius; ++i) {
            for (int j = n2 - this.playerViewRadius; j <= n2 + this.playerViewRadius; ++j) {
                final PlayerInstance playerInstance = this.getPlayerInstance(i, j, false);
                if (playerInstance != null) {
                    playerInstance.removePlayer(entityPlayerMP);
                }
            }
        }
        this.players.remove(entityPlayerMP);
    }
    
    public boolean hasPlayerInstance(final int n, final int n2) {
        return this.playerInstances.getValueByKey(n + 2147483647L | n2 + 2147483647L << 32) != null;
    }
    
    public void updatePlayerInstances() {
        final long totalWorldTime = this.theWorldServer.getTotalWorldTime();
        if (totalWorldTime - this.previousTotalWorldTime > 8000L) {
            this.previousTotalWorldTime = totalWorldTime;
            while (0 < this.playerInstanceList.size()) {
                final PlayerInstance playerInstance = this.playerInstanceList.get(0);
                playerInstance.onUpdate();
                playerInstance.processChunk();
                int n = 0;
                ++n;
            }
        }
        else {
            while (0 < this.playerInstancesToUpdate.size()) {
                this.playerInstancesToUpdate.get(0).onUpdate();
                int n = 0;
                ++n;
            }
        }
        this.playerInstancesToUpdate.clear();
        if (this.players.isEmpty() && !this.theWorldServer.provider.canRespawnHere()) {
            this.theWorldServer.theChunkProviderServer.unloadAllChunks();
        }
    }
    
    class PlayerInstance
    {
        private final ChunkCoordIntPair chunkCoords;
        private final List playersWatchingChunk;
        private long previousWorldTime;
        final PlayerManager this$0;
        private short[] locationOfBlockChange;
        private int numBlocksToUpdate;
        private int flagsYAreasToUpdate;
        
        public void sendToAllPlayersWatchingChunk(final Packet packet) {
            while (0 < this.playersWatchingChunk.size()) {
                final EntityPlayerMP entityPlayerMP = this.playersWatchingChunk.get(0);
                if (!entityPlayerMP.loadedChunks.contains(this.chunkCoords)) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                }
                int n = 0;
                ++n;
            }
        }
        
        public void processChunk() {
            this.increaseInhabitedTime(PlayerManager.access$300(this.this$0).getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos));
        }
        
        public void flagChunkForUpdate(final int n, final int n2, final int n3) {
            if (this.numBlocksToUpdate == 0) {
                PlayerManager.access$600(this.this$0).add(this);
            }
            this.flagsYAreasToUpdate |= 1 << (n2 >> 4);
            if (this.numBlocksToUpdate < 64) {
                final short n4 = (short)(n << 12 | n3 << 8 | n2);
                while (0 < this.numBlocksToUpdate) {
                    if (this.locationOfBlockChange[0] == n4) {
                        return;
                    }
                    int n5 = 0;
                    ++n5;
                }
                this.locationOfBlockChange[this.numBlocksToUpdate++] = n4;
            }
        }
        
        public void removePlayer(final EntityPlayerMP entityPlayerMP) {
            if (this.playersWatchingChunk.contains(entityPlayerMP)) {
                final Chunk chunkFromChunkCoords = PlayerManager.access$300(this.this$0).getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
                if (chunkFromChunkCoords.isPopulated()) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(new S21PacketChunkData(chunkFromChunkCoords, true, 0));
                }
                this.playersWatchingChunk.remove(entityPlayerMP);
                entityPlayerMP.loadedChunks.remove(this.chunkCoords);
                if (this.playersWatchingChunk.isEmpty()) {
                    final long n = this.chunkCoords.chunkXPos + 2147483647L | this.chunkCoords.chunkZPos + 2147483647L << 32;
                    this.increaseInhabitedTime(chunkFromChunkCoords);
                    PlayerManager.access$400(this.this$0).remove(n);
                    PlayerManager.access$500(this.this$0).remove(this);
                    if (this.numBlocksToUpdate > 0) {
                        PlayerManager.access$600(this.this$0).remove(this);
                    }
                    this.this$0.getWorldServer().theChunkProviderServer.dropChunk(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
                }
            }
        }
        
        static ChunkCoordIntPair access$000(final PlayerInstance playerInstance) {
            return playerInstance.chunkCoords;
        }
        
        static List access$100(final PlayerInstance playerInstance) {
            return playerInstance.playersWatchingChunk;
        }
        
        private void increaseInhabitedTime(final Chunk chunk) {
            chunk.setInhabitedTime(chunk.getInhabitedTime() + PlayerManager.access$300(this.this$0).getTotalWorldTime() - this.previousWorldTime);
            this.previousWorldTime = PlayerManager.access$300(this.this$0).getTotalWorldTime();
        }
        
        public void onUpdate() {
            if (this.numBlocksToUpdate != 0) {
                if (this.numBlocksToUpdate == 1) {
                    final int n = (this.locationOfBlockChange[0] >> 12 & 0xF) + this.chunkCoords.chunkXPos * 16;
                    final int n2 = this.locationOfBlockChange[0] & 0xFF;
                    final int n3 = (this.locationOfBlockChange[0] >> 8 & 0xF) + this.chunkCoords.chunkZPos * 16;
                    final BlockPos blockPos = new BlockPos(0, n2, 0);
                    this.sendToAllPlayersWatchingChunk(new S23PacketBlockChange(PlayerManager.access$300(this.this$0), blockPos));
                    if (PlayerManager.access$300(this.this$0).getBlockState(blockPos).getBlock().hasTileEntity()) {
                        this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$300(this.this$0).getTileEntity(blockPos));
                    }
                }
                else if (this.numBlocksToUpdate == 64) {
                    final int n = this.chunkCoords.chunkXPos * 16;
                    final int n4 = this.chunkCoords.chunkZPos * 16;
                    this.sendToAllPlayersWatchingChunk(new S21PacketChunkData(PlayerManager.access$300(this.this$0).getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos), false, this.flagsYAreasToUpdate));
                    while (true) {
                        if ((this.flagsYAreasToUpdate & 0x1) != 0x0) {
                            final List tileEntitiesIn = PlayerManager.access$300(this.this$0).getTileEntitiesIn(0, 0, n4, 16, 16, n4 + 16);
                            while (0 < tileEntitiesIn.size()) {
                                this.sendTileToAllPlayersWatchingChunk(tileEntitiesIn.get(0));
                                int n5 = 0;
                                ++n5;
                            }
                        }
                        int n3 = 0;
                        ++n3;
                    }
                }
                else {
                    this.sendToAllPlayersWatchingChunk(new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.access$300(this.this$0).getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos)));
                    while (0 < this.numBlocksToUpdate) {
                        final int n6 = (this.locationOfBlockChange[0] >> 12 & 0xF) + this.chunkCoords.chunkXPos * 16;
                        final int n3 = this.locationOfBlockChange[0] & 0xFF;
                        final int n7 = (this.locationOfBlockChange[0] >> 8 & 0xF) + this.chunkCoords.chunkZPos * 16;
                        final BlockPos blockPos2 = new BlockPos(n6, 0, 0);
                        if (PlayerManager.access$300(this.this$0).getBlockState(blockPos2).getBlock().hasTileEntity()) {
                            this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$300(this.this$0).getTileEntity(blockPos2));
                        }
                        int n = 0;
                        ++n;
                    }
                }
                this.numBlocksToUpdate = 0;
                this.flagsYAreasToUpdate = 0;
            }
        }
        
        private void sendTileToAllPlayersWatchingChunk(final TileEntity tileEntity) {
            if (tileEntity != null) {
                final Packet descriptionPacket = tileEntity.getDescriptionPacket();
                if (descriptionPacket != null) {
                    this.sendToAllPlayersWatchingChunk(descriptionPacket);
                }
            }
        }
        
        public void addPlayer(final EntityPlayerMP entityPlayerMP) {
            if (this.playersWatchingChunk.contains(entityPlayerMP)) {
                PlayerManager.access$200().debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { entityPlayerMP, this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos });
            }
            else {
                if (this.playersWatchingChunk.isEmpty()) {
                    this.previousWorldTime = PlayerManager.access$300(this.this$0).getTotalWorldTime();
                }
                this.playersWatchingChunk.add(entityPlayerMP);
                entityPlayerMP.loadedChunks.add(this.chunkCoords);
            }
        }
        
        public PlayerInstance(final PlayerManager this$0, final int n, final int n2) {
            this.this$0 = this$0;
            this.playersWatchingChunk = Lists.newArrayList();
            this.locationOfBlockChange = new short[64];
            this.chunkCoords = new ChunkCoordIntPair(n, n2);
            this$0.getWorldServer().theChunkProviderServer.loadChunk(n, n2);
        }
    }
}
