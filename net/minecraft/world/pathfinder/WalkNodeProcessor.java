package net.minecraft.world.pathfinder;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WalkNodeProcessor extends NodeProcessor
{
    private boolean shouldAvoidWater;
    private boolean canEnterDoors;
    private boolean avoidsWater;
    private boolean canSwim;
    private boolean canBreakDoors;
    
    @Override
    public void initProcessor(final IBlockAccess blockAccess, final Entity entity) {
        super.initProcessor(blockAccess, entity);
        this.shouldAvoidWater = this.avoidsWater;
    }
    
    @Override
    public int findPathOptions(final PathPoint[] array, final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final float n) {
        if (this.getVerticalOffset(entity, pathPoint.xCoord, pathPoint.yCoord + 1, pathPoint.zCoord) == 1) {}
        final PathPoint safePoint = this.getSafePoint(entity, pathPoint.xCoord, pathPoint.yCoord, pathPoint.zCoord + 1, 1);
        final PathPoint safePoint2 = this.getSafePoint(entity, pathPoint.xCoord - 1, pathPoint.yCoord, pathPoint.zCoord, 1);
        final PathPoint safePoint3 = this.getSafePoint(entity, pathPoint.xCoord + 1, pathPoint.yCoord, pathPoint.zCoord, 1);
        final PathPoint safePoint4 = this.getSafePoint(entity, pathPoint.xCoord, pathPoint.yCoord, pathPoint.zCoord - 1, 1);
        int n3 = 0;
        if (safePoint != null && !safePoint.visited && safePoint.distanceTo(pathPoint2) < n) {
            final int n2 = 0;
            ++n3;
            array[n2] = safePoint;
        }
        if (safePoint2 != null && !safePoint2.visited && safePoint2.distanceTo(pathPoint2) < n) {
            final int n4 = 0;
            ++n3;
            array[n4] = safePoint2;
        }
        if (safePoint3 != null && !safePoint3.visited && safePoint3.distanceTo(pathPoint2) < n) {
            final int n5 = 0;
            ++n3;
            array[n5] = safePoint3;
        }
        if (safePoint4 != null && !safePoint4.visited && safePoint4.distanceTo(pathPoint2) < n) {
            final int n6 = 0;
            ++n3;
            array[n6] = safePoint4;
        }
        return 0;
    }
    
    @Override
    public void postProcess() {
        super.postProcess();
        this.avoidsWater = this.shouldAvoidWater;
    }
    
    public void setAvoidsWater(final boolean avoidsWater) {
        this.avoidsWater = avoidsWater;
    }
    
    @Override
    public PathPoint getPathPointToCoords(final Entity entity, final double n, final double n2, final double n3) {
        return this.openPoint(MathHelper.floor_double(n - entity.width / 2.0f), MathHelper.floor_double(n2), MathHelper.floor_double(n3 - entity.width / 2.0f));
    }
    
    public static int func_176170_a(final IBlockAccess blockAccess, final Entity entity, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final boolean b2, final boolean b3) {
        final BlockPos blockPos = new BlockPos(entity);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = n; i < n + n4; ++i) {
            for (int j = n2; j < n2 + n5; ++j) {
                for (int k = n3; k < n3 + n6; ++k) {
                    mutableBlockPos.func_181079_c(i, j, k);
                    final Block block = blockAccess.getBlockState(mutableBlockPos).getBlock();
                    if (block.getMaterial() != Material.air) {
                        if (block != Blocks.trapdoor && block != Blocks.iron_trapdoor) {
                            if (block != Blocks.flowing_water && block != Blocks.water) {
                                if (!b3 && block instanceof BlockDoor && block.getMaterial() == Material.wood) {
                                    return 0;
                                }
                            }
                            else if (b) {
                                return -1;
                            }
                        }
                        if (entity.worldObj.getBlockState(mutableBlockPos).getBlock() instanceof BlockRailBase) {
                            if (!(entity.worldObj.getBlockState(blockPos).getBlock() instanceof BlockRailBase) && !(entity.worldObj.getBlockState(blockPos.down()).getBlock() instanceof BlockRailBase)) {
                                return -3;
                            }
                        }
                        else if (!block.isPassable(blockAccess, mutableBlockPos) && (!b2 || !(block instanceof BlockDoor) || block.getMaterial() != Material.wood)) {
                            if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall) {
                                return -3;
                            }
                            if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor) {
                                return -4;
                            }
                            if (block.getMaterial() != Material.lava) {
                                return 0;
                            }
                            if (!entity.isInLava()) {
                                return -2;
                            }
                        }
                    }
                }
            }
        }
        return 2;
    }
    
    private int getVerticalOffset(final Entity entity, final int n, final int n2, final int n3) {
        return func_176170_a(this.blockaccess, entity, n, n2, n3, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.avoidsWater, this.canBreakDoors, this.canEnterDoors);
    }
    
    @Override
    public PathPoint getPathPointTo(final Entity entity) {
        int floor_double;
        if (this.canSwim && entity.isInWater()) {
            floor_double = (int)entity.getEntityBoundingBox().minY;
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(MathHelper.floor_double(entity.posX), floor_double, MathHelper.floor_double(entity.posZ));
            for (Block block = this.blockaccess.getBlockState(mutableBlockPos).getBlock(); block == Blocks.flowing_water || block == Blocks.water; block = this.blockaccess.getBlockState(mutableBlockPos).getBlock()) {
                ++floor_double;
                mutableBlockPos.func_181079_c(MathHelper.floor_double(entity.posX), floor_double, MathHelper.floor_double(entity.posZ));
            }
            this.avoidsWater = false;
        }
        else {
            floor_double = MathHelper.floor_double(entity.getEntityBoundingBox().minY + 0.5);
        }
        return this.openPoint(MathHelper.floor_double(entity.getEntityBoundingBox().minX), floor_double, MathHelper.floor_double(entity.getEntityBoundingBox().minZ));
    }
    
    public boolean getAvoidsWater() {
        return this.avoidsWater;
    }
    
    public void setCanSwim(final boolean canSwim) {
        this.canSwim = canSwim;
    }
    
    public void setBreakDoors(final boolean canBreakDoors) {
        this.canBreakDoors = canBreakDoors;
    }
    
    public boolean getCanSwim() {
        return this.canSwim;
    }
    
    private PathPoint getSafePoint(final Entity entity, final int n, int n2, final int n3, final int n4) {
        PathPoint pathPoint = null;
        final int verticalOffset = this.getVerticalOffset(entity, n, n2, n3);
        if (verticalOffset == 2) {
            return this.openPoint(n, n2, n3);
        }
        if (verticalOffset == 1) {
            pathPoint = this.openPoint(n, n2, n3);
        }
        if (pathPoint == null && n4 > 0 && verticalOffset != -3 && verticalOffset != -4 && this.getVerticalOffset(entity, n, n2 + n4, n3) == 1) {
            pathPoint = this.openPoint(n, n2 + n4, n3);
            n2 += n4;
        }
        if (pathPoint != null) {
            if (n2 > 0) {
                this.getVerticalOffset(entity, n, n2 - 1, n3);
                if (this.avoidsWater) {}
            }
        }
        return pathPoint;
    }
    
    public boolean getEnterDoors() {
        return this.canEnterDoors;
    }
    
    public void setEnterDoors(final boolean canEnterDoors) {
        this.canEnterDoors = canEnterDoors;
    }
}
