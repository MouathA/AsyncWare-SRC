package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RandomPositionGenerator
{
    private static Vec3 staticVector;
    
    static {
        RandomPositionGenerator.staticVector = new Vec3(0.0, 0.0, 0.0);
    }
    
    public static Vec3 findRandomTargetBlockTowards(final EntityCreature entityCreature, final int n, final int n2, final Vec3 vec3) {
        RandomPositionGenerator.staticVector = vec3.subtract(entityCreature.posX, entityCreature.posY, entityCreature.posZ);
        return findRandomTargetBlock(entityCreature, n, n2, RandomPositionGenerator.staticVector);
    }
    
    public static Vec3 findRandomTargetBlockAwayFrom(final EntityCreature entityCreature, final int n, final int n2, final Vec3 vec3) {
        RandomPositionGenerator.staticVector = new Vec3(entityCreature.posX, entityCreature.posY, entityCreature.posZ).subtract(vec3);
        return findRandomTargetBlock(entityCreature, n, n2, RandomPositionGenerator.staticVector);
    }
    
    private static Vec3 findRandomTargetBlock(final EntityCreature p0, final int p1, final int p2, final Vec3 p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/entity/EntityCreature.getRNG:()Ljava/util/Random;
        //     4: astore          4
        //     6: ldc             -99999.0
        //     8: fstore          9
        //    10: aload_0        
        //    11: invokevirtual   net/minecraft/entity/EntityCreature.hasHome:()Z
        //    14: ifeq            85
        //    17: aload_0        
        //    18: invokevirtual   net/minecraft/entity/EntityCreature.getHomePosition:()Lnet/minecraft/util/BlockPos;
        //    21: aload_0        
        //    22: getfield        net/minecraft/entity/EntityCreature.posX:D
        //    25: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    28: i2d            
        //    29: aload_0        
        //    30: getfield        net/minecraft/entity/EntityCreature.posY:D
        //    33: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    36: i2d            
        //    37: aload_0        
        //    38: getfield        net/minecraft/entity/EntityCreature.posZ:D
        //    41: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //    44: i2d            
        //    45: invokevirtual   net/minecraft/util/BlockPos.distanceSq:(DDD)D
        //    48: ldc2_w          4.0
        //    51: dadd           
        //    52: dstore          11
        //    54: aload_0        
        //    55: invokevirtual   net/minecraft/entity/EntityCreature.getMaximumHomeDistance:()F
        //    58: iload_1        
        //    59: i2f            
        //    60: fadd           
        //    61: f2d            
        //    62: dstore          13
        //    64: dload           11
        //    66: dload           13
        //    68: dload           13
        //    70: dmul           
        //    71: dcmpg          
        //    72: ifge            79
        //    75: iconst_1       
        //    76: goto            80
        //    79: iconst_0       
        //    80: istore          10
        //    82: goto            85
        //    85: aload           4
        //    87: iconst_2       
        //    88: iload_1        
        //    89: imul           
        //    90: iconst_1       
        //    91: iadd           
        //    92: invokevirtual   java/util/Random.nextInt:(I)I
        //    95: iload_1        
        //    96: isub           
        //    97: istore          12
        //    99: aload           4
        //   101: iconst_2       
        //   102: iload_2        
        //   103: imul           
        //   104: iconst_1       
        //   105: iadd           
        //   106: invokevirtual   java/util/Random.nextInt:(I)I
        //   109: iload_2        
        //   110: isub           
        //   111: istore          13
        //   113: aload           4
        //   115: iconst_2       
        //   116: iload_1        
        //   117: imul           
        //   118: iconst_1       
        //   119: iadd           
        //   120: invokevirtual   java/util/Random.nextInt:(I)I
        //   123: iload_1        
        //   124: isub           
        //   125: istore          14
        //   127: aload_3        
        //   128: ifnull          153
        //   131: iload           12
        //   133: i2d            
        //   134: aload_3        
        //   135: getfield        net/minecraft/util/Vec3.xCoord:D
        //   138: dmul           
        //   139: iload           14
        //   141: i2d            
        //   142: aload_3        
        //   143: getfield        net/minecraft/util/Vec3.zCoord:D
        //   146: dmul           
        //   147: dadd           
        //   148: dconst_0       
        //   149: dcmpl          
        //   150: iflt            352
        //   153: aload_0        
        //   154: invokevirtual   net/minecraft/entity/EntityCreature.hasHome:()Z
        //   157: ifeq            257
        //   160: iload_1        
        //   161: iconst_1       
        //   162: if_icmple       257
        //   165: aload_0        
        //   166: invokevirtual   net/minecraft/entity/EntityCreature.getHomePosition:()Lnet/minecraft/util/BlockPos;
        //   169: astore          15
        //   171: aload_0        
        //   172: getfield        net/minecraft/entity/EntityCreature.posX:D
        //   175: aload           15
        //   177: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //   180: i2d            
        //   181: dcmpl          
        //   182: ifle            201
        //   185: iload           12
        //   187: aload           4
        //   189: iload_1        
        //   190: iconst_2       
        //   191: idiv           
        //   192: invokevirtual   java/util/Random.nextInt:(I)I
        //   195: isub           
        //   196: istore          12
        //   198: goto            214
        //   201: iload           12
        //   203: aload           4
        //   205: iload_1        
        //   206: iconst_2       
        //   207: idiv           
        //   208: invokevirtual   java/util/Random.nextInt:(I)I
        //   211: iadd           
        //   212: istore          12
        //   214: aload_0        
        //   215: getfield        net/minecraft/entity/EntityCreature.posZ:D
        //   218: aload           15
        //   220: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //   223: i2d            
        //   224: dcmpl          
        //   225: ifle            244
        //   228: iload           14
        //   230: aload           4
        //   232: iload_1        
        //   233: iconst_2       
        //   234: idiv           
        //   235: invokevirtual   java/util/Random.nextInt:(I)I
        //   238: isub           
        //   239: istore          14
        //   241: goto            257
        //   244: iload           14
        //   246: aload           4
        //   248: iload_1        
        //   249: iconst_2       
        //   250: idiv           
        //   251: invokevirtual   java/util/Random.nextInt:(I)I
        //   254: iadd           
        //   255: istore          14
        //   257: iload           12
        //   259: aload_0        
        //   260: getfield        net/minecraft/entity/EntityCreature.posX:D
        //   263: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   266: iadd           
        //   267: istore          12
        //   269: iload           13
        //   271: aload_0        
        //   272: getfield        net/minecraft/entity/EntityCreature.posY:D
        //   275: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   278: iadd           
        //   279: istore          13
        //   281: iload           14
        //   283: aload_0        
        //   284: getfield        net/minecraft/entity/EntityCreature.posZ:D
        //   287: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //   290: iadd           
        //   291: istore          14
        //   293: new             Lnet/minecraft/util/BlockPos;
        //   296: dup            
        //   297: iload           12
        //   299: iload           13
        //   301: iload           14
        //   303: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   306: astore          15
        //   308: goto            320
        //   311: aload_0        
        //   312: aload           15
        //   314: invokevirtual   net/minecraft/entity/EntityCreature.isWithinHomeDistanceFromPosition:(Lnet/minecraft/util/BlockPos;)Z
        //   317: ifeq            352
        //   320: aload_0        
        //   321: aload           15
        //   323: invokevirtual   net/minecraft/entity/EntityCreature.getBlockPathWeight:(Lnet/minecraft/util/BlockPos;)F
        //   326: fstore          16
        //   328: fload           16
        //   330: fload           9
        //   332: fcmpl          
        //   333: ifle            352
        //   336: fload           16
        //   338: fstore          9
        //   340: iload           12
        //   342: istore          6
        //   344: iload           13
        //   346: istore          7
        //   348: iload           14
        //   350: istore          8
        //   352: iinc            11, 1
        //   355: goto            85
        //   358: new             Lnet/minecraft/util/Vec3;
        //   361: dup            
        //   362: iconst_0       
        //   363: i2d            
        //   364: iconst_0       
        //   365: i2d            
        //   366: iconst_0       
        //   367: i2d            
        //   368: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   371: areturn        
        //   372: aconst_null    
        //   373: areturn        
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
    
    public static Vec3 findRandomTarget(final EntityCreature entityCreature, final int n, final int n2) {
        return findRandomTargetBlock(entityCreature, n, n2, null);
    }
}
