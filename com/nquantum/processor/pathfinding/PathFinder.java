package com.nquantum.processor.pathfinding;

import net.minecraft.util.*;

public class PathFinder
{
    public static double distanceBetween(final BlockPos blockPos, final BlockPos blockPos2) {
        final double n = blockPos.getX() - blockPos2.getX();
        final double n2 = blockPos.getY() - blockPos2.getY();
        final double n3 = blockPos.getZ() - blockPos2.getZ();
        return Math.sqrt(n * n + n2 * n2 + n3 * n3);
    }
    
    public static BlockPos[] findPath(final BlockPos blockPos, final BlockPos blockPos2) {
        final int n = (int)Math.ceil(distanceBetween(blockPos, blockPos2));
        final BlockPos[] array = new BlockPos[n];
        final double n2 = (blockPos2.getX() - (double)blockPos.getX()) / n;
        final double n3 = (blockPos2.getY() - (double)blockPos.getY()) / n;
        final double n4 = (blockPos2.getZ() - (double)blockPos.getZ()) / n;
        while (0 < n) {
            array[0] = bypassStambles(blockPos.add(1 * n2, 1 * n3, 1 * n4), true);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public static BlockPos moveBlockToFace(final BlockPos blockPos, final EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.DOWN) {
            return blockPos.add(0, -1, 0);
        }
        if (enumFacing == EnumFacing.UP) {
            return blockPos.add(0, 1, 0);
        }
        if (enumFacing == EnumFacing.EAST) {
            return blockPos.add(1, 0, 0);
        }
        if (enumFacing == EnumFacing.WEST) {
            return blockPos.add(-1, 0, 0);
        }
        if (enumFacing == EnumFacing.NORTH) {
            return blockPos.add(0, 0, -1);
        }
        if (enumFacing == EnumFacing.SOUTH) {
            return blockPos.add(0, 0, 1);
        }
        return null;
    }
    
    private static BlockPos bypassStambles(final BlockPos p0, final boolean p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore_2       
        //     2: iload_1        
        //     3: ifeq            10
        //     6: iconst_1       
        //     7: goto            11
        //    10: iconst_m1      
        //    11: istore_3       
        //    12: aload_2        
        //    13: if_acmpne       27
        //    16: aload_2        
        //    17: iconst_0       
        //    18: iload_3        
        //    19: iconst_0       
        //    20: invokevirtual   net/minecraft/util/BlockPos.add:(III)Lnet/minecraft/util/BlockPos;
        //    23: astore_2       
        //    24: goto            12
        //    27: aload_2        
        //    28: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
