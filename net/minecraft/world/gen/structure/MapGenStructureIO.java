package net.minecraft.world.gen.structure;

import java.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class MapGenStructureIO
{
    private static Map startNameToClassMap;
    private static final Logger logger;
    private static Map componentClassToNameMap;
    private static Map startClassToNameMap;
    private static Map componentNameToClassMap;
    
    private static void registerStructure(final Class clazz, final String s) {
        MapGenStructureIO.startNameToClassMap.put(s, clazz);
        MapGenStructureIO.startClassToNameMap.put(clazz, s);
    }
    
    public static String getStructureStartName(final StructureStart structureStart) {
        return MapGenStructureIO.startClassToNameMap.get(structureStart.getClass());
    }
    
    static {
        logger = LogManager.getLogger();
        MapGenStructureIO.startNameToClassMap = Maps.newHashMap();
        MapGenStructureIO.startClassToNameMap = Maps.newHashMap();
        MapGenStructureIO.componentNameToClassMap = Maps.newHashMap();
        MapGenStructureIO.componentClassToNameMap = Maps.newHashMap();
        registerStructure(StructureMineshaftStart.class, "Mineshaft");
        registerStructure(MapGenVillage.Start.class, "Village");
        registerStructure(MapGenNetherBridge.Start.class, "Fortress");
        registerStructure(MapGenStronghold.Start.class, "Stronghold");
        registerStructure(MapGenScatteredFeature.Start.class, "Temple");
        registerStructure(StructureOceanMonument.StartMonument.class, "Monument");
    }
    
    static void registerStructureComponent(final Class clazz, final String s) {
        MapGenStructureIO.componentNameToClassMap.put(s, clazz);
        MapGenStructureIO.componentClassToNameMap.put(clazz, s);
    }
    
    public static String getStructureComponentName(final StructureComponent structureComponent) {
        return MapGenStructureIO.componentClassToNameMap.get(structureComponent.getClass());
    }
    
    public static StructureStart getStructureStart(final NBTTagCompound nbtTagCompound, final World world) {
        StructureStart structureStart = null;
        final Class<StructureStart> clazz = MapGenStructureIO.startNameToClassMap.get(nbtTagCompound.getString("id"));
        if (clazz != null) {
            structureStart = clazz.newInstance();
        }
        if (structureStart != null) {
            structureStart.readStructureComponentsFromNBT(world, nbtTagCompound);
        }
        else {
            MapGenStructureIO.logger.warn("Skipping Structure with id " + nbtTagCompound.getString("id"));
        }
        return structureStart;
    }
    
    public static StructureComponent getStructureComponent(final NBTTagCompound nbtTagCompound, final World world) {
        StructureComponent structureComponent = null;
        final Class<StructureComponent> clazz = MapGenStructureIO.componentNameToClassMap.get(nbtTagCompound.getString("id"));
        if (clazz != null) {
            structureComponent = clazz.newInstance();
        }
        if (structureComponent != null) {
            structureComponent.readStructureBaseNBT(world, nbtTagCompound);
        }
        else {
            MapGenStructureIO.logger.warn("Skipping Piece with id " + nbtTagCompound.getString("id"));
        }
        return structureComponent;
    }
}
