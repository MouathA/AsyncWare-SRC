package net.minecraft.village;

import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.nbt.*;
import net.minecraft.server.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;

public class Village
{
    private World worldObj;
    private List villageAgressors;
    private int tickCounter;
    private int numIronGolems;
    private int numVillagers;
    private BlockPos centerHelper;
    private int villageRadius;
    private TreeMap playerReputation;
    private BlockPos center;
    private int noBreedTicks;
    private int lastAddDoorTimestamp;
    private final List villageDoorInfoList;
    
    public void tick(final int tickCounter) {
        this.tickCounter = tickCounter;
        this.removeDeadAndOutOfRangeDoors();
        this.removeDeadAndOldAgressors();
        if (tickCounter % 20 == 0) {
            this.updateNumVillagers();
        }
        if (tickCounter % 30 == 0) {
            this.updateNumIronGolems();
        }
        if (this.numIronGolems < this.numVillagers / 10 && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
            final Vec3 func_179862_a = this.func_179862_a(this.center, 2, 4, 2);
            if (func_179862_a != null) {
                final EntityIronGolem entityIronGolem = new EntityIronGolem(this.worldObj);
                entityIronGolem.setPosition(func_179862_a.xCoord, func_179862_a.yCoord, func_179862_a.zCoord);
                this.worldObj.spawnEntityInWorld(entityIronGolem);
                ++this.numIronGolems;
            }
        }
    }
    
    public void writeVillageDataToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("PopSize", this.numVillagers);
        nbtTagCompound.setInteger("Radius", this.villageRadius);
        nbtTagCompound.setInteger("Golems", this.numIronGolems);
        nbtTagCompound.setInteger("Stable", this.lastAddDoorTimestamp);
        nbtTagCompound.setInteger("Tick", this.tickCounter);
        nbtTagCompound.setInteger("MTick", this.noBreedTicks);
        nbtTagCompound.setInteger("CX", this.center.getX());
        nbtTagCompound.setInteger("CY", this.center.getY());
        nbtTagCompound.setInteger("CZ", this.center.getZ());
        nbtTagCompound.setInteger("ACX", this.centerHelper.getX());
        nbtTagCompound.setInteger("ACY", this.centerHelper.getY());
        nbtTagCompound.setInteger("ACZ", this.centerHelper.getZ());
        final NBTTagList list = new NBTTagList();
        for (final VillageDoorInfo villageDoorInfo : this.villageDoorInfoList) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            nbtTagCompound2.setInteger("X", villageDoorInfo.getDoorBlockPos().getX());
            nbtTagCompound2.setInteger("Y", villageDoorInfo.getDoorBlockPos().getY());
            nbtTagCompound2.setInteger("Z", villageDoorInfo.getDoorBlockPos().getZ());
            nbtTagCompound2.setInteger("IDX", villageDoorInfo.getInsideOffsetX());
            nbtTagCompound2.setInteger("IDZ", villageDoorInfo.getInsideOffsetZ());
            nbtTagCompound2.setInteger("TS", villageDoorInfo.getInsidePosY());
            list.appendTag(nbtTagCompound2);
        }
        nbtTagCompound.setTag("Doors", list);
        final NBTTagList list2 = new NBTTagList();
        for (final String s : this.playerReputation.keySet()) {
            final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
            final GameProfile gameProfileForUsername = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(s);
            if (gameProfileForUsername != null) {
                nbtTagCompound3.setString("UUID", gameProfileForUsername.getId().toString());
                nbtTagCompound3.setInteger("S", this.playerReputation.get(s));
                list2.appendTag(nbtTagCompound3);
            }
        }
        nbtTagCompound.setTag("Players", list2);
    }
    
    public List getVillageDoorInfoList() {
        return this.villageDoorInfoList;
    }
    
    public void setDefaultPlayerReputation(final int n) {
        final Iterator<String> iterator = this.playerReputation.keySet().iterator();
        while (iterator.hasNext()) {
            this.setReputationForPlayer(iterator.next(), n);
        }
    }
    
    private void removeDeadAndOldAgressors() {
        final Iterator<VillageAggressor> iterator = (Iterator<VillageAggressor>)this.villageAgressors.iterator();
        while (iterator.hasNext()) {
            final VillageAggressor villageAggressor = iterator.next();
            if (!villageAggressor.agressor.isEntityAlive() || Math.abs(this.tickCounter - villageAggressor.agressionTime) > 300) {
                iterator.remove();
            }
        }
    }
    
    private Vec3 func_179862_a(final BlockPos p0, final int p1, final int p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: getfield        net/minecraft/village/Village.worldObj:Lnet/minecraft/world/World;
        //     5: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //     8: bipush          16
        //    10: invokevirtual   java/util/Random.nextInt:(I)I
        //    13: bipush          8
        //    15: isub           
        //    16: aload_0        
        //    17: getfield        net/minecraft/village/Village.worldObj:Lnet/minecraft/world/World;
        //    20: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //    23: bipush          6
        //    25: invokevirtual   java/util/Random.nextInt:(I)I
        //    28: iconst_3       
        //    29: isub           
        //    30: aload_0        
        //    31: getfield        net/minecraft/village/Village.worldObj:Lnet/minecraft/world/World;
        //    34: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //    37: bipush          16
        //    39: invokevirtual   java/util/Random.nextInt:(I)I
        //    42: bipush          8
        //    44: isub           
        //    45: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    48: astore          6
        //    50: aload_0        
        //    51: aload           6
        //    53: ifge            99
        //    56: aload_0        
        //    57: new             Lnet/minecraft/util/BlockPos;
        //    60: dup            
        //    61: iload_2        
        //    62: iload_3        
        //    63: iload           4
        //    65: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    68: aload           6
        //    70: ifne            99
        //    73: new             Lnet/minecraft/util/Vec3;
        //    76: dup            
        //    77: aload           6
        //    79: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    82: i2d            
        //    83: aload           6
        //    85: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    88: i2d            
        //    89: aload           6
        //    91: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    94: i2d            
        //    95: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //    98: areturn        
        //    99: iinc            5, 1
        //   102: goto            0
        //   105: aconst_null    
        //   106: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0000 (coming from #0102).
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
    
    public void addVillageDoorInfo(final VillageDoorInfo villageDoorInfo) {
        this.villageDoorInfoList.add(villageDoorInfo);
        this.centerHelper = this.centerHelper.add(villageDoorInfo.getDoorBlockPos());
        this.updateVillageRadiusAndCenter();
        this.lastAddDoorTimestamp = villageDoorInfo.getInsidePosY();
    }
    
    private void updateNumIronGolems() {
        this.numIronGolems = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, new AxisAlignedBB(this.center.getX() - this.villageRadius, this.center.getY() - 4, this.center.getZ() - this.villageRadius, this.center.getX() + this.villageRadius, this.center.getY() + 4, this.center.getZ() + this.villageRadius)).size();
    }
    
    private void updateVillageRadiusAndCenter() {
        final int size = this.villageDoorInfoList.size();
        if (size == 0) {
            this.center = new BlockPos(0, 0, 0);
            this.villageRadius = 0;
        }
        else {
            this.center = new BlockPos(this.centerHelper.getX() / size, this.centerHelper.getY() / size, this.centerHelper.getZ() / size);
            final Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
            while (iterator.hasNext()) {
                Math.max(iterator.next().getDistanceToDoorBlockSq(this.center), 0);
            }
            this.villageRadius = Math.max(32, (int)Math.sqrt(0) + 1);
        }
    }
    
    private void removeDeadAndOutOfRangeDoors() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/village/Village.worldObj:Lnet/minecraft/world/World;
        //     4: getfield        net/minecraft/world/World.rand:Ljava/util/Random;
        //     7: bipush          50
        //     9: invokevirtual   java/util/Random.nextInt:(I)I
        //    12: ifne            19
        //    15: iconst_1       
        //    16: goto            20
        //    19: iconst_0       
        //    20: istore_2       
        //    21: aload_0        
        //    22: getfield        net/minecraft/village/Village.villageDoorInfoList:Ljava/util/List;
        //    25: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    30: astore_3       
        //    31: aload_3        
        //    32: invokeinterface java/util/Iterator.hasNext:()Z
        //    37: ifeq            119
        //    40: aload_3        
        //    41: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    46: checkcast       Lnet/minecraft/village/VillageDoorInfo;
        //    49: astore          4
        //    51: iload_2        
        //    52: ifeq            60
        //    55: aload           4
        //    57: invokevirtual   net/minecraft/village/VillageDoorInfo.resetDoorOpeningRestrictionCounter:()V
        //    60: aload_0        
        //    61: aload           4
        //    63: invokevirtual   net/minecraft/village/VillageDoorInfo.getDoorBlockPos:()Lnet/minecraft/util/BlockPos;
        //    66: ifeq            88
        //    69: aload_0        
        //    70: getfield        net/minecraft/village/Village.tickCounter:I
        //    73: aload           4
        //    75: invokevirtual   net/minecraft/village/VillageDoorInfo.getInsidePosY:()I
        //    78: isub           
        //    79: invokestatic    java/lang/Math.abs:(I)I
        //    82: sipush          1200
        //    85: if_icmple       116
        //    88: aload_0        
        //    89: aload_0        
        //    90: getfield        net/minecraft/village/Village.centerHelper:Lnet/minecraft/util/BlockPos;
        //    93: aload           4
        //    95: invokevirtual   net/minecraft/village/VillageDoorInfo.getDoorBlockPos:()Lnet/minecraft/util/BlockPos;
        //    98: invokevirtual   net/minecraft/util/BlockPos.subtract:(Lnet/minecraft/util/Vec3i;)Lnet/minecraft/util/BlockPos;
        //   101: putfield        net/minecraft/village/Village.centerHelper:Lnet/minecraft/util/BlockPos;
        //   104: aload           4
        //   106: iconst_1       
        //   107: invokevirtual   net/minecraft/village/VillageDoorInfo.setIsDetachedFromVillageFlag:(Z)V
        //   110: aload_3        
        //   111: invokeinterface java/util/Iterator.remove:()V
        //   116: goto            31
        //   119: aload_0        
        //   120: invokespecial   net/minecraft/village/Village.updateVillageRadiusAndCenter:()V
        //   123: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0031 (coming from #0116).
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
    
    public EntityLivingBase findNearestVillageAggressor(final EntityLivingBase entityLivingBase) {
        double n = Double.MAX_VALUE;
        VillageAggressor villageAggressor = null;
        while (0 < this.villageAgressors.size()) {
            final VillageAggressor villageAggressor2 = this.villageAgressors.get(0);
            final double distanceSqToEntity = villageAggressor2.agressor.getDistanceSqToEntity(entityLivingBase);
            if (distanceSqToEntity <= n) {
                villageAggressor = villageAggressor2;
                n = distanceSqToEntity;
            }
            int n2 = 0;
            ++n2;
        }
        return (villageAggressor != null) ? villageAggressor.agressor : null;
    }
    
    public boolean isAnnihilated() {
        return this.villageDoorInfoList.isEmpty();
    }
    
    public int getNumVillagers() {
        return this.numVillagers;
    }
    
    public int getNumVillageDoors() {
        return this.villageDoorInfoList.size();
    }
    
    public int getReputationForPlayer(final String s) {
        final Integer n = this.playerReputation.get(s);
        return (n != null) ? n : 0;
    }
    
    public VillageDoorInfo getDoorInfo(final BlockPos blockPos) {
        VillageDoorInfo villageDoorInfo = null;
        for (final VillageDoorInfo villageDoorInfo2 : this.villageDoorInfoList) {
            final int distanceToDoorBlockSq = villageDoorInfo2.getDistanceToDoorBlockSq(blockPos);
            int doorOpeningRestrictionCounter;
            if (distanceToDoorBlockSq > 256) {
                doorOpeningRestrictionCounter = distanceToDoorBlockSq * 1000;
            }
            else {
                doorOpeningRestrictionCounter = villageDoorInfo2.getDoorOpeningRestrictionCounter();
            }
            if (doorOpeningRestrictionCounter < Integer.MAX_VALUE) {
                villageDoorInfo = villageDoorInfo2;
            }
        }
        return villageDoorInfo;
    }
    
    private void updateNumVillagers() {
        this.numVillagers = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB(this.center.getX() - this.villageRadius, this.center.getY() - 4, this.center.getZ() - this.villageRadius, this.center.getX() + this.villageRadius, this.center.getY() + 4, this.center.getZ() + this.villageRadius)).size();
        if (this.numVillagers == 0) {
            this.playerReputation.clear();
        }
    }
    
    public boolean isMatingSeason() {
        return this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600;
    }
    
    public int getVillageRadius() {
        return this.villageRadius;
    }
    
    public int setReputationForPlayer(final String s, final int n) {
        final int clamp_int = MathHelper.clamp_int(this.getReputationForPlayer(s) + n, -30, 10);
        this.playerReputation.put(s, clamp_int);
        return clamp_int;
    }
    
    public Village() {
        this.villageDoorInfoList = Lists.newArrayList();
        this.centerHelper = BlockPos.ORIGIN;
        this.center = BlockPos.ORIGIN;
        this.playerReputation = new TreeMap();
        this.villageAgressors = Lists.newArrayList();
    }
    
    public EntityPlayer getNearestTargetPlayer(final EntityLivingBase entityLivingBase) {
        double n = Double.MAX_VALUE;
        EntityPlayer entityPlayer = null;
        for (final String s : this.playerReputation.keySet()) {
            if (this <= s) {
                final EntityPlayer playerEntityByName = this.worldObj.getPlayerEntityByName(s);
                if (playerEntityByName == null) {
                    continue;
                }
                final double distanceSqToEntity = playerEntityByName.getDistanceSqToEntity(entityLivingBase);
                if (distanceSqToEntity > n) {
                    continue;
                }
                entityPlayer = playerEntityByName;
                n = distanceSqToEntity;
            }
        }
        return entityPlayer;
    }
    
    public void setWorld(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public void endMatingSeason() {
        this.noBreedTicks = this.tickCounter;
    }
    
    public void addOrRenewAgressor(final EntityLivingBase entityLivingBase) {
        for (final VillageAggressor villageAggressor : this.villageAgressors) {
            if (villageAggressor.agressor == entityLivingBase) {
                villageAggressor.agressionTime = this.tickCounter;
                return;
            }
        }
        this.villageAgressors.add(new VillageAggressor(entityLivingBase, this.tickCounter));
    }
    
    public VillageDoorInfo getNearestDoor(final BlockPos blockPos) {
        VillageDoorInfo villageDoorInfo = null;
        for (final VillageDoorInfo villageDoorInfo2 : this.villageDoorInfoList) {
            if (villageDoorInfo2.getDistanceToDoorBlockSq(blockPos) < Integer.MAX_VALUE) {
                villageDoorInfo = villageDoorInfo2;
            }
        }
        return villageDoorInfo;
    }
    
    public void readVillageDataFromNBT(final NBTTagCompound p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: ldc             "PopSize"
        //     4: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //     7: putfield        net/minecraft/village/Village.numVillagers:I
        //    10: aload_0        
        //    11: aload_1        
        //    12: ldc             "Radius"
        //    14: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    17: putfield        net/minecraft/village/Village.villageRadius:I
        //    20: aload_0        
        //    21: aload_1        
        //    22: ldc             "Golems"
        //    24: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    27: putfield        net/minecraft/village/Village.numIronGolems:I
        //    30: aload_0        
        //    31: aload_1        
        //    32: ldc             "Stable"
        //    34: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    37: putfield        net/minecraft/village/Village.lastAddDoorTimestamp:I
        //    40: aload_0        
        //    41: aload_1        
        //    42: ldc             "Tick"
        //    44: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    47: putfield        net/minecraft/village/Village.tickCounter:I
        //    50: aload_0        
        //    51: aload_1        
        //    52: ldc             "MTick"
        //    54: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    57: putfield        net/minecraft/village/Village.noBreedTicks:I
        //    60: aload_0        
        //    61: new             Lnet/minecraft/util/BlockPos;
        //    64: dup            
        //    65: aload_1        
        //    66: ldc             "CX"
        //    68: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    71: aload_1        
        //    72: ldc             "CY"
        //    74: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    77: aload_1        
        //    78: ldc             "CZ"
        //    80: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //    83: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    86: putfield        net/minecraft/village/Village.center:Lnet/minecraft/util/BlockPos;
        //    89: aload_0        
        //    90: new             Lnet/minecraft/util/BlockPos;
        //    93: dup            
        //    94: aload_1        
        //    95: ldc             "ACX"
        //    97: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   100: aload_1        
        //   101: ldc             "ACY"
        //   103: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   106: aload_1        
        //   107: ldc             "ACZ"
        //   109: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   112: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   115: putfield        net/minecraft/village/Village.centerHelper:Lnet/minecraft/util/BlockPos;
        //   118: aload_1        
        //   119: ldc             "Doors"
        //   121: bipush          10
        //   123: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   126: astore_2       
        //   127: iconst_0       
        //   128: aload_2        
        //   129: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   132: if_icmpge       218
        //   135: aload_2        
        //   136: iconst_0       
        //   137: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   140: astore          4
        //   142: new             Lnet/minecraft/village/VillageDoorInfo;
        //   145: dup            
        //   146: new             Lnet/minecraft/util/BlockPos;
        //   149: dup            
        //   150: aload           4
        //   152: ldc             "X"
        //   154: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   157: aload           4
        //   159: ldc             "Y"
        //   161: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   164: aload           4
        //   166: ldc             "Z"
        //   168: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   171: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   174: aload           4
        //   176: ldc             "IDX"
        //   178: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   181: aload           4
        //   183: ldc             "IDZ"
        //   185: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   188: aload           4
        //   190: ldc             "TS"
        //   192: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   195: invokespecial   net/minecraft/village/VillageDoorInfo.<init>:(Lnet/minecraft/util/BlockPos;III)V
        //   198: astore          5
        //   200: aload_0        
        //   201: getfield        net/minecraft/village/Village.villageDoorInfoList:Ljava/util/List;
        //   204: aload           5
        //   206: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   211: pop            
        //   212: iinc            3, 1
        //   215: goto            127
        //   218: aload_1        
        //   219: ldc_w           "Players"
        //   222: bipush          10
        //   224: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   227: astore_3       
        //   228: iconst_0       
        //   229: aload_3        
        //   230: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   233: if_icmpge       343
        //   236: aload_3        
        //   237: iconst_0       
        //   238: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   241: astore          5
        //   243: aload           5
        //   245: ldc             "UUID"
        //   247: invokevirtual   net/minecraft/nbt/NBTTagCompound.hasKey:(Ljava/lang/String;)Z
        //   250: ifeq            310
        //   253: invokestatic    net/minecraft/server/MinecraftServer.getServer:()Lnet/minecraft/server/MinecraftServer;
        //   256: invokevirtual   net/minecraft/server/MinecraftServer.getPlayerProfileCache:()Lnet/minecraft/server/management/PlayerProfileCache;
        //   259: astore          6
        //   261: aload           6
        //   263: aload           5
        //   265: ldc             "UUID"
        //   267: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   270: invokestatic    java/util/UUID.fromString:(Ljava/lang/String;)Ljava/util/UUID;
        //   273: invokevirtual   net/minecraft/server/management/PlayerProfileCache.getProfileByUUID:(Ljava/util/UUID;)Lcom/mojang/authlib/GameProfile;
        //   276: astore          7
        //   278: aload           7
        //   280: ifnull          307
        //   283: aload_0        
        //   284: getfield        net/minecraft/village/Village.playerReputation:Ljava/util/TreeMap;
        //   287: aload           7
        //   289: invokevirtual   com/mojang/authlib/GameProfile.getName:()Ljava/lang/String;
        //   292: aload           5
        //   294: ldc_w           "S"
        //   297: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   300: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   303: invokevirtual   java/util/TreeMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   306: pop            
        //   307: goto            337
        //   310: aload_0        
        //   311: getfield        net/minecraft/village/Village.playerReputation:Ljava/util/TreeMap;
        //   314: aload           5
        //   316: ldc_w           "Name"
        //   319: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   322: aload           5
        //   324: ldc_w           "S"
        //   327: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   330: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   333: invokevirtual   java/util/TreeMap.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   336: pop            
        //   337: iinc            4, 1
        //   340: goto            228
        //   343: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public Village(final World worldObj) {
        this.villageDoorInfoList = Lists.newArrayList();
        this.centerHelper = BlockPos.ORIGIN;
        this.center = BlockPos.ORIGIN;
        this.playerReputation = new TreeMap();
        this.villageAgressors = Lists.newArrayList();
        this.worldObj = worldObj;
    }
    
    public int getTicksSinceLastDoorAdding() {
        return this.tickCounter - this.lastAddDoorTimestamp;
    }
    
    public VillageDoorInfo getExistedDoor(final BlockPos blockPos) {
        if (this.center.distanceSq(blockPos) > this.villageRadius * this.villageRadius) {
            return null;
        }
        for (final VillageDoorInfo villageDoorInfo : this.villageDoorInfoList) {
            if (villageDoorInfo.getDoorBlockPos().getX() == blockPos.getX() && villageDoorInfo.getDoorBlockPos().getZ() == blockPos.getZ() && Math.abs(villageDoorInfo.getDoorBlockPos().getY() - blockPos.getY()) <= 1) {
                return villageDoorInfo;
            }
        }
        return null;
    }
    
    public BlockPos getCenter() {
        return this.center;
    }
    
    class VillageAggressor
    {
        public int agressionTime;
        public EntityLivingBase agressor;
        final Village this$0;
        
        VillageAggressor(final Village this$0, final EntityLivingBase agressor, final int agressionTime) {
            this.this$0 = this$0;
            this.agressor = agressor;
            this.agressionTime = agressionTime;
        }
    }
}
