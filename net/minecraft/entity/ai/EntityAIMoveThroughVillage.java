package net.minecraft.entity.ai;

import java.util.*;
import net.minecraft.village.*;
import com.google.common.collect.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIMoveThroughVillage extends EntityAIBase
{
    private PathEntity entityPathNavigate;
    private List doorList;
    private VillageDoorInfo doorInfo;
    private boolean isNocturnal;
    private double movementSpeed;
    private EntityCreature theEntity;
    
    private VillageDoorInfo findNearestDoor(final Village p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore_2       
        //     2: aload_1        
        //     3: invokevirtual   net/minecraft/village/Village.getVillageDoorInfoList:()Ljava/util/List;
        //     6: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    11: astore          4
        //    13: aload           4
        //    15: invokeinterface java/util/Iterator.hasNext:()Z
        //    20: ifeq            94
        //    23: aload           4
        //    25: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    30: checkcast       Lnet/minecraft/village/VillageDoorInfo;
        //    33: astore          5
        //    35: aload           5
        //    37: aload_0        
        //    38: getfield        net/minecraft/entity/ai/EntityAIMoveThroughVillage.theEntity:Lnet/minecraft/entity/EntityCreature;
        //    41: getfield        net/minecraft/entity/EntityCreature.posX:D
        //    44: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    47: aload_0        
        //    48: getfield        net/minecraft/entity/ai/EntityAIMoveThroughVillage.theEntity:Lnet/minecraft/entity/EntityCreature;
        //    51: getfield        net/minecraft/entity/EntityCreature.posY:D
        //    54: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    57: aload_0        
        //    58: getfield        net/minecraft/entity/ai/EntityAIMoveThroughVillage.theEntity:Lnet/minecraft/entity/EntityCreature;
        //    61: getfield        net/minecraft/entity/EntityCreature.posZ:D
        //    64: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    67: invokevirtual   net/minecraft/village/VillageDoorInfo.getDistanceSquared:(III)I
        //    70: istore          6
        //    72: iload           6
        //    74: ldc             2147483647
        //    76: if_icmpge       91
        //    79: aload_0        
        //    80: aload           5
        //    82: ifeq            91
        //    85: aload           5
        //    87: astore_2       
        //    88: iload           6
        //    90: istore_3       
        //    91: goto            13
        //    94: aload_2        
        //    95: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0091 (coming from #0082).
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
    
    public EntityAIMoveThroughVillage(final EntityCreature theEntity, final double movementSpeed, final boolean isNocturnal) {
        this.doorList = Lists.newArrayList();
        this.theEntity = theEntity;
        this.movementSpeed = movementSpeed;
        this.isNocturnal = isNocturnal;
        this.setMutexBits(1);
        if (!(theEntity.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
        }
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
    }
    
    @Override
    public void resetTask() {
        if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) < 16.0) {
            this.doorList.add(this.doorInfo);
        }
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.theEntity.getNavigator().noPath()) {
            return false;
        }
        final float n = this.theEntity.width + 4.0f;
        return this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) > n * n;
    }
    
    private void resizeDoorList() {
        if (this.doorList.size() > 15) {
            this.doorList.remove(0);
        }
    }
    
    @Override
    public boolean shouldExecute() {
        this.resizeDoorList();
        if (this.isNocturnal && this.theEntity.worldObj.isDaytime()) {
            return false;
        }
        final Village nearestVillage = this.theEntity.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this.theEntity), 0);
        if (nearestVillage == null) {
            return false;
        }
        this.doorInfo = this.findNearestDoor(nearestVillage);
        if (this.doorInfo == null) {
            return false;
        }
        final PathNavigateGround pathNavigateGround = (PathNavigateGround)this.theEntity.getNavigator();
        final boolean enterDoors = pathNavigateGround.getEnterDoors();
        pathNavigateGround.setBreakDoors(false);
        this.entityPathNavigate = pathNavigateGround.getPathToPos(this.doorInfo.getDoorBlockPos());
        pathNavigateGround.setBreakDoors(enterDoors);
        if (this.entityPathNavigate != null) {
            return true;
        }
        final Vec3 randomTargetBlockTowards = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, new Vec3(this.doorInfo.getDoorBlockPos().getX(), this.doorInfo.getDoorBlockPos().getY(), this.doorInfo.getDoorBlockPos().getZ()));
        if (randomTargetBlockTowards == null) {
            return false;
        }
        pathNavigateGround.setBreakDoors(false);
        this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(randomTargetBlockTowards.xCoord, randomTargetBlockTowards.yCoord, randomTargetBlockTowards.zCoord);
        pathNavigateGround.setBreakDoors(enterDoors);
        return this.entityPathNavigate != null;
    }
}
