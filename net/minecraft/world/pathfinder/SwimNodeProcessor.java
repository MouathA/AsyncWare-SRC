package net.minecraft.world.pathfinder;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class SwimNodeProcessor extends NodeProcessor
{
    @Override
    public PathPoint getPathPointTo(final Entity entity) {
        return this.openPoint(MathHelper.floor_double(entity.getEntityBoundingBox().minX), MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5), MathHelper.floor_double(entity.getEntityBoundingBox().minZ));
    }
    
    @Override
    public PathPoint getPathPointToCoords(final Entity entity, final double n, final double n2, final double n3) {
        return this.openPoint(MathHelper.floor_double(n - entity.width / 2.0f), MathHelper.floor_double(n2 + 0.5), MathHelper.floor_double(n3 - entity.width / 2.0f));
    }
    
    private PathPoint getSafePoint(final Entity entity, final int n, final int n2, final int n3) {
        return (this.func_176186_b(entity, n, n2, n3) == -1) ? this.openPoint(n, n2, n3) : null;
    }
    
    @Override
    public int findPathOptions(final PathPoint[] array, final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        final EnumFacing[] values = EnumFacing.values();
        while (0 < values.length) {
            final EnumFacing enumFacing = values[0];
            final PathPoint safePoint = this.getSafePoint(entity, pathPoint.xCoord + enumFacing.getFrontOffsetX(), pathPoint.yCoord + enumFacing.getFrontOffsetY(), pathPoint.zCoord + enumFacing.getFrontOffsetZ());
            if (safePoint != null && !safePoint.visited && safePoint.distanceTo(pathPoint2) < n) {
                final int n2 = 0;
                int n3 = 0;
                ++n3;
                array[n2] = safePoint;
            }
            int n4 = 0;
            ++n4;
        }
        return 0;
    }
    
    @Override
    public void initProcessor(final IBlockAccess blockAccess, final Entity entity) {
        super.initProcessor(blockAccess, entity);
    }
    
    @Override
    public void postProcess() {
        super.postProcess();
    }
    
    private int func_176186_b(final Entity entity, final int n, final int n2, final int n3) {
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = n; i < n + this.entitySizeX; ++i) {
            for (int j = n2; j < n2 + this.entitySizeY; ++j) {
                for (int k = n3; k < n3 + this.entitySizeZ; ++k) {
                    if (this.blockaccess.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock().getMaterial() != Material.water) {
                        return 0;
                    }
                }
            }
        }
        return -1;
    }
}
