package net.minecraft.world.biome;

import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class BiomeColorHelper
{
    private static final ColorResolver field_180290_c;
    private static final ColorResolver field_180289_b;
    private static final ColorResolver field_180291_a;
    
    private static int func_180285_a(final IBlockAccess blockAccess, final BlockPos blockPos, final ColorResolver colorResolver) {
        for (final BlockPos.MutableBlockPos mutableBlockPos : BlockPos.getAllInBoxMutable(blockPos.add(-1, 0, -1), blockPos.add(1, 0, 1))) {
            colorResolver.getColorAtPos(blockAccess.getBiomeGenForCoords(mutableBlockPos), mutableBlockPos);
        }
        return 0;
    }
    
    public static int getGrassColorAtPos(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180291_a);
    }
    
    public static int getFoliageColorAtPos(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180289_b);
    }
    
    public static int getWaterColorAtPos(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return func_180285_a(blockAccess, blockPos, BiomeColorHelper.field_180290_c);
    }
    
    static {
        field_180291_a = new ColorResolver() {
            @Override
            public int getColorAtPos(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.getGrassColorAtPos(blockPos);
            }
        };
        field_180289_b = new ColorResolver() {
            @Override
            public int getColorAtPos(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.getFoliageColorAtPos(blockPos);
            }
        };
        field_180290_c = new ColorResolver() {
            @Override
            public int getColorAtPos(final BiomeGenBase biomeGenBase, final BlockPos blockPos) {
                return biomeGenBase.waterColorMultiplier;
            }
        };
    }
    
    interface ColorResolver
    {
        int getColorAtPos(final BiomeGenBase p0, final BlockPos p1);
    }
}
