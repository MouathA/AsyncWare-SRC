package net.minecraft.pathfinding;

import net.minecraft.world.pathfinder.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class PathFinder
{
    private Path path;
    private PathPoint[] pathOptions;
    private NodeProcessor nodeProcessor;
    
    public PathEntity createEntityPathTo(final IBlockAccess blockAccess, final Entity entity, final BlockPos blockPos, final float n) {
        return this.createEntityPathTo(blockAccess, entity, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, n);
    }
    
    private PathEntity addToPath(final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        pathPoint.totalPathDistance = 0.0f;
        pathPoint.distanceToNext = pathPoint.distanceToSquared(pathPoint2);
        pathPoint.distanceToTarget = pathPoint.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(pathPoint);
        PathPoint pathPoint3 = pathPoint;
        while (!this.path.isPathEmpty()) {
            final PathPoint dequeue = this.path.dequeue();
            if (dequeue.equals(pathPoint2)) {
                return this.createEntityPath(pathPoint, pathPoint2);
            }
            if (dequeue.distanceToSquared(pathPoint2) < pathPoint3.distanceToSquared(pathPoint2)) {
                pathPoint3 = dequeue;
            }
            dequeue.visited = true;
            while (0 < this.nodeProcessor.findPathOptions(this.pathOptions, entity, dequeue, pathPoint2, n)) {
                final PathPoint pathPoint4 = this.pathOptions[0];
                final float totalPathDistance = dequeue.totalPathDistance + dequeue.distanceToSquared(pathPoint4);
                if (totalPathDistance < n * 2.0f && (!pathPoint4.isAssigned() || totalPathDistance < pathPoint4.totalPathDistance)) {
                    pathPoint4.previous = dequeue;
                    pathPoint4.totalPathDistance = totalPathDistance;
                    pathPoint4.distanceToNext = pathPoint4.distanceToSquared(pathPoint2);
                    if (pathPoint4.isAssigned()) {
                        this.path.changeDistance(pathPoint4, pathPoint4.totalPathDistance + pathPoint4.distanceToNext);
                    }
                    else {
                        pathPoint4.distanceToTarget = pathPoint4.totalPathDistance + pathPoint4.distanceToNext;
                        this.path.addPoint(pathPoint4);
                    }
                }
                int n2 = 0;
                ++n2;
            }
        }
        if (pathPoint3 == pathPoint) {
            return null;
        }
        return this.createEntityPath(pathPoint, pathPoint3);
    }
    
    public PathFinder(final NodeProcessor nodeProcessor) {
        this.path = new Path();
        this.pathOptions = new PathPoint[32];
        this.nodeProcessor = nodeProcessor;
    }
    
    public PathEntity createEntityPathTo(final IBlockAccess blockAccess, final Entity entity, final Entity entity2, final float n) {
        return this.createEntityPathTo(blockAccess, entity, entity2.posX, entity2.getEntityBoundingBox().minY, entity2.posZ, n);
    }
    
    private PathEntity createEntityPath(final PathPoint pathPoint, final PathPoint pathPoint2) {
        int n = 0;
        for (PathPoint previous = pathPoint2; previous.previous != null; previous = previous.previous) {
            ++n;
        }
        final PathPoint[] array = { null };
        PathPoint previous2 = pathPoint2;
        --n;
        array[1] = pathPoint2;
        while (previous2.previous != null) {
            previous2 = previous2.previous;
            --n;
            array[1] = previous2;
        }
        return new PathEntity(array);
    }
    
    private PathEntity createEntityPathTo(final IBlockAccess blockAccess, final Entity entity, final double n, final double n2, final double n3, final float n4) {
        this.path.clearPath();
        this.nodeProcessor.initProcessor(blockAccess, entity);
        final PathEntity addToPath = this.addToPath(entity, this.nodeProcessor.getPathPointTo(entity), this.nodeProcessor.getPathPointToCoords(entity, n, n2, n3), n4);
        this.nodeProcessor.postProcess();
        return addToPath;
    }
}
