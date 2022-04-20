package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import java.util.*;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree
{
    protected final IBlockState woodMetadata;
    protected int extraRandomHeight;
    protected final IBlockState leavesMetadata;
    protected final int baseHeight;
    
    public WorldGenHugeTrees(final boolean b, final int baseHeight, final int extraRandomHeight, final IBlockState woodMetadata, final IBlockState leavesMetadata) {
        super(b);
        this.baseHeight = baseHeight;
        this.extraRandomHeight = extraRandomHeight;
        this.woodMetadata = woodMetadata;
        this.leavesMetadata = leavesMetadata;
    }
    
    protected void func_175928_b(final World world, final BlockPos blockPos, final int n) {
        final int n2 = n * n;
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= n; ++j) {
                if (i * i + j * j <= n2) {
                    final BlockPos add = blockPos.add(i, 0, j);
                    final Material material = world.getBlockState(add).getBlock().getMaterial();
                    if (material == Material.air || material == Material.leaves) {
                        this.setBlockAndNotifyAdequately(world, add, this.leavesMetadata);
                    }
                }
            }
        }
    }
    
    protected int func_150533_a(final Random random) {
        int n = random.nextInt(3) + this.baseHeight;
        if (this.extraRandomHeight > 1) {
            n += random.nextInt(this.extraRandomHeight);
        }
        return n;
    }
    
    protected void func_175925_a(final World world, final BlockPos blockPos, final int n) {
        final int n2 = n * n;
        for (int i = -n; i <= n + 1; ++i) {
            for (int j = -n; j <= n + 1; ++j) {
                final int n3 = i - 1;
                final int n4 = j - 1;
                if (i * i + j * j <= n2 || n3 * n3 + n4 * n4 <= n2 || i * i + n4 * n4 <= n2 || n3 * n3 + j * j <= n2) {
                    final BlockPos add = blockPos.add(i, 0, j);
                    final Material material = world.getBlockState(add).getBlock().getMaterial();
                    if (material == Material.air || material == Material.leaves) {
                        this.setBlockAndNotifyAdequately(world, add, this.leavesMetadata);
                    }
                }
            }
        }
    }
    
    protected boolean func_175929_a(final World p0, final Random p1, final BlockPos p2, final int p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: aload_3        
        //     3: iload           4
        //     5: if_icmplt       18
        //     8: aload_0        
        //     9: aload_3        
        //    10: aload_1        
        //    11: if_acmpeq       18
        //    14: iconst_1       
        //    15: goto            19
        //    18: iconst_0       
        //    19: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0018 (coming from #0011).
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
}
