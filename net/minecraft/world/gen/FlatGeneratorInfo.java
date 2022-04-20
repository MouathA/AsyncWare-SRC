package net.minecraft.world.gen;

import net.minecraft.world.biome.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class FlatGeneratorInfo
{
    private final Map worldFeatures;
    private int biomeToUse;
    private final List flatLayers;
    
    public void func_82645_d() {
        for (final FlatLayerInfo flatLayerInfo : this.flatLayers) {
            flatLayerInfo.setMinY(0);
            final int n = 0 + flatLayerInfo.getLayerCount();
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(3);
        sb.append(";");
        int n = 0;
        while (0 < this.flatLayers.size()) {
            sb.append(this.flatLayers.get(0).toString());
            ++n;
        }
        sb.append(";");
        sb.append(this.biomeToUse);
        if (!this.worldFeatures.isEmpty()) {
            sb.append(";");
            for (final Map.Entry<String, V> entry : this.worldFeatures.entrySet()) {
                final int n2 = 0;
                ++n;
                if (n2 > 0) {
                    sb.append(",");
                }
                sb.append(entry.getKey().toLowerCase());
                final Map map = (Map)entry.getValue();
                if (!map.isEmpty()) {
                    sb.append("(");
                    for (final Map.Entry<String, V> entry2 : map.entrySet()) {
                        final int n3 = 0;
                        int n4 = 0;
                        ++n4;
                        if (n3 > 0) {
                            sb.append(" ");
                        }
                        sb.append(entry2.getKey());
                        sb.append("=");
                        sb.append((String)entry2.getValue());
                    }
                    sb.append(")");
                }
            }
        }
        else {
            sb.append(";");
        }
        return sb.toString();
    }
    
    public static FlatGeneratorInfo getDefaultFlatGenerator() {
        final FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
        flatGeneratorInfo.setBiome(BiomeGenBase.plains.biomeID);
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo(1, Blocks.bedrock));
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo(2, Blocks.dirt));
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo(1, Blocks.grass));
        flatGeneratorInfo.func_82645_d();
        flatGeneratorInfo.getWorldFeatures().put("village", Maps.newHashMap());
        return flatGeneratorInfo;
    }
    
    public List getFlatLayers() {
        return this.flatLayers;
    }
    
    public Map getWorldFeatures() {
        return this.worldFeatures;
    }
    
    public int getBiome() {
        return this.biomeToUse;
    }
    
    private static FlatLayerInfo func_180715_a(final int n, final String s, final int minY) {
        final String[] array = (n >= 3) ? s.split("\\*", 2) : s.split("x", 2);
        if (array.length == 2) {
            Integer.parseInt(array[0]);
            if (minY + 0 >= 256) {}
        }
        final String s2 = array[array.length - 1];
        Block block;
        if (n < 3) {
            final String[] split = s2.split(":", 2);
            if (split.length > 1) {
                Integer.parseInt(split[1]);
            }
            block = Block.getBlockById(Integer.parseInt(split[0]));
        }
        else {
            final String[] split2 = s2.split(":", 3);
            block = ((split2.length > 1) ? Block.getBlockFromName(split2[0] + ":" + split2[1]) : null);
            if (block != null) {
                final int n2 = (split2.length > 2) ? Integer.parseInt(split2[2]) : 0;
            }
            else {
                block = Block.getBlockFromName(split2[0]);
                if (block != null) {
                    final int n3 = (split2.length > 1) ? Integer.parseInt(split2[1]) : 0;
                }
            }
            if (block == null) {
                return null;
            }
        }
        if (block == Blocks.air) {}
        final FlatLayerInfo flatLayerInfo = new FlatLayerInfo(n, 0, block, 0);
        flatLayerInfo.setMinY(minY);
        return flatLayerInfo;
    }
    
    public void setBiome(final int biomeToUse) {
        this.biomeToUse = biomeToUse;
    }
    
    public static FlatGeneratorInfo createFlatGeneratorFromString(final String s) {
        if (s == null) {
            return getDefaultFlatGenerator();
        }
        final String[] split = s.split(";", -1);
        final int n = (split.length == 1) ? 0 : MathHelper.parseIntWithDefault(split[0], 0);
        if (n < 0 || n > 3) {
            return getDefaultFlatGenerator();
        }
        final FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
        int n2 = (split.length != 1) ? 1 : 0;
        final List func_180716_a = func_180716_a(n, split[n2++]);
        if (func_180716_a != null && !func_180716_a.isEmpty()) {
            flatGeneratorInfo.getFlatLayers().addAll(func_180716_a);
            flatGeneratorInfo.func_82645_d();
            int biome = BiomeGenBase.plains.biomeID;
            if (n > 0 && split.length > n2) {
                biome = MathHelper.parseIntWithDefault(split[n2++], biome);
            }
            flatGeneratorInfo.setBiome(biome);
            if (n > 0 && split.length > n2) {
                final String[] split2 = split[n2++].toLowerCase().split(",");
                while (0 < split2.length) {
                    final String[] split3 = split2[0].split("\\(", 2);
                    final HashMap hashMap = Maps.newHashMap();
                    if (split3[0].length() > 0) {
                        flatGeneratorInfo.getWorldFeatures().put(split3[0], hashMap);
                        if (split3.length > 1 && split3[1].endsWith(")") && split3[1].length() > 1) {
                            final String[] split4 = split3[1].substring(0, split3[1].length() - 1).split(" ");
                            while (0 < split4.length) {
                                final String[] split5 = split4[0].split("=", 2);
                                if (split5.length == 2) {
                                    hashMap.put(split5[0], split5[1]);
                                }
                                int n3 = 0;
                                ++n3;
                            }
                        }
                    }
                    int n4 = 0;
                    ++n4;
                }
            }
            else {
                flatGeneratorInfo.getWorldFeatures().put("village", Maps.newHashMap());
            }
            return flatGeneratorInfo;
        }
        return getDefaultFlatGenerator();
    }
    
    public FlatGeneratorInfo() {
        this.flatLayers = Lists.newArrayList();
        this.worldFeatures = Maps.newHashMap();
    }
    
    private static List func_180716_a(final int n, final String s) {
        if (s != null && s.length() >= 1) {
            final ArrayList arrayList = Lists.newArrayList();
            final String[] split = s.split(",");
            while (0 < split.length) {
                final FlatLayerInfo func_180715_a = func_180715_a(n, split[0], 0);
                if (func_180715_a == null) {
                    return null;
                }
                arrayList.add(func_180715_a);
                final int n2 = 0 + func_180715_a.getLayerCount();
                int n3 = 0;
                ++n3;
            }
            return arrayList;
        }
        return null;
    }
}
