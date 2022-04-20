package net.minecraft.village;

import net.minecraft.nbt.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class VillageCollection extends WorldSavedData
{
    private final List villageList;
    private int tickCounter;
    private final List villagerPositionsList;
    private World worldObj;
    private final List newDoors;
    
    public void addToVillagerPositionList(final BlockPos p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/village/VillageCollection.villagerPositionsList:Ljava/util/List;
        //     4: invokeinterface java/util/List.size:()I
        //     9: bipush          64
        //    11: if_icmpgt       30
        //    14: aload_0        
        //    15: aload_1        
        //    16: ifeq            30
        //    19: aload_0        
        //    20: getfield        net/minecraft/village/VillageCollection.villagerPositionsList:Ljava/util/List;
        //    23: aload_1        
        //    24: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    29: pop            
        //    30: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0030 (coming from #0016).
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
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("Tick", this.tickCounter);
        final NBTTagList list = new NBTTagList();
        for (final Village village : this.villageList) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            village.writeVillageDataToNBT(nbtTagCompound2);
            list.appendTag(nbtTagCompound2);
        }
        nbtTagCompound.setTag("Villages", list);
    }
    
    public void setWorldsForAll(final World world) {
        this.worldObj = world;
        final Iterator<Village> iterator = this.villageList.iterator();
        while (iterator.hasNext()) {
            iterator.next().setWorld(world);
        }
    }
    
    private void removeAnnihilatedVillages() {
        final Iterator<Village> iterator = (Iterator<Village>)this.villageList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isAnnihilated()) {
                iterator.remove();
                this.markDirty();
            }
        }
    }
    
    public void tick() {
        ++this.tickCounter;
        final Iterator<Village> iterator = this.villageList.iterator();
        while (iterator.hasNext()) {
            iterator.next().tick(this.tickCounter);
        }
        this.removeAnnihilatedVillages();
        this.dropOldestVillagerPosition();
        this.addNewDoorsToVillageOrCreateVillage();
        if (this.tickCounter % 400 == 0) {
            this.markDirty();
        }
    }
    
    public VillageCollection(final String s) {
        super(s);
        this.villagerPositionsList = Lists.newArrayList();
        this.newDoors = Lists.newArrayList();
        this.villageList = Lists.newArrayList();
    }
    
    private void addToNewDoorsList(final BlockPos blockPos) {
        final EnumFacing facing = BlockDoor.getFacing(this.worldObj, blockPos);
        final EnumFacing opposite = facing.getOpposite();
        final int countBlocksCanSeeSky = this.countBlocksCanSeeSky(blockPos, facing, 5);
        final int countBlocksCanSeeSky2 = this.countBlocksCanSeeSky(blockPos, opposite, countBlocksCanSeeSky + 1);
        if (countBlocksCanSeeSky != countBlocksCanSeeSky2) {
            this.newDoors.add(new VillageDoorInfo(blockPos, (countBlocksCanSeeSky < countBlocksCanSeeSky2) ? facing : opposite, this.tickCounter));
        }
    }
    
    public VillageCollection(final World worldObj) {
        super(fileNameForProvider(worldObj.provider));
        this.villagerPositionsList = Lists.newArrayList();
        this.newDoors = Lists.newArrayList();
        this.villageList = Lists.newArrayList();
        this.worldObj = worldObj;
        this.markDirty();
    }
    
    private void dropOldestVillagerPosition() {
        if (!this.villagerPositionsList.isEmpty()) {
            this.addDoorsAround(this.villagerPositionsList.remove(0));
        }
    }
    
    private void addDoorsAround(final BlockPos p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: bipush          -16
        //     3: bipush          -4
        //     5: bipush          -16
        //     7: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    10: astore          8
        //    12: aload_0        
        //    13: aload           8
        //    15: ifeq            49
        //    18: aload_0        
        //    19: aload           8
        //    21: invokespecial   net/minecraft/village/VillageCollection.checkDoorExistence:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/village/VillageDoorInfo;
        //    24: astore          9
        //    26: aload           9
        //    28: ifnonnull       40
        //    31: aload_0        
        //    32: aload           8
        //    34: invokespecial   net/minecraft/village/VillageCollection.addToNewDoorsList:(Lnet/minecraft/util/BlockPos;)V
        //    37: goto            49
        //    40: aload           9
        //    42: aload_0        
        //    43: getfield        net/minecraft/village/VillageCollection.tickCounter:I
        //    46: invokevirtual   net/minecraft/village/VillageDoorInfo.func_179849_a:(I)V
        //    49: iinc            7, 1
        //    52: goto            0
        //    55: iinc            6, 1
        //    58: goto            0
        //    61: iinc            5, 1
        //    64: goto            0
        //    67: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0000 (coming from #0052).
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
    
    private void addNewDoorsToVillageOrCreateVillage() {
        while (0 < this.newDoors.size()) {
            final VillageDoorInfo villageDoorInfo = this.newDoors.get(0);
            Village nearestVillage = this.getNearestVillage(villageDoorInfo.getDoorBlockPos(), 32);
            if (nearestVillage == null) {
                nearestVillage = new Village(this.worldObj);
                this.villageList.add(nearestVillage);
                this.markDirty();
            }
            nearestVillage.addVillageDoorInfo(villageDoorInfo);
            int n = 0;
            ++n;
        }
        this.newDoors.clear();
    }
    
    public List getVillageList() {
        return this.villageList;
    }
    
    public static String fileNameForProvider(final WorldProvider worldProvider) {
        return "villages" + worldProvider.getInternalNameSuffix();
    }
    
    public Village getNearestVillage(final BlockPos blockPos, final int n) {
        Village village = null;
        double n2 = 3.4028234663852886E38;
        for (final Village village2 : this.villageList) {
            final double distanceSq = village2.getCenter().distanceSq(blockPos);
            if (distanceSq < n2) {
                final float n3 = (float)(n + village2.getVillageRadius());
                if (distanceSq > n3 * n3) {
                    continue;
                }
                village = village2;
                n2 = distanceSq;
            }
        }
        return village;
    }
    
    private VillageDoorInfo checkDoorExistence(final BlockPos blockPos) {
        for (final VillageDoorInfo villageDoorInfo : this.newDoors) {
            if (villageDoorInfo.getDoorBlockPos().getX() == blockPos.getX() && villageDoorInfo.getDoorBlockPos().getZ() == blockPos.getZ() && Math.abs(villageDoorInfo.getDoorBlockPos().getY() - blockPos.getY()) <= 1) {
                return villageDoorInfo;
            }
        }
        final Iterator<Village> iterator2 = this.villageList.iterator();
        while (iterator2.hasNext()) {
            final VillageDoorInfo existedDoor = iterator2.next().getExistedDoor(blockPos);
            if (existedDoor != null) {
                return existedDoor;
            }
        }
        return null;
    }
    
    private int countBlocksCanSeeSky(final BlockPos blockPos, final EnumFacing enumFacing, final int n) {
        while (true) {
            if (this.worldObj.canSeeSky(blockPos.offset(enumFacing, 1))) {
                int n2 = 0;
                ++n2;
                if (0 >= n) {
                    break;
                }
            }
            int n3 = 0;
            ++n3;
        }
        return 0;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.tickCounter = nbtTagCompound.getInteger("Tick");
        final NBTTagList tagList = nbtTagCompound.getTagList("Villages", 10);
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final Village village = new Village();
            village.readVillageDataFromNBT(compoundTag);
            this.villageList.add(village);
            int n = 0;
            ++n;
        }
    }
}
