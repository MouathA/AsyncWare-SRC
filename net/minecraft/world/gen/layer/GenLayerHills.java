package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;
import org.apache.logging.log4j.*;

public class GenLayerHills extends GenLayer
{
    private static final Logger logger;
    private GenLayer field_151628_d;
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n - 1, n2 - 1, n3 + 2, n4 + 2);
        final int[] ints2 = this.field_151628_d.getInts(n - 1, n2 - 1, n3 + 2, n4 + 2);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        while (0 < n4) {
            while (0 < n3) {
                this.initChunkSeed(0 + n, 0 + n2);
                final int n5 = ints[1 + 1 * (n3 + 2)];
                final int n6 = ints2[1 + 1 * (n3 + 2)];
                final boolean b = (n6 - 2) % 29 == 0;
                if (n5 > 255) {
                    GenLayerHills.logger.debug("old! " + n5);
                }
                if (n5 != 0 && n6 >= 2 && (n6 - 2) % 29 == 1 && n5 < 128) {
                    if (BiomeGenBase.getBiome(n5 + 128) != null) {
                        intCache[0 + 0 * n3] = n5 + 128;
                    }
                    else {
                        intCache[0 + 0 * n3] = n5;
                    }
                }
                else if (this.nextInt(3) != 0 && !b) {
                    intCache[0 + 0 * n3] = n5;
                }
                else {
                    int n7;
                    if ((n7 = n5) == BiomeGenBase.desert.biomeID) {
                        n7 = BiomeGenBase.desertHills.biomeID;
                    }
                    else if (n5 == BiomeGenBase.forest.biomeID) {
                        n7 = BiomeGenBase.forestHills.biomeID;
                    }
                    else if (n5 == BiomeGenBase.birchForest.biomeID) {
                        n7 = BiomeGenBase.birchForestHills.biomeID;
                    }
                    else if (n5 == BiomeGenBase.roofedForest.biomeID) {
                        n7 = BiomeGenBase.plains.biomeID;
                    }
                    else if (n5 == BiomeGenBase.taiga.biomeID) {
                        n7 = BiomeGenBase.taigaHills.biomeID;
                    }
                    else if (n5 == BiomeGenBase.megaTaiga.biomeID) {
                        n7 = BiomeGenBase.megaTaigaHills.biomeID;
                    }
                    else if (n5 == BiomeGenBase.coldTaiga.biomeID) {
                        n7 = BiomeGenBase.coldTaigaHills.biomeID;
                    }
                    else if (n5 == BiomeGenBase.plains.biomeID) {
                        if (this.nextInt(3) == 0) {
                            n7 = BiomeGenBase.forestHills.biomeID;
                        }
                        else {
                            n7 = BiomeGenBase.forest.biomeID;
                        }
                    }
                    else if (n5 == BiomeGenBase.icePlains.biomeID) {
                        n7 = BiomeGenBase.iceMountains.biomeID;
                    }
                    else if (n5 == BiomeGenBase.jungle.biomeID) {
                        n7 = BiomeGenBase.jungleHills.biomeID;
                    }
                    else if (n5 == BiomeGenBase.ocean.biomeID) {
                        n7 = BiomeGenBase.deepOcean.biomeID;
                    }
                    else if (n5 == BiomeGenBase.extremeHills.biomeID) {
                        n7 = BiomeGenBase.extremeHillsPlus.biomeID;
                    }
                    else if (n5 == BiomeGenBase.savanna.biomeID) {
                        n7 = BiomeGenBase.savannaPlateau.biomeID;
                    }
                    else if (GenLayer.biomesEqualOrMesaPlateau(n5, BiomeGenBase.mesaPlateau_F.biomeID)) {
                        n7 = BiomeGenBase.mesa.biomeID;
                    }
                    else if (n5 == BiomeGenBase.deepOcean.biomeID && this.nextInt(3) == 0) {
                        if (this.nextInt(2) == 0) {
                            n7 = BiomeGenBase.plains.biomeID;
                        }
                        else {
                            n7 = BiomeGenBase.forest.biomeID;
                        }
                    }
                    if (b && n7 != n5) {
                        if (BiomeGenBase.getBiome(n7 + 128) != null) {
                            n7 += 128;
                        }
                        else {
                            n7 = n5;
                        }
                    }
                    if (n7 == n5) {
                        intCache[0 + 0 * n3] = n5;
                    }
                    else {
                        final int n8 = ints[1 + 0 * (n3 + 2)];
                        final int n9 = ints[2 + 1 * (n3 + 2)];
                        final int n10 = ints[0 + 1 * (n3 + 2)];
                        final int n11 = ints[1 + 2 * (n3 + 2)];
                        int n12 = 0;
                        if (GenLayer.biomesEqualOrMesaPlateau(n8, n5)) {
                            ++n12;
                        }
                        if (GenLayer.biomesEqualOrMesaPlateau(n9, n5)) {
                            ++n12;
                        }
                        if (GenLayer.biomesEqualOrMesaPlateau(n10, n5)) {
                            ++n12;
                        }
                        if (GenLayer.biomesEqualOrMesaPlateau(n11, n5)) {
                            ++n12;
                        }
                        intCache[0 + 0 * n3] = n5;
                    }
                }
                int n13 = 0;
                ++n13;
            }
            int n14 = 0;
            ++n14;
        }
        return intCache;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public GenLayerHills(final long n, final GenLayer parent, final GenLayer field_151628_d) {
        super(n);
        this.parent = parent;
        this.field_151628_d = field_151628_d;
    }
}
