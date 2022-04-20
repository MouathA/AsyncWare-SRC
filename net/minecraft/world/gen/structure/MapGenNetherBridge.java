package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import java.util.*;

public class MapGenNetherBridge extends MapGenStructure
{
    private List spawnList;
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new Start(this.worldObj, this.rand, n, n2);
    }
    
    public MapGenNetherBridge() {
        (this.spawnList = Lists.newArrayList()).add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
        this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
        this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int n, final int n2) {
        final int n3 = n >> 4;
        final int n4 = n2 >> 4;
        this.rand.setSeed((long)(n3 ^ n4 << 4) ^ this.worldObj.getSeed());
        this.rand.nextInt();
        return this.rand.nextInt(3) == 0 && n == (n3 << 4) + 4 + this.rand.nextInt(8) && n2 == (n4 << 4) + 4 + this.rand.nextInt(8);
    }
    
    public List getSpawnList() {
        return this.spawnList;
    }
    
    @Override
    public String getStructureName() {
        return "Fortress";
    }
    
    public static class Start extends StructureStart
    {
        public Start() {
        }
        
        public Start(final World world, final Random random, final int n, final int n2) {
            super(n, n2);
            final StructureNetherBridgePieces.Start start = new StructureNetherBridgePieces.Start(random, (n << 4) + 2, (n2 << 4) + 2);
            this.components.add(start);
            start.buildComponent(start, this.components, random);
            final List field_74967_d = start.field_74967_d;
            while (!field_74967_d.isEmpty()) {
                field_74967_d.remove(random.nextInt(field_74967_d.size())).buildComponent(start, this.components, random);
            }
            this.updateBoundingBox();
            this.setRandomHeight(world, random, 48, 70);
        }
    }
}
