package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.block.*;

public class WorldGenBigTree extends WorldGenAbstractTree
{
    double scaleWidth;
    private Random rand;
    int heightLimit;
    double heightAttenuation;
    int height;
    private World world;
    double leafDensity;
    int heightLimitLimit;
    private BlockPos basePos;
    List field_175948_j;
    int trunkSize;
    int leafDistanceLimit;
    double branchSlope;
    
    @Override
    public void func_175904_e() {
        this.leafDistanceLimit = 5;
    }
    
    float layerSize(final int n) {
        if (n < this.heightLimit * 0.3f) {
            return -1.0f;
        }
        final float n2 = this.heightLimit / 2.0f;
        final float n3 = n2 - n;
        float sqrt_float = MathHelper.sqrt_float(n2 * n2 - n3 * n3);
        if (n3 == 0.0f) {
            sqrt_float = n2;
        }
        else if (Math.abs(n3) >= n2) {
            return 0.0f;
        }
        return sqrt_float * 0.5f;
    }
    
    void generateTrunk() {
        final BlockPos basePos = this.basePos;
        final BlockPos up = this.basePos.up(this.height);
        final Block log = Blocks.log;
        this.func_175937_a(basePos, up, log);
        if (this.trunkSize == 2) {
            this.func_175937_a(basePos.east(), up.east(), log);
            this.func_175937_a(basePos.east().south(), up.east().south(), log);
            this.func_175937_a(basePos.south(), up.south(), log);
        }
    }
    
    void generateLeafNode(final BlockPos blockPos) {
        while (0 < this.leafDistanceLimit) {
            this.func_181631_a(blockPos.up(0), this.leafSize(0), Blocks.leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false));
            int n = 0;
            ++n;
        }
    }
    
    void generateLeaves() {
        final Iterator<FoliageCoordinates> iterator = this.field_175948_j.iterator();
        while (iterator.hasNext()) {
            this.generateLeafNode(iterator.next());
        }
    }
    
    int checkBlockLine(final BlockPos blockPos, final BlockPos blockPos2) {
        final BlockPos add = blockPos2.add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        final int greatestDistance = this.getGreatestDistance(add);
        final float n = add.getX() / (float)greatestDistance;
        final float n2 = add.getY() / (float)greatestDistance;
        final float n3 = add.getZ() / (float)greatestDistance;
        if (greatestDistance == 0) {
            return -1;
        }
        while (0 <= greatestDistance) {
            if (!this.func_150523_a(this.world.getBlockState(blockPos.add(0.5f + 0 * n, 0.5f + 0 * n2, 0.5f + 0 * n3)).getBlock())) {
                return 0;
            }
            int n4 = 0;
            ++n4;
        }
        return -1;
    }
    
    void func_181631_a(final BlockPos blockPos, final float n, final IBlockState blockState) {
        for (int n2 = (int)(n + 0.618), i = -n2; i <= n2; ++i) {
            for (int j = -n2; j <= n2; ++j) {
                if (Math.pow(Math.abs(i) + 0.5, 2.0) + Math.pow(Math.abs(j) + 0.5, 2.0) <= n * n) {
                    final BlockPos add = blockPos.add(i, 0, j);
                    final Material material = this.world.getBlockState(add).getBlock().getMaterial();
                    if (material == Material.air || material == Material.leaves) {
                        this.setBlockAndNotifyAdequately(this.world, add, blockState);
                    }
                }
            }
        }
    }
    
    void generateLeafNodeList() {
        this.height = (int)(this.heightLimit * this.heightAttenuation);
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }
        final int n = (int)(1.382 + Math.pow(this.leafDensity * this.heightLimit / 13.0, 2.0));
        final int n2 = this.basePos.getY() + this.height;
        int i = this.heightLimit - this.leafDistanceLimit;
        (this.field_175948_j = Lists.newArrayList()).add(new FoliageCoordinates(this.basePos.up(i), n2));
        while (i >= 0) {
            final float layerSize = this.layerSize(i);
            if (layerSize >= 0.0f) {
                while (true) {
                    final double n3 = this.scaleWidth * layerSize * (this.rand.nextFloat() + 0.328);
                    final double n4 = this.rand.nextFloat() * 2.0f * 3.141592653589793;
                    final BlockPos add = this.basePos.add(n3 * Math.sin(n4) + 0.5, i - 1, n3 * Math.cos(n4) + 0.5);
                    if (this.checkBlockLine(add, add.up(this.leafDistanceLimit)) == -1) {
                        final int n5 = this.basePos.getX() - add.getX();
                        final int n6 = this.basePos.getZ() - add.getZ();
                        final double n7 = add.getY() - Math.sqrt(n5 * n5 + n6 * n6) * this.branchSlope;
                        final BlockPos blockPos = new BlockPos(this.basePos.getX(), (n7 > n2) ? n2 : ((int)n7), this.basePos.getZ());
                        if (this.checkBlockLine(blockPos, add) == -1) {
                            this.field_175948_j.add(new FoliageCoordinates(add, blockPos.getY()));
                        }
                    }
                    int n8 = 0;
                    ++n8;
                }
            }
            else {
                --i;
            }
        }
    }
    
    float leafSize(final int n) {
        return (n >= 0 && n < this.leafDistanceLimit) ? ((n != 0 && n != this.leafDistanceLimit - 1) ? 3.0f : 2.0f) : -1.0f;
    }
    
    @Override
    public boolean generate(final World p0, final Random p1, final BlockPos p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: putfield        net/minecraft/world/gen/feature/WorldGenBigTree.world:Lnet/minecraft/world/World;
        //     5: aload_0        
        //     6: aload_3        
        //     7: putfield        net/minecraft/world/gen/feature/WorldGenBigTree.basePos:Lnet/minecraft/util/BlockPos;
        //    10: aload_0        
        //    11: new             Ljava/util/Random;
        //    14: dup            
        //    15: aload_2        
        //    16: invokevirtual   java/util/Random.nextLong:()J
        //    19: invokespecial   java/util/Random.<init>:(J)V
        //    22: putfield        net/minecraft/world/gen/feature/WorldGenBigTree.rand:Ljava/util/Random;
        //    25: aload_0        
        //    26: getfield        net/minecraft/world/gen/feature/WorldGenBigTree.heightLimit:I
        //    29: ifne            49
        //    32: aload_0        
        //    33: iconst_5       
        //    34: aload_0        
        //    35: getfield        net/minecraft/world/gen/feature/WorldGenBigTree.rand:Ljava/util/Random;
        //    38: aload_0        
        //    39: getfield        net/minecraft/world/gen/feature/WorldGenBigTree.heightLimitLimit:I
        //    42: invokevirtual   java/util/Random.nextInt:(I)I
        //    45: iadd           
        //    46: putfield        net/minecraft/world/gen/feature/WorldGenBigTree.heightLimit:I
        //    49: aload_0        
        //    50: if_acmpeq       55
        //    53: iconst_0       
        //    54: ireturn        
        //    55: aload_0        
        //    56: invokevirtual   net/minecraft/world/gen/feature/WorldGenBigTree.generateLeafNodeList:()V
        //    59: aload_0        
        //    60: invokevirtual   net/minecraft/world/gen/feature/WorldGenBigTree.generateLeaves:()V
        //    63: aload_0        
        //    64: invokevirtual   net/minecraft/world/gen/feature/WorldGenBigTree.generateTrunk:()V
        //    67: aload_0        
        //    68: invokevirtual   net/minecraft/world/gen/feature/WorldGenBigTree.generateLeafNodeBases:()V
        //    71: iconst_1       
        //    72: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    void generateLeafNodeBases() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //     9: astore_1       
        //    10: aload_1        
        //    11: invokeinterface java/util/Iterator.hasNext:()Z
        //    16: ifeq            93
        //    19: aload_1        
        //    20: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    25: checkcast       Lnet/minecraft/world/gen/feature/WorldGenBigTree$FoliageCoordinates;
        //    28: astore_2       
        //    29: aload_2        
        //    30: invokevirtual   net/minecraft/world/gen/feature/WorldGenBigTree$FoliageCoordinates.func_177999_q:()I
        //    33: istore_3       
        //    34: new             Lnet/minecraft/util/BlockPos;
        //    37: dup            
        //    38: aload_0        
        //    39: getfield        net/minecraft/world/gen/feature/WorldGenBigTree.basePos:Lnet/minecraft/util/BlockPos;
        //    42: invokevirtual   net/minecraft/util/BlockPos.getX:()I
        //    45: iload_3        
        //    46: aload_0        
        //    47: getfield        net/minecraft/world/gen/feature/WorldGenBigTree.basePos:Lnet/minecraft/util/BlockPos;
        //    50: invokevirtual   net/minecraft/util/BlockPos.getZ:()I
        //    53: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //    56: astore          4
        //    58: aload           4
        //    60: aload_2        
        //    61: invokevirtual   net/minecraft/util/BlockPos.equals:(Ljava/lang/Object;)Z
        //    64: ifne            90
        //    67: aload_0        
        //    68: iload_3        
        //    69: aload_0        
        //    70: getfield        net/minecraft/world/gen/feature/WorldGenBigTree.basePos:Lnet/minecraft/util/BlockPos;
        //    73: invokevirtual   net/minecraft/util/BlockPos.getY:()I
        //    76: isub           
        //    77: iflt            90
        //    80: aload_0        
        //    81: aload           4
        //    83: aload_2        
        //    84: getstatic       net/minecraft/init/Blocks.log:Lnet/minecraft/block/Block;
        //    87: invokevirtual   net/minecraft/world/gen/feature/WorldGenBigTree.func_175937_a:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;)V
        //    90: goto            10
        //    93: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0090 (coming from #0077).
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
    
    private BlockLog.EnumAxis func_175938_b(final BlockPos blockPos, final BlockPos blockPos2) {
        BlockLog.EnumAxis enumAxis = BlockLog.EnumAxis.Y;
        final int abs = Math.abs(blockPos2.getX() - blockPos.getX());
        final int abs2 = Math.abs(blockPos2.getZ() - blockPos.getZ());
        final int max = Math.max(abs, abs2);
        if (max > 0) {
            if (abs == max) {
                enumAxis = BlockLog.EnumAxis.X;
            }
            else if (abs2 == max) {
                enumAxis = BlockLog.EnumAxis.Z;
            }
        }
        return enumAxis;
    }
    
    public WorldGenBigTree(final boolean b) {
        super(b);
        this.basePos = BlockPos.ORIGIN;
        this.heightAttenuation = 0.618;
        this.branchSlope = 0.381;
        this.scaleWidth = 1.0;
        this.leafDensity = 1.0;
        this.trunkSize = 1;
        this.heightLimitLimit = 12;
        this.leafDistanceLimit = 4;
    }
    
    void func_175937_a(final BlockPos blockPos, final BlockPos blockPos2, final Block block) {
        final BlockPos add = blockPos2.add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        final int greatestDistance = this.getGreatestDistance(add);
        final float n = add.getX() / (float)greatestDistance;
        final float n2 = add.getY() / (float)greatestDistance;
        final float n3 = add.getZ() / (float)greatestDistance;
        while (0 <= greatestDistance) {
            final BlockPos add2 = blockPos.add(0.5f + 0 * n, 0.5f + 0 * n2, 0.5f + 0 * n3);
            this.setBlockAndNotifyAdequately(this.world, add2, block.getDefaultState().withProperty(BlockLog.LOG_AXIS, this.func_175938_b(blockPos, add2)));
            int n4 = 0;
            ++n4;
        }
    }
    
    private int getGreatestDistance(final BlockPos blockPos) {
        final int abs_int = MathHelper.abs_int(blockPos.getX());
        final int abs_int2 = MathHelper.abs_int(blockPos.getY());
        final int abs_int3 = MathHelper.abs_int(blockPos.getZ());
        return (abs_int3 > abs_int && abs_int3 > abs_int2) ? abs_int3 : ((abs_int2 > abs_int) ? abs_int2 : abs_int);
    }
    
    static class FoliageCoordinates extends BlockPos
    {
        private final int field_178000_b;
        
        public int func_177999_q() {
            return this.field_178000_b;
        }
        
        public FoliageCoordinates(final BlockPos blockPos, final int field_178000_b) {
            super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.field_178000_b = field_178000_b;
        }
    }
}
