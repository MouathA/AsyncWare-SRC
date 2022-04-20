package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.*;

public class GenLayerBiome extends GenLayer
{
    private BiomeGenBase[] field_151620_f;
    private BiomeGenBase[] field_151622_e;
    private final ChunkProviderSettings field_175973_g;
    private BiomeGenBase[] field_151623_c;
    private BiomeGenBase[] field_151621_d;
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n, n2, n3, n4);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                this.initChunkSeed(0 + n, 0 + n2);
                final int n5 = ints[0 + 0 * n3];
                final int n6 = (n5 & 0xF00) >> 8;
                final int n7 = n5 & 0xFFFFF0FF;
                if (this.field_175973_g != null && this.field_175973_g.fixedBiome >= 0) {
                    intCache[0 + 0 * n3] = this.field_175973_g.fixedBiome;
                }
                else if (GenLayer.isBiomeOceanic(n7)) {
                    intCache[0 + 0 * n3] = n7;
                }
                else if (n7 == BiomeGenBase.mushroomIsland.biomeID) {
                    intCache[0 + 0 * n3] = n7;
                }
                else if (n7 == 1) {
                    if (n6 > 0) {
                        if (this.nextInt(3) == 0) {
                            intCache[0 + 0 * n3] = BiomeGenBase.mesaPlateau.biomeID;
                        }
                        else {
                            intCache[0 + 0 * n3] = BiomeGenBase.mesaPlateau_F.biomeID;
                        }
                    }
                    else {
                        intCache[0 + 0 * n3] = this.field_151623_c[this.nextInt(this.field_151623_c.length)].biomeID;
                    }
                }
                else if (n7 == 2) {
                    if (n6 > 0) {
                        intCache[0 + 0 * n3] = BiomeGenBase.jungle.biomeID;
                    }
                    else {
                        intCache[0 + 0 * n3] = this.field_151621_d[this.nextInt(this.field_151621_d.length)].biomeID;
                    }
                }
                else if (n7 == 3) {
                    if (n6 > 0) {
                        intCache[0 + 0 * n3] = BiomeGenBase.megaTaiga.biomeID;
                    }
                    else {
                        intCache[0 + 0 * n3] = this.field_151622_e[this.nextInt(this.field_151622_e.length)].biomeID;
                    }
                }
                else if (n7 == 4) {
                    intCache[0 + 0 * n3] = this.field_151620_f[this.nextInt(this.field_151620_f.length)].biomeID;
                }
                else {
                    intCache[0 + 0 * n3] = BiomeGenBase.mushroomIsland.biomeID;
                }
                int n8 = 0;
                ++n8;
            }
            int n9 = 0;
            ++n9;
        }
        return intCache;
    }
    
    public GenLayerBiome(final long n, final GenLayer parent, final WorldType worldType, final String s) {
        super(n);
        this.field_151623_c = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.savanna, BiomeGenBase.savanna, BiomeGenBase.plains };
        this.field_151621_d = new BiomeGenBase[] { BiomeGenBase.forest, BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.birchForest, BiomeGenBase.swampland };
        this.field_151622_e = new BiomeGenBase[] { BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains };
        this.field_151620_f = new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.coldTaiga };
        this.parent = parent;
        if (worldType == WorldType.DEFAULT_1_1) {
            this.field_151623_c = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga };
            this.field_175973_g = null;
        }
        else if (worldType == WorldType.CUSTOMIZED) {
            this.field_175973_g = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
        }
        else {
            this.field_175973_g = null;
        }
    }
}
