package net.minecraft.world;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;

public class Teleporter
{
    private final List destinationCoordinateKeys;
    private final Random random;
    private final LongHashMap destinationCoordinateCache;
    private final WorldServer worldServerInstance;
    
    public void placeInPortal(final Entity entity, final float n) {
        if (this.worldServerInstance.provider.getDimensionId() != 1) {
            if (n != 0) {
                this.makePortal(entity);
                this.placeInExistingPortal(entity, n);
            }
            return;
        }
        final int floor_double = MathHelper.floor_double(entity.posX);
        final int n2 = MathHelper.floor_double(entity.posY) - 1;
        final int floor_double2 = MathHelper.floor_double(entity.posZ);
        while (true) {
            this.worldServerInstance.setBlockState(new BlockPos(floor_double - 2 + 0, n2 - 1, floor_double2 + 0 + 2), true ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
            int n3 = 0;
            ++n3;
        }
    }
    
    public void removeStalePortalLocations(final long n) {
        if (n % 100L == 0L) {
            final Iterator<Long> iterator = (Iterator<Long>)this.destinationCoordinateKeys.iterator();
            final long n2 = n - 300L;
            while (iterator.hasNext()) {
                final Long n3 = iterator.next();
                final PortalPosition portalPosition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(n3);
                if (portalPosition == null || portalPosition.lastUpdateTime < n2) {
                    iterator.remove();
                    this.destinationCoordinateCache.remove(n3);
                }
            }
        }
    }
    
    public boolean makePortal(final Entity entity) {
        final double n = -1.0;
        final int floor_double = MathHelper.floor_double(entity.posX);
        MathHelper.floor_double(entity.posY);
        final int floor_double2 = MathHelper.floor_double(entity.posZ);
        final int n2 = floor_double;
        final int nextInt = this.random.nextInt(4);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int n6 = 0;
        for (int i = floor_double - 16; i <= floor_double + 16; ++i) {
            final double n3 = i + 0.5 - entity.posX;
            if (0 <= floor_double2 + 16) {
                final double n4 = 0 + 0.5 - entity.posZ;
                int n5 = this.worldServerInstance.getActualHeight() - 1;
                while (true) {
                    if (this.worldServerInstance.isAirBlock(mutableBlockPos.func_181079_c(i, 0, 0))) {
                        n6 = nextInt;
                        if (0 < nextInt + 4) {
                            while (true) {
                                mutableBlockPos.func_181079_c(-1, -1, -4);
                                if (!this.worldServerInstance.getBlockState(mutableBlockPos).getBlock().getMaterial().isSolid()) {
                                    break;
                                }
                                int n7 = 0;
                                ++n7;
                            }
                        }
                    }
                    --n5;
                }
            }
            else {}
        }
        if (n < 0.0) {
            for (int j = floor_double - 16; j <= floor_double + 16; ++j) {
                final double n8 = j + 0.5 - entity.posX;
                if (0 <= floor_double2 + 16) {
                    final double n9 = 0 + 0.5 - entity.posZ;
                    int n10 = this.worldServerInstance.getActualHeight() - 1;
                    while (true) {
                        if (this.worldServerInstance.isAirBlock(mutableBlockPos.func_181079_c(j, 0, 0))) {
                            n6 = nextInt;
                            if (0 < nextInt + 2) {
                                while (true) {
                                    final int n7 = j + 1;
                                    mutableBlockPos.func_181079_c(-1, -1, -1);
                                    if (!this.worldServerInstance.getBlockState(mutableBlockPos).getBlock().getMaterial().isSolid()) {
                                        break;
                                    }
                                    int n11 = 0;
                                    ++n11;
                                }
                            }
                        }
                        --n10;
                    }
                }
                else {}
            }
        }
        final int n12 = n2;
        if (n < 0.0) {
            MathHelper.clamp_int(0, 70, this.worldServerInstance.getActualHeight() - 10);
            while (true) {
                final int n13 = n12 + 0 - 1;
                final int n11 = 0;
                this.worldServerInstance.setBlockState(new BlockPos(-1, 2, 0), Blocks.air.getDefaultState());
                ++n6;
            }
        }
        else {
            final IBlockState withProperty = Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, EnumFacing.Axis.Z);
            while (true) {
                final int n11 = -1;
                final int n7 = 1;
                this.worldServerInstance.setBlockState(new BlockPos(2, 0, -1), withProperty, 2);
                int n13 = 0;
                ++n13;
            }
        }
    }
    
    public Teleporter(final WorldServer worldServerInstance) {
        this.destinationCoordinateCache = new LongHashMap();
        this.destinationCoordinateKeys = Lists.newArrayList();
        this.worldServerInstance = worldServerInstance;
        this.random = new Random(worldServerInstance.getSeed());
    }
    
    public class PortalPosition extends BlockPos
    {
        final Teleporter this$0;
        public long lastUpdateTime;
        
        public PortalPosition(final Teleporter this$0, final BlockPos blockPos, final long lastUpdateTime) {
            this.this$0 = this$0;
            super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.lastUpdateTime = lastUpdateTime;
        }
    }
}
