package net.minecraft.world.pathfinder;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;

public abstract class NodeProcessor
{
    protected IntHashMap pointMap;
    protected int entitySizeY;
    protected int entitySizeZ;
    protected int entitySizeX;
    protected IBlockAccess blockaccess;
    
    public abstract PathPoint getPathPointTo(final Entity p0);
    
    public abstract PathPoint getPathPointToCoords(final Entity p0, final double p1, final double p2, final double p3);
    
    public NodeProcessor() {
        this.pointMap = new IntHashMap();
    }
    
    public abstract int findPathOptions(final PathPoint[] p0, final Entity p1, final PathPoint p2, final PathPoint p3, final float p4);
    
    public void postProcess() {
    }
    
    protected PathPoint openPoint(final int n, final int n2, final int n3) {
        final int hash = PathPoint.makeHash(n, n2, n3);
        PathPoint pathPoint = (PathPoint)this.pointMap.lookup(hash);
        if (pathPoint == null) {
            pathPoint = new PathPoint(n, n2, n3);
            this.pointMap.addKey(hash, pathPoint);
        }
        return pathPoint;
    }
    
    public void initProcessor(final IBlockAccess blockaccess, final Entity entity) {
        this.blockaccess = blockaccess;
        this.pointMap.clearMap();
        this.entitySizeX = MathHelper.floor_float(entity.width + 1.0f);
        this.entitySizeY = MathHelper.floor_float(entity.height + 1.0f);
        this.entitySizeZ = MathHelper.floor_float(entity.width + 1.0f);
    }
}
