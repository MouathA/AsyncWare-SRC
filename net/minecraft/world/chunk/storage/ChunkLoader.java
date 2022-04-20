package net.minecraft.world.chunk.storage;

import net.minecraft.world.biome.*;
import net.minecraft.nbt.*;
import net.minecraft.world.chunk.*;

public class ChunkLoader
{
    public static AnvilConverterData load(final NBTTagCompound nbtTagCompound) {
        final AnvilConverterData anvilConverterData = new AnvilConverterData(nbtTagCompound.getInteger("xPos"), nbtTagCompound.getInteger("zPos"));
        anvilConverterData.blocks = nbtTagCompound.getByteArray("Blocks");
        anvilConverterData.data = new NibbleArrayReader(nbtTagCompound.getByteArray("Data"), 7);
        anvilConverterData.skyLight = new NibbleArrayReader(nbtTagCompound.getByteArray("SkyLight"), 7);
        anvilConverterData.blockLight = new NibbleArrayReader(nbtTagCompound.getByteArray("BlockLight"), 7);
        anvilConverterData.heightmap = nbtTagCompound.getByteArray("HeightMap");
        anvilConverterData.terrainPopulated = nbtTagCompound.getBoolean("TerrainPopulated");
        anvilConverterData.entities = nbtTagCompound.getTagList("Entities", 10);
        anvilConverterData.tileEntities = nbtTagCompound.getTagList("TileEntities", 10);
        anvilConverterData.tileTicks = nbtTagCompound.getTagList("TileTicks", 10);
        anvilConverterData.lastUpdated = nbtTagCompound.getLong("LastUpdate");
        return anvilConverterData;
    }
    
    public static void convertToAnvilFormat(final AnvilConverterData anvilConverterData, final NBTTagCompound nbtTagCompound, final WorldChunkManager worldChunkManager) {
        nbtTagCompound.setInteger("xPos", anvilConverterData.x);
        nbtTagCompound.setInteger("zPos", anvilConverterData.z);
        nbtTagCompound.setLong("LastUpdate", anvilConverterData.lastUpdated);
        final int[] array = new int[anvilConverterData.heightmap.length];
        while (0 < anvilConverterData.heightmap.length) {
            array[0] = anvilConverterData.heightmap[0];
            int n = 0;
            ++n;
        }
        nbtTagCompound.setIntArray("HeightMap", array);
        nbtTagCompound.setBoolean("TerrainPopulated", anvilConverterData.terrainPopulated);
        final NBTTagList list = new NBTTagList();
        final byte[] array2 = new byte[4096];
        final NibbleArray nibbleArray = new NibbleArray();
        final NibbleArray nibbleArray2 = new NibbleArray();
        final NibbleArray nibbleArray3 = new NibbleArray();
        while (true) {
            array2[0] = (byte)(anvilConverterData.blocks[0] & 0xFF);
            nibbleArray.set(0, 0, 0, anvilConverterData.data.get(0, 0, 0));
            nibbleArray2.set(0, 0, 0, anvilConverterData.skyLight.get(0, 0, 0));
            nibbleArray3.set(0, 0, 0, anvilConverterData.blockLight.get(0, 0, 0));
            int n2 = 0;
            ++n2;
        }
    }
    
    public static class AnvilConverterData
    {
        public boolean terrainPopulated;
        public NibbleArrayReader data;
        public final int z;
        public byte[] heightmap;
        public byte[] blocks;
        public NibbleArrayReader skyLight;
        public NBTTagList tileTicks;
        public NBTTagList tileEntities;
        public NBTTagList entities;
        public final int x;
        public long lastUpdated;
        public NibbleArrayReader blockLight;
        
        public AnvilConverterData(final int x, final int z) {
            this.x = x;
            this.z = z;
        }
    }
}
