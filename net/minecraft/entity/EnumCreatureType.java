package net.minecraft.entity;

import net.minecraft.block.material.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;

public enum EnumCreatureType
{
    private final boolean isPeacefulCreature;
    private final boolean isAnimal;
    
    WATER_CREATURE("WATER_CREATURE", 3, (Class)EntityWaterMob.class, 5, Material.water, true, false);
    
    private static final EnumCreatureType[] $VALUES;
    private final int maxNumberOfCreature;
    private final Class creatureClass;
    
    CREATURE("CREATURE", 1, (Class)EntityAnimal.class, 10, Material.air, true, true), 
    MONSTER("MONSTER", 0, (Class)IMob.class, 70, Material.air, false, false), 
    AMBIENT("AMBIENT", 2, (Class)EntityAmbientCreature.class, 15, Material.air, true, false);
    
    private final Material creatureMaterial;
    
    public boolean getAnimal() {
        return this.isAnimal;
    }
    
    public boolean getPeacefulCreature() {
        return this.isPeacefulCreature;
    }
    
    private EnumCreatureType(final String s, final int n, final Class creatureClass, final int maxNumberOfCreature, final Material creatureMaterial, final boolean isPeacefulCreature, final boolean isAnimal) {
        this.creatureClass = creatureClass;
        this.maxNumberOfCreature = maxNumberOfCreature;
        this.creatureMaterial = creatureMaterial;
        this.isPeacefulCreature = isPeacefulCreature;
        this.isAnimal = isAnimal;
    }
    
    public Class getCreatureClass() {
        return this.creatureClass;
    }
    
    public int getMaxNumberOfCreature() {
        return this.maxNumberOfCreature;
    }
    
    static {
        $VALUES = new EnumCreatureType[] { EnumCreatureType.MONSTER, EnumCreatureType.CREATURE, EnumCreatureType.AMBIENT, EnumCreatureType.WATER_CREATURE };
    }
}
