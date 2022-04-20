package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.init.*;

public class ItemMap extends ItemMapBase
{
    @Override
    public Packet createMapDataPacket(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return this.getMapData(itemStack, world).getMapPacket(itemStack, world, entityPlayer);
    }
    
    public static MapData loadMapData(final int n, final World world) {
        final String string = "map_" + n;
        MapData mapData = (MapData)world.loadItemData(MapData.class, string);
        if (mapData == null) {
            mapData = new MapData(string);
            world.setItemData(string, mapData);
        }
        return mapData;
    }
    
    @Override
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int n, final boolean b) {
        if (!world.isRemote) {
            final MapData mapData = this.getMapData(itemStack, world);
            if (entity instanceof EntityPlayer) {
                mapData.updateVisiblePlayers((EntityPlayer)entity, itemStack);
            }
            if (b) {
                this.updateMapData(world, entity, mapData);
            }
        }
    }
    
    public void updateMapData(final World world, final Entity entity, final MapData mapData) {
        if (world.provider.getDimensionId() == mapData.dimension && entity instanceof EntityPlayer) {
            final int n = 1 << mapData.scale;
            final int xCenter = mapData.xCenter;
            final int zCenter = mapData.zCenter;
            final int n2 = MathHelper.floor_double(entity.posX - xCenter) / n + 64;
            final int n3 = MathHelper.floor_double(entity.posZ - zCenter) / n + 64;
            int n4 = 128 / n;
            if (world.provider.getHasNoSky()) {
                n4 /= 2;
            }
            final MapData.MapInfo mapInfo2;
            final MapData.MapInfo mapInfo = mapInfo2 = mapData.getMapInfo((EntityPlayer)entity);
            ++mapInfo2.field_82569_d;
            for (int i = n2 - n4 + 1; i < n2 + n4; ++i) {
                if ((i & 0xF) != (mapInfo.field_82569_d & 0xF)) {}
                double n5 = 0.0;
                for (int j = n3 - n4 - 1; j < n3 + n4; ++j) {
                    if (i >= 0 && j >= -1 && i < 128 && j < 128) {
                        final int n6 = i - n2;
                        final int n7 = j - n3;
                        final boolean b = n6 * n6 + n7 * n7 > (n4 - 2) * (n4 - 2);
                        final int n8 = (xCenter / n + i - 64) * n;
                        final int n9 = (zCenter / n + j - 64) * n;
                        final HashMultiset create = HashMultiset.create();
                        final Chunk chunkFromBlockCoords = world.getChunkFromBlockCoords(new BlockPos(n8, 0, n9));
                        if (!chunkFromBlockCoords.isEmpty()) {
                            final int n10 = n8 & 0xF;
                            final int n11 = n9 & 0xF;
                            double n12 = 0.0;
                            if (world.provider.getHasNoSky()) {
                                final int n13 = n8 + n9 * 231871;
                                if ((n13 * n13 * 31287121 + n13 * 11 >> 20 & 0x1) == 0x0) {
                                    ((Multiset)create).add((Object)Blocks.dirt.getMapColor(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT)), 10);
                                }
                                else {
                                    ((Multiset)create).add((Object)Blocks.stone.getMapColor(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE)), 100);
                                }
                                n12 = 100.0;
                            }
                            else {
                                final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                                while (0 < n) {
                                    while (0 < n) {
                                        int n14 = chunkFromBlockCoords.getHeightValue(0 + n10, 0 + n11) + 1;
                                        IBlockState blockState = Blocks.air.getDefaultState();
                                        if (n14 > 1) {
                                            do {
                                                --n14;
                                                blockState = chunkFromBlockCoords.getBlockState(mutableBlockPos.func_181079_c(0 + n10, n14, 0 + n11));
                                            } while (blockState.getBlock().getMapColor(blockState) == MapColor.airColor && n14 > 0);
                                            if (n14 > 0 && blockState.getBlock().getMaterial().isLiquid()) {
                                                int n15 = n14 - 1;
                                                Block block;
                                                do {
                                                    block = chunkFromBlockCoords.getBlock(0 + n10, n15--, 0 + n11);
                                                    int n16 = 0;
                                                    ++n16;
                                                    if (n15 <= 0) {
                                                        break;
                                                    }
                                                } while (block.getMaterial().isLiquid());
                                            }
                                        }
                                        n12 += n14 / (double)(n * n);
                                        ((Multiset)create).add((Object)blockState.getBlock().getMapColor(blockState));
                                        int n17 = 0;
                                        ++n17;
                                    }
                                    int n18 = 0;
                                    ++n18;
                                }
                            }
                            int n16 = 0 / (n * n);
                            final double n19 = (n12 - n5) * 4.0 / (n + 4) + ((i + j & 0x1) - 0.5) * 0.4;
                            if (n19 > 0.6) {}
                            if (n19 < -0.6) {}
                            final MapColor mapColor = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)create), (Object)MapColor.airColor);
                            if (mapColor == MapColor.waterColor) {
                                final double n20 = 0 * 0.1 + (i + j & 0x1) * 0.2;
                                if (n20 < 0.5) {}
                                if (n20 > 0.9) {}
                            }
                            n5 = n12;
                            if (j >= 0 && n6 * n6 + n7 * n7 < n4 * n4 && (!b || (i + j & 0x1) != 0x0)) {
                                final byte b2 = mapData.colors[i + j * 128];
                                final byte b3 = (byte)(mapColor.colorIndex * 4 + 0);
                                if (b2 != b3) {
                                    mapData.colors[i + j * 128] = b3;
                                    mapData.updateMapData(i, j);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        final MapData mapData = this.getMapData(itemStack, entityPlayer.worldObj);
        if (b) {
            if (mapData == null) {
                list.add("Unknown map");
            }
            else {
                list.add("Scaling at 1:" + (1 << mapData.scale));
                list.add("(Level " + mapData.scale + "/" + 4 + ")");
            }
        }
    }
    
    public MapData getMapData(final ItemStack itemStack, final World world) {
        MapData mapData = (MapData)world.loadItemData(MapData.class, "map_" + itemStack.getMetadata());
        if (mapData == null && !world.isRemote) {
            itemStack.setItemDamage(world.getUniqueDataId("map"));
            final String string = "map_" + itemStack.getMetadata();
            mapData = new MapData(string);
            mapData.scale = 3;
            mapData.calculateMapCenter(world.getWorldInfo().getSpawnX(), world.getWorldInfo().getSpawnZ(), mapData.scale);
            mapData.dimension = (byte)world.provider.getDimensionId();
            mapData.markDirty();
            world.setItemData(string, mapData);
        }
        return mapData;
    }
    
    @Override
    public void onCreated(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("map_is_scaling")) {
            final MapData mapData = Items.filled_map.getMapData(itemStack, world);
            itemStack.setItemDamage(world.getUniqueDataId("map"));
            final MapData mapData2 = new MapData("map_" + itemStack.getMetadata());
            mapData2.scale = (byte)(mapData.scale + 1);
            if (mapData2.scale > 4) {
                mapData2.scale = 4;
            }
            mapData2.calculateMapCenter(mapData.xCenter, mapData.zCenter, mapData2.scale);
            mapData2.dimension = mapData.dimension;
            mapData2.markDirty();
            world.setItemData("map_" + itemStack.getMetadata(), mapData2);
        }
    }
    
    protected ItemMap() {
        this.setHasSubtypes(true);
    }
}
