package net.minecraft.world.storage;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.network.play.server.*;
import java.util.*;

public class MapData extends WorldSavedData
{
    private Map playersHashMap;
    public byte scale;
    public int zCenter;
    public byte[] colors;
    public byte dimension;
    public List playersArrayList;
    public int xCenter;
    public Map mapDecorations;
    
    public Packet getMapPacket(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MapInfo mapInfo = this.playersHashMap.get(entityPlayer);
        return (mapInfo == null) ? null : mapInfo.getPacket(itemStack);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setByte("dimension", this.dimension);
        nbtTagCompound.setInteger("xCenter", this.xCenter);
        nbtTagCompound.setInteger("zCenter", this.zCenter);
        nbtTagCompound.setByte("scale", this.scale);
        nbtTagCompound.setShort("width", (short)128);
        nbtTagCompound.setShort("height", (short)128);
        nbtTagCompound.setByteArray("colors", this.colors);
    }
    
    public MapInfo getMapInfo(final EntityPlayer entityPlayer) {
        MapInfo mapInfo = this.playersHashMap.get(entityPlayer);
        if (mapInfo == null) {
            mapInfo = new MapInfo(entityPlayer);
            this.playersHashMap.put(entityPlayer, mapInfo);
            this.playersArrayList.add(mapInfo);
        }
        return mapInfo;
    }
    
    public void updateVisiblePlayers(final EntityPlayer p0, final ItemStack p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/world/storage/MapData.playersHashMap:Ljava/util/Map;
        //     4: aload_1        
        //     5: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //    10: ifne            46
        //    13: new             Lnet/minecraft/world/storage/MapData$MapInfo;
        //    16: dup            
        //    17: aload_0        
        //    18: aload_1        
        //    19: invokespecial   net/minecraft/world/storage/MapData$MapInfo.<init>:(Lnet/minecraft/world/storage/MapData;Lnet/minecraft/entity/player/EntityPlayer;)V
        //    22: astore_3       
        //    23: aload_0        
        //    24: getfield        net/minecraft/world/storage/MapData.playersHashMap:Ljava/util/Map;
        //    27: aload_1        
        //    28: aload_3        
        //    29: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    34: pop            
        //    35: aload_0        
        //    36: getfield        net/minecraft/world/storage/MapData.playersArrayList:Ljava/util/List;
        //    39: aload_3        
        //    40: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    45: pop            
        //    46: aload_1        
        //    47: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //    50: aload_2        
        //    51: invokevirtual   net/minecraft/entity/player/InventoryPlayer.hasItemStack:(Lnet/minecraft/item/ItemStack;)Z
        //    54: ifne            71
        //    57: aload_0        
        //    58: getfield        net/minecraft/world/storage/MapData.mapDecorations:Ljava/util/Map;
        //    61: aload_1        
        //    62: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //    65: invokeinterface java/util/Map.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //    70: pop            
        //    71: iconst_0       
        //    72: aload_0        
        //    73: getfield        net/minecraft/world/storage/MapData.playersArrayList:Ljava/util/List;
        //    76: invokeinterface java/util/List.size:()I
        //    81: if_icmpge       236
        //    84: aload_0        
        //    85: getfield        net/minecraft/world/storage/MapData.playersArrayList:Ljava/util/List;
        //    88: iconst_0       
        //    89: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //    94: checkcast       Lnet/minecraft/world/storage/MapData$MapInfo;
        //    97: astore          4
        //    99: aload           4
        //   101: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   104: getfield        net/minecraft/entity/player/EntityPlayer.isDead:Z
        //   107: ifne            203
        //   110: aload           4
        //   112: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   115: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   118: aload_2        
        //   119: invokevirtual   net/minecraft/entity/player/InventoryPlayer.hasItemStack:(Lnet/minecraft/item/ItemStack;)Z
        //   122: ifne            132
        //   125: aload_2        
        //   126: invokevirtual   net/minecraft/item/ItemStack.isOnItemFrame:()Z
        //   129: ifeq            203
        //   132: aload_2        
        //   133: invokevirtual   net/minecraft/item/ItemStack.isOnItemFrame:()Z
        //   136: ifne            230
        //   139: aload           4
        //   141: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   144: getfield        net/minecraft/entity/player/EntityPlayer.dimension:I
        //   147: aload_0        
        //   148: getfield        net/minecraft/world/storage/MapData.dimension:B
        //   151: if_icmpne       230
        //   154: aload_0        
        //   155: iconst_0       
        //   156: aload           4
        //   158: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   161: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   164: aload           4
        //   166: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   169: invokevirtual   net/minecraft/entity/player/EntityPlayer.getName:()Ljava/lang/String;
        //   172: aload           4
        //   174: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   177: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   180: aload           4
        //   182: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   185: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   188: aload           4
        //   190: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   193: getfield        net/minecraft/entity/player/EntityPlayer.rotationYaw:F
        //   196: f2d            
        //   197: invokespecial   net/minecraft/world/storage/MapData.updateDecorations:(ILnet/minecraft/world/World;Ljava/lang/String;DDD)V
        //   200: goto            230
        //   203: aload_0        
        //   204: getfield        net/minecraft/world/storage/MapData.playersHashMap:Ljava/util/Map;
        //   207: aload           4
        //   209: getfield        net/minecraft/world/storage/MapData$MapInfo.entityplayerObj:Lnet/minecraft/entity/player/EntityPlayer;
        //   212: invokeinterface java/util/Map.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //   217: pop            
        //   218: aload_0        
        //   219: getfield        net/minecraft/world/storage/MapData.playersArrayList:Ljava/util/List;
        //   222: aload           4
        //   224: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //   229: pop            
        //   230: iinc            3, 1
        //   233: goto            71
        //   236: aload_2        
        //   237: invokevirtual   net/minecraft/item/ItemStack.isOnItemFrame:()Z
        //   240: ifeq            308
        //   243: aload_2        
        //   244: invokevirtual   net/minecraft/item/ItemStack.getItemFrame:()Lnet/minecraft/entity/item/EntityItemFrame;
        //   247: astore_3       
        //   248: aload_3        
        //   249: invokevirtual   net/minecraft/entity/item/EntityItemFrame.getHangingPosition:()Lnet/minecraft/util/BlockPos;
        //   252: astore          4
        //   254: aload_0        
        //   255: iconst_1       
        //   256: aload_1        
        //   257: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   260: new             Ljava/lang/StringBuilder;
        //   263: dup            
        //   264: invokespecial   java/lang/StringBuilder.<init>:()V
        //   267: ldc             "frame-"
        //   269: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   272: aload_3        
        //   273: invokevirtual   net/minecraft/entity/item/EntityItemFrame.getEntityId:()I
        //   276: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   279: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   282: aload           4
        //   284: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   287: i2d            
        //   288: aload           4
        //   290: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   293: i2d            
        //   294: aload_3        
        //   295: getfield        net/minecraft/entity/item/EntityItemFrame.facingDirection:Lnet/minecraft/util/EnumFacing;
        //   298: invokevirtual   net/minecraft/util/EnumFacing.getHorizontalIndex:()I
        //   301: bipush          90
        //   303: imul           
        //   304: i2d            
        //   305: invokespecial   net/minecraft/world/storage/MapData.updateDecorations:(ILnet/minecraft/world/World;Ljava/lang/String;DDD)V
        //   308: aload_2        
        //   309: invokevirtual   net/minecraft/item/ItemStack.hasTagCompound:()Z
        //   312: ifeq            425
        //   315: aload_2        
        //   316: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   319: ldc             "Decorations"
        //   321: bipush          9
        //   323: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;I)Z
        //   326: ifeq            425
        //   329: aload_2        
        //   330: invokevirtual   net/minecraft/item/ItemStack.getTagCompound:()Lnet/minecraft/nbt/NBTTagCompound;
        //   333: ldc             "Decorations"
        //   335: bipush          10
        //   337: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   340: astore_3       
        //   341: iconst_0       
        //   342: aload_3        
        //   343: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   346: if_icmpge       425
        //   349: aload_3        
        //   350: iconst_0       
        //   351: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   354: astore          5
        //   356: aload_0        
        //   357: getfield        net/minecraft/world/storage/MapData.mapDecorations:Ljava/util/Map;
        //   360: aload           5
        //   362: ldc             "id"
        //   364: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   367: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   372: ifne            419
        //   375: aload_0        
        //   376: aload           5
        //   378: ldc             "type"
        //   380: invokevirtual   net/minecraft/nbt/NBTTagCompound.getByte:(Ljava/lang/String;)B
        //   383: aload_1        
        //   384: getfield        net/minecraft/entity/player/EntityPlayer.worldObj:Lnet/minecraft/world/World;
        //   387: aload           5
        //   389: ldc             "id"
        //   391: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   394: aload           5
        //   396: ldc             "x"
        //   398: invokevirtual   net/minecraft/nbt/NBTTagCompound.getDouble:(Ljava/lang/String;)D
        //   401: aload           5
        //   403: ldc             "z"
        //   405: invokevirtual   net/minecraft/nbt/NBTTagCompound.getDouble:(Ljava/lang/String;)D
        //   408: aload           5
        //   410: ldc_w           "rot"
        //   413: invokevirtual   net/minecraft/nbt/NBTTagCompound.getDouble:(Ljava/lang/String;)D
        //   416: invokespecial   net/minecraft/world/storage/MapData.updateDecorations:(ILnet/minecraft/world/World;Ljava/lang/String;DDD)V
        //   419: iinc            4, 1
        //   422: goto            341
        //   425: return         
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
    
    public MapData(final String s) {
        super(s);
        this.colors = new byte[16384];
        this.playersArrayList = Lists.newArrayList();
        this.playersHashMap = Maps.newHashMap();
        this.mapDecorations = Maps.newLinkedHashMap();
    }
    
    private void updateDecorations(final int n, final World world, final String s, final double n2, final double n3, double n4) {
        final int n5 = 1 << this.scale;
        final float n6 = (float)(n2 - this.xCenter) / n5;
        final float n7 = (float)(n3 - this.zCenter) / n5;
        byte b = (byte)(n6 * 2.0f + 0.5);
        byte b2 = (byte)(n7 * 2.0f + 0.5);
        if (n6 >= -63 && n7 >= -63 && n6 <= 63 && n7 <= 63) {
            n4 += ((n4 < 0.0) ? -8.0 : 8.0);
            final byte b3 = (byte)(n4 * 16.0 / 360.0);
            if (this.dimension < 0) {
                final int n8 = (int)(world.getWorldInfo().getWorldTime() / 10L);
                final byte b4 = (byte)(n8 * n8 * 34187121 + n8 * 121 >> 15 & 0xF);
            }
        }
        else {
            if (Math.abs(n6) >= 320.0f || Math.abs(n7) >= 320.0f) {
                this.mapDecorations.remove(s);
                return;
            }
            if (n6 <= -63) {
                b = (byte)(126 + 2.5);
            }
            if (n7 <= -63) {
                b2 = (byte)(126 + 2.5);
            }
            if (n6 >= 63) {
                b = 127;
            }
            if (n7 >= 63) {
                b2 = 127;
            }
        }
        this.mapDecorations.put(s, new Vec4b((byte)6, b, b2, (byte)0));
    }
    
    public void updateMapData(final int n, final int n2) {
        super.markDirty();
        final Iterator<MapInfo> iterator = this.playersArrayList.iterator();
        while (iterator.hasNext()) {
            iterator.next().update(n, n2);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.dimension = nbtTagCompound.getByte("dimension");
        this.xCenter = nbtTagCompound.getInteger("xCenter");
        this.zCenter = nbtTagCompound.getInteger("zCenter");
        this.scale = nbtTagCompound.getByte("scale");
        this.scale = (byte)MathHelper.clamp_int(this.scale, 0, 4);
        final short short1 = nbtTagCompound.getShort("width");
        final short short2 = nbtTagCompound.getShort("height");
        if (short1 == 128 && short2 == 128) {
            this.colors = nbtTagCompound.getByteArray("colors");
        }
        else {
            final byte[] byteArray = nbtTagCompound.getByteArray("colors");
            this.colors = new byte[16384];
            final int n = (128 - short1) / 2;
            final int n2 = (128 - short2) / 2;
            while (0 < short2) {
                final int n3 = 0 + n2;
                if (n3 >= 0 || n3 < 128) {
                    while (0 < short1) {
                        final int n4 = 0 + n;
                        if (n4 >= 0 || n4 < 128) {
                            this.colors[n4 + n3 * 128] = byteArray[0 + 0 * short1];
                        }
                        int n5 = 0;
                        ++n5;
                    }
                }
                int n6 = 0;
                ++n6;
            }
        }
    }
    
    public void calculateMapCenter(final double n, final double n2, final int n3) {
        final int n4 = 128 * (1 << n3);
        final int floor_double = MathHelper.floor_double((n + 64.0) / n4);
        final int floor_double2 = MathHelper.floor_double((n2 + 64.0) / n4);
        this.xCenter = floor_double * n4 + n4 / 2 - 64;
        this.zCenter = floor_double2 * n4 + n4 / 2 - 64;
    }
    
    public class MapInfo
    {
        private int maxY;
        final MapData this$0;
        private int minY;
        private int maxX;
        private int minX;
        public final EntityPlayer entityplayerObj;
        public int field_82569_d;
        private boolean field_176105_d;
        private int field_176109_i;
        
        public Packet getPacket(final ItemStack itemStack) {
            if (this.field_176105_d) {
                this.field_176105_d = false;
                return new S34PacketMaps(itemStack.getMetadata(), this.this$0.scale, this.this$0.mapDecorations.values(), this.this$0.colors, this.minX, this.minY, this.maxX + 1 - this.minX, this.maxY + 1 - this.minY);
            }
            return (this.field_176109_i++ % 5 == 0) ? new S34PacketMaps(itemStack.getMetadata(), this.this$0.scale, this.this$0.mapDecorations.values(), this.this$0.colors, 0, 0, 0, 0) : null;
        }
        
        public MapInfo(final MapData this$0, final EntityPlayer entityplayerObj) {
            this.this$0 = this$0;
            this.field_176105_d = true;
            this.minX = 0;
            this.minY = 0;
            this.maxX = 127;
            this.maxY = 127;
            this.entityplayerObj = entityplayerObj;
        }
        
        public void update(final int n, final int n2) {
            if (this.field_176105_d) {
                this.minX = Math.min(this.minX, n);
                this.minY = Math.min(this.minY, n2);
                this.maxX = Math.max(this.maxX, n);
                this.maxY = Math.max(this.maxY, n2);
            }
            else {
                this.field_176105_d = true;
                this.minX = n;
                this.minY = n2;
                this.maxX = n;
                this.maxY = n2;
            }
        }
    }
}
