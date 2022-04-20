package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;

public class MapGenMineshaft extends MapGenStructure
{
    private double field_82673_e;
    
    @Override
    public String getStructureName() {
        return "Mineshaft";
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int n, final int n2) {
        return this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(n), Math.abs(n2));
    }
    
    @Override
    protected StructureStart getStructureStart(final int n, final int n2) {
        return new StructureMineshaftStart(this.worldObj, this.rand, n, n2);
    }
    
    public MapGenMineshaft() {
        this.field_82673_e = 0.004;
    }
    
    public MapGenMineshaft(final Map map) {
        this.field_82673_e = 0.004;
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getKey().equals("chance")) {
                this.field_82673_e = MathHelper.parseDoubleWithDefault((String)entry.getValue(), this.field_82673_e);
            }
        }
    }
}
