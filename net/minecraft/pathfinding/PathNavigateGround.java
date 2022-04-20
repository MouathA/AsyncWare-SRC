package net.minecraft.pathfinding;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.pathfinder.*;
import net.minecraft.util.*;

public class PathNavigateGround extends PathNavigate
{
    protected WalkNodeProcessor nodeProcessor;
    private boolean shouldAvoidSun;
    
    public boolean getEnterDoors() {
        return this.nodeProcessor.getEnterDoors();
    }
    
    public PathNavigateGround(final EntityLiving entityLiving, final World world) {
        super(entityLiving, world);
    }
    
    public boolean getCanSwim() {
        return this.nodeProcessor.getCanSwim();
    }
    
    public void setCanSwim(final boolean canSwim) {
        this.nodeProcessor.setCanSwim(canSwim);
    }
    
    public boolean getAvoidsWater() {
        return this.nodeProcessor.getAvoidsWater();
    }
    
    @Override
    protected boolean canNavigate() {
        return this.theEntity.onGround || (this.getCanSwim() && this.isInLiquid()) || (this.theEntity.isRiding() && this.theEntity instanceof EntityZombie && this.theEntity.ridingEntity instanceof EntityChicken);
    }
    
    public void setEnterDoors(final boolean enterDoors) {
        this.nodeProcessor.setEnterDoors(enterDoors);
    }
    
    public void setAvoidSun(final boolean shouldAvoidSun) {
        this.shouldAvoidSun = shouldAvoidSun;
    }
    
    public void setBreakDoors(final boolean breakDoors) {
        this.nodeProcessor.setBreakDoors(breakDoors);
    }
    
    private int getPathablePosY() {
        if (!this.theEntity.isInWater() || !this.getCanSwim()) {
            return (int)(this.theEntity.getEntityBoundingBox().minY + 0.5);
        }
        int n = (int)this.theEntity.getEntityBoundingBox().minY;
        final Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), n, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
        if (block == Blocks.flowing_water || block == Blocks.water) {
            ++n;
            this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), n, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
            int n2 = 0;
            ++n2;
            return (int)this.theEntity.getEntityBoundingBox().minY;
        }
        return n;
    }
    
    @Override
    protected void removeSunnyPath() {
        super.removeSunnyPath();
        if (this.shouldAvoidSun) {
            if (this.worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.getEntityBoundingBox().minY + 0.5), MathHelper.floor_double(this.theEntity.posZ)))) {
                return;
            }
            while (0 < this.currentPath.getCurrentPathLength()) {
                final PathPoint pathPointFromIndex = this.currentPath.getPathPointFromIndex(0);
                if (this.worldObj.canSeeSky(new BlockPos(pathPointFromIndex.xCoord, pathPointFromIndex.yCoord, pathPointFromIndex.zCoord))) {
                    this.currentPath.setCurrentPathLength(-1);
                    return;
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    @Override
    protected PathFinder getPathFinder() {
        (this.nodeProcessor = new WalkNodeProcessor()).setEnterDoors(true);
        return new PathFinder(this.nodeProcessor);
    }
    
    @Override
    protected boolean isDirectPathBetweenPoints(final Vec3 p0, final Vec3 p1, final int p2, final int p3, final int p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/util/Vec3.xCoord:D
        //     4: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //     7: istore          6
        //     9: aload_1        
        //    10: getfield        net/minecraft/util/Vec3.zCoord:D
        //    13: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    16: istore          7
        //    18: aload_2        
        //    19: getfield        net/minecraft/util/Vec3.xCoord:D
        //    22: aload_1        
        //    23: getfield        net/minecraft/util/Vec3.xCoord:D
        //    26: dsub           
        //    27: dstore          8
        //    29: aload_2        
        //    30: getfield        net/minecraft/util/Vec3.zCoord:D
        //    33: aload_1        
        //    34: getfield        net/minecraft/util/Vec3.zCoord:D
        //    37: dsub           
        //    38: dstore          10
        //    40: dload           8
        //    42: dload           8
        //    44: dmul           
        //    45: dload           10
        //    47: dload           10
        //    49: dmul           
        //    50: dadd           
        //    51: dstore          12
        //    53: dload           12
        //    55: ldc2_w          1.0E-8
        //    58: dcmpg          
        //    59: ifge            64
        //    62: iconst_0       
        //    63: ireturn        
        //    64: dconst_1       
        //    65: dload           12
        //    67: invokestatic    java/lang/Math.sqrt:(D)D
        //    70: ddiv           
        //    71: dstore          14
        //    73: dload           8
        //    75: dload           14
        //    77: dmul           
        //    78: dstore          8
        //    80: dload           10
        //    82: dload           14
        //    84: dmul           
        //    85: dstore          10
        //    87: iload_3        
        //    88: iconst_2       
        //    89: iadd           
        //    90: istore_3       
        //    91: iload           5
        //    93: iconst_2       
        //    94: iadd           
        //    95: istore          5
        //    97: aload_0        
        //    98: iload           6
        //   100: aload_1        
        //   101: getfield        net/minecraft/util/Vec3.yCoord:D
        //   104: d2i            
        //   105: iload           7
        //   107: iload_3        
        //   108: iload           4
        //   110: iload           5
        //   112: aload_1        
        //   113: dload           8
        //   115: dload           10
        //   117: ifeq            122
        //   120: iconst_0       
        //   121: ireturn        
        //   122: iload_3        
        //   123: iconst_2       
        //   124: isub           
        //   125: istore_3       
        //   126: iload           5
        //   128: iconst_2       
        //   129: isub           
        //   130: istore          5
        //   132: dconst_1       
        //   133: dload           8
        //   135: invokestatic    java/lang/Math.abs:(D)D
        //   138: ddiv           
        //   139: dstore          16
        //   141: dconst_1       
        //   142: dload           10
        //   144: invokestatic    java/lang/Math.abs:(D)D
        //   147: ddiv           
        //   148: dstore          18
        //   150: iload           6
        //   152: iconst_1       
        //   153: imul           
        //   154: i2d            
        //   155: aload_1        
        //   156: getfield        net/minecraft/util/Vec3.xCoord:D
        //   159: dsub           
        //   160: dstore          20
        //   162: iload           7
        //   164: iconst_1       
        //   165: imul           
        //   166: i2d            
        //   167: aload_1        
        //   168: getfield        net/minecraft/util/Vec3.zCoord:D
        //   171: dsub           
        //   172: dstore          22
        //   174: dload           8
        //   176: dconst_0       
        //   177: dcmpl          
        //   178: iflt            187
        //   181: dload           20
        //   183: dconst_1       
        //   184: dadd           
        //   185: dstore          20
        //   187: dload           10
        //   189: dconst_0       
        //   190: dcmpl          
        //   191: iflt            200
        //   194: dload           22
        //   196: dconst_1       
        //   197: dadd           
        //   198: dstore          22
        //   200: dload           20
        //   202: dload           8
        //   204: ddiv           
        //   205: dstore          20
        //   207: dload           22
        //   209: dload           10
        //   211: ddiv           
        //   212: dstore          22
        //   214: dload           8
        //   216: dconst_0       
        //   217: dcmpg          
        //   218: ifge            225
        //   221: iconst_m1      
        //   222: goto            226
        //   225: iconst_1       
        //   226: istore          24
        //   228: dload           10
        //   230: dconst_0       
        //   231: dcmpg          
        //   232: ifge            239
        //   235: iconst_m1      
        //   236: goto            240
        //   239: iconst_1       
        //   240: istore          25
        //   242: aload_2        
        //   243: getfield        net/minecraft/util/Vec3.xCoord:D
        //   246: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   249: istore          26
        //   251: aload_2        
        //   252: getfield        net/minecraft/util/Vec3.zCoord:D
        //   255: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   258: istore          27
        //   260: iload           26
        //   262: iload           6
        //   264: isub           
        //   265: istore          28
        //   267: iload           27
        //   269: iload           7
        //   271: isub           
        //   272: istore          29
        //   274: iload           28
        //   276: iload           24
        //   278: imul           
        //   279: ifgt            290
        //   282: iload           29
        //   284: iload           25
        //   286: imul           
        //   287: ifle            368
        //   290: dload           20
        //   292: dload           22
        //   294: dcmpg          
        //   295: ifge            322
        //   298: dload           20
        //   300: dload           16
        //   302: dadd           
        //   303: dstore          20
        //   305: iload           6
        //   307: iload           24
        //   309: iadd           
        //   310: istore          6
        //   312: iload           26
        //   314: iload           6
        //   316: isub           
        //   317: istore          28
        //   319: goto            343
        //   322: dload           22
        //   324: dload           18
        //   326: dadd           
        //   327: dstore          22
        //   329: iload           7
        //   331: iload           25
        //   333: iadd           
        //   334: istore          7
        //   336: iload           27
        //   338: iload           7
        //   340: isub           
        //   341: istore          29
        //   343: aload_0        
        //   344: iload           6
        //   346: aload_1        
        //   347: getfield        net/minecraft/util/Vec3.yCoord:D
        //   350: d2i            
        //   351: iload           7
        //   353: iload_3        
        //   354: iload           4
        //   356: iload           5
        //   358: aload_1        
        //   359: dload           8
        //   361: dload           10
        //   363: ifeq            274
        //   366: iconst_0       
        //   367: ireturn        
        //   368: iconst_1       
        //   369: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0274 (coming from #0363).
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
    protected Vec3 getEntityPosition() {
        return new Vec3(this.theEntity.posX, this.getPathablePosY(), this.theEntity.posZ);
    }
    
    public void setAvoidsWater(final boolean avoidsWater) {
        this.nodeProcessor.setAvoidsWater(avoidsWater);
    }
}
